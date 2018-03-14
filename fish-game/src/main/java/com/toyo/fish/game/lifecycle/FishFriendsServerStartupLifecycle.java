/**
 * 
 */
package com.toyo.fish.game.lifecycle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.configuration.ice.IceServiceConfigRegstry;
import com.sky.game.context.service.IServerStarupLifeCycle;
import com.toyo.fish.game.logic.handler.GlobalFriendsManager;


/**
 * @author sparrow
 *
 */
public class FishFriendsServerStartupLifecycle implements IServerStarupLifeCycle {

	private static final Log logger=LogFactory.getLog(FishFriendsServerStartupLifecycle.class);
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
		 GlobalFriendsManager.instance.init();
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.service.IServerStarupLifeCycle#afterStartup()
	 */
	public void afterStartup() {
		// TODO Auto-generated method stub
		logger.info("############################ after server startup ###########################");

		try {
			IceServiceConfigRegstry.localIceServiceConfig().ping();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	

}
