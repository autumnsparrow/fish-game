package com.sky.mobile.protocol.domain;

import java.math.BigDecimal;
import java.util.Date;

public class FlymeNotify {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.notify_time
     *
     * @mbggenerated
     */
    private Date notifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.notify_id
     *
     * @mbggenerated
     */
    private String notifyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.order_id
     *
     * @mbggenerated
     */
    private Long orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.app_id
     *
     * @mbggenerated
     */
    private Long appId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.uid
     *
     * @mbggenerated
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.parttern_id
     *
     * @mbggenerated
     */
    private String partternId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.local_order_id
     *
     * @mbggenerated
     */
    private String localOrderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.product_id
     *
     * @mbggenerated
     */
    private String productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.product_unit
     *
     * @mbggenerated
     */
    private String productUnit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.buy_amount
     *
     * @mbggenerated
     */
    private Integer buyAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.product_per_price
     *
     * @mbggenerated
     */
    private BigDecimal productPerPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.total_price
     *
     * @mbggenerated
     */
    private BigDecimal totalPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.trade_status
     *
     * @mbggenerated
     */
    private String tradeStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.pay_time
     *
     * @mbggenerated
     */
    private Date payTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.pay_type
     *
     * @mbggenerated
     */
    private Integer payType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.user_info
     *
     * @mbggenerated
     */
    private String userInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.sign
     *
     * @mbggenerated
     */
    private String sign;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.sign_type
     *
     * @mbggenerated
     */
    private String signType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_flyme_notify.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.id
     *
     * @return the value of tbl_flyme_notify.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.id
     *
     * @param id the value for tbl_flyme_notify.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.notify_time
     *
     * @return the value of tbl_flyme_notify.notify_time
     *
     * @mbggenerated
     */
    public Date getNotifyTime() {
        return notifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.notify_time
     *
     * @param notifyTime the value for tbl_flyme_notify.notify_time
     *
     * @mbggenerated
     */
    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.notify_id
     *
     * @return the value of tbl_flyme_notify.notify_id
     *
     * @mbggenerated
     */
    public String getNotifyId() {
        return notifyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.notify_id
     *
     * @param notifyId the value for tbl_flyme_notify.notify_id
     *
     * @mbggenerated
     */
    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId == null ? null : notifyId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.order_id
     *
     * @return the value of tbl_flyme_notify.order_id
     *
     * @mbggenerated
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.order_id
     *
     * @param orderId the value for tbl_flyme_notify.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.app_id
     *
     * @return the value of tbl_flyme_notify.app_id
     *
     * @mbggenerated
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.app_id
     *
     * @param appId the value for tbl_flyme_notify.app_id
     *
     * @mbggenerated
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.uid
     *
     * @return the value of tbl_flyme_notify.uid
     *
     * @mbggenerated
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.uid
     *
     * @param uid the value for tbl_flyme_notify.uid
     *
     * @mbggenerated
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.parttern_id
     *
     * @return the value of tbl_flyme_notify.parttern_id
     *
     * @mbggenerated
     */
    public String getPartternId() {
        return partternId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.parttern_id
     *
     * @param partternId the value for tbl_flyme_notify.parttern_id
     *
     * @mbggenerated
     */
    public void setPartternId(String partternId) {
        this.partternId = partternId == null ? null : partternId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.local_order_id
     *
     * @return the value of tbl_flyme_notify.local_order_id
     *
     * @mbggenerated
     */
    public String getLocalOrderId() {
        return localOrderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.local_order_id
     *
     * @param localOrderId the value for tbl_flyme_notify.local_order_id
     *
     * @mbggenerated
     */
    public void setLocalOrderId(String localOrderId) {
        this.localOrderId = localOrderId == null ? null : localOrderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.product_id
     *
     * @return the value of tbl_flyme_notify.product_id
     *
     * @mbggenerated
     */
    public String getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.product_id
     *
     * @param productId the value for tbl_flyme_notify.product_id
     *
     * @mbggenerated
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.product_unit
     *
     * @return the value of tbl_flyme_notify.product_unit
     *
     * @mbggenerated
     */
    public String getProductUnit() {
        return productUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.product_unit
     *
     * @param productUnit the value for tbl_flyme_notify.product_unit
     *
     * @mbggenerated
     */
    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit == null ? null : productUnit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.buy_amount
     *
     * @return the value of tbl_flyme_notify.buy_amount
     *
     * @mbggenerated
     */
    public Integer getBuyAmount() {
        return buyAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.buy_amount
     *
     * @param buyAmount the value for tbl_flyme_notify.buy_amount
     *
     * @mbggenerated
     */
    public void setBuyAmount(Integer buyAmount) {
        this.buyAmount = buyAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.product_per_price
     *
     * @return the value of tbl_flyme_notify.product_per_price
     *
     * @mbggenerated
     */
    public BigDecimal getProductPerPrice() {
        return productPerPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.product_per_price
     *
     * @param productPerPrice the value for tbl_flyme_notify.product_per_price
     *
     * @mbggenerated
     */
    public void setProductPerPrice(BigDecimal productPerPrice) {
        this.productPerPrice = productPerPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.total_price
     *
     * @return the value of tbl_flyme_notify.total_price
     *
     * @mbggenerated
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.total_price
     *
     * @param totalPrice the value for tbl_flyme_notify.total_price
     *
     * @mbggenerated
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.trade_status
     *
     * @return the value of tbl_flyme_notify.trade_status
     *
     * @mbggenerated
     */
    public String getTradeStatus() {
        return tradeStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.trade_status
     *
     * @param tradeStatus the value for tbl_flyme_notify.trade_status
     *
     * @mbggenerated
     */
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus == null ? null : tradeStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.pay_time
     *
     * @return the value of tbl_flyme_notify.pay_time
     *
     * @mbggenerated
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.pay_time
     *
     * @param payTime the value for tbl_flyme_notify.pay_time
     *
     * @mbggenerated
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.pay_type
     *
     * @return the value of tbl_flyme_notify.pay_type
     *
     * @mbggenerated
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.pay_type
     *
     * @param payType the value for tbl_flyme_notify.pay_type
     *
     * @mbggenerated
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.user_info
     *
     * @return the value of tbl_flyme_notify.user_info
     *
     * @mbggenerated
     */
    public String getUserInfo() {
        return userInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.user_info
     *
     * @param userInfo the value for tbl_flyme_notify.user_info
     *
     * @mbggenerated
     */
    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo == null ? null : userInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.sign
     *
     * @return the value of tbl_flyme_notify.sign
     *
     * @mbggenerated
     */
    public String getSign() {
        return sign;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.sign
     *
     * @param sign the value for tbl_flyme_notify.sign
     *
     * @mbggenerated
     */
    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.sign_type
     *
     * @return the value of tbl_flyme_notify.sign_type
     *
     * @mbggenerated
     */
    public String getSignType() {
        return signType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.sign_type
     *
     * @param signType the value for tbl_flyme_notify.sign_type
     *
     * @mbggenerated
     */
    public void setSignType(String signType) {
        this.signType = signType == null ? null : signType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.create_time
     *
     * @return the value of tbl_flyme_notify.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.create_time
     *
     * @param createTime the value for tbl_flyme_notify.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_flyme_notify.update_time
     *
     * @return the value of tbl_flyme_notify.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_flyme_notify.update_time
     *
     * @param updateTime the value for tbl_flyme_notify.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}