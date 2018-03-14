/**
 * @author:sparrow
 * @date:Jul 23, 2014
 * @company:北京博志万通科技有限公司
 *
 *	
 */
package com.sky.mobile.protocol.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author sparrow
 *
 */
public class CollectionUtil {

	/**
	 * 
	 */
	public CollectionUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public  static <T> List<T> getList(Set<T> s){
		List<T> list=new LinkedList<T>();
		for(T t:s){
			list.add(t);
		}
		return list;
	}

}
