package com.ipay.res;

import java.util.Map;

import net.sf.json.JSONObject;

import com.ipay.util.ConstValue;
import com.ipay.util.RSAHelper;

/**
 * cporderid	商户订单号				String(64)	必填	商户订单号
 *	transid		交易流水号				String(32)	必填	计费支付平台的交易流水号
 *	appuserid	用户在商户应用的唯一标识	String(32)	必填	用户在商户应用的唯一标识
 *	appid		游戏id				String(20)	必填	平台为商户应用分配的唯一代码
 *	waresid		商品编码				integer		必填	平台为应用内需计费商品分配的编码
 *	feetype		计费方式				integer		必填	计费方式，具体定义见附录
 *	money		交易金额				Float(6,2)	必填	本次交易的金额
 *	currency	货币类型				String(32)	必填	货币类型以及单位：RMB – 人民币（单位：元）
 *	result		交易结果				integer		必填	交易结果：0–交易成功；2–待支付
 *	transtime	交易完成时间			String(20)	必填	交易时间格式：yyyy-mm-dd hh24:mi:ss
 *	cpprivate	商户私有信息			String(64)	可选	商户私有信息
 *	paytype		支付方式				integer		可选	支付方式，具体定义见附录
 *
 * 
 * @author visen
 *
 */
public class QueryPayResultRes extends IpayResponseBase {

	private String cpOrderId 	= null;
	private String transId 		= null;
	private String appUserId 	= null;
	private String appId		= null;
	private String waresId		= null;
	private String feeType 		= null;
	private Float  money		= null;
	private Integer result		= null;
	private String transTime	= null;
	private String cpPrivate	= null;
	private Integer paytype		= null;
	private String currency     = null;
	
	public QueryPayResultRes( String responseStr ){
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
			
			if( json.containsKey( ConstValue.CP_ORDER_ID ) )
				this.setCpOrderId( json.getString( ConstValue.CP_ORDER_ID ) );			
			if( json.containsKey( ConstValue.TRANSID ) )
				this.setTransId( json.getString( ConstValue.TRANSID ) );			
			if( json.containsKey( ConstValue.APP_USER_ID ) )
				this.setAppUserId( json.getString( ConstValue.APP_USER_ID ) );			
			if( json.containsKey( ConstValue.APP_ID ) )
				this.setAppId( json.getString( ConstValue.APP_ID ) );			
			if( json.containsKey( ConstValue.WARES_ID ) )
				this.setWaresId( json.getString( ConstValue.WARES_ID ) );			
			if( json.containsKey( ConstValue.FEE_TYPE ) )
				this.setFeeType( json.getString( ConstValue.FEE_TYPE ) );
			if( json.containsKey( ConstValue.MONEY ) )
				this.setMoney( Float.valueOf( json.getString( ConstValue.MONEY ) ) );			
			if( json.containsKey( ConstValue.RESULT ) )
				this.setResult( json.getInt( ConstValue.RESULT ) );			
			if( json.containsKey( ConstValue.TRANS_TIME ) )
				this.setTransTime( json.getString( ConstValue.TRANS_TIME ) );			
			if( json.containsKey( ConstValue.CP_PRIVATE ) )
				this.setCpPrivateInfo( json.getString( ConstValue.CP_PRIVATE ) );			
			if( json.containsKey( ConstValue.PAY_TYPE ) )
				this.setPaytype( json.getInt( ConstValue.PAY_TYPE ) );		
			if( json.containsKey( ConstValue.CURRENCY ) )
				this.setCurrency( json.getString( ConstValue.CURRENCY ) );		
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

	public String getCpOrderId() {
		return cpOrderId;
	}

	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getWaresId() {
		return waresId;
	}

	public void setWaresId(String waresId) {
		this.waresId = waresId;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getCpPrivateInfo() {
		return cpPrivate;
	}

	public void setCpPrivateInfo(String cpPrivate) {
		this.cpPrivate = cpPrivate;
	}

	public Integer getPaytype() {
		return paytype;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
