package com.sky.mobile.protocol.persistence;

import com.sky.mobile.protocol.domain.KuPaiNotify;

public interface KuPaiNotifyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_kupai_notify
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_kupai_notify
     *
     * @mbggenerated
     */
    int insert(KuPaiNotify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_kupai_notify
     *
     * @mbggenerated
     */
    int insertSelective(KuPaiNotify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_kupai_notify
     *
     * @mbggenerated
     */
    KuPaiNotify selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_kupai_notify
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(KuPaiNotify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_kupai_notify
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(KuPaiNotify record);
}