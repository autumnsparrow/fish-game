/**
 * 
 */
package com.sky.game.websocket.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.Message;
import com.sky.game.context.MessageException;
import com.sky.game.context.SpringContext;
import com.sky.game.context.domain.MinaBeans.Extra;
import com.sky.game.context.domain.MinaBeans.Request;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.IGameEventObserver;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.message.IceProxyMessageInvoker;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.toyo.fish.data.wrapper.domain.Login;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0001Request;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0002Request;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0001Requet;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0002Requet;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0004Requet;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0005Requet;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0002Requet;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0002Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0003Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0004Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0005Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0006Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0007Request;
import com.toyo.remote.service.payment.IPaymentService;
import com.toyo.remote.service.user.ILoginService;

/**
 * @author sparrow
 *
 */
public class FishGameAppTest implements IGameEventObserver {
	
	private static Log logger=LogFactory.getLog(FishGameAppTest.class);
	
	
	
	public FishGameAppTest() {
		super();
		// TODO Auto-generated constructor stub
		GameEventHandler.handler.registerObserver(GameEvent.EVENT_WEBSOCKET_RECEIVE_DATA, this);
	}
	
	public ILoginService remoteLogService(){
		ILoginService loginService=SpringContext.getBean("ILoginService");
		return loginService;
	}

	public void send(String deviceId,Message message){
		//
		RouterHeader r=new RouterHeader(deviceId, "0");
		
			try {
				IceProxyMessageInvoker.onRecieve(message, r);
			} catch (MessageException e) {
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
		// get the token
		ILoginService loginService=remoteLogService();
		try {
			Login login=loginService.findLoginByDeviceId(IMEI);
			m.token=login.getToken();
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//m.token="";
		
		send(IMEI,m);
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
		Message m=G.o(Message.class);
		m.transcode=G.transcode(req, true);
		m.content=GameContextGlobals.getJsonConvertor().format(req);
		m.token="";
		
		send(IMEI,m);
		
	}
	
	
	
	public void testPU0005(){
		logger.info("testPU0005 - ");
		PU0005Request req=G.o(PU0005Request.class);
		req.setPhone("13810715929");
		
		send(req);
	}
	
	public void testPU0004(){
		logger.info("testPU0004 - ");
		PU0004Request req=G.o(PU0004Request.class);
		req.setUserName("13810715929");
		req.setPassWord("123456");
		
		send(req);
		
	}
	
	public void testPU0002(){
		logger.info("testPU0002 - ");
		PU0002Request req=G.o(PU0002Request.class);
		req.setUserName("13810715929");
		req.setPassWord("123456");
		req.setImei(IMEI);
		
		send(req);
		
	}
	
	public void testPU0003(){
		logger.info("testPU0003 - ");
		PU0003Request req=G.o(PU0003Request.class);
		req.setAccount("xiaome@360.com");
		send(req);
		
	}
	public void testPU0006(){
		logger.info("testPU0006 - ");
		PU0006Request req=G.o(PU0006Request.class);
		req.setPassword("234567");
		send(req);
		
	}
	public void testPU0007(){
		logger.info("testPU0007 - ");
		PU0007Request req=G.o(PU0007Request.class);
		req.setValidcode("324253");
		send(req);
		
	}
	
	
	
	
	public void runTests(){
		testBinding();
		testPU0001();
		
		// passed
		//testPU0005(); 
		// to fast
		//testPU0004();
		//passed
		//testPU0002();
		//testPU0003();
		//testPU0006();
		//testPU0007();
		//testPS0002();
		
		
	}

	@Override
	public String getUri() {
		// TODO Auto-generated method stub
		return GameEvent.EVENT_WEBSOCKET_RECEIVE_DATA;
	}

	@Override
	public void observer(GameEvent evt) {
		// TODO Auto-generated method stub
		if(evt.isEvent(GameEvent.EVENT_WEBSOCKET_RECEIVE_DATA)){
			Request req=(Request) evt.obj;
			String content=req.getContent();
			logger.info("####Response:"+content);
			
			if(content.contains("UP0001")){
				//testPP0001();
				//testPP0004();
			//	testPP0005();
				testPEC0001();
			//	testPEC0002();
				//testPS0002();
			}
			
		}
		
	}
	

	private void testPEC0001(){
		logger.info("testPEC001 - ");
		PEC0001Request req=G.o(PEC0001Request.class);
		req.setExchangeCode("AXC002939");
		send(req);
	}
	

	//2
	private void testPEC0002(){
		logger.info("testPEC002 - ");
		PEC0002Request req=G.o(PEC0002Request.class);
		req.setExchangeCode("AXC002939");
		req.setGiftId(2);
		send(req);
	}
	
	
	private void testPS0002(){
		logger.info("testPS0002 - ");
		PS0002Requet req=G.o(PS0002Requet.class);
		req.setChannelId(1);
		send(req);
		
	}
	

	private void testPP0001(){
		
		logger.info("testPP001 - ");
		PP0001Requet req=G.o(PP0001Requet.class);
		req.setPrice(10);
		req.setProductId("10001");
		send(req);
	}
	
	private void testPP0002(){
		logger.info("testPP002 - ");
		PP0002Requet req=G.o(PP0002Requet.class);
		req.setOrderId("00001201552090412550000000000");
		req.setState(IPaymentService.STATE_EXPIRED); // must be failed
		req.setState(IPaymentService.STATE_NOTIFIED_FAILED); // must be failed
		req.setState(IPaymentService.STATE_NOTIFIED_SUCCEED); // must be failed
		req.setState(IPaymentService.STATE_NOTIFIED_SUCCEED_CLIENT_USED); // must be failed
		//req.setState(IPaymentService.STATE_USED_BY_CANCEL); // must be succeed
		//req.setState(IPaymentService.STATE_USED_SDK); // must be succeed.
		
		
		
		send(req);
		
		
	}
	
	private void testPP0005(){
		logger.info("testPP005- ");
		PP0005Requet req=G.o(PP0005Requet.class);
		req.setOrderId("00001201552090412550000000000");
		
		
		
		
		send(req);
		
		
	}
	
	
	private void testPP0004(){
		logger.info("testPP004 - ");
		PP0004Requet req=G.o(PP0004Requet.class);
		//req.setOrderId("00001201552090412550000000000");
		
		
		
		
		send(req);
		
		
	}
}
