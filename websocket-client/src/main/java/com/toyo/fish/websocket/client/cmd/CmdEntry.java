/**
 * 
 */
package com.toyo.fish.websocket.client.cmd;

/**
 * @author sparrow
 *
 */
public class CmdEntry <Req,Resp>{
	
	Integer seq;
	Req req;
	Resp resp;
	
	public CmdEntry(Integer seq, Req req) {
		super();
		this.seq = seq;
		this.req = req;
	}
	
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Req getReq() {
		return req;
	}
	public void setReq(Req req) {
		this.req = req;
	}
	public Resp getResp() {
		return resp;
	}
	public void setResp(Resp resp) {
		this.resp = resp;
	}
	
	

}
