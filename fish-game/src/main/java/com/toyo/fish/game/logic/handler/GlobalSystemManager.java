/**
 * 
 */
package com.toyo.fish.game.logic.handler;


import com.sky.game.context.SpringContext;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.IGameEventObserver;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.game.logic.system.remote.service.SystemManagerRemote;

import com.toyo.fish.game.logic.user.remote.service.UserAccountManagerRemote;
//import com.toyo.fish.game.logic.user.remote.service.UserAccountRemote;

import com.toyo.fish.protocol.beans.user.UserAccountInfo;
import com.toyo.remote.service.payment.IPaymentService;
import com.toyo.remote.service.system.ISystemRemoteService;
import com.toyo.remote.service.user.ILoginService;

/**
 * @author sparrow
 *
 */
public class GlobalSystemManager  {
	
	public static final GlobalSystemManager instance=new GlobalSystemManager();
	
	
	private SystemManagerRemote systemManagerRemote;
	
	
	public void init(){
		systemManagerRemote=new SystemManagerRemote();
	}
	
	
	
	public GlobalSystemManager() {
		super();
		// TODO Auto-generated constructor stub
		
	}




	
	

}
