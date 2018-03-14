/**
 * 
 */
package com.sky.mobile.inteface;

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

import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.sky.mobile.inteface.PayloadPostData.IPostDataParser;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.service.BaiduService;
import com.sky.mobile.protocol.service.impl.PaymentService;
import com.sky.mobile.protocol.util.Md5Encrypt;


/**
 * @author sparrow
 *
 */

@RequestMapping("/baidu")
@Controller
public class BaiduController {
	
	private static final Log logger=LogFactory.getLog(BaiduController.class);
	@Autowired
	BaiduService baiduService;
	@Autowired
	ErrorMessage errorMessage;
	
	public static class BaiduPaymentResponse implements IPostDataParser{
		String appid;
		String orderid;
		String amount;
		String unit;
		String jfd;
		String status;
		String paychannel;
		String phone;
		String channel;
		String from;
		String sign;
		String extchannel;
		String cpdefinepart;
		
		
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getJfd() {
			return jfd;
		}
		public void setJfd(String jfd) {
			this.jfd = jfd;
		}
		
		
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getPaychannel() {
			return paychannel;
		}
		public void setPaychannel(String paychannel) {
			this.paychannel = paychannel;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getChannel() {
			return channel;
		}
		public void setChannel(String channel) {
			this.channel = channel;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		
		public String getAppid() {
			return appid;
		}
		public void setAppid(String appid) {
			this.appid = appid;
		}
		public String getOrderid() {
			return orderid;
		}
		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}
		public String getExtchannel() {
			return extchannel;
		}
		public void setExtchannel(String extchannel) {
			this.extchannel = extchannel;
		}
		
		
		
		public String getCpdefinepart() {
			return cpdefinepart;
		}
		public void setCpdefinepart(String cpdefinepart) {
			this.cpdefinepart = cpdefinepart;
		}
		/**
		 * 
		 * String appId;
		String orderId;
		String amount;
		String unit;
		String jfd;
		String status;
		String payname;
		String Phone;
		String channel;
		String from;
		String sign;
		String extChannel;
		String cpdeinfepart;
		MD5值(对appid,ordered,amount,unit,status,paychannel,appsecret拼接字符串取MD5值)
		 * @param appSecret
		 * @return
		 */
		public boolean valid(String appSecret){
			String raw=String.format("%s%s%s%s%s%s%s",
					appid,orderid,amount,unit,status,paychannel,appSecret);
			
			String md5=Md5Encrypt.md5(raw);
			logger.info("raw:"+raw+",local.md5:"+md5+",server.md5:"+sign);
			return md5.equalsIgnoreCase(sign);
			
			
			
		}
		
		@Override
		public BaiduPaymentResponse parse(PayloadPostData data) {
			// TODO Auto-generated method stub
			 appid=data.s("appid");
			 orderid=data.s("orderid");
			 amount=data.s("amount");
			 unit=data.s("unit");
			 jfd=data.s("jfd");
			 status=data.s("status");
			 paychannel=data.s("paychannel");
			 phone=data.s("phone");
			 channel=data.s("channel");
			 from=data.s("from");
			 sign=data.s("sign");
			 extchannel=data.s("extchannel");
			 cpdefinepart=data.s("cpdefinepart");
			
			return this;
		}
		
	}
	
	
	@Autowired
	PaymentService paymentService;
	@RequestMapping(value = "/notify")
	@ResponseBody
	public ResponseEntity<String> notify(BaiduPaymentResponse resp) {

		try {
			//String orderId=paymentService.getSequence(Integer.valueOf(10002));
			//logger.info(orderId);
			//logger.info(payload);
			//BaiduPaymentResponse resp=G.o(BaiduPaymentResponse.class);
			//	PayloadPostData payloadData=G.o(PayloadPostData.class);
			//resp.parse(payloadData);
			boolean valid=resp.valid(errorMessage.baiduKeys.getAppSecret());
			if(valid){
				baiduService.notify(resp);
			}
			
			//
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");

		return new ResponseEntity<String>("ok", headers, HttpStatus.OK);

	}


}
