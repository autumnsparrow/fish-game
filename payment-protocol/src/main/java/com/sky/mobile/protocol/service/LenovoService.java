/**
 * 
 */
package com.sky.mobile.protocol.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipay.req.OrderReq;
import com.ipay.res.OrderRes;
import com.ipay.util.ConstValue;
import com.sky.game.context.util.G;
import com.sky.mobile.protocol.domain.LenovoGetOrder;
import com.sky.mobile.protocol.persistence.LenovoGetOrderMapper;

/**
 * @author sparrow
 *
 */
@Service
public class LenovoService {
	
	
	@Autowired
	LenovoGetOrderMapper lenovoGetOrderMapper;
	/**
	 * 
	 * @param userId
	 * @param wareId
	 * @param wareName
	 * @param price
	 * @param localOrderId
	 * @return
	 */
	public String getOrder(String userId,int wareId,String wareName,int price,String localOrderId){
		OrderReq o=G.o(OrderReq.class);
		o.setWaresId(wareId);
		o.setWarasName(wareName);
		o.setPrice(Float.valueOf(price*.01f));
		o.setCpOrderId(localOrderId);
		o.setAppId(ConstValue.AppId);
		o.setAppUserId(userId);
		o.setCurrency("RMB");
		o.setNotifyUrl(ConstValue.NotifyUrl);
		
		OrderRes resp=o.send();
		String orderId	=	null;
		if(!resp.isError()){
			orderId=resp.getTransid();
			// insert the data into databases.
			LenovoGetOrder record=G.o(LenovoGetOrder.class);
			record.setAmount(price);
			record.setCreateTime(new Date());
			record.setLocalOrderId(localOrderId);
			record.setWareId(wareId);
			record.setWareName(wareName);
			record.setOrderId(orderId);
			
			lenovoGetOrderMapper.insertSelective(record);
		}
		
		return orderId;
	}
	
	public LenovoGetOrder findOrderByOrderId(String orderId){
		LenovoGetOrder o=lenovoGetOrderMapper.findByOrderId(orderId);
		return o;
	}

}
