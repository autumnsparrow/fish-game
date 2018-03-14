package com.toyo.remote.service.user;

import java.util.List;

import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.spring.ice.SeqWrapper;
import com.toyo.fish.data.wrapper.domain.Login;
import com.toyo.fish.data.wrapper.domain.ProtocolHandler;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.remote.service.system.domain.ProtocolHandleConfig;
import com.toyo.remote.service.system.domain.ProtocolModuleTypes;


/**
 * service of login
 * 
 * @author sparrow
 *
 */
public interface ILoginService {
	
	
	/**
	 * client connected the server
	 * @param connectionId
	 * @param deviceId
	 * @param desKey
	 * @return login
	 * @see Login
	 */
	public Login create(String connectionId,String deviceId,String desKey) throws RemoteServiceException;
	
	/**
	 * client disconnected the server
	 * 
	 * @param login
	 */
	public void delete(Long id)throws RemoteServiceException;
	
	
	/**
	 * 
	 * @param deviceId
	 */
	public void deleteByDeviceId(String deviceId)throws RemoteServiceException;
	
	/**
	 * find the Login by device id
	 * @param deviceId
	 * @return
	 */
	public Login findLoginByDeviceId(String deviceId)throws LocalServiceException;
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Login findLoginByUserId(Long userId) throws LocalServiceException;
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws LocalServiceException
	 */
	public RouterHeader findRouteHeaderByUserId(Long userId) throws LocalServiceException;
	
	/**
	 * 
	 * 
	 * @param login
	 * @return
	 */
	public boolean update(Login login) throws LocalServiceException;
	
	
	public boolean updateUserLevel(Long userId,Integer level) throws LocalServiceException;
	
	public boolean updateApkVersion(Long userId,String version) throws LocalServiceException;

	/**
	 * 
	 * 
	 * @param userIds
	 * @return
	 * @throws LocalServiceException
	 */
	public SeqUserAccount getUserAccount(List<Long> userIds) throws LocalServiceException;

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws LocalServiceException
	 */
	public UserAccount getUserAccountById(Long userId) throws LocalServiceException;
	
	/**
	 * 
	 * @return
	 * @throws LocalServiceException
	 */
	public SeqUserAccountFriends getOnlineUserAccount()throws LocalServiceException;
	
	
	
	/**
	 * 
	 * @param handleName
	 * @return
	 */
	public ProtocolHandler getHandlerConfigByHanldeName(String handleName) ;
	/**
	 * 
	 * @param deviceId
	 * @param channelId
	 * @throws RemoteServiceException
	 */
	public void updateUserBan(String deviceId, Integer channelId) throws RemoteServiceException;
	
	
	public void updateHandlerConfig(ProtocolModuleTypes types, boolean state) throws RemoteServiceException;
	
	
	public ProtocolHandleConfig getHandleConfig() throws RemoteServiceException;
	
	
	
	public  void updateHandleTask();
	
	
	public List<UserAccount> getUserAccountByUserIdRange(Long startUserId,Long endUserId);
}
