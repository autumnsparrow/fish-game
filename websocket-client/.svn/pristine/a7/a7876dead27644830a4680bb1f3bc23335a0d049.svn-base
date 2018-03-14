package com.toyo.fish.websocket.client.cmd;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.executor.OrderedThreadPoolExecutor;
import com.sky.game.context.executor.UnorderedThreadPoolExecutor;
import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.data.wrapper.domain.UserAccount;
import com.toyo.fish.data.wrapper.domain.UserAccountFriends;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0001Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0002Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0003Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0004Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0005Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0006Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0006Response;
import com.toyo.fish.protocol.beans.PFU0000Beans.CurrencyRecord;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0001Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0003Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0004Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.PFU0005Request;
import com.toyo.fish.protocol.beans.PFU0000Beans.VitRecord;
import com.toyo.fish.protocol.beans.PMS0000Beans.MailObj;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0003Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0004Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0005Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0006Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0007Request;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0001Requet;
import com.toyo.fish.protocol.beans.PP0000Beans.PP0006Request;
import com.toyo.fish.protocol.beans.PRS0000Beans.PRS0001Request;
import com.toyo.fish.protocol.beans.PRS0000Beans.PRS0002Request;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0003Requet;
import com.toyo.fish.protocol.beans.PS0000Beans.PS0004Requet;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0011Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0012Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0013Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0014Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0014Response;
import com.toyo.fish.protocol.beans.PU0000Beans.User;
import com.toyo.fish.protocol.service.domain.FriendsUser;
import com.toyo.fish.protocol.service.domain.MailAttachment;
import com.toyo.fish.protocol.service.domain.MailAttachments;
import com.toyo.fish.protocol.service.domain.MailLevel;
import com.toyo.fish.protocol.service.domain.MailTriggerExpresion;
import com.toyo.fish.websocket.client.FishWebsocketWrapper;
import com.toyo.fish.websocket.client.IWebsocketMessage;
import com.toyo.fish.websocket.client.WebsocketClientHandler.IWebsocketMessageHandle;
import com.toyo.fish.websocket.client.cmd.MockMobile.FriendMailTypes;
import com.toyo.fish.websocket.client.cmd.MockMobile.MailCategory;
import com.toyo.fish.websocket.client.cmd.MockMobile.MailStates;
import com.toyo.remote.service.system.domain.ProtocolModuleTypes;

@Component
public class WebsocketCommand implements CommandMarker {

	private static final Log logger = LogFactory.getLog(WebsocketCommand.class);
	
	  static class DefaultWebsocketThreadFactory implements ThreadFactory {
	        private static final AtomicInteger poolNumber = new AtomicInteger(1);
	        private final ThreadGroup group;
	        private final AtomicInteger threadNumber = new AtomicInteger(1);
	        private final String namePrefix;

	        DefaultWebsocketThreadFactory() {
	            SecurityManager s = System.getSecurityManager();
	            group = (s != null) ? s.getThreadGroup() :
	                                  Thread.currentThread().getThreadGroup();
	            namePrefix = "websocket-" +
	                          poolNumber.getAndIncrement() +
	                         "-thread-";
	        }

	        public Thread newThread(Runnable r) {
	            Thread t = new Thread(group, r,
	                                  namePrefix + threadNumber.getAndIncrement(),
	                                  0);
	            if (t.isDaemon())
	                t.setDaemon(false);
	            if (t.getPriority() != Thread.NORM_PRIORITY)
	                t.setPriority(Thread.NORM_PRIORITY);
	            return t;
	        }
	    }
	  
	  static class DefaultTaskThreadFactory implements ThreadFactory {
	        private static final AtomicInteger poolNumber = new AtomicInteger(1);
	        private final ThreadGroup group;
	        private final AtomicInteger threadNumber = new AtomicInteger(1);
	        private final String namePrefix;

	        DefaultTaskThreadFactory() {
	            SecurityManager s = System.getSecurityManager();
	            group = (s != null) ? s.getThreadGroup() :
	                                  Thread.currentThread().getThreadGroup();
	            namePrefix = "task-" +
	                          poolNumber.getAndIncrement() +
	                         "-thread-";
	        }

	        public Thread newThread(Runnable r) {
	            Thread t = new Thread(group, r,
	                                  namePrefix + threadNumber.getAndIncrement(),
	                                  0);
	            if (t.isDaemon())
	                t.setDaemon(false);
	            if (t.getPriority() != Thread.NORM_PRIORITY)
	                t.setPriority(Thread.NORM_PRIORITY);
	            return t;
	        }
	    }

	@Autowired
	UnorderedThreadPoolExecutor executor;
	
	
	
//	ThreadPoolExecutor executor=new ThreadPoolExecutor(100, 300, 50, TimeUnit.MINUTES, new BlockingArrayQueue<Runnable>(1000));
	@CliAvailabilityIndicator({ "ws connect" })
	public boolean isCommandAvailable() {
		return true;
	}

	String destUri = "ws://www.toyo.cool:8080/game-websocket/api";
	// String destUri = "ws://localhost:8080/game-websockets/api";

	private static enum ServerTypes {
		Local(0), Test(1), Product(2), Bench(3);
		private int type;

		private ServerTypes(int type) {
			this.type = type;
		}

	}

	private <R> String baseCommand(Long deviceId, String desc, final String transcode, final String retranscode,
			final R o) {

		// final DeviceId id = deviceId;

		final Long uid = deviceId;
//		executor.execute(new Runnable() {
//
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					userWebsocketMap.get(uid).send(transcode, retranscode, o, new IWebsocketMessageHandle() {
//
//						public void handle(String content) {
//							// TODO Auto-generated method stub
//							logger.info(content);
//						}
//					});
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
		try {
			userWebsocketMap.get(uid).send(transcode, retranscode, o, new IWebsocketMessageHandle() {

				public void handle(String content) {
					// TODO Auto-generated method stub
					logger.info(content);
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return desc + " " + uid;
	}

	private List<Long> getIdSeqs(String idSeq) {
		String[] seqs = idSeq.split(",");
		List<Long> mailIdSeq = GameUtil.getList();
		for (String s : seqs) {
			mailIdSeq.add(Long.parseLong(s));
		}

		return mailIdSeq;
	}

	/**
	 * 19922094,19922116,19922118,19922099
	 * 
	 * | 39054942f270f15a380ee944581f8b96 | 10012 | |
	 * 0af2a43850b0f18770b46d3865a51866 | 10012 | |
	 * 51c80a1fbf9ab2168d595f1d089df64f | 10012 | |
	 * 55eeaf2390f3d0e3b23638c923df99a2 | 10012 |
	 * 
	 * 1488,1510,1512,1493)
	 * 
	 * 
	 * 
	 * D19922103 | 19922103|1497 | db82b0084d6cb69af29e1e1c4d85dc13 | 10018 | 0
	 * | | D19922104 | 19922104|1498 | 0af2a43850b0f18770b46d3865a51866 | 10038
	 * | 0 | | D19922105 | 19922105|1499 | 123 | 10038 | 0 | | D19922106 |
	 * 19922106|1500 | 98f27e93be26bd57352ab8d34acf4b23 | 10030 | 0 | |
	 * D19922107 | 19922107|1501 | 1234 | 10038 | 0 | | D19922108 |
	 * 19922108|1502 | 98f27e93be26bd57352ab8d34acf4b23 | 10032 | 0 | |
	 * D19922109 | 19922109|1503 | 695084c99b3c77533f50437dfd54e6729b2cb8b3 |
	 * 10032 | 0 | | D19922110 | 19922110|1504 |
	 * 979abc98b334758024d934afa939beb7 | 10032 | 0 | | D19922111 |
	 * 19922111|1505 | 0af2a43850b0f18770b46d3865a51866 | 100 | 0 | | D19922112
	 * | 19922112|1506 | 0af2a43850b0f18770b46d3865a51866 | 10018 | 0 | |
	 * D19922113 | 19922113|1507 | de44dd48c2c165f55b066a1da459d3bb | 10030 | 0
	 * | | D19922114 | 19922114|1508 | de44dd48c2c165f55b066a1da459d3bb | 10047
	 * | 0 | | D19922115 | 19922115|1509 | c05d25fb4629401a12dbd44645a7d6a4 |
	 * 10012 | 0 | | D19922116 | 19922116|1510 |
	 * 51c80a1fbf9ab2168d595f1d089df64f | 10012 | 0 | | D19922117 |
	 * 19922117|1511 | 98f27e93be26bd57352ab8d34acf4b23 | 10012 | 0 | |
	 * D19922118 | 19922118|1512 | 55eeaf2390f3d0e3b23638c923df99a2 | 10012 | 0
	 * | | D19922119 | 19922119|1513 | 11111 | 10012 | 0 | | D19922120 |
	 * 19922120|1514 | 51c80a1fbf9ab2168d595f1d089df64f | 10038 | 0 | |
	 * D19922121 | 19922121|1515 | 143b3c42e058efbe7403910afea8bad69e6d00ec | 20
	 * | 0 | D19922122('19922122|1516','53991009054104da91faee389b26b05e',10030
	 * , 0 )
	 * 
	 * @author sparrow
	 *
	 */
	public static enum DeviceId {
		SYS("SYS", "5da2961e618e75d64d06df0b251cad78f0beeae7", 360, 0);

		private String id;
		private String deviceId;
		private int channel;
		private int state;
		private FishWebsocketWrapper wrapper;

		public FishWebsocketWrapper getWrapper() {
			return wrapper;
		}

		public void setWrapper(FishWebsocketWrapper wrapper) {
			this.wrapper = wrapper;
		}

		private DeviceId(String id, String deviceId, int channel, int state) {
			this.id = id;
			this.deviceId = deviceId;
			this.channel = channel;
			this.state = state;
		}

		public String toString() {
			return id + " |" + deviceId + " " + channel + " " + state;
		}

	}

	private static final ConcurrentHashMap<String, DeviceId> idMaps = new ConcurrentHashMap<String, WebsocketCommand.DeviceId>();
	private   WebSocketClient client;
	@CliCommand(value = "server", help = " switch servers ")
	public String server(@CliOption(key = { "uri" }, mandatory = true, help = " server uri") ServerTypes types) {

		switch (types.type) {
		case 0:
			destUri = "ws://localhost:8080/game-websockets/api";
			break;
		case 1:
			destUri = "ws://www.toyo.cool:8080/game-websocket/api";
			break;
		case 2:
			destUri = "ws://websocket.toyo.cool:8080/game-websocket/api";
			break;
		case 3:
			destUri = "ws://m.toyo.cool:8090/game-websocket/api";

		default:
			break;
		}
		

		executor.setMaximumPoolSize(1000);
		executor.setThreadFactory(new DefaultTaskThreadFactory());
		
		
		return " Switch :" + destUri;
	}

	
	
	Map<Long,MockMobile> mobiles=GameUtil.getMap();
	
	
	@CliCommand(value = "sys connect", help = " ")
	public String sysconnect() {
		
		
		DeviceId sys=DeviceId.SYS;
		//User account = users.get(uid);
		for(int i=0;i<1;i++){
			client=new WebSocketClient();
			//client.setExecutor(threadPoolExecutor);
			client.setConnectTimeout(10000);
			client.setAsyncWriteTimeout(5000);
			try {
				client.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		final MockMobile mobile=new MockMobile(21l, sys.deviceId, sys.channel, destUri, client);
		mobile.enableMailSender=false;
		mobiles.put(mobile.getClientId(), mobile);
		
			executor.execute(mobile);
			
			
		
		}
		return  "connect:"+(21+19920606);
	}
	
	boolean enableSender;
	boolean enalbeReceiver;
	boolean enableQueue;
	@CliCommand(value = "connect", help = " ")
	public String connect(@CliOption(key = { "userId" }, mandatory = true, help = " user id") Long userId) {
		
		
		final Long uid = userId;
		User account = users.get(uid);
		final MockMobile mobile=new MockMobile(account.getId(), account.getDeviceId(), account.getChannelId(), destUri, client);
		
		mobile.enableMailReceiver=enalbeReceiver;
		mobile.enableMailSender=enableSender;
		mobile.enableQueue=enableQueue;
		
		mobiles.put(mobile.getClientId(), mobile);
		executor.execute(mobile);
		
		
		return  "connect:"+userId;
	}
	
	
	@CliCommand(value = "disconnect", help = "disconnect connection ws ")
	public String disconn(@CliOption(key = { "userId" }, mandatory = true, help = " user id") Long userId) {
		// final DeviceId id = deviceId;
		MockMobile mobile=getMock(userId);

		mobile.close();
		return "disconnect " + (userId);
	}
	
	

	@CliCommand(value = "mobiles", help = "disconnect connection ws ")
	public String mobile() {
		// final DeviceId id = deviceId;
		int size=MockMobile.sizeOfOnlineMock();
		StringBuffer buffer=new StringBuffer();
		buffer.append("mobiles:").append(size).append("\n");
		for(MockMobile mobile:MockMobile.values()){
			buffer.append(mobile.toString()).append("\n");
			
		}
		
		return buffer.toString();
	}
	
	

	@CliCommand(value = "mobiles clear", help = "disconnect connection ws ")
	public String mobileClear() {
		// final DeviceId id = deviceId;
		int size=MockMobile.sizeOfOnlineMock();
		StringBuffer buffer=new StringBuffer();
		buffer.append("mobiles:").append(size).append("\n");
		for(MockMobile mobile:MockMobile.values()){
			//buffer.append(mobile.toString()).append("\n");
			if(mobile.getClientId()==19920267)
				continue;
			mobile.close();
		}
		
		return buffer.toString();
	}
	
	
	
	private MockMobile getMock(Long userId){
		return mobiles.get(userId);
	}
	MobileSender mobileSender=new MobileSender();
	
	
	@CliCommand(value = "list", help = " ")
	public String getUserList(
			@CliOption(key = { "userId" }, mandatory = true, help = " user id") Long userId,
			@CliOption(key = { "start" }, mandatory = true, help = " user id") Long start,
			@CliOption(key = { "end" }, mandatory = true, help = " user id") Long end
			) {
		
		
		//final Long uid = userId;
		MockMobile  mobile=mobiles.get(userId);
	
		
		//mobile.enableMailSender=enable;
		//User account = users.get(uid);
		//final MockMobile mobile=new MockMobile(account.getId(), account.getDeviceId(), account.getChannelId(), destUri, client);
		try {
			List<User> u=mobile.getUserByUserId(start, end);
			logger.info("users:"+u.size());
			for (User uu : u) {
				final Long oid = GameUtil.s2c(uu.getId());
				users.put(oid, uu);
				//connect(oid);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return  "connect:"+userId;
	}
	
	
	@CliCommand(value = "make friends", help = " ")
	public String sender(
			@CliOption(key = { "userId" }, mandatory = true, help = " user id") Long userId,
			@CliOption(key = { "start" }, mandatory = true, help = " start id") Long start,
			@CliOption(key = { "end" }, mandatory = true, help = " end id") Long end,
			
			@CliOption(key = { "target" }, mandatory = true, help = " target id") Long target
			) {
		
		
		//final Long uid = userId;
		MockMobile  mobile=mobiles.get(userId);
		enableQueue=true;
		enableSender=true;
		enalbeReceiver=false;
		
		MobileSender.setTargetId(target);
		//mobile.enableMailSender=enable;
		//User account = users.get(uid);
		//final MockMobile mobile=new MockMobile(account.getId(), account.getDeviceId(), account.getChannelId(), destUri, client);
		try {
			List<User> u=mobile.getUserByUserId(start, end);
			logger.info("users:"+u.size());
			for (User uu : u) {
				final Long oid = GameUtil.s2c(uu.getId());
				users.put(oid, uu);
				if(enableQueue){
					executor.execute(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							try {
								connect(oid);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return  "sending by:"+19920627;
	}
	
	
	@CliCommand(value = "receiver", help = " ")
	public String receiver(
			@CliOption(key = { "userId" }, mandatory = true, help = " user id") Long userId,
			@CliOption(key = { "start" }, mandatory = true, help = " user id") Long start,
			@CliOption(key = { "end" }, mandatory = true, help = " user id") Long end
			) {
		
		
		//final Long uid = userId;
		MockMobile  mobile=mobiles.get(userId);
		enableQueue=true;
		enableSender=false;
		enalbeReceiver=true;
		try {
			List<User> u=mobile.getUserByUserId(start, end);
			logger.info("users:"+u.size());
			for (User uu : u) {
				final Long oid = GameUtil.s2c(uu.getId());
				users.put(oid, uu);
				if(enableQueue){
					executor.execute(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							try {
								connect(oid);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return  "receiver by :"+19920627;
	}
	
	
	//
	//
	// Friends Functions
	//
	//
	
	@CliCommand(value = "friends searchFriends", help = " PFS0001(Search UserId) ")
	public String searchUserId(@CliOption(key = { "userId" }, mandatory = true, help = " device id") Long userId,
			@CliOption(key = { "searchUserId" }, mandatory = true, help = "user id") Long searchUserId) {

		
		MockMobile mobile=getMock(userId);
		
		String ret=null;
		try {
			UserAccountFriends friends=mobile.getFriendsByUserId(searchUserId);
			ret=friends.getId()+"|"+friends.getNickName();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;

	}

	

	@CliCommand(value = "friends sendFriendMail", help = " PFS0002(Send Friend Mail) ")
	public String sendFriendsMail(@CliOption(key = { "userId" }, mandatory = true, help = " device id") Long userId,
			@CliOption(key = { "friendsId" }, mandatory = true, help = "friends id") Long friendsId,
			@CliOption(key = { "mailTypes" }, mandatory = true, help = "user id") FriendMailTypes actionType) {

		MockMobile mobile=getMock(userId);
		
		
		Integer ret=Integer.valueOf(0);
		try {
			ret = mobile.sendFriendMail(friendsId, actionType);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ret==1?"Succeed":"Failed";

	}

	@CliCommand(value = "friends getFriends", help = " PFS0003(Get Friend Seq) ")
	public String getFriends(@CliOption(key = { "userId" }, mandatory = true, help = " user id") Long userId) {

		MockMobile mobile=getMock(userId);
		
		List<FriendsUser> friendsUser=null;
		try {
			friendsUser = mobile.getFriendsUser(0, 200);
			for(FriendsUser u:friendsUser){
				logger.info(u.getUserId()+" - "+u.getNickName());
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "getFriends:"+(friendsUser==null?0:friendsUser.size());

	}

	@CliCommand(value = "friends getRecomment", help = " PFS0004(Get Recomment Friend Seq) ")
	public String getRecommentFriends(
			@CliOption(key = { "userId" }, mandatory = true, help = " user id") Long userId) {

		
		MockMobile mobile=getMock(userId);
		
		List<FriendsUser> friendsUser=null;
		try {
			friendsUser = mobile.getRecomment();
			for(FriendsUser u:friendsUser){
				logger.info(u.getUserId()+" - "+u.getNickName());
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "getFriends:"+(friendsUser==null?0:friendsUser.size());

	}

	@CliCommand(value = "friends removeFriends", help = " PFS0005(Remove  Friend IdSeq) ")
	public String removeFriends(@CliOption(key = { "userId" }, mandatory = true, help = " user id") Long userId,
			@CliOption(key = { "friendIds" }, mandatory = true, help = "friend IdsSeq") String friendsIds) {

		
		List<Long> idSeq = getIdSeqs(friendsIds);
		
		MockMobile mobile=getMock(userId);
		
		Integer ret=Integer.valueOf(0);
		try {
			ret = mobile.removeFriends(idSeq);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return friendsIds+( ret==1?"Removed":"Failed");
	}

	@CliCommand(value = "friends getRestriction", help = " PFS0006(Get  Friend Restriction) ")
	public String getRestriction(
			@CliOption(key = { "userId" }, mandatory = true, help = " user id") Long userId) {
		
		MockMobile mobile=getMock(userId);
		
		String ret=null;
		try {
			PFS0006Response response=mobile.getRestriction();
			ret="friends:"+response.getCurrentFriends()+"| friendsRequest:"+response.getCurrentFriendsRequest()+"| getVit:"+response.getCurrentGetVit()+"| giveVit:"  +response.getCurrentGiveVit();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;

	}

	
	
	
	
	
	//
	// Mail
	//
	//
	
	

	@CliCommand(value = "mail update", help = " PMS0003(update mail state) ")
	public String updateMailState(
			@CliOption(key = { "userId" }, mandatory = false, help = " user id") Long userId,
			@CliOption(key = { "mailId" }, mandatory = false, help = "mailId ") Long mailId,
			@CliOption(key = { "state" }, mandatory = false, help = "mail state (0:unread,1:read) ") MailStates state) {

		MockMobile mobile=getMock(userId);
		
		Integer ret=Integer.valueOf(0);
		try {
			ret = mobile.updateMail(mailId, state);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (ret==1?"Done ":"Failed");
	}
	
	AtomicInteger Seq=new AtomicInteger(0);

	@CliCommand(value = "mail getAllMail", help = " PMS0004(Get All Mail Seq) ")
	public String getMailSeq(@CliOption(key = { "userId" }, mandatory = true, help = " user id") Long userId,
			@CliOption(key = {
					"category" }, mandatory = true, help = "mail category:\n\t0:normal\n\t1:vit\n\t2,friend request\n\t3:aready friends\n\t4:client mail,5:attachment mail ") MailCategory category,
			@CliOption(key = { "state" }, mandatory = true, help = "mail state (0:unread,1:read) ") MailStates state

	) {
		MockMobile mobile=getMock(userId);
		
		List<Long> idsSeq=null;
		String ret=null;
		try {
			idsSeq = mobile.getMailId(category, state, 0, 20);
			ret=Arrays.toString(idsSeq.toArray(new Long[]{}));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
		
	}

	@CliCommand(value = "mail getmail", help = " PMS0005(get Mail Seq) ")
	public String getMail(@CliOption(key = { "userId" }, mandatory = true, help = " device id") Long userId,
			@CliOption(key = { "idseq" }, mandatory = true, help = "device id seq sperate with ,") String idSeq) {

		
		MockMobile mobile=getMock(userId);
		List<Long> mailIdSeq = getIdSeqs(idSeq);
		
		String ret=null;
		try {
			List<MailObj> mailObjs=mobile.getMailByIdSeq(mailIdSeq);
			
			if(mailObjs.size()>0){
				for(MailObj obj:mailObjs){
					ret+=(obj.getMailId()+"|"+obj.getTitle());
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ret;

	}
	
	@CliCommand(value = "mail remove", help = " PMS0007(update user level avtar nickname order ")
	public String getOrder(@CliOption(key = { "userId" }, mandatory = false, help = " device id") Long userId,
			@CliOption(key = { "idSeq" }, mandatory = false, help = " mail id seq") String idSeqs,

			@CliOption(key = { "state" }, mandatory = false, help = " user level") MailStates state) {
		
		MockMobile mobile=getMock(userId);
		List<Long> idSeq = getIdSeqs(idSeqs);
		
		
		try {
			List<Long> failed=mobile.updateMailIdSeq(idSeq, state);
			if(failed!=null)
			logger.info(Arrays.toString(failed.toArray(new Long[]{})));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idSeqs+" removed";
		
		
	}

	
	//
	//  MailSender.
	//
	//

	
	
	///
	///
	/// suffxied
	///

	//
	// Mail
	//
	//
	
	//
	// PU0012
	//
	@CliCommand(value = "user clear", help = " PU0012() ")
	public String systemSwitcherSet(
			@CliOption(key = { "user" }, mandatory = false, help = " user id") Long userId

	) {
		MockMobile mobile=getMock(userId);
		Integer ret=Integer.valueOf(0);
		try {
			ret=mobile.userClear();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret==1?"Done":"Failed";
	}

	

	// create the scheduled mail and system mail.

	public static enum MailAttachmentTypes {

		Item(1), Command(2), Kv(3);

		private int type;

		private MailAttachmentTypes(int type) {
			this.type = type;
		}

	}

	private static class MailAttachmentObjectSeq extends ObjectSeq<String, MailAttachment> {

	}

	MailAttachmentObjectSeq mailAttachmentObjectSeq = new MailAttachmentObjectSeq();

	@CliCommand(value = "attachment create", help = " create attachment ")
	public String createMailAttachment(

			@CliOption(key = { "id" }, mandatory = true, help = " id") String id,
			@CliOption(key = { "type" }, mandatory = true, help = " type") MailAttachmentTypes type,
			@CliOption(key = { "value" }, mandatory = true, help = " value") String value) {

		MailAttachment o = G.o(MailAttachment.class);
		o.setId(id);
		o.setType(type.type);
		o.setValue(value);

		return mailAttachmentObjectSeq.create(id, o);

	}

	@CliCommand(value = "attachment show", help = " show attachment ")
	public String showMailAttachments() {

		return mailAttachmentObjectSeq.show();
	}

	@CliCommand(value = "attachment delete", help = " delete attachment ")
	public String deleteMailAttachments(@CliOption(key = { "id" }, mandatory = true, help = " id") String id) {
		return mailAttachmentObjectSeq.delete(id);
	}

	@CliCommand(value = "attachment clear", help = " delete attachment ")
	public String clearMailAttachments() {

		return mailAttachmentObjectSeq.clear();
	}

	MailTriggerExpresion trigger;// =new MailTriggerExpresion();

	private static enum MailTriggerUserLevelTypes {

		LE(1), BW(2), GE(3);
		private int type;

		private MailTriggerUserLevelTypes(int type) {
			this.type = type;
		}

	}

	@CliCommand(value = "mail trigger", help = " delete attachment ")
	public String createTrigger(@CliOption(key = { "minLevel" }, mandatory = false, help = " minLevel") int minLevel,
			@CliOption(key = { "maxLevel" }, mandatory = false, help = " maxLevel") int maxLevel,
			@CliOption(key = {
					"userLevelTypes" }, mandatory = false, help = " userLevelTypes") MailTriggerUserLevelTypes userLevelTypes,
			@CliOption(key = { "apkVersion" }, mandatory = false, help = " apkVersion") boolean apkVersion,
			@CliOption(key = { "loginAfterNDays" }, mandatory = false, help = " loginAfterNDays") int loginAfterNDays,
			@CliOption(key = { "userIdSeq" }, mandatory = false, help = " userIdSeq") String userIdSeq) {
		trigger = G.o(MailTriggerExpresion.class);
		MailLevel mailLevel = G.o(MailLevel.class);
		mailLevel.setMax(maxLevel);
		mailLevel.setMin(minLevel);
		mailLevel.setRelation(userLevelTypes.type);
		trigger.setUserLevel(mailLevel);
		trigger.setApkVersion(apkVersion);
		trigger.setLoginAfterNDays(loginAfterNDays);

		if (userIdSeq != null) {
			List<Long> userIds = getIdSeqs(userIdSeq);
			trigger.setUserIds(userIds);
		}

		return GameContextGlobals.getJsonConvertor().format(trigger);
	}

	@CliCommand(value = "mail create", help = " PMS0006( If need the trigger and attachments ,please create it first.) ")
	public String crateMail(@CliOption(key = { "userId" }, mandatory = true, help = " ser id") Long userId,
			@CliOption(key = { "target" }, mandatory = true, help = " target id") Long targetId,
			@CliOption(key = { "title" }, mandatory = true, help = " device id") String title,
			@CliOption(key = { "content" }, mandatory = true, help = " device id") String content,
			@CliOption(key = { "category" }, mandatory = true, help = " device id") MailCategory category,
			@CliOption(key = { "activeTime" }, mandatory = true, help = " device id") String activeDateTime,
			@CliOption(key = { "activeMinutes" }, mandatory = true, help = " device id") int activeMinutes

	) {

		final PMS0006Request o = G.o(PMS0006Request.class);
		
		o.setUserId(GameUtil.c2s(targetId));
		
		o.setActiveDateTime(activeDateTime);
		o.setActiveHours(activeMinutes);
		MailAttachments attachments = G.o(MailAttachments.class);
		List<MailAttachment> mas = GameUtil.getList();
		mas.addAll(mailAttachmentObjectSeq.getAll());
		attachments.setAttachments(mas);
		o.setAttachments(attachments);
		o.setTrigger(trigger);
		o.setCategory(category.getState());
	
	

		o.setTitle(title);
		o.setContent(content);

		return baseCommand(userId, "Create Mail", "PMS0006", "SMP0006", o);

	}

	//
	// PFS
	//
	//
	//


	//
	//
	// FriendZone. PFU
	//
	//
	public static enum UserFarmActionTypes {

		JoinFarm(1), LeaveFarm(2);

		private int state;

		private UserFarmActionTypes(int state) {
			this.state = state;
		}

	}

	@CliCommand(value = "fish farm", help = " PFU0001(Join or Leave farm) ")
	public String joinOrLeaveFarm(@CliOption(key = { "deviceId" }, mandatory = true, help = " device id") Long deviceId,
			@CliOption(key = { "farmId" }, mandatory = true, help = " device id") String farmId, @CliOption(key = {
					"actionTypes" }, mandatory = true, help = "farm action 1:join,2:leave") UserFarmActionTypes actionTypes) {

		final PFU0001Request o = G.o(PFU0001Request.class);
		o.setFarmId(farmId);
		o.setUserAction(actionTypes.state);

		return baseCommand(deviceId, "Join or Leave farm", "PFU0001", "UFP0001", o);

	}

	/**
	 * 
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	public static enum ModuleTypes {

		Backpack(1), Achievement(2), Task(3), GoldFinger(4), Fish(5), Aqua(6), Cdk(7), Chest(8), LevelUp(9), Pack(
				10), Signature(11), Payment(12);
		private int type;

		private ModuleTypes(int type) {
			this.type = type;
		}

	}

	public static enum ActionTypes {
		LevelUp(1),

		Buy(2), Sell(3), Fished(4), Refresh(5), ClickGold(6), Get(7), Reward(8), Repair(9);

		private int type;

		private ActionTypes(int type) {

			this.type = type;
		}

	}

	public static enum ParamTypes {
		Drug(1), Rod(2), Line(3), Lure(4), Wheel(5), MainTask(6), TimedTask(7), EveryDayTask(8), Fish(9), Garbage(
				10), ExchageCode(11), OneTime(12), TenTimes(13), TuhaoPack(14), FailurePack(15);

		private int type;

		private ParamTypes(int type) {
			this.type = type;
		}

	}

	public static enum CategoryTypes {
		Gold(1), Vit(2), Diamond(3);

		private int type;

		private CategoryTypes(int type) {
			this.type = type;
		}

	}

	private AtomicLong seq = new AtomicLong(0);

	private Long getSeqId() {
		long id = System.currentTimeMillis() + seq.getAndIncrement();
		return Long.valueOf(id);
	}

	private static class CurrencyObjectSeq extends ObjectSeq<Long, CurrencyRecord> {

	}

	// private Map<Long,CurrencyRecord> currentRecords=GameUtil.getMap();
	CurrencyObjectSeq currencyObjectSeq = new CurrencyObjectSeq();

	@CliCommand(value = "currency delete", help = " delete currency record ")
	public String deleteCurrency(@CliOption(key = { "id" }, mandatory = true, help = "client id") Long id) {

		return currencyObjectSeq.delete(id);
	}

	@CliCommand(value = "currency clear", help = " clear currency record ")
	public String clearCurrency() {
		return currencyObjectSeq.clear();
	}

	@CliCommand(value = "currency show", help = " show currency record ")
	public String showCurrency() {
		return currencyObjectSeq.show();
	}

	@CliCommand(value = "currency create", help = " create currency record ")
	public String createCurrencyRecord(
			@CliOption(key = { "module" }, mandatory = true, help = " module") ModuleTypes module,
			@CliOption(key = { "action" }, mandatory = true, help = " action") ActionTypes action,
			@CliOption(key = { "param" }, mandatory = true, help = " param") ParamTypes param,
			@CliOption(key = { "amount" }, mandatory = true, help = " amount") int amount,
			@CliOption(key = { "category" }, mandatory = true, help = " category") CategoryTypes category) {

		CurrencyRecord o = G.o(CurrencyRecord.class);
		o.setClientId(getSeqId());
		o.setOrderTime(CronUtil.getFormatedDate(new Date()));
		o.setAction(action.type);
		o.setAmount(amount);
		o.setCategory(category.type);
		o.setModule(module.type);
		o.setParam(param.type);

		return currencyObjectSeq.create(o.getClientId(), o);

	}

	@CliCommand(value = "fish currency", help = " PFU0003(add currency list) ")
	public String fishCurrency(@CliOption(key = { "deviceId" }, mandatory = true, help = " device id") Long deviceId) {

		final PFU0003Request o = G.o(PFU0003Request.class);

		List<CurrencyRecord> records = GameUtil.getList();
		records.addAll(currencyObjectSeq.getAll());

		o.setRecords(records);

		return baseCommand(deviceId, "Add Currency Records ", "PFU0003", "UFP0003", o);

	}

	// private Map<Long,VitRecord> vitRecords=GameUtil.getMap();

	private static class VitRecordObjectSeq extends ObjectSeq<Long, VitRecord> {
	}

	VitRecordObjectSeq vitRecordObjectSeq = new VitRecordObjectSeq();

	@CliCommand(value = "vit create", help = " create currency record ")
	public String createVitRecord(@CliOption(key = { "vit" }, mandatory = true, help = " module") int vit,
			@CliOption(key = { "online" }, mandatory = true, help = " action") int online

	) {

		VitRecord o = G.o(VitRecord.class);
		o.setClientId(getSeqId());
		o.setOnline(online);
		o.setVit(vit);
		o.setOrderTime(CronUtil.getFormatedDate(new Date()));

		return vitRecordObjectSeq.create(o.getClientId(), o);
	}

	@CliCommand(value = "vit delete", help = " delete vit record ")
	public String createCurrencyRecord(@CliOption(key = { "id" }, mandatory = true, help = "client id") Long id) {

		return vitRecordObjectSeq.delete(id);
	}

	@CliCommand(value = "vit clear", help = " clear vit record ")
	public String clearVitRecords() {

		return vitRecordObjectSeq.clear();
	}

	@CliCommand(value = "vit show", help = " show vit record ")
	public String showVit() {
		return vitRecordObjectSeq.show();
	}

	@CliCommand(value = "fish vit", help = " PFU0004(add currency list) ")
	public String fishVit(@CliOption(key = { "deviceId" }, mandatory = true, help = " device id") Long deviceId) {

		final PFU0004Request o = G.o(PFU0004Request.class);

		List<VitRecord> records = GameUtil.getList();
		records.addAll(vitRecordObjectSeq.getAll());

		o.setRecords(records);

		return baseCommand(deviceId, "Add Vit Records ", "PFU0004", "UFP0004", o);

	}

	@CliCommand(value = "fish data", help = " PFU0005(fish data list) ")
	public String fishData(@CliOption(key = { "deviceId" }, mandatory = true, help = " device id") Long deviceId,
			@CliOption(key = { "fishId" }, mandatory = true, help = "") String fishId,
			@CliOption(key = { "level" }, mandatory = true, help = "", specifiedDefaultValue = "0") int level,
			@CliOption(key = { "rodId" }, mandatory = false, help = "", specifiedDefaultValue = "") String rodId,
			@CliOption(key = { "rodProps" }, mandatory = false, help = "", specifiedDefaultValue = "") String rodProps,
			@CliOption(key = { "wheelId" }, mandatory = false, help = "", specifiedDefaultValue = "") String wheelId,
			@CliOption(key = {
					"wheelProps" }, mandatory = false, help = "", specifiedDefaultValue = "") String wheelProps,
			@CliOption(key = { "lineId" }, mandatory = false, help = "", specifiedDefaultValue = "") String lineId,
			@CliOption(key = { "lureId" }, mandatory = false, help = "", specifiedDefaultValue = "") String lureId,
			@CliOption(key = { "drugId1" }, mandatory = false, help = "", specifiedDefaultValue = "") String drugId1,
			@CliOption(key = { "drugId2" }, mandatory = false, help = "", specifiedDefaultValue = "") String drugId2,
			@CliOption(key = { "drugId3" }, mandatory = false, help = "", specifiedDefaultValue = "") String drugId3,
			@CliOption(key = {
					"bigFishAddition" }, mandatory = false, help = "", specifiedDefaultValue = "") String bigFishAddition,
			@CliOption(key = { "fishWeight" }, mandatory = true, help = "") float fishWeight,
			@CliOption(key = { "fishRank" }, mandatory = true, help = "") int fishRank,
			@CliOption(key = { "fishRare" }, mandatory = true, help = "") int fishRare,
			@CliOption(key = {
					"tensionTriggers" }, mandatory = false, help = "", specifiedDefaultValue = "0") int tensionTriggers,
			@CliOption(key = {
					"tensionUsed" }, mandatory = false, help = "", specifiedDefaultValue = "0") int tensionUsed,
			@CliOption(key = {
					"dragTriggers" }, mandatory = false, help = "", specifiedDefaultValue = "0") int dragTriggers,
			@CliOption(key = { "dragUsed" }, mandatory = false, help = "", specifiedDefaultValue = "0") int dragUsed,
			@CliOption(key = {
					"fishDuration" }, mandatory = true, help = "", specifiedDefaultValue = "0") int fishDuration,
			@CliOption(key = { "succeed" }, mandatory = true, help = "") int succeed

	) {

		final PFU0005Request o = G.o(PFU0005Request.class);
		o.setBigFishAddition(bigFishAddition);
		o.setDragTriggers(dragTriggers);
		o.setDragUsed(dragUsed);
		o.setDrugId1(drugId1);
		o.setDrugId2(drugId2);
		o.setDrugId3(drugId3);
		o.setFishDuration(fishDuration);
		o.setFishId(fishId);
		o.setFishRank(fishRank);
		o.setFishRare(fishRare);
		o.setFishWeight(fishWeight);
		o.setLevel(level);
		o.setLineId(lineId);
		o.setLureId(lureId);
		o.setRodId(rodId);
		o.setRodProps(rodProps);
		o.setSucceed(succeed);
		o.setTensionTriggers(tensionTriggers);
		o.setTensionUsed(tensionTriggers);
		o.setWheelId(wheelId);
		o.setWheelProps(wheelProps);

		return baseCommand(deviceId, "Add fish data ", "PFU0005", "UFP0005", o);

	}

	//
	// Protocol Rank System.
	//
	//

	public static enum FishRankTypes {
		World(0), Friends(1);
		private int type;

		private FishRankTypes(int type) {
			this.type = type;
		}

	}

	@CliCommand(value = "fish rank", help = " PRS0001(fish rank  world list) ")
	public String fishData(@CliOption(key = { "deviceId" }, mandatory = true, help = " device id") Long deviceId,
			@CliOption(key = { "fishId" }, mandatory = true, help = "") String fishId) {

		final PRS0001Request o = G.o(PRS0001Request.class);

		o.setFishId(fishId);

		return baseCommand(deviceId, "Fish Rank List ", "PRS0001", "SRP0001", o);
	}

	@CliCommand(value = "friend rank", help = " PFS0007(fish rank  friend list) ")
	public String fishRankFriends(@CliOption(key = { "deviceId" }, mandatory = true, help = " device id") Long deviceId,
			@CliOption(key = { "fishId" }, mandatory = true, help = "") String fishId) {

		final PRS0002Request o = G.o(PRS0002Request.class);

		o.setFishId(fishId);

		return baseCommand(deviceId, "Fish Rank List ", "PFS0007", "SFP0007", o);
	}

	@CliCommand(value = "payment order", help = " PP0001|PP0006(payment order ")
	public String getOrder(@CliOption(key = { "deviceId" }, mandatory = true, help = " device id") DeviceId deviceId) {
		final DeviceId id = deviceId;
		executor.execute(new Runnable() {

			public void run() {

				try {
					id.getWrapper().sendPaymentOrder();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		return " requet order: " + id.toString();
	}

	@CliCommand(value = "user set", help = " PU0011(update user level avtar nickname order ")
	public String getOrder(@CliOption(key = { "deviceId" }, mandatory = true, help = " device id") Long deviceId,
			@CliOption(key = { "avatar" }, mandatory = true, help = " avatar") String avatar,
			@CliOption(key = { "nick" }, mandatory = true, help = " nick name") String nickName,
			@CliOption(key = { "level" }, mandatory = true, help = " user level") Integer level) {
		final PU0011Request o = G.o(PU0011Request.class);
		o.setAvatar(avatar);
		o.setLevel(level);
		o.setNickName(nickName);

		return baseCommand(deviceId, "Update User information", "PU0011", "UP0011", o);
	}

	
	private static int sequence = 0;

	@CliCommand(value = "on", help = " PS0003() ")
	public String systemSwitcher(
			@CliOption(key = { "deviceId" }, mandatory = false, help = " device id") Long deviceId) {
		final PS0003Requet o = G.o(PS0003Requet.class);
		o.setSeq(sequence++);

		return baseCommand(deviceId, "system switcher", "PS0003", "SP0003", o);
	}

	@CliCommand(value = "set", help = " PS0004() ")
	public String systemSwitcherSet(
			@CliOption(key = { "deviceId" }, mandatory = false, help = " device id") Long deviceId,
			@CliOption(key = { "protocol" }, mandatory = false, help = " protocol") ProtocolModuleTypes type,
			@CliOption(key = { "state" }, mandatory = false, help = " state") Boolean state

	) {
		final PS0004Requet o = G.o(PS0004Requet.class);
		o.setType(type);
		o.setState(state);
		o.setSeq(sequence++);

		return baseCommand(deviceId, "system switcher set", "PS0004", "SP0004", o);
	}

	

	@CliCommand(value = "time", help = " PU0013() ")
	public String systemTime(@CliOption(key = { "deviceId" }, mandatory = false, help = " device id") Long deviceId

	) {
		final PU0013Request o = G.o(PU0013Request.class);

		return baseCommand(deviceId, "system time", "PU0013", "UP0013", o);
	}

	//
	// this part will be executed as scripts.
	//

	static Map<Long, User> users = GameUtil.getMap();
	ScriptsBuilder builder=new ScriptsBuilder();
	@CliCommand(value = "userlist", help = " PU0014() ")
	public String userlist(@CliOption(key = { "deviceId" }, mandatory = false, help = " device id") DeviceId deviceId,
			@CliOption(key = { "start" }, mandatory = false, help = " start user id") Long start) {
		Long end=start+500;
		final PU0014Request o = G.o(PU0014Request.class);
		o.setStart(start);
		o.setEnd(end);

		final DeviceId id = deviceId;
		
		final String fileName=String.format("%d-%d.ss", start,end);
		executor.execute(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {
					id.getWrapper().send("PU0014", "UP0014", o, new IWebsocketMessageHandle() {

						public void handle(String content) {
							// TODO Auto-generated method stub
							//logger.info(content);
							// load the object into the object map
							File f=new File(fileName);
							if (content != null) {
								PU0014Response resp = G.parse(content, PU0014Response.class);
								StringBuffer buf=new StringBuffer();
								for (User u : resp.getUsers()) {
									Long oid = GameUtil.s2c(u.getId());
									users.put(oid, u);
									// logger.info(" users:"+users.size()+"
									// put:"+oid);
								//	connect(oid);
									buf.append("connect --userId ").append(oid).append("\n");
								}
								
								try {
									
									FileUtils.writeStringToFile(f, buf.toString());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//start=start+users.size();
								logger.info("\n users:" + users.size()+" scripts:"+f.getAbsolutePath());
								builder.reset();
							}
							// resp.getUsers();
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		return "user list";
	}

	Map<Long, FishWebsocketWrapper> userWebsocketMap = GameUtil.getMap();

	

	@CliCommand(value = "save", help = " ")
	public void save(
			@CliOption(key = { "name" }, mandatory = true, help = " save scripts name") String name
			){
		builder.save(name);
		builder.reset();
	}
	

//	static Map<Long, User> connectedUsers = GameUtil.getMap();

	private void afterConnected(Long userId) {
		// execute the getRecomment method.u
		
	}
	
	
	

	@CliCommand(value = "connections", help = " show connections  ")
	public String connections() {
		StringBuffer buf = new StringBuffer();
		buf.append("connections:"+userWebsocketMap.size()+"\n");
		List<Long> ids=GameUtil.getList();
		for (Iterator<Long> it= userWebsocketMap.keySet().iterator();it.hasNext();) {
			Long userId=it.next();
			ids.add(userId);
			
		}
		
		Collections.sort(ids);
		
		for(int i=0;i<ids.size();i++){
			Long userId=ids.get(i);
			User id=users.get(userId);
			boolean online=false;
			FishWebsocketWrapper wrapper=userWebsocketMap.get(userId);
			if(wrapper!=null){
				online=wrapper.isOnline();
			}
			String msg = String.format("%60s|%10d|%10d|%10d|%10s", id.getDeviceId(), id.getChannelId(), GameUtil.s2c(id.getId()),id.getId(),online?"online":"");
			buf.append(msg).append("\n");
		}

		return buf.toString();
	}

	@CliCommand(value = "users", help = " show connections  ")
	public String users() {
		StringBuffer buf = new StringBuffer();
		buf.append("users:"+users.size()+"\n");
		List<User> u=new LinkedList<User>(users.values());
		Collections.sort(u, new Comparator<User>() {

			public int compare(User o1, User o2) {
				// TODO Auto-generated method stub
				return (int)(o1.getId()-o2.getId());
			}
		});
		
		for (User id : u) {
			String msg = String.format("%60s|%10d|%10d|%10d", id.getDeviceId(), id.getChannelId(), GameUtil.s2c(id.getId()),id.getId());
			buf.append(msg).append("\n");
		}

		return buf.toString();
	}



}
