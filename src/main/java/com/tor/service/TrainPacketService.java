package com.tor.service;

import com.tor.dao.TrainPacketDao;
import com.tor.domain.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainPacketService {
    @Autowired
    private TrainPacketDao trainPacketDao;

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

    public List<Packet> findPacketByName(String packetName) {
        return trainPacketDao.findPacketByName(packetName);
    }

    public Packet findExactPacketByName(String packetName) {
        return trainPacketDao.findExactPacketByName(packetName);
    }

    public List<Packet> findPacketByType(String type) {
        return trainPacketDao.findPacketByType(type);
    }
}