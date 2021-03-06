package com.toyo.fish.data.wrapper.friends.domain;

import java.util.Date;

public class FriendsRelation {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_friends_relation.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_friends_relation.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_friends_relation.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_friends_relation.owner_user_id
     *
     * @mbggenerated
     */
    private Long ownerUserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_friends_relation.friend_user_id
     *
     * @mbggenerated
     */
    private Long friendUserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_friends_relation.vit_state
     *
     * @mbggenerated
     */
    private Integer vitState;
    
    private Integer state;
    
  

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	private Friends friend;

    public Friends getFriend() {
		return friend;
	}

	public void setFriend(Friends friend) {
		this.friend = friend;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_friends_relation.id
     *
     * @return the value of ty_friends_relation.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_friends_relation.id
     *
     * @param id the value for ty_friends_relation.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_friends_relation.update_time
     *
     * @return the value of ty_friends_relation.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_friends_relation.update_time
     *
     * @param updateTime the value for ty_friends_relation.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_friends_relation.create_time
     *
     * @return the value of ty_friends_relation.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_friends_relation.create_time
     *
     * @param createTime the value for ty_friends_relation.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_friends_relation.owner_user_id
     *
     * @return the value of ty_friends_relation.owner_user_id
     *
     * @mbggenerated
     */
    public Long getOwnerUserId() {
        return ownerUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_friends_relation.owner_user_id
     *
     * @param ownerUserId the value for ty_friends_relation.owner_user_id
     *
     * @mbggenerated
     */
    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_friends_relation.friend_user_id
     *
     * @return the value of ty_friends_relation.friend_user_id
     *
     * @mbggenerated
     */
    public Long getFriendUserId() {
        return friendUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_friends_relation.friend_user_id
     *
     * @param friendUserId the value for ty_friends_relation.friend_user_id
     *
     * @mbggenerated
     */
    public void setFriendUserId(Long friendUserId) {
        this.friendUserId = friendUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_friends_relation.vit_state
     *
     * @return the value of ty_friends_relation.vit_state
     *
     * @mbggenerated
     */
    public Integer getVitState() {
        return vitState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_friends_relation.vit_state
     *
     * @param vitState the value for ty_friends_relation.vit_state
     *
     * @mbggenerated
     */
    public void setVitState(Integer vitState) {
        this.vitState = vitState;
    }
}