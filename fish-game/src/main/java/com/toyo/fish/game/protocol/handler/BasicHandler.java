/**
 * 
 */
package com.toyo.fish.game.protocol.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.configuration.GameContextConfiguration;
import com.sky.game.context.configuration.ice.IceServiceConfigLookup;
import com.sky.game.context.configuration.ice.IceServiceConfigRegstry;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.event.WebsocketEvent;
import com.sky.game.context.event.WebsocketEventHandler;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.route.RouterHeader;
import com.toyo.fish.data.wrapper.domain.Login;

import com.toyo.fish.game.logic.handler.GlobalUserManager;

/**
 * @author sparrow
 *
 */
public class BasicHandler  implements IProtocolAsyncHandler<Object, RouterHeader> {
	private static final Log logger=LogFactory.getLog(BasicHandler.class);
	
	protected void recieve(Object request,RouterHeader header){
		
		
		String appId=GameContextGlobals.getConfig().getServiceName();
		header.setTo(header.getFrom());
		header.setFrom(appId);
	//	logger.info(header.getTo()+","+header.getDeviceId()+","+header.getConnectionId());
		
		 try {
					
				
			if(header.getId()==0){
						String deviceId=header.getDeviceId();
						Login login =null;
						try {
							login=GlobalUserManager.localLoginService().findLoginByDeviceId(deviceId);
							if(login!=null){
								Long id=login.getUserId();
								header.setId(id);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						//　if the server don't contains
						
			}
			
			if(header.getId()==0){
				header.setId(Long.valueOf(-1));
			}
				
				
			if(request instanceof IIdentifiedObject){
					IIdentifiedObject o=(IIdentifiedObject)request;
					o.setId(header.getId());
			}
			
			logger.info(header.toString());
					 
					
					
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info(e.getMessage());
			}
	}
	
	
	public void onRecieve(Object request,RouterHeader extra) throws ProtocolException {
		// TODO Auto-generated method stub
		extra.m(this.getClass().getSimpleName()+"_onRecieve");
		recieve(request, extra);
		WebsocketEvent evt=WebsocketEvent.o(extra,request, true);
		WebsocketEventHandler.getHandler().addRecievedEvent(evt);
		extra.e(this.getClass().getSimpleName()+"_onRecieve");
		
	}

}
