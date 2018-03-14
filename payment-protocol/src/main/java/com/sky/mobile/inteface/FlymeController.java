/**
 * 
 */
package com.sky.mobile.inteface;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.rmi.transport.proxy.HttpReceiveSocket;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.domain.FlymeNotify;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.service.FlymeService;
import com.sky.mobile.sign.flyme.util.SignUtil;

/**
 * @author sparrow
 *
 */
@RequestMapping("/mz")
@Controller
public class FlymeController {
	
	private static final Log logger=LogFactory.getLog(FlymeController.class);
	
	
	@Autowired
	ErrorMessage errorMessage;
	
	
	@Autowired
	FlymeService flymeService;
	
	private String v(Map<String,String> r,String k){
		
		String  v=r.get(k);
	
		return v==null?"":v;
	}
	
	public Long vL(Map<String,String>  r,String k){
		String v=v(r,k);
		Long l=Long.valueOf(0);
		if(!"".equals(v)){
			l=Long.parseLong(v);
		}
		return l;
		
	}
	
	private Integer s2I(String v){
		Integer i=0;
		if(!"".equals(v)){
			i=Integer.parseInt(v);
		}
		return i;
		
	}
	
	private Long s2L(String v){
		Long i=Long.valueOf(0);
		if(!"".equals(v)){
			i=Long.parseLong(v);
		}
		return i;
		
	}
	
	private Date s2D(String v){
		return CronUtil.getDateFromString(v,CronUtil.DF_YYYYMMddHHmmss);
	}
	
	
	/**
	 * 
	 *
	 * 
	 * @param uid
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value="/notify", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> notify(
			@RequestBody String payload,HttpServletRequest req
			
			){
		
		// log the post data.
		logger.info(payload);
		
		//
		//
		/**
		 * 
		 * 
		 * 
		 * 
		 * 
		 * user_info=AAAA&
		 * uid=114178585
		 * &partner_id=5751847&
		 * notify_time=2016-01-17+02%3A33%3A20&
		 * product_id=jewel_30&
		 * buy_amount=1
		 * &sign_type=MD5&
		 * pay_time=1452964955122
		 * &order_id=16011721010368299
		 * &total_price=0.01&
		 * trade_status=3&
		 * product_unit=%E4%B8%AA&
		 * sign=2325f3bdcda8749b680a8d3e83841626&
		 * cp_order_id=10017201622170101110000000007
		 * &pay_type=0&
		 * product_per_price=0.01&
		 * create_time=1452964934611&
		 * app_id=2884873&
		 * notify_id=1452969200981
			*/
		//
		//
		
		
		
		Map<String,String> params=GameUtil.getMap();
		
		try {
			String[] kvs=payload.split("&");
			for(String kv:kvs){
				String[] kkv=kv.split("=");
				if(kkv.length!=2){
					params.put(kkv[0],"");
					continue;
				}
				
				if("".equals(kkv[1])){
					params.put(kkv[0],"");
					continue;
				}
					
				String v=null;
				try {
					v = URLDecoder.decode(kkv[1], "utf-8");
					params.put(kkv[0], v);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
				
				
			}
			
			
			
			
			String userInfo=v(params,"user_info");
			String uid=v(params,"uid");
			String notifyTime=v(params,"notify_time");
			String parternId=v(params,"partner_id");
			String productId=v(params,"prodcut_id");
			String buyAmount=v(params,"buy_amount");
			String payTime=v(params,"pay_time"); // yyyyMMdd
			String orderId=v(params,"order_id");
			String totalPrice=v(params,"total_price");
			String tradeStatus=v(params,"trade_status");
			String productUnit=v(params,"product_unit");
			String cpOrderId=v(params,"cp_order_id");
			String payType=v(params,"pay_type");
			String createTime=v(params,"create_time");
			String appId=v(params,"app_id");
			String notifyId=v(params,"notify_id");
			String productPerPrice=v(params,"product_per_price");
			String signType=v(params,"sign_type");
			String sign=params.get("sign");
			

			
			String localSign=SignUtil.getSignCode(params, errorMessage.flymeKeys.getAppSecret());
			//TODO: valid the sign 
			if(localSign.equals(sign)){
				// 1. save into the database.
				FlymeNotify o=G.o(FlymeNotify.class);
				o.setAppId(s2L(appId));
				o.setBuyAmount(s2I(buyAmount));
				o.setCreateTime(new Date(s2L(createTime)));
				o.setLocalOrderId(cpOrderId);
				o.setNotifyId(notifyId);
				o.setNotifyTime(CronUtil.getDateFromString(notifyTime));
				o.setOrderId(s2L(orderId));
				o.setPartternId(parternId);
				o.setPayTime(new Date(s2L(payTime)));
				o.setProductId(productId);
				o.setProductPerPrice(new BigDecimal(productPerPrice));
				o.setProductUnit((productUnit));
				o.setSign(sign);
				o.setTradeStatus(tradeStatus);
				o.setUid(s2L(uid));
			//	o.setUpdateTime(updateTime);
				o.setUserInfo(userInfo);
				
				// save the object
				
				flymeService.flymeNotify(o);
				
				// TODO:2. update the payment order status (in database)
				
				//TODO:3. update the payment remote 
			}else{
				
				
				logger.info("sign failed:"+localSign+" server:"+sign);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FlymeResponse resp=G.o(FlymeResponse.class);
		resp.setCode("200");
		resp.setMessage("");
		resp.setRedirect("");
		resp.setValue("");
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		String ret=GameContextGlobals.getJsonConvertor().format(resp);
		return new ResponseEntity<String>(ret, headers,
				HttpStatus.OK);
	}
	
	public static class FlymeResponse{
		String code;
		String message;
		String value;
		String redirect;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getRedirect() {
			return redirect;
		}
		public void setRedirect(String redirect) {
			this.redirect = redirect;
		}
		
		
		
	}

}
