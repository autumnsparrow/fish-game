/**
 * 
 */
package com.toyo.fish.protocol.user.service.impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author Administrator
 *
 */
@Service("smsMessageService")
public class SmsMessageService {
	
	private final static Log logger=LogFactory.getLog(SmsMessageService.class);
	
	String url;
	String account;
	String password;
	String contentTemplate;
	XmlMapper objectMapper;
	/**
	 * 
	 */
	public SmsMessageService() {
		// TODO Auto-generated constructor stub
	}
	
	

	@Autowired
	public SmsMessageService(@Value("${sms.url}")String url, 
			@Value("${sms.account}")String account, 
			@Value("${sms.password}")String password,
			@Value("${sms.contentTemplate}")String contentTemplate) {
		super();
		this.url = url;
		this.account = account;
		this.password = password;
		this.contentTemplate = contentTemplate;
		objectMapper=new XmlMapper();
	}

	public  void send(String mobile, String sendMesg) {
		
		if (mobile != null && !"".equals(mobile)) {

			String msg=null;
			
			msg=String.format(contentTemplate, sendMesg);
				
			
			
			httpGet(mobile,msg);
		}
	}
	
	
	private void httpGet(String mobile,String content){
		 CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {
	            HttpPost httpget = new HttpPost(url);
	            httpget.addHeader("ContentType",
	    				"application/x-www-form-urlencoded;charset=UTF-8");
	            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	            formparams.add(new BasicNameValuePair("account", account));
	            formparams.add(new BasicNameValuePair("password", password));
	            formparams.add(new BasicNameValuePair("mobile", mobile));
	            formparams.add(new BasicNameValuePair("content", content));
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);

	            httpget.setEntity(entity);
	            ResponseHandler<SubmitResult> responseHandler = new ResponseHandler<SubmitResult>() {

	                public SubmitResult handleResponse(
	                        final HttpResponse response) throws ClientProtocolException, IOException {
	                    int status = response.getStatusLine().getStatusCode();
	                    if (status >= 200 && status < 300) {
	                        HttpEntity entity = response.getEntity();
	                        String resp= entity != null ? EntityUtils.toString(entity) : null;
	                        SubmitResult result=null;
	                       
	                        if(resp!=null){
	                        	result=objectMapper.readValue(resp, SubmitResult.class);//GameContextGlobals.getJsonConvertor().convert(resp, SmsSentResult.class);
	                        	
	                        }
	                        return result;
	                    } else {
	                        throw new ClientProtocolException("Unexpected response status: " + status);
	                    }
	                }

	            };
	            SubmitResult responseBody = httpclient.execute(httpget, responseHandler);

	            if(responseBody!=null&&responseBody.code==2){
	            	logger.info(" sms sent!");
	            }
	            
	        } catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            try {
					httpclient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	}
	
	
	
	
	public static class SubmitResult{
		int code;
		String smsid;
		String msg;
		
		
	
		
		public SubmitResult(int code, String smsid, String msg) {
			super();
			this.code = code;
			this.smsid = smsid;
			this.msg = msg;
		}
		public SubmitResult() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
	
		public String getSmsid() {
			return smsid;
		}
		public void setSmsid(String smsid) {
			this.smsid = smsid;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		@Override
		public String toString() {
			return "SubmitResult [code=" + code + ", smsid=" + smsid + ", msg="
					+ msg + "]";
		}
		
	}
}
