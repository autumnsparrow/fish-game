package com.sky.mobile.protocol.domain;

public class OrderSeq {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_sequence.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_sequence.seq
     *
     * @mbggenerated
     */
    private Long seq;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_sequence.id
     *
     * @return the value of tbl_sequence.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_sequence.id
     *
     * @param id the value for tbl_sequence.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_sequence.seq
     *
     * @return the value of tbl_sequence.seq
     *
     * @mbggenerated
     */
    public Long getSeq() {
        return seq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_sequence.seq
     *
     * @param seq the value for tbl_sequence.seq
     *
     * @mbggenerated
     */
    public void setSeq(Long seq) {
        this.seq = seq;
    }
}