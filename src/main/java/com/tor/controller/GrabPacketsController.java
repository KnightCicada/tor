package com.tor.controller;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.tor.service.GrabPacketsService;
import com.tor.util.LabelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
public class GrabPacketsController {

    @Autowired
    private GrabPacketsService grabPacketsService;


    @RequestMapping(value = "/index")
    public String index(ModelMap modelMap){
        return "classify";
    }

    @RequestMapping(value = "/grab")
    public String grab(ModelMap modelMap){
        return "grabpacket";
    }

    @RequestMapping(value = "/grab_packets")
    public String grabPacket(ModelMap modelMap, @RequestParam(value = "grabPlace", required = false, defaultValue = "server") String grabPlace,
                             @RequestParam(value = "command", required = false, defaultValue = "ifconfig") String command,
                             @RequestParam(value = "packetCount", required = false, defaultValue = "5") int packetCount,
                             @RequestParam(value = "protocol", required = false, defaultValue = "ip") String protocol,
                             @RequestParam(value = "selectWay", required = false, defaultValue = "0") int selectWay,
                             HttpServletResponse response) throws InterruptedException, JSchException, SftpException, IOException {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd+HH-mm-ss");
        String time = formatter.format(date);

        String fileName = "";
        if (grabPlace.equals("local")) {
            fileName = "localPcap_" + time + ".pcap";
        } else {
            fileName = "serverPcap_" + time + ".pcap";
        }
        log.info("grab_packets:参数为：grabPlace：{}，command：{}，packetCount:{},protocol:{},selectWay:{}", grabPlace, command, packetCount, protocol, selectWay);
        grabPacketsService.grabPackets(fileName, grabPlace, command, packetCount, protocol, selectWay);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("<script>alert('正在抓包，抓包成功之后将存入数据库，数据包名称为：" + fileName + "！网页将跳转到抓包页面');  window.location='index';</script>");
        response.getWriter().flush();
        return "grabpacket";
    }


    public static void main(String[] args) throws InterruptedException {
//        if (ISCXFlowMeter.singlePcap("/home/zyan/AW/data/pcap/remote/" + "serverPcap_2020-08-09+11-11-49.pcap", "/home/zyan/AW/data/pcap/remote/")) {
//            log.info("grabPackets：数据包转换成功");
//        } else {
//            log.info("grabPackets：数据包转换失败");
//        }
//        Thread.sleep(2000);
        LabelUtil.singleCsvLabel("/home/zyan/AW/data/pcap/remote/ISCX_serverPcap_2020-08-09+11-11-49.pcap.csv", "train"); //训练集打标签
    }

}

