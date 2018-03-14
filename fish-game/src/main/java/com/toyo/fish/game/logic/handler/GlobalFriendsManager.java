/**
 * 
 */
package com.toyo.fish.game.logic.handler;

import com.toyo.fish.game.logic.friends.remote.service.FriendsManagerRemote;

/**
 * @author sparrow
 *
 */
public class GlobalFriendsManager  {
	
	public static final GlobalFriendsManager instance=new GlobalFriendsManager();
	
	
	
	
	
	
	private FriendsManagerRemote managerRemote;
	
	
	public void init(){
		managerRemote=new FriendsManagerRemote();
	}
	
	
	
	public GlobalFriendsManager() {
		super();
		// TODO Auto-generated constructor stub
		
	}




	
	

}
