/**
 * 
 */
package com.sky.mobile.protocol;

/**
 * @author sparrow
 *
 */
public interface IHandler {
	public static final Integer STATUS_OK=Integer.valueOf(0);
	
	
	public static final Integer STATUS_FAILED=Integer.valueOf(-90001);
	public static final Integer STATUS_DES_KEY_EMPTY=Integer.valueOf(-90002);
	public static final Integer STATUS_DECRYPT_FAILED=Integer.valueOf(-90003);
	public static final Integer STATUS_ENCRYPT_FAILED=Integer.valueOf(-90004);
	public static final Integer STATUS_PROTOCOL_HANDLE_EMPTY=Integer.valueOf(-90005);
	public static final Integer STATUS_PROTOCOL_ERROR=Integer.valueOf(-90006);
	
	public static final Integer STATUS_NO_TRANSCODE =Integer.valueOf(999999999);// "-90009";
	// define the error message.
	
	
	public static final Integer PAYMENT_2000=Integer.valueOf(2000);
	public static final Integer PAYMENT_2001=Integer.valueOf(2001);
	public static final Integer PAYMENT_2002=Integer.valueOf(2002);
	public static final Integer PAYMENT_2003=Integer.valueOf(2003);
	public static final Integer PAYMENT_2004=Integer.valueOf(2004);
	
	
	
	public Integer getCode();
	public boolean handle(ProtocolRequestEntry request,ProtocolResponseEntry response) throws HandlerException;

}
