/**
 * 
 */
package com.toyo.remote.service.friends;

import java.util.List;

import com.sky.game.context.spring.RemoteServiceException;
import com.toyo.remote.service.friends.domain.FriendsIdSeq;

/**
 * 
 * @namespace Protocol Friends System (PFS)
 * 
 * remote service part.
 * 
 * 
 * @author sparrow
 *
 */
public interface IFriendsRemoteService {
	
	
	// 
	//
	public static final int MAIL_ACTION_FRIEND_REQUEST_ACCEPT=1;
	public static final int MAIL_ACTION_FRIEND_REQUEST_DECLINE=2;
	public static final int MAIL_ACTION_VIR_ACCEPT=2;
	
	
	//
	// API  for FishUser update friends last access time
	//
	
	
	/**
	 * 
	 * update friends last access information. (FishUser invokes)
	 * also update the last access time.
	 * @param userId
	 * @param avatar
	 * @param level
	 * @param nickName
	 * @return
	 * @throws RemoteServiceException
	 */
	public boolean updateFriendsAccessTime(
			Long userId,
			String avatar,
			int level,
			String nickName
			) throws RemoteServiceException;
	
	
	
	/**
	 * notify the mail attachment(action) (FishMail invokes)
	 * 
	 * @param ownerUserId
	 * @param friendUserId
	 * @param mailAction  1:accept friends request,2:decline friends request,3:accept vit.
	 * @return
	 * @throws RemoteServiceException
	 */
	public boolean notifyMailActions(
			Long ownerUserId,
			Long friendUserId,
			int mailAction
			) throws RemoteServiceException;
	
	
	
	/**
	 * get friends user id by user id (FishRank invokes)
	 * @param userId
	 * @return
	 * @throws RemoteServiceException
	 */
	public FriendsIdSeq getFriendsUserIds(Long userId) throws RemoteServiceException;
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws RemoteServiceException
	 */
	public boolean reset(Long userId) throws RemoteServiceException;
	
}
