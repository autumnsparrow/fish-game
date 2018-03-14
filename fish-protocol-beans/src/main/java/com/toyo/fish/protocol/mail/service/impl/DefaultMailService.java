/**
 * 
 */
package com.toyo.fish.protocol.mail.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.event.WebsocketEvent;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.spring.ice.SeqWrapper;
import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.data.wrapper.domain.UserAccountFriends;
import com.toyo.fish.data.wrapper.friends.persistence.FriendsMailTemplateMapper;
import com.toyo.fish.data.wrapper.mail.domain.Mail;
import com.toyo.fish.data.wrapper.mail.domain.MailTriggerLog;
import com.toyo.fish.data.wrapper.mail.domain.MailTriggerUserInformation;
import com.toyo.fish.data.wrapper.mail.persistence.MailMapper;

import com.toyo.fish.data.wrapper.mail.persistence.MailTriggerLogMapper;
import com.toyo.fish.data.wrapper.mail.persistence.MailTriggerUserInformationMapper;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0002Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.User;
import com.toyo.fish.protocol.service.IFriendsService;
import com.toyo.fish.protocol.service.IMailService;
import com.toyo.fish.protocol.service.domain.MailAttachment;
import com.toyo.fish.protocol.service.domain.MailAttachments;
import com.toyo.fish.protocol.service.domain.MailIdSeq;
import com.toyo.fish.protocol.service.domain.MailLevel;
import com.toyo.fish.protocol.service.domain.MailTriggerExpresion;
import com.toyo.remote.service.friends.IFriendsRemoteService;
import com.toyo.remote.service.friends.domain.FriendsIdSeq;
import com.toyo.remote.service.user.ILoginService;
import com.toyo.remote.service.user.SeqUserAccount;
import com.toyo.remote.service.user.SeqUserAccountFriends;

/**
 * Mail Core algorithm:
 * 
 * 1.mail category(trigger expression) - category >5 (6,7) use the trigger
 * expression. - category <=5 do not use the trigger expression.
 * 
 * 2. trigger expression: - login n days before. - user level range min : min
 * user level max : max user level relation : LE GE BW LE : less than min user
 * level GE : max than min user level BW : user level between min level and max
 * level. - userIdSeq send multiple reciever .
 * 
 * 3. time trigger and time active hours - time trigger : - active hours : mail
 * filter state will turn from 0 to 1 when current system between the time
 * trigger and time time trigger + active hours(minutes)
 * 
 * 
 * 4. mail process: - when system starting,the mail system will Reset Task: 1.
 * reset the mail (with filter state 1 ) in queue state to 0 (not in queue). 2.
 * clear the global mail map 3. load the mail from database that (not in queue)
 * into the global mail map. 4. update the mail in the global map in queue state
 * to 1 (in queue).
 * 
 * - mail system will execute the Reset Task (every 1 hour) - mail system will
 * schedule task(every 1 sec) 1. check mail filter turn 0 (not active ) to 1
 * (active) (mainly time trigger) 1.1 if mail system has new active mail, mail
 * system will load the active record(Reset Task 3,4 )
 * 
 * - mail system will execute task (10 seconds) 1. fetch the online user from
 * UserModule. - mail system will execute task (5 seconds) 1. iterate every mail
 * in (global mail) to every user (online user) check if the mail should
 * trigger.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @author sparrow
 *
 */
@Service("IMailRemoteService")
public class DefaultMailService implements IMailService {
	public static final int FILTER_STATE_NEW = 0;
	public static final int FILTER_STATE_ACTIVE = 1;
	public static final int FILTER_STATE_DEACTIVE = 2;
	public static final int FILTER_STATE_CLEANEDUP = 3;

	private static final Log logger = LogFactory.getLog(DefaultMailService.class);
	@Autowired
	MailMapper mailMapper;

	@Autowired
	MailTriggerLogMapper mailTriggerLogMapper;

	@Autowired
	ILoginService loginService;

	@Autowired
	MailTriggerUserInformationMapper mailTriggerUserInformationMapper;

	//
	// remote service mail
	//
	@Autowired
	IFriendsRemoteService friendsService;

	boolean initialized = false;

	/*
	 * (non-Javadoc) after user login, user invoke the method.
	 * 
	 * 
	 * 
	 * 
	 * @see
	 * com.toyo.remote.service.mail.IMailRemoteService#notifyUserLogined(java.
	 * lang.Long, int, boolean, int)
	 */

	public DefaultMailService() {
		super();
		// TODO Auto-generated constructor stub
		// clear the mail state

	}

	public void notifyUserLogined(Long userId, int userLevel, int apkVersion) throws RemoteServiceException {
		logger.info("notifyUserLogined(" + userId + "," + userLevel + "," + apkVersion+")");
		notifyUserLogined(userId, userLevel, apkVersion, true);
	}

	private void notifyUserLogined(Long userId, int userLevel, int apkVersion, boolean updateUserInformation)
			throws RemoteServiceException {
		// TODO Auto-generated method stub

		try {

			//
			// update the MailUserInformation.
			//
			boolean apkVesionUpdated = false;
			long durations = 0;
			if (updateUserInformation) {
				MailTriggerUserInformation record = G.o(MailTriggerUserInformation.class);

				MailTriggerUserInformation o = mailTriggerUserInformationMapper.selectByUserId(userId);

				if (o == null) {
					o = G.o(MailTriggerUserInformation.class);
					o.setUserId(userId);
					o.setLastApkVersion(apkVersion);
					o.setCreateTime(new Date());
					o.setLastLogin(new Date());
					o.setUpdateTime(new Date());
					mailTriggerUserInformationMapper.insertSelective(o);

				} else {

					record.setId(o.getId());

					Date lastLogin = o.getLastLogin();
					Date current = new Date();
					record.setLastLogin(current);

					durations = current.getTime() - lastLogin.getTime();

					if (o.getLastApkVersion() != apkVersion) {
						apkVesionUpdated = true;
						record.setLastApkVersion(apkVersion);
					}

					mailTriggerUserInformationMapper.updateByPrimaryKeySelective(record);
				}

			}

			int loginAfterNDays = (int) (durations / (24 * 60 * 60 * 1000));

			// check the user in the global mail map.
			//
			//

			for (Mail t : mailQueue) {
				// first to fetch the userId
				boolean trigged = false;
				if (t.getCategory() < IMailService.MAIL_CATEOGRY_TRIGGER_NORMAL) {

					if (t.getReciever() != null && t.getReciever().longValue() == userId.longValue()) {

						trigged = true;
					} else {
						continue;
					}

					// check if triggered
					// if alread trigged ,not process any more.
					int size = mailTriggerLogMapper.sizeOfUserIdAndMailId(userId, t.getId());
					if (size > 0) {
						trigged = false;
						continue;
					}

				} else

				//
				// only mail category larger or equal
				// MAIL_CATEOGRY_TRIGGER_NORMAL need the mail trigger expression
				// process
				if (t.getCategory() >= IMailService.MAIL_CATEOGRY_TRIGGER_NORMAL) {

					MailTriggerExpresion e = MailTriggerExpresion.getInstance(t.getTriggerExpression());
					if (e != null) {
						List<Long> userIds = e.getUserIds();
						if (userIds != null)
							for (Long id : userIds) {
								if (userId.longValue() == id.longValue()) {
									trigged = true;
									break;
								}
							}

						if (!trigged) {
							if (e.isApkVersion())
								trigged = apkVesionUpdated;
						}

						if (!trigged) {
							int days = e.getLoginAfterNDays();
							if (days > 0) {
								trigged = loginAfterNDays > days;
							}
						}

						if (!trigged) {
							// check user level
							/**
							 * 0:
							 * 
							 */
							MailLevel mailLevel = e.getUserLevel();
							if (mailLevel != null) {
								int relation = mailLevel.getRelation();
								switch (relation) {
								case MailLevel.LE:
									trigged = userLevel < mailLevel.getMin();
									break;
								case MailLevel.BW:
									trigged = userLevel > mailLevel.getMin() && userLevel < mailLevel.getMax();
									break;

								case MailLevel.GE:
									trigged = userLevel > mailLevel.getMin();
									break;

								default:
									break;
								}
							}

						}
					}

				}

				if (trigged) {
					try {
						logger.info(
								"Mail Triggered:" + userId + " - " + t.getId() + " - " + t.getTimeTriggerExpression());
						this.mailTriggers(userId, t.getId());
					} catch (LocalServiceException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toyo.fish.protocol.service.IMailService#
	 * createMailWithUserAndTimeTrigger(java.lang.String, java.lang.String,
	 * java.lang.String, int, java.lang.String, int, java.util.List,
	 * java.lang.String)
	 */

	public long createMailWithUserAndTimeTrigger(Long userId, Long reciever, String title, String content, int category,
			String activeDateTime, int activeLifeHours, String attachments, String triggerExrepsion)
			throws RemoteServiceException {
		// TODO Auto-generated method stub

		Long id = null;
		long l = System.currentTimeMillis();
		try {
			Mail record = G.o(Mail.class);
			record.setTitle(title);
			record.setActiveHours(activeLifeHours);
			record.setAttachments(attachments);
			record.setAttachmentState(0);
			record.setCategory(category);
			record.setCleanupState(0);
			record.setContent(content);
			record.setCreateTime(new Date());
			//

			record.setFilterState(0);

			record.setSender(userId);
			record.setInQueueState(0);
			// Add the reciever.
			record.setReciever(reciever);
			record.setTimeTriggerExpression(activeDateTime);
			record.setTriggerExpression(triggerExrepsion);

			int affectedRows = mailMapper.insertSelective(record);
			id = null;
			if (affectedRows > 0) {
				id = record.getId();

				if (record.getFilterState() == 1) {
					logger.info(" mail should active:" + id + " - " + activeDateTime);
					// should call update
					// this.triggerMails.add(record);
					// triggerMails = filterMailTriggerExrepsionActive();

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("xxxxxxx  create mail duration" + (System.currentTimeMillis() - l));
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toyo.fish.protocol.service.IMailService#
	 * getMailTriggerExresionByMailId(java.lang.Long)
	 */

	public MailTriggerLog getMailTriggerExresionByMailId(Long mailId) throws LocalServiceException {
		// TODO Auto-generated method stub

		MailTriggerLog item = null;
		try {
			item = mailTriggerLogMapper.selectByMailId(mailId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.protocol.service.IMailService#updateMailTriggerExpresion(
	 * java.lang.Long, int)
	 */

	public boolean updateMailTriggerExpresion(Long userId, Long mailId, int state) throws LocalServiceException {
		// TODO Auto-generated method stub

		int affectedRows = 0;
		try {
			if (state == 0) {
				logger.warn(" privileges exceed: " + userId + " - " + mailId + " - " + state);
				return false;
			}

			MailTriggerLog entry = mailTriggerLogMapper.selectByPrimaryKey(mailId);
			boolean result = false;
			if (entry != null && entry.getReciever().longValue() == userId) {

				if (entry.getMail() != null) {
					if (entry.getCategory() == IMailService.MAIL_CATEOGRY_FRIENDS_REQUEST) {
						if (state == IFriendsService.MAIL_ACTION_FRIEND_REQUEST_ACCEPT) {
							try {
								if (entry.getMail() != null) {
									result = friendsService.notifyMailActions(entry.getMail().getSender(),
											entry.getReciever(), IFriendsService.MAIL_ACTION_FRIEND_REQUEST_ACCEPT);
								}

								 if (result) {
									// delete the friends request mail
									// Long mailId=entry.getMailId();
									Mail m = G.o(Mail.class);
									m.setId(entry.getMailId());
									m.setActiveHours(0);
									// m.setFilterState(Integer.valueOf(3));

									mailMapper.updateByPrimaryKeySelective(m);

								}
							} catch (RemoteServiceException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new LocalServiceException(e.status, e.getMessage());
							}
						} else if (state == IFriendsService.MAIL_ACTION_FRIEND_REQUEST_DECLINE) {
							try {
								Mail m = G.o(Mail.class);
								m.setId(entry.getMailId());
								m.setActiveHours(0);
								// m.setFilterState(Integer.valueOf(3));

								int affectedRow = mailMapper.updateByPrimaryKeySelective(m);
								result = affectedRow > 0 ? true : false;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					} else if (entry.getCategory() == IMailService.MAIL_CATEOGRY_VIT) {

						if (state == 1) {
							try {
								if (entry.getMail() != null) {
									Long sender = entry.getMail().getSender();
									Long reciever = entry.getReciever();
									// using 3 as accept
									result = friendsService.notifyMailActions(sender, reciever,
											IFriendsService.MAIL_ACTION_VIR_ACCEPT + 1);
								}
							} catch (RemoteServiceException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new LocalServiceException(e.status, e.getMessage());
							}
						}
					} else  if(entry.getCategory()==IMailService.MAIL_CATEGORY_ALREADY_BE_FRIENDS) {
						if (state == 1) {
							try {
								Mail m = G.o(Mail.class);
								m.setId(entry.getMailId());
								m.setActiveHours(0);
								// m.setFilterState(Integer.valueOf(3));

								int affectedRow = mailMapper.updateByPrimaryKeySelective(m);
								result = affectedRow > 0 ? true : false;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else{
						result=true;
					}

				} else {
					// mail is null;
					result = true;
				}

				// if the reult
				if (result) {
					MailTriggerLog o = G.o(MailTriggerLog.class);
					o.setId(mailId);
					o.setState(state);
					affectedRows = mailTriggerLogMapper.updateByPrimaryKeySelective(o);
				}
			}

		} catch (LocalServiceException e) {

			throw e;
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
	 * com.toyo.fish.protocol.service.IMailService#getMailIdByRangeAndState(java
	 * .lang.Long, int, int, int)
	 */

	public MailIdSeq getMailIdByRangeAndState(Long userId, int category, int state, int offset, int length)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		MailIdSeq mailIdSeq = null;
		try {
			int total = mailTriggerLogMapper.sizeOfCategoryAndState(userId, category, state);

			RowBounds rb = new RowBounds(offset, length);

			List<Long> idSeq = mailTriggerLogMapper.idSeqOfCategoryAndStateByRange(userId, category, state, rb);

			mailIdSeq = G.o(MailIdSeq.class);
			mailIdSeq.setIds(idSeq);
			mailIdSeq.setTotal(total);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mailIdSeq;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.protocol.service.IMailService#getMailsByIdSeqs(java.lang.
	 * Long, java.util.List)
	 */

	public List<MailTriggerLog> getMailsByIdSeqs(Long userId, List<Long> idSeqs) throws LocalServiceException {
		// TODO Auto-generated method stub

		List<MailTriggerLog> items = null;
		try {
			logger.info("getMailByIdSeqs:" + userId + " -  " + Arrays.toString(idSeqs.toArray(new Long[] {})));
			items = mailTriggerLogMapper.selectByIdSeqs(userId, idSeqs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}

	private int resetMailState() {
		int affectedRows = 0;
		try {
			logger.info("====mail in queue state 0,global map 0.");
			mailMapper.updateIdInQueueClear();
			mailMap.clear();
			affectedRows = filterMailTriggerExrepsionActive();
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return affectedRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toyo.fish.protocol.service.IMailService#
	 * filterMailTriggerExrepsionActive()
	 */
	private int offset = 0;
	// MAX MAIL QUEUE is 5000
	private static final int limit = 5000;
	List<Mail> mailQueue = GameUtil.getList();
	Map<Long, Mail> mailMap = GameUtil.getMap();

	public int filterMailTriggerExrepsionActive() throws LocalServiceException {
		// TODO Auto-generated method stub
		// mailMapper.updateTriggerActive();
		List<Mail> items = null;
		int affectedRows = 0;
		try {
			Integer total = mailMapper.sizeOfActiveExpression();

			RowBounds rb = new RowBounds(offset, limit);

			items = mailMapper.selectActiveExpressionNotInQueue(rb);
			List<Long> idSeq = GameUtil.getList();
			for (Mail item : items) {
				mailMap.put(item.getId(), item);
				// update the state the mail alread in queue.
				idSeq.add(item.getId());
			}

			if (idSeq.size() == 0) {
				idSeq = null;
			}

			affectedRows = mailMapper.updateIdInQueue(idSeq);

			logger.info("active mail: " + total + " -  " + affectedRows + " in queue" + " - queue:" + mailMap.size());

			// mailQueue.addAll(items);

			mailQueue.clear();
			mailQueue.addAll(mailMap.values());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return affectedRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toyo.fish.protocol.service.IMailService#
	 * filterMailTimeTriggerExpresionDeactive()
	 */

	public List<Long> filterMailTimeTriggerExpresionDeactive() throws LocalServiceException {
		// TODO Auto-generated method stub

		List<Long> items = null;
		try {
			items = mailMapper.selectByFilterState(FILTER_STATE_DEACTIVE);

			int affectedRows = mailTriggerLogMapper.deleteByMailIdSeq(items.size() == 0 ? null : items);

			// update the state
			if (affectedRows > 0)
				mailMapper.updateTriggerCleanedup(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return items;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.toyo.fish.protocol.service.IMailService#mailTriggers(java.lang.Long,
	 * java.lang.Long)
	 */

	public boolean mailTriggers(Long userId, Long mailId) throws LocalServiceException {
		// TODO Auto-generated method stub

		int affectedRows = 0;

		try {
			int size = mailTriggerLogMapper.sizeOfUserIdAndMailId(userId, mailId);
			if (size == 0) {
				Mail mail = mailMapper.selectByPrimaryKey(mailId);
				if (mail == null) {
					return false;
				}
				// if the mail triggered.
				MailTriggerLog o = G.o(MailTriggerLog.class);
				o.setCategory(mail.getCategory());
				o.setMailId(mailId);
				o.setCreateTime(new Date());
				o.setReciever(userId);
				affectedRows = mailTriggerLogMapper.insertSelective(o);
				Long id = o.getId();

				//
				// server push
				PMS0002Request req = G.o(PMS0002Request.class);
				req.setActiveDateTime(mail.getTimeTriggerExpression());
				req.setActiveHours(mail.getActiveHours());

				if (mail.getAttachments() != null) {
					MailAttachments attachments = GameContextGlobals.getJsonConvertor().convert(mail.getAttachments(),
							MailAttachments.class);
					if (attachments != null)
						req.setAttachments(attachments.getAttachments());
				}

				req.setCategory(mail.getCategory());
				req.setContent(mail.getContent());
				// req.setMailId( id);
				UserAccount account = loginService.getUserAccountById(mail.getSender());
				if (account != null) {
					User user = G.o(User.class);
					user.setAvatar(account.getAvatar());
					user.setId(GameUtil.s2c(account.getId()));
					user.setLevel(account.getLevel());
					user.setUserName(account.getNickName());
					// user.setLevel(account.get);

					req.setFrom(user);
				}

				req.setMailId(id);
				req.setSentDateTime(CronUtil.getFormatedDate(new Date()));
				req.setTitle(mail.getTitle());
				req.setSeq(0);

				RouterHeader r = loginService.findRouteHeaderByUserId(userId);
				// r.setTranscode("PMS0002");
				WebsocketEvent.send(r, req, true);

				// set the mail out of the queue.
				if (mail.getCategory().intValue() < IMailService.MAIL_CATEOGRY_TRIGGER_NORMAL) {
					Mail oo = G.o(Mail.class);
					oo.setId(mail.getId());
					oo.setInQueueState(3);
					int rows = mailMapper.updateByPrimaryKeySelective(oo);
					if (rows > 0) {
						mailMap.remove(mail.getId());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// check if the user alread has the mail.

		return affectedRows > 0 ? true : false;
	}

	@Transactional
	public int filterMailTriggerTask() {
		// TODO Auto-generated method stub
		// batch of mail active and expired.
		int affectedRows = mailMapper.updateTriggerActive();
		int rows = mailMapper.updateTriggerExpired();
		if (affectedRows > 0)
			logger.info("active:" + affectedRows + " expired:" + rows);

		return affectedRows;
	}

	//
	// schedule the time task.
	// filter the
	// execute every hour.
	private static final Object lock = new Object();

	/**
	 * reset the mail in queue every 1hour.
	 * 
	 * 
	 */
	

	/**
	 * every 12 hour deactive mail.
	 */
	@Scheduled(fixedRate =   30 * 60 * 1000)
	public void scheduledDeactiveTask() {
		try {
			List<Long> ids = filterMailTimeTriggerExpresionDeactive();
			logger.info("mail  deactive:" + ids.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static long b;

	/**
	 * 
	 * make sure only one thread sync user.
	 * 
	 */
	List<UserAccountFriends> onlinUsers;

	/**
	 * 
	 * online notify will execute 5 seconds .
	 * 
	 */

	private final ReentrantLock mutex = new ReentrantLock();

	// private ConcurrentLinkedQueue<UserAccountFriends> processUserQueue=new
	// ConcurrentLinkedQueue<UserAccountFriends>();
	private static final ConcurrentHashMap<Long, UserAccountFriends> processingUserMap = new ConcurrentHashMap<Long, UserAccountFriends>();

	private void checkUserMail(UserAccountFriends accout) throws NumberFormatException, RemoteServiceException {
		notifyUserLogined(accout.getId(), accout.getLevel(),
				accout.getApkVersion() == null ? 0 : Integer.parseInt(accout.getApkVersion()), false);
		accout.setLastMailChecked(System.currentTimeMillis());
	}

	long resetTimestamp = 0;
	private static final long MAX_RESET_INTERVAL = 5 * 60 * 1000;

	@Scheduled(fixedRate = 1 * 1000)
	public void onlineUserNotify() {
		long b = System.currentTimeMillis();
		boolean shouldCheckUserMail = false;
		SeqUserAccountFriends users = null;
		try {

			mutex.lock();

			try {
				int affectedRows = 0;
				// update the mail
				affectedRows = filterMailTriggerTask();
				// load the mail into the queue.
				if (affectedRows > 0) {

					// TODO Auto-generated method stub
					try {
						// when a mail trun active,fetch the mail into the
						// queue.
						affectedRows = filterMailTriggerExrepsionActive();
						// affectedRows=mails.size();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				//
				// reset the mail queue state ,
				// that the queue flush
				if (System.currentTimeMillis() - resetTimestamp > MAX_RESET_INTERVAL) {
					affectedRows = resetMailState();
					resetTimestamp = System.currentTimeMillis();
				}

				//
				// if the queue flush or new mail in queue.
				// We should check the user mail.
				shouldCheckUserMail = affectedRows > 0 ? true : false;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//
			// if no mail ,why we get the online users?
			//
			if (!shouldCheckUserMail) {
				return;
			}
			try {
				users = loginService.getOnlineUserAccount();
				if (users == null) {
					resetTimestamp = 0;
					return;
				}
				onlinUsers = users.getAccounts();

				logger.info("user online:" + onlinUsers.size());
			} catch (LocalServiceException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}

			if (onlinUsers == null || onlinUsers.size() == 0) {
				return;
			}

			try {

				// if(processingUserMap.size()==0){
				int total = onlinUsers.size();
				int current = 0;
				for (final UserAccountFriends accout : onlinUsers) {

					// processingUserMap.get(key)

					if (!processingUserMap.containsKey(accout.getId())) {
						// logger.info("processing user:" + accout.getId() + " -
						// " + (current++) + "/" + total);
						// processingUserMap.put(accout.getId(), accout);
						checkUserMail(accout);
						processingUserMap.put(accout.getId(), accout);
						current++;

					} else {
						UserAccountFriends userAccount = processingUserMap.get(accout.getId());
						if (userAccount.shouldCheck()) {
							// logger.info("processing user:" + accout.getId() +
							// " - " + (current++) + "/" + total);
							checkUserMail(accout);
							current++;
						}

					}

					// }
				}

				logger.info("processing user  -  " + (current++) + "/" + total);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mutex.unlock();
			if (shouldCheckUserMail)
				logger.info(">>>>>>>>>>notify online  duration:" + (System.currentTimeMillis() - b));
		}

	}

	public boolean canSendFriendRequest(Long userId, Long friendId) throws RemoteServiceException {
		// TODO Auto-generated method stub
		boolean found = false;
		try {

			int size = mailMapper.sizeOfActiveFriendRequestMail(userId, friendId);
			found = size > 0 ? true : false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return !found;
	}

	public boolean deleteFriendsRequestMail(Long userId, SeqWrapper<Long> friendsId) throws RemoteServiceException {
		// TODO Auto-generated method stub
		boolean ret = false;
		try {
			List<Long> idSeq = GameUtil.getList();
			for (Long friendId : friendsId.getSeq()) {
				idSeq.add(friendId);
			}
			
			
			int affectedRows =mailMapper.deleteActiveFriendMailLogBySender(userId, idSeq);
			 affectedRows =mailMapper.deleteActiveFriendMailLogBySender(userId, idSeq);
				
				
			affectedRows=	mailMapper.deleteActiveFriendMailBySender(userId, idSeq);
			affectedRows = mailMapper.deleteActiveFriendMailByReciever(userId, idSeq);
			ret = affectedRows > 0 ? true : false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 
	 * 
	 */
	public FriendsIdSeq getFriendRequestSentIds(Long userId) throws RemoteServiceException {
		// TODO Auto-generated method stub
		FriendsIdSeq idSeq = G.o(FriendsIdSeq.class);
		List<Long> ids = GameUtil.getList();
		idSeq.setIds(ids);
		try {

			List<Mail> triggerExpresion = mailMapper.selectActiveFriendRequestMail(userId);
			// List<Long> mailIds = GameUtil.getList();

			for (Mail m : triggerExpresion) {
				ids.add(m.getReciever());

			}

			// delete.

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return idSeq;
	}

	/**
	 * Reset Mail should clean
	 * 
	 */
	@Transactional
	public boolean reset(Long userId) throws RemoteServiceException {
		// TODO Auto-generated method stub
		boolean ret = false;
		try {
			mailMapper.deleteBySenderOrRecieverId(userId);
			mailTriggerLogMapper.deleteBySenderOrRecieverId(userId);
			mailTriggerUserInformationMapper.deleteByUserId(userId);
			ret = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

}
