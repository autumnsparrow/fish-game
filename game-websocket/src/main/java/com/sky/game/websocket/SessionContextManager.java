/**
 * 
 */
package com.sky.game.websocket;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.SpringContext;
import com.sky.game.context.configuration.GameContextConfiguration;
import com.sky.game.context.event.EventHandler;
import com.sky.game.context.event.EventProcessTask;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.game.context.util.G.IExecution;
//import com.sky.game.protocol.commons.MS0001Beans;
import com.sky.game.websocket.util.WebsocketConfiguration;
import com.toyo.remote.service.system.ISystemRemoteService;
import com.toyo.remote.service.user.ILoginService;

/**
 * @author Administrator
 *
 */
@Service
public class SessionContextManager {

	public final static Logger LOGGER = LoggerFactory.getLogger("Session");

	/**
	 * 
	 * Demo of remote service invoke
	 * 
	 */
	@Autowired
	ILoginService loginService;

	@Autowired
	ISystemRemoteService systemService;
	
	static SessionContextManager instance;// new SessionContextManager();

	private static final Object lock = new Object();

	public static SessionContextManager mgr() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					// instance=new SessionContextManager();
					instance = SpringContext.getBean("sessionContextManager");
				}
			}
		}
		return instance;
	}

	public void post(Runnable task) {
		try {
			this.taskExceutor.execute(task);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SessionContextManager() {
		super();
		// TODO Auto-generated constructor stub
		taskExceutor = Executors.newFixedThreadPool(WebsocketConfiguration.getInstance().getThreadPoolSize());

		hanlder = EventHandler.obtain(taskExceutor);
		LOGGER.info("Initialized ! taskExecutor.size="+WebsocketConfiguration.getInstance().getThreadPoolSize());
	}

	// 标志session回话连接的唯一性
	public final static ConcurrentHashMap<String, Session> playerSessionMap = new ConcurrentHashMap<String, Session>();
	
	private final static ConcurrentHashMap<String, Session> closedSessionMap=new ConcurrentHashMap<String, Session>();

	public String getSessionMap() {
		return Arrays.toString(playerSessionMap.keySet().toArray(new String[] {}));
	}

	void onBindingSession(Session session) throws Exception {
		SessionContext context = SessionContext.getContext(session);
		String deviceId = context.deviceId;
		if (deviceId != null) {
			if (playerSessionMap.containsKey(deviceId)) {
				// 把前一个session断掉
				final String deviceid = deviceId;
				final String sessionId = session.getId();

				// TODO Auto-generated method stub
				Session oldSession;
				try {
					oldSession = playerSessionMap.get(deviceid);
					SessionContext oldSessionContext = SessionContext.getContext(oldSession);
					oldSessionContext.doRemoveSessionMap = false;
					//oldSession.close();
					playerSessionMap.remove(deviceid, oldSession);
					closedSessionMap.put(session.getId(), oldSession);
					LOGGER.info("replace:" + deviceid + ",close old.session:" + oldSession.getId() + " new.session:"
							+ sessionId);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//

			}
			playerSessionMap.put(deviceId, session);
			// 给客户端发送成功的消息

			// on bind
			long begin = System.currentTimeMillis();
			try {
				String seriveName = GameContextGlobals.getConfig().getServiceName();
				String sessionId = session.getId() + "@" + seriveName;

				loginService.create(sessionId, deviceId, context.desKey);
				LOGGER.info("binding :" + (System.currentTimeMillis() - begin) + " session.id:" + sessionId
						+ ",device.id:" + deviceId);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOGGER.info("bind.failed deviceId:" + deviceId);

			}
			LOGGER.info(context.toString());

		} else {
			session.close();
			LOGGER.error("DeviceId is null");
		}
	}

	void unBindingSession(Session session) {
		SessionContext context = SessionContext.getContext(session);
		String deviceId = context.deviceId;
		if (deviceId != null && playerSessionMap.containsKey(deviceId)) {
			Session oldSession = playerSessionMap.get(deviceId);
			LOGGER.info(context.toString());
			if (oldSession.getId().equals(session.getId())) {
				playerSessionMap.remove(deviceId);
			}
			try {
				loginService.deleteByDeviceId(deviceId);
			} catch (RemoteServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOGGER.error("session closed,Delete Session:" + session + "," + "SessionID=" + session.getId());
		}
	}

	public Session getSession(String deviceId) {
		Session session = null;
		if (deviceId != null && playerSessionMap.containsKey(deviceId)) {
			session = playerSessionMap.get(deviceId);
		}
		return session;
	}

	private ExecutorService taskExceutor = null;
	EventHandler<EventProcessTask<?>> hanlder;

	public EventHandler<EventProcessTask<?>> getHandler() {
		return hanlder;
	}
	
	

	//@Scheduled(fixedDelay=5000)
	public void cleanupTask(){
		
		if(closedSessionMap.size()>0){
			
			for(Iterator<String> it=closedSessionMap.keySet().iterator();it.hasNext();){
				
				// TODO Auto-generated method stub
				String id=it.next();
				try {
					Session s=closedSessionMap.get(id);
					//if(s.isOpen())
						s.close();
					//	s.setMaxIdleTimeout(1);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					closedSessionMap.remove(id);
				}
				
				
			}
		}
	}

}
