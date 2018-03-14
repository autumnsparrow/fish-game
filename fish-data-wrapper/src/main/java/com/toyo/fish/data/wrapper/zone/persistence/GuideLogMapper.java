package com.toyo.fish.data.wrapper.zone.persistence;

import org.apache.ibatis.annotations.Param;

import com.toyo.fish.data.wrapper.zone.domain.GuideLog;

public interface GuideLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_guide_log
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);
    
    int deleteByUserId(@Param("userId") Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_guide_log
     *
     * @mbggenerated
     */
    int insert(GuideLog record);
    
    
    int insertBySelectUserId(@Param("userId") Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_guide_log
     *
     * @mbggenerated
     */
    int insertSelective(GuideLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_guide_log
     *
     * @mbggenerated
     */
    GuideLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_guide_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(GuideLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_guide_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(GuideLog record);
}