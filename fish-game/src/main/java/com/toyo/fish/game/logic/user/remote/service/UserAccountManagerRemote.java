/**
 * 
 */
package com.toyo.fish.game.logic.user.remote.service;




import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeanUtils;

import com.sky.game.context.SpringContext;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.EventHandlerClass;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.domain.BrokerMessageBeans;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.event.WebsocketEvent;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.BeanUtil;
import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeGift;
import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeItem;
import com.toyo.fish.game.logic.handler.RemoteObjectCache;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0001Request;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0001Response;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0002Request;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0002Response;
import com.toyo.fish.protocol.beans.PEC0000Beans.PECItem;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0000Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0002Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0002Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0003Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0003Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0004Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0004Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0005Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0005Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0006Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0006Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0007Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0007Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0008Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0008Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0009Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0009Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0010Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0010Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0011Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0011Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0012Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0012Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0013Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0013Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0014Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0014Response;
import com.toyo.fish.protocol.beans.PU0000Beans.User;
import com.toyo.fish.protocol.beans.user.UserAccountInfo;
import com.toyo.fish.protocol.service.IUserAccount;
import com.toyo.fish.protocol.service.IUserAccountManager;
import com.toyo.remote.service.user.ILoginService;



/**
 * @author sparrow
 *
 */

public class UserAccountManagerRemote implements IIdentifiedObject{
	
	
	
	IUserAccountManager userAccountManager;
	
	
	public IUserAccountManager getUserAccountManager() {
		return userAccountManager;
	}

	public UserAccountManagerRemote() {
		super();
		userAccountManager=SpringContext.getBean("IUserAccountManager");
		// TODO Auto-generated constructor stub
		G.regeisterHandler(this, "PU0001","PU0002",
		"PU0003","PU0004","PU0005","PU0006","PU0007","PU0008","PU0009","PU0010","PU0011","PU0012","PU0013","PU0014","PEC0001","PEC0002");
		
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return EventHandlerClass.DEFAULT_ID;
	}

	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	
	//ConcurrentHashMap<Long, UserAccountRemote> userAccountRemotes=new ConcurrentHashMap<Long, UserAccountRemote>();
	
	
	
	
	//
	// Remote Login Service.
	//
	
	
	
	//
	// Remote Service
	//
	
//	@RegisterEventHandler(transcode="PU0000")
//	public void handlePU0000(WebsocketEvent evt){
//		PU0000Request req=G.evtAsObj(evt);
//		
//		// checking if the user alread exist.
//		try {
//			UserAccountRemote remote=RemoteObjectCache.getObject(req.getAccount().getId(), UserAccountRemote.class);
//			
//		} catch (LocalServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			// if can't find the remote object.
//			//req.getAccount().getHeader().set
//			// no use don't need to send again.
//			GameEventHandler.handler.broadcast(new GameEvent(GameEvent.EVENT_USER_LOADED,req.getAccount()));
//			
//		}
//		
//		
//		
//	}
	
	/**
	 * 
	 * 
	 * 
	 * @param evt
	 */
	@RegisterEventHandler(transcode="PU0001")
	public void handlePU0001(WebsocketEvent evt){
		PU0001Request req=G.evtAsObj(evt);
		// broadcast the message.
		//BrokerMessageBeans.broadcast("", "", "", obj);
		
		
		PU0001Response resp=G.o(PU0001Response.class);
		UserAccount account=null;
		try {
			
			account=userAccountManager.getUserAccount(req.getImei(), req.getChannel());
			
			
			resp.setState(1);
			String token="";//BeanUtil.v(info, "login.token");
			
			String userName=account.getUserName();
			resp.setToken(token);
			resp.setUserId(account.getId());
			resp.setUserName(userName);
			
			evt.header.setId(account.getId());
			evt.header.setToken(token);
			//info.setHeader(evt.header);
			
			GameEventHandler.handler.broadcast(new GameEvent(GameEvent.EVENT_USER_LOADED,account));
			//new UserAccountRemote(info.getId(),info);
		
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setState(e.state);
			resp.setToken("");
			resp.setUserId(-1);
			resp.setUserName("");
			
		}
		
		
	
		
		
		if(account!=null){
			evt.token=resp.getToken();
			WebsocketEvent.send(evt.header, resp, false);
			
		}
		
		
		
		
	}
	
	
	/**
	 * 
	 * 
	 * @param evt
	 */
	@RegisterEventHandler(transcode="PU0002")
	public void handlePU0002(WebsocketEvent evt){
		PU0002Request req=G.evtAsObj(evt);
		PU0002Response resp=G.o(PU0002Response.class);
		
		UserAccount info=null;
		try {
			info=userAccountManager.getUserAccount(req.getImei(), req.getUserName(), req.getPassWord());
			
			
			
			resp.setState(1);
			String token="";
			resp.setToken(token);
			resp.setMsg("OK");
			
			evt.header.setId(info.getId());
			evt.header.setToken(token);
		//	info.setHeader(evt.header);
			
			GameEventHandler.handler.broadcast(new GameEvent(GameEvent.EVENT_USER_LOADED,info));
			
			
			
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setState(e.state);
			resp.setToken("");
			resp.setMsg(e.getMessage());
			
		}
		
		
		
		if(info!=null){
			evt.token=resp.getToken();
			WebsocketEvent.send(evt.header, resp, false);
		}
		
		
	}
	
	
	
	/**
	 * 
	 * 
	 * @param evt
	 * {@link PU0003Request}
	 * {@link PU0003Response}
	 * {@link IUserAccount#bindThirdpartyAccount(Long, String)}
	 * @throws LocalServiceException 
	 * 
	 * 
	 */
	@RegisterEventHandler(transcode="PU0003")
	public void handlePU0003(WebsocketEvent evt) throws LocalServiceException{
		
		PU0003Request req=G.evtAsObj(evt);
		PU0003Response resp=G.o(PU0003Response.class);
		
		boolean ret=false;
		try {
			ret=userAccountManager.bindThirdpartyAccount(req.getId(), req.getAccount());
			resp.setState(ret?1:0);
			resp.setMessage("Ok");
			resp.setUserId(req.getId());
			//String token=BeanUtil.v(o, e)
			String token="";
			resp.setToken(token);
			
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			
		}
		
		
		
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	
	
	/**
	 * 
	 * 
	 * @param evt
	 * 
	 * {@link IUserAccount#bindPhone(Long, String, String, String)}
	 * @throws LocalServiceException 
	 */
	@RegisterEventHandler(transcode="PU0004")
	public void handlePU0004(WebsocketEvent evt) throws LocalServiceException{
		PU0004Request req=G.evtAsObj(evt);
		PU0004Response resp=G.o(PU0004Response.class);
		
		
		try {
			
			userAccountManager.
					bindPhone(req.getId(), req.getUserName(), req.getPassWord());
			resp.setState(1);
			resp.setMsg("");
			
			resp.setToken("");
			resp.setUserId(req.getId());
			
					
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw e;
			
		}
		
		
		//evt.token=resp.getToken();
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	
	
	/**
	 * 
	 * @param evt
	 * 
	 * {@link IUserAccountManager#sendValidCode(Long, String)}
	 * @throws LocalServiceException 
	 * @see PU0005Request
	 * @see PU0005Response
	 */
	@RegisterEventHandler(transcode="PU0005")
	public void handlePU0005(WebsocketEvent evt) throws LocalServiceException{
		
		PU0005Request req=G.evtAsObj(evt);
		PU0005Response resp=G.o(PU0005Response.class);
		
		
		try {
			boolean ret=userAccountManager.sendValidCode(req.getId(), req.getPhone());
			
			resp.setState(ret?1:0);
			
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			//resp.setState(e.state);
		}
		

		WebsocketEvent.send(evt.header, resp, false);
		
		
		
		
	}
	
	
	
	/**
	 * 
	 * @param evt
	 * @throws LocalServiceException 
	 * @see PU0006Request
	 * @see PU0006Response
	 * 
	 */
	@RegisterEventHandler(transcode="PU0006")
	public void handlePU0006(WebsocketEvent evt) throws LocalServiceException{
		PU0006Request req=G.evtAsObj(evt);
		PU0006Response resp=G.o(PU0006Response.class);
		
		try {
			
			boolean ret=userAccountManager.modifyPassword(req.getId(), req.getPassword());
			resp.setState(ret?1:0);
			resp.setMsg("ok");
		
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
		
		
	}



	/**
	 * 
	 * @param evt
	 * @throws LocalServiceException 
	 * @see PU0006Request
	 * @see PU0006Response
	 * 
	 */
	@RegisterEventHandler(transcode="PU0007")
	public void handlePU0007(WebsocketEvent evt) throws LocalServiceException{
		PU0007Request req=G.evtAsObj(evt);
		PU0007Response resp=G.o(PU0007Response.class);
		
		try {
			
			boolean ret=userAccountManager.validCode(req.getId(), req.getValidcode());
			resp.setState(ret?1:0);
			resp.setMsg("");
		
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
		
		
	}
	
	
	
	/**
	 * 
	 * @param evt
	 * @throws LocalServiceException 
	 * @see PU0006Request
	 * @see PU0006Response
	 * 
	 */
	@RegisterEventHandler(transcode="PU0008")
	public void handlePU0008(WebsocketEvent evt) throws LocalServiceException{
		PU0008Request req=G.evtAsObj(evt);
		PU0008Response resp=G.o(PU0008Response.class);
		
		try {
			boolean ret=userAccountManager.updateAvatar(req.getId(), req.getAvatar());
			//userAccountManager.validCode(id, req.getValidcode());
			resp.setState(ret?1:0);
			//resp.setMsg("ok");
		
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
		
		
	}
	
	
	/**
	 * 
	 * @param evt
	 * @throws LocalServiceException 
	 * @see PU0006Request
	 * @see PU0006Response
	 * 
	 */
	@RegisterEventHandler(transcode="PU0009")
	public void handlePU0009(WebsocketEvent evt) throws LocalServiceException{
		PU0009Request req=G.evtAsObj(evt);
		PU0009Response resp=G.o(PU0009Response.class);
		
		try {
		
			boolean ret=userAccountManager.updateNickname(req.getId(), req.getNickName());
			//userAccountManager.validCode(id, req.getValidcode());
			resp.setState(ret?1:0);
			//resp.setMsg("ok");
		
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
		
		
	}
	
	ILoginService localLoginService(){
		return SpringContext.getBean("ILoginService");
	}
	
	/**
	 * 
	 * @param evt
	 * @throws LocalServiceException 
	 * @see PU0006Request
	 * @see PU0006Response
	 * 
	 */
	@RegisterEventHandler(transcode="PU0010")
	public void handlePU0010(WebsocketEvent evt) throws LocalServiceException{
		PU0010Request req=G.evtAsObj(evt);
		PU0010Response resp=G.o(PU0010Response.class);
		
		try {
		
			boolean ret=localLoginService().updateUserLevel(req.getId(), req.getLevel());
			
			resp.setState(ret?1:0);
			
		
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
		
		
	}
	
	
	/**
	 * 
	 * @param evt
	 * @throws LocalServiceException 
	 * @see PU0006Request
	 * @see PU0006Response
	 * 
	 */
	@RegisterEventHandler(transcode="PU0011")
	public void handlePU0011(WebsocketEvent evt) throws LocalServiceException{
		PU0011Request req=G.evtAsObj(evt);
		PU0011Response resp=G.o(PU0011Response.class);
		
		try {
		
			
			int affectedRows=userAccountManager.updateUserBasicInformation(req.getId(), req.getAvatar(), req.getNickName(), req.getLevel());
			
			resp.setState(affectedRows);
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LocalServiceException(-1,"-  "+e.getMessage());
		}
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
		
		
	}
	
	
	/**
	 * 
	 * @param evt
	 * @throws LocalServiceException
	 */
	@RegisterEventHandler(transcode="PU0012")
	public void handlePU0012(WebsocketEvent evt) throws LocalServiceException{
		PU0012Request req=G.evtAsObj(evt);
		PU0012Response resp=G.o(PU0012Response.class);
		
		try {
		
			
			userAccountManager.resetAccount(req.getId());
			
			resp.setState(1);
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new LocalServiceException(-1,"-  "+e.getMessage());
		}
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
		
		
	}
	
	
	@RegisterEventHandler(transcode="PU0013")
	public void handlePU0013(WebsocketEvent evt) throws LocalServiceException{
		PU0013Request req=G.evtAsObj(evt);
		PU0013Response resp=G.o(PU0013Response.class);
		
		try {
		
			resp.setTimestamp(CronUtil.getFormatedDateNow());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new LocalServiceException(-1,"-  "+e.getMessage());
		}
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
		
		
	}
	
	
	@RegisterEventHandler(transcode="PU0014")
	public void handlePU0014(WebsocketEvent evt) throws LocalServiceException{
		PU0014Request req=G.evtAsObj(evt);
		PU0014Response resp=G.o(PU0014Response.class);
		
		try {
		
			List<UserAccount> users=localLoginService().getUserAccountByUserIdRange(req.getStart(), req.getEnd());
			List<User> items=GameUtil.getList();
			
			for(UserAccount u:users){
				User o=G.o(User.class);
				BeanUtils.copyProperties(u, o);
				items.add(o);
			}
			resp.setUsers(items);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new LocalServiceException(-1,"-  "+e.getMessage());
		}
		
		WebsocketEvent.send(evt.header, resp, false);
		
		
		
		
	}
	/**
	 * 
	 * @param evt
	 * @throws LocalServiceException 
	 * @throws RemoteServiceException 
	 */
	@RegisterEventHandler(transcode="PEC0001")
	public void handlePEC0001(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		
		PEC0001Request req=G.evtAsObj(evt);
		PEC0001Response resp = G.o(PEC0001Response.class);
		
		ExchangeCodeGift gift=null;
		try {
			gift = userAccountManager.exchangeCode(req.getId(), req.getExchangeCode());

			if(gift!=null){
				resp.setState(1);
				resp.setGift(gift.getName());
				resp.setGiftId(gift.getId());
				List<PECItem> items=GameUtil.getList();
				resp.setItemList(items);
				//resp.setItemList(gift.getItems());
				if(gift.getItems()!=null){
					for(ExchangeCodeItem i:gift.getItems()){
						items.add(PECItem.wrap(i));
					}
				}
			}
		} catch (LocalServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		
	
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	
	
	@RegisterEventHandler(transcode="PEC0002")
	public void handlePEC0002(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		
		PEC0002Request req=G.evtAsObj(evt);
		PEC0002Response resp = G.o(PEC0002Response.class);
		
		boolean ret=false;
		try {
			ret=userAccountManager.updateChangeCode(req.getId(), req.getExchangeCode(), req.getGiftId());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generatedcatch block
			if(e instanceof LocalServiceException){
				throw (LocalServiceException)e;
			}
			
		}
		
		resp.setState(ret?1:0);
		WebsocketEvent.send(evt.header, resp, false);
		
	}
	
	
	

}
