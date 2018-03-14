/**
 * 
 */
package com.toyo.fish.protocol.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.MessageInternalBean;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.spring.ice.SeqWrapper;
import com.sky.game.context.util.G;
import com.toyo.fish.data.wrapper.domain.ApkVersionUpdateLog;
import com.toyo.fish.data.wrapper.domain.Login;
import com.toyo.fish.data.wrapper.domain.ProtocolHandler;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.data.wrapper.domain.UserAccountFriends;
import com.toyo.fish.data.wrapper.domain.UserBan;
import com.toyo.fish.data.wrapper.persistence.ApkVersionUpdateLogMapper;
import com.toyo.fish.data.wrapper.persistence.LoginMapper;
import com.toyo.fish.data.wrapper.persistence.ProtocolHandlerMapper;
import com.toyo.fish.data.wrapper.persistence.UserAccountMapper;
import com.toyo.fish.data.wrapper.persistence.UserBanMapper;
import com.toyo.remote.service.mail.IMailRemoteService;
import com.toyo.remote.service.system.domain.ProtocolHandleConfig;
import com.toyo.remote.service.system.domain.ProtocolModuleTypes;
import com.toyo.remote.service.user.ILoginService;
import com.toyo.remote.service.user.SeqUserAccount;
import com.toyo.remote.service.user.SeqUserAccountFriends;

/**
 * internal service.
 * 
 * Using this service as remote service.
 * 
 * @author sparrow
 *
 */
@Service("ILoginService")
public class DefaultLoginService implements ILoginService {
	private static final Log logger=LogFactory.getLog(DefaultLoginService.class);
	
	@Autowired
	LoginMapper loginMapper;
	@Autowired
	UserAccountMapper userAccountMapper;
	
	
	@Autowired
	ApkVersionUpdateLogMapper apkVersionUpdateLogMapper;
	
	
	@Autowired
	ProtocolHandlerMapper protocolHandlerMapper;
	
	@Autowired
	UserBanMapper userBanMapper;
	
	static AtomicInteger invoked=new AtomicInteger(9);
	static AtomicLong duration=new AtomicLong(0);

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.ILoginService#create(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	public Login create(String connectionId, String deviceId, String desKey) throws RemoteServiceException {
		//long b=System.nanoTime();
		//MessageInternalBean response= prx.invoke(request);
		
	
		Login o=G.o(Login.class);
		
		try {
			logger.info("login: device_id:"+deviceId+",connection:"+connectionId);
			// clear the data before.
			loginMapper.deleteByDeviceId(deviceId);
			// clear the other connection in the server.
			
			
			// TODO Auto-generated method stub
			
			o.setConnectionId(connectionId);
			o.setDeviceId(deviceId);
			o.setDesKey(desKey);
			o.setCreateTime(new Date());
			int affectedRows=loginMapper.insertSelective(o);
			if(affectedRows>0){
				// insert succeed.
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return o;
	}

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.ILoginService#delete(java.lang.Long)
	 */
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
		try {
			loginMapper.deleteByPrimaryKey(id);
			
			GameEventHandler.handler.broadcast(new GameEvent(GameEvent.EVENT_USER_UNLOADED,id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.ILoginService#findLoginByDeviceId(java.lang.String)
	 */
	public Login findLoginByDeviceId(String deviceId) {
		// TODO Auto-generated method stub
		Login login=null;
		try {
			login= loginMapper.findLoginByDeviceId(deviceId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return login;
	}

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.ILoginService#findLoginByUserId(java.lang.Long)
	 */
	public Login findLoginByUserId(Long userId) {
		// TODO Auto-generated method stub
		Login login=null;
		try {
			login= loginMapper.findLoginByUserId(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return login;
		
	}

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.ILoginService#update(com.toyo.fish.data.wrapper.domain.Login)
	 */
	public boolean update(Login login) {
		// TODO Auto-generated method stub
		boolean ret=false;
		try {
			int affectedRows= loginMapper.updateByPrimaryKeySelective(login);
			ret = false;
			if(affectedRows>0){
				ret=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public void deleteByDeviceId(String deviceId) {
		// TODO Auto-generated method stub
		try {
			loginMapper.deleteByDeviceId(deviceId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Transactional
	public boolean updateApkVersion(Long userId, String version)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		int affectedRows=0;
		try {
			UserAccount u=userAccountMapper.selectByPrimaryKey(userId);
			int size=apkVersionUpdateLogMapper.selectByUserIdAndApkVersion(userId, u.getApkVersion());
			if(size==0){
				ApkVersionUpdateLog o=G.o(ApkVersionUpdateLog.class);
				o.setApkVersion(u.getApkVersion());
				o.setCreateTime(new Date());
				o.setUserId(userId);
				apkVersionUpdateLogMapper.insertSelective(o);
			}
			userAccountMapper.updateApkVersion(version, userId);
			 //should save the old version.
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return affectedRows>0;
	}

	
	public SeqUserAccount getUserAccount(List<Long> userIds)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		SeqUserAccount o = G.o(SeqUserAccount.class);
		try {
			
			List<UserAccount> accouts=userAccountMapper.findUserAccountByUserIds(userIds);
			
			o.setAccounts(accouts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	
	

	
	public RouterHeader findRouteHeaderByUserId(Long userId)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		
		//login.getDeviceId()
		RouterHeader r = G.o(RouterHeader.class);
		try {
			Login login=findLoginByUserId(userId);
			
			if(login!=null){
			r.setDeviceId(login.getDeviceId());
			if(login.getConnectionId()!=null&&login.getConnectionId().indexOf('@')>0){
				String[] items=login.getConnectionId().split("@");
				if(items!=null&items.length==2){}
				r.setTo(items[1]);
				r.setConnectionId(items[0]);
			
			}
			}else{
				logger.warn("userId:"+userId+" don't online");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}

	
	public UserAccount getUserAccountById(Long userId)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		UserAccount userAccount=null;
		try {
			userAccount=userAccountMapper.selectByPrimaryKey(userId);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userAccount;
	}

	
	public boolean updateUserLevel(Long userId, Integer level)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		int affectedRows=0;
		try {
			affectedRows=userAccountMapper.updateUserLevel(userId, level);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return affectedRows>0?true:false;
	}

	
	public SeqUserAccountFriends getOnlineUserAccount() throws LocalServiceException {
		// TODO Auto-generated method stub
		
		SeqUserAccountFriends o = G.o(SeqUserAccountFriends.class);
		try {
			List<UserAccountFriends> accouts=userAccountMapper.findUserAccountOnlines();
			
			o.setAccounts(accouts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	
	
	
	public ProtocolHandler getHandlerConfigByHanldeName(String handleName) {
		// TODO Auto-generated method stub
		ProtocolHandler config=null;
		try {
			config=protocolHandlerMapper.selectByHandleName(handleName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return config;
	}


	
	public void updateHandlerConfig(ProtocolModuleTypes types, boolean state) throws RemoteServiceException {
		// TODO Auto-generated method stub
		ProtocolHandler o=getHandlerConfigByHanldeName(types.toString());
		if(o==null){
			o=G.o(ProtocolHandler.class);
			o.setCreateTime(new Date());
			o.setHandle(types.toString());
			o.setState(state?1:0);
			o.setUpdateTime(new Date());
			protocolHandlerMapper.insertSelective(o);
		}else{
			ProtocolHandler 	oo=G.o(ProtocolHandler.class);
			oo.setId(o.getId());
			oo.setState(state?1:0);
			oo.setUpdateTime(new Date());
			protocolHandlerMapper.updateByPrimaryKeySelective(oo);
			
		}
		updateHandleTask();
		
	}
	
	
	public void updateUserBan(String deviceId, Integer channelId) throws RemoteServiceException {
		// TODO Auto-generated method stub
		
		UserBan o=userBanMapper.selectByDeviceIdAndChannelId(deviceId, channelId);
		if(o==null){
			o=G.o(UserBan.class);
			o.setChannelId(channelId);
			o.setCreateTime(new Date());
			o.setUpdateTime(new Date());
			o.setDeviceId(deviceId);
			
			userBanMapper.insertSelective(o);
		}else{
			UserBan oo=G.o(UserBan.class);
			oo.setId(o.getId());
			oo.setUpdateTime(new Date());
			userBanMapper.updateByPrimaryKeySelective(oo);
		}
		
		
	}
	
	
	ProtocolHandleConfig config=new ProtocolHandleConfig();
	@Scheduled(fixedRate=60000)
	public synchronized void updateHandleTask(){
		
		config.setFriends(getConfig(ProtocolModuleTypes.PFS));
		config.setMail(getConfig(ProtocolModuleTypes.PMS));
		config.setRank(getConfig(ProtocolModuleTypes.PRS));
		config.setUserData(getConfig(ProtocolModuleTypes.PFU));
	}
	
	private boolean getConfig(ProtocolModuleTypes moduleName){
		boolean ret=false;
		ProtocolHandler handle=getHandlerConfigByHanldeName(moduleName.toString());
		if(handle!=null){
			ret=handle.getState().intValue()==1?true:false;
		}
		return ret;
	}

	public ProtocolHandleConfig getHandleConfig() throws RemoteServiceException {
		// TODO Auto-generated method stub
		return config;
	}

	public List<UserAccount> getUserAccountByUserIdRange(Long startUserId, Long endUserId) {
		// TODO Auto-generated method stub
		List<UserAccount>  accounts=userAccountMapper.selectByUserIdRange(startUserId, endUserId);
		return accounts;
	}

	

}
