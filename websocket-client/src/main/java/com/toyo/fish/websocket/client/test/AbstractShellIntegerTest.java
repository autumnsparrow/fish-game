/**
 * 
 */
package com.toyo.fish.websocket.client.test;

import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.JLineShellComponent;

/**
 * @author sparrow
 *
 */
public abstract	 class AbstractShellIntegerTest {
	private static JLineShellComponent shell;
	
	
	public static void startUp() throws InterruptedException { Bootstrap bootstrap = new Bootstrap();
		shell = bootstrap.getJLineShellComponent();
	}
	
	public static void shutdown() { 
		shell.stop();
	}
	public static JLineShellComponent getShell() {
		return shell;
	}
	
	public abstract void doTask();

}
