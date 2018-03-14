/**
 * 
 */
package com.sky.mobile.protocol.service.impl;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author sparrow
 *
 */
public class PaymentStatics {
	private static final Log logger=LogFactory.getLog(PaymentStatics.class);
	
	 int succeedMin=1;
	 int failerMax=5;
	 long timeout=10*60*1000;
	 long warnTimeout=8*60*1000;
	  AtomicLong succeed=new AtomicLong(0l);
	  AtomicLong failure=new AtomicLong(0l);
	  String category;
	  long begin;
	  
	  public void reset(){
		  if(System.currentTimeMillis()-begin>timeout){
			  begin=System.currentTimeMillis();
			  succeed.set(0);
			  failure.set(0);
		  }
	  }
	
	  public PaymentStatics(String category) {
		super();
		this.category = category;
		reset();
	}
	  
	  synchronized	boolean  shouldWarn(){
		boolean shouldCheck=(System.currentTimeMillis()-begin)>warnTimeout;
		boolean ret=false;
		if(shouldCheck){
			ret=( succeed.longValue()<succeedMin||failure.longValue()>failerMax);
		}
		logger.info("SmsWarn - "+ ret+"(time:"+((System.currentTimeMillis()-begin)/1000)+",succeed:"+succeed.longValue()+",failure:"+failure.longValue()+category );
		return ret;
	}


	@Override
	public String toString() {
		reset();
		return " "+((System.currentTimeMillis()-begin)/1000)+"/"+(timeout/1000)+" Secs  "+succeed+"/"+succeedMin+" Succeed  "+failure+"/"+failerMax+" Failure  -   < "+category+" > ";
	}
	  
	  
	

}
