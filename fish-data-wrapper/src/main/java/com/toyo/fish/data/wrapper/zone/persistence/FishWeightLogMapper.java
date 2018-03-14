package com.toyo.fish.data.wrapper.zone.persistence;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.toyo.fish.data.wrapper.zone.domain.FishWeightLog;


public interface FishWeightLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_fish_weight_log
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);
    
    
    /**
     * 
     * @param userId
     * @return
     */
    int deleteByUserId(@Param("userId") Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_fish_weight_log
     *
     * @mbggenerated
     */
    int insert(FishWeightLog record);
    
    
    
    int insertBySelectUserId(@Param("userId") Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_fish_weight_log
     *
     * @mbggenerated
     */
    int insertSelective(FishWeightLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_fish_weight_log
     *
     * @mbggenerated
     */
    FishWeightLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_fish_weight_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FishWeightLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_fish_weight_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FishWeightLog record);
    
    
    List<FishWeightLog> selectByFishWeightSort(@Param("fishId") String fishId);
    
    List<FishWeightLog>    selectByFishWeightOfFriendsSort(@Param("fishId") String fishId,@Param("userIds") List<Long> userIds);
    
    int selectCountOfFishId(@Param("fishId") String fishId);
    int selectOffsetOfFishId(@Param("userId") Long userId,@Param("fishId") String fishId);
    
    Float selectWeightOfTenThousands(@Param("fishId") String fishId);
    Float selectWeightOfUser(@Param("userId") Long userId,@Param("fishId") String fishId);
    
    /**
     * valid the weight of fishId
     * @param fishId
     * @param weight
     * @return
     */
    int findFishWeightInRange(@Param("fishId") String fishId,@Param("weight") Float weight);
 
    /**
     * find the weight of user 
     * 
     * @param userId
     * @param fishId
     * @return
     */
    int findFishWeightInUser(@Param("userId") Long userId,@Param("fishId") String fishId);
    
    
    FishWeightLog findFishWeightByUserIdAndFishId(@Param("userId") Long userId,@Param("fishId") String fishId);
    
   
    
    
    
}