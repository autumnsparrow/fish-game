package com.ipay.req;

import com.ipay.res.TokenCheckRes;

public class TokenCheckReq extends IpayRequestBase{
	
	public TokenCheckReq() {

	}

	public void setAppId( String appid ){
		this.paramsMap.put( "appid", appid );
	}
	
	public void setLoginToken( String loginToken ){
		this.paramsMap.put( "logintoken", loginToken );
	}

	@Override
	public String getRequestUrl() {
		return "/payapi/tokencheck";
	}

	public TokenCheckRes send(){
		if( !this.verifykParameter() )
			throw new IllegalArgumentException( "参数错误" );
		
		String responseStr = this.sendRequest();
		if( responseStr == null || responseStr.trim().length() <= 0 )
			throw new RuntimeException( "Token check 返回值为空，请检查请求参数和网络后重试" );
		
		
		
		return new TokenCheckRes( responseStr );
	}
	
	@Override
	protected boolean verifykParameter() {
		if( !this.paramsMap.containsKey( "appid" ) )
			return false;
		if( !this.paramsMap.containsKey( "logintoken" ) )
			return false;
		
		return true;
	}
}
