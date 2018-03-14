/**
 * 
 */
package com.sky.mobile.protocol;

/**
 * @author sparrow
 *
 */
public class ProtocolEntity {
	
	private Integer  code;
	
	private String content;
	private long timestamp;
	private Integer elapsed;
	
	
	
	public ProtocolEntity() {
		super();
		// TODO Auto-generated constructor stub
		
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getElapsed() {
		return elapsed;
	}
	public void setElapsed(Integer elapsed) {
		this.elapsed = elapsed;
	}
	
	@Override
	public String toString() {
		return "ProtocolEntity [code=" + code + ", content=" + content
				+ ", elapsed=" + elapsed + "]";
	}
	

}
