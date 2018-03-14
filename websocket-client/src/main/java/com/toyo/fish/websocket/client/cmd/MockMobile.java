package com.toyo.fish.websocket.client.cmd;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.sky.game.context.executor.UnorderedThreadPoolExecutor;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.protocol.beans.PMS0000Beans;
import com.toyo.fish.data.wrapper.domain.UserAccountFriends;
import com.toyo.fish.protocol.beans.PFS0000Beans;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0001Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0001Response;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0002Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0002Response;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0003Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0003Response;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0004Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0004Response;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0005Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0005Response;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0006Request;
import com.toyo.fish.protocol.beans.PFS0000Beans.PFS0006Response;
import com.toyo.fish.protocol.beans.PMS0000Beans.MailObj;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0002Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0003Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0003Response;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0004Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0004Response;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0005Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0005Response;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0007Request;
import com.toyo.fish.protocol.beans.PMS0000Beans.PMS0007Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0012Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0012Response;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0014Request;
import com.toyo.fish.protocol.beans.PU0000Beans.PU0014Response;
import com.toyo.fish.protocol.beans.PU0000Beans.User;
import com.toyo.fish.protocol.service.domain.FriendsUser;
import com.toyo.fish.websocket.client.FishWebsocketWrapper;
import com.toyo.fish.websocket.client.IOnResponse;

import com.toyo.fish.websocket.client.FishWebsocketWrapper.IOnClosed;

/**
 * This MockMobile will act like a mobile phone.
 * 
 * 
 * @author sparrow
 *
 */
public class MockMobile implements Runnable, IOnResponse, IOnClosed {
	private static final Log logger = LogFactory.getLog(MockMobile.class);
	
	private static final ConcurrentMap<Long, MockMobile>  onlineMockMobiles=new ConcurrentHashMap<Long, MockMobile>(1000);
	
	private Long id;
	private Long clientId;
	private String deviceId;
	private int channel;
	private int state;
	private String destUri;
	private WebSocketClient client;
	private FishWebsocketWrapper wrapper;
	
	boolean enableMailSender;
	boolean enableMailReceiver;
	
	boolean enableQueue;

	public Long getClientId() {
		return clientId;
	}

	/**
	 * 
	 * @param id
	 * @param clientId
	 * @param deviceId
	 * @param channel
	 * @param destUri
	 * @param client
	 */

	public MockMobile(Long id, String deviceId, int channel, String destUri, WebSocketClient client) {
		super();
		this.id = id;
		this.clientId = id + 19920606;
		this.deviceId = deviceId;
		this.channel = channel;
		this.destUri = destUri;
		this.client = client;
		this.enableMailReceiver=false;
		this.enableMailSender=false;
		this.enableQueue=false;
		
	}

	public String toString() {
		String msg = String.format("%10d|%15d|%50s|%10d|%5d", id, clientId, deviceId, channel, state);
		return msg;
	}

	public FishWebsocketWrapper getWrapper() {
		return wrapper;
	}

	public void setWrapper(FishWebsocketWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	/**
	 * connect the websocket
	 * 
	 */
	public void run() {
		// TODO Auto-generated method stub
		try {
			wrapper = new FishWebsocketWrapper(destUri, deviceId, channel, 240);
			wrapper.setClient(this.client);
			// this.client.set
			wrapper.getSocket().setOnResponse(this);
			wrapper.getSocket().setOnClosed(this);

			if (!this.client.isStarted()) {
				try {
					this.client.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			wrapper.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * send interface,
	 * 
	 * @param transcode
	 * @param obj
	 */
	public void send(String transcode, Object obj) {
		wrapper.getSocket().send(transcode, obj);
	}

	BlockingQueue<Boolean> connectedQueue = new BlockingArrayQueue<Boolean>(1);

	public Boolean connected() throws InterruptedException {

		return connectedQueue.take();
	}

	/**
	 * 
	 * response interface.
	 * 
	 */
	public void response(Long userId, String transcode, String content) {
		// TODO Auto-generated method stub
		if (userId.longValue() != id.longValue()) {
			throw new WebSocketException(" server.user.id:" + userId + "  local.user.id:" + id + " mismatach!");
		}
		if ("UP0001".equals(transcode)) {
			state = 1;// online
			logger.info("User:" + userId + " loggined!");
			// connectedQueue.add(Boolean.valueOf(true));
			onlineMockMobiles.put(userId, this);
			// 
			if(enableQueue){
				logger.info("User:" + userId + " in send friends request queue!");
				MobileSender.put(MockMobile.this);
			}
		}
		
		if("UP0012".equals(transcode)){
			PU0012Response o=G.parse(content, PU0012Response.class);
			handlePU0014(o);
		}

		//
		// handle pu
		//
		if ("UP0014".equals(transcode)) {
			PU0014Response response = G.parse(content, PU0014Response.class);
			handlePU0014(response);
		}

		//
		// handle protocol pms.
		//

		if ("PMS0002".equals(transcode)) {
			PMS0002Request request = G.parse(content, PMS0002Request.class);
			handlePMS0002(request);
		}

		if ("SMP0003".equals(transcode)) {
			PMS0003Response response = G.parse(content, PMS0003Response.class);
			handlePMS0003(response);
		}
		if ("SMP0004".equals(transcode)) {
			PMS0004Response response = G.parse(content, PMS0004Response.class);
			handlePMS0004(response);
		}
		if ("SMP0005".equals(transcode)) {
			PMS0005Response response = G.parse(content, PMS0005Response.class);
			handlePMS0005(response);
		}
		if ("SMP0007".equals(transcode)) {
			PMS0007Response response = G.parse(content, PMS0007Response.class);
			handlePMS0007(response);
		}

		//
		// Friends
		////
		//

		if ("SFP0001".equals(transcode)) {
			PFS0001Response response = G.parse(content, PFS0001Response.class);
			handlePFS0001(response);
		}

		if ("SFP0002".equals(transcode)) {
			PFS0002Response response = G.parse(content, PFS0002Response.class);
			handlePFS0002(response);
		}

		if ("SFP0003".equals(transcode)) {
			PFS0003Response response = G.parse(content, PFS0003Response.class);
			handlePFS0003(response);
		}

		if ("SFP0004".equals(transcode)) {
			PFS0004Response response = G.parse(content, PFS0004Response.class);
			handlePFS0004(response);
		}

		if ("SFP0005".equals(transcode)) {
			PFS0005Response response = G.parse(content, PFS0005Response.class);
			handlePFS0005(response);
		}

		if ("SFP0006".equals(transcode)) {
			PFS0006Response response = G.parse(content, PFS0006Response.class);
			handlePFS0006(response);
		}

	}
	
	private BlockingQueue<Integer> userClearQ=new BlockingArrayQueue<Integer>(1);
	public Integer userClear() throws InterruptedException{
		
		PU0012Request o=G.o(PU0012Request.class);
		send("PU0012",o);
		return userClearQ.take();
	}
	

	private void handlePU0014(PU0012Response o) {
		// TODO Auto-generated method stub
		if(o!=null){
			userClearQ.add(Integer.valueOf(o.getState()));
		}else{
			userClearQ.add(Integer.valueOf(0));
		}
		
	}

	/**
	 * connection closed.
	 * 
	 */
	public void onClose(int state, String reason) {
		// TODO Auto-generated method stub
		this.state = state;
		logger.info(" Closed connection:" + id + " state:" + state + " reason:" + reason);
		onlineMockMobiles.remove(id, this);

	}

	public static enum MailCategory {
		Normal(0), Vit(1), FriendRequest(2), AlreadyFriend(3), ClientMail(4), AttachmentMail(5), SystemNormal(
				6), SystemAttachment(7);

		private int state;

		private MailCategory(int state) {
			this.state = state;
		}
		
		public int getState(){
			return state;
		}

	}

	public static enum MailStates {
		Decline(2), UnRead(0), Read(1), Deleted(2);

		private int state;

		private MailStates(int state) {
			this.state = state;
		}

	}

	/**
	 * 
	 * UserCase#1 1. find an online user ,send the friends request mail. 2.
	 * receive an friends request mail, accept the friends request. 3. check the
	 * mail category and type.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public void handlePMS0002(PMS0002Request request) {
		//
		Long mailId = request.getMailId();
		// update the mail state
		if (request.getCategory() == MailCategory.FriendRequest.state) {
			pms0003(mailId, MailStates.Read.state);
		}

	}

	private static final AtomicInteger seq = new AtomicInteger(0);

	/**
	 * update mail state
	 * 
	 * @param mailId
	 * @param state
	 */
	public void pms0003(Long mailId, int state) {

	}

	private BlockingQueue<Integer> updateMailQ = new BlockingArrayQueue<Integer>(1);

	/**
	 * update mail
	 * 
	 * @param mailId
	 * @param state
	 * @return
	 * @throws InterruptedException
	 */
	public Integer updateMail(Long mailId, MailStates state) throws InterruptedException {
		PMS0003Request o = G.o(PMS0003Request.class);
		o.setMailId(mailId);
		o.setSeq(seq.incrementAndGet());
		o.setState(state.state);
		send("PMS0003", o);
		return updateMailQ.take();
	}

	/**
	 * 
	 * @param response
	 */
	private void handlePMS0003(PMS0003Response response) {
		// response.get
		if (response != null) {
			updateMailQ.add(Integer.valueOf(response.getState()));
		} else {
			updateMailQ.add(Integer.valueOf(0));
		}
	}

	private BlockingQueue<List<MailObj>> mailObjQ = new BlockingArrayQueue<List<MailObj>>(1);

	/**
	 * get mail objects.
	 * 
	 * @param mailIdSeq
	 * @return
	 * @throws InterruptedException
	 */
	public List<MailObj> getMailByIdSeq(List<Long> mailIdSeq) throws InterruptedException {
		PMS0005Request o = G.o(PMS0005Request.class);
		o.setIds(mailIdSeq);
		o.setSeq(seq.incrementAndGet());

		send("PMS0005", o);

		return mailObjQ.take();
	}

	/**
	 * 
	 * @param response
	 */
	private void handlePMS0005(PMS0005Response response) {
		if (response != null && response.getState() == 1) {
			//
			mailObjQ.add(response.getMails());
		} else {
			List<MailObj> objs = GameUtil.getList();
			mailObjQ.add(objs);
		}
	}

	private BlockingQueue<List<Long>> updateMailIdSeqQ = new BlockingArrayQueue<List<Long>>(1);

	/**
	 * update mail batch
	 * 
	 * @param idSeq
	 * @param state
	 * @return
	 * @throws InterruptedException
	 */
	public List<Long> updateMailIdSeq(List<Long> idSeq, MailStates state) throws InterruptedException {
		PMS0007Request o = G.o(PMS0007Request.class);
		o.setMailId(idSeq);
		o.setSeq(seq.incrementAndGet());
		o.setState(state.state);

		send("PMS0007", o);

		return updateMailIdSeqQ.take();
	}

	/**
	 * 
	 * @param response
	 */
	private void handlePMS0007(PMS0007Response response) {
		if (response != null) {
			updateMailIdSeqQ.add(response.getFailedMailId());
		} else {
			List<Long> idSeq = GameUtil.getList();
			updateMailIdSeqQ.add(idSeq);

		}
	}

	private BlockingQueue<List<Long>> mailIdQ = new BlockingArrayQueue<List<Long>>(1);

	/**
	 * get mail id
	 * 
	 * @param category
	 * @param state
	 * @param offset
	 * @param length
	 * @return
	 * @throws InterruptedException
	 */
	public List<Long> getMailId(MailCategory category, MailStates state, int offset, int length) throws InterruptedException {
		PMS0004Request o = G.o(PMS0004Request.class);
		o.setCategory(category.state);
		o.setState(state.state);
		o.setOffset(offset);
		o.setLength(length);
		o.setSeq(seq.incrementAndGet());
		putCmd(o.getSeq(), o);
		send("PMS0004", o);

		return mailIdQ.take();
	}

	/**
	 * 
	 * @param response
	 */
	public void handlePMS0004(PMS0004Response response) {

		if (response != null && response.getState() == 1) {
			mailIdQ.add(response.getIds());
		} else {
			List<Long> ids = GameUtil.getList();
			mailIdQ.add(ids);
		}

	}

	//
	//
	// friends
	//

	public static enum FriendMailTypes {
		FriendRequestMail(1), FriendVitMail(2);

		private int types;

		private FriendMailTypes(int types) {
			this.types = types;
		}

	}

	//
	// Friends
	//
	//
	//

	private BlockingQueue<UserAccountFriends> getFriendsByUserIdQ = new BlockingArrayQueue<UserAccountFriends>(1);

	/**
	 * search the user id
	 * 
	 * @param userId
	 * @return
	 * @throws InterruptedException
	 */
	public UserAccountFriends getFriendsByUserId(Long userId) throws InterruptedException {
		PFS0001Request o = G.o(PFS0001Request.class);
		o.setSearchUserId(userId);
		o.setSeq(seq.incrementAndGet());

		send("PFS0001", o);

		return getFriendsByUserIdQ.take();
	}

	private void handlePFS0001(PFS0001Response response) {
		UserAccountFriends o = G.o(UserAccountFriends.class);
		if (response != null && response.getState() == 1) {

			o.setAvatar(response.getAvatar());
			o.setNickName(response.getNickName());
			o.setId(response.getId());
			o.setLevel(response.getLevel());

		}

		getFriendsByUserIdQ.add(o);
	}

	/**
	 * send friends mail.
	 * 
	 * @param friendsId
	 * @param mailTypes
	 */

	BlockingQueue<Integer> mailQ = new BlockingArrayQueue<Integer>(1);

	/**
	 * send friend mail.
	 * 
	 * @param friendsId
	 * @param mailTypes
	 * @return
	 * @throws InterruptedException
	 */
	public Integer sendFriendMail(Long friendsId, FriendMailTypes mailTypes) throws InterruptedException {
		PFS0002Request o = G.o(PFS0002Request.class);

		o.setActionType(mailTypes.types);
		o.setUserId(friendsId);
		o.setSeq(seq.incrementAndGet());
		putCmd(o.getSeq(), o);
		send("PFS0002", o);
		return mailQ.take();
	}

	/**
	 * 
	 * 
	 * @param response
	 */
	private void handlePFS0002(PFS0002Response response) {
		if (response != null)
			mailQ.add(Integer.valueOf(response.getState()));
		else
			mailQ.add(Integer.valueOf(0));
	}

	private BlockingQueue<List<FriendsUser>> getFriendsUserQ = new BlockingArrayQueue<List<FriendsUser>>(1);

	/**
	 * 
	 * @param offset
	 * @param length
	 * @return
	 * @throws InterruptedException
	 */
	public List<FriendsUser> getFriendsUser(int offset, int length) throws InterruptedException {
		PFS0003Request o = G.o(PFS0003Request.class);
		o.setLength(length);
		o.setOffset(offset);
		o.setSeq(seq.incrementAndGet());

		send("PFS0003", o);

		return getFriendsUserQ.take();
	}

	/**
	 * 
	 * @param response
	 */
	private void handlePFS0003(PFS0003Response response) {

		if (response != null && response.getState() == 1) {
			getFriendsUserQ.add(response.getFriends());
		} else {
			List<FriendsUser> friendsUsers = GameUtil.getList();
			getFriendsUserQ.add(friendsUsers);
		}
	}

	private BlockingQueue<List<FriendsUser>> getRecommentQ = new BlockingArrayQueue<List<FriendsUser>>(1);

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public List<FriendsUser> getRecomment() throws InterruptedException {
		PFS0004Request o = G.o(PFS0004Request.class);
		o.setSeq(seq.incrementAndGet());

		send("PFS0004", o);

		return getRecommentQ.take();
	}

	/**
	 * 
	 * @param response
	 */
	private void handlePFS0004(PFS0004Response response) {
		if (response != null && response.getState() == 1) {
			getRecommentQ.add(response.getFriends());
		} else {
			List<FriendsUser> friendsUser = GameUtil.getList();
			getRecommentQ.add(friendsUser);
		}

	}

	private BlockingQueue<Integer> removeFriendsQ = new BlockingArrayQueue<Integer>(1);

	/**
	 * remove friends
	 * @param friends
	 * @return
	 * @throws InterruptedException
	 */
	public Integer removeFriends(List<Long> friends) throws InterruptedException {

		PFS0005Request o = G.o(PFS0005Request.class);
		o.setFriendIds(friends);
		o.setSeq(seq.incrementAndGet());

		send("PFS0005", o);

		return removeFriendsQ.take();
	}

	/**
	 * 
	 * @param response
	 */
	private void handlePFS0005(PFS0005Response response) {
		if (response != null) {
			removeFriendsQ.add(Integer.valueOf(response.getState()));
		} else {
			removeFriendsQ.add(Integer.valueOf(0));
		}
	}

	private BlockingQueue<PFS0006Response> getRestrictionQ = new BlockingArrayQueue<PFS0000Beans.PFS0006Response>(1);

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public PFS0006Response getRestriction() throws InterruptedException {

		PFS0006Request o = G.o(PFS0006Request.class);
		o.setSeq(seq.incrementAndGet());

		send("PFS0006", o);
		return getRestrictionQ.take();
	}

	/**
	 * 
	 * 
	 * @param response
	 */
	private void handlePFS0006(PFS0006Response response) {
		if (response != null) {
			getRestrictionQ.add(response);
		} else {
			PFS0006Response o = G.o(PFS0006Response.class);
			getRestrictionQ.add(o);
		}
	}

	//
	//
	//
	//

	private BlockingQueue<List<User>> q = new BlockingArrayQueue<List<User>>(1);

	public List<User> getUserByUserId(long start, long end) throws InterruptedException {

		PU0014Request o = G.o(PU0014Request.class);
		o.setStart(start);
		o.setEnd(end);

		send("PU0014", o);

		return q.take();

	}

	// PU0014Response pu0014Response;
	// Map<Long,User> globalUsers=GameUtil.getMap();
	private void handlePU0014(PU0014Response response) {

		q.add(response.getUsers());
	}

	/**
	 * 
	 * cmd
	 */
	private Map<Integer, Object> cmds = GameUtil.getMap();

	private void putCmd(int seq, Object req) {
		cmds.put(Integer.valueOf(seq), req);
	}

	private <T> T getReq(int seq) {
		T t = null;
		Object obj = cmds.get(Integer.valueOf(seq));
		if (obj != null) {
			t = (T) obj;
		}
		return t;
	}
	
	
	//
	//
	//  Mock online collections.
 	//
	public static int sizeOfOnlineMock(){
		return onlineMockMobiles.size();
	}
	
	public  static Iterator<Long> keyIterator(){
		return onlineMockMobiles.keySet().iterator();
	}
	
	public static Collection<MockMobile> values(){
		return onlineMockMobiles.values();
	}
	
	public static boolean isOnline(Long id){
		return onlineMockMobiles.containsKey(id);
	}
	
	public static List<MockMobile> getMockMobileByRange(int offset,int len){
		// we need sort the user id.
		List<MockMobile> mobiles=new LinkedList<MockMobile>(onlineMockMobiles.values());
		Collections.sort(mobiles, new Comparator<MockMobile>() {

			public int compare(MockMobile o1, MockMobile o2) {
				// TODO Auto-generated method stub
				return (int)(o1.id-o2.id);
			}
		});
		List<MockMobile> items=null;
		if(offset+len<mobiles.size()){
			items=mobiles.subList(offset, offset+len);
		}else if(offset+len>mobiles.size()&&offset<mobiles.size()){
			items=mobiles.subList(offset, mobiles.size()-1);
		}else if(offset>mobiles.size()){
			items=GameUtil.getList();
		}
		return items;
	}
	
	
	public void close(){
		
		wrapper.disconnect();
		onlineMockMobiles.remove(id, this);
		
	}
	
	

}
