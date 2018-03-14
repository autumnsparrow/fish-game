/**
 * 
 */
package com.toyo.fish.websocket.client;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.sky.game.context.SpringContext;

/**
 * @author sparrow
 *
 */
public class FishAppWebsocketClient {

	private static void connect() {
		WebSocketClient client = new WebSocketClient();
		WebsocketClientHandler socket = new WebsocketClientHandler();
		try {
			client.start();
			URI echoUri = new URI(destUri);
			ClientUpgradeRequest request = new ClientUpgradeRequest();
			client.connect(socket, echoUri, request);
			System.out.printf("Connecting to : %s%n", echoUri);
			socket.awaitClose(10, TimeUnit.MINUTES);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				client.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	private static final boolean ONLINE = false;

	//private static String destUri = "ws://www.toyo.cool:8080/game-websocket/api";
	private static String destUri = ONLINE?"ws://websocket.toyo.cool:8080/game-websocket/api":"ws://localhost:8080/game-websockets/api";
	//private static String  destUri = "ws://localhost:8080/game-websockets/api";

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//SpringContext.init("classpath:/META-INF/spring/applicationContext.xml");
		runTask(1);
	}

	private static void runTask(int start) {
		CountDownLatch countDownLatch = new CountDownLatch(start);

		ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 200, 6, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>());
		// EventProcessTask task=new EventProcessTask<T>(t);
		for (int i = 0; i < 100; i++) {
			try {

				executor.submit(new Runnable() {

					
					public void run() {
						// TODO Auto-generated method stub
						connect();
					
					}
				});
				countDownLatch.await(30, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
