/**
 * 
 */
package com.toyo.fish.game.lifecycle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.MethodInvoker;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.SpringContext;
import com.sky.game.context.configuration.ice.IceServiceConfig;
import com.sky.game.context.configuration.ice.IceServiceConfigRegstry;
import com.sky.game.context.event.WebsocketEvent;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.service.IServerStarupLifeCycle;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.toyo.fish.game.logic.handler.GlobalUserManager;
import com.toyo.fish.game.logic.handler.GlobalSystemManager;
import com.toyo.fish.game.protocol.handler.PEC0000Handler;
import com.toyo.fish.protocol.beans.PEC0000Beans.Extra;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0001Request;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0002Request;
import com.toyo.remote.service.system.IExchangeCodeService;

/**
 * @author sparrow
 *
 */
public class FishSystemServerStartupLifecycle implements IServerStarupLifeCycle {

	private static final Log logger=LogFactory.getLog(FishSystemServerStartupLifecycle.class);
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
		 GlobalSystemManager.instance.init();
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.service.IServerStarupLifeCycle#afterStartup()
	 */
	public void afterStartup() {
		// TODO Auto-generated method stub
		logger.info("############################ after server startup ###########################");

		// geneate some random code.
		// unique exchange code  xxxdddddd or dddddd
		
//		PEC0000Handler handler=SpringContext.getBean("PEC0000Handler");
//		PEC0002Request req=G.o(PEC0002Request.class);
//		req.setExchangeCode("AXC002939");
//		req.setGiftId(2);
//		///WebsocketEvent evt=WebsocketEvent.o(req, "1xx29933");
//		Extra extra=new Extra();
//		extra.setDeviceId("xxx-xxx-xxxx-xxx");
//		try {
//			handler.onRecieve(req, extra);
//		} catch (ProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//			
			
	//	testPEC0001();
		//IceServiceConfig GameCon
		try {
			IceServiceConfigRegstry.localIceServiceConfig().ping();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	String IMEI="xxxxx";
	
	public IExchangeCodeService localExchangeCodeService(){
		return SpringContext.getBean("IExchangeCodeService");
		
	}

	
	//ce=ProtocolUser,
	private void testPEC0001(){
		logger.info("testPEC001 - ");
		
		
		PEC0001Request req=G.o(PEC0001Request.class);
		req.setExchangeCode("AAA022926");
		//
		RouterHeader r=new RouterHeader(IMEI, "0");
		r.setFrom("Websocket");
		r.setId(131L);
		PEC0000Handler handler=SpringContext.getBean("PEC0000Handler");
		try {
			handler.onRecieve(req, r);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//send(req);
	}

}
