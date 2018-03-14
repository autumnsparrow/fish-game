/**
 * 
 */
package com.toyo.remote.service.payment.domain;

/**
 * @author sparrow
 *
 */
public class LenovoOrder {
	String lenovoOrderId;
	PaymentOrder order;
	public String getLenovoOrderId() {
		return lenovoOrderId;
	}
	public void setLenovoOrderId(String lenovoOrderId) {
		this.lenovoOrderId = lenovoOrderId;
	}
	public PaymentOrder getOrder() {
		return order;
	}
	public void setOrder(PaymentOrder order) {
		this.order = order;
	}
	

}
