package com.toyo.fish.game;

import com.sky.game.context.SpringContext;
import com.sky.game.context.service.ServerStarupService;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.game.logic.handler.GlobalUserManager;
import com.toyo.remote.service.user.ILoginService;


/**
 * Hello world!
 *
 */
public class FishZoneApp 
{
   
	
	
	// 
	
	
	
	
	
	public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
    	GameUtil.initTokenSerail();
    	boolean flag=FishApp.DEBUG;
    	if(flag){
    		System.out.println("##########DEBUG MODE##############");
    		SpringContext.init(new String[]{
    			"classpath:/META-INF/spring/debug//applicationContext-fish-protocol-service-zone.xml",
    			"classpath:/META-INF/spring/debug/applicationContext-fish-zone.xml"
    			
    		});
    	}else{
    		System.out.println("##########RELEASE MODE##############");
    		SpringContext.init(new String[]{
        			"classpath:/META-INF/spring/release/applicationContext-fish-protocol-service-zone.xml",
        			"classpath:/META-INF/spring/release/applicationContext-fish-zone.xml"
        			
        		});
    	}
    	
    	
    	
    }
}
