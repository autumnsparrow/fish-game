/**
 * 
 */
package com.toyo.fish.protocol.service;

import java.util.List;

import com.sky.game.context.event.LocalServiceException;
import com.toyo.fish.protocol.service.domain.FishRank;

/**
 * @author sparrow
 *
 */
public interface IFishRankService {
	
	/**
	 * 
	 * @param fishId
	 * @return
	 * @throws LocalServiceException
	 */
	public List<FishRank> getTop50(String fishId) throws LocalServiceException;
	
	/**
	 * 
	 * @param userId
	 * @param fishId
	 * @return
	 * @throws LocalServiceException
	 */
	public String getUserRank(Long userId,String fishId) throws LocalServiceException;
	
	
	
	/**
	 * 
	 * @param userId
	 * @param fishId
	 * @return
	 * @throws LocalServiceException
	 */
	public List<FishRank> getFriends(Long userId,String fishId) throws LocalServiceException;

	
}
