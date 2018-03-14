package com.sky.mobile.inteface;

/**
 * 
 * 
 * 
 */

import java.util.Map;

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

import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.service.UcNotifyService;
import com.sky.mobile.protocol.util.Md5Encrypt;
import com.sky.mobile.protocol.util.U;
@RequestMapping("/uc")
@Controller
public class UcConstroller {
	
	
	
	
private static final Log logger=LogFactory.getLog(UcConstroller.class);
	
	private static final String SUCCESS="SUCCESS";
	private static final String FAILURE="FAILURE";
	
	
	@Autowired
	ErrorMessage errorMessage;
	@Autowired
	UcNotifyService ucNotifyService;
	@Autowired
	PaymentOrderService paymentOrderService;
	
	public static final class Request{
		
		String ver;
		String sign;
		Data data;
		public String getVer() {
			return ver;
		}
		public void setVer(String ver) {
			this.ver = ver;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		
		public Data getData() {
			return data;
		}
		public void setData(Data data) {
			this.data = data;
		}
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		public boolean validMd5(String appKey){
			return data.validMd5(sign, appKey);
		}
		
	}
	
	public static final class Data{
		String tradeId;
		String tradeTime;
		String orderId;
		String gameId;
		String amount;
		String payType;
		String attachInfo;
		String orderStatus;
		String failedDesc;
		public String getTradeId() {
			return tradeId;
		}
		public void setTradeId(String tradeId) {
			this.tradeId = tradeId;
		}
		public String getTradeTime() {
			return tradeTime;
		}
		public void setTradeTime(String tradeTime) {
			this.tradeTime = tradeTime;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getGameId() {
			return gameId;
		}
		public void setGameId(String gameId) {
			this.gameId = gameId;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getPayType() {
			return payType;
		}
		public void setPayType(String payType) {
			this.payType = payType;
		}
		public String getAttachInfo() {
			return attachInfo;
		}
		public void setAttachInfo(String attachInfo) {
			this.attachInfo = attachInfo;
		}
		public String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
		public String getFailedDesc() {
			return failedDesc;
		}
		public void setFailedDesc(String failedDesc) {
			this.failedDesc = failedDesc;
		}
		public Data() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		public boolean validMd5(String md5,String appKey){
			boolean ret=true;
			Map<String,String> parameters=GameUtil.getMap();
			parameters.put("tradeId", tradeId);
			parameters.put("tradeTime", tradeTime);
			parameters.put("orderId", orderId);
			parameters.put("gameId", gameId);
			parameters.put("amount", amount);
			parameters.put("payType", payType);
			parameters.put("attachInfo", attachInfo);
			parameters.put("orderStatus", orderStatus);
			parameters.put("failedDesc", failedDesc);
			
			String[]  sortedParameter={
					"amount",
					"attachInfo",
					"failedDesc",
					"gameId",
					"orderId",
					"orderStatus",
					"payType",
					"tradeId",
					"tradeTime"
					
			};
			StringBuffer buffer=new StringBuffer();
			for(int i=0;i<sortedParameter.length;i++){
				String p=sortedParameter[i];
				String v=parameters.get(p);
				logger.info("p="+p+",v="+v);
				if((p.equals("orderId")||p.equals("attachInfo"))&&v.equals("")){
					continue;
				}
				buffer.append(p).append("=").append(v);
			}
			
			buffer.append(appKey);
			String newMd5=Md5Encrypt.md5(buffer.toString());
			
			ret=newMd5.toLowerCase().equals(md5);
			
			return ret;
		}
		
	}
	
	@RequestMapping(value="/notify", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> api(@RequestBody String json) {
		
		
		String result=FAILURE;
		try {
			logger.info("UcConstroller.notify:"+json);
			result = SUCCESS;
			String appKey=errorMessage.getValue(ErrorMessage.UC_APP_SECRET, 0);
			Request request=U.parse(json, Request.class);
			boolean validSign=request.validMd5(appKey);
			logger.info("UcConstroller.validSign:"+validSign);
			
			if(validSign){
					// do some logical.
					boolean ret=false;
					Long id=ucNotifyService.save(request.data.tradeId,request.data. tradeTime, request.data.orderId, request.data.gameId, request.data.amount, request.data.payType, request.data.attachInfo, request.data.orderStatus, request.data.failedDesc, request.sign);
					String localOrderId=request.data.orderId;
					String status="S".equalsIgnoreCase(request.data.orderStatus)?"200":"";
					paymentOrderService.updateOrderState(id, localOrderId, status, UcConstroller.class.getSimpleName());
					
					
					
				
			}
			else{
				result=FAILURE;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");
		
	
		
		return new ResponseEntity<String>(result, headers,
				HttpStatus.OK);

	}
	
	
}
