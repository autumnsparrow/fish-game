package com.toyo.fish.data.wrapper.persistence;

import com.toyo.fish.data.wrapper.domain.Channel;

public interface ChannelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_channel
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_channel
     *
     * @mbggenerated
     */
    int insert(Channel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_channel
     *
     * @mbggenerated
     */
    int insertSelective(Channel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_channel
     *
     * @mbggenerated
     */
    Channel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_channel
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Channel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_channel
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Channel record);
}