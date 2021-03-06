package com.sky.mobile.protocol.persistence;

import com.sky.mobile.protocol.domain.YoukuNotify;

public interface YoukuNotifyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_youku_notify
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_youku_notify
     *
     * @mbggenerated
     */
    int insert(YoukuNotify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_youku_notify
     *
     * @mbggenerated
     */
    int insertSelective(YoukuNotify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_youku_notify
     *
     * @mbggenerated
     */
    YoukuNotify selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_youku_notify
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(YoukuNotify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_youku_notify
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(YoukuNotify record);
}