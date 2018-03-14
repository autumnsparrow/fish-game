package com.sky.mobile.inteface;

import java.io.UnsupportedEncodingException;
import java.util.Map;

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

import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.service.KupaiService;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.util.U;
import com.sky.mobile.sign.kupai.KupaiSign;


@RequestMapping("/kupai")
@Controller
public class KupaiController {
	
	
	private static final Log logger=LogFactory.getLog(KupaiController.class);
	@Autowired
	KupaiService kupaiService;
	
	// 1.create the bean accept the KuPai server request.
	/**
 amount	金额，单位元，String类型
extra	透传参数，String类型
app_key	应用的唯一标识，String类型
goods_key	计费点key，String类型
sn	支付序号，String类型
trade_id	订单号，String类型
uid	用户id，String类型
op	运营商，String类型，（1移动，2联通，3电信，0第三方）
area	区域，String类型，算hash时需要先URLEncoder
ts	时间戳，String类型
ch_key	渠道key，String类型
result	支付结果（200：确认可出账，203：执行计费成功但不确定出账，其他：失败）
hash	参数摘要（secret为upay分配），加密时参数名称按字母升序排列， String类型

	 * 
	 * @author sparrow
	 *
	 */
	public static class KupaiNotify{
		String amount;
		String extra;
		String app_key;
		String goods_key;
		String sn;
		String trade_id;
		String uid;
		String op;
		String area;
		String ts;
		String ch_key;
		String result;
		String hash;
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getExtra() {
			return extra;
		}
		public void setExtra(String extra) {
			this.extra = extra;
		}
		public String getApp_key() {
			return app_key;
		}
		public void setApp_key(String app_key) {
			this.app_key = app_key;
		}
		public String getGoods_key() {
			return goods_key;
		}
		public void setGoods_key(String goods_key) {
			this.goods_key = goods_key;
		}
		public String getSn() {
			return sn;
		}
		public void setSn(String sn) {
			this.sn = sn;
		}
		public String getTrade_id() {
			return trade_id;
		}
		public void setTrade_id(String trade_id) {
			this.trade_id = trade_id;
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getOp() {
			return op;
		}
		public void setOp(String op) {
			this.op = op;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getTs() {
			return ts;
		}
		public void setTs(String ts) {
			this.ts = ts;
		}
		public String getCh_key() {
			return ch_key;
		}
		public void setCh_key(String ch_key) {
			this.ch_key = ch_key;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getHash() {
			return hash;
		}
		public void setHash(String hash) {
			this.hash = hash;
		}
		
		public boolean validSign(String secret){
			boolean ret=false;
			
			Map<String,String> params=GameUtil.getMap();
		
			params.put("amount",amount);
			params.put("extra",extra);
			params.put("app_key",app_key);
			params.put("goods_key",goods_key);
			params.put("sn",sn);
			params.put("trade_id",trade_id);
			params.put("uid",uid);
			params.put("op",op);
			params.put("area",area);
			params.put("ts",ts);
			params.put("ch_key",ch_key);
			params.put("result",result);
			
			
			String md5=null;
			try {
				md5 = KupaiSign.getHash(params, secret);
				ret=md5.equals(this.hash);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return ret;
		}
		
		
	}
	
	//TODO: 2.create the handler method for the http request(get)
	private static final String OK="SUCCESS";
	
	@Autowired
	ErrorMessage errorMessage;
	
	@Autowired
	PaymentOrderService paymentOrderService;
	
	
	
	@RequestMapping(value="/notify", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> notify(KupaiNotify notify){
		
		
		try {
			if(notify.validSign(errorMessage.kupaiKeys.getAppSecret())){
				Long id=kupaiService.save(notify);
				String localOrderId=notify.getExtra();
				String status=notify.getResult();
				paymentOrderService.updateOrderState(id, localOrderId, status, KupaiController.class.getSimpleName());
				
			}
		} catch (NumberFormatException e) {
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

		
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");
		
		return new ResponseEntity<String>(OK, headers,
				HttpStatus.OK);
	}
	
}
