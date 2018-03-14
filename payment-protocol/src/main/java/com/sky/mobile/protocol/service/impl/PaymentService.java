/**
 * 
 */
package com.sky.mobile.protocol.service.impl;

import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.game.context.util.G.IExecution;
import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.domain.OrderSeq;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.persistence.OrderSeqMapper;
import com.sky.mobile.protocol.service.FlymeService;
import com.sky.mobile.protocol.service.LenovoService;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.protocol.service.VivoService;
import com.sky.mobile.protocol.service.WdjService;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.util.U;
import com.toyo.remote.service.payment.IPaymentService;
import com.toyo.remote.service.payment.domain.HuaweiConf;
import com.toyo.remote.service.payment.domain.LenovoOrder;
import com.toyo.remote.service.payment.domain.PaymentOrder;
import com.toyo.remote.service.payment.domain.VivoOrder;

/**
 * @author sparrow
 *
 */
@Service("IPaymentService")
public class PaymentService implements IPaymentService {
	
	@Autowired
	ErrorMessage errorMessage;
	
	
	@Autowired
	PaymentOrderService paymentOrderService;
	
	@Autowired
	VivoService vivoService;
	
	@Autowired
	FlymeService flymeService;
	
	@Autowired
	WdjService wdjService;
	
	@Autowired
	OrderSeqMapper orderSeqMapper;
	
	@Autowired
	LenovoService lenovoService;
	
	@Autowired
	SmsMessageService smsMessageService;
	
	
	
	private static final Log logger=LogFactory.getLog(PaymentService.class);
	

	public PaymentService() {
		super();
		// TODO Auto-generated constructor stub
		logger.info("###############IPaymentService(servant) initialized");
	}
	
	
	private String getOrderId(Integer channel){
		String orderId=(channel==10002||channel==10003||channel==10004||channel==10005)?getSequence(10002):U.generateOrderId(channel);
		return orderId;
	}
	
	final static PaymentStatics getOrderStatics=new PaymentStatics("getOrderId");

	/* (non-Javadoc)
	 * @see com.toyo.remote.service.IPaymentService#getOrderId(java.lang.String, int, int, long, java.lang.String)
	 */
	@Override
	public String getOrderId(String prouctId, int price, int channelId,
			long userId, String deviceId) throws RemoteServiceException {
		// TODO Auto-generated method stub
		Integer channel=Integer.valueOf(channelId);
		//U.generateOrderId(channel);
		String orderId=null;
		try {
			int expireTime=1000*60*60;

			orderId = getOrderId(channel);
			
			paymentOrderService.savePaymentOrderId(1, channelId,expireTime , deviceId, price, prouctId,String.valueOf( userId), orderId);
			
			if(vivoService.isVivoChannel(channelId)){
				//TODO:
			}
			getOrderStatics.succeed.getAndIncrement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getOrderStatics.failure.getAndIncrement();
			logger.error("<FAIURE>.generateOrder(prductId:"+prouctId+",price:"+price+",channelId:"+channelId+",userId:"+userId+",deviceId:"+deviceId+") error:"+e.getMessage());
			
		}
		
		logger.info(getOrderStatics.toString());
		return orderId;
	}

	/* (non-Javadoc)
	 * @see com.toyo.remote.service.IPaymentService#updateOrderState(java.lang.String, int)
	 */
	@Override
	public boolean updateOrderState(String orderId, int state) throws RemoteServiceException{
		// TODO Auto-generated method stub
		boolean ret=false;
		
		try {
			Order o=paymentOrderService.selectByOrderId(orderId);
			
			
			Order oo=GameUtil.obtain(Order.class);
			oo.setId(o.getId());
			// only when that is
			
			if(shouldChangeState(o.getState(), state)){
				oo.setState(state);
				ret=paymentOrderService.updateOrderSelective(oo);
			}else{
				if(state==9){
					oo.setClientState(state);
					ret=paymentOrderService.updateOrderSelective(oo);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("updateOrderState (orderId:"+orderId+",state:"+state+") error:"+e.getMessage());
			
		}
		
		return ret;
	}
	
	
	
	
	private boolean shouldChangeState(int oldState,int newState){
		boolean ret=false;
		if(oldState==STATE_NEW&&(newState==STATE_USED_SDK||
				newState==STATE_USED_BY_CANCEL)){
			ret=true;
		}
		
		// can not update the payment state.
//		if(oldState==STATE_USED_SDK&&
//				(newState==STATE_NOTIFIED_FAILED||newState==STATE_NOTIFIED_SUCCEED)){
//			ret=true;
//		}
		if(oldState==STATE_NOTIFIED_SUCCEED&&(newState==STATE_NOTIFIED_SUCCEED_CLIENT_USED)){
			ret=true;
		}
		
		
		
		return ret;
	}

	@Override
	public List<PaymentOrder> getPaymentOrder(long userId, String deviceId)
			throws RemoteServiceException {
		// TODO Auto-generated method stub
		List<Order> orders=null;
		
		List<PaymentOrder> oo=null;
		try {
			orders=paymentOrderService.selectByUserIdAndImei(String.valueOf(userId), deviceId);
			
			oo = GameUtil.getList();
			for(Order o:orders){
				PaymentOrder po=G.o(PaymentOrder.class);
				po.setOrderId(o.getOrderId());
				po.setState(o.getState());
				po.setAmount(o.getAmount());
				po.setPrice(o.getPrice());
				po.setProductId(o.getProductId());
				oo.add(po);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("getPaymentOrder (userId:"+userId+",deviceId:"+deviceId+") error:"+e.getMessage());
			
		}
		
		
		
		
		
		return oo;
	}

	@Override
	public PaymentOrder findByOrderId(String orderId)
			throws RemoteServiceException {
		// TODO Auto-generated method stub
		PaymentOrder po=G.o(PaymentOrder.class);
		try {
			Order o=null;
			o=paymentOrderService.selectByOrderId(orderId);
			
			po.setOrderId(o.getOrderId());
			po.setState(o.getState());
			po.setAmount(o.getAmount());
			po.setPrice(o.getPrice());
			po.setProductId(o.getProductId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("findByOrderId (orderId:"+orderId+") error:"+e.getMessage());
			
		}
		return po;
	}
	
	/**
	 * 
	 * 
	 * get the vivo order.
	 * 
	 * 
	 * 
	 */
	final static PaymentStatics getVivoOrderStatics=new PaymentStatics("getVivoOrder");
	
	@Override
	public VivoOrder getVivoOrder(String prouctId,String title,String desc, int price, int channelId,
			long userId, String deviceId) throws RemoteServiceException {
		// TODO Auto-generated method stub
		logger.info("get vivo order");
		Integer channel=Integer.valueOf(channelId);
		int expireTime=1000*60*60;
		String orderId=U.generateOrderId(channel);
		
		
		float amount=price*0.01f;
		VivoOrder order=null;
		try {

			paymentOrderService.savePaymentOrderId(1, channelId,expireTime , deviceId, price, prouctId,String.valueOf( userId), orderId);
			//TODO:valid the channel is vivo
			if(!vivoService.isVivoChannel(channelId)){
				throw new RemoteServiceException(-1, "not a vivo channel id:"+channelId);
			}
			order=vivoService.getOrder(title, desc, amount, orderId);
			order.setLocalOrderId(orderId);
			getVivoOrderStatics.succeed.getAndIncrement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getVivoOrderStatics.failure.getAndIncrement();
			logger.error("generateVivoOrder (prductId:"+prouctId+",price:"+price+",channelId:"+channelId+",userId:"+userId+",deviceId:"+deviceId+") error:"+e.getMessage());
			
			
					//throw new RemoteServiceException(e.state, e.getMessage());
		}
		
		logger.info(getVivoOrderStatics.toString());
		return order;
	}

	@Override
	public boolean validFlymeSession(Long uid, String sessionId)
			throws RemoteServiceException {
		// TODO Auto-generated method stub
		boolean ret=false;
		try {
			ret = flymeService.validSession(uid, sessionId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("validFlymeSession (uid:"+uid+",sessionId:"+sessionId+") error:"+e.getMessage());
			
			//throw new RemoteServiceException(e.state, e.getMessage());
		}
		return ret;
	}

	@Override
	public HuaweiConf getHuaweiConfiguration() throws RemoteServiceException {
		// TODO Auto-generated method stub
		
		HuaweiConf o=G.o(HuaweiConf.class);
		o.setAppId(errorMessage.huaweiKeys.getAppId());
		o.setBuoSecret(errorMessage.huaweiKeys.getBuoSecret());
		o.setPayRsaPrivate(errorMessage.huaweiKeys.getPayRsaPrivate());
		
		
		return o;
	}

	@Override
	public boolean validWdjToken(String uid, String token)
			throws RemoteServiceException {
		// TODO Auto-generated method stub
		
		boolean ret=false;
		try {
			ret = wdjService.validToken(uid, token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("validWdjToken (uid:"+uid+",token:"+token+") error:"+e.getMessage());
			
			
			throw new RemoteServiceException(-1, "valid (uid="+uid+" ,token="+token+") failed");
		}
		return ret;
	}
	
	
	@Transactional
	public String getSequence(Integer id){
		//
		Long orderId=Long.valueOf(0);
		try {
			OrderSeq seq=orderSeqMapper.selectByPrimaryKey(id);
			if(seq==null){
				seq=G.o(OrderSeq.class);
				seq.setId(id);
				seq.setSeq(Long.valueOf(0));
				orderSeqMapper.insert(seq);
			}else{
				//seq.setSeq(seq.getse);
				int affectedRows=orderSeqMapper.updateSeqByPrimaryKey(id);
			}
			
			orderId = seq.getSeq();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("getSequence (id:"+id+") error:"+e.getMessage());
			
		}
		
		String order=String.format("%010d", orderId);
		
		return order;
	}

	final static PaymentStatics getLenovoOrderIdStatics=new PaymentStatics("getLenovoOrderId");
	
	
	@Override
	public LenovoOrder getLenovoOrderId(String prouctId, String title, int price,
			int channelId, long userId, String deviceId)
			throws RemoteServiceException {
		// TODO Auto-generated method stub
		logger.info("Get Lenovo Order id");
		if(10014!=channelId)
			throw new RemoteServiceException(-1, "not a lenovo channel" );
		
		Integer channel=Integer.valueOf(channelId);
		Integer wareId=Integer.parseInt(prouctId);
		int expireTime=1000*60*60;
		String orderId=U.generateOrderId(channel);
		PaymentOrder paymentOrder=null;
		
			
		String lenvonoOrderId=null;
		LenovoOrder o=G.o(LenovoOrder.class);
		try {
			paymentOrderService.savePaymentOrderId(1, channelId,expireTime , deviceId, price, prouctId,String.valueOf( userId), orderId);
			
			lenvonoOrderId=lenovoService.getOrder(String.valueOf(userId), wareId, title, price, orderId);
			paymentOrder=findByOrderId(orderId);
			if(paymentOrder!=null){
				//paymentOrder.setOrderId(lenvonoOrderId);
				o.setOrder(paymentOrder);
				o.setLenovoOrderId(lenvonoOrderId);
				getLenovoOrderIdStatics.succeed.getAndIncrement();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("getLenovoOrderId (prductId:"+prouctId+",price:"+price+",channelId:"+channelId+",userId:"+userId+",deviceId:"+deviceId+") error:"+e.getMessage());
			getLenovoOrderIdStatics.failure.getAndIncrement();
			
			//logger.error("getSequence (id:"+id+") error:"+e.getMessage());
			//throw new RemoteServiceException(-1, e.getMessage());
		}
		
		logger.info(getLenovoOrderIdStatics.toString());
		
		return o;
	}
	
	
	public boolean shouldWarn(){
		return getOrderStatics.shouldWarn()||getVivoOrderStatics.shouldWarn();
	}
	
	final Object lock=new Object();
	String[] mobiles=new String[]{
			"13810715929",
			"13581567526",
			"13911266887"
			
	};
	
	
	//@Scheduled(fixedRate=1000*60*10)
	public void smsWarn(){
		synchronized (lock) {
			
			
			if(shouldWarn()){
				for(String m:mobiles){
					smsMessageService.send(m, "订单生成异常！");
					logger.info("订单生成异常！");
				}
			}
		}
	}
	
	
	public void updatePaymentStaticsParam(int category,int succeedMin,int failureMax,int timeout){
		switch (category) {
		case 1:{
			getOrderStatics.failerMax=failureMax;
			getOrderStatics.succeedMin=succeedMin;
			getOrderStatics.timeout=timeout;
		}
			break;
			
		case 2:{
			getVivoOrderStatics.failerMax=failureMax;
			getVivoOrderStatics.succeedMin=succeedMin;
			getVivoOrderStatics.timeout=timeout;
		}
			break;
			
		case 3:{
			getLenovoOrderIdStatics.failerMax=failureMax;
			getLenovoOrderIdStatics.succeedMin=succeedMin;
			getLenovoOrderIdStatics.timeout=timeout;
		}
			break;

		default:
			break;
		}
	}
	
	
}
