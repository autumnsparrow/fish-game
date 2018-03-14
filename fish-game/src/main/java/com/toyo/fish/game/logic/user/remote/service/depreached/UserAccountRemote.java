///**
// * 
// */
//package com.toyo.fish.game.logic.user.remote.service.depreached;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.springframework.beans.BeanUtils;
//
//import com.sky.game.context.SpringContext;
//import com.sky.game.context.annotation.RegisterEventHandler;
//import com.sky.game.context.annotation.introspector.IIdentifiedObject;
//import com.sky.game.context.event.LocalServiceException;
//import com.sky.game.context.event.WebsocketEvent;
//import com.sky.game.context.event.WebsocketEventHandler;
//import com.sky.game.context.spring.RemoteServiceException;
//import com.sky.game.context.util.BeanUtil;
//import com.sky.game.context.util.G;
//import com.sky.game.context.util.GameUtil;
//import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeGift;
//import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeItem;
//import com.toyo.fish.game.logic.handler.GlobalUserManager;
//import com.toyo.fish.game.logic.handler.RemoteObjectCache;
//import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0001Request;
//import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0001Response;
//import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0002Request;
//import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0002Response;
//import com.toyo.fish.protocol.beans.PEC0000Beans.PECItem;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0003Request;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0003Response;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0004Request;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0004Response;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0005Request;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0005Response;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0006Request;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0006Response;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0007Request;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0007Response;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0008Request;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0008Response;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0009Request;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0009Response;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0010Request;
//import com.toyo.fish.protocol.beans.PU0000Beans.PU0010Response;
//import com.toyo.fish.protocol.beans.user.UserAccountInfo;
//import com.toyo.fish.protocol.service.IUserAccount;
//import com.toyo.fish.protocol.service.IUserAccountManager;
//import com.toyo.remote.service.system.IExchangeCodeService;
//import com.toyo.remote.service.user.ILoginService;
//
//
///**
// * 
// * 
// * binding the service and networking proxy
// * @author sparrow
// *
// */
//public class UserAccountRemote  extends RemoteObjectCache{
//	
//	
//	
//	
//	UserAccountInfo userAccountInfo;
//	//UserAccountManagerRemote userAccountManagerRemote;
//	
//	
//	IUserAccountManager userAccountManager;
//	
//	ILoginService localLoginService(){
//		return SpringContext.getBean("ILoginService");
//	}
//	
//	
//	
//	public UserAccountRemote(Long id,UserAccountInfo info) {
//		super(id);
//		userAccountInfo=info;
//		userAccountManager=SpringContext.getBean("IUserAccountManager");
//		G.regeisterHandler(this, "PU0003","PU0004","PU0005","PU0006","PU0007","PU0008","PU0009","PU0010", "PEC0001","PEC0002");
//	}
//	
//	//
//	//
//	// Local Service
//	//
//
//
//
//	
//	//
//	// RemoteService
//	//
//	//
//	
//	
//	/**
//	 * 
//	 * 
//	 * @param evt
//	 * {@link PU0003Request}
//	 * {@link PU0003Response}
//	 * {@link IUserAccount#bindThirdpartyAccount(Long, String)}
//	 * @throws LocalServiceException 
//	 * 
//	 * 
//	 */
//	@RegisterEventHandler(transcode="PU0003")
//	public void handlePU0003(WebsocketEvent evt) throws LocalServiceException{
//		
//		PU0003Request req=G.evtAsObj(evt);
//		PU0003Response resp=G.o(PU0003Response.class);
//		
//		boolean ret=true;
//		try {
//			ret=userAccountManager.bindThirdpartyAccount(id, req.getAccount());
//			resp.setState(1);
//			resp.setMessage("Ok");
//			resp.setUserId(id);
//			//String token=BeanUtil.v(o, e)
//			String token=BeanUtil.v(userAccountInfo,"login.token");
//			resp.setToken(token);
//			
//		} catch (LocalServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw e;
//			
//		}
//		
//		
//		
//		//evt.token=resp.getToken();
//		WebsocketEvent.send(userAccountInfo.getHeader(), resp, false);
//		
//		
//	}
//	
//	
//	/**
//	 * 
//	 * 
//	 * @param evt
//	 * 
//	 * {@link IUserAccount#bindPhone(Long, String, String, String)}
//	 * @throws LocalServiceException 
//	 */
//	@RegisterEventHandler(transcode="PU0004")
//	public void handlePU0004(WebsocketEvent evt) throws LocalServiceException{
//		PU0004Request req=G.evtAsObj(evt);
//		PU0004Response resp=G.o(PU0004Response.class);
//		
//		
//		try {
//			
//			userAccountManager.
//					bindPhone(id, req.getUserName(), req.getPassWord());
//			resp.setState(1);
//			resp.setMsg("");
//			String token=BeanUtil.v(userAccountInfo,"login.token");
//			resp.setToken(token);
//			resp.setUserId(id);
//			
//					
//		} catch (LocalServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//			throw e;
//			
//		}
//		
//		
//		//evt.token=resp.getToken();
//		WebsocketEvent.send(userAccountInfo.getHeader(), resp, false);
//		
//		
//	}
//	
//	
//	/**
//	 * 
//	 * @param evt
//	 * 
//	 * {@link IUserAccountManager#sendValidCode(Long, String)}
//	 * @throws LocalServiceException 
//	 * @see PU0005Request
//	 * @see PU0005Response
//	 */
//	@RegisterEventHandler(transcode="PU0005")
//	public void handlePU0005(WebsocketEvent evt) throws LocalServiceException{
//		
//		PU0005Request req=G.evtAsObj(evt);
//		PU0005Response resp=G.o(PU0005Response.class);
//		
//		
//		try {
//			userAccountManager.sendValidCode(id, req.getPhone());
//			
//			resp.setState(1);
//			
//		} catch (LocalServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw e;
//			//resp.setState(e.state);
//		}
//		
//
//		WebsocketEvent.send(userAccountInfo.getHeader(), resp, false);
//		
//		
//		
//		
//	}
//	
//	
//	
//	/**
//	 * 
//	 * @param evt
//	 * @throws LocalServiceException 
//	 * @see PU0006Request
//	 * @see PU0006Response
//	 * 
//	 */
//	@RegisterEventHandler(transcode="PU0006")
//	public void handlePU0006(WebsocketEvent evt) throws LocalServiceException{
//		PU0006Request req=G.evtAsObj(evt);
//		PU0006Response resp=G.o(PU0006Response.class);
//		
//		try {
//			
//			userAccountManager.modifyPassword(id, req.getPassword());
//			resp.setState(1);
//			resp.setMsg("ok");
//		
//		} catch (LocalServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw e;
//		}
//		
//		WebsocketEvent.send(userAccountInfo.getHeader(), resp, false);
//		
//		
//		
//		
//	}
//
//
//
//	/**
//	 * 
//	 * @param evt
//	 * @throws LocalServiceException 
//	 * @see PU0006Request
//	 * @see PU0006Response
//	 * 
//	 */
//	@RegisterEventHandler(transcode="PU0007")
//	public void handlePU0007(WebsocketEvent evt) throws LocalServiceException{
//		PU0007Request req=G.evtAsObj(evt);
//		PU0007Response resp=G.o(PU0007Response.class);
//		
//		try {
//			
//			userAccountManager.validCode(id, req.getValidcode());
//			resp.setState(1);
//			resp.setMsg("ok");
//		
//		} catch (LocalServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw e;
//		}
//		
//		WebsocketEvent.send(userAccountInfo.getHeader(), resp, false);
//		
//		
//		
//		
//	}
//	
//	
//	
//	/**
//	 * 
//	 * @param evt
//	 * @throws LocalServiceException 
//	 * @see PU0006Request
//	 * @see PU0006Response
//	 * 
//	 */
//	@RegisterEventHandler(transcode="PU0008")
//	public void handlePU0008(WebsocketEvent evt) throws LocalServiceException{
//		PU0008Request req=G.evtAsObj(evt);
//		PU0008Response resp=G.o(PU0008Response.class);
//		
//		try {
//			userAccountManager.updateAvatar(req.getId(), req.getAvatar());
//			//userAccountManager.validCode(id, req.getValidcode());
//			resp.setState(1);
//			//resp.setMsg("ok");
//		
//		} catch (LocalServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw e;
//		}
//		
//		WebsocketEvent.send(userAccountInfo.getHeader(), resp, false);
//		
//		
//		
//		
//	}
//	
//	
//	/**
//	 * 
//	 * @param evt
//	 * @throws LocalServiceException 
//	 * @see PU0006Request
//	 * @see PU0006Response
//	 * 
//	 */
//	@RegisterEventHandler(transcode="PU0009")
//	public void handlePU0009(WebsocketEvent evt) throws LocalServiceException{
//		PU0009Request req=G.evtAsObj(evt);
//		PU0009Response resp=G.o(PU0009Response.class);
//		
//		try {
//		
//			boolean ret=userAccountManager.updateNickname(req.getId(), req.getNickName());
//			//userAccountManager.validCode(id, req.getValidcode());
//			resp.setState(ret?1:0);
//			//resp.setMsg("ok");
//		
//		} catch (LocalServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw e;
//		}
//		
//		WebsocketEvent.send(userAccountInfo.getHeader(), resp, false);
//		
//		
//		
//		
//	}
//	
//	
//	
//	/**
//	 * 
//	 * @param evt
//	 * @throws LocalServiceException 
//	 * @see PU0006Request
//	 * @see PU0006Response
//	 * 
//	 */
//	@RegisterEventHandler(transcode="PU0010")
//	public void handlePU0010(WebsocketEvent evt) throws LocalServiceException{
//		PU0010Request req=G.evtAsObj(evt);
//		PU0010Response resp=G.o(PU0010Response.class);
//		
//		try {
//		
//			boolean ret=localLoginService().updateUserLevel(req.getId(), req.getLevel());
//			
//			resp.setState(ret?1:0);
//			
//		
//		} catch (LocalServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw e;
//		}
//		
//		WebsocketEvent.send(userAccountInfo.getHeader(), resp, false);
//		
//		
//		
//		
//	}
//	
//	/**
//	 * 
//	 * @param evt
//	 * @throws LocalServiceException 
//	 * @throws RemoteServiceException 
//	 */
//	@RegisterEventHandler(transcode="PEC0001")
//	public void handlePEC0001(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
//		
//		PEC0001Request req=G.evtAsObj(evt);
//		PEC0001Response resp = G.o(PEC0001Response.class);
//		
//		ExchangeCodeGift gift=null;
//		try {
//			gift = userAccountManager.exchangeCode(id, req.getExchangeCode());
//
//			if(gift!=null){
//				resp.setState(1);
//				resp.setGift(gift.getName());
//				resp.setGiftId(gift.getId());
//				List<PECItem> items=GameUtil.getList();
//				resp.setItemList(items);
//				//resp.setItemList(gift.getItems());
//				if(gift.getItems()!=null){
//					for(ExchangeCodeItem i:gift.getItems()){
//						items.add(PECItem.wrap(i));
//					}
//				}
//			}
//		} catch (LocalServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw e;
//		}
//		
//		
//	
//		WebsocketEvent.send(userAccountInfo.getHeader(), resp, false);
//		
//		
//	}
//	
//	
//	@RegisterEventHandler(transcode="PEC0002")
//	public void handlePEC0002(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
//		
//		PEC0002Request req=G.evtAsObj(evt);
//		PEC0002Response resp = G.o(PEC0002Response.class);
//		
//		boolean ret=false;
//		try {
//			ret=userAccountManager.updateChangeCode(req.getId(), req.getExchangeCode(), req.getGiftId());
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO Auto-generatedcatch block
//			if(e instanceof LocalServiceException){
//				throw (LocalServiceException)e;
//			}
//			
//		}
//		
//		resp.setState(ret?1:0);
//		WebsocketEvent.send(userAccountInfo.getHeader(), resp, false);
//		
//	}
//	
//	
//
//}
