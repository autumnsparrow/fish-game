/**
 * 
 */
package com.sky.mobile.protocol.service;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.domain.FlymeNotify;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.persistence.FlymeNotifyMapper;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.util.HttpUtil;
import com.sky.mobile.protocol.util.U;
import com.sky.mobile.sign.flyme.util.SignUtil;

/**
 * 
 * 
 * 
 * @author sparrow
 *
 */
@Service
public class FlymeService {
	
	private static final Log logger=LogFactory.getLog(FlymeService.class);
	
	@Autowired
	ErrorMessage errorMessage;
	
	
	@Autowired
	FlymeNotifyMapper flymeNotifyMapper;
	
	@Autowired
	PaymentOrderService paymentOrderService;
	
	public static class ValidSessionResponse{
		int code;
		String message;
		String value;
		String redirect;
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
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
	
	
	/**
	 * 
	 * should add the protocol in the Remote Service.
	 * valid the sesion .
	 * @param uid
	 * @param sessionId
	 * @return true: session valid, false : session invalid.
	 * @throws LocalServiceException
	 */
	public boolean validSession(Long uid,String sessionId) throws LocalServiceException{
		boolean ret=false;
		Map<String,String> params=GameUtil.getMap();
		params.put("app_id",errorMessage.flymeKeys.getAppId() );
		params.put("uid", String.valueOf(uid));
		params.put("session_id", sessionId);
		params.put("ts", String.valueOf(System.currentTimeMillis()) );
		
		String sign = SignUtil.getSignCode(params, errorMessage.flymeKeys.getAppSecret());
		
		params.put("sign_type", "md5");
		params.put("sign", sign);
		
		try {
			String content=HttpUtil.httpPost(errorMessage.flymeKeys.getCheckSessionUrl(),params);
			ValidSessionResponse response=GameContextGlobals.getJsonConvertor().convert(content, ValidSessionResponse.class);
			
			if(response.code==200){
				ret=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LocalServiceException(-1, "check  session: uid="+uid+",session_id="+sessionId);
		}
		
		return ret;
	}
	

	
	/**
	 * trade_status:
	 * 1:待支付(订单已创建) 2:支付中 3:已支付 .4:取消订单 5:未知异常取消订单
	 * 
	 * 
	 * @param notify
	 */
	public void flymeNotify(FlymeNotify notify){

		try {
			// save into the database
			int affectedRows=	flymeNotifyMapper.insertSelective(notify);
			Long id=notify.getId();
			// TODO:2. update the payment order status (in database)
			String localOrderId=notify.getLocalOrderId();
			String status="3".equals(notify.getTradeStatus())?"200":"";
			paymentOrderService.updateOrderState(id, localOrderId, status, FlymeService.class.getSimpleName());
			
			
			//TODO:3. update the payment remote 
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public static void main(String args[]){
//		
//		String url="https://api.game.meizu.com/game/security/checksession";
//		String appId="2884873";
//		String appSecret="5ECvSYTEG91d4hnRcrU3lwg0BM2ooStj";
//		// app_id=2884873&session_id=eyJ2IjoyLCJ1IjoiMTE0MTc4NTg1IiwidCI6MTQ1Mjk0NDMzMzMzOSwicyI6Im5zIiwiciI6IlBTUlM1NjRZSzRWN2E4ZnpKbThrIiwiYSI6Ijk3MTlDN0E1MEVGNTNGQThEOUFDMUE3RDNDQzMwMjYzIn0&ts=1452944337534&uid=114178585:7e94cec3c0ae43e2a3076f630fc46569
//		String sessionId="eyJ2IjoyLCJ1IjoiMTE0MTc4NTg1IiwidCI6MTQ1Mjk0NDMzMzMzOSwicyI6Im5zIiwiciI6IlBTUlM1NjRZSzRWN2E4ZnpKbThrIiwiYSI6Ijk3MTlDN0E1MEVGNTNGQThEOUFDMUE3RDNDQzMwMjYzIn0";
//		
//		Map<String,String> params=GameUtil.getMap();
//		params.put("app_id",appId);
//		params.put("uid", String.valueOf(114178585L));
//		params.put("session_id", sessionId);
//		params.put("ts", String.valueOf(System.currentTimeMillis()) );
//		
//		String sign = SignUtil.getSignCode(params, appSecret);
//		
//		params.put("sign_type", "md5");
//		params.put("sign", sign);
//		
//		String content=HttpUtil.httpPost(url,params);
//		
//		System.out.println(content);
//		
//	}
	
	

}
