/**
 * 
 */
package com.toyo.fish.protocol.zone.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.data.wrapper.zone.domain.CurrencyLog;
import com.toyo.fish.data.wrapper.zone.domain.FishEquipmentLog;
import com.toyo.fish.data.wrapper.zone.domain.FishFarmJoinLog;
import com.toyo.fish.data.wrapper.zone.domain.FishWeightFailedLog;
import com.toyo.fish.data.wrapper.zone.domain.FishWeightLog;
import com.toyo.fish.data.wrapper.zone.domain.GuideLog;
import com.toyo.fish.data.wrapper.zone.domain.VitLog;
import com.toyo.fish.data.wrapper.zone.persistence.CurrencyLogMapper;
import com.toyo.fish.data.wrapper.zone.persistence.FishEquipmentLogMapper;
import com.toyo.fish.data.wrapper.zone.persistence.FishFarmJoinLogMapper;
import com.toyo.fish.data.wrapper.zone.persistence.FishWeightFailedLogMapper;
import com.toyo.fish.data.wrapper.zone.persistence.FishWeightLogMapper;
import com.toyo.fish.data.wrapper.zone.persistence.GuideLogMapper;
import com.toyo.fish.data.wrapper.zone.persistence.VitLogMapper;
import com.toyo.fish.protocol.service.IUserDataService;
import com.toyo.fish.protocol.service.domain.CurrencyRecord;
import com.toyo.fish.protocol.service.domain.VitRecord;
import com.toyo.remote.service.friends.IFriendsRemoteService;
import com.toyo.remote.service.mail.IMailRemoteService;
import com.toyo.remote.service.zone.IZoneRemoteService;

/**
 * @author sparrow
 *
 */
@Service("IZoneRemoteService")
public class DefaultUserDataService implements IUserDataService {
	
	private static final Log logger=LogFactory.getLog(DefaultUserDataService.class);
	
	@Autowired
	FishFarmJoinLogMapper fishFarmJoinLogMapper;
	
	@Autowired
	FishWeightLogMapper fishWeightLogMapper;
	
	@Autowired
	CurrencyLogMapper currencyLogMapper;
	
	@Autowired
	VitLogMapper vitLogMapper;
	
	@Autowired
	FishEquipmentLogMapper fishEquipmentLogMapper;
	
	@Autowired
	GuideLogMapper guideLogMapper;
	
	@Autowired
	FishWeightFailedLogMapper fishWeightFailedLogMapper;
	
	
	

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.IUserDataService#joinOrLeaveFarm(java.lang.Long, java.lang.String, boolean)
	 */
	
	public void joinOrLeaveFarm(Long userId, String farmId, boolean join)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		try {
			FishFarmJoinLog o=G.o(FishFarmJoinLog.class);
			o.setFarmId(farmId);
			o.setUserId(userId);
			o.setLogType(join?Integer.valueOf(1):Integer.valueOf(2));
			o.setCreateTime(new Date());
			fishFarmJoinLogMapper.insertSelective(o);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.IUserDataService#saveFishReward(java.lang.Long, java.lang.String, int, int, float)
	 */
	
	public Long saveFishWeight(Long userId, String fishId, int grade,
			int rare, float weight,int succeed) throws LocalServiceException {
		// TODO Auto-generated method stub
		
		boolean ret=false;
		
		Long id=null;
		
		try {
			int validWeight=fishWeightLogMapper.findFishWeightInRange(fishId, weight);
			
			if(validWeight>0){
				
			if(succeed==1){
				FishWeightLog validWeightInUser=fishWeightLogMapper.findFishWeightByUserIdAndFishId(userId, fishId);
				if(validWeightInUser!=null){
					FishWeightLog o=G.o(FishWeightLog.class);
					o.setFishId(fishId);
					o.setGrade(grade);
					o.setRare(rare);
					o.setWeight(new BigDecimal(weight));
					o.setUserId(userId);
					o.setUpdateTime(new Date());
					o.setId(validWeightInUser.getId());
					id=validWeightInUser.getId();
					int affectedRows=fishWeightLogMapper.updateByPrimaryKeySelective(o);
					ret=affectedRows>0?true:false;
				}else{
					FishWeightLog o=G.o(FishWeightLog.class);
					o.setFishId(fishId);
					o.setGrade(grade);
					o.setRare(rare);
					o.setWeight(new BigDecimal(weight));
					o.setUserId(userId);
					o.setCreateTime(new Date());
					// valid the weight.max and weight.min
					// save only one entry of a fishId for user.
					
					int affectedRows=fishWeightLogMapper.insertSelective(o);
					ret=affectedRows>0?true:false;
					id=o.getId();
					
				}
			}else{
				
				FishWeightFailedLog validWeightInUser=fishWeightFailedLogMapper.findFishWeighFailedtByUserIdAndFishId(userId, fishId);
				if(validWeightInUser!=null){
					FishWeightFailedLog o=G.o(FishWeightFailedLog.class);
					o.setFishId(fishId);
					o.setGrade(grade);
					o.setRare(rare);
					o.setWeight(new BigDecimal(weight));
					o.setUserId(userId);
					o.setUpdateTime(new Date());
					o.setId(validWeightInUser.getId());
					id=validWeightInUser.getId();
					int affectedRows=fishWeightFailedLogMapper.updateByPrimaryKeySelective(o);
					ret=affectedRows>0?true:false;
				}else{
					FishWeightFailedLog o=G.o(FishWeightFailedLog.class);
					o.setFishId(fishId);
					o.setGrade(grade);
					o.setRare(rare);
					o.setWeight(new BigDecimal(weight));
					o.setUserId(userId);
					o.setCreateTime(new Date());
					// valid the weight.max and weight.min
					// save only one entry of a fishId for user.
					
					int affectedRows=fishWeightFailedLogMapper.insertSelective(o);
					ret=affectedRows>0?true:false;
					id=o.getId();
					
				}
				
			}
				
			}else{
				throw new LocalServiceException(-1, "Invalid Weight");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return id;
	}

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.IUserDataService#saveCurrencyRecord(long, java.util.List)
	 */
	
	public int saveCurrencyRecord(long userId, List<CurrencyRecord> records)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		
		int affectedRows=0;
		try {
			List<CurrencyLog> items=GameUtil.getList();
			Date createTime=new Date();
			for(CurrencyRecord r:records){
				CurrencyLog o=G.o(CurrencyLog.class);
				o.setUserId(userId);
				o.setCreateTime(createTime);
				o.setfAction(r.getAction());
				o.setfAmount(r.getAmount()	);
				o.setfCategory(r.getCategory());
				o.setfModule(r.getModule());
				o.setfParam(r.getParam());
				o.setClientId(r.getClientId());
				
				
				items.add(o);
			}
			
			affectedRows = currencyLogMapper.saveCurrencyLogBatch(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.IUserDataService#saveVitRecord(java.lang.Long, java.util.List)
	 */
	
	public int saveVitRecord(Long userId, List<VitRecord> records)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		List<VitLog> items=GameUtil.getList();
		//Date createTime=new Date();
		int affectedRows=0;
		try {
			for(VitRecord r:records){
				VitLog o=G.o(VitLog.class);
				o.setCreateTime(r.getOrderTime());
				
				o.setOnline(r.getOnline());
				o.setUserId(userId);
				o.setVit(r.getVit());
				o.setClientId(r.getClientId());
				items.add(o);
			}
			
			affectedRows = vitLogMapper.insertBatch(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return affectedRows;
	}

	
	public int saveEquipment(Long weightLogId, int level, String rodId,
			String rodProps, String wheelId, String wheelProps, String lineId,
			String lureId, String drugId1, String drugId2, String drugId3,
			String bigFishAddition, int tenstionTriggers, int tesionUsed,
			int dragTriggers, int dragUsed, int fishDuration,int succeed)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		int affectedRows=0;
		try {
			FishEquipmentLog item=
					
					succeed==1?fishEquipmentLogMapper.selectByWeightLogId(weightLogId):fishEquipmentLogMapper.selectByWeightFailedLogId(weightLogId);
			FishEquipmentLog o=G.o(FishEquipmentLog.class);
			
			o.setBigFishAddition(bigFishAddition);
			o.setDragTriggers(dragTriggers);
			o.setDragUsed(dragUsed);
			o.setDrugId1(drugId1);
			o.setDrugId2(drugId2);

			o.setFishDuration(fishDuration);
			o.setLineId(lineId);
			o.setLureId(lureId);
			o.setRodId(rodId);
			o.setRodProps(rodProps);
			o.setTesionTriggers(tenstionTriggers);
			o.setTesionUsed(tesionUsed);
			o.setUserLevel(level);
			if(succeed==1)
				o.setWeightLogId(weightLogId);
			else
				o.setWeightFailedLogId(weightLogId);
			
			o.setWheelId(wheelId);
			o.setWheelProps(wheelProps);
			affectedRows = 0;
			if(item!=null){
				o.setId(item.getId());
				affectedRows=fishEquipmentLogMapper.updateByPrimaryKey(o);
			}else{
				affectedRows=fishEquipmentLogMapper.insertSelective(o);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return affectedRows;
	}

	
	public int saveGuideDate(Long userId, String guideDate)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		int affectedRows=0;
		try {
			GuideLog record=G.o(GuideLog.class);
			record.setGuideDate(guideDate);
			record.setUserId(userId);
			affectedRows = guideLogMapper.insert(record);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return affectedRows;
	}

	@Transactional
	public boolean reset(Long userId) throws RemoteServiceException {
		// TODO Auto-generated method stub
	boolean ret=false;
	int affectedRows=0;
		try {
			currencyLogMapper.insertBySelectUserId(userId);
			affectedRows =currencyLogMapper.deleteByUserId(userId);
			logger.info("remove currency:"+userId+" - "+affectedRows);
			
//			fishEquipmentLogMapper.insertBySelectUserId(userId);
//			affectedRows=fishEquipmentLogMapper.deleteByUserId(userId);
//			logger.info("remove equipment:"+userId+" - "+affectedRows);
			
			fishFarmJoinLogMapper.insertBySelectUserId(userId);
			affectedRows=fishFarmJoinLogMapper.deleteByUserId(userId);
			logger.info("remove farmjoin:"+userId+" - "+affectedRows);
			
			fishWeightFailedLogMapper.insertBySelectUserId(userId);
			affectedRows=fishWeightFailedLogMapper.deleteByUserId(userId);
			logger.info("remove weight failed:"+userId+" - "+affectedRows);
			
			fishWeightLogMapper.insertBySelectUserId(userId);
			affectedRows=fishWeightLogMapper.deleteByUserId(userId);
			logger.info("remove weight:"+userId+" - "+affectedRows);
			
			
			guideLogMapper.insertBySelectUserId(userId);//(record);
			affectedRows=guideLogMapper.deleteByUserId(userId);//
			logger.info("remove guide log:"+userId+" - "+affectedRows);
			
			vitLogMapper.insertBySelectUserId(userId);
			affectedRows= vitLogMapper.deleteByUserId(userId);
			logger.info("remove vit log:"+userId+" - "+affectedRows);
			
			ret=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ret;
	}

}
