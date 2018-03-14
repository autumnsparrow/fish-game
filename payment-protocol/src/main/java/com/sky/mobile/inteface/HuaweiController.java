/**
 * 
 */
package com.sky.mobile.inteface;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
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

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.mobile.protocol.domain.HuaweiNotify;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.service.HuaweiService;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.util.U;
import com.sky.mobile.sign.huawei.util.HuaweiRSA;

/**
 * @author sparrow
 *
 */

@RequestMapping("/huawei")
@Controller
public class HuaweiController {

	private static final Log logger = LogFactory.getLog(HuaweiController.class);
	@Autowired
	ErrorMessage errorMessage;

	@Autowired
	PaymentOrderService paymentOrderService;
	
	@Autowired
	HuaweiService huaweiService;

	public static class HuaweiResponse {

		String result;

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

	}


	@RequestMapping(value = "/notify")
	@ResponseBody
	public ResponseEntity<String> notify(@RequestBody String payload) {
		HuaweiResponse resp = new HuaweiResponse();
		resp.setResult("0");
		logger.info(payload);
		boolean valid = false;

		Map<String, Object> map = null;
		map = getValue(payload);
		try {

			if (null != map) {
				// return;'

				String sign = (String) map.get("sign");
				 String content = HuaweiRSA.getSignData(map);
				String localSign= HuaweiRSA.sign(content, errorMessage.huaweiKeys.getPayRsaPrivate());
				 logger.info("\r\n"+content+"\r\n"+localSign+"\r\n"+sign);
				// 验签成功
				if (HuaweiRSA.rsaDoCheck(map, sign,
						errorMessage.huaweiKeys.getPayRsaPublic())) {
					resp.setResult("0");
					valid = true;
				} else { // 验签失败
					resp.setResult("1");
				}
				if (valid) {
					logger.info("sign valided!");
					HuaweiNotify notify=huaweiService.getNotiy(map);
					
					Long id = huaweiService.save(notify);//kupaiService.save(notify);
					String localOrderId = notify.getRequestId();
					String status="0".equals(notify.getMsg())?"200":"";
					
					paymentOrderService.updateOrderState(id, localOrderId, status, HuaweiController.class.getSimpleName());
					
				}
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

		// return json

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8");

		String json = GameContextGlobals.getJsonConvertor().format(resp);
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}

	/**
	 * @param request
	 * @return 本接口Content-Type是：application/x-www-form-urlencoded，对所有参数，会自动进行编码，
	 *         接收端收到消息也会自动根据Content-Type进行解码。 同时，接口中参数在发送端并没有进行单独的URLEncode
	 *         (sign和extReserved
	 *         、sysReserved参数除外)，所以，在接收端根据Content-Type解码后，即为原始的参数信息。
	 *         但是HttpServletRequest的getParameter
	 *         ()方法会对指定参数执行隐含的URLDecoder.decode(),所以，相应参数中如果包含比如"%"，就会发生错误。
	 *         因此，我们建议通过如下方法获取原始参数信息。
	 * 
	 *         注：使用如下方法必须在原始ServletRequest未被处理的情况下进行，否则无法获取到信息。比如，在Struts情况，
	 *         由于struts层已经对参数进行若干处理，
	 *         http中InputStream中其实已经没有信息，因此，本方法不适用。要获取原始信息，
	 *         必须在原始的，未经处理的ServletRequest中进行。
	 */
	public Map<String, Object> getValue(String str) {

//		String line = null;
//		StringBuffer sb = new StringBuffer();
//		try {
//			request.setCharacterEncoding("UTF-8");
//
//			InputStream stream = request.getInputStream();
//			InputStreamReader isr = new InputStreamReader(stream);
//			BufferedReader br = new BufferedReader(isr);
//			while ((line = br.readLine()) != null) {
//				sb.append(line).append("\r\n");
//			}
//			System.out.println("The original data is : " + sb.toString());
//			br.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//
	//	String str = sb.toString();
	//	logger.info(str);
		Map<String, Object> valueMap = new HashMap<String, Object>();
		if (null == str || "".equals(str)) {
			return valueMap;
		}

		String[] valueKey = str.split("&");
		for (String temp : valueKey) {
			String[] single = temp.split("=");
			
				
				try {
					valueMap.put(single[0],  URLDecoder.decode(single[1],"utf-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}

		// 接口中，如下参数sign和extReserved、sysReserved是URLEncode的，所以需要decode，其他参数直接是原始信息发送，不需要decode
//		try {
//			String sign = (String) valueMap.get("sign");
//			String extReserved = (String) valueMap.get("extReserved");
//			String sysReserved = (String) valueMap.get("sysReserved");
//			String productName=(String)valueMap.get("productName");
//			if (null != sign) {
//				sign = URLDecoder.decode(sign, "utf-8");
//				valueMap.put("sign", sign);
//			}
//			if (null != productName) {
//				productName = URLDecoder.decode(productName, "utf-8");
//				valueMap.put("productName", productName);
//			}
//			if (null != extReserved) {
//				extReserved = URLDecoder.decode(extReserved, "utf-8");
//				valueMap.put("extReserved", extReserved);
//			}
//
//			if (null != sysReserved) {
//				sysReserved = URLDecoder.decode(sysReserved, "utf-8");
//				valueMap.put("sysReserved", sysReserved);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return valueMap;

	}

}
