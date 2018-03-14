package com.sky.mobile.sign.mi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MiSignUtil {
    private static final String MAC_NAME = "HmacSHA1" ;
    private static final String ENCODING = "UTF-8";
    /**
     * 使用 HMAC-SHA1 签名方法对对 encryptText 进行签名
     * @param encryptText 被签名的字符串
     * @param encryptKey 密钥
     * @return 返回被加密后的字符串
     * @throws Exception
     */
    public static String HmacSHA1Encrypt( String encryptText, String encryptKey ) throws Exception{
        byte[] data = encryptKey.getBytes( ENCODING );
        SecretKey secretKey = new SecretKeySpec( data, MAC_NAME );
        Mac mac = Mac.getInstance ( MAC_NAME );
        mac.init( secretKey );
        byte[] text = encryptText.getBytes( ENCODING );
        byte[] digest = mac.doFinal( text );
        StringBuilder sBuilder = bytesToHexString ( digest );
        return sBuilder.toString();
    }
    /**
     * 转换成Hex
     * @param bytesArray
     */
    public static StringBuilder bytesToHexString( byte[] bytesArray ){
        if ( bytesArray == null ){
            return null;
        }
        StringBuilder sBuilder = new StringBuilder();
        for ( byte b : bytesArray ){
            String hv = String.format("%02x", b);
            sBuilder.append( hv );
        }
        return sBuilder;
    }
    /**
     * 使用 HMAC-SHA1 签名方法对对 encryptText 进行签名
     * @param encryptData 被签名的字符串
     * @param encryptKey 密钥
     * @return 返回被加密后的字符串
     * @throws Exception
     */
     public static String HmacSHA1Encrypt( byte[] encryptData, String encryptKey ) throws Exception{
         byte[] data = encryptKey.getBytes( ENCODING );
         SecretKey secretKey = new SecretKeySpec(data,MAC_NAME);
         Mac mac=Mac.getInstance(MAC_NAME);
         mac.init( secretKey);
         byte[] digest = mac.doFinal( encryptData );
         StringBuilder sBuilder = bytesToHexString ( digest );
         return sBuilder.toString();
     }
     
     public static String getSignData(Map<String, String> params)
     {
         StringBuffer content = new StringBuffer();

         // 按照key做排序
         List<String> keys = new ArrayList<String>(params.keySet());
         Collections.sort(keys);

         for (int i = 0; i < keys.size(); i++)
         {
             String key = (String) keys.get(i);
             if ("signature".equals(key))
             {
                 continue;
             }
             String value = (String) params.get(key);
             if("".equals(value)||null==value){
            	 continue;
             }
             if (value != null)
             {
                 content.append((i == 0 ? "" : "&") + key + "=" + value);
             }
             

         }
         return content.toString();
     }
     
}