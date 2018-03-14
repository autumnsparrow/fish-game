package com.toyo.remote.service.system;


import java.util.Date;
import java.util.List;

import com.sky.game.context.spring.IRemoteService;
import com.sky.game.context.spring.RemoteServiceException;
import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeGift;




public interface IExchangeCodeService extends IRemoteService {
	
	public static final int EC_NOT_START = 2; // 兑换码没开始
	public static final int EC_EXPIRED = 3;  // 兑换码过期
	public static final int EC_USER_USEED = 4; // 用户已使用该类型（根据giftId一致）的兑换码
	public static final int EC_NOT_EXSIT = 5;  // 兑换码不存在
	public static final int EC_USE_OK = 1;   // 使用成功
	public static final int EC_DISAVAILABLE = 6;// 兑换码不可用，特殊状态，不使用
	
	public static final int EC_EXCHANGE_FAILED=-1;

	public static final int EC_DATA_FLAG_AVAILABLE = 0; // 兑换码未被使用
	public static final int EC_DATA_FLAG_DISAVAILABLE = 1; // 兑换码已被使用
	
	/**
	 * 用户使用兑换码
	 * @param userId
	 * @param exchangeCode
	 * @return
	 * @throws ServiceException
	 */
	public int use(Long userId,String exchangeCode) throws RemoteServiceException;
	/**
	 * 显示兑换码对应的礼品包
	 * @param exchangeCode
	 * @return
	 * @throws ServiceException
	 */
	public ExchangeCodeGift showGift(String exchangeCode) throws RemoteServiceException;
	
	
	/**
	 * 
	 * @param userId
	 * @param code
	 * @return
	 * @throws RemoteServiceException
	 */
	public ExchangeCodeGift exchangeCode(Long userId, String code) throws RemoteServiceException;
	
	
	public boolean updateExchangeCode(Long userId,String code, Integer giftId)
			throws RemoteServiceException;
}
