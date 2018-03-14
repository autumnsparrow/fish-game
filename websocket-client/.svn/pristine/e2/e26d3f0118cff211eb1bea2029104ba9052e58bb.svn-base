/**
 * 
 */
package com.toyo.fish.websocket.client;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.introspector.AnnotationIntrospector.ProtocolMapEntry;
import com.sky.game.context.util.G;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0001Requet;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0006Request;
import com.toyo.fish.websocket.client.WebsocketClientHandler.IWebsocketMessageHandle;

/**
 * @author sparrow
 *
 */
public class FishWebsocketWrapper  {
	
	private static final Log logger=LogFactory.getLog(FishWebsocketWrapper.class);
	
	public static  interface IOnClosed{
		void onClose(int state,String reason);
	}
	
	
	String destUri;
	
	String deviceId;
	int channel;
	int timeout;
	Long userId;
	
	boolean online;
	
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}


	Map<Long,FishWebsocketWrapper>  wrappers;
	 
	public void setWrappers(Map<Long, FishWebsocketWrapper> wrappers) {
		this.wrappers = wrappers;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	WebsocketClientHandler socket;
	
	
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getDestUri() {
		return destUri;
	}

	public void setDestUri(String destUri) {
		this.destUri = destUri;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}
	
	
	
	
	
	public FishWebsocketWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * create a connecitons.
	 * @param destUri
	 * @param deviceId
	 * @param channel
	 * @param timeout
	 */
	public FishWebsocketWrapper(String destUri, String deviceId, int channel, int timeout) {
		super();
		this.destUri = destUri;
		this.deviceId = deviceId;
		this.channel = channel;
		this.timeout = timeout;
		this.lastOrderTimestamp=System.currentTimeMillis();
		this.online=false;
		socket = new WebsocketClientHandler(deviceId,channel);
		
	}

	public void setCallbackHandler(IWebsocketMessageHandle handler){
		this.socket.setAfterLoginHandler(handler);
	}
	
	private static final Object lock=new Object();
	private   WebSocketClient client ;//= new WebSocketClient();
	
	
	
	
	public WebsocketClientHandler getSocket() {
		return socket;
	}

	public void connect(IWebsocketMessageHandle handler){
		
		try {
			
			
		
			this.socket.setAfterLoginHandler(handler);
			socket.setOnClosed(new IOnClosed() {
				
				public void onClose(int state,String reason) {
					// TODO Auto-generated method stub
					onClientClosed(state,reason);
				}
			});
			//client.start();
			URI echoUri = new URI(destUri);
			ClientUpgradeRequest request = new ClientUpgradeRequest();
			client.connect(socket, echoUri, request);
			System.out.printf(">>>>>>>>>>>>>Connecting to : %s%n", echoUri);
			socket.awaitClose(timeout, TimeUnit.SECONDS);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				client.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public  void connect() {
		//client = new WebSocketClient();
		
		try {
			
			//socket = new WebsocketClientHandler(deviceId,channel);
			socket.setOnClosed(new IOnClosed() {
				
				public void onClose(int state,String reason) {
					// TODO Auto-generated method stub
					onClientClosed(state,reason);
				}
			});
			
			
			//client.start();
			URI echoUri = new URI(destUri);
			ClientUpgradeRequest request = new ClientUpgradeRequest();
			client.connect(socket, echoUri, request);
			System.out.printf(">>>>>>>>>>>>>Connecting to : %s%n", echoUri);
			socket.awaitClose(timeout, TimeUnit.SECONDS);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				client.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void setClient(WebSocketClient client) {
		this.client = client;
		
	}

	public <R> void send(String trancode,String retranscode,R o,IWebsocketMessageHandle callback){
		
				this.socket.send(trancode, retranscode, o, callback);
	}
	
	public void add(String transcode,IWebsocketMessageHandle callback){
		this.socket.add(transcode, callback);
	}
	
	
	
	public void disconnect(){
		
		try {
			client.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static boolean flag = false;
	
	long lastOrderTimestamp;
	
	
	public boolean shouldWarn(){
		return System.currentTimeMillis()-lastOrderTimestamp>5*60*1000;
	}
	
	public long getLastTimestamp(){
		return lastOrderTimestamp;
	}
	
	public boolean getFlag(){
		return flag;
	}
	
	public void reset(){
		lastOrderTimestamp=System.currentTimeMillis();
	}
	
	public void sendPaymentOrder() {
		if (flag) {
			add("PP0001", new IWebsocketMessageHandle() {

				
				public void handle(String content) {
					// TODO Auto-generated method stub
					logger.info(content);
					lastOrderTimestamp=System.currentTimeMillis();
					
				}
			});

			PP0001Requet req = G.o(PP0001Requet.class);
			req.setPrice(600);
			req.setProductId("DLB");
			socket.send("PP0001", req);
			flag = false;
		} else {
			add("PP0006", new IWebsocketMessageHandle() {

				
				public void handle(String content) {
					// TODO Auto-generated method stub
					logger.info(content);
					lastOrderTimestamp=System.currentTimeMillis();
				}
			});

			PP0006Request req2 = G.o(PP0006Request.class);
			req2.setPrice(600);
			req2.setProductId("DLB");
			req2.setProductDescription("Websocket Test ");
			req2.setProductTitle("Websocket Client Test ");
			socket.send("PP0006", req2);
			flag = true;
		}
	}

	@Override
	public String toString() {
		return "" + deviceId + "|" + channel + "|" + userId + " |"+online;
	}
	
	public void onClientClosed(int state,String reason){
		if(wrappers!=null){
			logger.info("connection closed:"+userId+" state:"+state+" reason:"+reason);
			wrappers.remove(userId);
		}
	}
	

}
