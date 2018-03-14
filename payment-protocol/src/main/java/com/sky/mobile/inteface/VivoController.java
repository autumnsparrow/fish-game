/**
 * 
 */
package com.sky.mobile.inteface;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.sky.mobile.protocol.service.VivoService;
import com.sky.mobile.protocol.service.VivoService.VivoPaymentResponse;
import com.sky.mobile.protocol.util.U;

/**
 * 
 * 
 * 
 * @author sparrow
 *
 */
@RequestMapping("/vivo")
@Controller
public class VivoController {
	private static final Log logger=LogFactory.getLog(VivoController.class);
	
	@Autowired
	VivoService vivoService;
	
	// respCode=0000&signMethod=MD5&vivoOrder=145310876641729140599&storeOrder=10037201619180501250000000000&orderAmount=0.01&channelFee=0.00&respMsg=%E4%BA%A4%E6%98%93%E5%AE%8C%E6%88%90&storeId=20140702025804544919&channel=1001&signature=8b5251d3e63cd9d372f13fa92249e681


	/**
	 * 
	 * receive the Vivo Server Request.
	 * 
	 * 
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> notify(@RequestBody String payload) {

		logger.info(payload);
		
		try {
			//Map<String,String> parameters=U.getParameters(payload);
			VivoPaymentResponse r=new VivoPaymentResponse();
			PayloadPostData postData=new PayloadPostData(payload);
			r.parse(postData);
			//r.setChannel(U.s2I(parameters, "channel"));
			
			
			vivoService.vivoPaymentNotify(r);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");

		return new ResponseEntity<String>("ok", headers, HttpStatus.OK);

	}

}
