/**
 * 
 */
package com.toyo.fish.protocol.user.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameServiceUtil;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.data.wrapper.domain.Login;
import com.toyo.fish.data.wrapper.domain.ThirdPartyAccount;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.data.wrapper.domain.ValidCode;
import com.toyo.fish.data.wrapper.domain.Zone;
import com.toyo.fish.data.wrapper.persistence.ThirdPartyAccountMapper;
import com.toyo.fish.data.wrapper.persistence.UserAccountMapper;
import com.toyo.fish.data.wrapper.persistence.ZoneMapper;
import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeGift;
import com.toyo.fish.protocol.beans.user.UserAccountInfo;
import com.toyo.fish.protocol.service.IUserAccount;
import com.toyo.fish.protocol.service.IUserAccountManager;
import com.toyo.fish.protocol.service.IValidCodeService;
import com.toyo.remote.service.friends.IFriendsRemoteService;
import com.toyo.remote.service.mail.IMailRemoteService;
import com.toyo.remote.service.system.IExchangeCodeService;
import com.toyo.remote.service.user.ILoginService;
import com.toyo.remote.service.zone.IZoneRemoteService;

/**
 * @author sparrow
 *
 */
@Service("IUserAccountManager")
public class DefaultUserAccountManager implements IUserAccountManager {

	private static final Log logger = LogFactory.getLog(DefaultUserAccountManager.class);

	@Autowired
	UserAccountMapper userAccountMapper;
	@Autowired
	ZoneMapper zoneMapper;
	@Autowired
	ILoginService loginService;

	@Autowired
	ThirdPartyAccountMapper thirdPartyAccountMapper;

	@Autowired
	IValidCodeService validCodeService;

	@Autowired
	SmsMessageService smsMessageService;

	@Autowired
	IExchangeCodeService exchangeCodeService;
	
	/**
	 * 
	 * should auto the remote service
	 */
	@Autowired
	IZoneRemoteService zoneRemoteService;
	
	@Autowired
	IFriendsRemoteService friendsRemoteService;
	@Autowired
	IMailRemoteService mailRemoteService;
	

	ConcurrentHashMap<Long, UserAccountInfo> userAccountInfoMap = new ConcurrentHashMap<Long, UserAccountInfo>();

	public DefaultUserAccountManager() {
		super();
		// TODO Auto-generated constructor stub
		try {
			init();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.game.logic.user.IUserAccount#bindThirdpartyAccount(java.
	 * lang.Long, java.lang.String)
	 */
	public boolean bindThirdpartyAccount(Long userId, String account) throws LocalServiceException {
		// TODO Auto-generated method stub

		ThirdPartyAccount thirdPartyAccount = thirdPartyAccountMapper.findThirdPartyAccountByUserId(userId);
		//
		int affectedRows = 0;
		if (thirdPartyAccount == null) {
			ThirdPartyAccount o = G.o(ThirdPartyAccount.class);
			o.setAccount(account);
			o.setUserId(userId);
			o.setCreateTime(new Date());
			
			affectedRows = thirdPartyAccountMapper.insertSelective(o);

		}

		return affectedRows > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toyo.fish.game.logic.user.IUserAccount#bindPhone(java.lang.Long,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean bindPhone(Long userId, String userName, String password) throws LocalServiceException {
		// TODO Auto-generated method stub

		UserAccount account = userAccountMapper.selectByPrimaryKey(userId);
		if (account.getUserName() != null) {
			throw new LocalServiceException(-2, "username already bind info don't exist in the memory!");
		}
		int affectedRows = 0;
		if (account != null) {
			// update the object selective.
			UserAccount o = G.o(UserAccount.class);
			o.setId(userId);
			o.setUserName(userName);
			o.setPasswd(password);
			userAccountMapper.updateByPrimaryKeySelective(o);
		}

		return affectedRows > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.game.logic.user.IUserAccount#sendValidCode(java.lang.Long,
	 * java.lang.String)
	 */
	public boolean sendValidCode(Long userId, String phone) throws LocalServiceException {
		// TODO Auto-generated method stub

		// every time create or update a valid code.
		ValidCode v = validCodeService.reGenerateValidCode(userId);

		if (v != null) {
			// send the
			smsMessageService.send(phone, v.getValidCode());
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.game.logic.user.IUserAccount#modifyPassword(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	public boolean modifyPassword(Long userId, String password) throws LocalServiceException {
		// TODO Auto-generated method stub

		//
		UserAccount account = userAccountMapper.selectByPrimaryKey(userId);
		if (account == null || account.getUserName() == null) {
			throw new LocalServiceException(-2, "username not  bind info don't exist in the memory!");
		}

		UserAccount o = G.o(UserAccount.class);
		o.setId(account.getId());
		o.setPasswd(password);

		userAccountMapper.updateByPrimaryKeySelective(o);

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.game.logic.user.IUserAccountManager#getUserAccount(java.
	 * lang.String, java.lang.String)
	 */
	public UserAccount getUserAccount(String imei, Integer channel) throws LocalServiceException {
		// TODO Auto-generated method stub
		// 1 . get the user account.

		// UserAccountInfo info=G.o(UserAccountInfo.class);

		UserAccount account=null;
		try {
			account = userAccountMapper.findUserAccountByDeviceIdAndChannelId(imei, channel);
			int affectedRows = 0;
			if (account == null) {
				// register the user
				// Zone zone=zoneMapper.findZoneByActive();
				// dispatch the user to zone.

				account = G.o(UserAccount.class);
				account.setChannelId(channel);
				account.setDeviceId(imei);
				account.setCreateTime(new Date());
				account.setApkVersion("0");
				account.setAvatar("001");
				account.setLevel(Integer.valueOf(1));
				account.setNickName("");
				

				affectedRows = userAccountMapper.insertSelective(account);

			}

			// 3.
			// loadUserInfo(info,account,imei);
			updateLogin(account, imei);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return account;
	}

	/**
	 * 
	 * @param info
	 * @param account
	 * @param deviceId
	 * @throws LocalServiceException
	 */
	private void updateLogin(UserAccount account, String deviceId) throws LocalServiceException {

		if (account != null) {
			// info.setId(account.getId());

			// Zone
			// zone=zoneMapper.selectByPrimaryKey(Long.valueOf(account.getZoneId()));

			// login the server give it a token for communication.
			Login login = loginService.findLoginByDeviceId(deviceId);
			if (login == null) {
				// that's impossible!!!!!!
				throw new LocalServiceException(-1, "Can't find the connection! " + deviceId);
			}

			if (login != null) {
				String token = "";// GameUtil.getToken(login.getDeviceId(),login.getDesKey());
				login.setUserId(account.getId());
				login.setToken(token);
				logger.info(">>>>>userId:" + account.getId() + " deviceId:" + login.getDeviceId());
				loginService.update(login);

			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.game.logic.user.IUserAccountManager#getUserAccount(java.
	 * lang.String, java.lang.String, java.lang.String)
	 */
	public UserAccount getUserAccount(String imei, String userName, String password) throws LocalServiceException {

		// UserAccountInfo info=G.o(UserAccountInfo.class);
		// TODO Auto-generated method stub
		UserAccount account = userAccountMapper.findUserAccountByUserName(userName);

		if (account == null) {
			throw new LocalServiceException(1, "username don't exist!");
		}

		if (account.getPasswd().equals(password)) {
			// update the token.
			updateLogin(account, imei);
		} else {
			throw new LocalServiceException(-1, "username passsord not match!");
		}

		return account;
	}

	public UserAccount getUserAccountByUserName(String userName) throws LocalServiceException {
		// TODO Auto-generated method stub
		UserAccount account = userAccountMapper.findUserAccountByUserName(userName);

		if (account == null) {
			throw new LocalServiceException(1, "username ,password don't match");
		}

		return account;
	}

	public boolean validCode(Long userId, String validCode) throws LocalServiceException {
		// TODO Auto-generated method stub

		// first valid the valid code.
		boolean valid = validCodeService.validValidCode(userId, validCode);

		if (!valid) {
			throw new LocalServiceException(-1, "invalid valid code");
		}

		return valid;
	}

	/**
	 * @throws RemoteServiceException
	 * 
	 * 
	 */
	public ExchangeCodeGift exchangeCode(Long userId, String code) throws LocalServiceException {
		// TODO Auto-generated method stub
		ExchangeCodeGift gift = null;

		int useState;

		try {
			gift = exchangeCodeService.exchangeCode(userId, code);
		} catch (RemoteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			throw new LocalServiceException(e.status, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gift;
	}

	public boolean updateChangeCode(Long userId, String code, Integer giftId) throws LocalServiceException {
		// TODO Auto-generated method stub
		boolean ret = false;

		try {
			ret = exchangeCodeService.updateExchangeCode(userId, code, giftId);
		} catch (RemoteServiceException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new LocalServiceException(e.status, e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
		}

		return ret;
	}

	
	public UserAccount getUserAccountByUserId(Long userId) throws LocalServiceException {
		// TODO Auto-generated method stub

		UserAccount userAccount = userAccountMapper.selectByPrimaryKey(userId);
		return userAccount;
	}

	
	public boolean updateAvatar(Long userId, String avatar) throws LocalServiceException {
		// TODO Auto-generated method stub
		int affectedRows = 0;
		try {
			affectedRows=userAccountMapper.updateAvtar(userId, avatar);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return affectedRows > 0 ? true : false;
	}

	
	public boolean updateNickname(Long userId, String nickName) throws LocalServiceException {
		// TODO Auto-generated method stub
		// should valid the bad words.
		boolean ret = false;
		try {
			logger.info(">>>>>userId:" + userId + " update nickName:" + nickName);
			if (!selectWords(nickName)) {
				int affectedRows = userAccountMapper.updateNickname(userId, nickName);
				ret = affectedRows > 0 ? true : false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;

	}

	LinkedList<String> keywords;

	public boolean selectWords(String name) {
		String str;
		for (int i = 0; i < keywords.size(); i++) {
			str = keywords.get(i);
			if (name.contains(str)) {
				return true;
			}
		}
		return false;
	}

	public void init() throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		InputStream is = GameServiceUtil.class.getResourceAsStream("/META-INF/random/badword.xml");
		keywords = xmlMapper.readValue(is, LinkedList.class);

	}

	public int updateUserBasicInformation(Long userId, String avtar, String nickName, Integer level) {
		// TODO Auto-generated method stub
		
		int affectedRows=0;
		try {
			logger.info(">>>>>userId:" + userId );
			String name=null;
			if (null!=nickName&&!selectWords(nickName)) {
				name=nickName;
				
			}
			affectedRows = userAccountMapper.updateUserBasicInformation(userId,name, avtar,  level);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return affectedRows;
		
	}

	public void resetAccount(Long userId) throws LocalServiceException {
		// TODO Auto-generated method stub
		try {
			zoneRemoteService.reset(userId);
			mailRemoteService.reset(userId);
			friendsRemoteService.reset(userId);
		} catch (RemoteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LocalServiceException(e.status, e.getMessage());
		}
		
		
	}

	

}
