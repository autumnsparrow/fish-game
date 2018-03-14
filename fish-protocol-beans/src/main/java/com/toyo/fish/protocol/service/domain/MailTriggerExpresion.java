/**
 * 
 */
package com.toyo.fish.protocol.service.domain;

import java.util.List;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.util.G;

/**
 * @author sparrow
 *
 */
public class MailTriggerExpresion {
	
	 MailLevel userLevel;
	 boolean apkVersion;
	 int loginAfterNDays;
	 
	 List<Long> userIds;
	 
	
	 
	
	public List<Long> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}
	
	public MailLevel getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(MailLevel userLevel) {
		this.userLevel = userLevel;
	}
	public boolean isApkVersion() {
		return apkVersion;
	}
	public void setApkVersion(boolean apkVersion) {
		this.apkVersion = apkVersion;
	}
	public int getLoginAfterNDays() {
		return loginAfterNDays;
	}
	public void setLoginAfterNDays(int loginAfterNDays) {
		this.loginAfterNDays = loginAfterNDays;
	}
	
	
	 
	
	@Override
	public String toString() {
		return GameContextGlobals.getJsonConvertor().format(this);
	}
	public static MailTriggerExpresion getInstance(String expresion){
	//	MailTriggerExpresion o=G.o(MailTriggerExpresion.class);
		MailTriggerExpresion o=GameContextGlobals.getJsonConvertor().convert(expresion, MailTriggerExpresion.class);
		return o;
		
	}
	
	public static void main(String args[]){
		MailTriggerExpresion e=G.o(MailTriggerExpresion.class);
		MailLevel level=G.o(MailLevel.class);
		level.min=0;
		level.relation=MailLevel.GE;
		e.setUserLevel(level);
		String exp=e.toString();
		System.out.println(exp);
		
	}

}
