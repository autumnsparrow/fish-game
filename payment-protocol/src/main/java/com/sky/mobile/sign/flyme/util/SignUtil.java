package com.sky.mobile.sign.flyme.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.util.Md5Encrypt;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SignUtil {
	
	private static final Log logger=LogFactory.getLog(SignUtil.class);
	
	private static final String PARAM_SIGN="sign";
	private static final String PARAM_SIGN_TYPE="sign_type";

	private SignUtil() {
	}

	public static final String concatMap(Map<String, String> params) {
		List<String> keysSet = new ArrayList<String>();
		keysSet.addAll(params.keySet());
		Collections.sort(keysSet);
		StringBuilder sb = new StringBuilder();
		for (String key : keysSet) {
			if (key.equals(PARAM_SIGN) || key.equals(PARAM_SIGN_TYPE)) {
				continue;
			}
			Object value = params.get(key);
			sb.append("&").append(key).append("=").append(value.toString());
		}

		if (sb.length() > 0 && sb.toString().startsWith("&")) {
			sb.delete(0, 1);
		}
		return sb.toString();
	}

	public static final String getSignCode(Map map, String appSecret) {
		String queryString = concatMap(map);
		String signText = queryString + ":" + appSecret;
		System.out.println("signText=" + signText);
		String serverSignCode = Md5Encrypt.md5(signText);
		return serverSignCode;
	}

	public static final String getSignCode(String queryString, String appSecret) {
		String signText = queryString + ":" + appSecret;
		String serverSignCode = Md5Encrypt.md5(signText);
		return serverSignCode;
	}

	public static final String getQueryString(Map map) {
		return concatMap(map);
	}
	
	public static void main(String args[]){
		String key="5ECvSYTEG91d4hnRcrU3lwg0BM2ooStj";
		/*
		01-16 21:40:51.259: E/meizu(6598): orderId:10017201640160901460000000006
		01-16 21:40:51.259: E/meizu(6598): sign:f5afe7039e5e08a6d1ad4ed36467e9ab
		01-16 21:40:51.259: E/meizu(6598): signType:md5
		01-16 21:40:51.259: E/meizu(6598): buyCount:1
		01-16 21:40:51.259: E/meizu(6598): cpUserInfo:AAAA
		01-16 21:40:51.259: E/meizu(6598): amount(price):30.0
		01-16 21:40:51.259: E/meizu(6598): productId:jewel_30
		01-16 21:40:51.259: E/meizu(6598): productSubject:购买 1个30元钻石礼包
		01-16 21:40:51.259: E/meizu(6598): productBody:30元钻石礼包
		01-16 21:40:51.259: E/meizu(6598): productUnit:个
		01-16 21:40:51.259: E/meizu(6598): appid:2884873
		01-16 21:40:51.259: E/meizu(6598): uid:220
		01-16 21:40:51.259: E/meizu(6598): perPrice:30.0
		01-16 21:40:51.259: E/meizu(6598): createTime:1452951651
		01-16 21:40:51.259: E/meizu(6598): payType:0
		
		app_id=2884873&buy_amount=1&cp_order_id=10017201603161001530000000035&create_time=1452953037
		&pay_type=0&product_body=30元钻石礼包&product_id=jewel_30
		&product_per_price=30.0&product_subject=购买 1个30元钻石礼包
		&product_unit=个&total_price=30.
		0&uid=114178585&user_info=AAAA
		
		app_id=464013&buy_amount=1&cp_order_id=2680&create_time=139868782768&pay_type=0&product_body=
&product_id=0&product_per_price=1.0&product_subject= 购 买 500 枚 金 币 &product_unit=
&total_price=1.0&uid=5535004&user_info=:appSecret
		*/
		Map<String,String> p=GameUtil.getMap();
		
		p.put("app_id","2884873");
		p.put("buy_amount","1");
		p.put("cp_order_id","10017201603161001530000000035");
		p.put("create_time","1452953037");
		p.put("pay_type","0");
		p.put("product_body","30元钻石礼包");
		p.put("product_id","jewel_30");
		p.put("product_per_price","30.0");
		p.put("product_subject","购买 1个30元钻石礼包");
		p.put("product_unit","");
		p.put("total_price","30.0");
		p.put("uid","114178585");
		p.put("user_info","AAAA");
		
		System.out.println(getSignCode(p, key));
		
		//f5afe7039e5e08a6d1ad4ed36467e9ab
		//f5afe7039e5e08a6d1ad4ed36467e9ab
		
		
		
	}
}

