package com.toyo.fish.protocol.beans.user;

import java.util.List;
import java.util.Map;

import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.route.RouterHeader;
import com.toyo.fish.data.wrapper.domain.Login;
import com.toyo.fish.data.wrapper.domain.ThirdPartyAccount;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.data.wrapper.domain.Zone;

public class UserAccountInfo implements IIdentifiedObject{
	
	
	Long id;
	UserAccount account;
//	Zone zone;
//	Login login;
//	ThirdPartyAccount thirdPartyAccount;
	
	//RouterHeader header;

	
//	public RouterHeader getHeader() {
//		return header;
//	}
//	public void setHeader(RouterHeader header) {
//		this.header = header;
//	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserAccount getAccount() {
		return account;
	}
	public void setAccount(UserAccount account) {
		this.account = account;
	}
//	public Zone getZone() {
//		return zone;
//	}
//	public void setZone(Zone zone) {
//		this.zone = zone;
//	}
//	public Login getLogin() {
//		return login;
//	}
//	public void setLogin(Login login) {
//		this.login = login;
//	}
//	public ThirdPartyAccount getThirdPartyAccount() {
//		return thirdPartyAccount;
//	}
//	public void setThirdPartyAccount(ThirdPartyAccount thirdPartyAccount) {
//		this.thirdPartyAccount = thirdPartyAccount;
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	


}
