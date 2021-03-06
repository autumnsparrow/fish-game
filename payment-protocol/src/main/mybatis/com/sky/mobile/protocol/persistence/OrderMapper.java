package com.sky.mobile.protocol.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sky.mobile.protocol.domain.Order;

public interface OrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_order
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_order
     *
     * @mbggenerated
     */
    int insert(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_order
     *
     * @mbggenerated
     */
    int insertSelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_order
     *
     * @mbggenerated
     */
    Order selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Order record);


    int updateByExpireTime();
    
    Order selectByOrderId (String orderId);
    
    
    List<Order> selectByUserIdAndImei(String userId,String imei);
    
    
    List<Order> selectOrderByState(@Param("interval") Integer interval,@Param("length") Integer length);

}

