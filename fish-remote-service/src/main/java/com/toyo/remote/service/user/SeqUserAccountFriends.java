/**
 * 
 */
package com.toyo.remote.service.user;

import java.util.List;

import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.data.wrapper.domain.UserAccountFriends;

/**
 * @author sparrow
 *
 */
public class SeqUserAccountFriends {
	
	List<UserAccountFriends> accounts;

	public List<UserAccountFriends> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<UserAccountFriends> accounts) {
		this.accounts = accounts;
	}
	
	

}
