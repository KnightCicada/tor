package com.tor.service;


import com.csvreader.CsvReader;
import com.tor.domain.IPVO;
import com.tor.domain.Packet;
import com.tor.domain.SourceAndDesIPVO;
import com.tor.util.RandomColorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TraceService {

    @Autowired
    private TrainPacketService packetService;

    public Set<SourceAndDesIPVO> getIPs() throws IOException {
        List<Packet> list = packetService.findAllPacket();
        Set<SourceAndDesIPVO> sourceAndDesIPVOS = new HashSet<>();
        for (Packet packet : list) {
            String csvName = packet.getCsvPath();
            if (csvName == null) {
                continue;
            }
            if (!csvName.endsWith("csv")) {
                continue;
            }
            File file = new File(csvName);
            if (!file.exists()) {
                continue;
            }
            List<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(csvName, ',', StandardCharsets.UTF_8);
            reader.readHeaders();//跳过表头。
            while (reader.readRecord()) {
                csvList.add(reader.getValues());
            }
            reader.close();//csvList中是除去表头的一个测试文件的全部内容。

            for (String[] strings : csvList) {
                if (strings[strings.length - 1].equals("TOR")) {
                    String sourceIP = strings[0];
                    String desIP = strings[2];
                    SourceAndDesIPVO sourceAndDesIPVO = new SourceAndDesIPVO();
                    sourceAndDesIPVO.setSource(sourceIP);
                    sourceAndDesIPVO.setTarget(desIP);
                    sourceAndDesIPVOS.add(sourceAndDesIPVO);
                }
            }
        }
        return sourceAndDesIPVOS;
    }

    public List<IPVO> getIPVO(Set<SourceAndDesIPVO> set) {
        List<IPVO> list = new ArrayList<>();
        Set<String> strings = new HashSet<>();
        for (SourceAndDesIPVO sourceAndDesIPVO : set) {
            String source = sourceAndDesIPVO.getSource();
            String dest = sourceAndDesIPVO.getTarget();
            strings.add(source);
            strings.add(dest);
        }
        for (String str : strings) {
            IPVO ipvo = new IPVO();
            ipvo.setName(str);
            ipvo.setColor(RandomColorUtils.generateColor());//TODO 随机颜色
            ipvo.setSymbolSize(10);
            list.add(ipvo);
        }
        return list;
    }
}
