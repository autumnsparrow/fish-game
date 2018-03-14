package com.toyo.fish.data.wrapper.system.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.toyo.fish.data.wrapper.system.domain.ExchangeCodeItem;

public interface ExchangeCodeItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_exchange_code_item
     *
     * @mbggenerated
     */
    int insert(ExchangeCodeItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_exchange_code_item
     *
     * @mbggenerated
     */
    int insertSelective(ExchangeCodeItem record);
    
    
    List<ExchangeCodeItem> selectByGiftId(@Param("giftId") Integer giftId);
}