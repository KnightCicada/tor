package com.tor.dao;

import com.tor.domain.Packet;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface TrainPacketDao {

    @Insert("insert into packet (packetName,packetPath,type,csvPath) values(#{packetName},#{packetPath},#{type},#{csvPath})")
    int insertPacket(Packet packet);

    @Select("select * from packet")
    List<Packet> findAllPacket();

    @Select("select * from packet ORDER BY id DESC")
    List<Packet> findAllPacketDesc();

    @Delete("delete from packet where id=#{id}")
    int deletePacket(Integer id);

    @Select("SELECT * FROM packet WHERE packetName like '%${value}%' OR type like '%${value}%'")
    List<Packet> findPacketByName(String packetName);

    @Select("SELECT * FROM packet WHERE packetName=#{packetName}")
    Packet findExactPacketByName(String packetName);

    @Select("SELECT * FROM packet WHERE type=#{type}")
    List<Packet> findPacketByType(String type);
}

