package com.ipay.req;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ipay.res.OrderRes;
import com.ipay.util.ConstValue;
import com.sky.game.context.util.G;

/**
 * CP服务端下单接口
 * appId			必填		应用ID
 * waresId			必填		商品编号
 * cpOrderId		必填		商户订单号
 * currency			必填		货币类型（默认值为RMB）
 * appuserid		必填		用户在商户应用的唯一标识，建议为用户帐号。对于游戏，需要区分到不同区服，#号分隔；比如游戏帐号abc在01区，则传入“abc#01”
 * 
 * waresname		可选		商品名称，对于消费型_应用传入价格的计费方式有效，如果不传则展示后台设置的商品名称
 * price			可选		支付金额，对于消费型_应用传入价格的计费方式有效，其它计费方式不需要传入本参数
 * cpprivateinfo	可选		商户私有信息，支付完成后发送支付结果通知时会透传给商户
 * notifyurl		可选		商户服务端接收支付结果通知的地址
 * 
 * @author visen
 *
 */
public class OrderReq extends IpayRequestBase {
	
	private static final Log logger=LogFactory.getLog(OrderReq.class);
	
	@Override
	protected String getRequestUrl() {
		return "/payapi/order";
	}

	/**
	 * 验证参数正确性，同时初始化部分默认值
	 * 
	 */
	@Override
	protected boolean verifykParameter() {
		if( !this.paramsMap.containsKey( ConstValue.CURRENCY ) )
			this.paramsMap.put( ConstValue.CURRENCY, "RMB" );
		
		return this.paramsMap.containsKey( ConstValue.APP_ID) &&
				this.paramsMap.containsKey( ConstValue.WARES_ID) &&
				this.paramsMap.containsKey( ConstValue.CP_ORDER_ID) &&
				this.paramsMap.containsKey( ConstValue.APP_USER_ID); 
	}
	
	public OrderRes send(){
		if( !this.verifykParameter() )
			throw new IllegalArgumentException( "参数错误" );
		
		String responseStr = this.sendRequest();
			if( responseStr == null || responseStr.trim().length() <= 0 )
			throw new RuntimeException( "返回值为空，请检查请求参数和网络后重试" );
		
		logger.info(responseStr);
		
		return new OrderRes( responseStr );
	}

	public String getAppId() {
		return String.valueOf( this.paramsMap.get( ConstValue.APP_ID ) );
	}

	public void setAppId(String appId) {
		this.paramsMap.put( ConstValue.APP_ID, appId );
	}

	public Integer getWaresId() {
		return Integer.valueOf( this.paramsMap.get( ConstValue.WARES_ID ).toString() );
	}

	public void setWaresId(Integer waresId) {
		this.paramsMap.put( ConstValue.WARES_ID, waresId );
	}

	public String getWarasName() {
		if( !this.paramsMap.containsKey( ConstValue.WARES_NAME ) )
			return null;
		return String.valueOf( this.paramsMap.get( ConstValue.WARES_NAME ) );
	}

	public void setWarasName(String warasName) {
		this.paramsMap.put( ConstValue.WARES_NAME, warasName );
	}

	public String getCpOrderId() {
		return String.valueOf( this.paramsMap.get( ConstValue.CP_ORDER_ID ) );
	}

	public void setCpOrderId(String cpOrderId) {
		this.paramsMap.put( ConstValue.CP_ORDER_ID, cpOrderId );
	}

	public Float getPrice() {
		if( this.paramsMap.containsKey( ConstValue.PRICE ) )
			return Float.valueOf( this.paramsMap.get( ConstValue.PRICE).toString() );
		
		return null;
	}

	public void setPrice(Float price) {
		this.paramsMap.put( ConstValue.PRICE, price );
	}

	public String getCurrency() {
		return String.valueOf( this.paramsMap.get( ConstValue.CURRENCY ) );
	}

	public void setCurrency(String currency) {
		this.paramsMap.put( ConstValue.CURRENCY, currency );
	}

	public String getAppUserId() {
		return String.valueOf( this.paramsMap.get( ConstValue.APP_USER_ID ) );
	}

	public void setAppUserId(String appUserId) {
		this.paramsMap.put( ConstValue.APP_USER_ID, appUserId );
	}

	public String getCpPrivateInfo() {
		return String.valueOf( this.paramsMap.get( ConstValue.CP_PRIVATE_INFO ) );
	}

	public void setCpPrivateInfo(String cpPrivateInfo) {
		this.paramsMap.put( ConstValue.CP_PRIVATE_INFO, cpPrivateInfo );
	}

	public String getNotifyUrl() {
		return String.valueOf( this.paramsMap.get( ConstValue.NOTIFY_URL ) );
	}

	public void setNotifyUrl(String notifyUrl) {
		this.paramsMap.put(ConstValue.NOTIFY_URL, notifyUrl );
	}

	
	public static void main(String args[]){
		OrderReq o=G.o(OrderReq.class);
		
		
		o.setWaresId(2);
		o.setWarasName("6元钻石");
		o.setPrice(Float.valueOf(.01f));
		
		o.setCpOrderId("10024201601280028430000020110");
		o.setAppId(ConstValue.AppId);
		o.setAppUserId("1102221");
		o.setCurrency("RMB");
		o.setNotifyUrl("http://payment.toyo.cool:8080/payment/lenovo/notify");
		
		OrderRes resp=o.send();
		System.out.println(resp.getTransid());
		
		
		
	}
	
}
