/**
 * 
 */
package com.toyo.remote.service.system.domain;

/**
 * @author sparrow
 *
 */
public class ProtocolHandleConfig {
	
	boolean friends;
	boolean mail;
	boolean rank;
	boolean userData;
	
	public boolean isFriends() {
		return friends;
	}
	public void setFriends(boolean friends) {
		this.friends = friends;
	}
	public boolean isMail() {
		return mail;
	}
	public void setMail(boolean mail) {
		this.mail = mail;
	}
	public boolean isRank() {
		return rank;
	}
	public void setRank(boolean rank) {
		this.rank = rank;
	}
	public boolean isUserData() {
		return userData;
	}
	public void setUserData(boolean userData) {
		this.userData = userData;
	}
	
	
	public boolean valid(String transcode){
		boolean ret=false;
		if(transcode.startsWith(ProtocolModuleTypes.PFS.toString())){
			ret=friends;
		}else if(transcode.startsWith(ProtocolModuleTypes.PFU.toString())){
			ret=userData;
		}else if(transcode.startsWith(ProtocolModuleTypes.PMS.toString())){
			ret=mail;
		}else if(transcode.startsWith(ProtocolModuleTypes.PRS.toString())){
			ret=rank;
		}else{
		
			
			ret=false;
		}
		
		if(transcode.equals("PRS0002")){
			ret=friends;
		}
		
		return ret;
	}

}
