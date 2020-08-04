package com.tor.service;

import com.tor.dao.TestPacketDao;
import com.tor.domain.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPacketService {
    @Autowired
    private TestPacketDao testPacketDao;

    public int insertPacket(Packet packet) {
        return testPacketDao.insertPacket(packet);
    }

    public List<Packet> findAllPacket() {
        return testPacketDao.findAllPacket();
    }

    public List<Packet> findAllPacketDesc() {
        return testPacketDao.findAllPacketDesc();
    }

    public int deletePacket(Integer id) {
        return testPacketDao.deletePacket(id);
    }

    public List<Packet> findPacketByName(String packetName) {
        return testPacketDao.findPacketByName(packetName);
    }

    public Packet findExactPacketByName(String packetName) {
        return testPacketDao.findExactPacketByName(packetName);
    }

    public List<Packet> findPacketByType(String type) {
        return testPacketDao.findPacketByType(type);
    }
}