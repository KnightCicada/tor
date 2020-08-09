package com.tor.service;

import com.tor.dao.PacketDao;
import com.tor.domain.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacketService {
    @Autowired
    private PacketDao packetDao;

    public int insertPacket(Packet packet) {
        return packetDao.insertPacket(packet);
    }

    public List<Packet> findAllPacket() {
        return packetDao.findAllPacketDesc();
    }

    public List<Packet> findAllPacketDesc() {
        return packetDao.findAllPacketDesc();
    }

    public int deletePacket(Integer id) {
        return packetDao.deletePacket(id);
    }

    public List<Packet> findPacketByKeyword(String word) {
        return packetDao.findPacketByKeyword(word);
    }

    public Packet findPacketByExactPath(String path) {
        return packetDao.findPacketByExactPath(path);
    }

    public List<Packet> findPacketByType(String type) {
        return packetDao.findPacketByType(type);
    }

    public List<Packet> findAllTestPacket() {
        return packetDao.findAllTestPacket();
    }

    public List<Packet> findAllTestPacketDesc() {
        return packetDao.findAllTestPacketDesc();
    }

    public int deleteTestPacket(Integer id) {
        return packetDao.deleteTestPacket(id);
    }

    public List<Packet> findTestPacketByName(String packetName) {
        return packetDao.findTestPacketByName(packetName);
    }

    public List<Packet> findAllTrainPacket() {
        return packetDao.findAllTrainPacket();
    }

    public boolean getPacketByMd5(String md5) {
        Packet packet = packetDao.getPacketByMd5(md5);
        return !(packet == null);
    }

    public Packet findPacketById(Integer id) {
        return packetDao.findPacketById(id);
    }

    public boolean updateType(Packet packet) {
        return packetDao.updateType(packet);
    }
}