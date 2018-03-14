/**
 * 
 */
package com.toyo.fish.game.logic.handler;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.SpringContext;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.IGameEventObserver;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.game.logic.user.remote.service.PaymentManageRemote;
import com.toyo.fish.game.logic.user.remote.service.UserAccountManagerRemote;
//import com.toyo.fish.game.logic.user.remote.service.UserAccountRemote;
import com.toyo.fish.protocol.beans.user.UserAccountInfo;
import com.toyo.fish.protocol.service.IFriendsService;
import com.toyo.fish.protocol.service.IUserAccountManager;
import com.toyo.remote.service.friends.IFriendsRemoteService;
import com.toyo.remote.service.mail.IMailRemoteService;
import com.toyo.remote.service.payment.IPaymentService;
import com.toyo.remote.service.system.IExchangeCodeService;
import com.toyo.remote.service.user.ILoginService;

/**
 * @author sparrow
 *
 */
public class GlobalUserManager implements IGameEventObserver {
	
	private static final Log logger=LogFactory.getLog(GlobalUserManager.class);
	
	public static final GlobalUserManager instance=new GlobalUserManager();
	
	
	
	
	
	
	private UserAccountManagerRemote userAccountManagerRemote;
	
	private PaymentManageRemote paymentManageRemote;
	public void init(){
		userAccountManagerRemote=new UserAccountManagerRemote();
		paymentManageRemote=new PaymentManageRemote();
	}
	
	
	
	
	public GlobalUserManager() {
		super();
		// TODO Auto-generated constructor stub
		GameEventHandler.handler.registerObserver(GameEvent.EVENT_USER_LOADED, this);
		GameEventHandler.handler.registerObserver(GameEvent.EVENT_USER_UNLOADED, this);
	}




	//
	//
	// Local or Remote 
	//
	public static  ILoginService localLoginService(){
		ILoginService service=SpringContext.getBean("ILoginService");
		return service;
		
	}
	
	public static IExchangeCodeService remoteExchangeCodeService(){
		IExchangeCodeService service = SpringContext.getBean("IExchangeCodeService");
		return service;
	}
	
	public static IPaymentService remotePaymentService(){
		IPaymentService service=SpringContext.getBean("IPaymentService");
		return service;
	}
	
	
	static IFriendsRemoteService remoteFriendsService(){
		IFriendsRemoteService service=SpringContext.getBean("IFriendsRemoteService");
		return service;
	}

	
	
	public static IUserAccountManager localUserAccountManager() {
		IUserAccountManager userAccountManager=SpringContext.getBean("IUserAccountManager");
		return userAccountManager;
	}
	
	public static IMailRemoteService remoteMailService(){
		IMailRemoteService service=SpringContext.getBean("IMailRemoteService");
		return service;
	}




	public String getUri() {
		// TODO Auto-generated method stub
		return GlobalUserManager.class.getName();
	}


	// message need to 
 
	public void observer(GameEvent evt) {
		// TODO Auto-generated method stub
		if(evt.isEvent(GameEvent.EVENT_USER_LOADED)){
			
			UserAccount id=GameUtil.evtAsObj(evt);
			//userLoaded(id);
			//logger.info("load.user.id="+id.getId());
			// notify the useraccount info?
			String apkVersion=id.getApkVersion();
			
			if(apkVersion==null)
				apkVersion="0";
			
			if(apkVersion!=null){
			int version=Integer.parseInt(apkVersion);
			
			try {
				remoteMailService().notifyUserLogined(id.getId(), id.getLevel(), version);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("user load:"+e.getMessage());
			}
			}
			
		//	UserAccount account=id.getAccount();
			try {
				remoteFriendsService().updateFriendsAccessTime(id.getId(), id.getAvatar()==null?"001":id.getAvatar(), id.getLevel()==null?1:id.getLevel(), id.getNickName()==null?"":id.getNickName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.error("user load:"+e.getMessage());
			}
			
			
		}else if(evt.isEvent(GameEvent.EVENT_USER_UNLOADED)){
			
		
		}
		
	}
	
	public void userLoaded(UserAccountInfo info){
		// load the UserAccountRemote
		
		
		//new PaymentRemote(info.getId());
		
	}
	
	public void userUnloaded(UserAccountInfo info){
		//RemoteObjectCache.unload(info.getId());
		
		
	}
	
	

}
