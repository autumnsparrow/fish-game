/**
 * 
 */
package com.toyo.fish.protocol.service.domain;

import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
public class FriendsUser {
	
	Long userId;
	String avatar;
	String nickName;
	Integer level;
	Integer vitState;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getVitState() {
		return vitState;
	}
	public void setVitState(Integer vitState) {
		this.vitState = vitState;
	}
	

}
