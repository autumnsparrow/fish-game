package com.sky.mobile.protocol.domain;

import java.util.Date;

public class LenovoGetOrder {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_lenovo_get_order.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_lenovo_get_order.amount
     *
     * @mbggenerated
     */
    private Integer amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_lenovo_get_order.ware_id
     *
     * @mbggenerated
     */
    private Integer wareId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_lenovo_get_order.ware_name
     *
     * @mbggenerated
     */
    private String wareName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_lenovo_get_order.order_id
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_lenovo_get_order.local_order_id
     *
     * @mbggenerated
     */
    private String localOrderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_lenovo_get_order.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_lenovo_get_order.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_lenovo_get_order.id
     *
     * @return the value of tbl_lenovo_get_order.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_lenovo_get_order.id
     *
     * @param id the value for tbl_lenovo_get_order.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_lenovo_get_order.amount
     *
     * @return the value of tbl_lenovo_get_order.amount
     *
     * @mbggenerated
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_lenovo_get_order.amount
     *
     * @param amount the value for tbl_lenovo_get_order.amount
     *
     * @mbggenerated
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_lenovo_get_order.ware_id
     *
     * @return the value of tbl_lenovo_get_order.ware_id
     *
     * @mbggenerated
     */
    public Integer getWareId() {
        return wareId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_lenovo_get_order.ware_id
     *
     * @param wareId the value for tbl_lenovo_get_order.ware_id
     *
     * @mbggenerated
     */
    public void setWareId(Integer wareId) {
        this.wareId = wareId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_lenovo_get_order.ware_name
     *
     * @return the value of tbl_lenovo_get_order.ware_name
     *
     * @mbggenerated
     */
    public String getWareName() {
        return wareName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_lenovo_get_order.ware_name
     *
     * @param wareName the value for tbl_lenovo_get_order.ware_name
     *
     * @mbggenerated
     */
    public void setWareName(String wareName) {
        this.wareName = wareName == null ? null : wareName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_lenovo_get_order.order_id
     *
     * @return the value of tbl_lenovo_get_order.order_id
     *
     * @mbggenerated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_lenovo_get_order.order_id
     *
     * @param orderId the value for tbl_lenovo_get_order.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_lenovo_get_order.local_order_id
     *
     * @return the value of tbl_lenovo_get_order.local_order_id
     *
     * @mbggenerated
     */
    public String getLocalOrderId() {
        return localOrderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_lenovo_get_order.local_order_id
     *
     * @param localOrderId the value for tbl_lenovo_get_order.local_order_id
     *
     * @mbggenerated
     */
    public void setLocalOrderId(String localOrderId) {
        this.localOrderId = localOrderId == null ? null : localOrderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_lenovo_get_order.update_time
     *
     * @return the value of tbl_lenovo_get_order.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_lenovo_get_order.update_time
     *
     * @param updateTime the value for tbl_lenovo_get_order.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_lenovo_get_order.create_time
     *
     * @return the value of tbl_lenovo_get_order.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_lenovo_get_order.create_time
     *
     * @param createTime the value for tbl_lenovo_get_order.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}