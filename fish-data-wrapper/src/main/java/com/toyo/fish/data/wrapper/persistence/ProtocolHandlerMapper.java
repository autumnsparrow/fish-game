package com.toyo.fish.data.wrapper.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.toyo.fish.data.wrapper.domain.ProtocolHandler;

public interface ProtocolHandlerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_protocol_handler
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_protocol_handler
     *
     * @mbggenerated
     */
    int insert(ProtocolHandler record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_protocol_handler
     *
     * @mbggenerated
     */
    int insertSelective(ProtocolHandler record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_protocol_handler
     *
     * @mbggenerated
     */
    ProtocolHandler selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_protocol_handler
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ProtocolHandler record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ty_protocol_handler
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ProtocolHandler record);
    
//    /**
//     * 
//     * @param id
//     * @return
//     */
//    List<ProtocolHandler> selectByHandleNames(@Param("handleNames") List<String> handleNames);
    
    ProtocolHandler  selectByHandleName(@Param("handleName") String handleName);
    
}