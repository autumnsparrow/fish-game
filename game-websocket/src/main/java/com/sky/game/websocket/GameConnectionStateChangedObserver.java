/**
 * 
 */
package com.sky.game.websocket;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.j256.simplejmx.common.JmxAttributeMethod;
import com.j256.simplejmx.common.JmxOperation;
import com.j256.simplejmx.common.JmxResource;
import com.sky.game.context.util.CronUtil;
import com.sky.game.websocket.context.GameConnectionStates;
import com.sky.game.websocket.context.IGameConnectionStateChanged;


/**
 * @author sparrow
 *
 */

@JmxResource(domainName="com.sky.game.websocket",beanName="GameConnectionStateChangedObserver",description=" Mina Server IoSession Context.")
public class GameConnectionStateChangedObserver  implements IGameConnectionStateChanged{
	
	private   final ConcurrentHashMap<String, SessionContext> S_SESSION_MAP=new ConcurrentHashMap<String,SessionContext >();
	private static final Log log=LogFactory.getLog(GameConnectionStateChangedObserver.class);
	
	
	IGameConnectionStateChanged  gameConnectionStateChanged;
	
	public void addSessionContext(SessionContext ctx){
		S_SESSION_MAP.put(ctx.connectionId, ctx);
	}
	
	
	public void removeSession(SessionContext ctx){
		if(S_SESSION_MAP.containsKey(ctx.connectionId))
			S_SESSION_MAP.remove(ctx.connectionId);
	}
	
	
	private SessionContext getSessionContext(String connectionId){
		SessionContext ctx=null;
		if(S_SESSION_MAP.containsKey(connectionId)){
			ctx=S_SESSION_MAP.get(connectionId);
		}
		return ctx;
	}
	
	@JmxOperation(parameterNames={"connectionId"},parameterDescriptions={" get deviceid from the connection id"})
	public String deviceId(String connecitonId){
		SessionContext ctx=getSessionContext(connecitonId);
		String deviceId=null;
		if(ctx!=null){
			deviceId=ctx.deviceId;
		}
		return deviceId;
	}
	
	@JmxOperation(parameterNames={"connectionId"},parameterDescriptions={" get session info from the connection id"})
	public String sessionInfo(String connectionId){
		String msg=null;
		if(S_SESSION_MAP.containsKey(connectionId)){
			msg=S_SESSION_MAP.get(connectionId).toString();
		}
		log.info(msg);
		return msg;
	}
	
//	@JmxOperation(parameterNames={"crontab"},parameterDescriptions={"crontab format."})
//	public String getCronString(String crontab){
//		Date date=CronUtil.getScheduledTime(crontab);
//		String d=CronUtil.getFormatedDate(date);
//		return d;
//	}
	
	@JmxAttributeMethod(description=" get connection ids!")
	public String getConnectionIds(){
		StringBuffer sb=new StringBuffer();
		
		for(String connectionId:S_SESSION_MAP.keySet()){
			sb.append(connectionId).append("\r\n");
		}
		String msg=sb.toString();
		log.info(msg);
		
		return msg;
	}
	
	@JmxOperation(description="dump the connection context")
	//@Scheduled(cron="0 0/1 0 * * ?") 
	public String dump(){
	
		StringBuffer buffer=new StringBuffer();
		for(String connectionId:S_SESSION_MAP.keySet()){
			String msg=sessionInfo(connectionId);
			buffer.append(msg).append("\r\n");
		}
		
		//log.info("==========\r\nsize:"+S_SESSION_MAP.size());
		return buffer.toString();
	}


	public void onGameConnectionStateChanged(String connectionId,
			GameConnectionStates states) {
		// TODO Auto-generated method stub
		if(gameConnectionStateChanged!=null)
			gameConnectionStateChanged.onGameConnectionStateChanged(connectionId, states);
		
		//log.info("["+connectionId+"] - "+states.toString());
	}


	public void onGameConnectionStateBinding(String connectionId, String deviceId) {
		// TODO Auto-generated method stub
		if(gameConnectionStateChanged!=null)
			gameConnectionStateChanged.onGameConnectionStateBinding(connectionId, deviceId);
		//log.info("["+connectionId+"] -B-"+deviceId);
	}


	public void onGameConnectionStateUnBind(String connectionId) {
		// TODO Auto-generated method stub
		if(gameConnectionStateChanged!=null)
			gameConnectionStateChanged.onGameConnectionStateUnBind(connectionId);
		//log.info("GameConnectionStates:["+connectionId+"] -L-");
	}


	public void clearup() {
		// TODO Auto-generated method stub
		
		if(gameConnectionStateChanged!=null)
			gameConnectionStateChanged.clearup();
		//log.info("GameConnectionStates:cleanup");
		
	}

}
