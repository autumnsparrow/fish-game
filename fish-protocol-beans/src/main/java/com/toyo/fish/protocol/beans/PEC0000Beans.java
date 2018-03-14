/**
 * 
 */
package com.toyo.fish.protocol.beans;

import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.domain.BaseResponse;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.util.G;
import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeItem;


/**
 * 
 * 
 * 
 * 
 * 
 * @author cris
 *
 */
public class PEC0000Beans {
	
	public static class PECItem{
		String id;
		int  total;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		
		public static PECItem wrap(ExchangeCodeItem item){
			PECItem o = G.o(PECItem.class);
			o.setId(item.getItemId());
			o.setTotal(item.getItemTotal());
			return o;
		}
		
	};
	
	
	/**
	 * Extra Object of
	 * User Protocol 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerNamespaceExtraType(namespace="ProtocolExchangeCode")
	public static class Extra extends RouterHeader {
		
		
	}
	
	
	/**
	 * User Register.
	 * 
	 * 
	 * 
	 * @author cris
	 *
	 */
	@HandlerRequestType(transcode="PEC0001")
	public static class PEC0001Request extends  UserBaseObj {
		
		String exchangeCode;
		
		public PEC0001Request() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getExchangeCode() {
			return exchangeCode;
		}

		public void setExchangeCode(String exchangeCode) {
			this.exchangeCode = exchangeCode;
		}
	}
	
	@HandlerResponseType(transcode="PEC0001",responsecode="CEP0001")
	public static class PEC0001Response {
		int state;
		
		String gift;
		
		int giftId;
		
		public int getGiftId() {
			return giftId;
		}
		public void setGiftId(int giftId) {
			this.giftId = giftId;
		}
		List<PECItem> itemList;
		
		public PEC0001Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getGift() {
			return gift;
		}
		public void setGift(String gift) {
			this.gift = gift;
		}
		public List<PECItem> getItemList() {
			return itemList;
		}
		public void setItemList(List<PECItem> itemList) {
			this.itemList = itemList;
		}
		
		
		
	}
	
	
	
	
	
	public static class UserBaseObj implements IIdentifiedObject{
		Long id;

		public Long getId() {
			// TODO Auto-generated method stub
			return id;
		}

		public void setId(Long id) {
			// TODO Auto-generated method stub
			this.id=id;
		}
		
	}
	
	
	@HandlerRequestType(transcode="PEC0002")
	public static class PEC0002Request extends  UserBaseObj {
		
		String exchangeCode;
		
		Integer giftId;
		
		public Integer getGiftId() {
			return giftId;
		}

		public void setGiftId(Integer giftId) {
			this.giftId = giftId;
		}

		public PEC0002Request() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getExchangeCode() {
			return exchangeCode;
		}

		public void setExchangeCode(String exchangeCode) {
			this.exchangeCode = exchangeCode;
		}
	}
	
	@HandlerResponseType(transcode="PEC0002",responsecode="CEP0002")
	public static class PEC0002Response {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
		
	}
	
	
	

}
