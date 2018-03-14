/**
 * 
 */
package com.toyo.fish.protocol.service;

import java.util.List;

import com.sky.game.context.event.LocalServiceException;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.protocol.beans.user.UserAccountInfo;

/**
 * @author sparrow
 *
 */
public interface IUserAccountManager extends IUserAccount {
	
	/**
	 * 
	 * @param imei
	 * @param channel
	 * @return
	 */
	public UserAccount getUserAccount(String imei,Integer channel) throws LocalServiceException ;
	
	/**
	 * 
	 * 
	 * @param imie
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserAccount getUserAccount(String imie,String userName,String password) throws LocalServiceException ;
	
	
	/**
	 * 
	 * @param userName
	 * @return
	 * @throws LocalServiceException
	 */
	
	public UserAccount getUserAccountByUserName(String userName) throws LocalServiceException;
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws LocalServiceException
	 */
	public UserAccount getUserAccountByUserId(Long userId) throws LocalServiceException;
	
	/**
	 * 
	 * @param userId
	 * @param avtar
	 * @param nickName
	 * @param level
	 * @return
	 */
	public int updateUserBasicInformation(Long userId,String avtar,String nickName,Integer level);
	
	
	public void resetAccount(Long userId) throws LocalServiceException;

	
	
	
}
