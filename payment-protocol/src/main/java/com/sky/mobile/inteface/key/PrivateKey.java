/**
 * 
 * @Date:Nov 11, 2014 - 2:30:39 PM
 * @Author: sparrow
 * @Project :moible-protocol 
 * 
 *
 */
package com.sky.mobile.inteface.key;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sparrow
 *
 */
public class PrivateKey {
	
	String pattern;
	String desKey;

	/**
	 * 
	 */
	public PrivateKey() {
		// TODO Auto-generated constructor stub
	}

	

	public String getPattern() {
		return pattern;
	}



	public void setPattern(String pattern) {
		this.pattern = pattern;
	}



	public String getDesKey() {
		return desKey;
	}

	public void setDesKey(String desKey) {
		this.desKey = desKey;
	}
	
	public boolean  valid(String transcode){
		
		//return pattern.equals(cpId);
		Pattern p=Pattern.compile(this.pattern);
		Matcher m=p.matcher(String.valueOf(transcode));
		boolean  found=m.matches();
		
		
		return found;
		
	}
	

}
