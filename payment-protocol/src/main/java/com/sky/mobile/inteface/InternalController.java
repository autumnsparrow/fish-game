/**
 * 
 */
package com.sky.mobile.inteface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.mobile.inteface.KupaiController.KupaiNotify;
import com.sky.mobile.protocol.service.impl.PaymentService;
import com.toyo.remote.service.payment.domain.PaymentOrder;
import com.toyo.remote.service.user.IPaymentNotifyService;

/**
 * @author sparrow
 *
 */
//@RequestMapping("/remote")
//@Controller
public class InternalController {
	
	
	@Autowired
	IPaymentNotifyService paymentNotifyService;
	
	@RequestMapping(value="/notify", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> notify(
			@RequestParam("orderId") String orderId,
			@RequestParam("state") int state,
			@RequestParam("amount") int amount,
			@RequestParam("price") int price,
			@RequestParam("productId") String productId,
			@RequestParam("userId") Long userId
			
			){
		
		
		PaymentOrder po=G.o(PaymentOrder.class);
		po.setOrderId(orderId);
		po.setState(state);
		po.setAmount(amount);
		po.setPrice(price);
		po.setProductId(productId);
		try {
			paymentNotifyService.notifyPaymentResult(userId, po);
		} catch (RemoteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");
		
		return new ResponseEntity<String>("Ok", headers,
				HttpStatus.OK);
	}
	


	

}
