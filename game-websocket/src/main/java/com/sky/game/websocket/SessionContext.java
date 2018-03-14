/**
 * 
 */
package com.sky.game.websocket;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.Message;
import com.sky.game.context.MessageException;
import com.sky.game.context.SpringContext;
import com.sky.game.context.domain.ErrorBeans;
import com.sky.game.context.domain.MinaBeans.Extra;
import com.sky.game.context.event.EventProcessTask;
import com.sky.game.context.message.IceProxyMessageInvoker;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.sky.game.context.util.G.IExecution;
import com.sky.game.websocket.context.GameConnectionStates;
import com.sky.game.websocket.context.IGameConnectionStateChanged;
import com.sky.game.websocket.packet.EncryptTypes;
import com.sky.game.websocket.packet.PacketTypes;
import com.toyo.remote.service.system.ISystemRemoteService;
import com.toyo.remote.service.system.domain.ProtocolHandleConfig;
import com.toyo.remote.service.user.ILoginService;

/**
 * 
 * connectionId connection established. ( openedTimestamp)
 * 
 * deviceId device binding connection.
 * 
 * requestRecieved (bytesReceived) responsePushed (bytesSent)
 * 
 * connection idle connection exception. (should close the connection)
 * 
 * device un-binding connection/ connection closed (closedTimestamp)
 * 
 * 
 * 
 * 
 * @author sparrow
 *
 */

public class SessionContext implements Serializable, IGameConnectionStateChanged {

	private static final Log logger = LogFactory.getLog(SessionContext.class);

	public boolean isEncrypt() {
		return encrypt;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7011960836362363693L;
	private static final Future<Void> COMPLETED = new CompletedFuture();
	Future<Void> f = COMPLETED;
	String deviceId;
	String connectionId;
	long bindingTimestamp;
	long unbindingTimestamp;

	long createdTimestamp;
	long openedTimestamp;
	long closedTimestamp;
	long lastRequestTimestamp;
	long lastIdleTimestamp;
	long requestReceived;
	long responsePushed;

	long bytesReceived;
	long bytesSent;

	int idle;

	GameConnectionStates states;
	Session session;
	String desKey;
	boolean encrypt;

	boolean doRemoveSessionMap;

	RouterHeader header;

	private static final AtomicInteger snakeIds = new AtomicInteger(0);

	private static final ConcurrentHashMap<String, Long> ban = new ConcurrentHashMap<String, Long>();

	private final Map<String, RouterHeader> lookupTables;

	public GameConnectionStateChangedObserver getFactory() {

		return SpringContext.getBean("gameObserver");
	}

	private SessionContext(Session session) {
		super();
		this.session = session;

		session.getUserProperties().put(SessionContext.class.getSimpleName(), this);
		this.doRemoveSessionMap = true;
		this.connectionId = String.valueOf(session.getId());
		this.createdTimestamp = System.currentTimeMillis();
		this.states = GameConnectionStates.ConnectionOpened;
		this.lookupTables = GameUtil.getMap();

		if (getFactory() != null)
			getFactory().addSessionContext(this);
	}

	public static SessionContext getContext(Session session) {
		Object obj = session.getUserProperties().get(SessionContext.class.getSimpleName());
		return (obj != null) ? (SessionContext) obj : null;
	}

	public static SessionContext obtain(Session session) {
		return new SessionContext(session);
	}

	@Override
	public String toString() {
		return "<S> ( "+ "connectionId=" + connectionId +",state="+states.toString()+ ",active="
				+ (((unbindingTimestamp > 0 ? (unbindingTimestamp - bindingTimestamp) : 0)) / 1000) + " sec  ,deviceId=" + deviceId+")" ;
	}

	public void closed() {

		closedTimestamp = System.currentTimeMillis();
		states = GameConnectionStates.ConnectionClosed;
		onGameConnectionStateChanged(connectionId, states);

		if (getFactory() != null)
			getFactory().removeSession(this);

	}

	private final static long BanTime = 60000;

	private void ban(String deviceId) {
		if (ban.containsKey(deviceId)) {
			long banTime = ban.get(deviceId).longValue() - System.currentTimeMillis();
			// if(banTime>0){
			banTime = banTime > 0 ? banTime : 0;
			ban.put(deviceId, Long.valueOf(System.currentTimeMillis() + banTime + BanTime));
			// }
		} else {
			ban.put(deviceId, Long.valueOf(System.currentTimeMillis() + BanTime));
		}
	}

	public boolean shouldBan(String deviceId) {
		boolean ret = false;
		if (ban.containsKey(deviceId)) {
			long banTime = ban.get(deviceId).longValue() - System.currentTimeMillis();
			ret = banTime > 0 ? true : false;
		}
		return ret;
	}

	public long bannedTime(String deviceId) {
		long banTime = 0;
		if (ban.containsKey(deviceId)) {
			banTime = ban.get(deviceId).longValue() - System.currentTimeMillis();
			// ret=banTime>0?true:false;
		}
		return banTime / 1000;

	}
	
	int frequence=0;
	public void receive(int numbers, int bytes) {

		bytesReceived += bytes;
		requestReceived += numbers;
		
		frequence++;
		if(System.currentTimeMillis()-lastIdleTimestamp>1000){
			lastRequestTimestamp=System.currentTimeMillis();
			frequence=0;
		}
		
		states = GameConnectionStates.ConnectionDeviceRecieving;

		// checking the request frequence
		//int active = (int) ((System.currentTimeMillis() - lastRequestTimestamp) * 0.001f);

		//int frequence = active > 0 ? (int) (requestReceived / active) : 0;
		if (frequence > 10) {
			try {
				logger.info("session frequence is larger than 10.0 - "+frequence);
				remoteLoginService().updateUserBan(this.deviceId, 0);
				ban(deviceId);
				
				this.unbind();
				this.session.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// onGameConnectionStateChanged(connectionId, states);

	}

	public void sent(int numbers, int bytes) {

		bytesSent += bytes;
		responsePushed += numbers;
		states = GameConnectionStates.ConnectionDeviceSending;
		// onGameConnectionStateChanged(connectionId, states);

	}

	public void idle() {
		idle++;
		lastIdleTimestamp = System.currentTimeMillis();
		states = GameConnectionStates.ConnectionIdled;
		onGameConnectionStateChanged(connectionId, states);

	}

	public void bind(String deviceId) {

		bindingTimestamp = System.currentTimeMillis();
		states = GameConnectionStates.ConnectionDeviceBinding;
		onGameConnectionStateChanged(connectionId, states);
		onGameConnectionStateBinding(connectionId, deviceId);

		try {
			this.header = new RouterHeader(deviceId, this.connectionId);
			this.header.m(SessionContext.class.getSimpleName() + "_bind");
			logger.info((this.header != null ? this.header.toString() : ""));

			SessionContextManager.mgr().onBindingSession(session);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.header.e(SessionContext.class.getSimpleName() + "_bind");
		}

	}

	public void unbind() {

		try {
			this.header.m(SessionContext.class.getSimpleName() + "_unbind");
			unbindingTimestamp = System.currentTimeMillis();
			// ctx.deviceId=null;
			states = GameConnectionStates.ConnectionDeviceUnbind;
			onGameConnectionStateChanged(connectionId, states);
			SessionContextManager.mgr().unBindingSession(session);
			if (getFactory() != null)
				getFactory().removeSession(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			this.header.e(SessionContext.class.getSimpleName() + "_unbind");
			this.header.metrics();
		}

	}

	public void opened(Session session) {

		openedTimestamp = System.currentTimeMillis();
		states = GameConnectionStates.ConnectionOpened;
		onGameConnectionStateChanged(connectionId, states);

	}

	public void onGameConnectionStateChanged(String connectionId, GameConnectionStates state) {
		// TODO Auto-generated method stub

		if (getFactory() != null)
			getFactory().onGameConnectionStateChanged(connectionId, state);

	}

	public void onGameConnectionStateBinding(String connectionId, String deviceId) {
		// TODO Auto-generated method stub
		if (getFactory() != null)
			getFactory().onGameConnectionStateBinding(connectionId, deviceId);

	}

	public void onGameConnectionStateUnBind(String connectionId) {
		// TODO Auto-generated method stub
		if (getFactory() != null)
			getFactory().onGameConnectionStateUnBind(connectionId);
	}

	public void clearup() {
		// TODO Auto-generated method stub
		if (getFactory() != null)
			getFactory().clearup();

	}

	/**
	 * 
	 * 
	 * 
	 */

	/**
	 * 
	 * 
	 * @param buffer
	 * @return
	 */
	GameWebsocketMessage obtainMessage(ByteBuffer buffer) {
		return new GameWebsocketMessage(this, buffer);
	}

	void request(ByteBuffer buffer) {
		GameWebsocketMessage msg = obtainMessage(buffer);

		SessionContextManager.mgr().getHandler().addEvent(new EventProcessTask<GameWebsocketMessage>(msg) {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					t.unmashall();
					if (t.packetType.equalsType(PacketTypes.SyncPacketType)) {
						// forward the request.
						handleMessage(t);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					logger.error(e.getMessage());
				}
			}

		});

	}

	public GameWebsocketMessage obtainMessage(PacketTypes packetType, EncryptTypes encryptType, String content) {
		return new GameWebsocketMessage(this, packetType, encryptType, content);
	}

	public void response(GameWebsocketMessage msg) {
		SessionContextManager.mgr().getHandler().addEvent(new EventProcessTask<GameWebsocketMessage>(msg) {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					t.mashall();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	private ILoginService remoteLoginService() {
		return SpringContext.getBean("ILoginService");

	}

	// only
	private boolean validHandle(String transcode) {
		boolean ret = true;

		try {
			ProtocolHandleConfig o = remoteLoginService().getHandleConfig();
			ret = o.valid(transcode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;

	}

	private void handleException(String transcode, int state, String message) {
		ErrorBeans.Request obj = G.o(ErrorBeans.Request.class);
		obj.setState(-1);
		obj.setTranscode("ERROR");
		obj.setMessage(transcode + " - " + message);
		// handleException(obj);
		response(obtainMessage(PacketTypes.SyncPacketType, isEncrypt() ? EncryptTypes.True : EncryptTypes.False,
				GameContextGlobals.getJsonConvertor().format(obj)));

	}

	public void handleMessage(GameWebsocketMessage msg) {
		Message recieveMess = msg.getMessage();
		encrypt = msg.encryptType.booleanValue();
		// Just before processing the protocol. let's check if the protocol can
		// use.
		if (validHandle(recieveMess.transcode)) {
			// ErrorBeans.Request obj = ErrorBeans.Request.get(-1,
			// recieveMess.transcode,
			// "Protocol don't enable yet!");
			// handleException(obj);
			handleException(recieveMess.transcode, -1, "Protocol don't enable yet!");
			
			return;
		}

		try {

			boolean sync = IceProxyMessageInvoker.sync(recieveMess);
			if (!sync) {

				// intercept the PU0003
				String namespace = GameContextGlobals.locateNamespace(recieveMess.transcode);
				RouterHeader r = readRouter(namespace);
				IceProxyMessageInvoker.onRecieve(recieveMess, r);

			}

		} catch (MessageException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			handleException(recieveMess.transcode, -1, e.message);

		} catch (Exception ex) {
			handleException(recieveMess.transcode, -1, " invoked failed");
		}
	}

	private ReentrantLock lock = new ReentrantLock();

	public void sendBinary(ByteBuffer byteBuffer) {

		lock.lock();
		try {
			 session.getBasicRemote().sendBinary(byteBuffer);
			//session.getAsyncRemote().sendBinary(byteBuffer);
			if (shouldBan(deviceId)) {
				logger.info("Banned :" + deviceId + " - " + bannedTime(deviceId) + " Secs");
				session.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}

	public RouterHeader readRouter(String namespace) {
		RouterHeader r = this.lookupTables.get(namespace);

		if ((r != null && !r.isValid()) || r == null) {
			this.lookupTables.remove(namespace);
			r = this.header;
			this.header.setLookup(null);
		}
		logger.info(r.toString());
		return r;
	}

	public synchronized void writeRoute(RouterHeader routeHeader) {

		this.lookupTables.put(routeHeader.getNamespace(), routeHeader);

	}

	public void setRouterHeaderId(Long id) {
		this.header.setId(id);
	}

	private static class CompletedFuture implements Future<Void> {

		public boolean cancel(boolean mayInterruptIfRunning) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean isCancelled() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean isDone() {
			// TODO Auto-generated method stub
			return false;
		}

		public Void get() throws InterruptedException, ExecutionException {
			// TODO Auto-generated method stub
			return null;
		}

		public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
