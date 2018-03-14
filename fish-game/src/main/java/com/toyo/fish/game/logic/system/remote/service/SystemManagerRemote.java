/**
 * 
 */
package com.toyo.fish.game.logic.system.remote.service;


import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;

import com.sky.game.context.SpringContext;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.EventHandlerClass;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.event.WebsocketEvent;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.BeanUtil;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.data.wrapper.domain.Login;
import com.toyo.fish.data.wrapper.domain.ProtocolHandler;
import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeGift;
import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeItem;
import com.toyo.fish.data.wrapper.system.domain.ResourceFiles;
import com.toyo.fish.data.wrapper.system.domain.ResourceUpdate;
import com.toyo.fish.game.logic.handler.GlobalSystemManager;
import com.toyo.fish.game.logic.handler.GlobalUserManager;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0001Request;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0001Response;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0002Request;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0002Response;
import com.toyo.fish.protocol.beans.PEC0000Beans.PECItem;
import com.toyo.fish.protocol.beans.PS0000Beans.FileItem;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0002Requet;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0002Response;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0003Requet;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0003Response;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0004Requet;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0004Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Response;
import com.toyo.fish.protocol.beans.user.UserAccountInfo;

import com.toyo.remote.service.system.IExchangeCodeService;
import com.toyo.remote.service.system.ISystemRemoteService;
import com.toyo.remote.service.system.domain.ProtocolHandleConfig;
import com.toyo.remote.service.system.domain.ProtocolModuleTypes;
import com.toyo.remote.service.user.ILoginService;

/**
 * @author sparrow
 *
 */
public class SystemManagerRemote implements IIdentifiedObject {
	private static final Log logger = LogFactory.getLog(SystemManagerRemote.class);
	
	Long id;

	public SystemManagerRemote() {
		super();
		// TODO Auto-generated constructor stub
		G.regeisterHandler(this, "PS0002","PS0003","PS0004","PEC0001","PEC0002");
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return EventHandlerClass.DEFAULT_ID;
	}

	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id=id;
		
	}
	
	public IExchangeCodeService localExchangeCodeService(){
		return SpringContext.getBean("IExchangeCodeService");
		
	}
	
	public ISystemRemoteService systemSerivce(){
		return SpringContext.getBean("ISystemRemoteService");
	}

	
	public ILoginService remoteLoginService(){
		return SpringContext.getBean("ILoginService");
	}
	
	
	
	

	@RegisterEventHandler(transcode="PS0004")
	public void handlePS0004(WebsocketEvent evt) throws LocalServiceException,RemoteServiceException{
		logger.info(evt.header.toString());
		PS0004Requet req=G.evtAsObj(evt);
		PS0004Response resp=G.o(PS0004Response.class);
		
		remoteLoginService().updateHandlerConfig(req.getType(),req.isState());
		
		resp.setSeq(req.getSeq());
		
		WebsocketEvent.send(evt.header, resp, false);
	}

	
	
	@RegisterEventHandler(transcode="PS0003")
	public void handlePS0003(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		logger.info(evt.header.toString());
		PS0003Requet req=G.evtAsObj(evt);
		PS0003Response resp=G.o(PS0003Response.class);
		
		ProtocolHandleConfig o= remoteLoginService().getHandleConfig();
		resp.setFriend(o.isFriends());
		resp.setMail(o.isMail());
		resp.setRank(o.isRank());
		resp.setUserdata(o.isUserData());
		resp.setSeq(req.getSeq());
		
		WebsocketEvent.send(evt.header, resp, false);
	}
	
	
	@RegisterEventHandler(transcode="PS0002")
	public void handlePS0002(WebsocketEvent evt) throws LocalServiceException{
		logger.info(evt.header.toString());
		PS0002Requet req=G.evtAsObj(evt);
		logger.info(evt.header.toString()+req.getVersion());
		
		
		PS0002Response resp=G.o(PS0002Response.class);
		
		ISystemRemoteService service=systemSerivce();
		
		
		ILoginService loginService=remoteLoginService();
		loginService.updateApkVersion(evt.header.getId(), String.valueOf(req.getChannelId()));
		
	
		
		try {
			
			ResourceUpdate o=service.findResourceUpdate(req.getChannelId());
			
			if(o!=null){
				BeanUtils.copyProperties(o, resp);
				resp.setSystemVersion(o.getVersion());
				List<FileItem> items=GameUtil.getList();
				resp.setFileItems(items);
				resp.setForceUpdate(o.getForceUpdate()==1?true:false);
				
				for(ResourceFiles f:o.getFiles()){
					FileItem fItem=G.o(FileItem.class);
					BeanUtils.copyProperties(f, fItem);
					fItem.setSize(f.getSize());
					items.add(fItem);
				}
				
				
			}
			
			
			WebsocketEvent.send(evt.header, resp, false);
			
			
		} catch (RemoteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param evt
	 * @throws LocalServiceException 
	 * @throws RemoteServiceException 
	 * @throws ProtocolException 
	 */
	@RegisterEventHandler(transcode="PEC0001")
	public void handlePEC0001(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		
		PEC0001Request req=G.evtAsObj(evt);
		PEC0001Response resp = G.o(PEC0001Response.class);
		
		ExchangeCodeGift gift=null;
		
		try {
			gift = localExchangeCodeService().exchangeCode(req.getId(), req.getExchangeCode());// userAccountManager.exchangeCode(id, req.getExchangeCode());
		}  catch (RemoteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw e;
			//resp.setState(e.status);
		}
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
		}else{
			throw new RemoteServiceException(IExchangeCodeService.EC_NOT_EXSIT);
		}
		
		
		
	
		WebsocketEvent.send(evt.header, resp, false);
		
		
	}
	
	
	@RegisterEventHandler(transcode="PEC0002")
	public void handlePEC0002(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException, ProtocolException{
		
		PEC0002Request req=G.evtAsObj(evt);
		PEC0002Response resp = G.o(PEC0002Response.class);
		
		boolean ret=false;
		
		try {
			ret=localExchangeCodeService().updateExchangeCode(req.getId(), req.getExchangeCode(),  req.getGiftId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RemoteServiceException(-1,"");
		}
			
		
		resp.setState(ret?1:0);
		WebsocketEvent.send(evt.header, resp, false);
		
	}
	
	

}
