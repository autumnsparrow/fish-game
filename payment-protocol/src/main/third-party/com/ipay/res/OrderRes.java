package com.ipay.res;

import java.util.Map;

import net.sf.json.JSONObject;

import com.ipay.util.ConstValue;
import com.ipay.util.RSAHelper;

/**
 * 下单返回结果
 * transid	交易流水号
 * 
 * @author visen
 *
 */
public class OrderRes extends IpayResponseBase {

	private String transid = null;
	
	public OrderRes( String responseStr ){
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
			if( json.containsKey( ConstValue.TRANSID ) )
				this.setTransid( json.getString( ConstValue.TRANSID ) );
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

	public String getTransid() {
		return transid;
	}

	public void setTransid(String transid) {
		this.transid = transid;
	}
	
	
}
