/**
 * 
 */
package com.sky.mobile.inteface;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
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

import com.qq.open.OpensnsException;
import com.qq.open.SnsSigCheck;
import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.G;
import com.sky.mobile.protocol.domain.HuaweiNotify;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.domain.Qqnotify;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.persistence.QqnotifyMapper;
import com.sky.mobile.protocol.service.HuaweiService;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.util.U;
import com.sky.mobile.sign.huawei.util.HuaweiRSA;

/**
 * @author sparrow
 *
 */

@RequestMapping("/qq")
@Controller
public class QqNotifyController {

	private static final Log logger = LogFactory.getLog(QqNotifyController.class);
	@Autowired
	ErrorMessage errorMessage;

	@Autowired
	PaymentOrderService paymentOrderService;
	
	@Autowired
	QqnotifyMapper qqnotifyMapper;
	
	public static final class QqResponse{
		int ret;
		String msg;
		public int getRet() {
			return ret;
		}
		public void setRet(int ret) {
			this.ret = ret;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
		
	}
	
	


	@RequestMapping(value = "/notify")
	@ResponseBody
	public ResponseEntity<String> notify(HttpServletRequest req) {
		QqResponse resp = new QqResponse();
		resp.setRet(0);
		
		String payload=req.getQueryString();
		logger.info(payload);
		boolean valid = false;

		HashMap<String, String> map = null;
		map = getValue(payload);
		try {

			if (null != map) {
				// return;'

				String sign = (String) map.get("sig");
				
				boolean ret=SnsSigCheck.verifySig("GET", "/payment/qq/notify", map, errorMessage.qqKeys.getAppSecret()+"&", sign);
				
				if(ret){
					resp.setRet(0);
					resp.setMsg("OK");
					valid=true;
				}
				
				if (valid) {
					logger.info("sign valided!");
					
					Qqnotify notify=wrapper(map);
//					HuaweiNotify notify=huaweiService.getNotiy(map);
					int affectedRows=qqnotifyMapper.insertSelective(notify);
					if(affectedRows>0){
					Long id = notify.getId();;//huaweiService.save(notify);//kupaiService.save(notify);
					String localOrderId = notify.getOrderId();
					String status="200";
					
					paymentOrderService.updateOrderState(id, localOrderId, status, QqNotifyController.class.getSimpleName());
					}else{
						resp.setRet(1);
						resp.setMsg("系统繁忙");
					}
				}else{
					resp.setRet(4);
					resp.setMsg("请求参数错误:"+sign);
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
	
	
	
	private Qqnotify wrapper(Map<String,String> p){
		Qqnotify o=G.o(Qqnotify.class);
		
		o.setAppid(p.get("appid"));
		o.setAmt(p.get("amt"));
		o.setBillno(p.get("billno"));
		o.setOpenid(p.get("appid"));
		o.setPayamtCoins(p.get("payamt_coins"));
		o.setProvidetype(p.get("providetype"));
		o.setPubacctPayamtCoins(p.get("pubacct_payamt_coins"));
		o.setToken(p.get("token"));
		o.setTs(p.get("ts"));
		o.setVersion(p.get("version"));
		o.setZoneid(p.get("zoneid"));
		
		//String payItem=p.get("payitem");
		
		String appMeta=p.get("appmeta");
		String[]  items=appMeta.split("\\*");
		if(items.length==3){
			o.setOrderId(items[0]);
		}
		
	
		o.setCreateTime(new Date());
		o.setUpdateTime(new Date());
		
		
		return o;
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
	public HashMap<String, String> getValue(String str) {

//
	//	String str = sb.toString();
	//	logger.info(str);
		HashMap<String, String> valueMap = new HashMap<String, String>();
		if (null == str || "".equals(str)) {
			return valueMap;
		}

		String[] valueKey = str.split("&");
		for (String temp : valueKey) {
			String[] single = temp.split("=");
			
				
				try {
					if(single.length==1){
						valueMap.put(single[0],"");
					}else{
						valueMap.put(single[0],  URLDecoder.decode(single[1],"utf-8"));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}

		
		return valueMap;

	}

}
