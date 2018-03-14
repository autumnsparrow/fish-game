/**
 * 
 */
package com.sky.mobile.inteface;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sky.mobile.protocol.service.impl.PaymentService;



/**
 * 
 * 
 * 

 * @author sparrow
 *
 */

@RequestMapping("/payment/statics")
@Controller
public class PaymentStaticsController {

	private static final Log logger=LogFactory.getLog(PaymentStaticsController.class);
	
	@Autowired
	PaymentService paymentService;
	@RequestMapping(value="/config", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> notify(
			@RequestParam("category") int category,
			@RequestParam("succeed_min") int succeedMin,
			@RequestParam("failure_max") int failureMax,
			@RequestParam("timeout") int timeout,
			@RequestParam("password") String passwd,
			
			
			
			
			HttpServletRequest request){
		
		String result="ok";
		
		String queryString=request.getQueryString();
		logger.info("PaymentStaticsController.notify:"+queryString);
		
		
		// get and post.
		
		
		
		try {
			if(null!=passwd||!"".equals(passwd)){
				if(passwd.equals("5tgy7uji9")){
					paymentService.updatePaymentStaticsParam(category, succeedMin, failureMax, timeout);
				}
			}
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");
		
	
		
		return new ResponseEntity<String>(result, headers,
				HttpStatus.OK);
	}
	
	



}


