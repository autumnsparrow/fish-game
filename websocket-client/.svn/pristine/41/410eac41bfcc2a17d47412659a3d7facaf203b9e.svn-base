/**
 * 
 */
package com.toyo.fish.websocket.client.cmd;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.util.BlockingArrayQueue;

import com.sky.game.context.executor.UnorderedThreadPoolExecutor;
import com.sky.game.context.util.GameUtil;
import com.toyo.fish.protocol.beans.PU0000Beans.User;
import com.toyo.fish.websocket.client.cmd.MockMobile.FriendMailTypes;
import com.toyo.fish.websocket.client.cmd.MockMobile.MailCategory;
import com.toyo.fish.websocket.client.cmd.MockMobile.MailStates;

/**
 * @author sparrow
 *
 */
public class MobileSender implements Runnable{
	
	
	private static final BlockingQueue<MockMobile> watingMockMobile=new BlockingArrayQueue<MockMobile>(50);
	private static final UnorderedThreadPoolExecutor executor=new UnorderedThreadPoolExecutor(100);
	
	private static final AtomicLong receiverStart=new AtomicLong(100000);
	private static int recieverLength=20;
	
	private static final Log logger=LogFactory.getLog(MobileSender.class);
	
	public static void setTargetId(Long userId){
		receiverStart.set(userId);
	}
	
	
	public MobileSender() {
		super();
		// TODO Auto-generated constructor stub
		Thread t=new Thread(this);
		t.setDaemon(true);
		t.start();
	}

	
	private void execute(Runnable task){
		executor.execute(task);
	}
	
	public static class RecieverFriendsTask implements Runnable{
		MockMobile mobile;
		

		public RecieverFriendsTask(MockMobile mobile) {
			super();
			this.mobile = mobile;
		}


		public void run() {
			// TODO Auto-generated method stub
			try {
				//ScriptsBuilder scripts=new ScriptsBuilder();
				//receiverStart.getAndSet(receiverStart.get()+recieverLength);
				
				List<Long> mailIdSeq=mobile.getMailId(MailCategory.FriendRequest, MailStates.UnRead, 0, 50);
				
				List<Long> failedId=mobile.updateMailIdSeq(mailIdSeq, MailStates.Read);
				logger.info("update mailIds:"+Arrays.toString(mailIdSeq.toArray(new Long[]{}))+" failed mailIds:"+Arrays.toString(failedId.toArray(new Long[]{})));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public static class SendFriendMailTask implements Runnable{
		
		MockMobile mobile;
		

		public SendFriendMailTask(MockMobile mobile) {
			super();
			this.mobile = mobile;
		}


		public void run() {
			// TODO Auto-generated method stub
			try {
				//ScriptsBuilder scripts=new ScriptsBuilder();
				receiverStart.getAndSet(receiverStart.get()+recieverLength);
				List<User> users=mobile.getUserByUserId(receiverStart.get(), receiverStart.get()+recieverLength);
				for(User u:users){
					mobile.sendFriendMail(GameUtil.s2c(u.getId()), FriendMailTypes.FriendRequestMail);
					//scripts.append("mail update --userId "+mobile.getDeviceId()+" ");
					logger.info("send mail  from:"+mobile.getClientId() +" to "+u.getId()+","+(u.getId()+19920606)+","+u.getDeviceId()+","+u.getChannelId()+")");
					//scripts.append("make friends --userId "+(u.getId()+19920606));
					//receiverStart.incrementAndGet();
					
				}
				//scripts.save("make_friends_"+this.mobile.getClientId()+".ss");
				
				//mobile.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	public void run() {
		// TODO Auto-generated method stub
		for(;;){
			try {
				
				MockMobile mobile=watingMockMobile.take();
				//ScriptsBuilder scripts=new ScriptsBuilder();
				if(mobile.enableMailSender)
					execute(new SendFriendMailTask(mobile));
				if(mobile.enableMailReceiver){
					execute(new RecieverFriendsTask(mobile));
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	
	public static   void put(MockMobile mobile){
		watingMockMobile.add(mobile);
		
	}
	

}
