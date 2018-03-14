/**
 * 
 */
package com.toyo.fish.websocket.client.cmd;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author sparrow
 *
 */
public class ScriptsBuilder {
	
	StringBuffer buffer;

	public ScriptsBuilder() {
		super();
		// TODO Auto-generated constructor stub
		buffer=new StringBuffer();
	}
	
	public void reset(){
		buffer=new StringBuffer();
	}
	
	
	public void append(String cmd){
		buffer.append(cmd).append("\n");
	}
	
	public void save(String name){
		File f=new File(name);
		
		try {
			FileUtils.writeStringToFile(f, buffer.toString());
			System.out.println("scripts --file "+f.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
