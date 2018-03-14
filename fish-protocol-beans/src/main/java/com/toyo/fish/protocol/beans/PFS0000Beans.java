package com.toyo.fish.protocol.beans;

import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.route.RouterHeader;
import com.toyo.fish.protocol.beans.PRS0000Beans.FishRank;
import com.toyo.fish.protocol.service.IFriendsService;
import com.toyo.fish.protocol.service.domain.FriendsUser;
import com.toyo.fish.protocol.service.domain.FriendsUserSeq;
import com.toyo.remote.service.payment.IPaymentService;
import com.toyo.remote.service.payment.domain.HuaweiConf;
import com.toyo.remote.service.payment.domain.PaymentOrder;
import com.toyo.remote.service.payment.domain.VivoOrder;

/**
 * 
 * Protocol Friends System.
 * 
 * 
 * 
 * @author sparrow
 *
 */
public class PFS0000Beans {

	/**
	 * Extra Object of User Protocol
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerNamespaceExtraType(namespace = "ProtocolFriendsSystem")
	public static class Extra extends RouterHeader {

	}
	
	public static class SeqObj{

		public int getSeq() {
			return seq;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}

		int seq;
	}

	public static class BaseObj extends SeqObj implements IIdentifiedObject {

		public Long getId() {
			// TODO Auto-generated method stub
			return id;
		}

		public void setId(Long id) {
			// TODO Auto-generated method stub
			this.id = id;

		}

		

		Long id;
		
	}
	
	/**
	 * {@link IFriendsService#findByUserId(Long)}
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PFS0001")
	public static class PFS0001Request extends BaseObj{
		Long searchUserId;

		public Long getSearchUserId() {
			return (searchUserId);
		}

		public void setSearchUserId(Long searchUserId) {
			this.searchUserId = searchUserId;
		}
		
	}
	
	@HandlerResponseType(transcode="PFS0001",responsecode="SFP0001")
	public static class PFS0001Response extends SeqObj {
		int state;
		long id;
		String nickName;
		String avatar;
		int level;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		
	}
	
	
	/**
	 * {@link IFriendsService#sendFriendsMail(Long, Long, int)}
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PFS0002")
	public static class PFS0002Request extends BaseObj{
		Long userId;
		int actionType;
		public Long getUserId() {
			return (userId);
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public int getActionType() {
			return actionType;
		}
		public void setActionType(int actionType) {
			this.actionType = actionType;
		}
		
	}
	
	@HandlerResponseType(transcode="PFS0002",responsecode="SFP0002")
	public static class PFS0002Response extends SeqObj {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
	
	}
	
	/**
	 * {@link IFriendsService#getFriendsSeq(Long, int, int)}
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PFS0003")
	public static class PFS0003Request extends BaseObj{
		int offset;
		int length;
		public int getOffset() {
			return offset;
		}
		public void setOffset(int offset) {
			this.offset = offset;
		}
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
		
		
	}
	
	@HandlerResponseType(transcode="PFS0003",responsecode="SFP0003")
	public static class PFS0003Response extends SeqObj {
		int state;
		int total;
		List<FriendsUser> friends;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public List<FriendsUser> getFriends() {
			return friends;
		}
		public void setFriends(List<FriendsUser> friends) {
			this.friends = friends;
		}
		
	
	}
	
	/**
	 * {@link IFriendsService#getRecommendFriendsUser()}
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PFS0004")
	public static class PFS0004Request extends BaseObj{
		
	}
	
	@HandlerResponseType(transcode="PFS0004",responsecode="SFP0004")
	public static class PFS0004Response extends SeqObj {
		int state;
		List<FriendsUser> friends;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public List<FriendsUser> getFriends() {
			return friends;
		}
		public void setFriends(List<FriendsUser> friends) {
			this.friends = friends;
		}
		
	
	}
	
	
	
	/**
	 * {@link IFriendsService#removeFriends(Long, List)}
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PFS0005")
	public static class PFS0005Request extends BaseObj{
		List<Long> friendIds;

		public List<Long> getFriendIds() {
			return friendIds;
		}

		public void setFriendIds(List<Long> friendIds) {
			this.friendIds = friendIds;
		}
		
		
	}
	
	@HandlerResponseType(transcode="PFS0005",responsecode="SFP0005")
	public static class PFS0005Response extends SeqObj {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
	
	}
	
	
	/**
	 * {@link IFriendsService#getFriendsRestriction(Long)}
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PFS0006")
	public static class PFS0006Request extends BaseObj{
		
	}
	
	@HandlerResponseType(transcode="PFS0006",responsecode="SFP0006")
	public static class PFS0006Response extends SeqObj {
		int state;
		int currentGiveVit;
		int currentGetVit;
		int currentFriends;
		int currentFriendsRequest;
		
		int maxGiveVit;
		int maxGetVit;
		int maxFriends;
		int maxFriendsRequest;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public int getCurrentGiveVit() {
			return currentGiveVit;
		}
		public void setCurrentGiveVit(int currentGiveVit) {
			this.currentGiveVit = currentGiveVit;
		}
		public int getCurrentGetVit() {
			return currentGetVit;
		}
		public void setCurrentGetVit(int currentGetVit) {
			this.currentGetVit = currentGetVit;
		}
		public int getCurrentFriends() {
			return currentFriends;
		}
		public void setCurrentFriends(int currentFriends) {
			this.currentFriends = currentFriends;
		}
		public int getCurrentFriendsRequest() {
			return currentFriendsRequest;
		}
		public void setCurrentFriendsRequest(int currentFirendsReuqest) {
			this.currentFriendsRequest = currentFirendsReuqest;
		}
		public int getMaxGiveVit() {
			return maxGiveVit;
		}
		public void setMaxGiveVit(int maxGiveVit) {
			this.maxGiveVit = maxGiveVit;
		}
		public int getMaxGetVit() {
			return maxGetVit;
		}
		public void setMaxGetVit(int maxGetVit) {
			this.maxGetVit = maxGetVit;
		}
		public int getMaxFriends() {
			return maxFriends;
		}
		public void setMaxFriends(int maxFriends) {
			this.maxFriends = maxFriends;
		}
		public int getMaxFriendsRequest() {
			return maxFriendsRequest;
		}
		public void setMaxFriendsRequest(int maxFriendsRequest) {
			this.maxFriendsRequest = maxFriendsRequest;
		}
		
		
		
	}
	
	
	
	
	

}
