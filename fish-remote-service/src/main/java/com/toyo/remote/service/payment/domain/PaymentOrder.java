/**
 * 
 */
package com.toyo.remote.service.payment.domain;

/**
 * @author sparrow
 *
 */
public class PaymentOrder {
	
	String orderId;
	int state;
	int amount;
	int price;
	String productId;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
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
	
	
	@Override
	public String toString() {
		return "PaymentOrder [orderId=" + orderId + ", state=" + state
				+ ", amount=" + amount + ", price=" + price + ", productId="
				+ productId + "]";
	}
	
	
}
