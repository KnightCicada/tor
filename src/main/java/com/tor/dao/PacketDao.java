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
public interface PacketDao {

    @Insert("insert into packet (packetName,packetPath,type,csvPath,md5) values(#{packetName},#{packetPath},#{type},#{csvPath},#{md5})")
    int insertPacket(Packet packet);

    /*
        find
     */
    @Select("select * from packet")
    List<Packet> findAllPacket();

    @Select("select * from packet where type='train' ")
    List<Packet> findAllTrainPacket();

    @Select("select * from packet ORDER BY id DESC")
    List<Packet> findAllPacketDesc();

    @Select("SELECT * FROM packet WHERE (packetName like '%${value}%') OR (type like '%${value}%')")
    List<Packet> findPacketByName(String word);

    @Select("SELECT * FROM packet WHERE type=#{type}")
    List<Packet> findPacketByType(String type);


    @Delete("delete from packet where id=#{id}")
    int deletePacket(Integer id);


    /*
        test packet
     */
    @Select("select * from packet where type='test' ")
    List<Packet> findAllTestPacket();

    @Select("select * from packet  where type='test' ORDER BY id DESC")
    List<Packet> findAllTestPacketDesc();

    @Delete("delete from packet where type='test' and id=#{id}")
    int deleteTestPacket(Integer id);

    @Select("SELECT * FROM packet WHERE packetName like '%${value}%' and type = 'test'")
    List<Packet> findTestPacketByName(String packetName);

    /*
        md5
     */
    @Select("select * from packet where md5 = 'md5' ")
    Packet getPacketByMd5(String md5);
}

