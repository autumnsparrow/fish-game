/**
 * 
 */
package com.toyo.fish.protocol.service;

import com.sky.game.context.event.LocalServiceException;
import com.toyo.fish.data.wrapper.domain.ValidCode;

/**
 * @author sparrow
 *
 */
public interface IValidCodeService {
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public ValidCode reGenerateValidCode(Long userId) throws LocalServiceException; 
	
	/**
	 * 
	 * @param userId
	 * @param validCode
	 * @return
	 * @throws LocalServiceException
	 */
	public boolean validValidCode(Long userId,String validCode) throws LocalServiceException;

}
