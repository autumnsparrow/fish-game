/**
 * 
 */
package com.toyo.fish.protocol.service.domain;

import java.util.List;

/**
 * @author sparrow
 *
 */
public class FriendsUserSeq {
	int total;
	List<FriendsUser> friends;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<FriendsUser> getFriends() {
		return friends;
	}
	public void setFriends(List<FriendsUser> friends) {
		this.friends = friends;
	}
	
	

}
