package com.sky.mobile.sign.lenovo;

public class LenovoSignHelper
{
	// 字符编码格式 ，目前支持  utf-8
	public static String input_charset = "utf-8";
	
	public static boolean verify(String content, String sign, String pubKey)
	{
		// 目前版本，只支持RSA
		return LenovoRSA.verify(content, sign, pubKey, input_charset);
	}
	
	public static String sign(String content, String privateKey)
	{
		return LenovoRSA.sign(content, privateKey, input_charset);
	}
}
