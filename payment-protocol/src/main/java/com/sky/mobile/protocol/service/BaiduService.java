package com.sky.mobile.protocol.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.mobile.inteface.BaiduController.BaiduPaymentResponse;
import com.sky.mobile.protocol.domain.BaiduNotify;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.persistence.BaiduNotifyMapper;
@Service
public class BaiduService {
	
	private static final Log logger=LogFactory.getLog(BaiduService.class);
	@Autowired
	BaiduNotifyMapper baiduNotifyMapper;
	
	@Autowired
	PaymentOrderService paymentOrderService;
	
	
	
	
	
	public Long save(BaiduPaymentResponse r){
		BaiduNotify o =G.o(BaiduNotify.class);
		o.setAmount(r.getAmount());
		o.setAppId(r.getAppid());
		o.setChannel(r.getChannel());
		o.setCpDefinePart(r.getCpdefinepart());
		o.setCreateTime(new Date());
		o.setExtChannel(r.getExtchannel());
		o.setJfd(r.getJfd());
		o.setOrderId(r.getOrderid());
		o.setOrign(r.getFrom());
		o.setPayName(r.getPaychannel());
		o.setPhone(r.getPhone());
		o.setSign("");
		
		baiduNotifyMapper.insertSelective(o);
		
		return o.getId();
	}
	
	
	public void notify(BaiduPaymentResponse resp){
		
		Long id=save(resp);
		try {
			String localOrderId=resp.getCpdefinepart();
			String status="success".equalsIgnoreCase(resp.getStatus())?"200":"";
			paymentOrderService.updateOrderState(id, localOrderId, status, BaiduService.class.getSimpleName());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
