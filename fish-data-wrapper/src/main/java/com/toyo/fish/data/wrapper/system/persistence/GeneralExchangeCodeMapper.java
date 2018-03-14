package com.toyo.fish.data.wrapper.system.persistence;

import org.apache.ibatis.annotations.Param;

import com.toyo.fish.data.wrapper.system.domain.GeneralExchangeCode;

public interface GeneralExchangeCodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_general_exchange_code
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_general_exchange_code
     *
     * @mbggenerated
     */
    int insert(GeneralExchangeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_general_exchange_code
     *
     * @mbggenerated
     */
    int insertSelective(GeneralExchangeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_general_exchange_code
     *
     * @mbggenerated
     */
    GeneralExchangeCode selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_general_exchange_code
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(GeneralExchangeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_general_exchange_code
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(GeneralExchangeCode record);
    
    GeneralExchangeCode selectOneByCode(@Param("code") String code);
}