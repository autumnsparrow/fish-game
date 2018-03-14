/**
 * 
 */
package com.toyo.fish.protocol.service;

import java.util.List;

import com.sky.game.context.event.LocalServiceException;
import com.toyo.fish.protocol.service.domain.CurrencyRecord;
import com.toyo.fish.protocol.service.domain.VitRecord;
import com.toyo.remote.service.zone.IZoneRemoteService;

/**
 * @author sparrow
 *
 */
public interface IUserDataService  extends IZoneRemoteService{
	
	/**
	 * 
	 * @param userId
	 * @param farmId
	 * @param join
	 * @throws LocalServiceException
	 */
	public void joinOrLeaveFarm(Long userId,String farmId,boolean join) throws LocalServiceException;

	
	/**
	 * 
	 * @param userId
	 * @param fishId
	 * @param grade
	 * @param rare
	 * @param weight
	 * @return
	 * @throws LocalServiceException
	 */
	public Long saveFishWeight(Long userId,String fishId,int grade,int rare,float weight,int succeed) throws LocalServiceException;
	/**
	 * 
	 * 
	 * @param records
	 * @return
	 * @throws LocalServiceException
	 */
	public int saveCurrencyRecord(long userId,List<CurrencyRecord> records) throws LocalServiceException;
	
	
	/**
	 * 
	 * @param records
	 * @return
	 * @throws LocalServiceException
	 */
	public int saveVitRecord(Long userId,List<VitRecord> records) throws LocalServiceException;
	
	/**
	 * 
	 * @param weightLogId
	 * @param level
	 * @param rodId
	 * @param rodProps
	 * @param wheelId
	 * @param wheelProps
	 * @param lineId
	 * @param lureId
	 * @param drugId1
	 * @param drugId2
	 * @param drugId3
	 * @param bigFishAddition
	 * @param tenstionTriggers
	 * @param tesionUsed
	 * @param drgTriggers
	 * @param dragUsed
	 * @param fishDuration
	 * @return
	 * @throws LocalServiceException
	 */
	public int saveEquipment(
			Long weightLogId, 
			int level,
			String rodId,
			String rodProps, 
			String wheelId, 
			String wheelProps, 
			String lineId,
			String lureId, 
			String drugId1, 
			String drugId2, 
			String drugId3,
			String bigFishAddition, 
			int tenstionTriggers, 
			int tesionUsed,
			int drgTriggers, 
			int dragUsed,
			int fishDuration,
			int succeed)
			throws LocalServiceException;
	
	
	public int saveGuideDate(Long userId,String guideDate)throws LocalServiceException;
}
