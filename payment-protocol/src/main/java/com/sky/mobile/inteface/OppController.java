/**
 * 
 */
package com.sky.mobile.inteface;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.service.OppNotifyService;
import com.sky.mobile.protocol.service.PaymentOrderService;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.util.U;

/**
 * @author sparrow
 *
 */

@RequestMapping("/opp")
@Controller
public class OppController {
	private static final Log logger=LogFactory.getLog(OppController.class);
	
	private static final String RESULT_STR = "result=%s&resultMsg=%s";
	
	private static final String CALLBACK_OK = "OK";
	private static final String CALLBACK_FAIL = "FAIL";
	
	@Autowired
	ErrorMessage errorMessage;
	@Autowired
	OppNotifyService oppNotifyService;
	@Autowired
	PaymentOrderService paymentOrderService;
	
	
	
		
	@RequestMapping(value="/notify", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> notify(
			HttpServletRequest req){
		String retMsg=String.format(RESULT_STR, CALLBACK_FAIL, "failed");
		try {
			req.setCharacterEncoding("UTF-8");
			NotifyRequestEntity e = new NotifyRequestEntity();
			e.setNotifyId(req.getParameter("notifyId"));
			e.setPartnerOrder(req.getParameter("partnerOrder"));
			e.setProductName(req.getParameter("productName"));
			e.setProductDesc(req.getParameter("productDesc"));
			e.setPrice(Integer.parseInt(req.getParameter("price")));
			e.setCount(Integer.parseInt(req.getParameter("count")));
			e.setAttach(req.getParameter("attach"));
			e.setSign(req.getParameter("sign"));
			String baseString = getBaseString(e);
			logger.info("OppController.notify:"+baseString+" raw:"+e.toString());
			boolean check = false;
			try{
				String publicKey=errorMessage.getValue(ErrorMessage.OP_APP_SECRET, 0);
				check = doCheck(baseString, e.getSign(), publicKey);
				logger.info("OppController.validSign:"+check);
				if(check){
					
					// do some logical.
					boolean ret=false;
					Long id=oppNotifyService.save(e.notifyId, e.partnerOrder, e.productName, e.productDesc, e.price, e.count, e.attach, e.sign);
					String localOrderId=e.partnerOrder;
					String status="200";
					paymentOrderService.updateOrderState(id, localOrderId, status, OppController.class.getSimpleName());
					
					
				
				}
			}catch(Exception ex){
//				logger.error("验签失败baseString=" + baseString + ", sing=" + e.getSign(), ex);
				logger.error(ex.getMessage());
			}
			String result = CALLBACK_OK;
			String resultMsg = "回调成功";
			if(!check){
				result = CALLBACK_FAIL;
				resultMsg = "验签失败";
			}
			
			
			
			retMsg=String.format(RESULT_STR, result, URLEncoder.encode(resultMsg, "UTF-8"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");
		
		return new ResponseEntity<String>(retMsg, headers,
				HttpStatus.OK);
	
	}
	
	
	
	private String getBaseString(NotifyRequestEntity ne) {
		StringBuilder sb = new StringBuilder();
		sb.append("notifyId=").append(ne.getNotifyId());
		sb.append("&partnerOrder=").append(ne.getPartnerOrder());
		sb.append("&productName=").append(ne.getProductName());
		sb.append("&productDesc=").append(ne.getProductDesc());
		sb.append("&price=").append(ne.getPrice());
		sb.append("&count=").append(ne.getCount());
		sb.append("&attach=").append(ne.getAttach());
		return sb.toString();
	}
	
	public boolean doCheck(String content, String sign, String publicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] encodedKey = Base64.decodeBase64(publicKey);
		PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

		signature.initVerify(pubKey);
		signature.update(content.getBytes("UTF-8"));
		boolean bverify = signature.verify(Base64.decodeBase64(sign));
		return bverify;
	}
	
	public  static class NotifyRequestEntity {
		 String notifyId;
		 String partnerOrder;
		 String productName;
		 String productDesc;
		 int price;
		 int count;
		 String attach;
		 String sign;
		
		public String getNotifyId() {
			return notifyId == null ? "" : notifyId;
		}
		public void setNotifyId(String notifyId) {
			this.notifyId = notifyId;
		}
		public String getPartnerOrder() {
			return partnerOrder == null ? "" : partnerOrder;
		}
		public void setPartnerOrder(String partnerOrder) {
			this.partnerOrder = partnerOrder;
		}
		public String getProductName() {
			return productName == null ? "" : productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getProductDesc() {
			return productDesc == null ? "" : productDesc;
		}
		public void setProductDesc(String productDesc) {
			this.productDesc = productDesc;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public String getAttach() {
			return attach == null ? "" : attach;
		}
		public void setAttach(String attach) {
			this.attach = attach;
		}
		public String getSign() {
			return sign == null ? "" : sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		@Override
		public String toString() {
			return "NotifyRequestEntity [notifyId=" + notifyId
					+ ", partnerOrder=" + partnerOrder + ", productName="
					+ productName + ", productDesc=" + productDesc + ", price="
					+ price + ", count=" + count + ", attach=" + attach
					+ ", sign=" + sign + "]";
		}
		
		
	}
	
	
	
}


