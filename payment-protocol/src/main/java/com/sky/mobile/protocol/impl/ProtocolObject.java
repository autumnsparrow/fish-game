/**
 * 
 */
package com.sky.mobile.protocol.impl;

/**
 * @author sparrow
 *
 */
public class ProtocolObject {
	
	private long timestamp;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * 
	 */
	public ProtocolObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ProtocolObject [timestamp=" + timestamp + "]";
	}
	
	

}
