package com.sky.mobile.inteface;

/**
 * 
 * 
 * 
 */

import java.io.UnsupportedEncodingException;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.DES;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.sky.game.context.util.RSAUtils;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.persistence.AnZhiNotifyMapper;
import com.sky.mobile.protocol.service.AnZhiNotifyService;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.service.UcNotifyService;
import com.sky.mobile.protocol.util.Md5Encrypt;
import com.sky.mobile.protocol.util.U;
@RequestMapping("/anzhi")
@Controller
public class AnZhiConstroller {
	
	
	
	
public AnZhiConstroller() {
		super();
		// TODO Auto-generated constructor stub
		RSAUtils.load();
	}

private static final Log logger=LogFactory.getLog(AnZhiConstroller.class);
	
	private static final String SUCCESS="SUCCESS";
	private static final String FAILURE="FAILURE";
	
	
	@Autowired
	ErrorMessage errorMessage;
	@Autowired
	AnZhiNotifyService service;
	@Autowired
	PaymentOrderService paymentOrderService;
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> login(
			@RequestParam("msg") String msg,
			@RequestParam("ext") String ext,
			@RequestParam("action") String action
			){
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");
		
	
		String result="success";
		return new ResponseEntity<String>(result, headers,
				HttpStatus.OK);
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> logout(
			@RequestParam("msg") String msg,
			@RequestParam("action") String action
			){
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");
		
	
		String result="success";
		return new ResponseEntity<String>(result, headers,
				HttpStatus.OK);
	}
	
	
	public static final class Request{
		//“uid”:”xxx’, "orderId":””, "orderAmount":””, "orderTime":””, 
		//"orderAccount":””, "code":””, "msg":””, 
		//"payAmount":””, "cpInfo":””, "notifyTime":””, "memo":”” "redBagMoney":””
		String uid;
		String orderId;
		int orderAmount;// fen
		String orderTime;
		String orderAccount;
		int code;
		int payAmount;//fen
		String cpInfo;
		String msg;
		String notifyTime;
		String memo;
		int redBagMoney;
		
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
		public int getOrderAmount() {
			return orderAmount;
		}
		public void setOrderAmount(int orderAmount) {
			this.orderAmount = orderAmount;
		}
		public String getOrderTime() {
			return orderTime;
		}
		public void setOrderTime(String orderTime) {
			this.orderTime = orderTime;
		}
		public String getOrderAccount() {
			return orderAccount;
		}
		public void setOrderAccount(String orderAccount) {
			this.orderAccount = orderAccount;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public int getPayAmount() {
			return payAmount;
		}
		public void setPayAmount(int payAmount) {
			this.payAmount = payAmount;
		}
		public String getCpInfo() {
			return cpInfo;
		}
		public void setCpInfo(String cpInfo) {
			this.cpInfo = cpInfo;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
		public String getNotifyTime() {
			return notifyTime;
		}
		public void setNotifyTime(String notifyTime) {
			this.notifyTime = notifyTime;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
		public int getRedBagMoney() {
			return redBagMoney;
		}
		public void setRedBagMoney(int redBagMoney) {
			this.redBagMoney = redBagMoney;
		}
		
		
	}
	
	
	
	@RequestMapping(value="/pay", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> pay(
			@RequestParam("data") String data) {
	
		
		String result=FAILURE;
		try {
			logger.info("AnzhiConstroller.notify:"+data);
			result = SUCCESS;
			String appKey=errorMessage.getValue(ErrorMessage.ANZHI_APP_SECRET, 0);
			String json=DES.decrypt(data, appKey);//Des3Util.decrypt(data, appKey);
			Request r=U.parse(json, Request.class);
			
			if(r!=null){
					// do some logical.
					//boolean ret=false;
					long timestamp=r.getNotifyTime()!=null?Long.parseLong(r.getNotifyTime()):System.currentTimeMillis();
					
					
					Long id= service.save(r.cpInfo,
							r.memo, r.msg, new Date(timestamp),
							r.orderAccount,r. orderId,
							CronUtil.getDateFromString(r.orderTime),
							r. redBagMoney, r.code, r.uid);////ucNotifyService.save(request.data.tradeId,request.data. tradeTime, request.data.orderId, request.data.gameId, request.data.amount, request.data.payType, request.data.attachInfo, request.data.orderStatus, request.data.failedDesc, request.sign);
					
					
					String localOrderId=r.cpInfo;
					String status=r.code==1?"200":"";
					
					paymentOrderService.updateOrderState(id, localOrderId, status, AnZhiConstroller.class.getSimpleName());
					
					
				
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
	
	public static void main(String args[]){
		String data="BcwTUiUqeliAM0UDpMFSXC6qbzimeIWT1fambhqD+xB5AQ+NexYOJLvWeumlzcB6/68AMCIybVXN6Ce/OSYjzQsCVer5lzD7ft70aVgJl5M4zEdEZTS/6cA/NzXh/q7qELyU/omJ2kz+Y3rym/Dh6cuoSz15JFTMPn/7RSaMqc1u63xkJ7Zd8orpRfldriE6ttyU7D54uPyA8NzKBb3rOii7eWfH9g2m2PWTM+KBtQ5miUoGRo8fqw==";
		
		String appKey="5cfP0DU4QH342kRAlq52zOHR";
		//String appKey=errorMessage.getValue(ErrorMessage.UC_APP_SECRET, 0);
		String json;
		try {
			json = DES.decrypt(data, appKey);
			Request r=U.parse(json, Request.class);
			
			System.out.println(r);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
}
