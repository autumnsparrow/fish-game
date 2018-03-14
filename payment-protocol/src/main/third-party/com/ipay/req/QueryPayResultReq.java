package com.ipay.req;

import com.ipay.res.QueryPayResultRes;
import com.ipay.util.ConstValue;

/**
 * 查询支付结果
 * 
 * @author visen
 *
 */
public class QueryPayResultReq extends IpayRequestBase {

	@Override
	protected String getRequestUrl() {
		return "/payapi/queryresult";
	}

	@Override
	protected boolean verifykParameter() {
		return this.paramsMap.containsKey( ConstValue.APP_ID )&& this.paramsMap.containsKey( ConstValue.CP_ORDER_ID );
	}
	
	public QueryPayResultRes send(){
		if( !this.verifykParameter() )
			throw new IllegalArgumentException( "参数错误" );
		
		String responseStr = this.sendRequest();
		if( responseStr == null || responseStr.trim().length() <= 0 )
			throw new RuntimeException( "返回值为空，请检查请求参数和网络后重试" );
		
		
		
		return new QueryPayResultRes( responseStr );
	}
	
	public void setAppId( String appId ){
		this.paramsMap.put( ConstValue.APP_ID, appId );
	}
	
	public String getAppId(){
		return String.valueOf( this.paramsMap.get( ConstValue.APP_ID ) );
	}
	
	public String getCpOrderId(){
		return String.valueOf( this.paramsMap.get( ConstValue.CP_ORDER_ID ) );
	}
	
	public void setCpOrderId( String orderId ){
		this.paramsMap.put( ConstValue.CP_ORDER_ID, orderId );
	}

}
