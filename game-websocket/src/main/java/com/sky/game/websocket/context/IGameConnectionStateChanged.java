/**
 * 
 */
package com.sky.game.websocket.context;

/**
 * @author sparrow
 *
 */
public interface IGameConnectionStateChanged {
	public static final GameConnectionStates stateForPlayer=GameConnectionStates.ConnectionDeviceBinding;
	
	
	/**
	 * 
	 *  >=ConnectionDeviceBinding  for the player context watch the network connection state.
	 * 
	 * @param deviceId
	 * @param state
	 */
	public void onGameConnectionStateChanged(String connectionId,GameConnectionStates state);

	public void onGameConnectionStateBinding(String connectionId,String deviceId);
	
	public void onGameConnectionStateUnBind(String connectionId);
	
	public void clearup();
}
