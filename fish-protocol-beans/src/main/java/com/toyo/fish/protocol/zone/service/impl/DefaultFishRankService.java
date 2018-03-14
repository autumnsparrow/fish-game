/**
 * 
 */
package com.toyo.fish.protocol.zone.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.data.wrapper.zone.domain.FishWeightLog;
import com.toyo.fish.data.wrapper.zone.persistence.FishWeightLogMapper;
import com.toyo.fish.protocol.service.IFishRankService;
import com.toyo.fish.protocol.service.IFriendsService;
import com.toyo.fish.protocol.service.domain.FishRank;
import com.toyo.remote.service.friends.IFriendsRemoteService;
import com.toyo.remote.service.friends.domain.FriendsIdSeq;
import com.toyo.remote.service.user.ILoginService;

/**
 * @author sparrow
 *
 */
@Service("IFishRankService")
public class DefaultFishRankService implements IFishRankService {
	
	@Autowired
	FishWeightLogMapper fishWeightLogMapper;
	
	@Autowired
	IFriendsRemoteService friendsService;
	
	@Autowired
	ILoginService loginService;

	/* 
	 * 
	 * (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.IFishRankService#getTop50(java.lang.String)
	 */
	
	public List<FishRank> getTop50(String fishId) throws LocalServiceException {
		// TODO Auto-generated method stub
		List<FishRank> items=null;
		items = GameUtil.getList();
		try {
			List<FishWeightLog> top50=fishWeightLogMapper.selectByFishWeightSort(fishId);
			
			
			
			
			for(int i=0;i<top50.size();i++){
				FishRank rank=GameUtil.obtain(FishRank.class);
				FishWeightLog f=top50.get(i);
				
				rank.setAvatar("");
				rank.setCatchTime(f.getCreateTime().getTime());
				rank.setLevel(0);
				rank.setUserName("");
				rank.setWeight(f.getWeight().floatValue());
				rank.setId(f.getUserId());
				rank.setRank(i+1);
				items.add(rank);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return items;
	}

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.IFishRankService#getUserRank(java.lang.Long, java.lang.String)
	 */
	
	private static final float[] radio={0.35f,0.45f,0.65f,0.85f};
	
	private String getRankDescription(int total,int offset,Float weightOfTT,Float weightOfUser){
		String sort=null;
		try {
			
			sort = null;
			if(offset<1000){
				sort=String.format("%d", offset,total);
			}else if(offset<10000&&offset>1000){
				sort=String.format("%d+", (offset%100)*100,total);
			}else if (offset<100000&&offset>10000){
				sort=String.format("%d+", (offset%1000)*1000,total);
			}else {
					float v=0f;
				if(offset>100000&offset<1000000){
					v=(radio[0]*weightOfUser/weightOfTT);
				}else if(offset>1000000&&offset<10000000){
					v=(radio[1]*weightOfUser/weightOfTT);
				}else if(offset>10000000&&offset<100000000){
					v=(radio[2]*weightOfUser/weightOfTT);
				}else{
					v=(radio[3]*weightOfUser/weightOfTT);
				}
				
				sort=String.format("%d", v*100)+"%";
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sort;
	}
	
	public String getUserRank(Long userId, String fishId)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		// size of fishId in the user.
		int total=fishWeightLogMapper.selectCountOfFishId(fishId);
		int offset=fishWeightLogMapper.selectOffsetOfFishId(userId, fishId);
		Float weightOfTT=fishWeightLogMapper.selectWeightOfTenThousands(fishId);
		Float weightOfUser=fishWeightLogMapper.selectWeightOfUser(userId, fishId);

		return getRankDescription(total,offset,weightOfTT,weightOfUser);
	}
	
	public List<FishRank> getFriends(Long userId, String fishId)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		List<Long> userIds=null;
		List<FishRank> items = GameUtil.getList();
		try {
			
			try {
				FriendsIdSeq seq= friendsService.getFriendsUserIds(userId);
				seq.getIds().add(userId);
				userIds=seq.getIds();
			} catch (RemoteServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(userIds!=null){
			List<FishWeightLog> friendsFish=fishWeightLogMapper.selectByFishWeightOfFriendsSort(fishId, userIds);
			
			
			for(int i=0;i<friendsFish.size();i++){
				FishRank rank=GameUtil.obtain(FishRank.class);
				FishWeightLog f=friendsFish.get(i);
				
				rank.setAvatar("");
				rank.setCatchTime(f.getCreateTime().getTime());
				rank.setLevel(0);
				rank.setUserName("");
				rank.setWeight(f.getWeight().floatValue());
				rank.setId(f.getUserId());
				rank.setRank(i+1);
				
				items.add(rank);
				
			}
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;	
		
		
	}

	

}
