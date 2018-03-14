/**
 * 
 */
package com.toyo.fish.websocket.client.test;

import org.springframework.shell.core.CommandResult;

/**
 * @author sparrow
 *
 */
public class SendMailShell extends AbstractShellIntegerTest{
	
	private void log(String msg){
		System.out.println(msg);
	} 

	@Override
	public void doTask() {
		// TODO Auto-generated method stub
		
		if(executeCommand("server --uri Bench")){
			if(executeCommand("sys connect")){
				if(executeCommand("list --userId 19920627 --start 1000 --end 1200")){
					if(executeCommand("make friends --start 1000 --end 1200")){
						
						
					}
					
				}
			}
		}
		
		
	}
	
	
	private boolean executeCommand(String cmd){
		CommandResult cr = getShell().executeCommand(cmd);
		log(cr.toString());
		return cr.isSuccess();
	}
	public static void main(String args[]){
		SendMailShell shell=new SendMailShell();
		try {
			SendMailShell.startUp();
			shell.doTask();
			SendMailShell.shutdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
