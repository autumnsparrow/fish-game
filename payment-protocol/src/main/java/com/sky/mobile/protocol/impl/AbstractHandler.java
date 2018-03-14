/**
 * @author:sparrow
 * @date:Jul 23, 2014
 * @company:北京博志万通科技有限公司
 *
 *	
 */
package com.sky.mobile.protocol.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.mobile.inteface.key.PrivateKeyConfiguration;
import com.sky.mobile.protocol.HandlerException;
import com.sky.mobile.protocol.HandlerManager;
import com.sky.mobile.protocol.IHandler;
import com.sky.mobile.protocol.ProtocolRequestEntry;
import com.sky.mobile.protocol.ProtocolResponseEntry;
import com.sky.mobile.protocol.util.DES;

/**
 * @author sparrow
 *
 */
public abstract class AbstractHandler implements IHandler {

	private static final Log logger=LogFactory.getLog(AbstractHandler.class);
	/**
	 * 
	 */
	public AbstractHandler() {
		// TODO Auto-generated constructor stub
		HandlerManager.getInstance().registerHandler(this);
	}
	
	

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		HandlerManager.getInstance().registerHandler(this);
	}

	
	
	public abstract  boolean process(String json,ProtocolResponseEntry responseEntry) throws HandlerException;

	/* (non-Javadoc)
	 * @see com.sky.allinpay.mobile.protocol.IHandler#handle(com.sky.allinpay.mobile.protocol.ProtocolEntry, com.sky.allinpay.mobile.protocol.ProtocolEntry)
	 */
	@Override
	public boolean handle(ProtocolRequestEntry request, ProtocolResponseEntry response)
			throws HandlerException {
		// TODO Auto-generated method stub
		boolean ret=false;
		
		try {
			long transcode=request.getCode().longValue();
			
			String desKey=PrivateKeyConfiguration.getInstance().findDesKey(transcode);
			if(desKey==null){
				throw new HandlerException(STATUS_DES_KEY_EMPTY, transcode+" can't find des key ");
			}
			String plainJson=null;
			try {
				plainJson = DES.decrypt(request.getContent(),desKey);
				logger.info("Request:"+plainJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new HandlerException(STATUS_DECRYPT_FAILED, transcode+" decrypt failed ");
				
			}
			ret=process(plainJson,response);
			// need to encrypt
			plainJson=response.getContent();
			logger.info("Respone:"+plainJson);
			String json=null;
			try {
				json = DES.encrypt(plainJson, desKey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new HandlerException(STATUS_ENCRYPT_FAILED, transcode+" encrypt failed ");
				
			}
			response.setContent(json);
			
		}catch (HandlerException e1){
			throw e1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new HandlerException(STATUS_FAILED, e.getMessage());
		}
		
		
		
		
		return ret;
	}

}
