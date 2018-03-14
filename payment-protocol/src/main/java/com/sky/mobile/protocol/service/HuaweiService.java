package com.sky.mobile.protocol.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.util.G;
import com.sky.mobile.protocol.domain.HuaweiNotify;
import com.sky.mobile.protocol.persistence.HuaweiNotifyMapper;


@Service
public class HuaweiService {
	
	
	@Autowired
	HuaweiNotifyMapper huaweiNotifyMapper;
	
	
	/**
	
	msg varchar(3),
	user_name varchar(50),
	product_name varchar(100),
	pay_type int,
	amount varchar(20),
	order_id varchar(50),
	notify_time varchar(50),
	request_id varchar(50),
	bank_id varchar(50),
	trade_time varchar(50),
	access_mode varchar(5),
	spending varchar(50),
	ext_reserved varchar(50),
	sys_reserved varchar(50),
	sign_type varchar(10),
	sign varchar(100),
	 * @param param
	 * @return
	 */
	private <T> T getValue(Map<String,Object> param,String key){
		T t=null;
		Object obj=param.get(key);
		if(obj!=null){
			t=(T)obj;
		}
		return t;
	}
	
	/**
	 * 
	 * 
	 * @param param
	 * @return
	 */
	public HuaweiNotify getNotiy(Map<String,Object> param){

		HuaweiNotify o =G.o(HuaweiNotify.class);
		
		
		
		
		String msg=getValue(param, "result");
		String userName=getValue(param, "userName");
		String productName=getValue(param, "productName");
		String paytype=getValue(param, "payType");
		
		Integer payType=paytype!=null?Integer.parseInt(paytype):Integer.valueOf(0);//getValue(param, "payType");
		String amount=getValue(param, "amount");
		String orderId=getValue(param, "orderId");
		String notifyTime=getValue(param, "notifyTime");
		String requestId=getValue(param, "requestId");
		String bankId=getValue(param, "bankId");
		String tradeTime=getValue(param, "tradeTime");
		String accessMode=getValue(param, "accessMode");
		String spending=getValue(param, "spending");
		String extReserved=getValue(param, "extReserved");
		String sysReserved=getValue(param, "sysReserved");
		String signType=getValue(param, "signType");
		String sign=getValue(param, "sign");
		
		
		try {
			
			BeanUtils.copyProperty(o, "msg", msg);
			BeanUtils.copyProperty(o, "userName", userName);
			BeanUtils.copyProperty(o, "productName", productName);
			BeanUtils.copyProperty(o, "payType", payType);
			BeanUtils.copyProperty(o, "amount", amount);
			BeanUtils.copyProperty(o, "orderId", orderId);
			BeanUtils.copyProperty(o, "notifyTime", notifyTime);
			BeanUtils.copyProperty(o, "requestId", requestId);
			BeanUtils.copyProperty(o, "bankId", bankId);
			BeanUtils.copyProperty(o, "tradeTime", tradeTime);
			BeanUtils.copyProperty(o, "accessMode", accessMode);
			BeanUtils.copyProperty(o, "spending", spending);
			BeanUtils.copyProperty(o, "extReserved", extReserved);
			BeanUtils.copyProperty(o, "extReserved", sysReserved);
			BeanUtils.copyProperty(o, "signType", signType);
			BeanUtils.copyProperty(o, "sign", "");
			
		
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return o;
	}
	
	public Long save(HuaweiNotify o){
		
		//Long id=null;
		
		huaweiNotifyMapper.insertSelective(o);
		
		return o.getId();
	}

}
