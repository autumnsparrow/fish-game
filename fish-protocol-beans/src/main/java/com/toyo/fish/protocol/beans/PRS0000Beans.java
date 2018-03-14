package com.toyo.fish.protocol.beans;

import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.protocol.beans.PFS0000Beans.BaseObj;
import com.toyo.fish.protocol.beans.PFS0000Beans.SeqObj;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFUBaseObj;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFUSeqObj;
import com.toyo.fish.protocol.beans.PRS0000Beans.FishRank;

public class PRS0000Beans {
	
	/**
	 * Extra Object of
	 * User Protocol 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerNamespaceExtraType(namespace="ProtocolRankSystem")
	public static class Extra extends RouterHeader {
	}

	public static class PRSBaseObj extends PRSSeqObj implements IIdentifiedObject {

		public Long getId() {
			// TODO Auto-generated method stub
			return id;
		}

		public void setId(Long id) {
			// TODO Auto-generated method stub
			this.id = id;

		}

		Long id;
	}
	
	public static class PRSSeqObj {
		int seq;

		public int getSeq() {
			return seq;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}
		
	};
	
	

	/**
	 * 
	 * @param farmId farm id
	 * @param userAction 1.join the farm ,2.leave the farm
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "PRS0001")
	public static class PRS0001Request extends PFUBaseObj{  
		
		String fishId;
		//int category;  // 0  = world ,1=friends

		public String getFishId() {
			return fishId;
		}

		public void setFishId(String fishId) {
			this.fishId = fishId;
		}

		
		
	};
	
	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "PRS0001", responsecode = "SRP0001")
	public static class PRS0001Response  extends PFUSeqObj {
		int state;
		String description;
		List<FishRank> data;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public List<FishRank> getData() {
			return data;
		}

		public void setData(List<FishRank> data) {
			this.data = data;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
	}
	
	public static class FishRank{
		Long id;
		String userName;
		String avatar;
		int level;
		float weight;
		long catchTime;
		int rank;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = (id);
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		public float getWeight() {
			return weight;
		}
		public void setWeight(float weight) {
			this.weight = weight;
		}
		public long getCatchTime() {
			return catchTime;
		}
		public void setCatchTime(long catchTime) {
			this.catchTime = catchTime;
		}
		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}
		
		
	}
	
	
	/**
	 * 
	 * @param farmId farm id
	 * @param userAction 1.join the farm ,2.leave the farm
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "PRS0002")
	public static class PRS0002Request extends BaseObj{  
		
		String fishId;
		//int category;  // 0  = world ,1=friends

		public String getFishId() {
			return fishId;
		}

		public void setFishId(String fishId) {
			this.fishId = fishId;
		}

		
		
	};
	
	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "PRS0002", responsecode = "SRP0002")
	public static class PRS0002Response  extends SeqObj {
		int state;
		String description;
		List<FishRank> data;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public List<FishRank> getData() {
			return data;
		}

		public void setData(List<FishRank> data) {
			this.data = data;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
	}

	
	
	


}
