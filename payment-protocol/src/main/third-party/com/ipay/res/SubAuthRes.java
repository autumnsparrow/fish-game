package com.ipay.res;

import java.util.Map;

import net.sf.json.JSONObject;

import com.ipay.bean.Sub;
import com.ipay.util.ConstValue;
import com.ipay.util.RSAHelper;

public class SubAuthRes extends IpayResponseBase {

	private Sub sub = null;
	public SubAuthRes( String responseStr ){
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
			// 交易流水号
			if( json.containsKey( ConstValue.FEE_TYPE ) ){
				sub = new Sub();
				sub.setFeeType( json.getInt( ConstValue.FEE_TYPE ) );
			}
			if( json.containsKey( ConstValue.LEFT_COUNT ) ){
				sub.setLeftCount( json.getInt( ConstValue.LEFT_COUNT ) );
			}
			if( json.containsKey( ConstValue.SUBS_STATUS ) ){
				sub.setSubStatus( json.getInt( ConstValue.SUBS_STATUS ) );
			}
			if( json.containsKey( ConstValue.END_TIME ) ){
				sub.setEndTime( json.getString( ConstValue.END_TIME ) );
			}
		}
		
		if( this.getErrorCode() != null )
			throw new RuntimeException( "请求错误：" + this.getErrMsg() );
		
		// 获取sign签名和签名类型
		String sign = dataMap.get( ConstValue.SIGN );
		
		// 验证签名
		boolean checkSign = false;
		try {
			checkSign = RSAHelper.verify( transDataStr, ConstValue.PUBLIC_KEY, sign);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( !checkSign )
			throw new RuntimeException( "验证签名失败" );
	}
	public Sub getSub() {
		return sub;
	}
	
	
}
