/**
 * 
 */
package com.toyo.remote.service.user;

import com.sky.game.context.spring.IRemoteService;
import com.sky.game.context.spring.RemoteServiceException;
import com.toyo.remote.service.payment.domain.PaymentOrder;

/**
 * 
 * 
 * payment result notify service
 * 
 * @author sparrow
 *
 */
public interface IPaymentNotifyService  extends IRemoteService{
	
	
	/**
	 * 
	 * update the order state.
	 * 
	 * @param orderId
	 * @param state
	 * @return
	 */
	public boolean notifyPaymentResult(Long userId,PaymentOrder order) throws RemoteServiceException;

}
