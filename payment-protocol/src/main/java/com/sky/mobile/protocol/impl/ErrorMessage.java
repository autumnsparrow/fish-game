/**
 * @author:sparrow
 * @date:Jul 22, 2014
 * @company:北京博志万通科技有限公司
 *
 *	
 */
package com.sky.mobile.protocol.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.HandlerException;

/**
 * @author sparrow
 *
 */

public class ErrorMessage {
	
	public static final Integer S360_APP_SECRET=Integer.valueOf(0);
	public static final Integer UC_APP_SECRET=Integer.valueOf(1);
	public static final Integer OP_APP_SECRET=Integer.valueOf(2);
	public static final Integer ANZHI_APP_SECRET=Integer.valueOf(3);
	
	
	/**
	 * vivo configurations.
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	public  class VivoKeys{
		public  final Integer INDEX=Integer.valueOf(4);
		public  final Integer channelId=Integer.valueOf(0);
		public  final Integer cpId=Integer.valueOf(1);
		public  final Integer appId=Integer.valueOf(2);
		public final  Integer notifyUrl=Integer.valueOf(3);
		public final Integer vivoServerUrl=Integer.valueOf(4);
		public final Integer cpKey=Integer.valueOf(5);
		
		/**
		 * 
		 * @return get the vivo channel id.
		 */
		public Integer getChannelId(){
			String  v=getValue(INDEX, channelId);
			return GameUtil.s2i(v);
		}
		
		/**
		 * 
		 * @return get cp id
		 */
		public String getCpId(){
			return getValue(INDEX, cpId);
		}
		
		/**
		 * 
		 * @return app id
		 */
		public String getAppId(){
			return getValue(INDEX, appId);
		}
		
		/**
		 *  
		 * @return get notify url
		 */
		public String getNotifyUrl(){
			return getValue(INDEX, notifyUrl);
		}
		
		public String getVivoServerUrl(){
			return getValue(INDEX, vivoServerUrl);
		}
		
		public String getCpKey(){
			return getValue(INDEX,cpKey);
		}
		
	};
	
	
	
	
	
	public VivoKeys vivo=new VivoKeys();
	
	
	/**
	 * 
	 * 
	 * public final static String URL_CHECKSESSION = "https://api.game.meizu.com/game/security/checksession";
	
	public final static String PARAM_SIGN = "sign";
	
	public final static String PARAM_SIGN_TYPE = "sign_type";
	
	public final static long APP_ID = 1816347L;//�滻���ڿ�����ƽ̨��������ϷID
	
	public final static String APP_KEY = "6d1e9f41d57b44d1995efcab6782a311";//�滻���ڿ�����ƽ̨��������ϷID
	
	public final static String APP_SECRET = "qrEMjQ4kXjwGdeSWsvHMtMAWE1zHgq7D"
	 * Flyme
	 */
	public class FlymeKeys{
		String checkSessionUrl;
		String appId;
		String appKey;
		String appSecret;
		public  final Integer INDEX=Integer.valueOf(5);
		public  final Integer CheckSessionUrl=Integer.valueOf(0);
		public  final Integer AppId=Integer.valueOf(1);
		public  final Integer AppKey=Integer.valueOf(2);
		public  final Integer AppSecret=Integer.valueOf(3);
		/**
		 * public check sesssion url
		 * @return
		 */
		public String getCheckSessionUrl(){
			return getValue(INDEX, CheckSessionUrl);
		}
		
		public String getAppId(){
			return getValue(INDEX, AppId);
		}
		
		public String getAppKey(){
			return getValue(INDEX, AppKey);
		}
		
		public String getAppSecret(){
			return getValue(INDEX, AppSecret);
		}
		
		
	}
	public FlymeKeys  flymeKeys=new FlymeKeys();
	
	
	
	
	
	public class KupaiKeys{
		String appSecret;
		public  final Integer INDEX=Integer.valueOf(6);
		public  final Integer AppSecret=Integer.valueOf(0);
		
		public String getAppSecret(){
			return getValue(INDEX,AppSecret);
		}
	}
	
	public KupaiKeys kupaiKeys=new KupaiKeys();
	
	
	
	public class WdjKeys{
		String appSecret;
		public  final Integer INDEX=Integer.valueOf(7);
		public  final Integer AppSecret=Integer.valueOf(0);
		public  final Integer AppKey=Integer.valueOf(1);
		public  final Integer CheckUridUrl=Integer.valueOf(2);
		
		public String getAppSecret(){
			return getValue(INDEX,AppSecret);
		}
		
		public String getAppKey(){
			return getValue(INDEX,AppKey);
		}
		
		public String getCheckUriUrl(){
			 return getValue(INDEX,CheckUridUrl);
		}
	}
	
	public WdjKeys wdjKeys=new WdjKeys();
	
	

	public class BaiduKeys{
		String appSecret;
		public  final Integer INDEX=Integer.valueOf(8);
		public  final Integer AppSecret=Integer.valueOf(0);
		
		public String getAppSecret(){
			return getValue(INDEX,AppSecret);
		}
	}
	
	public BaiduKeys baiduKeys=new BaiduKeys();
	
	
	
	public class KinliKeys{
		String appSecret;
		public  final Integer INDEX=Integer.valueOf(9);
		public  final Integer AppSecret=Integer.valueOf(0);
		
		public String getAppSecret(){
			return getValue(INDEX,AppSecret);
		}
	}
	
	public KinliKeys kinliKeys=new KinliKeys();
	
	
	/**
	 * 
	 * BUO_SECRET
浮标密钥
浮标密钥
PAY_ID
支付ID
支付ID(原昵称)
PAY_RSA_PRIVATE
支付私钥
支付私钥
PAY_RSA_PUBLIC
	 * @author sparrow
	 *
	 */
	public class HuaweiKeys{
		public  final Integer INDEX=Integer.valueOf(10);
		public  final Integer AppId=Integer.valueOf(0);
		public  final Integer BuoSecret=Integer.valueOf(1);
		//public  final Integer PayId=Integer.valueOf(2);
		public  final Integer PayRsaPrivate=Integer.valueOf(2);
		public  final Integer PayRsaPublic=Integer.valueOf(3);
		
		public String getAppId(){
			return getValue(INDEX, AppId);
		}
		
		public String getBuoSecret(){
			return getValue(INDEX, BuoSecret);
		}
		
		public String getPayRsaPrivate(){
			return getValue(INDEX, PayRsaPrivate);
		}
		public String getPayRsaPublic(){
			return getValue(INDEX, PayRsaPublic);
		}
		
	}
	public HuaweiKeys huaweiKeys=new HuaweiKeys();
	
	
	public class MiKeys{
		String appSecret;
		public  final Integer INDEX=Integer.valueOf(11);
		public  final Integer AppSecret=Integer.valueOf(0);
		
		public String getAppSecret(){
			return getValue(INDEX,AppSecret);
		}
	}
	
	public MiKeys miKeys=new MiKeys();
	
	
	public class QqKeys{
		String appSecret;
		public  final Integer INDEX=Integer.valueOf(12);
		public  final Integer AppSecret=Integer.valueOf(0);
		
		public String getAppSecret(){
			return getValue(INDEX,AppSecret);
		}
	}
	
	public QqKeys qqKeys=new QqKeys();
	
	

	public class YoukuKeys{
		String appSecret;
		public  final Integer INDEX=Integer.valueOf(13);
		public  final Integer AppSecret=Integer.valueOf(0);
		public final Integer Callback=Integer.valueOf(1);
		
		public String getAppSecret(){
			return getValue(INDEX,AppSecret);
		}
		public String getCallback(){
			return getValue(INDEX, Callback);
		}
	}
	
	public YoukuKeys youkuKeys=new YoukuKeys();
	
	
	public class MeizuKeys{
		String checkSessionUrl;
		String appId;
		String appKey;
		String appSecret;
		public  final Integer INDEX=Integer.valueOf(14);
		public  final Integer CheckSessionUrl=Integer.valueOf(0);
		public  final Integer AppId=Integer.valueOf(1);
		public  final Integer AppKey=Integer.valueOf(2);
		public  final Integer AppSecret=Integer.valueOf(3);
		/**
		 * public check sesssion url
		 * @return
		 */
		public String getCheckSessionUrl(){
			return getValue(INDEX, CheckSessionUrl);
		}
		
		public String getAppId(){
			return getValue(INDEX, AppId);
		}
		
		public String getAppKey(){
			return getValue(INDEX, AppKey);
		}
		
		public String getAppSecret(){
			return getValue(INDEX, AppSecret);
		}
		
		
	}
	public MeizuKeys  meizuKeys=new MeizuKeys();
	
	
	
	/**
	 * 
	 */
	public ErrorMessage() {
		// TODO Auto-generated constructor stub
	}
	
	private HashMap<Integer,HashMap<Integer,String>> codes;

	public HashMap<Integer, HashMap<Integer,String>> getCodes() {
		return codes;
	}

	public void setCodes(HashMap<Integer, HashMap<Integer,String>> codes) {
		this.codes = codes;
	}
	
	public String getValue(Integer code,int sequence){
		HashMap<Integer,String> errors=codes.get(code);
		String msg=errors.get(Integer.valueOf(sequence));
		return msg;
	}
	
	
	public HandlerException getException(Integer code,int sequence){
		
		String value=getValue(code, sequence);
		sequence=-1*sequence;
		HashMap<Integer,String> errors=codes.get(code);
		HandlerException handleMessage=null;
		if(errors==null){
			errors=codes.get(Integer.valueOf(-999999));
			handleMessage=new HandlerException(Integer.valueOf(-999999),errors.get(Integer.valueOf(0)) );
		}else{
			handleMessage=new HandlerException(sequence, value+String.format("- (%d,%d)", code.intValue(),sequence));
		}
		 return handleMessage;
	}
	

	
	
}
