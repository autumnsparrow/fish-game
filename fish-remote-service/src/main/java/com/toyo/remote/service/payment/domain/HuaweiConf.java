/**
 * 
 */
package com.toyo.remote.service.payment.domain;

/**
 * @author sparrow
 *
 */
public class HuaweiConf {
	
	String appId;
	String buoSecret;
	String payRsaPrivate;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getBuoSecret() {
		return buoSecret;
	}
	public void setBuoSecret(String buoSecret) {
		this.buoSecret = buoSecret;
	}
	public String getPayRsaPrivate() {
		return payRsaPrivate;
	}
	public void setPayRsaPrivate(String payRsaPrivate) {
		this.payRsaPrivate = payRsaPrivate;
	}
	

}
