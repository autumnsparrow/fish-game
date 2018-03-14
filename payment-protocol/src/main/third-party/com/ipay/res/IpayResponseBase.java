package com.ipay.res;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.ipay.util.ConstValue;

public abstract class IpayResponseBase {

	private String errorCode = null;
	private String errMsg = null;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public boolean isError(){
		return this.errorCode != null;
	}
	
	/**
	 * 将结果解析成transdata、sign、signtype三个字符串置入map中
	 * 返回字符串样例：transdata={"appid":"123","logintoken":"3213213"}&sign=xxxxxx&signtype=RSA
	 * 
	 * @param resStr
	 * @return
	 */
	public static Map<String,String> _parseResStr( String resStr ){
		if( resStr == null || resStr.trim().length() <= 0 )
			return null;
		
		int lastIndex = 0;
//		String tmpStr = "transdata={\"appid\":\"123\",\"logintoken\":\"3213213\"}&sign=xxxxxx&signtype=RSA";
		String tmpStr = resStr;
		Map<String,String> retMap = new HashMap<String,String>();
		
		// 从后往前解析，先解析出signtype
		String indexStr = "&" + ConstValue.SIGN_TYPE + "=";
		lastIndex = tmpStr.lastIndexOf( indexStr );
		if( lastIndex >= 0 ){
			retMap.put( ConstValue.SIGN_TYPE, tmpStr.substring( lastIndex + indexStr.length() ) );
			
			// 截断signtype的内容
			tmpStr = tmpStr.substring( 0, lastIndex );
		}
		
		// 解析sign
		indexStr = "&" + ConstValue.SIGN + "=";
		lastIndex = tmpStr.lastIndexOf( indexStr );
		if( lastIndex >= 0 ){
			String urldecodeStr = "";
				try {
					urldecodeStr = URLDecoder.decode(tmpStr.substring( lastIndex + indexStr.length() ), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			retMap.put( ConstValue.SIGN,  urldecodeStr);
			
			// 截断sign的内容
			tmpStr = tmpStr.substring( 0, lastIndex );
		}
		
		// 解析transdata
		indexStr = ConstValue.TRANSDATA + "=";
		lastIndex = tmpStr.lastIndexOf( indexStr );
		if( lastIndex >= 0 ){
			String urlDecodeStr = "";
			try {
				urlDecodeStr = URLDecoder.decode(tmpStr.substring( lastIndex + indexStr.length() ),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			retMap.put( ConstValue.TRANSDATA,  urlDecodeStr);
		}else {//为了防止出现&transdata的情况
			indexStr = "&" + indexStr;
			lastIndex = tmpStr.lastIndexOf( lastIndex );
			if (lastIndex >= 0) {
				retMap.put( ConstValue.TRANSDATA, tmpStr.substring( lastIndex + indexStr.length() ) );
			}
		}
		
		
		return retMap;
	}
}
