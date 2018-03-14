package com.sky.mobile.protocol.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.sky.mobile.inteface.WdjController.WdjRequest;
import com.sky.mobile.protocol.domain.WdjNotify;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.persistence.WdjNotifyMapper;
import com.sky.mobile.protocol.util.HttpUtil;



@Service
public class WdjService {
	
	
	@Autowired
	WdjNotifyMapper wdjNotifyMapper;
	
	@Autowired
	ErrorMessage errorMessage;
	
	
	public Long save(WdjRequest r){
		Long id=null;
		
		WdjNotify o =G.o(WdjNotify.class);
		o.setAppKeyId(r.getAppKeyId());
		o.setBuyerId(r.getBuyerId());
		o.setCardNo(r.getCardNo());
		o.setChargeType(r.getChargeType());
		o.setCreateTime(new Date());
		o.setMoney(GameUtil.s2i(r.getMoney()));
		o.setOutTradeNo(r.getOut_trade_no());
		o.setWdjTimestamp(r.getTimeStamp());
		
		wdjNotifyMapper.insertSelective(o);
		
		id=o.getId();
		return id;
		
	}
	
	
	public boolean validToken(String uid,String token) {
		
		Map<String,String> params=GameUtil.getMap();
		params.put("uid", uid);
		params.put("token", token);
		
		String content=HttpUtil.httpGet(errorMessage.wdjKeys.getCheckUriUrl(), params);
		boolean ret=false;
		if(content!=null&&content.equalsIgnoreCase("true")){
			ret=true;
		}
		return ret;
	}

}
