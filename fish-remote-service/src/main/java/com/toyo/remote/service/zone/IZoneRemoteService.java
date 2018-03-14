/**
 * 
 */
package com.toyo.remote.service.zone;

import com.sky.game.context.spring.RemoteServiceException;

/**
 * @author sparrow
 *
 */
public interface IZoneRemoteService {

	
	public boolean reset(Long userId) throws RemoteServiceException;
}
