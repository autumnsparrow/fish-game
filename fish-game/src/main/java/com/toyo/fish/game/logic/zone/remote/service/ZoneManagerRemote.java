/**
 * 
 */
package com.toyo.fish.game.logic.zone.remote.service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

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

import com.toyo.fish.protocol.beans.PFU0000Beans;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0001Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0001Response;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0002Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0002Response;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0003Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0003Response;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0004Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0004Response;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0005Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0005Response;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0006Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0006Response;
import com.toyo.fish.protocol.beans.PRS0000Beans;
import com.toyo.fish.protocol.beans.PRS0000Beans.PRS0001Request;
import com.toyo.fish.protocol.beans.PRS0000Beans.PRS0001Response;
import com.toyo.fish.protocol.beans.PRS0000Beans.PRS0002Request;
import com.toyo.fish.protocol.beans.PRS0000Beans.PRS0002Response;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0002Requet;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0002Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Response;
import com.toyo.fish.protocol.beans.user.UserAccountInfo;
import com.toyo.fish.protocol.service.IFishRankService;
import com.toyo.fish.protocol.service.IUserDataService;
import com.toyo.fish.protocol.service.domain.CurrencyRecord;
import com.toyo.fish.protocol.service.domain.FishRank;
import com.toyo.fish.protocol.service.domain.VitRecord;
import com.toyo.remote.service.system.ISystemRemoteService;
import com.toyo.remote.service.system.domain.ProtocolHandleConfig;
import com.toyo.remote.service.user.ILoginService;
import com.toyo.remote.service.user.SeqUserAccount;


/**
 * @author sparrow
 *
 */
public class ZoneManagerRemote implements IIdentifiedObject {
	
	private static final Log logger=LogFactory.getLog(ZoneManagerRemote.class);
	
	Long id;

	public ZoneManagerRemote() {
		super();
		// TODO Auto-generated constructor stub
		G.regeisterHandler(this, "PFU0001","PFU0002","PFU0003","PFU0004","PFU0005","PFU0006","PRS0001","PRS0002");
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return EventHandlerClass.DEFAULT_ID;
	}

	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id=id;
		
	}
	
	public IFishRankService localFishRankService(){
		
		return SpringContext.getBean("IFishRankService");
	}
	
	public IUserDataService localUserDataService(){
		
		return SpringContext.getBean("IZoneRemoteService");
	}
	
	public ILoginService remoteLoginService(){
		return SpringContext.getBean("ILoginService");
	}
	
	public ISystemRemoteService remoteSystemService(){
		return SpringContext.getBean("ISystemService");
	}
	
	/**
	 * {@link IUserDataService#joinOrLeaveFarm(Long, String, boolean)}
	 * @param evt
	 * @throws LocalServiceException
	 * @throws RemoteServiceException
	 */
	@RegisterEventHandler(transcode="PFU0001")
	public void handlePFU0001(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
			PFU0001Request req=G.evtAsObj(evt);
			PFU0001Response resp=G.o(PFU0001Response.class);
			localUserDataService().joinOrLeaveFarm(req.getId(), req.getFarmId(), req.getUserAction()==1?true:false);
			
			resp.setSeq(req.getSeq());
			resp.setState(1);
			
			WebsocketEvent.send(evt.header, resp, false);
	}
	

	/**
	 * {@link IUserDataService#saveFishWeight(Long, String, int, int, float)}
	 * @param evt
	 * @throws LocalServiceException
	 * @throws RemoteServiceException
	 */
	@RegisterEventHandler(transcode="PFU0002")
	public void handlePFU0002(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		PFU0002Request req=G.evtAsObj(evt);
		PFU0002Response resp=G.o(PFU0002Response.class);
		
		Long weightLogId=localUserDataService().saveFishWeight(req.getId(),req.getFishId(), req.getGrade(),req.getRare()
				, req.getWeight(),1);
		
		resp.setSeq(req.getSeq());
		resp.setState(weightLogId>0?1:0);
		
		WebsocketEvent.send(evt.header, resp, false);
	
	}
	
	/**
	 * {@link IUserDataService#saveCurrencyRecord(long, java.util.List)}
	 * @param evt
	 * @throws LocalServiceException
	 * @throws RemoteServiceException
	 */
	@RegisterEventHandler(transcode="PFU0003")
	public void handlePFU0003(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		
		PFU0003Request req=G.evtAsObj(evt);
		PFU0003Response resp=G.o(PFU0003Response.class);
		List<CurrencyRecord> records=GameUtil.getList();
		for(PFU0000Beans.CurrencyRecord r:req.getRecords()){
			CurrencyRecord o=G.o(CurrencyRecord.class);
			o.setClientId(r.getClientId());
			o.setAction(r.getAction());
			o.setAmount(r.getAmount());
			o.setCategory(r.getCategory());
			o.setModule(r.getModule());
			o.setParam(r.getParam());
			o.setOrderTime(CronUtil.getDateFromString(r.getOrderTime()));
			records.add(o);
		}
		
		int affectedRows=localUserDataService().saveCurrencyRecord(req.getId(), records);
		
		resp.setSeq(req.getSeq());
		resp.setState(affectedRows>0?1:0);
		
		WebsocketEvent.send(evt.header, resp, false);
	}
	
	

	@RegisterEventHandler(transcode="PFU0004")
	public void handlePFU0004(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		
		PFU0004Request req=G.evtAsObj(evt);
		PFU0004Response resp=G.o(PFU0004Response.class);
		List<VitRecord> records=GameUtil.getList();
		for(PFU0000Beans.VitRecord r:req.getRecords()){
			VitRecord o=G.o(VitRecord.class);
			
			o.setOnline(r.getOnline());
			o.setOrderTime(CronUtil.getDateFromString(r.getOrderTime()));
			o.setVit(r.getVit());
			o.setClientId(r.getClientId());
	
		
			records.add(o);
			
		}
		
		int afffectedRows=localUserDataService().saveVitRecord(req.getId(), records);
		resp.setSeq(req.getSeq());
		resp.setState(afffectedRows>0?1:0);
		
		WebsocketEvent.send(evt.header, resp, false);
	}

	@RegisterEventHandler(transcode="PRS0001")
	public void handlePRS0001(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		PRS0001Request req=G.evtAsObj(evt);
		PRS0001Response resp=G.o(PRS0001Response.class);
		
		List<PRS0000Beans.FishRank> items=GameUtil.getList();
		List<FishRank> ranks=null;
		ranks=localFishRankService().getTop50(req.getFishId());
		
		
		
		String description=null;
		description=localFishRankService().getUserRank(req.getId(), req.getFishId());
		
		if(ranks!=null&&ranks.size()>0){
		
		Map<Long,Long> idMaps=GameUtil.getMap();
		//List<Long> userIds=GameUtil.getList();
		for(int i=0;i<ranks.size();i++){
			FishRank f=ranks.get(i);
			idMaps.put(Long.valueOf(f.getId()), Long.valueOf(f.getId()));
			//userIds.add(Long.valueOf(f.getId()));
		}
		List<Long> userIds=GameUtil.getMapAsList(idMaps);
		
		SeqUserAccount seqUserAccount=remoteLoginService().getUserAccount(userIds);
		List<com.toyo.fish.data.wrapper.domain.UserAccount> accounts=seqUserAccount.getAccounts();
		Map<Long,com.toyo.fish.data.wrapper.domain.UserAccount> avtarMaps=GameUtil.getMap();
		if(accounts!=null)
		for(com.toyo.fish.data.wrapper.domain.UserAccount account:accounts){
			avtarMaps.put(account.getId(), account);
		}
		
		
		
		for(FishRank r:ranks){
			PRS0000Beans.FishRank o=G.o(PRS0000Beans.FishRank.class);
			BeanUtils.copyProperties(r, o);
			o.setId(GameUtil.s2c(r.getId()));
			if(avtarMaps.containsKey(Long.valueOf(r.getId()))){
				com.toyo.fish.data.wrapper.domain.UserAccount account=avtarMaps.get(Long.valueOf(r.getId()));
				o.setAvatar(account.getAvatar());
				o.setUserName(account.getNickName());
				o.setLevel(account.getLevel());
			
			}
			
			if(o.getAvatar()==null||"".equals(o.getAvatar())){
				o.setAvatar("001");
			}
			if(o.getUserName()==null||"".equals(o.getUserName())){
				o.setUserName(String.valueOf(o.getId()));
			}
			
			
			
			
			

			items.add(o);
		}
	
		}
		
		resp.setSeq(req.getSeq());
		resp.setState(ranks.size()>0?1:0);
		resp.setDescription(description);
		resp.setData(items);
		

		WebsocketEvent.send(evt.header, resp, false);
		
	
	}
	
	
	
	@RegisterEventHandler(transcode="PRS0002")
	public void handlePFS0002(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		PRS0002Request req=G.evtAsObj(evt);
		PRS0002Response resp=G.o(PRS0002Response.class);
		
		List<PRS0000Beans.FishRank> items=GameUtil.getList();
		List<FishRank> ranks=null;
		
		ranks=localFishRankService().getFriends(req.getId(), req.getFishId());
		
		String description=null;
		
	for(FishRank r:ranks){
				if(r.getId()==req.getId()){
					description=String.valueOf(r.getRank());
					break;
				}
			}
			//description=localFishRankService().getFriends(req.getId(), req.getFishId());
		
		
		if(ranks!=null&&ranks.size()>0){
		
		Map<Long,Long> idMaps=GameUtil.getMap();
		//List<Long> userIds=GameUtil.getList();
		for(int i=0;i<ranks.size();i++){
			FishRank f=ranks.get(i);
			idMaps.put(Long.valueOf(f.getId()), Long.valueOf(f.getId()));
			//userIds.add(Long.valueOf(f.getId()));
		}
		List<Long> userIds=GameUtil.getMapAsList(idMaps);
		
		SeqUserAccount seqUserAccount=remoteLoginService().getUserAccount(userIds);
		List<com.toyo.fish.data.wrapper.domain.UserAccount> accounts=seqUserAccount.getAccounts();
		Map<Long,com.toyo.fish.data.wrapper.domain.UserAccount> avtarMaps=GameUtil.getMap();
		if(accounts!=null)
		for(com.toyo.fish.data.wrapper.domain.UserAccount account:accounts){
			avtarMaps.put(account.getId(), account);
		}
		
		
		
		for(FishRank r:ranks){
			PRS0000Beans.FishRank o=G.o(PRS0000Beans.FishRank.class);
			BeanUtils.copyProperties(r, o);
			o.setId(GameUtil.s2c(r.getId()));
			if(avtarMaps.containsKey(Long.valueOf(r.getId()))){
				com.toyo.fish.data.wrapper.domain.UserAccount account=avtarMaps.get(Long.valueOf(r.getId()));
				o.setAvatar(account.getAvatar());
				o.setUserName(account.getNickName());
				o.setLevel(account.getLevel());
			
			}
			
			if(o.getAvatar()==null||"".equals(o.getAvatar())){
				o.setAvatar("001");
			}
			if(o.getUserName()==null||"".equals(o.getUserName())){
				o.setUserName(String.valueOf(o.getId()));
			}
			
			
			
			
			

			items.add(o);
		}
	
		}
		
		resp.setSeq(req.getSeq());
		resp.setState(ranks.size()>0?1:0);
		resp.setDescription(description);
		resp.setData(items);
		

		WebsocketEvent.send(evt.header, resp, false);
		
	
	}
	
	
	/**
	 * {@link IUserDataService#saveFishWeight(Long, String, int, int, float)}
	 * @param evt
	 * @throws LocalServiceException
	 * @throws RemoteServiceException
	 */
	@RegisterEventHandler(transcode="PFU0005")
	public void handlePFU0005(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		PFU0005Request req=G.evtAsObj(evt);
		PFU0005Response resp=G.o(PFU0005Response.class);
		
		Long weightLogId=null;
		boolean flag=false;
		try {
			weightLogId = localUserDataService().saveFishWeight(req.getId(),req.getFishId(), req.getFishRank(),req.getFishRare()
					, req.getFishWeight(),req.getSucceed());
			
			if(weightLogId!=null&&weightLogId.longValue()>0){
				logger.info("FishWeightId:"+weightLogId.longValue()+" succeed :"+(req.getSucceed()==1?"true":"false"));
				localUserDataService().saveEquipment(weightLogId.longValue(), req.getLevel(), req.getRodId(), 
						req.getRodProps(), req.getWheelId(), req.getWheelProps(), req.getLineId(), 
						req.getLureId(), req.getDrugId1(), req.getDrugId2(), req.getDrugId3(), 
						req.getBigFishAddition(),
						req.getTensionTriggers(), req.getTensionUsed(), 
						req.getDragTriggers(), req.getDragUsed(), req.getFishDuration(),req.getSucceed());
			
				flag=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setSeq(req.getSeq());
		resp.setState(flag?1:0);
		
		
		WebsocketEvent.send(evt.header, resp, false);
	
	}
	
	
	@RegisterEventHandler(transcode="PFU0006")
	public void handlePFU0006(WebsocketEvent evt) throws LocalServiceException, RemoteServiceException{
		PFU0006Request req=G.evtAsObj(evt);
		PFU0006Response resp=G.o(PFU0006Response.class);
		
		int affectedRows=localUserDataService().saveGuideDate(req.getId(), req.getGuideDate());
		
		
		resp.setSeq(req.getSeq());
		resp.setState(affectedRows>0?1:0);
		
		WebsocketEvent.send(evt.header, resp, false);
	
	}
	

}
