/**
 * 
 */
package com.toyo.fish.websocket.client;

/**
 * @author sparrow
 *
 */
public interface IWebsocketMessage {
	
	public void send(String message);
	
	public void onResponse(String message);

}
