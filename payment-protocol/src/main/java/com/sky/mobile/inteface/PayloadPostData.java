/**
 * 
 */
package com.sky.mobile.inteface;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
public class PayloadPostData{
	
	public static interface IPostDataParser{
		
		public <T> T parse(PayloadPostData data);
	}

	 String payload;
	 Map<String, String> parameters;
	 
	 
	

	public Map<String, String> getParameters() {
		return parameters;
	}

	public PayloadPostData(String payload) {
		super();
		this.payload = payload;
		getParameters(this.payload);
	}

	public void getParameters(String payload) {
		parameters = GameUtil.getMap();

		try {
			String[] kvs = payload.split("&");
			for (String kv : kvs) {
				String[] kkv = kv.split("=");
				if (kkv.length != 2)
					continue;

				if ("".equals(kkv[1]))
					continue;
				String v = null;
				try {
					v = URLDecoder.decode(kkv[1], "utf-8");
					parameters.put(kkv[0], v);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		//return params;
	}

	public String s( String k) {

		String v = parameters.get(k);

		return v == null ? "" : v;
	}

	public Long l( String k) {
		String v = s( k);
		Long l = Long.valueOf(0);
		if (!"".equals(v)) {
			l = Long.parseLong(v);
		}
		return l;

	}

	public  Integer i( String k) {
		Integer i = 0;
		String v = s( k);
		if (!"".equals(v)) {
			i = Integer.parseInt(v);
		}
		return i;

	}
	public Float f(String k){
		Float f=Float.valueOf(0.0f);
		String v=s(k);
		if(!"".equals(v)){
			f=Float.parseFloat(v);
		}
		return f;
	}
	
	

}
