/**
 * 
 */
package com.toyo.fish.game.internal.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.event.WebsocketEvent;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.util.G;
import com.toyo.fish.data.wrapper.domain.Login;
import com.toyo.fish.game.logic.handler.GlobalUserManager;
import com.toyo.fish.game.logic.handler.RemoteObjectCache;

import com.toyo.fish.protocol.beans.PP0000Beans.PP0003Response;
import com.toyo.remote.service.payment.domain.PaymentOrder;
import com.toyo.remote.service.user.IPaymentNotifyService;

/**
 * @author sparrow
 *
 */
@Service("IPaymentNotifyService")
public class DefaultPaymentNotifyService implements IPaymentNotifyService {
	
	private static final Log logger=LogFactory.getLog(DefaultPaymentNotifyService.class);

	/* (non-Javadoc)
	 * @see com.toyo.remote.service.game.IPaymentNotifyService#notifyPaymentResult(long, java.lang.String, int)
	 */
	public boolean notifyPaymentResult(Long userId, PaymentOrder po) {
		// TODO Auto-generated method stub
		logger.info("IPaymentNotifyService.notifyPaymentResult("+userId+","+po.toString()+")");
		try {
			
				
				PP0003Response o=G.o(PP0003Response.class);
				//o.setOrderId(orderId);
				o.setState(1);
				
				RouterHeader r=GlobalUserManager.localLoginService().findRouteHeaderByUserId(userId);
				
				logger.info("loginService:"+r.toString());
				//r.setConnectionId(r.get);
				o.setOrder(po);
				
				WebsocketEvent.send(r, o, false);
				
			} catch (LocalServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
		
		
		return false;
	}

}
