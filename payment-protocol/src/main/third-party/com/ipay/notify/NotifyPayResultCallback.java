package com.ipay.notify;

import java.util.Map;

import net.sf.json.JSONObject;

import com.ipay.res.IpayResponseBase;
import com.ipay.util.ConstValue;
import com.ipay.util.RSAHelper;

/**
 * 	transtype	交易类型				integer		必填	交易类型：
 *													0–支付交易；
 *													1–支付冲正（暂未启用）；
 *													2–契约退订
 *													3–自动续费
 * 	cporderid	商户订单号				String(64)	可选	商户订单号
 *	transid		交易流水号				String(32)	必填	计费支付平台的交易流水号
 *	appuserid	用户在商户应用的唯一标识	String(32)	必填	用户在商户应用的唯一标识
 *	appid		游戏id				String(20)	必填	平台为商户应用分配的唯一代码
 *	waresid		商品编码				integer		必填	平台为应用内需计费商品分配的编码
 *	feetype		计费方式				integer		必填	计费方式，具体定义见附录
 *	money		交易金额				Float(6,2)	必填	本次交易的金额
 *	currency	货币类型				String(32)	必填	货币类型以及单位：RMB – 人民币（单位：元）
 *	result		交易结果				integer		必填	交易结果：
 *													0–交易成功
 *													1–交易失败
 *	transtime	交易完成时间			String(20)	必填	交易时间格式：yyyy-mm-dd hh24:mi:ss
 *	cpprivate	商户私有信息			String(64)	可选	商户私有信息
 *	paytype		支付方式				integer		可选	支付方式，具体定义见附录 
 *
 * 
 * @author visen
 *
 */
public class NotifyPayResultCallback {

	private Integer transType = null;
	private String cpOrderId  = null;
	private String transId = null;
	private String appUserId = null;
	private String appId = null;
	private Integer waresId = null;
	private Integer feeType = null;
	private Float money = null;
	private String currency = null;
	private Integer result = null;
	private String transTime = null;
	private String cpPrivateInfo = null;
	private Integer payType = null;
	
	public NotifyPayResultCallback( String transDataStr, String sign ){
		_init( transDataStr, sign );
	}
	
	public NotifyPayResultCallback( String requestStr ){
		Map<String,String> dataMap = IpayResponseBase._parseResStr( requestStr );
		
		String transDataStr = dataMap.get( ConstValue.TRANSDATA );
		// 获取sign签名和签名类型
		String sign = dataMap.get( ConstValue.SIGN );
		
		_init( transDataStr, sign );
	}
	
	private void _init( String transDataStr, String sign ){

		// 验证签名
		boolean checkSign = false;
		try {
			checkSign = RSAHelper.verify( transDataStr, ConstValue.PUBLIC_KEY,sign );
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( !checkSign )
			throw new RuntimeException( "验证签名失败" );

		if( transDataStr != null ){
			JSONObject json = JSONObject.fromObject( transDataStr );
			if( json.containsKey( ConstValue.TRANS_TYPE) )
				this.transType = json.getInt( ConstValue.TRANS_TYPE );
			if( json.containsKey( ConstValue.CP_ORDER_ID) )
				this.cpOrderId = json.getString( ConstValue.CP_ORDER_ID );
			
			if( json.containsKey( ConstValue.TRANSID) )
				this.transId = json.getString( ConstValue.TRANSID );
			
			if( json.containsKey( ConstValue.APP_USER_ID) )
				this.appUserId = json.getString( ConstValue.APP_USER_ID );
			
			if( json.containsKey( ConstValue.WARES_ID) )
				this.waresId = json.getInt( ConstValue.WARES_ID );
			
			if( json.containsKey( ConstValue.FEE_TYPE) )
				this.feeType = json.getInt( ConstValue.FEE_TYPE );
			
			if( json.containsKey( ConstValue.MONEY) )
				this.money = Float.valueOf( json.getString( ConstValue.MONEY ) );
			
			if( json.containsKey( ConstValue.CURRENCY) )
				this.currency = json.getString( ConstValue.CURRENCY );
			
			if( json.containsKey( ConstValue.RESULT) )
				this.result = json.getInt( ConstValue.RESULT );
			
			if( json.containsKey( ConstValue.TRANS_TIME) )
				this.transTime = json.getString( ConstValue.TRANS_TIME );
			if( json.containsKey( ConstValue.CP_PRIVATE) )
				this.cpPrivateInfo = json.getString( ConstValue.CP_PRIVATE );
			if( json.containsKey( ConstValue.PAY_TYPE) )
				this.payType = json.getInt( ConstValue.PAY_TYPE );
			if( json.containsKey( ConstValue.APP_ID) )
				this.appId = json.getString( ConstValue.APP_ID );
			
		}
		
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
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

	public Integer getWaresId() {
		return waresId;
	}

	public void setWaresId(Integer waresId) {
		this.waresId = waresId;
	}

	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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
		return cpPrivateInfo;
	}

	public void setCpPrivateInfo(String cpPrivateInfo) {
		this.cpPrivateInfo = cpPrivateInfo;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
	
}
