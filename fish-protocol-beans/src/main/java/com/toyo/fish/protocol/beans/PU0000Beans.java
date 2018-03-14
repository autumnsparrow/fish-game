/**
 * 
 */
package com.toyo.fish.protocol.beans;

import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.domain.BaseResponse;
import com.sky.game.context.route.RouterHeader;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.protocol.beans.user.UserAccountInfo;

/**
 * 
 * 
 * 
 * 
 * 
 * @author sparrow
 *
 */
public class PU0000Beans {
	
	
	/**
	 * Extra Object of
	 * User Protocol 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerNamespaceExtraType(namespace="ProtocolUser")
	public static class Extra  extends RouterHeader{
		
		
	}
	
	
	@HandlerRequestType(transcode="PU0000")
	public static class PU0000Request  extends UserBaseObj {
	
		UserAccountInfo account;

		public UserAccountInfo getAccount() {
			return account;
		}

		public void setAccount(UserAccountInfo account) {
			this.account = account;
		}
		
	}
	
	
	/**
	 * User Register.
	 * 
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PU0001")
	public static class PU0001Request extends  BaseRequest {
		
		String imei;
		Integer channel;
		
		
		public PU0001Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		public String getImei() {
			return imei;
		}
		public void setImei(String imei) {
			this.imei = imei;
		}
		public Integer getChannel() {
			return channel;
		}
		public void setChannel(Integer channel) {
			this.channel = channel;
		}
	
		
	}
	
	@HandlerResponseType(transcode="PU0001",responsecode="UP0001")
	public static class PU0001Response extends  BaseResponse{
		int state;
		long userId;
		String userName;
		
		
		public PU0001Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		
		
		
	}
	
	
	
	/**
	 * User Login
	 * 
	 * @param userName 
	 * @param passWord
	 * @param imei
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PU0002")
	public static class PU0002Request{
		String userName;
		String passWord;
		String imei;
		
	
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassWord() {
			return passWord;
		}
		public void setPassWord(String passWord) {
			this.passWord = passWord;
		}
		public String getImei() {
			return imei;
		}
		public void setImei(String imei) {
			this.imei = imei;
		}
		
	}
	
	@HandlerResponseType(transcode="PU0002",responsecode="UP0002")
	public static class PU0002Response extends BaseResponse{
		
		int state;
		String msg;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
		
	}
	
	public static class UserBaseObj implements IIdentifiedObject{
		Long id;

		public Long getId() {
			// TODO Auto-generated method stub
			return id;
		}

		public void setId(Long id) {
			// TODO Auto-generated method stub
			this.id=id;
		}
		
	}
	
	/**
	 * Bind third-party account
	 * 
	 * @param account  third-party account
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PU0003")
	public static class PU0003Request extends UserBaseObj {
		String account;

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		
		
	}
	
	@HandlerResponseType(transcode="PU0003",responsecode="UP0003")
	public static class PU0003Response extends BaseResponse{
		int state;
		String message;
		long userId;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		
	}
	
	
	/**
	 * 
	 * 4. bind phone
	 * 
	 * @param userId
	 * @param userName
	 * @param passWord
	 * @param validCode
	 * 
	 *
	 */
	@HandlerRequestType(transcode="PU0004")
	public static class PU0004Request  extends UserBaseObj {
		
		String userName;
		String passWord;
		
		
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassWord() {
			return passWord;
		}
		public void setPassWord(String passWord) {
			this.passWord = passWord;
		}
	
		
		
	}
	@HandlerResponseType(transcode="PU0004",responsecode="UP0004")
	public static class PU0004Response extends BaseResponse{
		int state;
		String msg;
		long userId;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		
		
	}
	
	
	
	/**
	 * 
	 *  send sms valid code.
	 * @param phone mobile phone number
	 *  send sms 
	 */
	@HandlerRequestType(transcode="PU0005")
	public static class PU0005Request   extends UserBaseObj {
		String phone;

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
		
	}
	
	/**
	 * @param state
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="PU0005",responsecode="UP0005")
	public static class PU0005Response{
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
		
	}
	
	
	/**
	 * Change password
	 * @param password   password
	 * @param validcode  valid code
	 * 
	 */
	@HandlerRequestType(transcode="PU0006")
	public static class PU0006Request  extends UserBaseObj {
		String password;
		
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		
	}
	
	/**
	 * 
	 * @author sparrow
	 * 
	 * @param state
	 * @param msg
	 *
	 */
	@HandlerResponseType(transcode="PU0006",responsecode="UP0006")
	public static class PU0006Response{
		int state;
		String msg;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
	}
	
	
	@HandlerRequestType(transcode="PU0007")
	public static class PU0007Request  extends UserBaseObj {
	
		String validcode;
		
		public String getValidcode() {
			return validcode;
		}
		public void setValidcode(String validcode) {
			this.validcode = validcode;	
		}
		
	}
	@HandlerResponseType(transcode="PU0007",responsecode="UP0007")
	public static class PU0007Response{
		int state;
		String msg;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
	}
	
	
	
	@HandlerRequestType(transcode="PU0008")
	public static class PU0008Request  extends UserBaseObj {
		
		String avatar;
		

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		
		
		
	}
	@HandlerResponseType(transcode="PU0008",responsecode="UP0008")
	public static class PU0008Response{
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
		
	}
	
	
	
	@HandlerRequestType(transcode="PU0009")
	public static class PU0009Request  extends UserBaseObj {
		
		String nickName;

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
	}
	
	@HandlerResponseType(transcode="PU0009",responsecode="UP0009")
	public static class PU0009Response{
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
		
	}
	
	

	@HandlerRequestType(transcode="PU0010")
	public static class PU0010Request  extends UserBaseObj {
		
		int level;

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

	
	}
	
	@HandlerResponseType(transcode="PU0010",responsecode="UP0010")
	public static class PU0010Response{
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
		
	}
	
	
	@HandlerRequestType(transcode="PU0011")
	public static class PU0011Request  extends UserBaseObj {
		String nickName;
		Integer level;
		String avatar;

		
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

		public void setLevel(Integer level) {
			this.level = level;
		}

		public Integer getLevel() {
			return level;
		}
		

	
	}
	
	@HandlerResponseType(transcode="PU0011",responsecode="UP0011")
	public static class PU0011Response{
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
		
	}
	
	
	/**
	 * 
	 * 
	 * reset the friends,mail,rank,userdata.
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PU0012")
	public static class PU0012Request  extends UserBaseObj {
		
	}
	
	
	@HandlerResponseType(transcode="PU0012",responsecode="UP0012")
	public static class PU0012Response{
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
		
	}
	
	
	@HandlerRequestType(transcode="PU0013")
	public static class PU0013Request  extends UserBaseObj {
		
	}
	
	
	@HandlerResponseType(transcode="PU0013",responsecode="UP0013")
	public static class PU0013Response{
		String timestamp;

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		
		
	}
	
	
	public static class User {
		Long id;
		String deviceId;
		Integer channelId;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		public Integer getChannelId() {
			return channelId;
		}
		public void setChannelId(Integer channelId) {
			this.channelId = channelId;
		}
		
		
		
	}
	
	@HandlerRequestType(transcode="PU0014")
	public static class PU0014Request  extends UserBaseObj {
		long start;
		long end;
		public long getStart() {
			return start;
		}
		public void setStart(long start) {
			this.start = start;
		}
		public long getEnd() {
			return end;
		}
		public void setEnd(long end) {
			this.end = end;
		}
		
	}
	
	
	@HandlerResponseType(transcode="PU0014",responsecode="UP0014")
	public static class PU0014Response{
		List<User> users;

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}
		
		
	}
	
	
	
	
	

}
