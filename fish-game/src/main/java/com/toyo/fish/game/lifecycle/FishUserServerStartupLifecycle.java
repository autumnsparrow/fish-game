/**
 * 
 */
package com.toyo.fish.game.lifecycle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.Message;
import com.sky.game.context.MessageException;
import com.sky.game.context.SpringContext;
import com.sky.game.context.configuration.ice.IceServiceConfigRegstry;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.message.IceProxyMessageInvoker;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.service.IServerStarupLifeCycle;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.toyo.fish.data.wrapper.domain.Login;
import com.toyo.fish.game.logic.handler.GlobalUserManager;
import com.toyo.fish.game.protocol.handler.PEC0000Handler;
import com.toyo.fish.game.protocol.handler.PP0000Handler;
import com.toyo.fish.game.protocol.handler.PU0000Handler;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0001Request;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0002Request;
import com.toyo.fish.protocol.beans.PU0000Beans.Extra;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0006Request;
import com.toyo.remote.service.user.ILoginService;

/**
 * @author sparrow
 *
 */
public class FishUserServerStartupLifecycle implements IServerStarupLifeCycle {

	private static final Log logger=LogFactory.getLog(FishUserServerStartupLifecycle.class);
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
		GlobalUserManager.instance.init();
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
	
	
	
	private void pause(){
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testBinding(){
		logger.info("testBinding - RemoteService invoke.");
		ILoginService loginService=SpringContext.getBean("ILoginService");
		try {
			loginService.create("9999", IMEI, DES_KEY);
		} catch (RemoteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testPU0001(){
		logger.info("testPU0001 - ");
		PU0001Request req=G.o(PU0001Request.class);
		req.setChannel(1);
		req.setImei(IMEI);
		
		PU0000Handler handler=SpringContext.getBean("PU0000Handler");
		try {
			RouterHeader r=new RouterHeader(IMEI, "0");
			handler.onRecieve(req, r);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	private static final String IMEI="C12345678";
	private static final String DES_KEY="0987654321";
	private static  String TOKEN="";
	private void send(Object req){
		Message m=G.o(Message.class);
		m.transcode=G.transcode(req, true);
		m.content=GameContextGlobals.getJsonConvertor().format(req);
		
		
		send(IMEI,m);
	}
	
	
	public void send(String deviceId,Message message){
		//
		RouterHeader r=new RouterHeader(deviceId, "0");
		
		PU0000Handler handler=SpringContext.getBean("PU0000Handler");
		try {
			handler.onRecieve(message.content, r);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//			try {
//				IceProxyMessageInvoker.onRecieve(message, r);
//			} catch (MessageException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
	}
	

	private void testPEC0001(){
		logger.info("testPEC001 - ");
		PEC0001Request req=G.o(PEC0001Request.class);
		req.setExchangeCode("AAA003926");
		RouterHeader r=new RouterHeader(IMEI, "0");
		
		PEC0000Handler handler=SpringContext.getBean("PEC0000Handler");
		try {
			handler.onRecieve(req, r);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//send(req);
	}
	

	//2
	private void testPEC0002(){
		logger.info("testPEC002 - ");
		PEC0002Request req=G.o(PEC0002Request.class);
		req.setExchangeCode("AAA003926");
		req.setGiftId(2);
		send(req);
	}
	

}
