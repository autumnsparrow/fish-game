/**
 * 
 */
package com.toyo.remote.service.system;

import java.util.List;

import com.sky.game.context.spring.IRemoteService;
import com.sky.game.context.spring.RemoteServiceException;
import com.toyo.fish.data.wrapper.domain.ProtocolHandler;
import com.toyo.fish.data.wrapper.system.domain.ResourceUpdate;
import com.toyo.remote.service.system.domain.ProtocolHandleConfig;
import com.toyo.remote.service.system.domain.ProtocolModuleTypes;

/**
 * 
 * 
 * @author sparrow
 *
 */
public interface ISystemRemoteService  {

	/**
	 * 
	 * @param channelId
	 * @return
	 * @throws RemoteException
	 */
	public ResourceUpdate findResourceUpdate(Integer channelId) throws RemoteServiceException;
	
	
	
	
 }
