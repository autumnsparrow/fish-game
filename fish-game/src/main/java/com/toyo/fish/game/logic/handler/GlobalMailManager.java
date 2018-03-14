/**
 * 
 */
package com.toyo.fish.game.logic.handler;

import com.toyo.fish.game.logic.mail.remote.service.MailManagerRemote;

/**
 * @author sparrow
 *
 */
public class GlobalMailManager  {
	
	public static final GlobalMailManager instance=new GlobalMailManager();
	
	
	
	
	
	
	private MailManagerRemote managerRemote;
	
	
	public void init(){
		managerRemote=new MailManagerRemote();
	}
	
	
	
	public GlobalMailManager() {
		super();
		// TODO Auto-generated constructor stub
		
	}




	
	

}
