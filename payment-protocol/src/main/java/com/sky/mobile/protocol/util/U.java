/**
 * 
 */
package com.sky.mobile.protocol.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.ibatis.session.AutoMappingBehavior;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.SpringContext;
import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.HandlerException;
import com.sky.mobile.protocol.ProtocolResponseEntry;
import com.sky.mobile.protocol.domain.SafeCenterNotify;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.protocol.service.SafeCenterNotifyService;


/**
 * @author sparrow
 *
 */
public class U {

	/**
	 * 
	 */
	public U() {
		// TODO Auto-generated constructor stub
	}
	
	public static PaymentOrderService getPaymentOrderService(){
		return (PaymentOrderService)SpringContext.getBean("paymentOrderService");
	}
	
	public static SafeCenterNotifyService getSafeCenterNotifyService(){
		return (SafeCenterNotifyService)SpringContext.getBean("safeCenterNotifyService");
	}
	
	public static HandlerException throwException(Integer code,int sequence){
		return U.getErrorMessage().getException(code, sequence);
	}
	
	public static ErrorMessage getErrorMessage(){
		return (ErrorMessage)SpringContext.getBean("errorMessage");
	}
	
	public static <T> T o(Class<?> t){
		return GameUtil.obtain(t);
	}
	
	public static Integer parser(String i){
		return Integer.valueOf(i);
	}
	
	public static <T> void parse(T t,ProtocolResponseEntry responseEntry){
		String json=U.parse(t);
		
		responseEntry.setContent(json);
		responseEntry.setTimestamp(System.currentTimeMillis());
		
	}
	
	public static <T> String parse(T t){
		return GameContextGlobals.getJsonConvertor().format(t);
	}
	
	public static <T> T parse(String json,Class<?> t){
		
		T req=GameContextGlobals.getJsonConvertor().convert(json, t);
		return req;
	}
	
	
	
	public static int getCarrierType(String imsi){
		return 1;
	}
	
	public static Date parseDate(String date){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
		Date d=null;
		try {
			d = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			d=new Date();
		}
		return d;
	}
	private  static final Long MAX_SERIAL=9999999900L;
	private  static final Long MIN_SERIAL=0L;
	private static final AtomicLong serial=new AtomicLong(MIN_SERIAL.longValue());
	private static final AtomicLong baiduSerial=new AtomicLong(MIN_SERIAL.longValue());
	
	private static String currentTimeStamp(){
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
		String o=df.format(new Date());
		return o;
	}
	
	
	private static String currentTimeStampBaidu(){
		SimpleDateFormat df=new SimpleDateFormat("MMddHHss");
		String o=df.format(new Date());
		return o;
	}
	
	
	public static synchronized String generateOrderIdBaidu(){
		
		String pattern="%s%05d";
		if(serial.longValue()>99999){
			serial.set(MIN_SERIAL.longValue());
		}
		String orderId=String.format(pattern, currentTimeStampBaidu(),serial.getAndIncrement());
		return orderId;
	}
	/**
	 * 
	 * orderId =[ 5 channelCarrierId + yyyymmddhhMMss + 10 serial
	 * 
	 * @param channelCarrierId
	 * @return
	 */
	
	
	public static synchronized String generateOrderId(Integer channelCarrierId){
		
		String pattern="%05d%s%010d";
		if(serial.longValue()>MAX_SERIAL.longValue()){
			serial.set(MIN_SERIAL.longValue());
		}
		String orderId=String.format(pattern, channelCarrierId.intValue(),currentTimeStamp(),serial.getAndIncrement());
		return orderId;
	}
	
	public static void main(String args[]){
		//System.out.println(U.parser("173"));
		Long channelCarrierId=5L;
//		long c=System.currentTimeMillis();
//		List<String> orderIds=new ArrayList<String>(100000);
//		for(int i=0;i<100000;i++){
//			orderIds.add(generateOrderId(channelCarrierId));
//		}
//		System.out.println("time elpase - "+(System.currentTimeMillis()-c));
//		c=System.currentTimeMillis();
//		for(String s:orderIds){
//			System.out.println(s);
//		}
//		System.out.println("time elpase - "+(System.currentTimeMillis()-c));
		
		
	}
	
	
	
}
