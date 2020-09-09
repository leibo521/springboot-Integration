package com.mybatismbg.testmybatismbg.mappers;

import com.mybatismbg.testmybatismbg.bean.Locations;
import com.mybatismbg.testmybatismbg.bean.LocationsExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LocationsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table locations
     *
     * @mbg.generated
     */
    long countByExample(LocationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table locations
     *
     * @mbg.generated
     */
    int deleteByExample(LocationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table locations
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer locationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table locations
     *
     * @mbg.generated
     */
    int insert(Locations record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table locations
     *
     * @mbg.generated
     */
    int insertSelective(Locations record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table locations
     *
     * @mbg.generated
     */
    List<Locations> selectByExample(LocationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table locations
     *
     * @mbg.generated
     */
    Locations selectByPrimaryKey(Integer locationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table locations
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Locations record, @Param("example") LocationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table locations
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Locations record, @Param("example") LocationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table locations
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Locations record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table locations
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Locations record);
}