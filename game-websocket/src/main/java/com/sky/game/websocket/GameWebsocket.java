/**
 * 
 */
package com.sky.game.websocket;

import java.nio.ByteBuffer;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.websocket.util.HexDump;


/**
 * @author sparrow
 *
 */
@ServerEndpoint(value="/api")
public class GameWebsocket {
	private static final Log logger=LogFactory.getLog(GameWebsocket.class);
	
	SessionContext context;
	
	
	/**
	 * on web socket open
	 * 
	 */
	private static final int K=1024;
	@OnOpen
	public void onOpen(Session session){
		session.setMaxBinaryMessageBufferSize(128*K);
		context=SessionContext.obtain(session);
	}
	
	/**
	 *  on web socket close
	 */
	@OnClose
	public void onClosed(){
		
		if(context!=null){
			logger.error(context.toString()+" onClosed!");
			context.unbind();
		}
	}
	
	/**
	 * ByteBuffer.
	 * 
	 *    
	 * on device binding.
	 * 
	 * 
	 * 
	 * @param message
	 * @throws Exception 
	 */
	@OnMessage
	public void onMessage(String message) throws Exception{
		
		//throw new Exception("Not implments yet.");
		//logger.info("buffer-length:"+message.length());
	}
	
	/**
	 * 
	 * byte 0 :   0 - async packet
	 *            1 - sync packet
	 *            2 - binding
	 *            3 - ping
	 *            4 - invalid
	 * byte 1 :   0 - no encrypt
	 * byte-      (transcode&token&content) encrypt
	 * 
	 * 
	 * 
	 * @param buffer
	 */
	
	@OnMessage
	public void onMessage(ByteBuffer buffer){
		//logger.info(HexDump.dumpHexString(buffer.array(),0,buffer.limit()));
		//SessionContext.receive(context.session, 1, buffer.limit());
		try {
			if(context!=null){
				context.receive(1, buffer.limit());
				context.request(buffer);
			//	context.session.getAsyncRemote().sendBinary(buffer);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	@OnError
	public void onError(Throwable t) throws Throwable{
		if(context!=null){
			//t.printStackTrace();
			//context.session.st
			logger.error(context.toString()+" onError:"+t.getMessage());
			context.session.close();
			//context.unbind();
		
			
		}
	}
	

}
