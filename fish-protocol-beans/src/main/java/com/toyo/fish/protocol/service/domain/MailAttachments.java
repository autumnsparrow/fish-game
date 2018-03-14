package com.toyo.fish.protocol.service.domain;

import java.util.List;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.util.G;
import com.sky.game.context.util.GameUtil;

public class MailAttachments {
	
	List<MailAttachment> attachments;

	public List<MailAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<MailAttachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return GameContextGlobals.getJsonConvertor().format(this);
	}
	
	
	
	public static void main(String args[]){
		
		MailAttachments o=G.o(MailAttachments.class);
		List<MailAttachment> l=GameUtil.getList();
		
		MailAttachment a=G.o(MailAttachment.class);
		a.setId("icon");
		a.setType(1);
		a.setValue("0");
		
		MailAttachment b=G.o(MailAttachment.class);
		b.setId("Sr_0008_01");
		b.setType(1);
		b.setValue("1");
		
		l.add(a);
		l.add(b);
		
		o.setAttachments(l);
		
		String s=GameContextGlobals.getJsonConvertor().format(o);
		
		System.out.println(s);
		
	}
	

}
