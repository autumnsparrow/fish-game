package com.sky.mobile.protocol.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public DateUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public  static Date getCurrentDate(){
		return new java.util.Date(System.currentTimeMillis());
	}

	private static final int DAY=24*60*60*100;
	public static Date getNextDaysDate(int days){
		return new java.util.Date(System.currentTimeMillis()+days*DAY);
	}
	
	
	public static String getDate(Date date){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	public static Date getDate(String date){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date d=null;
		try {
			d = format.parse(date);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
}
