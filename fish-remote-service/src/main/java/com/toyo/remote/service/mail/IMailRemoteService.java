/**
 * 
 */
package com.toyo.remote.service.mail;

import java.util.List;

import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.spring.ice.SeqWrapper;
import com.toyo.remote.service.friends.domain.FriendsIdSeq;

/**
 * @author sparrow
 *
 */
public interface IMailRemoteService {
	
	//
	// Expose the api as RemoteService.
	//
	
	/**
	 * create mail 
	
	 * @param title
	 * @param from
	 * @param content
	 * @param category
	 * @param activeDateTime
	 * @param activeLifeHours
	 * @param attachments
	 * @param triggerExrepsion
	 * @return mail id
	 */
	public long createMailWithUserAndTimeTrigger(
			Long userId,
			Long reciever,
			String title,
			
			String content,
			int category,
			String activeDateTime,
			int activeLifeHours,
			String attachments,
			String triggerExrepsion
			)throws RemoteServiceException;
	
	
	
	/**
	 * expose that api to FishUser service.
	 * @param userId
	 * @param userLevel
	 * @param apkUpdated
	 * @param loginAfterNDays
	 * @throws LocalServiceException
	 */
	public void notifyUserLogined(Long userId,int userLevel,int apkVersion) throws RemoteServiceException;
	
	
	
	
	/**
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 * @throws RemoteServiceException
	 */
	public boolean canSendFriendRequest(Long userId,Long friendId) throws RemoteServiceException;
	
	
	/**
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 * @throws RemoteServiceException
	 */
	public boolean deleteFriendsRequestMail(Long userId,SeqWrapper<Long> friendId)throws RemoteServiceException;
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws RemoteServiceException
	 */
	public FriendsIdSeq getFriendRequestSentIds(Long userId) throws RemoteServiceException;

	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws RemoteServiceException
	 */
	public boolean reset(Long userId) throws RemoteServiceException;

}
