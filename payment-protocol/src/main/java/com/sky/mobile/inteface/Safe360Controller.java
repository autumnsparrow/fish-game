/**
 * 
 */
package com.sky.mobile.inteface;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

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

import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.protocol.service.SafeCenterNotifyService;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.util.Md5Encrypt;
import com.sky.mobile.protocol.util.U;

/**
 * 
 * 
 * 

 * @author sparrow
 *
 */

@RequestMapping("/s360")
@Controller
public class Safe360Controller {

	private static final Log logger=LogFactory.getLog(Safe360Controller.class);
	
	@Autowired
	SafeCenterNotifyService safeCenterNotifyService;
	@Autowired
	PaymentOrderService paymentOrderService;
	
	@Autowired
	ErrorMessage errorMessage;
	@RequestMapping(value="/notify", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> notify(
			@RequestParam("app_key") String appKey,
			@RequestParam("product_id") String productId,
			@RequestParam("amount") int amount,
			@RequestParam("app_uid") String appUid,
			
			@RequestParam("user_id") long userId,
			@RequestParam("order_id") long orderId,
			@RequestParam("gateway_flag") String gatewayFlag,
			@RequestParam("sign_type") String signType,
			@RequestParam("app_order_id") String appOrderId,
			@RequestParam("sign_return") String signReturn,
			@RequestParam("sign") String sign,
			
			
			
			HttpServletRequest request){
		
		String result="ok";
		
		String queryString=request.getQueryString();
		logger.info("Safe360Controller.notify:"+queryString);
		
		
		// get and post.
		
		
		
		try {
			Map<String,String> parameters=request.getParameterMap();
			//request.get
			
			boolean validSign=validSign(parameters,request,sign);
			logger.info("Safe360Controller.validSign:"+validSign);
			if(validSign){
				String appExt1="";
				String appExt2="";
				// do some logical.
				boolean ret=false;
				Long id=safeCenterNotifyService.saveSafeCenterNotify(amount, appOrderId, appExt2, appKey, appUid, gatewayFlag, orderId, productId, "", "", signReturn, userId);
				
				String localOrderId=appOrderId;
				String status="success".equalsIgnoreCase(gatewayFlag)?"200":"";
				
				paymentOrderService.updateOrderState(id, localOrderId, status, Safe360Controller.class.getSimpleName());
				
				
			
			}else{
				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");
		
	
		
		return new ResponseEntity<String>(result, headers,
				HttpStatus.OK);
	}
	
	
	
	private boolean validSign(Map<String,String> parameters,HttpServletRequest request,String md5){
		boolean ret=false;
		Set<String> keys=parameters.keySet();
		// remove the sing_return and sign
		keys.remove("sign_return");
		keys.remove("sign");
		
		String[]  keyArray=keys.toArray(new String[]{});
		Arrays.sort(keyArray, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
		
		StringBuffer buffer=new StringBuffer();
		for(int i=0;i<keyArray.length;i++){
			String key=keyArray[i];
			String value=request.getParameter(key);
			buffer.append(value).append("#");
		}
		
		String appSecret=errorMessage.getValue(ErrorMessage.S360_APP_SECRET, 0);
		buffer.append(appSecret);
		
		
		String generateMd5=Md5Encrypt.md5(buffer.toString());
		//String md5=parameters.get("sign");
		
		if(generateMd5!=null&&md5!=null){
			ret=generateMd5.equalsIgnoreCase(md5);
		}
		
		
		return ret;
	}


}


