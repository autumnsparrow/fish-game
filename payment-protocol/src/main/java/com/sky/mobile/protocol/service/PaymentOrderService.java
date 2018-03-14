/**
 * 
 */
package com.sky.mobile.protocol.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.persistence.OrderMapper;
import com.sky.mobile.protocol.util.U;
import com.toyo.remote.service.payment.domain.PaymentOrder;
import com.toyo.remote.service.user.IPaymentNotifyService;

/**
 * @author sparrow
 *
 */
@Service
public class PaymentOrderService {
	
	private static final Log logger=LogFactory.getLog(PaymentOrderService.class);
	
	public static enum PaymentOrderLifeStates{
		PaymentOrderLifeStateNew(0),
		PaymentOrderLifeStateUsedBySdk(1),
		PaymentOrderLifeStateUsedBySdkUserCancel(6),
		PaymentOrderLifeStateUsedBySdkExpired(2),
		PaymentOrderLifeStateServerNotifiedResultFailed(3),
		PaymentOrderLifeStateServerNotifiedResultSuccess(4),
		PaymentOrderLifeStateServerNotifiedResultSuccessClientUsed(5);
		
		
		
		
		
		
		public int state;

		private PaymentOrderLifeStates(int state) {
			this.state = state;
		}
		
	}
	
	
	@Autowired
	ErrorMessage errorMessage;
	
	@Autowired
	OrderMapper orderMapper;
	
	
	@Autowired
	IPaymentNotifyService paymentNotifyService;
	
	
	/**
	 * 
	 * 
	 * @param amount
	 * @param channelId
	 * @param expireTime
	 * @param imei
	 * @param price
	 * @param productId
	 * @param userId
	 * @param orderId
	 */
	public void savePaymentOrderId(int amount,int channelId,int expireTime,String imei,int price,String productId,String userId,String orderId){
		// expire within 1 hours.
		if(expireTime==0){
			expireTime=60;
		}
		
		Order o=U.o(Order.class);
		o.setAmount(amount);
		o.setChannelId(channelId);
		o.setExpireTime(expireTime);
		o.setImei(imei);
		o.setPrice(price);
		o.setProductId(productId);
		o.setUserId(userId);
		o.setOrderId(orderId);
		
		
		o.setState(PaymentOrderLifeStates.PaymentOrderLifeStateNew.state);
		
		orderMapper.insert(o);
	}
	
	
	public Order selectByOrderId(String orderId){
		return orderMapper.selectByOrderId(orderId);
	}
	
	public boolean updateOrderSelective(Order order){
		// 
		int affectedRows=orderMapper.updateByPrimaryKeySelective(order);
		boolean ret=affectedRows>0;
		
		return ret;
	}
	
	
	
	public void notify(Order order,int state) throws NumberFormatException, RemoteServiceException{
		try {
			if(order.getClientState()==9){
				return;
			}
			
			if(
					state==PaymentOrderLifeStates.PaymentOrderLifeStateServerNotifiedResultFailed.state||
					state==PaymentOrderLifeStates.PaymentOrderLifeStateServerNotifiedResultSuccess.state){
				//Order order = selectByOrderId(localOrderId);
				PaymentOrder po=G.o(PaymentOrder.class);
				po.setOrderId(order.getOrderId());
				po.setState(order.getState());
				po.setAmount(order.getAmount());
				po.setPrice(order.getPrice());
				po.setProductId(order.getProductId());
				paymentNotifyService.notifyPaymentResult(Long.parseLong(order.getUserId()), po);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			///e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	
	
	public List<Order> selectByUserIdAndImei(String userId,String imei){
		return orderMapper.selectByUserIdAndImei(userId, imei);
	}
	
	
	
	// every 5 minutes.
	//@Scheduled(fixedRate=1000*60*5)
	public void scheduledTask(){
		
		int affectedRows=orderMapper.updateByExpireTime();
		
		logger.info("PaymentOrderService.orderId expired:"+affectedRows);
	}
	
	
	private final Object lock=new Object();
	@Scheduled(fixedRate=1000*60*10)
	public void broadcastPayOrder(){
		synchronized (lock) {
			
			
			List<Order> orders=orderMapper.selectOrderByState(10, 50);
			long begin=System.currentTimeMillis();
			logger.info("broadcastOrder:"+orders.size());
			for(Order o:orders){
			
				try {
					logger.info("push order:"+o.toString());
					
						notify(o, o.getState());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					logger.error(e);
				} catch (RemoteServiceException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					logger.error(e);
				}catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e);
					//e.printStackTrace();
				}
			}
			logger.info("BroadTask duration:"+(System.currentTimeMillis()-begin));
			
			
		}
		//int affectedRows=orderMapper.updateByExpireTime();
		
		
		
		//logger.info("PaymentOrderService.orderId expired:"+affectedRows);
	}
	
	
	
	
	
	/**
	 * 
	 * update the order state
	 * 
	 * @param notifyId
	 * @param localOrderId
	 * @param status
	 * @param category
	 * @throws NumberFormatException
	 * @throws RemoteServiceException
	 */
	public void updateOrderState(Long notifyId,String localOrderId,String status,String category) throws NumberFormatException, RemoteServiceException{

		if (notifyId != null && notifyId.longValue() > 0) {
			logger.info("PaymentOrderService.save:" + notifyId.longValue()+" localOrderId:"+localOrderId);
			Order order = selectByOrderId(localOrderId);
			if (order != null) {
				Order o = U.o(Order.class);
				o.setId(order.getId());
				o.setNotifyId(notifyId);
				int state = status.equals("200") ? PaymentOrderLifeStates.PaymentOrderLifeStateServerNotifiedResultSuccess.state
						: PaymentOrderLifeStates.PaymentOrderLifeStateServerNotifiedResultFailed.state;
				o.setState(state);
				boolean ret = updateOrderSelective(o);

				logger.info(category + ".updateOrderSelective:" + ret + " - "
						+ o.toString());
				if (ret) {
					Order ooo=selectByOrderId(localOrderId);
					
				
					notify(ooo, state);
					//logger.info(category +"Don"
				}
				

			} else {
				logger.info(category + ".selectByOrderId:" + localOrderId
						+ " don't exits");
			}
		}
		
	}
	
	

}
