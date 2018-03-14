package com.sky.mobile.protocol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.domain.SafeCenterNotify;
import com.sky.mobile.protocol.persistence.SafeCenterNotifyMapper;


@Service
public class SafeCenterNotifyService {
	
	@Autowired
	SafeCenterNotifyMapper safeCenterNotifyMapper;
	
	
	
	public Long saveSafeCenterNotify(int amount,String appExt1,String appExt2,String appKey,String appUid,String gatewayFlag,
			long orderId,String productId,String sign,String signType,String signReturn,long userId){
		
		// avoid repeat record.
		
		SafeCenterNotify o = GameUtil.obtain(SafeCenterNotify.class);
		o.setAmount(amount);
		o.setAppExt1(appExt1);
		o.setAppExt2(appExt2);
		o.setAppKey(appKey);
		o.setAppUid(appUid);
		o.setGatewayFlag(gatewayFlag);
		o.setOrderId(orderId);
		o.setProductId(productId);
		o.setSign(sign);
		o.setSignReturn(signReturn);
		o.setSignType(signType);
		o.setUserId(userId);
		
		
		int affectedRows=safeCenterNotifyMapper.insert(o);
		return o.getId();
		
	}

}
