/**
 * 
 */
package com.sky.mobile.protocol;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author sparrow
 *
 */

public class HandlerManager {
	
	private static final Log logger=LogFactory.getLog(HandlerManager.class);
	private static final ConcurrentHashMap<Integer, IHandler> maps=new ConcurrentHashMap<Integer, IHandler>();
	
	
	
	/**
	 * 
	 */
	public HandlerManager() {
		// TODO Auto-generated constructor stub
		// load the Handlers.
		
	}
		
	private static final HandlerManager instance=new HandlerManager();
	public static HandlerManager getInstance(){
		return instance;
	}
	
	
	
	
	

	
	public IHandler getHandler(Integer code){
		IHandler handler =null;
		if(maps.containsKey(code)){
			handler=maps.get(code);
		}
		return handler;
	}
	
	public void registerHandler(IHandler handler){
		if(handler.getCode()!=null&&!maps.containsKey(handler.getCode())){
			maps.put(handler.getCode(), handler);
			logger.info("HandlerManager.registerHandler="+handler.getClass().getSimpleName());
		}
	}
	
	
	public void removeHandler(IHandler handler){
		if(maps.containsKey(handler.getCode())){
			maps.remove(handler.getCode());
			logger.info("HandlerManager.removeHandler="+handler.getClass().getSimpleName());
		}
	}

}
