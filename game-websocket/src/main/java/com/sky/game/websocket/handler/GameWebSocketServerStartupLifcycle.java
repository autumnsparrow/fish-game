/**
 * 
 */
package com.sky.game.websocket.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.service.IServerStarupLifeCycle;
import com.sky.game.websocket.test.FishGameAppTest;
import com.toyo.fish.game.lifecycle.FishUserServerStartupLifecycle;
import com.toyo.fish.game.logic.handler.GlobalUserManager;

/**
 * @author sparrow
 *
 */
public class GameWebSocketServerStartupLifcycle implements
		IServerStarupLifeCycle {
	
	
	
	
	private static final Log logger=LogFactory.getLog(GameWebSocketServerStartupLifcycle.class);
	/* (non-Javadoc)
	 * @see com.sky.game.context.service.IServerStarupLifeCycle#beforeStartup()
	 */
	public void beforeStartup() {
		// TODO Auto-generated method stub
		logger.info("############################ before server startup ###########################");
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.service.IServerStarupLifeCycle#middleOfStartup()
	 */
	public void middleOfStartup() {
		logger.info("############################ middle server startup ###########################");

		// TODO Auto-generated method stub
		 //GlobalManager.instance.init();
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.service.IServerStarupLifeCycle#afterStartup()
	 */
	public void afterStartup() {
		// TODO Auto-generated method stub
		logger.info("############################ after server startup ###########################\r\n\r\n");

		
		
	//	FishGameAppTest fishGameAppTest=new FishGameAppTest();
		//fishGameAppTest.runTests();
		
		
		
	}

}
