/**
 * 
 */
package com.sky.mobile.inteface;

import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.mobile.inteface.HuaweiController.HuaweiResponse;
import com.sky.mobile.protocol.domain.HuaweiNotify;
import com.sky.mobile.protocol.domain.JinliNotify;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.persistence.JinliNotifyMapper;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.sign.huawei.util.HuaweiRSA;
import com.sky.mobile.sign.jinli.JinliRSASignature;

/**
 * @author sparrow
 *
 */

@RequestMapping("/jinli")
@Controller
public class JinliController {
	
	private static final Log logger=LogFactory.getLog(JinliController.class);
	@Autowired
	ErrorMessage errorMessage;
	
	@Autowired
	JinliNotifyMapper jinliNotifyMapper;
	
	@Autowired
	PaymentOrderService paymentOrderService;
	
	/**
	 * 
	 * @author sparrow
	 *
	 */
	public static class JinliPaymentResponse{
		String api_key;
		String close_time;
		String create_time;
		String deal_price;
		String out_order_no;
		String pay_channel;
		String submit_time;
		String user_id;
		String sign;
		public String getApi_key() {
			return api_key;
		}
		public void setApi_key(String api_key) {
			this.api_key = api_key;
		}
		public String getClose_time() {
			return close_time;
		}
		public void setClose_time(String close_time) {
			this.close_time = close_time;
		}
		public String getCreate_time() {
			return create_time;
		}
		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}
		public String getDeal_price() {
			return deal_price;
		}
		public void setDeal_price(String deal_price) {
			this.deal_price = deal_price;
		}
		public String getOut_order_no() {
			return out_order_no;
		}
		public void setOut_order_no(String out_order_no) {
			this.out_order_no = out_order_no;
		}
		public String getPay_channel() {
			return pay_channel;
		}
		public void setPay_channel(String pay_channel) {
			this.pay_channel = pay_channel;
		}
		public String getSubmit_time() {
			return submit_time;
		}
		public void setSubmit_time(String submit_time) {
			this.submit_time = submit_time;
		}
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		
		public void readValud(Map<String,String> params){
			api_key=params.get("api_key");
			close_time=params.get("close_time");
			create_time=params.get("create_time");
			deal_price=params.get("deal_price");
			pay_channel=params.get("pay_channel");
			submit_time=params.get("submit_time");
			user_id=params.get("user_id");
			sign=params.get("sign");
			out_order_no=params.get("out_order_no");
			
			
			
		}
		
		
	}
	
	private boolean valid(HttpServletRequest req) throws LocalServiceException, NumberFormatException, RemoteServiceException{
		
		boolean ret=false;
		Map<String, String> map = new HashMap<String, String>();

		String sign = req.getParameter("sign");

		/*************************************** 组装重排序参数 *********************************************/
		Enumeration<String> attributeNames = req.getParameterNames();
		while (attributeNames.hasMoreElements()) {
			String name = attributeNames.nextElement();
			map.put(name, req.getParameter(name));
		}

		StringBuilder contentBuffer = new StringBuilder();
		Object[] signParamArray = map.keySet().toArray();
		Arrays.sort(signParamArray);
		for (Object key : signParamArray) {
			String value = map.get(key);
			if (!"sign".equals(key) && !"msg".equals(key)) {// sign和msg不参与签名
				contentBuffer.append(key + "=" + value + "&");
			}
		}

		String content = StringUtils.removeEnd(contentBuffer.toString(), "&");
		logger.info(content);
		if (StringUtils.isBlank(sign)) {
			try {
				throw new LocalServiceException(-1, "fail");
			} catch (LocalServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/****************************** 签名验证 *******************************************/
		boolean isValid = false;
		try {
			isValid = JinliRSASignature.doCheck(content, sign, errorMessage.kinliKeys.getAppSecret(), CharEncoding.UTF_8);
		} catch (Exception e) {
			throw new LocalServiceException(-1, "fail");
		}

		if (isValid) {
			// success
			JinliPaymentResponse r=G.o(JinliPaymentResponse.class);
			//map.put("sign", si)
			r.readValud(map);
			r.setSign(sign);
			
			JinliNotify o=G.o(JinliNotify.class);
			o.setApiKey(r.getApi_key());
			o.setCloseTime(r.getClose_time());
			o.setCreateTime(new Date());
			o.setDealPrice(r.getDeal_price());
			o.setJinliCreateTime(r.getCreate_time());
			o.setOutOrderNo(r.getOut_order_no());
			o.setPayChannel(r.getPay_channel());
			o.setSign("");
			o.setSubmitTime(r.getSubmit_time());
			
			jinliNotifyMapper.insertSelective(o);
			
			Long id=o.getId();
			String localOrderId=o.getOutOrderNo();
			String status="200";
			paymentOrderService.updateOrderState(id, localOrderId, status, JinliController.class.getSimpleName());
			
			
			ret=true;
		}
		return ret;
	}
	
	@RequestMapping(value = "/notify")
	@ResponseBody
	public ResponseEntity<String> notify(HttpServletRequest req) {
		
		boolean valid = false;
		String result="fail";
		try {
			if(valid(req)){
				result="success";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		// return json

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");

		
		return new ResponseEntity<String>(result, headers, HttpStatus.OK);
	}

}
