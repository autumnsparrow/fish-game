package com.toyo.fish.data.wrapper.system.persistence;

import org.apache.ibatis.annotations.Param;

import com.toyo.fish.data.wrapper.system.domain.UserExchangeCode;

public interface UserExchangeCodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_user_exchange_code
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_user_exchange_code
     *
     * @mbggenerated
     */
    int insert(UserExchangeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_user_exchange_code
     *
     * @mbggenerated
     */
    int insertSelective(UserExchangeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_user_exchange_code
     *
     * @mbggenerated
     */
    UserExchangeCode selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_user_exchange_code
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserExchangeCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_user_exchange_code
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserExchangeCode record);
    
    UserExchangeCode selectByGiftId(@Param("giftId") Integer giftId, @Param("userId") Long userId);


    int updateByGiftId(@Param("giftId") Integer giftId, @Param("userId") Long userId);






}