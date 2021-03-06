package com.sky.mobile.protocol.domain;

import java.util.Date;

public class UcNotify {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.trade_id
     *
     * @mbggenerated
     */
    private String tradeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.game_id
     *
     * @mbggenerated
     */
    private String gameId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.amount
     *
     * @mbggenerated
     */
    private String amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.order_id
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.pay_type
     *
     * @mbggenerated
     */
    private String payType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.attach_info
     *
     * @mbggenerated
     */
    private String attachInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.order_status
     *
     * @mbggenerated
     */
    private String orderStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.failed_desc
     *
     * @mbggenerated
     */
    private String failedDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.trade_time
     *
     * @mbggenerated
     */
    private String tradeTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.sign
     *
     * @mbggenerated
     */
    private String sign;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_uc_notify.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.id
     *
     * @return the value of tbl_uc_notify.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.id
     *
     * @param id the value for tbl_uc_notify.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.trade_id
     *
     * @return the value of tbl_uc_notify.trade_id
     *
     * @mbggenerated
     */
    public String getTradeId() {
        return tradeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.trade_id
     *
     * @param tradeId the value for tbl_uc_notify.trade_id
     *
     * @mbggenerated
     */
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.game_id
     *
     * @return the value of tbl_uc_notify.game_id
     *
     * @mbggenerated
     */
    public String getGameId() {
        return gameId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.game_id
     *
     * @param gameId the value for tbl_uc_notify.game_id
     *
     * @mbggenerated
     */
    public void setGameId(String gameId) {
        this.gameId = gameId == null ? null : gameId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.amount
     *
     * @return the value of tbl_uc_notify.amount
     *
     * @mbggenerated
     */
    public String getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.amount
     *
     * @param amount the value for tbl_uc_notify.amount
     *
     * @mbggenerated
     */
    public void setAmount(String amount) {
        this.amount = amount == null ? null : amount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.order_id
     *
     * @return the value of tbl_uc_notify.order_id
     *
     * @mbggenerated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.order_id
     *
     * @param orderId the value for tbl_uc_notify.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.pay_type
     *
     * @return the value of tbl_uc_notify.pay_type
     *
     * @mbggenerated
     */
    public String getPayType() {
        return payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.pay_type
     *
     * @param payType the value for tbl_uc_notify.pay_type
     *
     * @mbggenerated
     */
    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.attach_info
     *
     * @return the value of tbl_uc_notify.attach_info
     *
     * @mbggenerated
     */
    public String getAttachInfo() {
        return attachInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.attach_info
     *
     * @param attachInfo the value for tbl_uc_notify.attach_info
     *
     * @mbggenerated
     */
    public void setAttachInfo(String attachInfo) {
        this.attachInfo = attachInfo == null ? null : attachInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.order_status
     *
     * @return the value of tbl_uc_notify.order_status
     *
     * @mbggenerated
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.order_status
     *
     * @param orderStatus the value for tbl_uc_notify.order_status
     *
     * @mbggenerated
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.failed_desc
     *
     * @return the value of tbl_uc_notify.failed_desc
     *
     * @mbggenerated
     */
    public String getFailedDesc() {
        return failedDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.failed_desc
     *
     * @param failedDesc the value for tbl_uc_notify.failed_desc
     *
     * @mbggenerated
     */
    public void setFailedDesc(String failedDesc) {
        this.failedDesc = failedDesc == null ? null : failedDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.trade_time
     *
     * @return the value of tbl_uc_notify.trade_time
     *
     * @mbggenerated
     */
    public String getTradeTime() {
        return tradeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.trade_time
     *
     * @param tradeTime the value for tbl_uc_notify.trade_time
     *
     * @mbggenerated
     */
    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime == null ? null : tradeTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.sign
     *
     * @return the value of tbl_uc_notify.sign
     *
     * @mbggenerated
     */
    public String getSign() {
        return sign;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.sign
     *
     * @param sign the value for tbl_uc_notify.sign
     *
     * @mbggenerated
     */
    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.create_time
     *
     * @return the value of tbl_uc_notify.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.create_time
     *
     * @param createTime the value for tbl_uc_notify.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_uc_notify.update_time
     *
     * @return the value of tbl_uc_notify.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_uc_notify.update_time
     *
     * @param updateTime the value for tbl_uc_notify.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}