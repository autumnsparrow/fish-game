package com.toyo.fish.protocol.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.data.wrapper.system.domain.ExchangeCode;
import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeGift;
import com.toyo.fish.data.wrapper.system.domain.GeneralExchangeCode;
import com.toyo.fish.data.wrapper.system.domain.UserExchangeCode;
import com.toyo.fish.data.wrapper.system.domain.UserGeneralExchangeCode;
import com.toyo.fish.data.wrapper.system.persistence.ExchangeCodeGiftMapper;
import com.toyo.fish.data.wrapper.system.persistence.ExchangeCodeMapper;
import com.toyo.fish.data.wrapper.system.persistence.GeneralExchangeCodeMapper;
import com.toyo.fish.data.wrapper.system.persistence.UserExchangeCodeMapper;
import com.toyo.fish.data.wrapper.system.persistence.UserGeneralExchangeCodeMapper;
import com.toyo.remote.service.system.IExchangeCodeService;


@Service("IExchangeCodeService")
public class DefaultExchangeCodeService implements IExchangeCodeService {

	@Autowired
	ExchangeCodeMapper exchangeCodeMapper;
	@Autowired
	GeneralExchangeCodeMapper generalExchangeCodeMapper;
	@Autowired
	ExchangeCodeGiftMapper exchangeCodeGiftMapper;
	@Autowired
	UserExchangeCodeMapper userExchangeCodeMapper;
	@Autowired
	UserGeneralExchangeCodeMapper userGeneralExchangeCodeMapper;

	/**
	 * 判断兑换码是否通用兑换码,通用兑换码为6位固定长度
	 * 
	 * @param code
	 * @return true为通用 false为非通用
	 */
	private boolean isGeneral(String code) {
		return code.trim().length() == 6;
	}

	public int use(Long userId, String code) throws RemoteServiceException {
		if (this.isGeneral(code))
			return useGeneral(userId, code);
		else
			return useOther(userId, code);
	}

	// 使用通用兑换码
	private int useGeneral(Long userId, String code) {

		GeneralExchangeCode ecData = generalExchangeCodeMapper
				.selectOneByCode(code.toUpperCase());
		// 判断兑换码是否存在
		if (ecData == null)
			return EC_NOT_EXSIT;
		// 兑换码id
		int id = ecData.getId();

		// 判断是否可以开始使用
		long currentTime = new Date().getTime();
		if (currentTime < ecData.getBeginTime().getTime())
			return EC_NOT_START;
		// 判断是否过期
		if (currentTime > ecData.getEndTime().getTime())
			return EC_EXPIRED;
		// 判断用户是否已经使用过了该giftId
		UserGeneralExchangeCode uec = userGeneralExchangeCodeMapper
				.selectByGiftId(ecData.getGiftId(), userId);
		
		if (uec != null){
			if(uec.getState()==1)
				return EC_USER_USEED;
			
			
		}
		else{
//			ecData.setFlag(1);	
//			int effectedRows = generalExchangeCodeMapper.updateByPrimaryKey(ecData);
			UserGeneralExchangeCode record = G.o(UserGeneralExchangeCode.class);
			record.setCodeId(id);
			record.setCode(code);
			record.setGiftId(ecData.getGiftId());
			record.setUserId(userId);
			userGeneralExchangeCodeMapper.insertSelective(record);
		}

		// 修改兑换码为已使用

		// 记录用户使用兑换码信息
		

		return EC_USE_OK;
	}

	// 使用非通用兑换码
	private int useOther(Long userId, String code) {
		ExchangeCode ecData = exchangeCodeMapper.selectOneByCode(code
				.toUpperCase());
		// 判断兑换码是否存在
		if (ecData == null)
			return EC_NOT_EXSIT;
		// 兑换码id
		int id = ecData.getId();
		
		if(ecData.getFlag()==1){
			return EC_USER_USEED;
		}

		// 判断是否可以开始使用
		long currentTime = new Date().getTime();
		if (currentTime < ecData.getBeginTime().getTime())
			return EC_NOT_START;
		// 判断是否过期
		if (currentTime > ecData.getEndTime().getTime())
			return EC_EXPIRED;
		// 判断用户是否已经使用过了该giftId
		UserExchangeCode uec = userExchangeCodeMapper.selectByGiftId(
				ecData.getGiftId(), userId);
		if (uec != null){
			if(uec.getState()==1)
				return EC_USER_USEED;
			
		}
		else{
			// 记录用户使用兑换码信息
//			ecData.setFlag(1);
//			int effectedRows = exchangeCodeMapper.updateByPrimaryKey(ecData);
			//if(effectedRows>0){
			UserExchangeCode record = G.o(UserExchangeCode.class);
			record.setCodeId(id);
			record.setCode(code);
			record.setGiftId(ecData.getGiftId());
			record.setUserId(userId);
			record.setCreateTime(new Date());
			userExchangeCodeMapper.insertSelective(record);
		//	}
		}

		// 修改兑换码为已使用
		

		

		return EC_USE_OK;
	}

	public ExchangeCodeGift showGift(String exchangeCode)
			throws RemoteServiceException {
		int giftId = -1;
		com.toyo.fish.data.wrapper.system.domain.ExchangeCodeGift gift = null;
		
			if (isGeneral(exchangeCode)) {
				// 从通用兑换码表中获取兑换码对象
				GeneralExchangeCode ecData = generalExchangeCodeMapper
						.selectOneByCode(exchangeCode.toUpperCase());
				if(ecData==null)
					throw new RemoteServiceException(EC_NOT_EXSIT,"code="+exchangeCode.toUpperCase());
				giftId = ecData.getGiftId();
			} else {
				// 从非通用兑换码表中获取非兑换对象
				ExchangeCode ecData = exchangeCodeMapper
						.selectOneByCode(exchangeCode.toUpperCase());
				if(ecData==null)
					throw new RemoteServiceException(EC_NOT_EXSIT,"code="+exchangeCode.toUpperCase());
			
				giftId = ecData.getGiftId();
			}
			
			
			gift = exchangeCodeGiftMapper.selectById(giftId);
		
		
		

		return gift;
	}

	public ExchangeCodeGift exchangeCode(Long userId, String code)
			throws RemoteServiceException {
		// TODO Auto-generated method stub
		ExchangeCodeGift gift = null;
		int useState=0;

		try {
			useState = use(userId, code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			useState=IExchangeCodeService.EC_EXCHANGE_FAILED;
		}
		if (useState == IExchangeCodeService.EC_USE_OK) {
			gift = showGift(code);
		} else {
			throw new RemoteServiceException(useState, "");
		}

		return gift;
	}

	public boolean updateExchangeCode(Long userId,String code, Integer  giftId)
			throws RemoteServiceException {
		// TODO Auto-generated method stub
		int affectedRow=0;
		boolean ret=false;
		try {
			
			
			
			if(this.isGeneral(code)){
				GeneralExchangeCode ecData = generalExchangeCodeMapper
						.selectOneByCode(code.toUpperCase());
				
				GeneralExchangeCode o =G.o(GeneralExchangeCode.class);
				o.setId(ecData.getId());
				o.setFlag(1);
				generalExchangeCodeMapper.updateByPrimaryKeySelective(o);
				//ecData.setFlag(1);
				affectedRow=userGeneralExchangeCodeMapper.updateByGiftId(giftId, userId);
			}else{
				ExchangeCode ecData = exchangeCodeMapper
						.selectOneByCode(code.toUpperCase());
				ExchangeCode o=G.o(ExchangeCode.class);
				
				o.setId(ecData.getId());
				o.setFlag(1);
				exchangeCodeMapper.updateByPrimaryKeySelective(o);
				
				affectedRow=userExchangeCodeMapper.updateByGiftId(giftId, userId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new RemoteServiceException(-1,e.getMessage());
		}
		
		
		if(affectedRow>0){
			ret=true;
		}
		
		return ret;
		
	}

}
