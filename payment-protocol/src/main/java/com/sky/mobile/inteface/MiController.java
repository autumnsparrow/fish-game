/**
 * 
 */
package com.sky.mobile.inteface;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.logging.ErrorManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.sky.mobile.inteface.PayloadPostData.IPostDataParser;
import com.sky.mobile.protocol.domain.MiNotify;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.persistence.MiNotifyMapper;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.sign.mi.MiSignUtil;

/**
 * @author sparrow
 *
 */

@RequestMapping("/mi")
@Controller

public class MiController {

	@Autowired
	ErrorMessage errorMessage;
	@Autowired
	MiNotifyMapper miNotifyMapper;
	
	@Autowired
	PaymentOrderService paymentOrderService;
	
	private static final Log logger=LogFactory.getLog(MiController.class);
	public static class MiPaymentResponse {
		String errcode;
		String errMsg;
		public String getErrcode() {
			return errcode;
		}
		public void setErrcode(String errcode) {
			this.errcode = errcode;
		}
		public String getErrMsg() {
			return errMsg;
		}
		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}
		
	}
	
	/**
	 * 
	 * 
appId	必须	游戏ID
cpOrderId	必须	开发商订单ID
cpUserInfo	可选	开发商透传信息
uid	必须	用户ID
orderId	必须	游戏平台订单ID
orderStatus	必须	订单状态，TRADE_SUCCESS 代表成功
payFee	必须	支付金额,单位为分,即0.01 米币。
productCode	必须	商品代码
productName	必须	商品名称
productCount	必须	商品数量
payTime	必须	支付时间,格式 yyyy-MM-dd HH:mm:ss
orderConsumeType	可选	订单类型：
10：普通订单
11：直充直消订单
partnerGiftConsume	可选	使用游戏券金额 （如果订单使用游戏券则有,long型），如果有则参与签名
signature	必须	签名,签名方法见后面说明
	 * @author sparrow
	 *
	 */
	public static class MiPaymentRequest implements IPostDataParser{
		String appId;
		String cpOrderId;
		String cpUserInfo;
		String uid;
		String orderId;
		String orderStatus;
		String payFee;
		String productCode;
		String productName;
		int productCount;
		String payTime;
		String orderConsumeType;
		String partnerGiftConsume;
		String signature;
		public String getAppId() {
			return appId;
		}
		public void setAppId(String appId) {
			this.appId = appId;
		}
		public String getCpOrderId() {
			return cpOrderId;
		}
		public void setCpOrderId(String cpOrderId) {
			this.cpOrderId = cpOrderId;
		}
		public String getCpUserInfo() {
			return cpUserInfo;
		}
		public void setCpUserInfo(String cpUserInfo) {
			this.cpUserInfo = cpUserInfo;
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
		public String getPayFee() {
			return payFee;
		}
		public void setPayFee(String payFee) {
			this.payFee = payFee;
		}
		public String getProductCode() {
			return productCode;
		}
		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public int getProductCount() {
			return productCount;
		}
		public void setProductCount(int productCount) {
			this.productCount = productCount;
		}
		public String getPayTime() {
			return payTime;
		}
		public void setPayTime(String payTime) {
			this.payTime = payTime;
		}
		public String getOrderConsumeType() {
			return orderConsumeType;
		}
		public void setOrderConsumeType(String orderConsumeType) {
			this.orderConsumeType = orderConsumeType;
		}
		public String getPartnerGiftConsume() {
			return partnerGiftConsume;
		}
		public void setPartnerGiftConsume(String partnerGiftConsume) {
			this.partnerGiftConsume = partnerGiftConsume;
		}
		public String getSignature() {
			return signature;
		}
		public void setSignature(String signature) {
			this.signature = signature;
		}
		@Override
		public String toString() {
			return "MiPaymentRequest [appId=" + appId + ", cpOrderId="
					+ cpOrderId + ", cpUserInfo=" + cpUserInfo + ", uid=" + uid
					+ ", orderId=" + orderId + ", orderStatus=" + orderStatus
					+ ", payFee=" + payFee + ", productCode=" + productCode
					+ ", productName=" + productName + ", productCount="
					+ productCount + ", payTime=" + payTime
					+ ", orderConsumeType=" + orderConsumeType
					+ ", partnerGiftConsume=" + partnerGiftConsume
					+ ", signature=" + signature + "]";
		}
		
		public Map<String,String> getParameters(){
			Map<String,String> p=GameUtil.getMap();
			
			p.put("appId",this.appId);
			p.put("cpOrderId",cpOrderId);
			p.put("cpUserInfo",cpUserInfo);
			p.put("orderConsumeType",orderConsumeType);
			p.put("orderId",orderId);
			p.put("orderStatus",orderStatus);
			p.put("partnerGiftConsume",partnerGiftConsume);
			p.put("payFee",String.valueOf(payFee));
			p.put("payTime",payTime);
			p.put("productCode",productCode);
			p.put("productCount",String.valueOf(productCount));
			p.put("productName",productName);
			p.put("signature",signature);
			p.put("uid",uid);
			
			return p;
		}
		
		PayloadPostData payloadData;
		@Override
		public MiPaymentRequest parse(PayloadPostData data) {
			// TODO Auto-generated method stub
			this.payloadData=data;
			this.appId=data.s("appId");
			this.cpOrderId=data.s("cpOrderId");
			this.cpUserInfo=data.s("cpUserInfo");
			this.orderConsumeType=data.s("orderConsumeType");
			this.orderId=data.s("orderId");
			this.orderStatus=data.s("orderStatus");
			this.partnerGiftConsume=data.s("partnerGiftConsume");
			this.payFee=data.s("payFee");
			this.payTime=data.s("payTime");
			this.productCode=data.s("productCode");
			this.productCount=data.i("productCount");
			this.productName=data.s("productName");
			this.signature=data.s("signature");
			this.uid=data.s("uid");
			
			return this;
		}
		
		
		
	}
	
	
	@RequestMapping(value = "/notify")
	@ResponseBody
	public ResponseEntity<String> notify(MiPaymentRequest req) {
		
		//logger.info(payload);
		MiPaymentResponse resp=G.o(MiPaymentResponse.class);
		
		//MiPaymentRequest req=G.o(MiPaymentRequest.class);
		//PayloadPostData data=new PayloadPostData(payload);
		//req.parse(data);
		
		//content="appId=2882303761517430958&cpOrderId=10018201606200301230000000048&orderId=21145327358411404687&orderStatus=TRADE_SUCCESS&payFee=100.0&payTime=2016-01-20 15:08:37&productCode=fish_328yuan&productCount=1&productName=328元钻石&uid=88971332";
		try {
			String content=MiSignUtil.getSignData(req.getParameters());
			String sign=MiSignUtil.HmacSHA1Encrypt(content, errorMessage.miKeys.getAppSecret());
			if(req.getSignature().equals(sign)){
				
				MiNotify o=G.o(MiNotify.class);
				o.setAppId(req.getAppId());
				o.setCpOrderId(req.getCpOrderId());
				o.setCpUserInfo(req.getCpUserInfo());
				o.setCreateTime(new Date());
				o.setOrderConsumeType(req.getOrderConsumeType());
				o.setOrderId(req.getOrderId());
				o.setOrderStatus(req.getOrderStatus());
				o.setPartnerGiftConsume(req.getPartnerGiftConsume());
				o.setPayFee((req.getPayFee()));
				o.setPayTime(req.getPayTime());
				o.setProductCode(req.getProductCode());
				o.setProductCount(req.getProductCount());
				o.setProductName(req.getProductName());
				o.setUid(req.getUid());
				//o.set
			
				Long id=null;
				miNotifyMapper.insertSelective(o);
				id=o.getId();
				String localOrderId=req.getCpOrderId();
				String status="TRADE_SUCCESS".equals(req.getOrderStatus())?"200":"";
				
				paymentOrderService.updateOrderState(id, localOrderId, status, MiController.class.getSimpleName());
				resp.setErrcode("200");
				resp.setErrMsg("OK");
				
			}else{
				logger.info("raw:"+content+",local.sign:"+sign+",server.sign:"+req.getSignature());
			
				resp.setErrcode("1525");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		String json = GameContextGlobals.getJsonConvertor().format(resp);
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
}
