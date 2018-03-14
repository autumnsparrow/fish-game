/**
 * 
 */
package com.toyo.remote.service.payment.domain;

/**
 * @author sparrow
 *
 */
public class VivoOrder {

	float amount;
	String  signature;
	String order;
	String localOrderId;
	public String getLocalOrderId() {
		return localOrderId;
	}
	public void setLocalOrderId(String localOrderId) {
		this.localOrderId = localOrderId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
