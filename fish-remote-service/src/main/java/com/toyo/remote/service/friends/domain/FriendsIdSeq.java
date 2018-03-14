/**
 * 
 */
package com.toyo.remote.service.friends.domain;

import java.util.List;

/**
 * @author sparrow
 *
 */
public class FriendsIdSeq {
	
	List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	public static boolean valid(FriendsIdSeq idSeq){
		return idSeq!=null&&idSeq.getIds()!=null&&idSeq.ids.size()>0;
	}

}
