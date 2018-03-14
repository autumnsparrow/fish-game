/**
 * 
 */
package com.toyo.remote.service.payment;

import java.util.List;

import com.sky.game.context.spring.IRemoteService;
import com.sky.game.context.spring.RemoteServiceException;
import com.toyo.remote.service.payment.domain.HuaweiConf;
import com.toyo.remote.service.payment.domain.LenovoOrder;
import com.toyo.remote.service.payment.domain.PaymentOrder;
import com.toyo.remote.service.payment.domain.VivoOrder;

/**
 * 
 * 
 * fish-game(Proxy)  payment-server(Server)
 * @author sparrow
 *
 */
public interface IPaymentService extends IRemoteService {
	
	
	
	public static final int STATE_NEW=0;
	public static final int STATE_USED_SDK=1;
	public static final int STATE_USED_BY_CANCEL=6;
	public static final int STATE_EXPIRED=2;
	public static final int STATE_NOTIFIED_FAILED=3;
	public static final int STATE_NOTIFIED_SUCCEED=4;
	public static final int STATE_NOTIFIED_SUCCEED_CLIENT_USED=5;
	
	
	/**
	 * get vivo order ,which need the server request vivo server.may be slow.
	 * @param prouctId - product id (payment store item string id)
	 * @param title - product name
	 * @param desc - product description
	 * @param price - price of the product ( fen as integer)
	 * @param channelId  -  toyo client channel id
	 * @param userId    - user id
	 * @param deviceId   - user device id
	 * @return
	 * @throws RemoteServiceException
	 */
	public VivoOrder getVivoOrder(String prouctId,String title,String desc,int price,int channelId,long userId,String deviceId)throws RemoteServiceException;
	
	/**
	 * get the order id 
	 * @param prouctId
	 * @param price
	 * @param channelId
	 * @param userId
	 * @param deviceId
	 * @return
	 * @throws RemoteServiceException
	 */
	public String getOrderId(String prouctId,int price,int channelId,long userId,String deviceId)throws RemoteServiceException;

	/**
	 * 
	 * update the order state.
	 * 
	 * @param orderId
	 * @param state
	 * @return
	 */
	public boolean updateOrderState(String orderId,int state)throws RemoteServiceException;

	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws RemoteServiceException
	 */
	public List<PaymentOrder> getPaymentOrder(long userId,String deviceId)throws RemoteServiceException;
	
	
	
	/**
	 * 
	 * @param orderId - toyo server payment order id
	 * @return - payment order detail
	 * @throws RemoteServiceException
	 */
	public PaymentOrder findByOrderId(String orderId) throws RemoteServiceException;
	
	
	
	/**
	 * 
	 * 
	 * valid the flyem session id.
	 * @param uid
	 * @param sessionId
	 * @return
	 * @throws RemoteServiceException
	 */
	public boolean validFlymeSession(Long uid,String sessionId) throws RemoteServiceException;
	
	
	
	/**
	 * 
	 * 
	 * @return
	 * @throws RemoteServiceException
	 */
	public HuaweiConf getHuaweiConfiguration() throws RemoteServiceException;
	
	/**
	 * 
	 * @param uid
	 * @param token
	 * @return
	 * @throws RemoteServiceException
	 */
	public boolean validWdjToken(String uid,String token) throws RemoteServiceException;
	
	
	
	/**
	 * Get the lenovo transaction id .
	 * 
	 * @param prouctId
	 * @param title
	 * @param price
	 * @param channelId
	 * @param userId
	 * @param deviceId
	 * @return
	 * @throws RemoteServiceException
	 */
	public LenovoOrder getLenovoOrderId(String prouctId,String title,int price,int channelId,long userId,String deviceId) throws RemoteServiceException;
	


}
