package com.ipay.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.ipay.util.ConstValue;

/**
 * 发送的请求工具类
 * 
 * @author visen
 * @mail visen1118@gmail.com
 *
 */
public class HttpUtil
{
	private static final int TIME_OUT = 15;
	
	/**
	 * 以Post方式发送请求
	 * 
	 * @param httpUrl		请求的URL地址
	 * @param parameter		请求参数
	 * @param cookie		cookie
	 * @return
	 */
	public static String post( String httpUrl, Map<String, String> parameter, String cookie ){
		if (parameter == null || httpUrl == null) {
			return null;
		}

		// 初始化请求参数，变成以a=1&b=2&c=xxx的方式，同时作urlEncode，默认为UTF-8
		StringBuffer paramsString = new StringBuffer();
		Iterator<Map.Entry<String, String>> iterator = parameter.entrySet().iterator();
		while (iterator.hasNext()) 
		{
			if (paramsString.length() > 0)
			{
				paramsString.append('&');
			}
			Entry<String, String> entry = iterator.next();
			String key = entry.getKey();
			String value;
			try {
				value = URLEncoder.encode(entry.getValue(), ConstValue.DEFAULT_HTTP_ENCODING);
			} catch (UnsupportedEncodingException e) 
			{
				value = "";
			}
			paramsString.append(key).append('=').append(value);
		}
		
		// 初始化及打开网络链接
		HttpURLConnection httpCon = null;
		StringBuffer responseBody =  new StringBuffer();
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		try {
			
			URL url;
			try {
				url = new URL(httpUrl);
				httpCon = (HttpURLConnection) url.openConnection();
				
				// post方式，以下两行必填。
				httpCon.setDoOutput(true);
				httpCon.setDoInput(true);
				httpCon.setRequestProperty( ConstValue.CONTENT_LENGTH_STR, String.valueOf( paramsString.length() ) );
				
				// 设置为Post方式，同时设置连接和IO超时时间
				httpCon.setRequestMethod(ConstValue.HTTP_POST );
				httpCon.setConnectTimeout(TIME_OUT * 1000);
				httpCon.setReadTimeout(TIME_OUT * 1000);

			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}catch (ProtocolException e) {
				e.printStackTrace();
				return null;
			}
			
			// 设置cookie
			if (cookie != null && !"".equals(cookie))
				httpCon.setRequestProperty("Cookie", cookie);
			
			// 写入post的参数
			printWriter = new PrintWriter(httpCon.getOutputStream());  
			printWriter.write( paramsString.toString() );
			System.out.println("Request params : " + paramsString.toString());
			printWriter.flush();
			
			// 获取返回码，不为200，则返回错误 
			int responseCode = httpCon.getResponseCode();
			if (responseCode != ConstValue.STATUS_CODE_OK){
				System.out.println("ResponseCode is " + responseCode + " and return null for response now");
				return null;
			}
			
			// 读取返回结果
			bufferedReader = new BufferedReader(new InputStreamReader(  
					httpCon.getInputStream()));			
			String line;  
            while ((line = bufferedReader.readLine()) != null) {  
            	responseBody.append(line).append("\n");  
            }			
		} catch (Exception e) {
			System.err.println( "connect to iapppay server fail" );
			e.printStackTrace();
			return null;
		}
		finally
		{
			// 关闭网络链接，关闭读写通道
			if (httpCon != null)
				httpCon.disconnect();
			
			try {  
                if (printWriter != null) {  
                    printWriter.close();  
                }  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
		}
		System.out.println("Response data : " + responseBody.toString().trim());
		return responseBody.toString().trim();
	}
}
