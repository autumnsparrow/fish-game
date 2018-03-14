/**
 * 
 */
package com.toyo.fish.game.logic.mail.remote.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.SpringContext;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.EventHandlerClass;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.event.WebsocketEvent;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.spring.ice.SeqWrapper;
import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.data.wrapper.mail.domain.MailTriggerLog;

import com.toyo.fish.protocol.beans.PMS0000Beans.MailObj;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0001Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0001Response;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0003Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0003Response;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0004Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0004Response;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0005Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0005Response;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0006Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0006Response;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0007Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0007Response;
import com.toyo.fish.protocol.beans.PMS0000Beans.User;
import com.toyo.fish.protocol.service.IMailService;
import com.toyo.fish.protocol.service.domain.MailAttachments;
import com.toyo.fish.protocol.service.domain.MailIdSeq;
import com.toyo.fish.protocol.service.domain.MailTriggerExpresion;
import com.toyo.remote.service.user.ILoginService;
import com.toyo.remote.service.user.SeqUserAccount;

/**
 * @author sparrow
 *
 */
public class MailManagerRemote  implements IIdentifiedObject{
	
	
	private static final Log logger = LogFactory.getLog(MailManagerRemote.class);
	
	Long id;
	
	
	IMailService localMailService(){
		
		return SpringContext.getBean("IMailRemoteService");
	}
	ILoginService remoteLoginService(){
		return SpringContext.getBean("ILoginService");
	}

	public MailManagerRemote() {
		super();
		// TODO Auto-generated constructor stub
		G.regeisterHandler(this, "PMS0001","PMS0003","PMS0004","PMS0005","PMS0006","PMS0007");
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return EventHandlerClass.DEFAULT_ID;
	}

	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id=id;
		
	}
	
	
	@RegisterEventHandler(transcode="PMS0001")
	public void handlePMS0001(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		
		PMS0001Request req=G.evtAsObj(evt);
		PMS0001Response resp=G.o(PMS0001Response.class);
		
		//List<Long> idSeq=req.getTo()==null?null:GameUtil.c2s(req.getTo());
		//Long to=req.getTo()==null?null:GameUtil.c2s(ids)
		
		MailTriggerExpresion e=G.o(MailTriggerExpresion.class);

		Long to=null;
		
//		if(idSeq!=null&&idSeq.size()>0){
//			to=idSeq.get(0);
//		}else{
//			e.setApkVersion(false);
//			e.setLoginAfterNDays(0);
//			e.setUserIds(idSeq);
//		}
		
		String expression=GameContextGlobals.getJsonConvertor().format(e);
		Long mailId=null;
		long duration=1000*60*60*24*30;
		mailId=localMailService().createMailWithUserAndTimeTrigger(req.getId(),to, req.getTitle(),  req.getContent(), req.getCategory(),String.valueOf(duration), req.getActiveHours(), req.getAttachments(),expression );
		
		
		resp.setSeq(req.getSeq());
		if(mailId!=null){
			resp.setState(1);
			resp.setMailId(mailId);
		}else{
			resp.setState(0);
		}
		
		WebsocketEvent.send(evt.header, resp, false);
	}
	
	
//
//	@RegisterEventHandler(transcode="PMS0002")
//	public void handlePMS0002(WebsocketEvent evt) throws LocalServiceException{
////		WebsocketEvent.send(evt.header, resp, false);
//	}
//	
	
	@RegisterEventHandler(transcode="PMS0003")
	public void handlePMS0003(WebsocketEvent evt) throws LocalServiceException{
		PMS0003Request req=G.evtAsObj(evt);
		PMS0003Response resp=G.o(PMS0003Response.class);
		int state=req.getState()>0?req.getState():0;
		boolean ret=localMailService().updateMailTriggerExpresion(req.getId(),req.getMailId(),state);
		resp.setState(ret?1:0);
		resp.setSeq(req.getSeq());
		WebsocketEvent.send(evt.header, resp, false);
	}
	
	
	@RegisterEventHandler(transcode="PMS0004")
	public void handlePMS0004(WebsocketEvent evt) throws LocalServiceException{
		
		
//		WebsocketEvent.send(evt.header, resp, false);
		
		PMS0004Request req=G.evtAsObj(evt);
		PMS0004Response resp=G.o(PMS0004Response.class);
		
		MailIdSeq mailIdSeq=localMailService().getMailIdByRangeAndState(req.getId(), req.getCategory(), req.getState(), req.getOffset(), req.getLength());
		
		resp.setIds(mailIdSeq.getIds());
		resp.setTotal(mailIdSeq.getTotal());
		resp.setState(1);
		resp.setSeq(req.getSeq());
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	
	@RegisterEventHandler(transcode="PMS0005")
	public void handlePMS0005(WebsocketEvent evt) throws LocalServiceException{
//		WebsocketEvent.send(evt.header, resp, false);
		
		PMS0005Request req=G.evtAsObj(evt);
		PMS0005Response resp=G.o(PMS0005Response.class);
		List<MailObj> objs=GameUtil.getList();
		
		List<MailTriggerLog> items=localMailService().getMailsByIdSeqs(req.getId(), req.getIds());
		logger.info("item - "+items.size());
		if(items.size()>0){
		List<Long> userIds=GameUtil.getList();
		Map<Long,Integer> idMaps=GameUtil.getMap();
		for(MailTriggerLog item:items){
			if(item.getMail()!=null){
			if(!idMaps.containsKey(item.getMail().getSender())){
				userIds.add(item.getMail().getSender());
				idMaps.put(item.getMail().getSender(), Integer.valueOf(0));
			}
			}
		}
		Map<Long,User> accoutsMap=GameUtil.getMap();
	
		SeqUserAccount accounts=remoteLoginService().getUserAccount(userIds);
		if(accounts!=null&&accounts.getAccounts()!=null){
		for(UserAccount a:accounts.getAccounts()){
			User u=G.o(User.class);
			u.setAvatar(a.getAvatar());
			u.setId(GameUtil.s2c(a.getId()));
			u.setUserName(a.getNickName());
			u.setLevel(a.getLevel());
			accoutsMap.put(a.getId(), u);
		}
		
		
		
		for(MailTriggerLog item:items){
			if(item.getMail()==null)
				continue;
			MailObj o=G.o(MailObj.class);
			o.setActiveDateTime(item.getMail().getTimeTriggerExpression());
			o.setActiveHours(item.getMail().getActiveHours());
			if(item.getMail().getAttachments()!=null){
				MailAttachments attachments=GameContextGlobals.getJsonConvertor().convert(item.getMail().getAttachments(), MailAttachments.class);
				if(attachments!=null)
				o.setAttachments(attachments.getAttachments());
			}
			o.setCategory(item.getCategory());
			o.setContent(item.getMail().getContent());
			
			o.setFrom(accoutsMap.get(item.getMail().getSender()));
			o.setSentDateTime(CronUtil.getFormatedDate(item.getCreateTime()));
			o.setMailId(item.getId());
			o.setTitle(item.getMail().getTitle());
			objs.add(o);
		}
		
		}
		}
		resp.setMails(objs);
	
		resp.setState(1);
		resp.setSeq(req.getSeq());
		
		WebsocketEvent.send(evt.header, resp, false);
	}
	
	
	@RegisterEventHandler(transcode="PMS0006")
	public void handlePMS0006(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		
		PMS0006Request req=G.evtAsObj(evt);
		PMS0006Response resp=G.o(PMS0006Response.class);
		
		
		
		String expression=req.getTrigger()==null?"":GameContextGlobals.getJsonConvertor().format(req.getTrigger());
		String attachments=req.getAttachments()==null?"":GameContextGlobals.getJsonConvertor().format(req.getAttachments());
		Long mailId=null;
		mailId=localMailService().createMailWithUserAndTimeTrigger(req.getId(), req.getUserId(),req.getTitle(),  req.getContent(), req.getCategory(),req.getActiveDateTime(), req.getActiveHours(), attachments,expression );
		
		
		resp.setSeq(req.getSeq());
		
		if(mailId!=null){
			resp.setState(1);
			resp.setMailId(mailId);
		}else{
			resp.setState(0);
		}
		
		WebsocketEvent.send(evt.header, resp, false);
	}
	
	

	@RegisterEventHandler(transcode="PMS0007")
	public void handlePMS0007(WebsocketEvent evt) throws LocalServiceException{
		PMS0007Request req=G.evtAsObj(evt);
		PMS0007Response resp=G.o(PMS0007Response.class);
		
		boolean ret=false;
		List<Long> failedIdSeq=GameUtil.getList();
		int count=0;
		for(Long id:req.getMailId()){
			ret=localMailService().updateMailTriggerExpresion(req.getId(),id,req.getState());
			count=ret?count+1:count;
			if(!ret)
				failedIdSeq.add(id);
		}
		resp.setState(failedIdSeq.size()==0?1:0);
		resp.setFailedMailId(failedIdSeq);
		resp.setSeq(req.getSeq());
		WebsocketEvent.send(evt.header, resp, false);
	}
	
	
	
}
