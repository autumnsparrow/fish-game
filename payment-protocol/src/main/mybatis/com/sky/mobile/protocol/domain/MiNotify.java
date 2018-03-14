package com.sky.mobile.protocol.domain;

import java.util.Date;

public class MiNotify {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.app_id
     *
     * @mbggenerated
     */
    private String appId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.cp_order_id
     *
     * @mbggenerated
     */
    private String cpOrderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.cp_user_info
     *
     * @mbggenerated
     */
    private String cpUserInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.order_id
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.order_status
     *
     * @mbggenerated
     */
    private String orderStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.pay_fee
     *
     * @mbggenerated
     */
    private String payFee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.product_code
     *
     * @mbggenerated
     */
    private String productCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.product_name
     *
     * @mbggenerated
     */
    private String productName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.product_count
     *
     * @mbggenerated
     */
    private Integer productCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.pay_time
     *
     * @mbggenerated
     */
    private String payTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.order_consume_type
     *
     * @mbggenerated
     */
    private String orderConsumeType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.partner_gift_consume
     *
     * @mbggenerated
     */
    private String partnerGiftConsume;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_mi_notify.uid
     *
     * @mbggenerated
     */
    private String uid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.id
     *
     * @return the value of tbl_mi_notify.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.id
     *
     * @param id the value for tbl_mi_notify.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.app_id
     *
     * @return the value of tbl_mi_notify.app_id
     *
     * @mbggenerated
     */
    public String getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.app_id
     *
     * @param appId the value for tbl_mi_notify.app_id
     *
     * @mbggenerated
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.cp_order_id
     *
     * @return the value of tbl_mi_notify.cp_order_id
     *
     * @mbggenerated
     */
    public String getCpOrderId() {
        return cpOrderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.cp_order_id
     *
     * @param cpOrderId the value for tbl_mi_notify.cp_order_id
     *
     * @mbggenerated
     */
    public void setCpOrderId(String cpOrderId) {
        this.cpOrderId = cpOrderId == null ? null : cpOrderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.cp_user_info
     *
     * @return the value of tbl_mi_notify.cp_user_info
     *
     * @mbggenerated
     */
    public String getCpUserInfo() {
        return cpUserInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.cp_user_info
     *
     * @param cpUserInfo the value for tbl_mi_notify.cp_user_info
     *
     * @mbggenerated
     */
    public void setCpUserInfo(String cpUserInfo) {
        this.cpUserInfo = cpUserInfo == null ? null : cpUserInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.order_id
     *
     * @return the value of tbl_mi_notify.order_id
     *
     * @mbggenerated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.order_id
     *
     * @param orderId the value for tbl_mi_notify.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.order_status
     *
     * @return the value of tbl_mi_notify.order_status
     *
     * @mbggenerated
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.order_status
     *
     * @param orderStatus the value for tbl_mi_notify.order_status
     *
     * @mbggenerated
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.pay_fee
     *
     * @return the value of tbl_mi_notify.pay_fee
     *
     * @mbggenerated
     */
    public String getPayFee() {
        return payFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.pay_fee
     *
     * @param payFee the value for tbl_mi_notify.pay_fee
     *
     * @mbggenerated
     */
    public void setPayFee(String payFee) {
        this.payFee = payFee == null ? null : payFee.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.product_code
     *
     * @return the value of tbl_mi_notify.product_code
     *
     * @mbggenerated
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.product_code
     *
     * @param productCode the value for tbl_mi_notify.product_code
     *
     * @mbggenerated
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.product_name
     *
     * @return the value of tbl_mi_notify.product_name
     *
     * @mbggenerated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.product_name
     *
     * @param productName the value for tbl_mi_notify.product_name
     *
     * @mbggenerated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.product_count
     *
     * @return the value of tbl_mi_notify.product_count
     *
     * @mbggenerated
     */
    public Integer getProductCount() {
        return productCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.product_count
     *
     * @param productCount the value for tbl_mi_notify.product_count
     *
     * @mbggenerated
     */
    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.pay_time
     *
     * @return the value of tbl_mi_notify.pay_time
     *
     * @mbggenerated
     */
    public String getPayTime() {
        return payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.pay_time
     *
     * @param payTime the value for tbl_mi_notify.pay_time
     *
     * @mbggenerated
     */
    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.order_consume_type
     *
     * @return the value of tbl_mi_notify.order_consume_type
     *
     * @mbggenerated
     */
    public String getOrderConsumeType() {
        return orderConsumeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.order_consume_type
     *
     * @param orderConsumeType the value for tbl_mi_notify.order_consume_type
     *
     * @mbggenerated
     */
    public void setOrderConsumeType(String orderConsumeType) {
        this.orderConsumeType = orderConsumeType == null ? null : orderConsumeType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.partner_gift_consume
     *
     * @return the value of tbl_mi_notify.partner_gift_consume
     *
     * @mbggenerated
     */
    public String getPartnerGiftConsume() {
        return partnerGiftConsume;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.partner_gift_consume
     *
     * @param partnerGiftConsume the value for tbl_mi_notify.partner_gift_consume
     *
     * @mbggenerated
     */
    public void setPartnerGiftConsume(String partnerGiftConsume) {
        this.partnerGiftConsume = partnerGiftConsume == null ? null : partnerGiftConsume.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.update_time
     *
     * @return the value of tbl_mi_notify.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.update_time
     *
     * @param updateTime the value for tbl_mi_notify.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.create_time
     *
     * @return the value of tbl_mi_notify.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.create_time
     *
     * @param createTime the value for tbl_mi_notify.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_mi_notify.uid
     *
     * @return the value of tbl_mi_notify.uid
     *
     * @mbggenerated
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_mi_notify.uid
     *
     * @param uid the value for tbl_mi_notify.uid
     *
     * @mbggenerated
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }
}