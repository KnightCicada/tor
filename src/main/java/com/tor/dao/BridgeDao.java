package com.tor.dao;

import com.tor.domain.Bridge;
import com.tor.vo.CountryCnt;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BridgeDao {

    @Insert({"<script> " +
            "insert into bridge(ip, country, country_code, city, longitude, latitude, create_time) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> " +
            "(#{item.ip},#{item.country},#{item.countryCode},#{item.city},#{item.longitude},#{item.latitude},#{item.createTime})" +
            "</foreach> " +
            "</script>"})
    Integer insertBridges(List<Bridge> list);

    @Insert("insert into bridge(ip, country, country_code, city, longitude, latitude) " +
            "values(#{ip},#{country},#{countryCode},#{city},#{longitude},#{latitude})")
    int insert(Bridge bridge);

    @Select("select * from bridge order by id desc")
    @Results({
            @Result(property = "countryCode", column = "country_code"),
            @Result(property = "createTime", column = "create_time")
    })
    List<Bridge> selectBridges();

    @Select("select count(*) as cnt,country_code as countryCode from bridge GROUP BY country_code")
    List<CountryCnt> findByCountry();
}
