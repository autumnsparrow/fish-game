package com.toyo.fish.protocol.beans;

import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.route.RouterHeader;
import com.toyo.fish.protocol.beans.store.StoreItem;
import com.toyo.remote.service.system.domain.ProtocolModuleTypes;

/**
 * 
 * Protocol System.
 * 
 * 
 * 
 * @author sparrow
 *
 */
public class PS0000Beans {
	

	/**
	 * Extra Object of
	 * User Protocol 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerNamespaceExtraType(namespace="ProtocolSystem")
	public static class Extra  extends RouterHeader{
		
		
	}
	
	
	/**
	 * 
	 * 
	 * 
	 */
	@HandlerRequestType(transcode="PS0001")
	public static class PS0001Requet{
		
	}
	
	/**
	 * 
	 * Protocol System Store.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="PS0001",responsecode="SP0001")
	public static class PS0001Response {
		List<StoreItem> recomment;
		List<StoreItem> rod;
		List<StoreItem> reel;
		List<StoreItem> lure;
		List<StoreItem> line;
		List<StoreItem> drug;
		public List<StoreItem> getRecomment() {
			return recomment;
		}
		public void setRecomment(List<StoreItem> recomment) {
			this.recomment = recomment;
		}
		public List<StoreItem> getRod() {
			return rod;
		}
		public void setRod(List<StoreItem> rod) {
			this.rod = rod;
		}
		public List<StoreItem> getReel() {
			return reel;
		}
		public void setReel(List<StoreItem> reel) {
			this.reel = reel;
		}
		public List<StoreItem> getLure() {
			return lure;
		}
		public void setLure(List<StoreItem> lure) {
			this.lure = lure;
		}
		public List<StoreItem> getLine() {
			return line;
		}
		public void setLine(List<StoreItem> line) {
			this.line = line;
		}
		public List<StoreItem> getDrug() {
			return drug;
		}
		public void setDrug(List<StoreItem> drug) {
			this.drug = drug;
		}
		
	}
	
	
	@HandlerRequestType(transcode="PS0002")
	public static class PS0002Requet{
		
		Integer channelId;
		
		String  version;

		public Integer getChannelId() {
			return channelId;
		}

		public void setChannelId(Integer channelId) {
			this.channelId = channelId;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}
		
		
		
	}
	
	
	public static class FileItem{
		String url;
		String md5;
		long size;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getMd5() {
			return md5;
		}
		public void setMd5(String md5) {
			this.md5 = md5;
		}
		public long getSize() {
			return size;
		}
		public void setSize(long size) {
			this.size = size;
		}
		
		
		
	}
	
	/**
	 * 
	 * Protocol System Store.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="PS0002",responsecode="SP0002")
	public static class PS0002Response {
		
		String systemVersion;
		String url;
		String md5;
		List<FileItem> fileItems;
		boolean forceUpdate;
		
		
		public List<FileItem> getFileItems() {
			return fileItems;
		}
		public void setFileItems(List<FileItem> fileItems) {
			this.fileItems = fileItems;
		}
		public String getSystemVersion() {
			return systemVersion;
		}
		public void setSystemVersion(String systemVersion) {
			this.systemVersion = systemVersion;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getMd5() {
			return md5;
		}
		public void setMd5(String md5) {
			this.md5 = md5;
		}
		public boolean isForceUpdate() {
			return forceUpdate;
		}
		public void setForceUpdate(boolean forceUpdate) {
			this.forceUpdate = forceUpdate;
		}
	
	}
	
	@HandlerRequestType(transcode="PS0003")
	public static class PS0003Requet{
		int seq;

		public int getSeq() {
			return seq;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}
		
	}
	
	@HandlerResponseType(transcode="PS0003",responsecode="SP0003")
	public static class PS0003Response {
		boolean friend;
		boolean mail;
		boolean rank;
		boolean userdata;
		
		int seq;
		
		public int getSeq() {
			return seq;
		}
		public void setSeq(int seq) {
			this.seq = seq;
		}
		public boolean isFriend() {
			return friend;
		}
		public void setFriend(boolean friend) {
			this.friend = friend;
		}
		public boolean isMail() {
			return mail;
		}
		public void setMail(boolean mail) {
			this.mail = mail;
		}
		public boolean isRank() {
			return rank;
		}
		public void setRank(boolean rank) {
			this.rank = rank;
		}
		public boolean isUserdata() {
			return userdata;
		}
		public void setUserdata(boolean userdata) {
			this.userdata = userdata;
		}
		
		
		
		

	}
	
	
	@HandlerRequestType(transcode="PS0004")
	public static class PS0004Requet{
		int seq;
		ProtocolModuleTypes type;
		boolean state;
		public int getSeq() {
			return seq;
		}
		public void setSeq(int seq) {
			this.seq = seq;
		}
		public ProtocolModuleTypes getType() {
			return type;
		}
		public void setType(ProtocolModuleTypes type) {
			this.type = type;
		}
		public boolean isState() {
			return state;
		}
		public void setState(boolean state) {
			this.state = state;
		}
		
		
		
	}
	@HandlerResponseType(transcode="PS0004",responsecode="SP0004")
	public static class PS0004Response{
		int seq;

		public int getSeq() {
			return seq;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}
		
	}

}
