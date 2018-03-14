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

import com.ipay.notify.NotifyPayResultCallback;
import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.mobile.protocol.domain.LenovoGetOrder;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.domain.VivoGetOrder;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.service.LenovoService;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.protocol.service.VivoService;
import com.sky.mobile.protocol.service.WdjService;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.util.U;
import com.sky.mobile.sign.wdj.WandouRsa;

/**
 * @author sparrow
 *
 */

@RequestMapping("/lenovo")
@Controller
public class LenovoController {
	
	private static final Log logger=LogFactory.getLog(LenovoController.class);
	@Autowired
	ErrorMessage errorMessage;
	
	
	@Autowired
	PaymentOrderService paymentOrderService;
	
	
	@Autowired
	LenovoService lenovoService;
	
	@RequestMapping(value="/notify", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> notify(
			
			@RequestBody String payload
			
			) {
		logger.info("content:"+payload);
		String result="FAILURE";
		NotifyPayResultCallback callback=null;
		try {
			callback=new NotifyPayResultCallback(payload);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// if the result id valid.
		if(callback.getTransType()==0){
			try {
				Long id=Long.valueOf(0);
				LenovoGetOrder getOrder=lenovoService.findOrderByOrderId(callback.getTransId());
				if(getOrder==null){
					throw new LocalServiceException(-1, "can't find LenovoGetOrder by order id:"+callback.getTransId());
				}
				id=getOrder.getId();
				String localOrderId=callback.getCpOrderId();
				String status=callback.getResult()==0?"200":"";
				
				paymentOrderService.updateOrderState(id, localOrderId, status, VivoService.class.getSimpleName());
				 result="SUCCESS";
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LocalServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			}

	
			
		}
		
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");
		
	
		
		return new ResponseEntity<String>(result, headers,
				HttpStatus.OK);
		
	}

}
