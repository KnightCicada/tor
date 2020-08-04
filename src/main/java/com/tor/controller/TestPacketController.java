package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.domain.Packet;
import com.tor.result.CodeMsg;
import com.tor.result.Const;
import com.tor.result.Result;
import com.tor.service.TestPacketService;
import com.tor.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "/testPacket")
public class TestPacketController {
    @Autowired
    private TestPacketService testPacketService;

    @RequestMapping(value = "/findAllPacket", method = RequestMethod.POST)
    public String findAllPacket(ModelMap modelMap) {
        List<Packet> res = testPacketService.findAllPacket();
        if (res == null) {
            modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
            return Const.TEST_PACKET_PAGE;
        } else {
            List<Packet> packetList = testPacketService.findAllPacket();
            PageInfo<Packet> pageList = new PageInfo<>(packetList);
            modelMap.addAttribute("data", packetList);
            modelMap.addAttribute("page", pageList);
            return Const.TEST_PACKET_PAGE;
        }
    }

    //根据名字对数据包进行模糊查询
    @RequestMapping(value = "/findPacketByName", method = RequestMethod.POST)
    public String findPacketByName(@RequestParam("packetName") String packetName, ModelMap modelMap) {
        List<Packet> res = testPacketService.findPacketByName(packetName);
        if (res == null) {
            modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
            return Const.PACKET_PAGE;
        } else {
            List<Packet> packetList = res;
            PageInfo<Packet> pageList = new PageInfo<>(packetList);
            modelMap.addAttribute("data", packetList);
            modelMap.addAttribute("page", pageList);
            return Const.PACKET_PAGE;
        }
    }

    //根据类型对数据包进行模糊查询
    @RequestMapping(value = "/findPacketByType", method = RequestMethod.POST)
    public String findPacketByType(@RequestParam("type") String type, ModelMap modelMap) {
        List<Packet> res = testPacketService.findPacketByType(type);
        if (res == null) {
            modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
            return Const.TEST_PACKET_PAGE;
        } else {
            List<Packet> packetList = res;
            PageInfo<Packet> pageList = new PageInfo<>(packetList);
            modelMap.addAttribute("data", packetList);
            modelMap.addAttribute("page", pageList);
            return Const.TEST_PACKET_PAGE;
        }
    }

    //todo 删除文件之后的页面
    //删除文件
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deletePacket(@RequestParam(value = "id") Integer id, ModelMap modelMap) {
        testPacketService.deletePacket(id);
        List<Packet> resList = testPacketService.findAllPacket();
        PageInfo<Packet> pageList = new PageInfo<>(resList);
        modelMap.addAttribute("data", resList);
        modelMap.addAttribute("page", pageList);
        return Const.TEST_PACKET_PAGE;
    }

    //分页对数据包进行查询
    @RequestMapping(method = RequestMethod.GET)
    public String getPacketList(ModelMap modelMap, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn) {
        PageHelper.startPage(pn, 6);
        List<Packet> packetList = testPacketService.findAllPacket();
        PageInfo<Packet> pageList = new PageInfo<>(packetList);
        modelMap.addAttribute("data", packetList);
        modelMap.addAttribute("page", pageList);
        return Const.PACKET_PAGE;
    }


    @RequestMapping(value = "/addPacket")
    public String addPacket(ModelMap modelMap, @RequestParam("file") MultipartFile file, @RequestParam("packet") String type) throws Exception {
        try {
            if (file.isEmpty()) {
                modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
                return Const.TEST_PACKET_PAGE;
            }
            String filePcapName = file.getOriginalFilename();
            String suffixName = filePcapName.substring(filePcapName.lastIndexOf("."));
            if (!".pcap".equals(suffixName)) {
                modelMap.addAttribute("result", Result.error(CodeMsg.INVIVAD_FILE));
                return Const.TEST_PACKET_PAGE;
            }
            //path为要保存的pcap地址拼接原始fileName
            String fullPcapName = PropertiesUtil.getPcapPath() + filePcapName;
            File fullPcapFile = new File(fullPcapName);
            //检测是否存在目标
            if (!fullPcapFile.getParentFile().exists()) {
                fullPcapFile.getParentFile().mkdirs();
            }
            //TODO pacp转换为csv文件 去掉.pcap，以.csv结尾
            if (!fullPcapFile.exists()) {
                Packet packet = new Packet();
                packet.setPacketName(filePcapName);
                packet.setPacketPath(fullPcapName);
                packet.setType(type);
                packet.setCsvPath(PropertiesUtil.getPcapCsvPath() + filePcapName);
                testPacketService.insertPacket(packet);
                file.transferTo(fullPcapFile);
            } else {

            }

        } catch (Exception e) {
            log.error(e.toString());
        }
        //加入数据包之后，显示现有数据包
        List<Packet> packetList = testPacketService.findAllPacketDesc();
        PageInfo<Packet> pageList = new PageInfo<>(packetList);
        modelMap.addAttribute("data", packetList);
        modelMap.addAttribute("page", pageList);
        return Const.TEST_PACKET_PAGE;
    }
}
