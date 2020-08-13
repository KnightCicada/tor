package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.domain.Flow;
import com.tor.domain.Model;
import com.tor.domain.MultiNum;
import com.tor.domain.Packet;
import com.tor.result.CodeMsg;
import com.tor.result.Const;
import com.tor.result.Result;
import com.tor.service.ModelService;
import com.tor.service.PacketService;
import com.tor.service.TestService;
import com.tor.util.PropertiesUtil;
import com.tor.util.ProtocolLabel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/multiDir")
@Slf4j
public class MultiDirController {

    @Autowired
    TestService testService;
    @Autowired
    private PacketService packetService;
    @Autowired
    private ModelService modelService;

    private Model model = new Model();

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(ModelMap map, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn, @RequestParam(required = false, defaultValue = "1", value = "pn1") Integer pn1) {
        List<Packet> packetList = new LinkedList<>();

        PageHelper.startPage(pn1, 6);
        PageHelper.startPage(pn, 6);
        packetList = packetService.findAllTestPacket();
        map.addAttribute("packetList", packetList);
        PageInfo<Packet> packetPage = new PageInfo<>(packetList);
        map.addAttribute("packetPage", packetPage);
        return Const.MULTI_DIR_PAGE;
    }


    @RequestMapping(value = "/multiResultDir", method = RequestMethod.GET)
    public String testMultiDir(@RequestParam("testFile") String testCsvPath, ModelMap modelMap) throws Exception {
        if (testCsvPath == null) {
            modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
            return Const.TEST_RESULT_MULTI_PAGE;
        }
        //对testCsvPath进行处理，得到测试文件名字
        String testFileName = testCsvPath.substring(testCsvPath.lastIndexOf("/")).replace("/", "");
        //选取提供的多分类模型
        model = modelService.findExactModelByName("multiRandomForest.model");

        if (model == null) {
            modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
            return Const.TEST_RESULT_MULTI_PAGE;
        } else {
            String modelPath = model.getModelPath();//.model
            String featurePath = model.getFeaturePath();//Feature.txt

            //调用测试算法，得到一个表，表示测试结果。
            List<Flow> resultList = testService.getModelClassifyListMulti(testFileName, testCsvPath, modelPath, featurePath);
            MultiNum multiNum = ProtocolLabel.protocolAndMultiNum(resultList);

            modelMap.addAttribute("total", resultList.size());
            modelMap.addAttribute("video", multiNum.getVideo());
            modelMap.addAttribute("mail", multiNum.getMail());
            modelMap.addAttribute("browsing", multiNum.getBrowsing());
            modelMap.addAttribute("audio", multiNum.getAudio());
            modelMap.addAttribute("resultList", resultList);

            return Const.TEST_RESULT_MULTI_PAGE;
        }
    }
}
