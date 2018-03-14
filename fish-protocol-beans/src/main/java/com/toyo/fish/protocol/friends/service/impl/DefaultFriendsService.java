/**
 * 
 */
package com.toyo.fish.protocol.friends.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.spring.ice.SeqWrapper;
import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.data.wrapper.friends.domain.Friends;
import com.toyo.fish.data.wrapper.friends.domain.FriendsMailTemplate;
import com.toyo.fish.data.wrapper.friends.domain.FriendsRelation;
import com.toyo.fish.data.wrapper.friends.persistence.FriendsMailTemplateMapper;
import com.toyo.fish.data.wrapper.friends.persistence.FriendsMapper;
import com.toyo.fish.data.wrapper.friends.persistence.FriendsRelationMapper;
import com.toyo.fish.protocol.service.IFriendsService;
import com.toyo.fish.protocol.service.IMailService;
import com.toyo.fish.protocol.service.domain.FriendsRestriction;
import com.toyo.fish.protocol.service.domain.FriendsUser;
import com.toyo.fish.protocol.service.domain.FriendsUserSeq;
import com.toyo.fish.protocol.service.domain.MailAttachment;
import com.toyo.fish.protocol.service.domain.MailAttachments;
import com.toyo.fish.protocol.service.domain.MailTriggerExpresion;
import com.toyo.remote.service.friends.domain.FriendsIdSeq;
import com.toyo.remote.service.mail.IMailRemoteService;
import com.toyo.remote.service.user.ILoginService;

/**
 * @author sparrow
 *
 */
@Service("IFriendsRemoteService")
public class DefaultFriendsService implements IFriendsService {

	private static final Log logger = LogFactory.getLog(DefaultFriendsService.class);

	@Autowired
	ILoginService loginService;

	@Autowired
	IMailRemoteService mailService;

	@Autowired
	FriendsRelationMapper friendsRelationMapper;

	@Autowired
	FriendsMapper friendsMapper;

	@Autowired
	FriendsMailTemplateMapper friendsMailTemplateMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.protocol.service.IFriendsService#findByUserId(java.lang.
	 * Long)
	 */

	public FriendsUser findByUserId(Long userId) throws LocalServiceException {
		FriendsUser o = G.o(FriendsUser.class);
		try {
			// TODO Auto-generated method stub
			UserAccount item = loginService.getUserAccountById(userId);

			if (item != null) {
				o.setAvatar(item.getAvatar());
				o.setLevel(item.getLevel());
				o.setNickName(item.getNickName());
				o.setUserId(item.getId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.protocol.service.IFriendsService#sendFriendsMail(java.lang.
	 * Long, java.lang.Long, int)
	 */

	public boolean sendFriendsMail(Long userId, Long friendUserId, int actionType) throws LocalServiceException {
		// TODO Auto-generated method stub

		boolean ret = false;
		// try {
		if (actionType == 1) {
			RequestFriendStatus status = sendFriendsRequestMail(userId, friendUserId);
			if (status.compareTo(RequestFriendStatus.Succeed) == 0) {
				ret = true;
			} else {
				throw new LocalServiceException(status.ordinal(), status.toString());
			}
		} else if (actionType == 2) {
			SendFriendsVitStatus status = sendFriendsVitMail(userId, friendUserId);
			if (status.compareTo(SendFriendsVitStatus.Ok) == 0) {
				ret = true;
			} else {
				throw new LocalServiceException(status.ordinal(), status.toString());
			}
		}
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return ret;
	}

	private static final int DAY = 24 * 60 * 60 * 1000;

	/**
	 * 
	 * @param userId
	 * @param friendUserId
	 * @return
	 */
	private SendFriendsVitStatus sendFriendsVitMail(Long userId, Long friendUserId) {
		// TODO Auto-generated method stub

		SendFriendsVitStatus status = SendFriendsVitStatus.Ok;
		Friends friends = friendsMapper.selectByUserId(userId);

		if (friends.getCurrentGiveVit() >= friends.getMaxGiveVit()) {
			logger.warn("current givt exceed max vit:" + friends.getCurrentGiveVit());
			status = SendFriendsVitStatus.ExceedMaxGiveVit;
			return status;
		}

		FriendsRelation friendsRelation = friendsRelationMapper.selectFriendsRelationByFriendsId(userId, friendUserId);

		if (friendsRelation.getVitState() == 1
				&& (System.currentTimeMillis() - friendsRelation.getUpdateTime().getTime() < DAY)) {
			logger.warn("friend:" + friendUserId + " already sent (within 1 day):" + friendsRelation.getId());
			status = SendFriendsVitStatus.VitAlreadySent;
			return status;
		}

		MailTriggerExpresion e = G.o(MailTriggerExpresion.class);
		
		
		MailAttachments attachments = G.o(MailAttachments.class);
		List<MailAttachment> attach = GameUtil.getList();
		attachments.setAttachments(attach);

		MailAttachment attachment = G.o(MailAttachment.class);
		attach.add(attachment);
		attachment.setId("Vit");
		attachment.setType(MailAttachment.ITEM_TYPE);
		attachment.setValue(String.valueOf(3));

		try {

			FriendsMailTemplate mailTemplate = friendsMailTemplateMapper
					.selectByCategory(IMailService.MAIL_CATEOGRY_VIT);
			long mailId = mailService.createMailWithUserAndTimeTrigger(userId,friendUserId, mailTemplate.getMailTitle(),
					mailTemplate.getMailContent(),

					IMailService.MAIL_CATEOGRY_VIT, CronUtil.getFormatedDate(new Date()), 24 * 60,
					attachments.toString(), e.toString());

			Friends o = G.o(Friends.class);
			o.setId(friends.getId());
			o.setCurrentGiveVit(friends.getCurrentGiveVit() + 1);
			friendsMapper.updateByPrimaryKeySelective(o);

			FriendsRelation friendsRelationUpdate = G.o(FriendsRelation.class);
			friendsRelationUpdate.setId(friendsRelation.getId());
			friendsRelationUpdate.setVitState(Integer.valueOf(1));

			friendsRelationMapper.updateByPrimaryKeySelective(friendsRelationUpdate);

			if (mailId > 0) {
				status = SendFriendsVitStatus.Ok;
			}
		} catch (RemoteServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return status;
	}
	
	
	private Friends getFriendsByUserId(Long userId) throws LocalServiceException{
		Friends friends = friendsMapper.selectByUserId(userId);
		// logger.info("userId:"+userId);
		if (friends == null) {
			createFriendsBase(userId);
			friends = friendsMapper.selectByUserId(userId);
		}
		return friends;
	}

	/**
	 * 
	 * @param userId
	 * @param friendUserId
	 * @return
	 */
	private RequestFriendStatus sendFriendsRequestMail(Long userId, Long friendUserId) {
		// TODO Auto-generated method stub
		RequestFriendStatus status = RequestFriendStatus.Failed;

		boolean ret = false;
		try {
			if (userId == friendUserId) {
				logger.info("userId:" + userId + " - " + friendUserId + " already be friends!");

				status = RequestFriendStatus.AlreadyFriends;
				return status;
			}

			Friends friends = getFriendsByUserId(userId);
			getFriendsByUserId(friendUserId);
			FriendsRelation r = friendsRelationMapper.selectFriendsRelationByFriendsId(userId, friendUserId);

			if (r != null && r.getState() == 1) {
				logger.info("userId:" + userId + " - " + friendUserId + " already be friends!");
				status = RequestFriendStatus.AlreadyFriends;
				return status;
			}
			// BUG: friends ship terminated

			// should not check.
			ret = mailService.canSendFriendRequest(userId, friendUserId);

			if (!ret) {
				logger.info("userId:" + userId + " - " + friendUserId + " friends reqeust alread sent and valid!");
				status = RequestFriendStatus.RepeatFriendRequest;
				return status;
			}
			int sizeOfFriends=friendsRelationMapper.sizeOfFriends(userId);
			if (sizeOfFriends> friends.getMaxFriends()) {
				logger.info("userId:" + userId + " - " + friendUserId + " current friends already max");

				status = RequestFriendStatus.AlreadyMaxFriends;
				return status;
			}

			if (friends.getCurrentFriendsRequest() > friends.getMaxFriendsReuqest()) {
				logger.info("userId:" + userId + " - " + friendUserId + " current friends  request already max");
				return RequestFriendStatus.AlreadMaxFriendsRequest;
			}

			MailTriggerExpresion e = G.o(MailTriggerExpresion.class);
			

			MailAttachments attachments = G.o(MailAttachments.class);
			List<MailAttachment> attach = GameUtil.getList();
			attachments.setAttachments(attach);

			MailAttachment attachment = G.o(MailAttachment.class);
			attach.add(attachment);
			attachment.setId("Friends_Request_Cancel");
			attachment.setType(MailAttachment.ACTION_TYPE);
			attachment.setValue(String.valueOf(IFriendsService.MAIL_ACTION_FRIEND_REQUEST_DECLINE));

			MailAttachment attachmentConfirm = G.o(MailAttachment.class);
			attachmentConfirm.setId("Friends_Request_Ok");
			attachmentConfirm.setType(MailAttachment.ACTION_TYPE);
			attachmentConfirm.setValue(String.valueOf(IFriendsService.MAIL_ACTION_FRIEND_REQUEST_ACCEPT));
			attach.add(attachmentConfirm);

			FriendsMailTemplate mailTemplate = friendsMailTemplateMapper
					.selectByCategory(IMailService.MAIL_CATEOGRY_FRIENDS_REQUEST);
			long mailId = mailService.createMailWithUserAndTimeTrigger(userId,friendUserId, mailTemplate.getMailTitle(),
					mailTemplate.getMailContent(), IMailService.MAIL_CATEOGRY_FRIENDS_REQUEST,
					CronUtil.getFormatedDate(new Date()), 24 * 60, attachments.toString(), e.toString());

			Friends o = G.o(Friends.class);
			o.setId(friends.getId());
			o.setCurrentFriendsRequest(friends.getCurrentFriendsRequest() + 1);
			friendsMapper.updateByPrimaryKeySelective(o);

			status = mailId > 0 ? RequestFriendStatus.Succeed : status;
		} catch (RemoteServiceException e1) {
			// TODO Auto-RemoteServiceException catch block
			e1.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.protocol.service.IFriendsService#getFriendsSeq(java.lang.
	 * Long, int, int)
	 */

	public FriendsUserSeq getFriendsSeq(Long userId, int offset, int length) throws LocalServiceException {
		// TODO Auto-generated method stub

		FriendsUserSeq o = G.o(FriendsUserSeq.class);
		RowBounds rb = new RowBounds(offset, length);
		List<FriendsUser> users = GameUtil.getList();

		try {
			// userId=230l;
			int total = friendsRelationMapper.sizeOfFriends(userId);
			List<FriendsRelation> friends = friendsRelationMapper.getFriendsByUserId(userId, rb);

			o.setTotal(total);
			o.setFriends(users);
			for (FriendsRelation r : friends) {
				FriendsUser u = G.o(FriendsUser.class);
				Friends friend = r.getFriend();
				if (friend != null) {
					if(friend.getAvatar()==null){
						u.setAvatar(friend.getAvatar() == null ? "001" : friend.getAvatar());
						u.setLevel(friend.getUserLevel() == null ? 1 : friend.getUserLevel());
						u.setNickName(friend.getNickName() == null ? "" : friend.getNickName());
					}else{
						// get 
						UserAccount account=loginService.getUserAccountById(friend.getUserId());
						u.setAvatar(account.getAvatar());
						u.setLevel(account.getLevel());
						u.setNickName(account.getNickName());
					}
					
					u.setAvatar(u.getAvatar() == null ? "001" : u.getAvatar());
					u.setLevel(u.getLevel() == null ? 1 : u.getLevel());
					u.setNickName(u.getNickName() == null ? "" : u.getNickName());
					u.setVitState(r.getVitState() == null ? 0 : r.getVitState());
					u.setUserId(GameUtil.s2c(friend.getUserId()));
					users.add(u);
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return o;
	}

	List<Friends> recommentFriends;

	@Scheduled(fixedRate = 5* 60 * 1000)
	public void fetchRecommentUserTask() {
		alreadRecomentUsers.clear();
		recommentFriends = friendsMapper.getRecommentFriends();
	}

	private Map<Long, RecommentFriendsPosition> alreadRecomentUsers = GameUtil.getMap();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.protocol.service.IFriendsService#getRecommendFriendsUser()
	 *
	 *
	 */

	public List<FriendsUser> getRecommendFriendsUser(Long userId) throws LocalServiceException {
		// TODO Auto-generated method stub

		List<FriendsUser> users = GameUtil.getList();

		try {
			if(null==recommentFriends||recommentFriends.size()==0){
				logger.warn("Recomment Friends List is Emtpy");
				return users;
			}
			RecommentFriendsPosition position = null;
			
			if (alreadRecomentUsers.containsKey(userId)) {
				position = alreadRecomentUsers.get(userId);
			} else {
				position = G.o(RecommentFriendsPosition.class);
				position.pos = 0;
				position.userId=userId;
				alreadRecomentUsers.put(userId, position);
			}

			FriendsIdSeq idSeq = mailService.getFriendRequestSentIds(userId);
			FriendsIdSeq friendIdSeq = getFriendsUserIds(userId);
			List<Long> ids = GameUtil.getList();
			if(FriendsIdSeq.valid(idSeq))
				ids.addAll(idSeq.getIds());
			if(FriendsIdSeq.valid(friendIdSeq))
				ids.addAll(friendIdSeq.getIds());
			
			ids.add(userId);
			// position.setBlackList(ids);

			// filter the friends.
			List<Friends> leftFriends = GameUtil.getList();

			for (Friends f : recommentFriends) {
				boolean found = false;
				int sizeOfFriends=friendsRelationMapper.sizeOfFriends(f.getUserId());
				if(sizeOfFriends>f.getMaxFriends()){
					found=true;
				}
				
				if(!found){
					for (Long id : ids) {
						if (f.getUserId().longValue() == id.longValue()) {
						
							found = true;
							break;
						}
					}
				}
				
				if (found) {
					continue;
				}
				leftFriends.add(f);

			}
			
			if(leftFriends.size()==0){
				logger.warn("Recomment Friends List  after black list  is Emtpy");
				return users;
			}
			
			int len=position.pos+8;
			if(leftFriends.size()<len){
				position.pos=0;
				//BUG:
				len=leftFriends.size();
				if(len>8){
					len=position.pos+8;
				}
				
			}
			
			List<Friends> items = leftFriends.subList(position.pos, len);
			
			logger.info("GetItems: "+position.pos+" - "+len);
			position.setPos(len);
			//position.setPos(endOfPage?0:position.pos+8);

			Friends friends = getFriendsByUserId(userId);//friendsMapper.selectByUserId(userId);
			

			//if (System.currentTimeMillis() - friends.getLastAccessTime().getTime() > 5 * 1000) {
				Friends o = G.o(Friends.class);
				o.setId(friends.getId());
				o.setLastRefreshRecommentFriends(new Date());
				friendsMapper.updateByPrimaryKeySelective(o);

				for (Friends friend : items) {
					FriendsUser u = G.o(FriendsUser.class);
					u.setAvatar(friend.getAvatar() == null ? "001" : friend.getAvatar());
					u.setLevel(friend.getUserLevel() == null ? 0 : friend.getUserLevel());
					u.setNickName(friend.getNickName() == null ? "" : friend.getNickName());
					u.setVitState(0);
					u.setUserId(GameUtil.s2c(friend.getUserId()));
					users.add(u);

				}
			//}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.protocol.service.IFriendsService#removeFriends(java.util.
	 * List)
	 */

	public boolean removeFriends(final Long userId,final List<Long> friendsIds) throws LocalServiceException {
		// TODO Auto-generated method stub
		int affectedRows = 0;

		try {
			if (friendsIds == null || friendsIds.size() == 0) {
				// return false;
			} else {

				//Friends f = friendsMapper.selectByUserId(userId);
				//if (f.getCurrentFriends() >= friendsIds.size()) {
					affectedRows = friendsRelationMapper.deleteFriendsId(userId, friendsIds);
					friendsRelationMapper.deletedByFriends(userId, friendsIds);
					
					
					GameContextGlobals.postTask(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							// delete the friends reqeust mail.
							SeqWrapper<Long> seqs = G.o(SeqWrapper.class);
							seqs.setSeq(friendsIds);
							try {
								mailService.deleteFriendsRequestMail(userId, seqs);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

					
				}
			//}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return affectedRows > 0 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.protocol.service.IFriendsService#getFriendsRestriction(java
	 * .lang.Long)
	 */

	public FriendsRestriction getFriendsRestriction(Long userId) throws LocalServiceException {
		// TODO Auto-generated method stub

		FriendsRestriction o = G.o(FriendsRestriction.class);

		try {
			Friends item = friendsMapper.selectByUserId(userId);
			if(item==null){
				createFriendsBase(userId);
				item=friendsMapper.selectByUserId(userId);
			}
			int sizeOfFriends=friendsRelationMapper.sizeOfFriends(userId);
			o.setCurrentFriends(sizeOfFriends);
			o.setCurrentFriendsRequest(item.getCurrentFriendsRequest());
			o.setCurrentGetVit(item.getCurrentGetVit());
			o.setCurrentGiveVit(item.getCurrentGiveVit());
			o.setMaxFriends(item.getMaxFriends());
			o.setMaxFriendsRequest(item.getMaxFriendsReuqest());
			o.setMaxGetVit(item.getMaxGetVit());
			o.setMaxGiveVit(item.getMaxGiveVit());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.protocol.service.IFriendsService#createFriendsBase(java.
	 * lang.Long)
	 */

	public Long createFriendsBase(Long userId) throws LocalServiceException {
		// TODO Auto-generated method stub

		Friends o = G.o(Friends.class);

		try {
			o.setUserId(userId);

			UserAccount item = loginService.getUserAccountById(userId);

			if (item != null) {
				o.setAvatar(item.getAvatar());
				o.setUserLevel(item.getLevel());
				o.setNickName(item.getNickName());
				o.setUserId(item.getId());
				o.setCreateTime(new Date());
				o.setUpdateTime(new Date());
				o.setLastAccessTime(new Date());
				o.setLastRefreshRecommentFriends(new Date());
				friendsMapper.insertSelective(o);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return o.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toyo.fish.protocol.service.IFriendsService#resetRestrictions()
	 */

	public int resetRestrictions() throws LocalServiceException {
		// TODO Auto-generated method stub

		int affectedRows = 0;
		try {
			
			affectedRows = friendsMapper.updateFriendsRestrictions();
			friendsRelationMapper.updateVitState();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return affectedRows;
	}
	
	// the update need to be execute trunk
	

	public boolean updateFriendsAccessTime(Long userId, String avatar, int level, String nickName)
			throws RemoteServiceException {
		// TODO Auto-generated method stub

		// add or update.
		int affectedRows = 0;

		try {

			Friends item = friendsMapper.selectByUserId(userId);
			if (item != null) {
				Friends o = G.o(Friends.class);
				o.setAvatar(avatar);
				o.setUserLevel(level);
				o.setNickName(nickName);
				o.setLastAccessTime(new Date());
				o.setId(item.getId());
				affectedRows = friendsMapper.updateByPrimaryKeySelective(o);
			} else {
				Long id = createFriendsBase(userId);
				if (id != null && id > 0) {
					affectedRows = 1;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return affectedRows > 0 ? true : false;
	}

	private void updateCurrentFriendCount(Friends f,int count){
		Friends item = G.o(Friends.class);
		item.setId(f.getId());
		if(f.getCurrentFriends()+count>=0)
			item.setCurrentFriends(f.getCurrentFriends() +count);
		friendsMapper.updateByPrimaryKeySelective(item);
	}
	
	private boolean createFriendsShip(Long userId, Long friendsUserId, Friends friends) {
		int friendsState = 0;
		boolean ret=false;
		try {
			
			friendsState = friendsRelationMapper.selectFriendsRelation(userId, friendsUserId);
			if (friendsState == 0) {
				FriendsRelation o = G.o(FriendsRelation.class);
				o.setOwnerUserId(userId);
				o.setFriendUserId(friendsUserId);
				o.setCreateTime(new Date());
				o.setUpdateTime(new Date());

				friendsRelationMapper.insertSelective(o);

				
				ret=true;
				
			} else {
				FriendsRelation o = friendsRelationMapper.selectFriendsRelationByFriendsId(userId, friendsUserId);
				if (o.getState() == 0) {
					FriendsRelation oo = G.o(FriendsRelation.class);
					oo.setId(o.getId());
					oo.setState(Integer.valueOf(1));
					friendsRelationMapper.updateByPrimaryKeySelective(oo);
					ret=true;
				}

			}
			
			if(ret){
				updateCurrentFriendCount(friends,1);
				sendAlreadyFriends(userId, friendsUserId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public boolean notifyMailActions(Long ownerUserId, Long friendUserId, int mailAction)
			throws RemoteServiceException {
		// TODO Auto-generated method stub
		boolean ret = false;
		try {
			
			if (mailAction == IFriendsService.MAIL_ACTION_FRIEND_REQUEST_ACCEPT) {
				Friends friends = friendsMapper.selectByUserId(ownerUserId);
				Friends friends2 = friendsMapper.selectByUserId(friendUserId);
				int sizeOfFriendsOwner=friendsRelationMapper.sizeOfFriends(ownerUserId);
				int sizeOfFriendsFriends=friendsRelationMapper.sizeOfFriends(friendUserId);
				if (friends != null && friends2 != null){
					if (sizeOfFriendsOwner< friends.getMaxFriends()
							&& sizeOfFriendsFriends< friends2.getMaxFriends()) {
						// checking if already friends.
						boolean ret1=createFriendsShip(ownerUserId, friendUserId, friends);
						boolean ret2=createFriendsShip(friendUserId, ownerUserId, friends2);
						ret = ret1&&ret2;
					}
				}
			} else if (mailAction == IFriendsService.MAIL_ACTION_FRIEND_REQUEST_DECLINE) {

			} else if (mailAction == 3) {

				Friends friends = friendsMapper.selectByUserId(friendUserId);

				if (friends != null)
					if (friends.getCurrentGetVit() < friends.getMaxGetVit()) {

						Friends item = G.o(Friends.class);
						item.setId(friends.getId());
						item.setCurrentGetVit(friends.getCurrentGetVit() + 1);
						int affectedRows=friendsMapper.updateByPrimaryKeySelective(item);

						ret = affectedRows>0?true:false;
					}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	private void sendAlreadyFriends(Long userId, Long friendUserId) {
		// TODO Auto-generated method stub

		// Friends friends=friendsMapper.selectByUserId(userId);

		MailTriggerExpresion e = G.o(MailTriggerExpresion.class);
		

		try {
			FriendsMailTemplate mailTemplate = friendsMailTemplateMapper
					.selectByCategory(IMailService.MAIL_CATEGORY_ALREADY_BE_FRIENDS);
			long mailId = mailService.createMailWithUserAndTimeTrigger(userId,friendUserId, mailTemplate.getMailTitle(),
					mailTemplate.getMailContent(),

					IMailService.MAIL_CATEGORY_ALREADY_BE_FRIENDS, CronUtil.getFormatedDate(new Date()), 24 * 60, null,
					e.toString());

		} catch (RemoteServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public FriendsIdSeq getFriendsUserIds(Long userId) throws RemoteServiceException {
		// TODO Auto-generated method stub
		List<Long> userIds = null;
		FriendsIdSeq o = G.o(FriendsIdSeq.class);
		try {
			userIds = friendsRelationMapper.getFriendsUserIds(userId);
			o.setIds(userIds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return o;
	}

	// @Scheduled(cron="0 0 0 0 * *")
	// Every hour reset.
	@Scheduled(cron = "0 0 */3 * * ?")
	public void scheduledTaskResetRestriction() {
		try {
			logger.info(">>>>>>Cron Reset Restricts:"+CronUtil.getFormatedDateNow());
			resetRestrictions();
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	public boolean reset(Long userId) throws RemoteServiceException {
		// TODO Auto-generated method stub
		
		boolean ret=false;
		try {
			
			
			List<Long> friendsId=friendsRelationMapper.getFriendsUserIdsWithoutState(userId);
			if(friendsId.size()==0){
				friendsId=null;
			}
			
			int affectedRows = friendsRelationMapper.deletedByFriendsWithoutState(userId);
				//affectedRows = friendsRelationMapper.deletedByFriendsWithoutState(userId, friendsId);
			//friendsMapper.updateByUserIds(friendsId, null, null, -1, null);
			//friendsMapper.updateByUserId(userId, null, null, -1 * friendsId.size(), null);
			friendsMapper.deleteByUserId(userId);
			
			ret=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}

}
