package com.sky.mobile.protocol.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.domain.AnZhiNotify;
import com.sky.mobile.protocol.persistence.AnZhiNotifyMapper;

@Service
public class AnZhiNotifyService {
	
	@Autowired
	AnZhiNotifyMapper anZhiNotifyMapper;
	
	
	public Long save(String localOrderId, String memo, String msg, Date notifyTime, String orderAccount, String orderId, Date orderTime, Integer redBagMoney, Integer state, String uid){
		Long id=null;
		AnZhiNotify o=GameUtil.obtain(AnZhiNotify.class);
		o.setLocalOrderId(localOrderId);
		o.setCreateTime(new Date());
		o.setMemo(memo);
		o.setMsg(msg);
		o.setNotifyTime(notifyTime);
		o.setOrderAccount(orderAccount);
		o.setOrderId(orderId);
		o.setOrderTime(orderTime);
		o.setRedBagMoney(redBagMoney);
		o.setState(state);
		o.setUid(uid);
		o.setUpdateTime(new Date());
		
		
		int affectedRows=anZhiNotifyMapper.insert(o);
		
		if(affectedRows>0){
			id=o.getId();
		}
		return id;
		
		
	}

}
