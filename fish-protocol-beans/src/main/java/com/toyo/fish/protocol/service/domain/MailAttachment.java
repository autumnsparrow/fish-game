/**
 * 
 */
package com.toyo.fish.protocol.service.domain;

/**
 * @author sparrow
 *
 */
public class MailAttachment {
	
	public static final int ITEM_TYPE=1;
	public static final int ACTION_TYPE=2;
	String id;
	int type;
	String value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	

}
