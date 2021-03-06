package com.toyo.fish.data.wrapper.system.domain;

import java.util.Date;

public class GeneralExchangeCode {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_general_exchange_code.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_general_exchange_code.code
     *
     * @mbggenerated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_general_exchange_code.gift_id
     *
     * @mbggenerated
     */
    private Integer giftId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_general_exchange_code.begin_time
     *
     * @mbggenerated
     */
    private Date beginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_general_exchange_code.end_time
     *
     * @mbggenerated
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ty_general_exchange_code.flag
     *
     * @mbggenerated
     */
    private Integer flag;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_general_exchange_code.id
     *
     * @return the value of ty_general_exchange_code.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_general_exchange_code.id
     *
     * @param id the value for ty_general_exchange_code.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_general_exchange_code.code
     *
     * @return the value of ty_general_exchange_code.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_general_exchange_code.code
     *
     * @param code the value for ty_general_exchange_code.code
     *
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_general_exchange_code.gift_id
     *
     * @return the value of ty_general_exchange_code.gift_id
     *
     * @mbggenerated
     */
    public Integer getGiftId() {
        return giftId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_general_exchange_code.gift_id
     *
     * @param giftId the value for ty_general_exchange_code.gift_id
     *
     * @mbggenerated
     */
    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_general_exchange_code.begin_time
     *
     * @return the value of ty_general_exchange_code.begin_time
     *
     * @mbggenerated
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_general_exchange_code.begin_time
     *
     * @param beginTime the value for ty_general_exchange_code.begin_time
     *
     * @mbggenerated
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_general_exchange_code.end_time
     *
     * @return the value of ty_general_exchange_code.end_time
     *
     * @mbggenerated
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_general_exchange_code.end_time
     *
     * @param endTime the value for ty_general_exchange_code.end_time
     *
     * @mbggenerated
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ty_general_exchange_code.flag
     *
     * @return the value of ty_general_exchange_code.flag
     *
     * @mbggenerated
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ty_general_exchange_code.flag
     *
     * @param flag the value for ty_general_exchange_code.flag
     *
     * @mbggenerated
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}