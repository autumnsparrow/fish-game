/**
 * 
 */
package com.sky.mobile.inteface;

import java.util.Date;

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

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.protocol.service.WdjService;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.util.U;
import com.sky.mobile.sign.wdj.WandouRsa;

/**
 * @author sparrow
 *
 */

@RequestMapping("/wdj")
@Controller
public class WdjController {
	
	private static final Log logger=LogFactory.getLog(WdjController.class);
	@Autowired
	ErrorMessage errorMessage;
	
	@Autowired
	WdjService wdjService;
	@Autowired
	PaymentOrderService paymentOrderService;
	
	
	public static class WdjRequest{
		String timeStamp;
		String orderId;
		String money;// fen
		String chargeType;//
		String appKeyId;
		String buyerId;
		String out_trade_no;
		String cardNo;
		public String getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(String timeStamp) {
			this.timeStamp = timeStamp;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}
	
		public String getChargeType() {
			return chargeType;
		}
		public void setChargeType(String chargeType) {
			this.chargeType = chargeType;
		}
		public String getAppKeyId() {
			return appKeyId;
		}
		public void setAppKeyId(String appKeyId) {
			this.appKeyId = appKeyId;
		}
		public String getBuyerId() {
			return buyerId;
		}
		public void setBuyerId(String buyerId) {
			this.buyerId = buyerId;
		}
		public String getOut_trade_no() {
			return out_trade_no;
		}
		public void setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
		}
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		
		
		
	}
	
	
	@RequestMapping(value="/notify", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> notify(
			
			@RequestParam("content") String  json,
			@RequestParam("signType") String signType,
			@RequestParam("sign") String  sign
			) {
		logger.info("content:"+json+"sign:"+sign);
		String result="success";
		try {
		boolean valid=WandouRsa.doCheck(json, sign,errorMessage.wdjKeys.getAppSecret());
		
		if(valid){
			//process the reqeust parameters.
			WdjRequest request=GameContextGlobals.getJsonConvertor().convert(json, WdjRequest.class);
			
			// save the parameters in databases.
			Long id=wdjService.save(request);
			
			
			// update the order status by notify data.
			String localOrderId=request.getOut_trade_no();
			String status="200";
			
		
				paymentOrderService.updateOrderState(id, localOrderId, status, WdjController.class.getSimpleName());
			
			
		}
		
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			
			
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");
		
	
		
		return new ResponseEntity<String>(result, headers,
				HttpStatus.OK);
		
	}

}
