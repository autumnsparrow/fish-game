/**
 * 
 */
package com.sky.game.websocket.handler;


import java.util.List;

import javax.websocket.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.Message;
import com.sky.game.context.MessageException;
import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.configuration.GameContextConfiguration;
import com.sky.game.context.configuration.ice.IceServiceConfigLookup;
import com.sky.game.context.configuration.ice.IceServiceConfigRegstry;
import com.sky.game.context.domain.BrokerMessageBeans;
import com.sky.game.context.domain.BrokerMessageBeans.Request;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.message.IceProxyMessageInvoker;
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
@HandlerAsyncType(namespace="BrokerMessage",transcode="BM0001",enable=true,enableFilter=true)
@Component("BrokerMessageAsyncHandler")
public class BrokerMessageAsyncHandler implements IProtocolAsyncHandler<BrokerMessageBeans.Request, RouterHeader> {

	private static final Log logger=LogFactory.getLog(BrokerMessageAsyncHandler.class);
	/**
	 * 
	 */
	public BrokerMessageAsyncHandler() {
		// TODO Auto-generated constructor stub
	}
	
	

	@HandlerMethod(enable=true)
	public void onRecieve(Request request, RouterHeader extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
		String namespace=request.getNamespace();
		String from=extra.getFrom();
		List<IceServiceConfigLookup> lookup=IceServiceConfigRegstry.locate(namespace);
		// from
		// broad cast.
		for(IceServiceConfigLookup l:lookup){
			if(l.getService().equals(from)){
				continue;
			}
			Message message=getMessage(request.getContent());
			logger.info("broadcast:"+l.getService());
			extra.setNamespace(namespace);
			extra.setFrom(GameContextGlobals.getConfig().getServiceName());
			extra.setTo(l.getService());
			try {
				IceProxyMessageInvoker.onRecieve(message, extra);
			} catch (MessageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
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
