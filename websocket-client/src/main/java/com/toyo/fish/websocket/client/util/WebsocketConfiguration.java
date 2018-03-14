package com.toyo.fish.websocket.client.util;

import java.io.InputStream;

import com.sky.game.context.configuration.GameContxtConfigurationLoader;
import com.sky.game.context.util.G;


public class WebsocketConfiguration {
	
	
	private String publicKeyFile;
	
	private String privateKeyFile;
	
	private String password;
	
	
	private int threadPoolSize;
	
	

	static WebsocketConfiguration configuration=null;
	
	
	static{
		//String config=System.getenv("NODE_NAME");
		// debug.websocket.node-1
		//String config=G.getNode();
		String path="/META-INF/websocket-context.conf";
		load(path);
	}
	public static WebsocketConfiguration load(String file){
		InputStream url=WebsocketConfiguration.class.getResourceAsStream(file);
		configuration=GameContxtConfigurationLoader.loadConfiguration(url, WebsocketConfiguration.class);
		return configuration;
	}
	public static WebsocketConfiguration getInstance(){
		return configuration;
	}
	


	
	public String getVersion() {
		return null;
	}

	public void validate() {
		
	}


	public String getPublicKeyFile() {
		return publicKeyFile;
	}

	public void setPublicKeyFile(String publicKeyFile) {
		this.publicKeyFile = publicKeyFile;
	}

	public String getPrivateKeyFile() {
		return privateKeyFile;
	}

	public void setPrivateKeyFile(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

}
