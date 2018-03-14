package com.sky.mobile.protocol.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;



public class Md5Encrypt {
	
		/**
		 * 
		 * 
		 * @param str
		 * @return
		 */
        public static String md5(String str) {

                if (str == null) {
                        return null;
                }

                MessageDigest messageDigest = null;

                try {
                        messageDigest = MessageDigest.getInstance("md5");
                        messageDigest.reset();
                        messageDigest.update(str.getBytes("utf-8"));
                } catch (NoSuchAlgorithmException e) {

                        return str;
                } catch (UnsupportedEncodingException e) {
                        return str;
                }

                byte[] byteArray = messageDigest.digest();

                StringBuffer md5StrBuff = new StringBuffer();

                for (int i = 0; i < byteArray.length; i++) {
                        if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                        else
                                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }

                return md5StrBuff.toString();
        }
        
        
        /**
		 * 数组字典序排序
		 * @param array
		 * @return
		 */
		public static String arraysSort(String[] array){
			Arrays.sort(array);
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < array.length; i++) {
				if(array[i]!=null)
					stringBuilder.append(array[i]);
			}
			return stringBuilder.toString();
		}


}


