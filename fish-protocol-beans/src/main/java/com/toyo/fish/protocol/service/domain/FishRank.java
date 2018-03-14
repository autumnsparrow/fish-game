/**
 * 
 */
package com.toyo.fish.protocol.service.domain;

/**
 * @author sparrow
 *
 */
public class FishRank {
	long id;
	String userName;
	String avatar;
	int level;
	
	float weight;
	long catchTime;
	int rank;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public long getCatchTime() {
		return catchTime;
	}
	public void setCatchTime(long catchTime) {
		this.catchTime = catchTime;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	

}
