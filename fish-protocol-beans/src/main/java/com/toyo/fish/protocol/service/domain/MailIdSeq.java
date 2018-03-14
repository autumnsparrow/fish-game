/**
 * 
 */
package com.toyo.fish.protocol.service.domain;

import java.util.List;

/**
 * 
 * 
 * @author sparrow
 *
 */
public class MailIdSeq {
	int total;
	List<Long> ids;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	

}
