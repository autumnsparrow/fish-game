/**
 * 
 */
package com.toyo.fish.protocol.user.service.impl;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.util.G;
import com.toyo.fish.data.wrapper.domain.ValidCode;
import com.toyo.fish.data.wrapper.persistence.ValidCodeMapper;
import com.toyo.fish.protocol.service.IValidCodeService;

/**
 * @author sparrow
 *
 */
@Service("validCodeService")
public class DefaultValidCodeService implements IValidCodeService {
	
	
	
	@Autowired
	ValidCodeMapper validCodeMapper;
	
	
	
	public static final int VALID_TIME=1000*60*5;
	public static final int VALID_CODE_RESEND_MIN_TIME=1000*60;
	
	
	private String generateRandom(){
		 int a =new Random().nextInt(10);
		 int b =new Random().nextInt(10);
		 int c =new Random().nextInt(10);
		 int d =new Random().nextInt(10);
		 int e =new Random().nextInt(10);
		 int f =new Random().nextInt(10);
		 String y=String.format("%d%d%d%d%d%d", a,b,c,d,e,f);
		 return y;
		 
	}

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.IValidCodeService#reGenerateValidCode(java.lang.Long)
	 */
	public ValidCode reGenerateValidCode(Long userId) throws LocalServiceException {
		// TODO Auto-generated method stub
		
		ValidCode v=validCodeMapper.findValidCodeByUserId(userId);
		
		if(v==null){
			v=G.o(ValidCode.class);
			// create a new object of the ValidCode.
			v.setCreateTime(new Date());
			v.setUpdateTime(new Date());
			v.setValidCode(generateRandom());
			v.setUserId(userId);
			
			validCodeMapper.insertSelective(v);
		}else{
			
			long time=new Date().getTime()-v.getUpdateTime().getTime();
			if(time<VALID_CODE_RESEND_MIN_TIME){
				throw new LocalServiceException(-1,"waiting for next time send!");
			}
			
			
			ValidCode o=G.o(ValidCode.class);
			o.setId(v.getId());
			o.setValidCode(generateRandom());
			o.setUpdateTime(new Date());
			
			validCodeMapper.updateByPrimaryKeySelective(o);
			v=validCodeMapper.selectByPrimaryKey(o.getId());
		}
		
		
	
		
		return v;
		
	}

	/* (non-Javadoc)
	 * @see com.toyo.fish.protocol.service.IValidCodeService#validValidCode(java.lang.Long, java.lang.String)
	 */
	public boolean validValidCode(Long userId, String validCode)
			throws LocalServiceException {
		// TODO Auto-generated method stub
		boolean ret=false;
		ValidCode v=validCodeMapper.findValidCodeByUserId(userId);
		
		long time=new Date().getTime()-v.getUpdateTime().getTime();
		
		
		if(v.getValidCode().equals(validCode)){
			ret=true;
		}
		
		if(time>VALID_TIME){
			ret=false;
		}
		
		return ret;
	}

}
