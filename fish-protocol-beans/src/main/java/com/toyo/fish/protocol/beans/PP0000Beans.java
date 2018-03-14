package com.toyo.fish.protocol.beans;

import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.route.RouterHeader;
import com.toyo.fish.protocol.beans.store.StoreItem;
import com.toyo.remote.service.payment.IPaymentService;
import com.toyo.remote.service.payment.domain.HuaweiConf;
import com.toyo.remote.service.payment.domain.PaymentOrder;
import com.toyo.remote.service.payment.domain.VivoOrder;

/**
 * 
 * Protocol System.
 * 
 * 
 * 
 * @author sparrow
 *
 */
public class PP0000Beans {

	/**
	 * Extra Object of User Protocol
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerNamespaceExtraType(namespace = "ProtocolPayment")
	public static class Extra extends RouterHeader {

	}

	public static class PPBaseObj implements IIdentifiedObject {

		public Long getId() {
			// TODO Auto-generated method stub
			return id;
		}

		public void setId(Long id) {
			// TODO Auto-generated method stub
			this.id = id;

		}

		Long id;
	}

	/**
	 * 
	 * 
	 * 
	 */
	@HandlerRequestType(transcode = "PP0001")
	public static class PP0001Requet extends PPBaseObj {
		String productId;
		int price;

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

	}

	/**
	 * 
	 * Protocol System Store.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "PP0001", responsecode = "PP0001")
	public static class PP0001Response {
		String orderId;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

	}

	@HandlerRequestType(transcode = "PP0002")
	public static class PP0002Requet extends PPBaseObj {
		String orderId;
		int state;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

	}

	/**
	 * 
	 * Protocol System Store.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "PP0002", responsecode = "PP0002")
	public static class PP0002Response {
		

		int state;
		PaymentOrder order;

		public PaymentOrder getOrder() {
			return order;
		}

		public void setOrder(PaymentOrder order) {
			this.order = order;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
		
	}

	@HandlerResponseType(transcode = "PP0003", responsecode = "PP0003")
	public static class PP0003Response {

		int state;
		PaymentOrder order;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public PaymentOrder getOrder() {
			return order;
		}

		public void setOrder(PaymentOrder order) {
			this.order = order;
		}

	}

	@HandlerRequestType(transcode = "PP0004")
	public static class PP0004Requet extends PPBaseObj {

	}

	/**
	 * notify_failed or notify_succeed state Protocol System Store.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "PP0004", responsecode = "PP0004")
	public static class PP0004Response {
		List<PaymentOrder> data;

		public List<PaymentOrder> getData() {
			return data;
		}

		public void setData(List<PaymentOrder> data) {
			this.data = data;
		}

	}

	@HandlerRequestType(transcode = "PP0005")
	public static class PP0005Requet extends PPBaseObj {

		String orderId;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

	}

	/**
	 * notify_failed or notify_succeed state Protocol System Store.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "PP0005", responsecode = "PP0005")
	public static class PP0005Response {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

	}

	/**
	 * 
	 * For Vivo get order
	 * 
	 * 
	 * 
	 */

	/**
	 * 
	 * {@link IPaymentService#getVivoOrder(String, String, String, int, int, long, String)}
	 * 
	 * @author sparrow
	 * @param productId
	 * @param productTitle
	 * @param productDescription
	 * @param price
	 *
	 */
	@HandlerRequestType(transcode = "PP0006")
	public static class PP0006Request extends PPBaseObj {
		String productId;
		String productTitle;
		String productDescription;
		int price;

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getProductTitle() {
			return productTitle;
		}

		public void setProductTitle(String productTitle) {
			this.productTitle = productTitle;
		}

		public String getProductDescription() {
			return productDescription;
		}

		public void setProductDescription(String productDescription) {
			this.productDescription = productDescription;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

	}

	/**
	 * @param state
	 *            1: true 0:false
	 * @param vivoOrder
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "PP0006", responsecode = "PP0006")
	public static class PP0006Response {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		VivoOrder order;

		public VivoOrder getOrder() {
			return order;
		}

		public void setOrder(VivoOrder order) {
			this.order = order;
		}

	}

	/**
	 * 
	 * {@link IPaymentService#getVivoOrder(String, String, String, int, int, long, String)}
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "PP0007")
	public static class PP0007Request extends PPBaseObj {
		Long uid;
		String sessionId;

		public Long getUid() {
			return uid;
		}

		public void setUid(Long uid) {
			this.uid = uid;
		}

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

	}

	@HandlerResponseType(transcode = "PP0007", responsecode = "PP0007")
	public static class PP0007Response {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

	}

	/**
	 * 
	 * 
	 * @author sparrow
	 *
	 */

	@HandlerRequestType(transcode = "PP0008")
	public static class PP0008Request extends PPBaseObj {

	}

	@HandlerResponseType(transcode = "PP0008", responsecode = "PP0008")
	public static class PP0008Response {

		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		HuaweiConf conf;

		public HuaweiConf getConf() {
			return conf;
		}

		public void setConf(HuaweiConf conf) {
			this.conf = conf;
		}

	}

	/**
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "PP0009")
	public static class PP0009Request extends PPBaseObj {
		String uid;
		String token;
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
		

	}

	@HandlerResponseType(transcode = "PP0009", responsecode = "PP0009")
	public static class PP0009Response {

		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

	}
	
	
	
	/**
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "PP0010")
	public static class PP0010Request extends PPBaseObj {
		String productId;
		String productTitle;
		int price;
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getProductTitle() {
			return productTitle;
		}
		public void setProductTitle(String productTitle) {
			this.productTitle = productTitle;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		
	}

	@HandlerResponseType(transcode = "PP0010", responsecode = "PP0010")
	public static class PP0010Response {

		int state;
		String transId;
		public String getTransId() {
			return transId;
		}

		public void setTransId(String transId) {
			this.transId = transId;
		}

		PaymentOrder order;
		


		public PaymentOrder getOrder() {
			return order;
		}

		public void setOrder(PaymentOrder order) {
			this.order = order;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

	}

}
