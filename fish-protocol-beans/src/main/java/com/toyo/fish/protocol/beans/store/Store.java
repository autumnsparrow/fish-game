/**
 * 
 */
package com.toyo.fish.protocol.beans.store;

import java.util.List;

/**
 * @author sparrow
 *
 */
public class Store {

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
