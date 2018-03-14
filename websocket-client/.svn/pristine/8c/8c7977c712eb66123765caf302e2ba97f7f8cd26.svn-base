/**
 * 
 */
package com.toyo.fish.websocket.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.Message;
import com.sky.game.context.annotation.introspector.AnnotationIntrospector.ProtocolMapEntry;
import com.sky.game.context.configuration.GameContextConfiguration;
import com.sky.game.context.configuration.GameContxtConfigurationLoader;
import com.sky.game.context.util.DES.ProtocolEntry;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.sky.game.context.util.WebsocketConfiguration;

import com.toyo.fish.protocol.beans.PEC0000Beans;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0001Request;
import com.toyo.fish.protocol.beans.PEC0000Beans.PEC0001Response;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0001Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0002Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0003Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0005Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0006Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0003Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0004Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0005Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0001Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0002Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0003Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0004Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0005Request;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0001Requet;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0004Requet;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0006Request;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0008Request;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0008Response;
import com.toyo.fish.protocol.beans.PRS0000Beans.PRS0001Request;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0002Requet;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0001Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0003Request;
import com.toyo.fish.websocket.client.FishWebsocketWrapper.IOnClosed;
import com.toyo.fish.websocket.client.util.DES;
import com.toyo.fish.websocket.client.util.HexDump;
import com.toyo.fish.websocket.client.util.RSAUtils;

/**
 * 
 * 
 * The client command must be called serial.
 * @author sparrow
 *
 */
@WebSocket(maxTextMessageSize = 128 * 1024)
public class WebsocketClientHandler {
	private static final Log logger = LogFactory.getLog(WebsocketClientHandler.class);
	

	IOnResponse onResponse;
	public void setOnResponse(IOnResponse onResponse) {
		this.onResponse = onResponse;
	}



	private final CountDownLatch closeLatch;

	static AtomicInteger counter = new AtomicInteger(0);
	
	private boolean logined;

	private IWebsocketMessageHandle afterLoginHandler;
	
	private IOnClosed onClosed;
	public void setOnClosed(IOnClosed onClosed) {
		this.onClosed = onClosed;
	}



	public void setAfterLoginHandler(IWebsocketMessageHandle afterLoginHandler) {
		this.afterLoginHandler = afterLoginHandler;
	}

	@SuppressWarnings("unused")
	private Session session;

	public WebsocketClientHandler() {
		this.closeLatch = new CountDownLatch(1);
	}
	
	

	public WebsocketClientHandler(String deviceId, Integer channelId) {
		super();
		this.deviceId = deviceId;
		this.channelId = channelId;
		this.closeLatch = new CountDownLatch(1);
		this.logined=false;
	}



	public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
		return this.closeLatch.await(duration, unit);
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
	//	System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
		this.session = null;
		this.closeLatch.countDown();
		if(onClosed!=null){
			onClosed.onClose(statusCode,reason);
		}
	}

	@OnWebSocketMessage
	public void onMessage(Session session, byte[] bytes, int offset, int len) {

		try {
			//logger.info("\r\n" + com.sky.game.context.util.HexDump.dumpHexString(bytes, offset, len));
			this.binary = ByteBuffer.wrap(bytes, offset, len);
			this.unmashall();
			//logger.info(content);
			this.onResponse(this.content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	String deviceId;
	Long userId;
	
	
	

	public void setUserId(Long userId) {
		this.userId = userId;
	}



	@OnWebSocketConnect
	public void onConnect(Session session) {
		System.out.printf("Got connect: %s%n", session);
		this.session = session;
		try {
			counter.getAndAdd(1);
			//deviceId = String.format("D012345678%d", counter.intValue());
			desKey = String.format("xxxxx2222222222%d", counter.intValue());
			// deviceId="ae11181f4798d03a26fbad153bbd86b4";
			//deviceId = "D01234567861"+System.currentTimeMillis();
			//deviceId="fd860713ac85bfbb6eca52e4d6b809021409a623";
			//deviceId = "1d6a9bb40f43824c0c117fcb4abe98d172f0c8da";
			String binding = String.format("%s~%s", deviceId, desKey);
			this.packetType = PacketTypes.DeviceBindingPacketType;
			this.encryptType = EncryptTypes.False;
			this.content = binding;
			this.mashall();

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@OnWebSocketMessage
	public void onMessage(String msg) {
		System.out.printf("Got msg: %s%n", msg);

	}

	String desKey;
	PacketTypes packetType;
	EncryptTypes encryptType;
	ByteBuffer binary;

	String content;

	public void mashall() throws IOException {
		byte t = (byte) packetType.intValue();
		byte e = (byte) encryptType.value;

		byte[] contentBytes = null;
		if (encryptType.booleanValue()) {
			// encrypt
			contentBytes = RSAUtils.getBytes(content);
			if (contentBytes != null) {
				byte[] bytes = DES.des().encrypt(desKey, contentBytes);
				if (bytes != null) {
					ByteBuffer byteBuffer = ByteBuffer.allocate(2 + bytes.length);
					byteBuffer.put(t);
					byteBuffer.put(e);
					byteBuffer.put(bytes);
					byteBuffer.flip();
					this.binary = byteBuffer;
				}
				if (!packetType.equalsType(PacketTypes.PingPacketType))
					logger.debug("\n" + HexDump.dumpHexString(contentBytes));
			}

		} else {
			// no- encrypt
			this.binary = RSAUtils.getBytes(t, e, content);
			if (!packetType.equalsType(PacketTypes.PingPacketType))
				logger.debug("\n" + HexDump.dumpHexString(this.binary.array()));
		}

		// send the message

		session.getRemote().sendBytes(this.binary);

	}

	public void unmashall() {
		this.binary.mark();
		byte t = this.binary.get();
		byte e = this.binary.get();

		packetType = PacketTypes.get(t);
		encryptType = EncryptTypes.get(e);
		//
		byte[] encryptedBytes = binary.array();

		if (encryptType.booleanValue()) {

			byte[] bytes = null;

			if (packetType.equalsType(PacketTypes.DeviceBindingPacketType)) {

				bytes = RSAUtils.rsa().decrypt(encryptedBytes, 2, encryptedBytes.length - 2);

			} else if (packetType.equalsType(PacketTypes.SyncPacketType)) {
				bytes = DES.des().decrypt(desKey, encryptedBytes, 2, encryptedBytes.length - 2);
			}

			content = RSAUtils.rsa().bytes2String(bytes, 0, bytes.length);
			if (!packetType.equalsType(PacketTypes.PingPacketType))
				logger.info("\n" + HexDump.dumpHexString(bytes));
		} else {
			// no encrypt
			// byte[] bytes=this.binary.array();
			if (!packetType.equalsType(PacketTypes.PingPacketType))
				logger.debug("\n" + HexDump.dumpHexString(encryptedBytes));

			content = RSAUtils.rsa().bytes2String(encryptedBytes, 2, encryptedBytes.length - 2);

		}

		this.binary.reset();

		if (packetType.equalsType(PacketTypes.DeviceBindingPacketType)) {
			// binding(content,encryptType.booleanValue());
			// pass
		} else if (packetType.equalsType(PacketTypes.SyncPacketType)) {
			// forward the request.

		} else if (packetType.equalsType(PacketTypes.PingPacketType)) {
			// pong

			// pong();
		}
	}

	public void send() {
		// TODO Auto-generated method stub
		// this.content=message;
		this.packetType = PacketTypes.SyncPacketType;
		this.encryptType = EncryptTypes.False;
		try {
			this.mashall();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onResponse(String message) {
		// TODO Auto-generated method stub
		
		//final Long userId;
		if (this.packetType.equalsType(PacketTypes.SyncPacketType)) {
			Message msg = getMessage();
			logger.info(message);
			if (msg.transcode.equals("UP0001")) {
				afterUserLogin();
				PU0001Response resp=G.parse(msg.content, PU0001Response.class);
				userId=resp.getUserId();
			} else {

				IWebsocketMessageHandle handle = map.get(msg.transcode);
				if (handle != null)
					handle.handle(msg.content);
				
			}
			

			if(onResponse!=null){
				onResponse.response(userId, msg.transcode, msg.content);
			}
		} else if (this.packetType.equalsType(PacketTypes.DeviceBindingPacketType)) {
			afterDeviceBinding();
		}
		
		

	}

	public void add(String transcode, IWebsocketMessageHandle handle) {
		map.put(transcode, handle);
	}

	public static interface IWebsocketMessageHandle {

		public void handle(String content);
	}

	public final Map<String, IWebsocketMessageHandle> map = GameUtil.getMap();

	public Message getMessage() {
		Message message = new Message();
		String[] entries = content.split("&");
		if (entries != null && entries.length == 3) {
			message.transcode = entries[0];
			message.token = entries[1];
			message.content = entries[2];
		}
		return message;
	}

	public void send(String transcode, String o) {
		this.content = transcode + "&&" + o;
		this.send();
	}

	public void send(String transcode, Object obj) {
		// this.content=transcode+"&&"+GameContextGlobals.getJsonConvertor().format(obj);
		// this.send();
		String o = GameContextGlobals.getJsonConvertor().format(obj);
		this.send(transcode, o);
	}

	Integer channelId;

	public void afterDeviceBinding() {
		// here setting the login
		//channelId = 10037;

		// String deviceId=deviceId;//"id-0123456789";
		PU0001Request req = G.o(PU0001Request.class);
		req.setChannel(channelId);
		// req.setImei("ae11181f4798d03a26fbad153bbd86b4");
		req.setImei(deviceId);
		// Message msg=G.o(Message.class);

		send("PU0001", req);

	}
	
	

	public boolean isLogined() {
		return logined;
	}



	public void afterUserLogin() {
		this.logined=true;

		// here we can register other kinds of protocol.

		if(this.afterLoginHandler!=null){
			this.afterLoginHandler.handle(content);
		}
		// PP0008

		// testPP0008();
		// testPec001();
		// for(int i=0;i<1000;i++)
		// testPs0002();
		// testPRS0001();
		//testPP0001();
		// testPU0003();
		// testPFU0005();
		// send("pfu0003.json","PFU0003","UFP0003",PFU0003Request.class);
		// send("pfu0004.json","PFU0004","UFP0004",PFU0004Request.class);
		// send("pfu0005_failed.json","PFU0005","UFP0005",PFU0005Request.class);
		// send("pfu0005.json","PFU0005","UFP0005",PFU0005Request.class);
		// send("pms0001.json","PMS0001","SMP0001",PMS0001Request.class);
		// send("pfs0001_failed.json","PFS0001","SFP0001",PFS0001Request.class);
		// send("pfs0002.json","PFS0002","SFP0002",PFS0002Request.class);
		// send("pms0002.json","PMS0004","SMP0004",PMS0004Request.class);
		// send("pfs0006.json","PFS0006","SFP0006",PFS0006Request.class);
		//	send("pfs0003.json","PFS0003","SFP0003",PFS0003Request.class);
		// send("pms0003.json","PMS0003","SMP0003",PMS0003Request.class);
		
		//send("pfs0005.json","PFS0005","SFP0005",PFS0005Request.class);
		//send("pms0005.json","PMS0005","SMP0005",PMS0005Request.class);
	}

	
	
	private void testPMS0001(){
		add("PMS0001", new IWebsocketMessageHandle() {

			
			public void handle(String content) {
				// TODO Auto-generated method stub
				logger.info(content);
			}
		});

		
		PMS0001Request obj=G.o(PMS0001Request.class);
		obj.setActiveDateTime("2016-03-24 15:30:00");
		obj.setActiveHours(120);
		obj.setAttachments("");
		obj.setCategory(0);
		obj.setTitle("油荒很可怕！");
		obj.setContent("那们小哥呀！");
		obj.setTo(null);
		
		send("PMS0001", obj);
	}
	/**
	 * 
	 * {"fishId":"Fish_01_01_09","rodId":"Rod_0001_01","rodProps":
	 * "{\"rodProps\":{\"RodGrade\":0,\"RodUpGrade\":0}}","wheelId":"Sr_0001_01"
	 * ,"wheelProps":"{\"wheelProps\":{\"ReelGrade\":0,\"ReelUpGrade\":0}}",
	 * "lineId":"Line_0001_01","lureId":"Bait_0001_01","drugId1":"Drink_0000_01"
	 * ,"drugId2":"Drink_0000_01","drugId3":"Drink_0000_01","bigFishAddition":
	 * "18%","fishWeight":-1.0,"level":5,"fishRank":-1,"fishRare":-1,
	 * "tensionTriggers":0,"tensionUsed":0,"dragTriggers":0,"dragUsed":0,
	 * "fishDuration":2,"seq":105}
	 * 
	 */
	
	public <R> void send(String transcode,String restranscode,R o,IWebsocketMessageHandle callback){
		
		if(isLogined()){
			
				add(restranscode, callback);
				send(transcode, o);
			
		}
		
	}
	

	

	private <R> void send(String file, String transcode, String responseTranscode, Class<?> clz) {
		add(responseTranscode, new IWebsocketMessageHandle() {

			
			public void handle(String content) {
				// TODO Auto-generated method stub
				logger.info(content);
			}
		});
		InputStream url = WebsocketClientHandler.class.getResourceAsStream("/" + file);
		R req = (R) GameContxtConfigurationLoader.loadConfiguration(url, clz);
		send(transcode, req);

	}

	private void testPFU0005() {

		send("pfu0005_failed.json", "PFU0005", "UFP0005", PFU0005Request.class);

	}

	private void testPU0003() {
		add("UP0003", new IWebsocketMessageHandle() {

			
			public void handle(String content) {
				// TODO Auto-generated method stub
				logger.info(content);
			}
		});

		PU0003Request obj = G.o(PU0003Request.class);
		obj.setAccount("tx02");
		send("PU0003", obj);
	}
	static boolean flag = false;
	public void testPP0001() {
		if (flag) {
			add("PP0001", new IWebsocketMessageHandle() {

				
				public void handle(String content) {
					// TODO Auto-generated method stub
					logger.info(content);
				}
			});

			PP0001Requet req = G.o(PP0001Requet.class);
			req.setPrice(600);
			req.setProductId("DLB");
			send("PP0001", req);
			flag = false;
		} else {
			add("PP0006", new IWebsocketMessageHandle() {

				
				public void handle(String content) {
					// TODO Auto-generated method stub
					logger.info(content);
				}
			});

			PP0006Request req2 = G.o(PP0006Request.class);
			req2.setPrice(600);
			req2.setProductId("DLB");
			req2.setProductDescription("Websocket Test ");
			req2.setProductTitle("Websocket Client Test ");
			send("PP0006", req2);
			flag = true;
		}
	}

	private void testPP0006() {

	}

	private void testPRS0001() {

		add("SRP0001", new IWebsocketMessageHandle() {

			
			public void handle(String content) {
				// TODO Auto-generated method stub
				logger.info(content);
			}
		});

		PRS0001Request req = G.o(PRS0001Request.class);

		req.setFishId("");
		// req.set
		// PEC0001Response
		send("PRS0001", req);
	}

	private void testPs0002() {
		add("SP0002", new IWebsocketMessageHandle() {

			
			public void handle(String content) {
				// TODO Auto-generated method stub
				logger.info(content);
			}
		});

		PS0002Requet req = G.o(PS0002Requet.class);
		req.setChannelId(10037);
		req.setVersion("20001000");
		// PEC0001Response
		send("PS0002", req);

	}

	private void testPec001() {
		add("CEP0001", new IWebsocketMessageHandle() {

			
			public void handle(String content) {
				// TODO Auto-generated method stub
				logger.info(content);
			}
		});

		PEC0001Request req = G.o(PEC0001Request.class);
		req.setExchangeCode("666666");
		// PEC0001Response
		send("PEC0001", req);

	}

	private void testPP0008() {
		// PP0008Request req=G.o(PP0008Request.class);
		add("PP0008", new IWebsocketMessageHandle() {

			
			public void handle(String content) {
				// TODO Auto-generated method stub
				// PP0008Response
				// resp=GameContextGlobals.getJsonConvertor().convert(content,
				// PP0008Response.class);
				logger.info(content);

			}
		});
		send("PP0008", "{}");

	}
	
	
	
	
	/**
	 * 
	 *  Module Testing.
	 * 
	 * 
	 */
	
	//
	// Mail Test
	//
	//

}
