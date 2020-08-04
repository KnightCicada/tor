package com.tor.dao;

import com.tor.domain.Relay;
import com.tor.vo.CountryCnt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RelayDao {
    @Select("select * from init")
    List<Relay> findAll();

    @Select("select count(*) as cnt,country_code as countryCode from init GROUP BY country_code")
    List<CountryCnt> findByCountry();

    @Select("select * from init ORDER BY bandwidth DESC LIMIT 10")
    List<Relay> findTop10();
}
