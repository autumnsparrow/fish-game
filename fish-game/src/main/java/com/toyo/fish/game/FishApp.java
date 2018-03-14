/**
 * 
 */
package com.toyo.fish.game;

import java.util.Map;
import java.util.Properties;

import com.sky.game.context.SpringContext;
import com.sky.game.context.util.GameUtil;


/**
 * @author sparrow
 *
 */
public class FishApp {
	
	public static final boolean DEBUG=false;
	
	private static void startSpring(String path){
		GameUtil.initTokenSerail();
    	
		
		
		String protocol=String.format("classpath:/META-INF/spring/%s/applicationContext-*.xml", path);
		//String app=String.format("classpath:/META-INF/spring/%s/applicationContext-fish-app.xml", path);
		
		SpringContext.init(protocol);
    	
	}
	
	private static String getNode(){
		String path=System.getenv("NODE_NAME");
		path=path.replace(".", "/");
		System.out.println("Node="+path);
		return path;
	}
	
	public static void main(String args[]){
		
		
		String node=getNode();
		startSpring(node);
		
	}

}
