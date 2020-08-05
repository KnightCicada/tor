package com.tor.service;

import com.tor.dao.PacketDao;
import com.tor.domain.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacketService {
    @Autowired
    private PacketDao trainPacketDao;

    public int insertPacket(Packet packet) {
        return trainPacketDao.insertPacket(packet);
    }

    public List<Packet> findAllPacket() {
        return trainPacketDao.findAllPacket();
    }

    public List<Packet> findAllPacketDesc() {
        return trainPacketDao.findAllPacketDesc();
    }

    public int deletePacket(Integer id) {
        return trainPacketDao.deletePacket(id);
    }

    public List<Packet> findPacketByName(String word) {
        return trainPacketDao.findPacketByName(word);
    }


    public List<Packet> findPacketByType(String type) {
        return trainPacketDao.findPacketByType(type);
    }

    public List<Packet> findAllTestPacket() {
        return trainPacketDao.findAllTestPacket();
    }

    public List<Packet> findAllTestPacketDesc() {
        return trainPacketDao.findAllTestPacketDesc();
    }

    public int deleteTestPacket(Integer id) {
        return trainPacketDao.deleteTestPacket(id);
    }

    public List<Packet> findTestPacketByName(String packetName) {
        return trainPacketDao.findTestPacketByName(packetName);
    }

    public List<Packet> findAllTrainPacket() {
        return trainPacketDao.findAllTrainPacket();
    }
}