/**
 * 
 */
package com.toyo.fish.protocol.system.service.impl;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.toyo.fish.data.wrapper.domain.ProtocolHandler;
import com.toyo.fish.data.wrapper.domain.UserBan;
import com.toyo.fish.data.wrapper.persistence.ProtocolHandlerMapper;
import com.toyo.fish.data.wrapper.persistence.UserBanMapper;
import com.toyo.fish.data.wrapper.system.domain.ResourceUpdate;
import com.toyo.fish.data.wrapper.system.persistence.ResourceUpdateMapper;
import com.toyo.remote.service.system.ISystemRemoteService;
import com.toyo.remote.service.system.domain.ProtocolHandleConfig;
import com.toyo.remote.service.system.domain.ProtocolModuleTypes;
import com.toyo.remote.service.user.ILoginService;

/**
 * @author sparrow
 *
 */
@Service("ISystemRemoteService")
public class DefaultSystemService implements ISystemRemoteService {
	
	
	@Autowired
	ResourceUpdateMapper resourceUpdateMapper;
	
	@Autowired
	ILoginService loginService;
	
	

	/* (non-Javadoc)
	 * @see com.toyo.remote.service.system.ISystemService#findResourceUpdate(java.lang.Integer)
	 */
	public ResourceUpdate findResourceUpdate(Integer channelId)
			throws RemoteServiceException {
		// TODO Auto-generated method stub
		ResourceUpdate o=null;
		try {
			o=resourceUpdateMapper.selectByChannelId(channelId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return o;
	}
	
	
	
	
	

	
	

}
