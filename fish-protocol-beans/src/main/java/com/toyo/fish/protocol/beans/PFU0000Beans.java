package com.toyo.fish.protocol.beans;

import java.util.Date;
import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.route.RouterHeader;

public class PFU0000Beans {
	
	/**
	 * Extra Object of
	 * User Protocol 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerNamespaceExtraType(namespace="ProtocolFishUser")
	public static class Extra extends RouterHeader {
	}

	public static class PFUBaseObj extends PFUSeqObj implements IIdentifiedObject {

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
	
	public static class PFUSeqObj {
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
	@HandlerRequestType(transcode = "PFU0001")
	public static class PFU0001Request extends PFUBaseObj{  
		
		
		String farmId;
		int userAction;
		
		public String getFarmId() {
			return farmId;
		}
		public void setFarmId(String farmId) {
			this.farmId = farmId;
		}
		public int getUserAction() {
			return userAction;
		}
		public void setUserAction(int userAction) {
			this.userAction = userAction;
		}
		
		
		
	};
	
	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "PFU0001", responsecode = "UFP0001")
	public static class PFU0001Response  extends PFUSeqObj {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
	}
	
	
	
	/**
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "PFU0002")
	public static class PFU0002Request extends PFUBaseObj{  
		String fishId;
		int grade;
		int rare;
		float weight;
		public String getFishId() {
			return fishId;
		}
		public void setFishId(String fishId) {
			this.fishId = fishId;
		}
		public int getGrade() {
			return grade;
		}
		public void setGrade(int grade) {
			this.grade = grade;
		}
		public int getRare() {
			return rare;
		}
		public void setRare(int rare) {
			this.rare = rare;
		}
		public float getWeight() {
			return weight;
		}
		public void setWeight(float weight) {
			this.weight = weight;
		}
		
		
		
		
		
	};
	
	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "PFU0002", responsecode = "UFP0002")
	public static class PFU0002Response  extends PFUSeqObj {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
	}
	
	
	/**
	 * 
	 * @param farmId farm id
	 * @param userAction 1.join the farm ,2.leave the farm
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "PFU0003")
	public static class PFU0003Request extends PFUBaseObj{  
		List<CurrencyRecord> records;

		public List<CurrencyRecord> getRecords() {
			return records;
		}

		public void setRecords(List<CurrencyRecord> records) {
			this.records = records;
		}
		
		
		
	};
	
	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "PFU0003", responsecode = "UFP0003")
	public static class PFU0003Response  extends PFUSeqObj {
		
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
		
		
	}
	
	public static class CurrencyRecord{
		long clientId;
		int module;
		int action;
		int param;
		int amount;
		int category;
		String orderTime;
		
		public long getClientId() {
			return clientId;
		}
		public void setClientId(long clientId) {
			this.clientId = clientId;
		}
		
		public int getModule() {
			return module;
		}
		public void setModule(int module) {
			this.module = module;
		}
		public int getAction() {
			return action;
		}
		public void setAction(int action) {
			this.action = action;
		}
		public int getParam() {
			return param;
		}
		public void setParam(int param) {
			this.param = param;
		}
		public int getAmount() {
			return amount;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
		public int getCategory() {
			return category;
		}
		public void setCategory(int category) {
			this.category = category;
		}
		public String getOrderTime() {
			return orderTime;
		}
		public void setOrderTime(String orderTime) {
			this.orderTime = orderTime;
		}
		
	}
	
	
	@HandlerRequestType(transcode = "PFU0004")
	public static class PFU0004Request extends PFUBaseObj{  
		List<VitRecord> records;

		public List<VitRecord> getRecords() {
			return records;
		}

		public void setRecords(List<VitRecord> records) {
			this.records = records;
		}
		
		
	};
	
	public static class VitRecord{
		long clientId;
		int vit;
		int online;
		String orderTime;
		public int getVit() {
			return vit;
		}
		public void setVit(int vit) {
			this.vit = vit;
		}
		public int getOnline() {
			return online;
		}
		public void setOnline(int online) {
			this.online = online;
		}
		public String getOrderTime() {
			return orderTime;
		}
		public void setOrderTime(String orderTime) {
			this.orderTime = orderTime;
		}
		public long getClientId() {
			return clientId;
		}
		public void setClientId(long clientId) {
			this.clientId = clientId;
		}
		
	}
	
	
	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "PFU0004", responsecode = "UFP0004")
	public static class PFU0004Response  extends PFUSeqObj {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
	}
	
	
	@HandlerRequestType(transcode = "PFU0005")
	public static class PFU0005Request extends PFUBaseObj{  
		
		String fishId;
		int level;
		String rodId;
		String rodProps;
		String wheelId;
		String wheelProps;
		String lineId;
		String lureId;
		String drugId1;
		String drugId2;
		String drugId3;
		String bigFishAddition;
		float fishWeight;
		int fishRank;
		int fishRare;
		int tensionTriggers;
		int tensionUsed;
		int dragTriggers;
		int dragUsed;
		int fishDuration;
		int succeed; // 0:failure ,1 : succeed
		
		
		
		public int getSucceed() {
			return succeed;
		}
		public void setSucceed(int succeed) {
			this.succeed = succeed;
		}
		public String getFishId() {
			return fishId;
		}
		public void setFishId(String fishId) {
			this.fishId = fishId;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		public String getRodId() {
			return rodId;
		}
		public void setRodId(String rodId) {
			this.rodId = rodId;
		}
		public String getRodProps() {
			return rodProps;
		}
		public void setRodProps(String rodProps) {
			this.rodProps = rodProps;
		}
		public String getWheelId() {
			return wheelId;
		}
		public void setWheelId(String wheelId) {
			this.wheelId = wheelId;
		}
		public String getWheelProps() {
			return wheelProps;
		}
		public void setWheelProps(String wheelProps) {
			this.wheelProps = wheelProps;
		}
		public String getLineId() {
			return lineId;
		}
		public void setLineId(String lineId) {
			this.lineId = lineId;
		}
		public String getLureId() {
			return lureId;
		}
		public void setLureId(String lureId) {
			this.lureId = lureId;
		}
		public String getDrugId1() {
			return drugId1;
		}
		public void setDrugId1(String drugId1) {
			this.drugId1 = drugId1;
		}
		public String getDrugId2() {
			return drugId2;
		}
		public void setDrugId2(String drugId2) {
			this.drugId2 = drugId2;
		}
		public String getDrugId3() {
			return drugId3;
		}
		public void setDrugId3(String drugId3) {
			this.drugId3 = drugId3;
		}
		public String getBigFishAddition() {
			return bigFishAddition;
		}
		public void setBigFishAddition(String bigFishAddition) {
			this.bigFishAddition = bigFishAddition;
		}
		public float getFishWeight() {
			return fishWeight;
		}
		public void setFishWeight(float fishWeight) {
			this.fishWeight = fishWeight;
		}
		public int getFishRank() {
			return fishRank;
		}
		public void setFishRank(int fishRank) {
			this.fishRank = fishRank;
		}
		public int getFishRare() {
			return fishRare;
		}
		public void setFishRare(int fishRare) {
			this.fishRare = fishRare;
		}
		
		public int getTensionTriggers() {
			return tensionTriggers;
		}
		public void setTensionTriggers(int tensionTriggers) {
			this.tensionTriggers = tensionTriggers;
		}
		public int getTensionUsed() {
			return tensionUsed;
		}
		public void setTensionUsed(int tesionUsed) {
			this.tensionUsed = tesionUsed;
		}
		public int getDragTriggers() {
			return dragTriggers;
		}
		public void setDragTriggers(int drgTriggers) {
			this.dragTriggers = drgTriggers;
		}
		public int getDragUsed() {
			return dragUsed;
		}
		public void setDragUsed(int dragUsed) {
			this.dragUsed = dragUsed;
		}
		public int getFishDuration() {
			return fishDuration;
		}
		public void setFishDuration(int fishDuration) {
			this.fishDuration = fishDuration;
		}
		
		
		
	};
	
	
	@HandlerResponseType(transcode = "PFU0005", responsecode = "UFP0005")
	public static class PFU0005Response  extends PFUSeqObj {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
	}
	
	
	@HandlerRequestType(transcode = "PFU0006")
	public static class PFU0006Request extends PFUBaseObj{  
		
		String guideDate;

		public String getGuideDate() {
			return guideDate;
		}

		public void setGuideDate(String guideDate) {
			this.guideDate = guideDate;
		}
		
	
	}
	
	
	@HandlerResponseType(transcode = "PFU0006", responsecode = "UFP0006")
	public static class PFU0006Response  extends PFUSeqObj {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
	}
	
	

}
