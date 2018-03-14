/**
 * 
 */
package com.sky.mobile.protocol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.mobile.protocol.domain.OppNotify;
import com.sky.mobile.protocol.persistence.OppNotifyMapper;
import com.sky.mobile.protocol.util.U;

/**
 * @author sparrow
 *
 */
@Service
public class OppNotifyService {
	
	@Autowired
	OppNotifyMapper oppNotifyMapper;
	
	/**
	 * 
	 * 
	 * @param notifyId
	 * @param partnerOrder
	 * @param productName
	 * @param productDesc
	 * @param price
	 * @param count
	 * @param attach
	 * @param sign
	 * @return
	 */
	public Long save(String notifyId, String partnerOrder,
			String productName, String productDesc, int price, int count,
			String attach, String sign

	) {
		boolean ret = false;
		OppNotify o=U.o(OppNotify.class);
		o.setNotifyId(notifyId);
		o.setPartnerOrder(partnerOrder);
		o.setProductName(productName);
		o.setProductDesc(productDesc);
		o.setPrice(price);
		o.setCount(count);
		o.setAttach(attach);
		o.setSign(sign);
		int affectedRows=oppNotifyMapper.insert(o);
		
		ret=affectedRows>0;
		return o.getId();

	}

}
