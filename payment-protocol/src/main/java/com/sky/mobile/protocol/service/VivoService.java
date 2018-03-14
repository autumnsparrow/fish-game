/**
 * 
 */
package com.sky.mobile.protocol.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.spring.RemoteServiceException;
import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.sky.mobile.inteface.PayloadPostData;
import com.sky.mobile.inteface.PayloadPostData.IPostDataParser;
import com.sky.mobile.protocol.domain.Order;
import com.sky.mobile.protocol.domain.VivoGetOrder;
import com.sky.mobile.protocol.impl.ErrorMessage;
import com.sky.mobile.protocol.persistence.VivoGetOrderMapper;
import com.sky.mobile.protocol.service.PaymentOrderService.PaymentOrderLifeStates;
import com.sky.mobile.protocol.util.HttpUtil;
import com.sky.mobile.protocol.util.U;
import com.sky.mobile.sign.vivo.VivoSignUtils;
import com.toyo.remote.service.payment.domain.VivoOrder;

/**
 * 
 * Vivi SDK :
 * 
 * 1. get the vivo order and signature for an order.
 * 2. when the vivo notify the result of payment,the server change the order state.
 * 
 * 
 * 
 * 
 * 
 * @author sparrow
 *
 */
@Service
public class VivoService {
	private static final Log logger=LogFactory.getLog(VivoService.class);
	
	@Autowired
	ErrorMessage errorMessage;
	
	@Autowired
	PaymentOrderService paymentOrderService;
	
	
	@Autowired
	VivoGetOrderMapper vivoGetOrderMapper;
	
	/**
	 *  object that the vivo server returns.
	 * 
	 * 
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	public static class VivoOrderResponse{
		// json header
		int respCode;
		String respMsg;
		
		//json content.
		String signMethod;
		String signature;
		String vivoSignature;
		String vivoOrder;
		float orderAmount;
		public int getRespCode() {
			return respCode;
		}
		public void setRespCode(int respCode) {
			this.respCode = respCode;
		}
		public String getRespMsg() {
			return respMsg;
		}
		public void setRespMsg(String respMsg) {
			this.respMsg = respMsg;
		}
		public String getSignMethod() {
			return signMethod;
		}
		public void setSignMethod(String signMethod) {
			this.signMethod = signMethod;
		}
		public String getSignature() {
			return signature;
		}
		public void setSignature(String signature) {
			this.signature = signature;
		}
		public String getVivoSignature() {
			return vivoSignature;
		}
		public void setVivoSignature(String vivoSignature) {
			this.vivoSignature = vivoSignature;
		}
		public String getVivoOrder() {
			return vivoOrder;
		}
		public void setVivoOrder(String vivoOrder) {
			this.vivoOrder = vivoOrder;
		}
		public float getOrderAmount() {
			return orderAmount;
		}
		public void setOrderAmount(float orderAmount) {
			this.orderAmount = orderAmount;
		}
		
		public VivoOrder toVivoOrderObject(){
			VivoOrder o=G.o(VivoOrder.class);
			o.setAmount(this.orderAmount);
			o.setOrder(this.vivoOrder);
			o.setSignature(vivoSignature);
			return o;
		}
		

		public boolean validSignature(String key){
			boolean ret=false;
			
			Map<String,String> params=GameUtil.getMap();
			params.put("vivoSignature",vivoSignature );
			params.put("vivoOrder", vivoOrder);
			params.put("orderAmount",String.format("%.2f", orderAmount));
			params.put("signature", signature);
			params.put("signMethod", signMethod);
			params.put("respCode", String.valueOf(respCode));
			params.put("respMsg", respMsg);
			
			
			
			ret=VivoSignUtils.verifySignature(params,key );
			
			
			return ret;
		
		}
		
	}
	
	/**
	 * 
	 * 
	 * @param channelId
	 * @return
	 */
	public boolean isVivoChannel(int channelId){
		
		return errorMessage.vivo.getChannelId().intValue()==channelId;
	}
	
	/**
	 * get the vivo ordre and signature from the vivo server.
	 * 
	 * @param orderTitle
	 * @param orderDescription
	 * @param amount
	 * @param localOrderId
	 * @return
	 * @throws LocalServiceException
	 */
	public VivoOrder getOrder(String orderTitle,String orderDescription,float amount,String localOrderId) throws LocalServiceException{
		VivoOrder o=null;//
		Map<String,String> params=GameUtil.getMap();
		// fill the common parameters
		params.put("version", "1.0.0");
		
		Date createTime=new Date();
		
		// fill the payment server parameters.
		params.put("storeOrder", localOrderId);
		params.put("storeId", errorMessage.vivo.getCpId());
		params.put("appId", errorMessage.vivo.getAppId());
		params.put("notifyUrl", errorMessage.vivo.getNotifyUrl());
		params.put("orderTime", CronUtil.getFormatedDate(createTime, CronUtil.DF_YYYYMMddHHmmss));
		params.put("orderAmount", String.format("%.2f",amount));
		params.put("orderTitle",orderTitle);
		params.put("orderDesc", orderDescription);
		String signature=VivoSignUtils.getVivoSign(params, errorMessage.vivo.getCpKey());
		params.put("signMethod", "MD5");
		params.put("signature", signature);
		String content=null;
		
		
		try {
			content = HttpUtil.httpPost(errorMessage.vivo.getVivoServerUrl(), params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LocalServiceException(-1, "vivo http invoking failed:"+errorMessage.vivo.getVivoServerUrl());
		}
		
		logger.info(content);
		//  valid the parameter signature.
		try {
			VivoOrderResponse resp=GameContextGlobals.getJsonConvertor().convert(content, VivoOrderResponse.class);
			
			if(resp.validSignature(errorMessage.vivo.getCpKey())){
				
				o=resp.toVivoOrderObject();
				//TODO: insert the request and response in the databases.
				VivoGetOrder getOrder=G.o(VivoGetOrder.class);
				getOrder.setCreateTime(new Date());
				getOrder.setOrderTime( CronUtil.getFormatedDate(createTime));
				getOrder.setOrderAmount(new BigDecimal(amount));
				getOrder.setOrderTitle(orderTitle);
				getOrder.setOrderDescription(orderDescription);
				getOrder.setVivoOrder(resp.getVivoOrder());
				getOrder.setVivoSignature(resp.getSignature());
				getOrder.setLocalOrderId(localOrderId);
				
				int affectedRows=vivoGetOrderMapper.insertSelective(getOrder);
				
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LocalServiceException(-1, "vivo http response parse errored:"+e.getMessage());
		}
		
		
		
		return o;
	}
	
	
	
	public static class VivoPaymentResponse implements IPostDataParser{
		// json header
		//respCode=0000&
		//signMethod=MD5&
		//vivoOrder=145310876641729140599&
		//storeOrder=10037201619180501250000000000&
		//orderAmount=0.01&
		//channelFee=0.00&
		//respMsg=%E4%BA%A4%E6%98%93%E5%AE%8C%E6%88%90&
		//storeId=20140702025804544919&
		//channel=1001&
		//signature=8b5251d3e63cd9d372f13fa92249e681
		

		int respCode;
		String respMsg;
		
		//json content.
		String signMethod;
		String signature;
		String storeId;
		String storeOrder;
		String vivoSignature;
		String vivoOrder;
		float orderAmount;
		
		int channel;
		float channelFee;
		public int getRespCode() {
			return respCode;
		}
		public void setRespCode(int respCode) {
			this.respCode = respCode;
		}
		public String getRespMsg() {
			return respMsg;
		}
		public void setRespMsg(String respMsg) {
			this.respMsg = respMsg;
		}
		public String getSignMethod() {
			return signMethod;
		}
		public void setSignMethod(String signMethod) {
			this.signMethod = signMethod;
		}
		public String getSignature() {
			return signature;
		}
		public void setSignature(String signature) {
			this.signature = signature;
		}
		public String getStoreId() {
			return storeId;
		}
		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}
		public String getStoreOrder() {
			return storeOrder;
		}
		public void setStoreOrder(String storeOrder) {
			this.storeOrder = storeOrder;
		}
		public String getVivoSignature() {
			return vivoSignature;
		}
		public void setVivoSignature(String vivoSignature) {
			this.vivoSignature = vivoSignature;
		}
		public String getVivoOrder() {
			return vivoOrder;
		}
		public void setVivoOrder(String vivoOrder) {
			this.vivoOrder = vivoOrder;
		}
		public float getOrderAmount() {
			return orderAmount;
		}
		public void setOrderAmount(float orderAmount) {
			this.orderAmount = orderAmount;
		}
		public int getChannel() {
			return channel;
		}
		public void setChannel(int channel) {
			this.channel = channel;
		}
		public float getChannelFee() {
			return channelFee;
		}
		public void setChannelFee(float channelFee) {
			this.channelFee = channelFee;
		}
		
		public boolean validSignature(String key){
			boolean ret=false;
			Map<String ,String> params=this.payloadData.getParameters();
			
			ret=VivoSignUtils.verifySignature(params, key);
			return ret;
		}
		@Override
		public String toString() {
			return "VivoPaymentResponse [respCode=" + respCode + ", respMsg="
					+ respMsg + ", signMethod=" + signMethod + ", signature="
					+ signature + ", storeId=" + storeId + ", storeOrder="
					+ storeOrder + ", vivoSignature=" + vivoSignature
					+ ", vivoOrder=" + vivoOrder + ", orderAmount="
					+ orderAmount + ", channel=" + channel + ", channelFee="
					+ channelFee + "]";
		}
		
		PayloadPostData payloadData;
		@SuppressWarnings("unchecked")
		@Override
		public VivoPaymentResponse parse(PayloadPostData data) {
			this.payloadData=data;
			// TODO Auto-generated method stub
			this.channel=data.i("channel");
			this.channelFee=data.f("channelFee");
			this.orderAmount=data.f("orderAmount");
			this.respCode=data.i("respCode");
			this.respMsg=data.s("respMsg");
			this.signature=data.s("signature");
			this.signMethod=data.s("signMethod");
			this.storeId=data.s("storeId");
			this.storeOrder=data.s("storeOrder");
			this.vivoOrder=data.s("vivoOrder");
			this.vivoSignature=data.s("vivoSignature");
		
			return this;
		}
		
		
		
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * @param resp the server notification .
	 * @throws LocalServiceException
	 */
		public void vivoPaymentNotify(VivoPaymentResponse resp) throws LocalServiceException{
			try {
				//VivoPaymentResponse resp=GameContextGlobals.getJsonConvertor().convert(content, VivoPaymentResponse.class);
				if(resp.validSignature(errorMessage.vivo.getCpKey())){
					//1.query the vivo order data id
					Long id=Long.valueOf(0);
					VivoGetOrder getOrder=vivoGetOrderMapper.findByLocalOrderId(resp.getStoreOrder());
					if(getOrder==null){
						throw new LocalServiceException(-1, "can't find VivoGetOrder by localOrderId:"+resp.getStoreOrder());
					}
					id=getOrder.getId();
					String localOrderId=resp.getStoreOrder();
					String status=resp.respCode==0?"200":"";
					
					paymentOrderService.updateOrderState(id, localOrderId, status, VivoService.class.getSimpleName());
				
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			
		}
	
		
//		public static void main(String args[]){
//			
//			String content="{\"respCode\":200,\"signMethod\":\"MD5\",\"vivoOrder\":\"145294734967721754624\",\"orderAmount\":\"10.00\",\"respMsg\":\"success\",\"vivoSignature\":\"02d5eea1ebf833c79c614d037db69be0\",\"signature\":\"9cc6f7ee33f9c7bd169f081fd48162eb\"}";
//			System.out.println(content);
//			VivoOrderResponse resp=GameContextGlobals.getJsonConvertor().convert(content, VivoOrderResponse.class);
//			
//			if(resp.validSignature("3ddf395038e95fe6ddb4d40baaf973f5")){
//				System.out.println("OK");
//			}
//			
//		}
//	
//	
//	public static void main(String args[]){
//		float f=1.00f;
//		java.text.DecimalFormat df =new java.text.DecimalFormat( "#.00");
//		//System.out.println(String.format("%-10.2f", f));
//	}

}
