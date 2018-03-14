package com.ipay.req;

import com.ipay.res.CancelSubRes;
import com.ipay.util.ConstValue;

public class CancelSubReq extends IpayRequestBase {

	
	public CancelSubRes send(){
//		if( !this.verifykParameter() )
//			throw new IllegalArgumentException( "参数错误" );
		
		String responseStr = this.sendRequest();
		if( responseStr == null || responseStr.trim().length() <= 0 )
			throw new RuntimeException( "返回值为空，请检查请求参数和网络后重试" );		
		
		return new CancelSubRes( responseStr );
	}
	
	@Override
	protected String getRequestUrl() {
		return "/payapi/subcancel";
	}
	
	@Override
	protected boolean verifykParameter() {
		return this.paramsMap.containsKey( ConstValue.APP_ID )
				&& this.paramsMap.containsKey( ConstValue.WARES_ID )
				&& this.paramsMap.containsKey( ConstValue.APP_USER_ID );
	}

	public void setAppUserId( String appUserId ){
		this.paramsMap.put( ConstValue.APP_USER_ID, appUserId );
	}
	public String getAppUserId(){
		return String.valueOf( this.paramsMap.get( ConstValue.APP_USER_ID ) );
	}
	public void setAppId( String appId ){
		this.paramsMap.put( ConstValue.APP_ID, appId );
	}
	public String getAppId(){
		return String.valueOf( this.paramsMap.get( ConstValue.APP_ID ) );
	}
	public void setWaresId( Integer waresId ){
		this.paramsMap.put( ConstValue.WARES_ID, waresId );
	}
	public Integer getWaresId(){
		return Integer.valueOf( this.paramsMap.get( ConstValue.WARES_ID ).toString() );
	}

}
