package com.toyo.fish.protocol.beans;

import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.protocol.beans.store.StoreItem;
import com.toyo.fish.protocol.service.domain.MailAttachment;
import com.toyo.fish.protocol.service.domain.MailAttachments;
import com.toyo.fish.protocol.service.domain.MailTriggerExpresion;
import com.toyo.remote.service.payment.IPaymentService;
import com.toyo.remote.service.payment.domain.HuaweiConf;
import com.toyo.remote.service.payment.domain.PaymentOrder;
import com.toyo.remote.service.payment.domain.VivoOrder;

/**
 * 
 * Protocol System.
 * 
 * 
 * 
 * @author sparrow
 *
 */
public class PMS0000Beans {

	/**
	 * Extra Object of User Protocol
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerNamespaceExtraType(namespace = "ProtocolMailSystem")
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
	
	@HandlerRequestType(transcode="PMS0001")
	public static class PMS0001Request extends BaseObj{
		String title;
		String content;
		int category;
		String activeDateTime;
		int activeHours;
		String attachments;
		List<Long> to;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public int getCategory() {
			return category;
		}
		public void setCategory(int category) {
			this.category = category;
		}
		public String getActiveDateTime() {
			return activeDateTime;
		}
		public void setActiveDateTime(String activeDateTime) {
			this.activeDateTime = activeDateTime;
		}
		public int getActiveHours() {
			return activeHours;
		}
		public void setActiveHours(int activeHours) {
			this.activeHours = activeHours;
		}
		public String getAttachments() {
			return attachments;
		}
		public void setAttachments(String attachments) {
			this.attachments = attachments;
		}
		public List<Long> getTo() {
			return to;
		}
		public void setTo(List<Long> to) {
			this.to = to;
		}
		
		
	}
	
	@HandlerResponseType(transcode="PMS0001",responsecode="SMP0001")
	public static class PMS0001Response extends SeqObj {
		int state;
		long mailId;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public long getMailId() {
			return mailId;
		}
		public void setMailId(long mailId) {
			this.mailId = mailId;
		}
		
	}
	
	public static class User{
		
		long id;
		String userName;
		String avatar;
		int level;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
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
		
		
		
	};
	
	/**
	 * Recieved Push mail
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PMS0002")
	public static class PMS0002Request extends SeqObj{
		String title;
		String content;
		int category;
		String activeDateTime;
		int activeHours;
		List<MailAttachment> attachments;
		User from;
		Long mailId;
		String sentDateTime;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public int getCategory() {
			return category;
		}
		public void setCategory(int category) {
			this.category = category;
		}
		public String getActiveDateTime() {
			return activeDateTime;
		}
		public void setActiveDateTime(String activeDateTime) {
			this.activeDateTime = activeDateTime;
		}
		public int getActiveHours() {
			return activeHours;
		}
		public void setActiveHours(int activeHours) {
			this.activeHours = activeHours;
		}
		public List<MailAttachment> getAttachments() {
			return attachments;
		}
		public void setAttachments(List<MailAttachment> attachments) {
			this.attachments = attachments;
		}
		public User getFrom() {
			return from;
		}
		public void setFrom(User from) {
			this.from = from;
		}
		public Long getMailId() {
			return mailId;
		}
		public void setMailId(Long mailId) {
			this.mailId = mailId;
		}
		public String getSentDateTime() {
			return sentDateTime;
		}
		public void setSentDateTime(String sentDateTime) {
			this.sentDateTime = sentDateTime;
		}
		
		
		
		
	}
	
	/**
	 * update mail state
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PMS0003")
	public static class PMS0003Request extends BaseObj{
		Long mailId;
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public Long getMailId() {
			return mailId;
		}

		public void setMailId(Long mailId) {
			this.mailId = mailId;
		}
		
		
	}
	
	@HandlerResponseType(transcode="PMS0003",responsecode="SMP0003")
	public static class PMS0003Response extends SeqObj{
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
		
		
	}
	
	@HandlerRequestType(transcode="PMS0004")
	public static class PMS0004Request extends BaseObj{
		int state;
		int category;
		int offset;
		int length;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public int getCategory() {
			return category;
		}
		public void setCategory(int category) {
			this.category = category;
		}
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
	
	@HandlerResponseType(transcode="PMS0004",responsecode="SMP0004")
	public static class PMS0004Response extends SeqObj{
		int state;
		int total;
		List<Long> ids;
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
		public List<Long> getIds() {
			return ids;
		}
		public void setIds(List<Long> ids) {
			this.ids = ids;
		}
		
 	}
	
	@HandlerRequestType(transcode="PMS0005")
	public static class PMS0005Request extends BaseObj{
		List<Long> ids;

		public List<Long> getIds() {
			return ids;
		}

		public void setIds(List<Long> ids) {
			this.ids = ids;
		}
		
	}
	@HandlerResponseType(transcode="PMS0005",responsecode="SMP0005")
	public static class PMS0005Response extends SeqObj{
		int state;
		List<MailObj> mails;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public List<MailObj> getMails() {
			return mails;
		}
		public void setMails(List<MailObj> mails) {
			this.mails = mails;
		}
		
	}
	
	public static class MailObj{
		String title;
		String content;
		int category;
		String activeDateTime;
		int activeHours;
		List<MailAttachment> attachments;
		User from;
		Long mailId;
		String sentDateTime;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public int getCategory() {
			return category;
		}
		public void setCategory(int category) {
			this.category = category;
		}
		public String getActiveDateTime() {
			return activeDateTime;
		}
		public void setActiveDateTime(String activeDateTime) {
			this.activeDateTime = activeDateTime;
		}
		public int getActiveHours() {
			return activeHours;
		}
		public void setActiveHours(int activeHours) {
			this.activeHours = activeHours;
		}
		public List<MailAttachment> getAttachments() {
			return attachments;
		}
		public void setAttachments(List<MailAttachment> attachments) {
			this.attachments = attachments;
		}
		public User getFrom() {
			return from;
		}
		public void setFrom(User from) {
			this.from = from;
		}
		public Long getMailId() {
			return mailId;
		}
		public void setMailId(Long mailId) {
			this.mailId = mailId;
		}
		public String getSentDateTime() {
			return sentDateTime;
		}
		public void setSentDateTime(String sentDateTime) {
			this.sentDateTime = sentDateTime;
		}
		
		
		
		
	}
	
	
	

	@HandlerRequestType(transcode="PMS0006")
	public static class PMS0006Request extends BaseObj{
		String title;
		String content;
		int category;
		String activeDateTime;
		int activeHours;
		Long userId;
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		MailTriggerExpresion trigger;
		MailAttachments attachments;
		
		public MailTriggerExpresion getTrigger() {
			return trigger;
		}
		public void setTrigger(MailTriggerExpresion trigger) {
			this.trigger = trigger;
		}
		public MailAttachments getAttachments() {
			return attachments;
		}
		public void setAttachments(MailAttachments attachments) {
			this.attachments = attachments;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public int getCategory() {
			return category;
		}
		public void setCategory(int category) {
			this.category = category;
		}
		public String getActiveDateTime() {
			return activeDateTime;
		}
		public void setActiveDateTime(String activeDateTime) {
			this.activeDateTime = activeDateTime;
		}
		public int getActiveHours() {
			return activeHours;
		}
		public void setActiveHours(int activeHours) {
			this.activeHours = activeHours;
		}
		
		
		
	}
	
	@HandlerResponseType(transcode="PMS0006",responsecode="SMP0006")
	public static class PMS0006Response extends SeqObj {
		int state;
		long mailId;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public long getMailId() {
			return mailId;
		}
		public void setMailId(long mailId) {
			this.mailId = mailId;
		}
		
	}
	
	
	/**
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="PMS0007")
	public static class PMS0007Request extends BaseObj{
		List<Long> mailId;
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public List<Long> getMailId() {
			return mailId;
		}

		public void setMailId(List<Long> mailId) {
			this.mailId = mailId;
		}

		
		
	}
	
	@HandlerResponseType(transcode="PMS0007",responsecode="SMP0007")
	public static class PMS0007Response extends SeqObj{
		
		List<Long> failedMailId;
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public List<Long> getFailedMailId() {
			return failedMailId;
		}

		public void setFailedMailId(List<Long> failedMailId) {
			this.failedMailId = failedMailId;
		}
		
		
		
	}
	
	

}
