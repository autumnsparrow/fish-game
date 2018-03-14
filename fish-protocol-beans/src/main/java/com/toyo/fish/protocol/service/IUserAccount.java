package com.toyo.fish.protocol.service;

import com.sky.game.context.event.LocalServiceException;
import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeGift;

public interface IUserAccount {
	
	
	/**
	 * 
	 * @param account
	 * @return
	 */
	public boolean bindThirdpartyAccount(Long userId,String account) throws LocalServiceException ;
	
	/**
	 * 
	 * 
	 * @param userId
	 * @param userName
	 * @param password
	 * @param validCode
	 * @return
	 */
	public boolean bindPhone(Long userId,String userName,String password) throws LocalServiceException ;
	
	/**
	 * 
	 * 
	 * @param userId
	 * @param phone
	 * @return
	 */
	public boolean sendValidCode(Long userId,String phone) throws LocalServiceException ;
	
	/**
	 * 
	 * @param userId
	 * @param password
	 * @param validCode
	 * @return
	 */
	public boolean modifyPassword(Long userId,String password) throws LocalServiceException ;
	
	/**
	 * 
	 * @param validCode
	 * @return
	 * @throws LocalServiceException
	 */
	public boolean validCode(Long userId,String validCode) throws LocalServiceException;
	
	
	/**
	 * 
	 * @param userId
	 * @param code
	 * @return
	 * @throws LocalServiceException
	 */
	public ExchangeCodeGift exchangeCode(Long userId,String code)throws LocalServiceException;

	
	/**
	 * 
	 * @param userId
	 * @param code
	 * @param giftId
	 * @return
	 * @throws LocalServiceException
	 */
	public boolean updateChangeCode(Long userId,String code,Integer giftId) throws LocalServiceException;

	/**
	 * 
	 * @param userId
	 * @param avatar
	 * @return
	 * @throws LocalServiceException
	 */
	public boolean updateAvatar(Long userId,String avatar) throws LocalServiceException;
	
	
	/**
	 * 
	 * @param userId
	 * @param nickName
	 * @return
	 * @throws LocalServiceException
	 */
	public boolean updateNickname(Long userId,String nickName) throws LocalServiceException;

}
