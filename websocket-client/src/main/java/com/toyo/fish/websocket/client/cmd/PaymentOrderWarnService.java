package com.toyo.fish.websocket.client.cmd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sky.game.context.executor.UnorderedThreadPoolExecutor;
import com.toyo.fish.websocket.client.FishAppWebsocketClient;
import com.toyo.fish.websocket.client.FishWebsocketWrapper;
import com.toyo.fish.websocket.client.WebsocketClientHandler.IWebsocketMessageHandle;

@Service
public class PaymentOrderWarnService {
	private static final Log logger=LogFactory.getLog(PaymentOrderWarnService.class);
	
	@Autowired
	UnorderedThreadPoolExecutor executor;
	@Autowired
	SmsMessageService smsMessageService;
	
	private FishWebsocketWrapper wrapper;

	public PaymentOrderWarnService() {
	super();
	// TODO Auto-generated constructor stub
		wrapper=new FishWebsocketWrapper("ws://websocket.toyo.cool:8080/game-websocket/api", "5da2961e618e75d64d06df0b251cad78f0beeae7", 10037, 1);
	}
	
	
	
	
	@Scheduled(fixedRate=2*1000*60)
	public void requestPaymentOrderTask(){
		
		executor.execute(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				wrapper.connect(new IWebsocketMessageHandle() {
					
					public void handle(String content) {
						// TODO Auto-generated method stub
						logger.info("After login invoke Payment order");
						wrapper.sendPaymentOrder();
					}
				});
				
				
			//	wrapper.connect();
				
				
			}
		});
		
	}
	
	private static String[]  mobiles={
			"13810715929",
			"13581567526",
			"13911266887"
	};
	
	@Scheduled(fixedRate=2*1000*60)
	public void checkOrderStatusTask(){
		if(wrapper.shouldWarn()){
			for(String mobile:mobiles){
				logger.warn("超过5分种没有订单生成");
				smsMessageService.send(mobile, "超过5分种无订单生成！");
				wrapper.reset();
			}
		//
		}else{
			logger.info( (( System.currentTimeMillis()-wrapper.getLastTimestamp())/1000)+" secs ago ,ack payment order "+(wrapper.getFlag()?"normal":"vivo")   );
		}
	}
	
	

}
