/**
 * 
 */
package com.sky.game.websocket.handler;


import javax.websocket.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.Message;
import com.sky.game.context.MessageAsyncHandlerPrx;
import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.configuration.GameContextConfiguration;
import com.sky.game.context.domain.BrokerMessageBeans;
import com.sky.game.context.domain.MinaBeans;
import com.sky.game.context.domain.MinaBeans.Extra;
import com.sky.game.context.domain.MinaBeans.Request;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.util.G;
import com.sky.game.websocket.SessionContext;
import com.sky.game.websocket.SessionContextManager;
import com.sky.game.websocket.packet.EncryptTypes;
import com.sky.game.websocket.packet.PacketTypes;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Response;


/**
 * @author sparrow
 *
 */
@HandlerAsyncType(namespace="GameMina",transcode="SM0001",enable=true,enableFilter=true)
@Component("MinaAsyncHandler")
public class MinaAsyncHandler implements IProtocolAsyncHandler<MinaBeans.Request, RouterHeader> {

	private static final Log logger=LogFactory.getLog(MinaAsyncHandler.class);
	/**
	 * 
	 */
	public MinaAsyncHandler() {
		// TODO Auto-generated constructor stub
	}
	
	

	@HandlerMethod(enable=true)
	public void onRecieve(Request request, RouterHeader extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
		String deviceId=extra.getDeviceId();
		
		// for test protocol purpose.
		
		
		Session session=SessionContextManager.mgr().getSession(deviceId);
		if(session==null){
			logger.info("can't find the conneciton by device id :"+deviceId+" message :"+request+" \n  ");
			// broad cast the message

			return;
		}
		
		//logger.info(extra.toString()+"- "+request.getContent());
		
		//logger.info("deviceId:"+deviceId+" session:"+session);
		SessionContext context=SessionContext.getContext(session);
		context.writeRoute(extra);
		
		if(request.getContent().contains("UP0001")){
			 Message message=getMessage(request.getContent());
			 if(!message.transcode.equals("ERROR")){
				 
			 PU0001Response up0001=GameContextGlobals.getJsonConvertor().convert(message.content, PU0001Response.class);
			 context.setRouterHeaderId(up0001.getUserId());
			 }
					  
		}else if(request.getContent().contains("UP0003")){
			
		}
		context.response(context.obtainMessage(PacketTypes.SyncPacketType, context.isEncrypt()?EncryptTypes.True:EncryptTypes.False, request.getContent()));
		
		extra.e(MinaBeans.class.getSimpleName()+"_sendMessage");
		extra.e(MessageAsyncHandlerPrx.class.getSimpleName()+"_onReceive");
		//extra.metrics();
	}

	
	public Message getMessage(String content){
		Message message=new Message();
		String[]  entries=content.split("&");
		if(entries!=null&&entries.length==3){
			message.transcode=entries[0];
			message.token=entries[1];
			message.content=entries[2];
		}
		return message;
	}
	
	

}
