package com.ipay.res;

import java.util.Map;

import net.sf.json.JSONObject;

import com.ipay.util.ConstValue;
import com.ipay.util.RSAHelper;

/**
 * 验证登录Token接口的返回值，实例化，方便外界调用
 * 
 * @author visen
 *
 */
public class TokenCheckRes extends IpayResponseBase{

	private String loginName = null;
	private String userId 	 = null;
	
	public TokenCheckRes(){
	}
	
	/**
	 * 通过服务端返回的字符串值来初始化
	 * 
	 * @param responseStr
	 */
	public TokenCheckRes( String responseStr ){
		Map<String,String> dataMap = super._parseResStr( responseStr );
		String transDataStr = dataMap.get( ConstValue.TRANSDATA );
		if( transDataStr != null ){
			JSONObject json = JSONObject.fromObject( transDataStr );
			// 检查code
			if( json.containsKey( ConstValue.CODE ) )
				this.setErrorCode( json.getString( ConstValue.CODE ) );
			// 检查errmsg
			if( json.containsKey( ConstValue.ERROR_MSG ) )
				this.setErrMsg( json.getString( ConstValue.ERROR_MSG ) );
			// 检查loginname
			if( json.containsKey( ConstValue.LOGIN_NAME ) )
				this.setLoginName( json.getString( ConstValue.LOGIN_NAME ) );
			// 检查userid
			if( json.containsKey( ConstValue.USER_ID ) )
				this.setUserId( json.getString( ConstValue.USER_ID ) );
		}
		
		if( this.getErrorCode() != null )
			throw new RuntimeException( "请求错误：" + this.getErrMsg() );
		
		// 获取sign签名和签名类型
		String sign = dataMap.get( ConstValue.SIGN );
		
		// 验证签名
		boolean checkSign = false;
		try {
			checkSign = RSAHelper.verify( transDataStr, ConstValue.PUBLIC_KEY,sign );
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( !checkSign )
			throw new RuntimeException( "验证签名失败" );
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
