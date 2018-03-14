package com.toyo.fish.websocket.client;

public enum PacketTypes {
	AsyncPacketType(0x30,"Ice Server write to MinaServer"),
	SyncPacketType(0x31,"Hall&Platform Server Packet Type"),
	DeviceBindingPacketType(0x32,"Device Binding Packet Type"),
	PingPacketType(0x33,"Ping Packet Type"),
	InvalidPacketType(0x34,"Invlaid Packet Type");
	
	private int type;
	private String description;
	private PacketTypes(int type,String description){
		this.type=type;
		this.description=description;
	}
	private PacketTypes(int type) {
		this.type = type;
	}
	
	
	public boolean equalsType(PacketTypes types){
		return this.type==types.type;
		
	}
	
	public static PacketTypes get(int type){
		PacketTypes packetTypes=InvalidPacketType;
		switch (type) {
		case 0x31:
			packetTypes=SyncPacketType;
			break;
		case 0x32:
			packetTypes=DeviceBindingPacketType;
			break;
		case 0x33:
			packetTypes=PingPacketType;
			break;
			
		default:
			packetTypes=InvalidPacketType;
			break;
		}
		return packetTypes;
	}
	
	public int intValue(){
		return this.type;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "PacketTypes:[type=" +
				type +
				",description=" +
				description +
				"]";
	}
	public String getDescription() {
		return description;
	}
	
	
	
}
