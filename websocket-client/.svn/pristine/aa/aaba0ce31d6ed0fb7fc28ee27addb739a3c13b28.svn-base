/**
 * 
 */
package com.toyo.fish.websocket.client;

/**
 * @author sparrow
 *
 */
public enum EncryptTypes {
	True(0x31),
	False(0x30);
	
	public int value;

	private EncryptTypes(int value) {
		this.value = value;
	}
	
	public boolean booleanValue(){
		return value==0x31?true:false;
	}
	
	public static EncryptTypes get(int t){
		EncryptTypes et=False;
		switch (t) {
		case 0x31:
			et=True;
			break;
		case 0x30:
			et=False;
			break;

		default:
			break;
		}
		return et;
	}
	
}
