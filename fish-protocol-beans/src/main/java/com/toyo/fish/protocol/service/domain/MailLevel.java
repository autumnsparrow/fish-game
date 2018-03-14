/**
 * 
 */
package com.toyo.fish.protocol.service.domain;

/**
 * @author sparrow
 *
 */
public class MailLevel {
	
	public static final int LE=1; //  x < min
	public static final int BW=2; // x>min &&x<max
	public static final int GE=3; // x>min
	
	int min;
	int max;
	int relation;
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getRelation() {
		return relation;
	}
	public void setRelation(int relation) {
		this.relation = relation;
	}
	
	
	

}
