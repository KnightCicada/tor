package com.tor.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.tor.dao.BridgeDao;
import com.tor.domain.Bridge;
import com.tor.domain.IPEntity;
import com.tor.util.DownLoadUtil;
import com.tor.util.IPUtil;
import com.tor.util.PropertiesUtil;
import com.tor.vo.CountryCnt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class BridgeService {

    @Autowired
    private BridgeDao bridgeDao;

    public Integer insertBridges(List<Bridge> list) {
        return bridgeDao.insertBridges(list);
    }

    public List<Bridge> selectBridges() {
        return bridgeDao.selectBridges();
    }

    public List<CountryCnt> findByCountry() {
        return bridgeDao.findByCountry();
    }


    @Async("taskExecutor")
    @Scheduled(cron = "0 0/5 * * * ?  ")
    public void getBridgesFromRemote() throws JSchException, SftpException, IOException {
        String remoteDir = PropertiesUtil.getRemoteFilePath();
        String bridge = "bridge.txt";
        String localDir = PropertiesUtil.getLocalFilePath();
        DownLoadUtil.downloadFromRemote(remoteDir, bridge, localDir);
        List<String> bridgeIPList = getBridgeIPFromFile(localDir + bridge);
        List<Bridge> bridges = new ArrayList<>();
        List<Bridge> oldBridges = selectBridges();
        Set<String> stringSet = new HashSet<>();
        if (oldBridges != null) {
            for (Bridge oldBridge : oldBridges) {
                stringSet.add(oldBridge.getIp());
            }
        }

        for (String s : bridgeIPList) {
            if (!stringSet.contains(s)) {
                try {
                    IPEntity ipMsg = IPUtil.getIPMsg(s);
                    Bridge bridge1 = new Bridge();
                    bridge1.setIp(s);
                    bridge1.setCity(ipMsg.getCityName());
                    bridge1.setCountry(ipMsg.getCountryName());
                    bridge1.setCountryCode(ipMsg.getCountryCode());
                    bridge1.setLatitude(ipMsg.getLatitude());
                    bridge1.setLongitude(ipMsg.getLongitude());
                    bridge1.setCreateTime(new Date());
                    bridges.add(bridge1);
                } catch (Exception e) {
                    log.error("网桥IP转换错误：{}", s);
                }
            }
        }

        if (bridges.size() != 0) {
            Integer integer = insertBridges(bridges);
            if (integer != bridges.size()) {
                log.error("网桥插入数据库遇到错误");
            } else {
                log.info("插入了{}条网桥数据", integer);
            }
        }

    }

    public List<String> getBridgeIPFromFile(String fullFile) {
        List<String> list = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fullFile)));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                list.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) throws JSchException, SftpException, IOException {
        new BridgeService().getBridgesFromRemote();
    }

}
