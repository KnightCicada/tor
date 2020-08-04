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
public interface TestPacketDao {

    @Insert("insert into testPacket (packetName,packetPath,type,csvPath) values(#{packetName},#{packetPath},#{type},#{csvPath})")
    int insertPacket(Packet packet);

    @Select("select * from testPacket")
    List<Packet> findAllPacket();

    @Select("select * from testPacket ORDER BY id DESC")
    List<Packet> findAllPacketDesc();

    @Delete("delete from testPacket where id=#{id}")
    int deletePacket(Integer id);

    @Select("SELECT * FROM testPacket WHERE packetName like '%${value}%' OR type like '%${value}%'")
    List<Packet> findPacketByName(String packetName);

    @Select("SELECT * FROM testPacket WHERE packetName=#{packetName}")
    Packet findExactPacketByName(String packetName);

    @Select("SELECT * FROM testPacket WHERE type=#{type}")
    List<Packet> findPacketByType(String type);
}

