/**
 * 
 */
package com.sky.game.websocket.context;

/**
 * @author sparrow
 *
 */
public enum GameConnectionStates {
	
	
	//before device attached or attach failed.
	ConnectionUndefined(0,"---undefined---"),
	ConnectionOpened(1,"---connection opened--"),
	ConnectionException(2,"---connection exception--"),
	ConnectionIdled(3,"---connection idle---"),
	
	//after device attached
	
	
	ConnectionDeviceBinding(4,"---device binding---"),
	ConnectionDeviceRecieving(5,"--recieve packet--"),
	ConnectionDeviceSending(6,"--sending packet--"),
	ConnectionDeviceUnbind(7,"---device unbind---"),
	ConnectionClosed(8,"---connection close---");
	
	static GameConnectionStates states[]={
		    ConnectionUndefined,
			ConnectionOpened,
			ConnectionException,
			ConnectionIdled,
			ConnectionDeviceBinding,
			ConnectionDeviceRecieving,
			ConnectionDeviceSending,
			ConnectionDeviceUnbind,
			ConnectionClosed
			
			
	};
	
	int state;
	String reason;
	private GameConnectionStates(int state, String reason) {
		this.state = state;
		this.reason = reason;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	public static GameConnectionStates getState(int state){
		//GameConnectionStates states=GameConnectionStates.ConnectionDeviceBindFailed;
		
		return (state>=0&&state<=8)?states[state]:GameConnectionStates.ConnectionUndefined;
		
		//return states;
	}
	
	public String toString(){
		return "[state=" +state+
				",reason=" +reason+
				"]";
	}
	

}
