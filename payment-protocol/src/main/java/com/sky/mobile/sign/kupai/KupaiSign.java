/**
 * 
 */
package com.sky.mobile.sign.kupai;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author sparrow
 *
 */
public class KupaiSign {
	
	public static String getHash(Map<String, String> paramMap , String secret) throws UnsupportedEncodingException{
		List<String> keys = new ArrayList<String>(paramMap.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			if(key.equalsIgnoreCase("hash")){
				continue;
			}
			String value = String.valueOf(null == paramMap.get(key) ? "" : paramMap.get(key));
			if ("null".equalsIgnoreCase(value)) {
				value = "";
			} else {
				value = URLEncoder.encode(value, "UTF-8");
			}
			if (i == keys.size() - 1) {
				prestr += key + "=" + value;
			} else {
				prestr += key + "=" + value + "&";
			}
		}
		return md5(prestr += secret, true);
	}
	
	private static final char[] HEX = "0123456789ABCDEF".toCharArray();

	public static final String md5(String s, boolean upperCase) { 
		try { 
			MessageDigest digest = MessageDigest.getInstance("MD5"); 
			digest.reset(); 
			digest.update(s.getBytes("UTF-8")); 
			byte[] bb = digest.digest(); 
			StringBuffer out = new StringBuffer(); 
			for (byte b: bb) out.append(HEX[(b & 0xF0) >>> 4]).append(HEX[b & 0x0F]);
			String result = out.toString();
			return upperCase ? result : result.toLowerCase();
		} catch (Exception e) { } 
		return null;
	}


}
