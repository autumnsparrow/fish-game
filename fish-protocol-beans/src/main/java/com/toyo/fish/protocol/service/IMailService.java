/**
 * 
 */
package com.toyo.fish.protocol.service;

import java.util.List;

import com.sky.game.context.event.LocalServiceException;
import com.toyo.fish.data.wrapper.mail.domain.Mail;
import com.toyo.fish.data.wrapper.mail.domain.MailTriggerLog;
import com.toyo.fish.protocol.service.domain.MailAttachment;
import com.toyo.fish.protocol.service.domain.MailIdSeq;
import com.toyo.fish.protocol.service.domain.MailTriggerExpresion;
import com.toyo.remote.service.mail.IMailRemoteService;

/**
 * 
 * 
 * Mail (Message ) System.
 * 
 * 
 * @author sparrow
 *
 */
public interface IMailService  extends IMailRemoteService{
	

	
	public static final int MAIL_OPEN=1;
	
	public static final int MAIL_CATEGORY_NORMAL=0;
	public static final int MAIL_CATEOGRY_VIT=1;
	public static final int MAIL_CATEOGRY_FRIENDS_REQUEST=2;
	public static final int MAIL_CATEGORY_ALREADY_BE_FRIENDS=3;
	public static final int MAIL_CATEOGRY_CLIENT_FEEDBACK=4;
	public static final int MAIL_CATEGRORY_ATTACHMENT_ITEMS=5;
	
	//
	// Using the Trigger .
	//
	
	public static final int MAIL_CATEOGRY_TRIGGER_NORMAL=6;
	public static final int MAIL_CATEGRORY_TRIGGER_ITEMS=7;

	
	
	
	public static final int MAIL_VIT_GET=2;
	public static final int MAIL_FRIENDS_REQUEST_CANCEL=-1;
	public static final int MAIL_FRIENDS_REQUEST_CONFIRM=2;
	
	
	
	//
	// API : mobile client and server api.
	//
	

	
	/**
	 * get mail trigger expresion by mail id(push the new mail).
	 * @param mailId
	 * @return
	 * @throws LocalServiceException
	 */
	public MailTriggerLog getMailTriggerExresionByMailId(Long mailId) throws LocalServiceException;
	
	/**
	 * update the mail trigger expression
	 * @param mailId
	 * @param state
	 * @throws LocalServiceException
	 * @return
	 */
	public boolean updateMailTriggerExpresion(Long userId,Long mailId,int state) throws LocalServiceException;
	
	
	/**
	 * get the mail the by range and state
	 * @param userId
	 * @param category
	 * @param offset
	 * @param length
	 * @throws LocalServiceException
	 * @return {@link MailIdSeq}
	 */
	public MailIdSeq getMailIdByRangeAndState(Long userId,int category,int state,int offset,int length) throws LocalServiceException;
	
	/**
	 * 
	 * @param userId
	 * @param idSeqs
	 * @return
	 * @throws LocalServiceException
	 */
	public List<MailTriggerLog> getMailsByIdSeqs(Long userId,List<Long> idSeqs) throws LocalServiceException;

	
	
	//
	// Schedule Task API( should not be exposed)
	//
	
	public int filterMailTriggerTask();
	
	/**
	 * 
	 * @return
	 * @throws LocalServiceException
	 */
	public int filterMailTriggerExrepsionActive() throws LocalServiceException;
	
	
	/**
	 * the mail should update the cleanup_state.
	 * the MailTriggerLog should delete the log of related mailId.
	 * 
	 * @return
	 * @throws LocalServiceException
	 */
	public List<Long> filterMailTimeTriggerExpresionDeactive()throws LocalServiceException;
	
	
	/**
	 * if the user login and expression triggers,then add a new record.
	 * 
	 * that method should be a remote method.
	 * @param userId
	 * @param mailId
	 * @return
	 * @throws LocalServiceException
	 */
	public boolean mailTriggers(Long userId,Long mailId) throws LocalServiceException;
	
	
	
	
	
	//
	//schedule task
	//public  void scheduleMailTask();
	//public void scheduleTask();
	public void scheduledDeactiveTask() ;
	//public  void syncOnlineUsersTask();
	 public void onlineUserNotify();

}
