/**
 * 
 */
package com.toyo.fish.game.logic.user.remote.service;

import java.util.List;

import com.sky.game.context.SpringContext;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.EventHandlerClass;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.event.WebsocketEvent;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.toyo.fish.data.wrapper.domain.Login;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.game.logic.handler.GlobalUserManager;
import com.toyo.fish.game.logic.handler.RemoteObjectCache;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0001Requet;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0001Response;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0002Requet;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0002Response;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0004Requet;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0004Response;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0005Requet;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0005Response;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0006Request;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0006Response;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0007Request;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0007Response;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0008Response;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0009Request;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0009Response;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0010Request;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0010Response;
import com.toyo.fish.protocol.service.IUserAccountManager;
import com.toyo.remote.service.payment.IPaymentService;
import com.toyo.remote.service.payment.domain.HuaweiConf;
import com.toyo.remote.service.payment.domain.LenovoOrder;
import com.toyo.remote.service.payment.domain.PaymentOrder;
import com.toyo.remote.service.payment.domain.VivoOrder;

/**
 * @author sparrow
 *
 */
public class PaymentManageRemote implements IIdentifiedObject {
	
	
	
	

	public PaymentManageRemote() {
		super();
		//userAccountManager=SpringContext.getBean("userAccountManager");
		// TODO Auto-generated constructor stub
		G.regeisterHandler(this, "PP0001","PP0002","PP0004","PP0005","PP0006","PP0007","PP0008","PP0009","PP0010");

	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.annotation.introspector.IIdentifiedObject#getId()
	 */
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return EventHandlerClass.DEFAULT_ID;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.annotation.introspector.IIdentifiedObject#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub

	}
	
	public Login getLogin(Long userId) throws LocalServiceException{
		Login login=GlobalUserManager.localLoginService().findLoginByUserId(userId);
		return login;
	}
	
	/**
	 * {@link IPaymentService#getOrderId(String, int, int, long, String)}
	 * @param evt
	 * @throws LocalServiceException 
	 * @throws RemoteServiceException 
	 */
	@RegisterEventHandler(transcode="PP0001")
	public void handlePP0001(WebsocketEvent evt) throws RemoteServiceException, LocalServiceException{
		
		PP0001Requet req=G.evtAsObj(evt);
		
		IPaymentService service=GlobalUserManager.remotePaymentService();
		Login login=getLogin(req.getId());
		
		
		IUserAccountManager manager=GlobalUserManager.localUserAccountManager();
		UserAccount account=manager.getUserAccountByUserId(req.getId());
		Integer channelId=account.getChannelId();
	
		String orderId=service.getOrderId(req.getProductId(), req.getPrice(),channelId, req.getId(), login.getDeviceId());
		
		PP0001Response resp=G.o(PP0001Response.class);
		resp.setOrderId(orderId);
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	
	
	
	/**
	 * {@link IPaymentService#updateOrderState(String, int)}
	 * @param evt
	 * @throws LocalServiceException 
	 * @throws RemoteServiceException 
	 */
	@RegisterEventHandler(transcode="PP0002")
	public void handlePP0002(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		
		PP0002Requet req=G.evtAsObj(evt);
		
		IPaymentService service=GlobalUserManager.remotePaymentService();
		
		
		boolean ret=service.updateOrderState(req.getOrderId(), req.getState());
		PP0002Response resp=G.o(PP0002Response.class);
		resp.setState(ret?1:0);

		//IPaymentService service=GlobalUserManager.remotePaymentService();
		//Login login=getLogin();
		PaymentOrder data=service.findByOrderId(req.getOrderId());
		
		if(ret&&req.getState()==9){
			data.setState(9);
		}
		resp.setOrder(data);
		
	
		WebsocketEvent.send(evt.header, resp, false);
		
		// handlePP0004
		PP0004Requet request=G.o(PP0004Requet.class);
		
		request.setId(req.getId());
		evt.obj=request;
		this.handlePP0004(evt);
		
	}
	
	
	
	
	
	
	@RegisterEventHandler(transcode="PP0004")
	public void handlePP0004(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		
		PP0004Requet req=G.evtAsObj(evt);
		
		IPaymentService service=GlobalUserManager.remotePaymentService();
		Login login=getLogin(req.getId());
		List<PaymentOrder> data=service.getPaymentOrder(req.getId(), login.getDeviceId());
		
		PP0004Response resp=G.o(PP0004Response.class);
		resp.setData(data);
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}

	
	/**
	 * 
	 * {@link IPaymentService#findByOrderId(String)}
	 * @param evt
	 * @throws LocalServiceException
	 * @throws RemoteServiceException
	 */
	@RegisterEventHandler(transcode="PP0005")
	public void handlePP0005(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		
		PP0005Requet req=G.evtAsObj(evt);
		
		IPaymentService service=GlobalUserManager.remotePaymentService();
		//Login login=getLogin();
		PaymentOrder data=service.findByOrderId(req.getOrderId());
		
		PP0005Response resp=G.o(PP0005Response.class);
		resp.setState(data.getState());
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	
	

	/**
	 * {@link IPaymentService#getVivoOrder(String, String, String, int, int, long, String)}
	 * @param evt
	 * @throws LocalServiceException 
	 * @throws RemoteServiceException 
	 */
	@RegisterEventHandler(transcode="PP0006")
	public void handlePP0006(WebsocketEvent evt) throws RemoteServiceException, LocalServiceException{
		
		PP0006Request req=G.evtAsObj(evt);
		
		IPaymentService service=GlobalUserManager.remotePaymentService();
		Login login=getLogin(req.getId());
		
	
		
		IUserAccountManager manager=GlobalUserManager.localUserAccountManager();
		UserAccount account=manager.getUserAccountByUserId(req.getId());
		Integer channelId=account.getChannelId();
	
		VivoOrder order=service.getVivoOrder(req.getProductId(), req.getProductTitle(), req.getProductDescription(), req.getPrice(), channelId, req.getId(), login.getDeviceId());
		
		//String orderId=service.getOrderId(req.getProductId(), req.getPrice(),channelId, id, login.getDeviceId());
		
		PP0006Response resp=G.o(PP0006Response.class);
		resp.setOrder(order);
		resp.setState(1);
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	
	
	
	
	/**
	 * 
	 *{@link IPaymentService#validFlymeSession(Long, String)}
	 *
	 * @param evt
	 * @throws RemoteServiceException
	 * @throws LocalServiceException
	 */
	@RegisterEventHandler(transcode="PP0007")
	public void handlePP0007(WebsocketEvent evt) throws RemoteServiceException, LocalServiceException{
		
		PP0007Request req=G.evtAsObj(evt);
		
		IPaymentService service=GlobalUserManager.remotePaymentService();
		boolean ret=service.validFlymeSession(req.getUid(), req.getSessionId());
		
		//String orderId=service.getOrderId(req.getProductId(), req.getPrice(),channelId, id, login.getDeviceId());
		
		PP0007Response resp=G.o(PP0007Response.class);
		
		resp.setState(ret?1:0);
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	
	
	

	/**
	 * 
	 *{@link IPaymentService#getHuaweiConfiguration()}
	 *
	 * @param evt
	 * @throws RemoteServiceException
	 * @throws LocalServiceException
	 */
	@RegisterEventHandler(transcode="PP0008")
	public void handlePP0008(WebsocketEvent evt) throws RemoteServiceException, LocalServiceException{
		
		//PP0008Request req=G.evtAsObj(evt);
		
		IPaymentService service=GlobalUserManager.remotePaymentService();
		//boolean ret=service.validFlymeSession(req.getUid(), req.getSessionId());
		
		//String orderId=service.getOrderId(req.getProductId(), req.getPrice(),channelId, id, login.getDeviceId());
		
		HuaweiConf conf=service.getHuaweiConfiguration();
		PP0008Response resp=G.o(PP0008Response.class);
		
		resp.setState(1);
		resp.setConf(conf);
		
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	
	
	

	/**
	 * 
	 *{@link IPaymentService#getHuaweiConfiguration()}
	 *
	 * @param evt
	 * @throws RemoteServiceException
	 * @throws LocalServiceException
	 */
	@RegisterEventHandler(transcode="PP0009")
	public void handlePP0009(WebsocketEvent evt) throws RemoteServiceException, LocalServiceException{
		
		//PP0008Request req=G.evtAsObj(evt);
		PP0009Request req=G.evtAsObj(evt);
		
		IPaymentService service=GlobalUserManager.remotePaymentService();
		//boolean ret=service.validFlymeSession(req.getUid(), req.getSessionId());
		boolean ret=service.validWdjToken(req.getUid(), req.getToken());
		//String orderId=service.getOrderId(req.getProductId(), req.getPrice(),channelId, id, login.getDeviceId());
		
		//HuaweiConf conf=service.getHuaweiConfiguration();
		PP0009Response resp=G.o(PP0009Response.class);
		
		resp.setState(ret?1:0);
		//resp.setConf(conf);
		
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	
	
	
	/**
	 * 
	 *{@link IPaymentService#getLenovoOrderId(String, String, int, int, long, String)}
	 *
	 * @param evt
	 * @throws RemoteServiceException
	 * @throws LocalServiceException
	 */
	@RegisterEventHandler(transcode="PP0010")
	public void handlePP0010(WebsocketEvent evt) throws RemoteServiceException, LocalServiceException{
		
		//PP0008Request req=G.evtAsObj(evt);
		PP0010Request req=G.evtAsObj(evt);
		
		IPaymentService service=GlobalUserManager.remotePaymentService();
	
		
		IUserAccountManager manager=GlobalUserManager.localUserAccountManager();
		UserAccount account=manager.getUserAccountByUserId(req.getId());
		Integer channelId=account.getChannelId();
	
		
		//boolean ret=service.validFlymeSession(req.getUid(), req.getSessionId());
		//boolean ret=service.validWdjToken(req.getUid(), req.getToken());
		LenovoOrder order=service.getLenovoOrderId(req.getProductId(), req.getProductTitle(), req.getPrice(), channelId,req.getId(), evt.header.getDeviceId());
				//.getOrderId(req.getProductId(), req.getPrice(),channelId, id, login.getDeviceId());
		
		//HuaweiConf conf=service.getHuaweiConfiguration();
		PP0010Response resp=G.o(PP0010Response.class);
		
		resp.setState(order!=null?1:0);
		//resp.setConf(conf);
		resp.setOrder(order.getOrder());
		resp.setTransId(order.getLenovoOrderId());
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	

}
