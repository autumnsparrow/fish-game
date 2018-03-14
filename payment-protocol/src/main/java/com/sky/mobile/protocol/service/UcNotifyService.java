package com.sky.mobile.protocol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.mobile.protocol.domain.UcNotify;
import com.sky.mobile.protocol.persistence.UcNotifyMapper;
import com.sky.mobile.protocol.util.U;


@Service
public class UcNotifyService {
	
	@Autowired
	UcNotifyMapper ucNotifyMapper;
	
	
	/**
	 * 
	 * @param tradeId
	 * @param tradeTime
	 * @param orderId
	 * @param gameId
	 * @param amount
	 * @param payType
	 * @param attachInfo
	 * @param orderStatus
	 * @param failedDesc
	 * @param sign
	 * @return
	 */
	public Long save(String tradeId, String tradeTime, String orderId,
			String gameId, String amount, String payType, String attachInfo,
			String orderStatus, String failedDesc, String sign) {

		boolean ret = false;
		UcNotify o=U.o(UcNotify.class);
		o.setTradeId(tradeId);
		o.setTradeTime(tradeTime);
		o.setOrderId(orderId);
		o.setGameId(gameId);
		o.setAmount(amount);
		o.setPayType(payType);
		o.setAttachInfo(attachInfo);
		o.setOrderStatus(orderStatus);
		o.setFailedDesc(failedDesc);
		o.setSign(sign);
		
		
		int affectedRows=ucNotifyMapper.insert(o);
		
		//ret=affectedRows>0;
		

		return o.getId();

	}

}
