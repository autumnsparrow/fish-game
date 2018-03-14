/**
 * 
 */
package com.toyo.fish.game.lifecycle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.SpringContext;
import com.sky.game.context.configuration.ice.IceServiceConfigRegstry;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.service.IServerStarupLifeCycle;
import com.sky.game.context.util.G;
import com.toyo.fish.game.logic.handler.GlobalUserManager;
import com.toyo.fish.game.logic.handler.GlobalSystemManager;
import com.toyo.fish.game.logic.handler.GlobalZoneManager;
import com.toyo.fish.game.protocol.handler.PRS0000Handler;
import com.toyo.fish.game.protocol.handler.PU0000Handler;
import com.toyo.fish.protocol.beans.PRS0000Beans.PRS0001Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Request;

/**
 * @author sparrow
 *
 */
public class FishZoneServerStartupLifecycle implements IServerStarupLifeCycle {

	private static final Log logger=LogFactory.getLog(FishZoneServerStartupLifecycle.class);
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
		 GlobalZoneManager.instance.init();
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
