/**
 * 
 */
package com.sky.mobile.protocol;

/**
 * @author sparrow
 *
 */
public class HandlerException extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8115145962063425113L;
	int status;

	
	
	public int getStatus() {
		return status;
	}

	/**
	 * 
	 */
	public HandlerException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public HandlerException(int status,String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		this.status=status;
	}

	/**
	 * @param arg0
	 */
	public HandlerException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public HandlerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
