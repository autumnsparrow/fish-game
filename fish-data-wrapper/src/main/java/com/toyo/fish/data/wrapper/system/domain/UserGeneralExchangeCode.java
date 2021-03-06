package com.toyo.fish.data.wrapper.system.domain;

import java.util.Date;

public class UserGeneralExchangeCode {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_user_general_exchange_code.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_user_general_exchange_code.code_id
     *
     * @mbggenerated
     */
    private Integer codeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_user_general_exchange_code.gift_id
     *
     * @mbggenerated
     */
    private Integer giftId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_user_general_exchange_code.code
     *
     * @mbggenerated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_user_general_exchange_code.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_user_general_exchange_code.create_time
     *
     * @mbggenerated
     */
    private Date createTime;
    
    private Integer state;

    public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_user_general_exchange_code.id
     *
     * @return the value of ty_user_general_exchange_code.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_user_general_exchange_code.id
     *
     * @param id the value for ty_user_general_exchange_code.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_user_general_exchange_code.code_id
     *
     * @return the value of ty_user_general_exchange_code.code_id
     *
     * @mbggenerated
     */
    public Integer getCodeId() {
        return codeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_user_general_exchange_code.code_id
     *
     * @param codeId the value for ty_user_general_exchange_code.code_id
     *
     * @mbggenerated
     */
    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_user_general_exchange_code.gift_id
     *
     * @return the value of ty_user_general_exchange_code.gift_id
     *
     * @mbggenerated
     */
    public Integer getGiftId() {
        return giftId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_user_general_exchange_code.gift_id
     *
     * @param giftId the value for ty_user_general_exchange_code.gift_id
     *
     * @mbggenerated
     */
    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_user_general_exchange_code.code
     *
     * @return the value of ty_user_general_exchange_code.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_user_general_exchange_code.code
     *
     * @param code the value for ty_user_general_exchange_code.code
     *
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_user_general_exchange_code.user_id
     *
     * @return the value of ty_user_general_exchange_code.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_user_general_exchange_code.user_id
     *
     * @param userId the value for ty_user_general_exchange_code.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_user_general_exchange_code.create_time
     *
     * @return the value of ty_user_general_exchange_code.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_user_general_exchange_code.create_time
     *
     * @param createTime the value for ty_user_general_exchange_code.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}