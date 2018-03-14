/**
 * 
 */
package com.toyo.fish.protocol.service;

import java.util.Date;
import java.util.List;

import com.sky.game.context.event.LocalServiceException;
import com.toyo.fish.protocol.service.domain.FriendsRestriction;
import com.toyo.fish.protocol.service.domain.FriendsUser;
import com.toyo.fish.protocol.service.domain.FriendsUserSeq;
import com.toyo.remote.service.friends.IFriendsRemoteService;

/**
 * 
 * Fish Friends Interface .
 * 
 *
 * 1. create default friends object.
 * 2. search user information by userId(FishUser)
 * 3. send vit or friends request mail .(FishMail)
 * 4. provide remote service for mail .ack friends request(accept/decline) or accept vit
 * 5. get friends list 
 * 6. get friends related restriction.
 * 7. schedule task to reset restrictions.
 * 8. get recomment friends list .
 * 9. create friends relation both side.
 * 10. delete friends 
 * 11. get friends user id (FishRank)   (Friends Fish Rank, Big fish send mail) {RemoteService}
 * 
 * 
 * 
 * @author sparrow
 *
 */
public interface IFriendsService  extends IFriendsRemoteService{
	
	
	
	// Client API (PFSxxxx protocol)
	//
	//
	//
	
	/**
	 * search FriendsUser by userId.(PFS0001)
	 * @param userId user id that search for
	 * @return
	 * @throws LocalServiceException
	 */
	public FriendsUser findByUserId(Long userId) throws LocalServiceException;
	
	
	/**
	 * send friends request or send vit mail to friends.
	 * 
	 * @param userId owner user id
	 * @param friendUserId  the user that receive mail
	 * @param actionType  1= friends request mail ,2= vit mail
	 * @return
	 * @throws LocalServiceException
	 */
	public boolean sendFriendsMail(Long userId,Long friendUserId,int actionType) throws LocalServiceException;
	
	
	/**
	 * get friends list by range parameters.
	 * 
	 * @param userId owner user id
	 * @param offset offset that query start
	 * @param length limit of the query
	 * @return
	 * @throws LocalServiceException
	 */
	public FriendsUserSeq getFriendsSeq(Long userId,int offset,int length) throws LocalServiceException ;
	
	
	
	/**
	 * get recommend friends user.
	 * @return
	 * @throws LocalServiceException
	 */
	
	public List<FriendsUser> getRecommendFriendsUser(Long userId) throws LocalServiceException;

	
	/**
	 * remove friends by friends id.
	 * @param friendsIds
	 * @return
	 * @throws LocalServcerviceException
	 */
	public boolean removeFriends(Long userId,List<Long> friendsIds) throws LocalServiceException;
	
	
	/**
	 * 
	 * get current user friends restriction.
	 * @param userId
	 * @return
	 * @throws LocalServiceException
	 */
	public FriendsRestriction getFriendsRestriction(Long userId) throws LocalServiceException;
	
	//
	// Basic API
	//
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws LocalServiceException
	 */
	public Long createFriendsBase(
			Long userId	
			)throws LocalServiceException;
	
	
	
	
	//
	// schedule task service.
	//
	/**
	 * reset the friends restriction 
	 * 
	 * 
	 * @return
	 * @throws LocalServiceException
	 */
	public int resetRestrictions()throws LocalServiceException;
	
	
	//
	// schedule task
	//
	public void scheduledTaskResetRestriction();
	public void fetchRecommentUserTask();
	

}
