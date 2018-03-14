/**
 * 
 */
package com.toyo.fish.game.protocol.handler;
		

import org.springframework.stereotype.Component;

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
import com.toyo.fish.protocol.beans.PU0000Beans;
import com.toyo.fish.protocol.beans.PU0000Beans.Extra;

/**
 * @author sparrow
 *
 */

@HandlerAsyncType(enable=true,enableFilter=true,namespace="ProtocolUser",transcode="PU[0-9][0-9][0-9][0-9]")
@Component("PU0000Handler")
public class PU0000Handler   extends BasicHandler{

	
	@HandlerMethod(enable=true)
	public void onRecieve(Object request,RouterHeader extra) throws ProtocolException {
		// TODO Auto-generated method stub
		super.onRecieve(request, extra);
	}
}
