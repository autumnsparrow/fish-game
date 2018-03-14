/**
 * 
 */
package com.sky.mobile.protocol;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * @author sparrow
 *
 */

public class ProtocolResponseEntry  extends ProtocolEntity{

	/**
	 * 
	 */
	public ProtocolResponseEntry() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	
	private Integer status;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "ProtocolResponseEntry [status=" + status + ", toString()="
				+ super.toString() + "]";
	}
	

}
