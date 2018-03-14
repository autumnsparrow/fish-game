/**
 * 
 */
package com.toyo.fish.game.protocol.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.event.WebsocketEvent;
import com.sky.game.context.event.WebsocketEventHandler;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.route.RouterHeader;
import com.toyo.fish.data.wrapper.domain.Login;
import com.toyo.fish.game.logic.handler.GlobalUserManager;
import com.toyo.fish.protocol.beans.PS0000Beans;
import com.toyo.fish.protocol.beans.PS0000Beans.Extra;

/**
 * 
 * Protocol System
 * @author sparrow
 *
 */

@HandlerAsyncType(enable=true,enableFilter=true,namespace="ProtocolSystem",transcode="PS[0-9][0-9][0-9][0-9]")
@Component("PS0000Handler")
public class PS0000Handler implements IProtocolAsyncHandler<Object, RouterHeader> {

	private static final Log logger=LogFactory.getLog(PS0000Handler.class);
	@HandlerMethod(enable=true)
	public void onRecieve(Object request, RouterHeader extra) throws ProtocolException {
		// TODO Auto-generated method stub

		String appId=GameContextGlobals.getConfig().getServiceName();
		extra.setTo(extra.getFrom());
		extra.setFrom(appId);
		logger.info("onreceive:"+request+" route:"+extra);
		WebsocketEvent evt=WebsocketEvent.o(extra,request, true);
		
	
		
		WebsocketEventHandler.getHandler().addRecievedEvent(evt);
		
	}

}
