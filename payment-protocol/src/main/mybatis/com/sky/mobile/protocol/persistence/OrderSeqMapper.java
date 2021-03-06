package com.sky.mobile.protocol.persistence;

import com.sky.mobile.protocol.domain.OrderSeq;

public interface OrderSeqMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_sequence
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_sequence
     *
     * @mbggenerated
     */
    int insert(OrderSeq record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_sequence
     *
     * @mbggenerated
     */
    int insertSelective(OrderSeq record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_sequence
     *
     * @mbggenerated
     */
    OrderSeq selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_sequence
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OrderSeq record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_sequence
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OrderSeq record);
    
    
    int updateSeqByPrimaryKey(Integer id);
}