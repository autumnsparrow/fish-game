package com.ipay.req;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.ipay.util.ConstValue;
import com.ipay.util.RSAHelper;
import com.ipay.util.http.HttpUtil;

/**
 * 发送给Ipay的请求基类，所有日志默认打到控制 台，开发者可根据自己的日志规则，替换成Log4j或者logback等
 * 
 * @author visen
 *
 */
public abstract class IpayRequestBase {

	protected Map<String,Object> paramsMap = new TreeMap<String, Object>();
	
	protected String sendRequest(){
		
		JSONObject transdataJson = JSONObject.fromObject(paramsMap);
		
		Map<String,String> httpParams = new LinkedHashMap<String,String>();
		// 初始化transdata
		String transdataStr = transdataJson.toString();		
		
		// 获取sign
		String sign;
		try {
			sign = RSAHelper.signForPKCS1( transdataStr, ConstValue.PRIVATE_KEY );
			httpParams.put( ConstValue.TRANSDATA, transdataStr );
			httpParams.put( ConstValue.SIGN, sign );
			httpParams.put( ConstValue.SIGN_TYPE, ConstValue.SIGN_TYPE_VALUE );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 发送请求，获取结果
		String httpResponse = HttpUtil.post( ConstValue.HOST + getRequestUrl(), httpParams, null );
		
		return httpResponse;
	}
	
	protected abstract String getRequestUrl();
	protected abstract boolean verifykParameter();
}
