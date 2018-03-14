/**
 * 
 */
package com.toyo.fish.protocol.beans.store;

import java.util.List;

/**
 * @author sparrow
 *
 */
public class StoreItem {
	
	long id;
	String itemId;
	int star;
	int shopSequence;
	int gold;
	int jewel;
	int availableLevel;
	int openTreasureLevel;
	boolean repairable;
	int amount;// repairable =true,endure.
	int damage;
	List<EquipmentAbility> propertyEffect;
	int categoryType;
	int propertyAdditionCount;// repairable =true ,add property 
	int channelType;// get the item channel type (Store,Chest)
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public int getShopSequence() {
		return shopSequence;
	}
	public void setShopSequence(int shopSequence) {
		this.shopSequence = shopSequence;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getJewel() {
		return jewel;
	}
	public void setJewel(int jewel) {
		this.jewel = jewel;
	}
	public int getAvailableLevel() {
		return availableLevel;
	}
	public void setAvailableLevel(int availableLevel) {
		this.availableLevel = availableLevel;
	}
	public int getOpenTreasureLevel() {
		return openTreasureLevel;
	}
	public void setOpenTreasureLevel(int openTreasureLevel) {
		this.openTreasureLevel = openTreasureLevel;
	}
	public boolean isRepairable() {
		return repairable;
	}
	public void setRepairable(boolean repairable) {
		this.repairable = repairable;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public List<EquipmentAbility> getPropertyEffect() {
		return propertyEffect;
	}
	public void setPropertyEffect(List<EquipmentAbility> propertyEffect) {
		this.propertyEffect = propertyEffect;
	}
	public int getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(int categoryType) {
		this.categoryType = categoryType;
	}
	public int getPropertyAdditionCount() {
		return propertyAdditionCount;
	}
	public void setPropertyAdditionCount(int propertyAdditionCount) {
		this.propertyAdditionCount = propertyAdditionCount;
	}
	public int getChannelType() {
		return channelType;
	}
	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}
	
	

}
