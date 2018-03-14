/**
 * 
 */
package com.toyo.fish.game.logic.friends.remote.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.SpringContext;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.EventHandlerClass;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.event.WebsocketEvent;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0001Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0001Response;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0002Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0002Response;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0003Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0003Response;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0004Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0004Response;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0005Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0005Response;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0006Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0006Response;
import com.toyo.fish.protocol.service.IFriendsService;
import com.toyo.fish.protocol.service.domain.FriendsRestriction;
import com.toyo.fish.protocol.service.domain.FriendsUser;
import com.toyo.fish.protocol.service.domain.FriendsUserSeq;

/**
 * @author sparrow
 *
 */
public class FriendsManagerRemote  implements IIdentifiedObject{
	
	private static final Log logger = LogFactory.getLog(FriendsManagerRemote.class);
	
	Long id;
	
	
	IFriendsService localFriendsService(){
		return SpringContext.getBean("IFriendsRemoteService");
	}


	public Long getId() {
		// TODO Auto-generated method stub
		return EventHandlerClass.DEFAULT_ID;
	}

	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id=id;
		
	}
	
	public FriendsManagerRemote() {
		super();
		// TODO Auto-generated constructor stub
		G.regeisterHandler(this, "PFS0001","PFS0002","PFS0003","PFS0004","PFS0005","PFS0006");
	}
	
	
	
	@RegisterEventHandler(transcode="PFS0001")
	public void handlePFS0001(WebsocketEvent evt) throws LocalServiceException{
		
		PFS0001Request req=G.evtAsObj(evt);
		PFS0001Response resp=G.o(PFS0001Response.class);
		
		FriendsUser user=localFriendsService().findByUserId(GameUtil.c2s(req.getSearchUserId()));
		resp.setState(0);
		if(user!=null){
			resp.setAvatar(user.getAvatar());
			resp.setLevel(user.getLevel()==null?0:user.getLevel());
			resp.setNickName(user.getNickName());
			resp.setId(user.getUserId()==null?0:GameUtil.s2c(user.getUserId()));
			resp.setState(user.getUserId()!=null?1:0);
		}
		
		
		resp.setSeq(req.getSeq());
		
		WebsocketEvent.send(evt.header, resp, false);
		
	}
	
	
	@RegisterEventHandler(transcode="PFS0002")
	public void handlePFS0002(WebsocketEvent evt) throws LocalServiceException{

		PFS0002Request req=G.evtAsObj(evt);
		PFS0002Response resp=G.o(PFS0002Response.class);
		boolean ret=localFriendsService().sendFriendsMail(req.getId(),GameUtil.c2s( req.getUserId()), req.getActionType());
		
		
		resp.setState(ret?1:0);
		resp.setSeq(req.getSeq());
		WebsocketEvent.send(evt.header, resp, false);
		
	}
	
	@RegisterEventHandler(transcode="PFS0003")
	public void handlePFS0003(WebsocketEvent evt) throws LocalServiceException{

		PFS0003Request req=G.evtAsObj(evt);
		PFS0003Response resp=G.o(PFS0003Response.class);
		boolean ret=false;
		FriendsUserSeq item=localFriendsService().getFriendsSeq(req.getId(), req.getOffset(), req.getLength());
		if(item!=null){
			resp.setTotal(item.getTotal());
			resp.setFriends(item.getFriends());
			ret=true;
		}
		resp.setState(ret?1:0);
		resp.setSeq(req.getSeq());
		WebsocketEvent.send(evt.header, resp, false);
		
	}
	
	@RegisterEventHandler(transcode="PFS0004")
	public void handlePFS0004(WebsocketEvent evt) throws LocalServiceException{

		PFS0004Request req=G.evtAsObj(evt);
		PFS0004Response resp=G.o(PFS0004Response.class);
		boolean ret=false;
		List<FriendsUser> items=localFriendsService().getRecommendFriendsUser(req.getId());
		if(items!=null){
			resp.setFriends(items);
			ret=true;
		}
		resp.setState(ret?1:0);
		resp.setSeq(req.getSeq());
		
		
		WebsocketEvent.send(evt.header, resp, false);
		
	}
	
	
	@RegisterEventHandler(transcode="PFS0005")
	public void handlePFS0005(WebsocketEvent evt) throws LocalServiceException{
		
		PFS0005Request req=G.evtAsObj(evt);
		PFS0005Response resp=G.o(PFS0005Response.class);
		boolean ret=false;
		
		List<Long> friendIds=GameUtil.c2s(req.getFriendIds());
		ret=localFriendsService().removeFriends(req.getId(),friendIds );
		
		
		resp.setState(ret?1:0);
		resp.setSeq(req.getSeq());
		
		
		WebsocketEvent.send(evt.header, resp, false);
		
	}
	
	@RegisterEventHandler(transcode="PFS0006")
	public void handlePFS0006(WebsocketEvent evt) throws LocalServiceException{

		PFS0006Request req=G.evtAsObj(evt);
		PFS0006Response resp=G.o(PFS0006Response.class);
		
		boolean ret=false;
		
		FriendsRestriction item=localFriendsService().getFriendsRestriction(req.getId());
		
		if(item!=null){
			ret=true;
			resp.setCurrentFriendsRequest(item.getCurrentFriendsRequest());
			resp.setCurrentFriends(item.getCurrentFriends());
			resp.setCurrentGetVit(item.getCurrentGetVit());
			resp.setCurrentGiveVit(item.getCurrentGiveVit());
			
			resp.setMaxFriends(item.getMaxFriends());
			resp.setMaxFriendsRequest(item.getMaxFriendsRequest());
			resp.setMaxGetVit(item.getMaxGetVit());
			resp.setMaxGiveVit(item.getMaxGiveVit());
		}
		
		
		
		resp.setState(ret?1:0);
		resp.setSeq(req.getSeq());
		
		WebsocketEvent.send(evt.header, resp, false);
		
	}
	

}
