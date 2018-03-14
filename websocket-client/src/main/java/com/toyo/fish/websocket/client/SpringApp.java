/**
 * 
 */
package com.toyo.fish.websocket.client;

import java.io.IOException;

import org.springframework.shell.Bootstrap;

import com.sky.game.context.SpringContext;

/**
 * @author sparrow
 *
 */
public class SpringApp {

	/**
	 * 
	 */
	public SpringApp() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Bootstrap.main(args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
