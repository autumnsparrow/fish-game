package com.toyo.fish.data.wrapper.system.persistence;

import org.apache.ibatis.annotations.Param;

import com.toyo.fish.data.wrapper.system.domain.ExchangeCode;
import com.toyo.fish.data.wrapper.system.domain.GeneralExchangeCode;

public interface ExchangeCodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_exchange_code
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_exchange_code
     *
     * @mbggenerated
     */
    int insert(ExchangeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_exchange_code
     *
     * @mbggenerated
     */
    int insertSelective(ExchangeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_exchange_code
     *
     * @mbggenerated
     */
    ExchangeCode selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_exchange_code
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ExchangeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_exchange_code
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ExchangeCode record);
    
    ExchangeCode selectOneByCode(@Param("code") String code);
}