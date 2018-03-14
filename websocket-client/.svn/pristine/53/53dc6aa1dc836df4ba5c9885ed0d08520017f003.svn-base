/**
 * 
 */
package com.toyo.fish.websocket.client.cmd;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.util.GameUtil;


/**
 * @author sparrow
 *
 */
public class ObjectSeq<K,R> {

	/**
	 * 
	 */
	public ObjectSeq() {
		// TODO Auto-generated constructor stub
	}
	
	private Map<K,R> records=GameUtil.getMap();
	
	
	public String delete( K id){
		records.remove(id);
		return id+" deleted";
	}
	


	public String clear(
			){
		int size=records.size();
		records.clear();
		return size+" deleted";
	}
	
	
	public String show(
			){
		int size=records.size();
		
		StringBuffer buf=new StringBuffer();
		buf.append("Size:").append(size).append("\n");
		for(Iterator<Entry<K,R>> it= records.entrySet().iterator();it.hasNext();){
			Entry<K,R> e=it.next();
			buf.append(e.getKey()).append("|");
			buf.append(GameContextGlobals.getJsonConvertor().format(e.getValue())).append("\n");
		}
		
		return buf.toString();
	}
	
	
	
	
	
	public String create(K k,R o
				){
		
		records.put(k, o);
		String json=GameContextGlobals.getJsonConvertor().format(o);
		return json;
	}
	
	public Collection<R> getAll(){
		return records.values();
	}
	

}
