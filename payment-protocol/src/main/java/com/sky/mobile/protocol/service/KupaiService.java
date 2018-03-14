/**
 * 
 */
package com.sky.mobile.protocol.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.util.GameUtil;
import com.sky.mobile.inteface.KupaiController.KupaiNotify;
import com.sky.mobile.protocol.domain.AnZhiNotify;
import com.sky.mobile.protocol.domain.KuPaiNotify;
import com.sky.mobile.protocol.persistence.KuPaiNotifyMapper;;

/**
 * 
 * 
 * @author sparrow
 *
 */
@Service
public class KupaiService {
	
	@Autowired
	KuPaiNotifyMapper kupaiNotifyMapper;
	
	
	
	public Long save(KupaiNotify o){
		Long id=null;
		
		KuPaiNotify n=GameUtil.obtain(KuPaiNotify.class);
		n.setAmount(o.getAmount());
		n.setAppKey(o.getApp_key());
		n.setArea(o.getArea());
		n.setChKey(o.getCh_key());
		n.setCreateTime(new Date());
		n.setExtra(o.getExtra());
		n.setGoodsKey(o.getGoods_key());
		n.setMsg(o.getResult());
		n.setOp(o.getOp());
		n.setTradeId(o.getTrade_id());
		n.setTs(o.getTs());
		n.setUid(o.getUid());
		n.setUpdateTime(new Date());
		
		int affectedRows=kupaiNotifyMapper.insert(n);
		
		if(affectedRows>0){
			id=n.getId();
		}
		return id;
		
		
	}

}
