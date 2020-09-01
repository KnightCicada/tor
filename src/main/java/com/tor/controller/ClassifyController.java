package com.tor.controller;

import com.tor.domain.Flow;
import com.tor.domain.Model;
import com.tor.domain.MultiNum;
import com.tor.result.CodeMsg;
import com.tor.result.Const;
import com.tor.result.Result;
import com.tor.service.ClassifyService;
import com.tor.service.ModelService;
import com.tor.service.TestService;
import com.tor.util.CsvUtil;
import com.tor.util.PropertiesUtil;
import com.tor.util.ProtocolLabel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/classify")
    public String classify(ModelMap modelMap) {
        modelMap.addAttribute("show_table", false);
        return Const.CLASSIFY_PAGE;
    }


    @RequestMapping(value = "/classify_pcap")
    public String classifyPcap(ModelMap modelMap, @RequestParam("file") MultipartFile file) {
        System.out.println("文件上传1");
        Result<List<Flow>> result = null;
        try {
            if (file.isEmpty()) {
                modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
                return Const.CLASSIFY_PAGE;
            }
            String filePcapName = file.getOriginalFilename();
            String suffixName = filePcapName.substring(filePcapName.lastIndexOf("."));
            if (!suffixName.equals(".pcap")) {
                modelMap.addAttribute("result", Result.error(CodeMsg.INVIVAD_FILE));
                modelMap.addAttribute("show_table", false);
                return Const.CLASSIFY_PAGE;
            }
            //path为要保存的pcap地址拼接原始fileName
            String fullPcapName = PropertiesUtil.getPcapPath() + filePcapName;
            File fullPcapFile = new File(fullPcapName);
            //检测是否存在目标
            String isExistFile = PropertiesUtil.getPcapPath() + filePcapName;
            List<Flow> list = null;
            if (!new File(isExistFile).exists()) {
                file.transferTo(fullPcapFile);
                result = classifyService.getClassifyResult(fullPcapName, filePcapName);
            } else {
                list = classifyService.getFlowListFromFile(filePcapName);
                result = Result.success(list);
            }

            List<Flow> lastList;

            if (result == null || result.getData() == null) {
                lastList = list;
            } else {
                lastList = result.getData();
            }
            int torSize = 0;
            if (result == null) {
                modelMap.addAttribute("result", Result.error(CodeMsg.SERVER_ERROR));
                modelMap.addAttribute("show_table", false);
                return Const.CLASSIFY_PAGE;
            }

            if (result.getCode() == 1) {
                List<Flow> flowList = result.getData();
                torSize = ProtocolLabel.protocolAndTorNum(flowList);
            }

            Model model = modelService.findExactModelByName("multiRandomForest.model");
            String multimodelPath = model.getModelPath();//.model
            String multiFeaturePath = model.getFeaturePath();//Feature.txt
            String testFileName = filePcapName + ".csv";
            String multiFileName = "multiTmp" + testFileName;
            String multiFilePath = PropertiesUtil.getPcapCsvPath() + multiFileName;
            CsvUtil.save(lastList, testFileName);
            //调用测试算法，得到一个表，表示测试结果。
            List<Flow> multiResultList = testService.getModelClassifyListMulti(multiFileName, multiFilePath, multimodelPath, multiFeaturePath);
            MultiNum multiNum = ProtocolLabel.protocolAndMultiNum(multiResultList);

            modelMap.addAttribute("MultiTotal", multiResultList.size());
            modelMap.addAttribute("video", multiNum.getVideo());
            modelMap.addAttribute("mail", multiNum.getMail());
            modelMap.addAttribute("browsing", multiNum.getBrowsing());
            modelMap.addAttribute("audio", multiNum.getAudio());
            modelMap.addAttribute("other", multiNum.getOther());
            modelMap.addAttribute("multiResultList", multiResultList);

            System.out.println("result.size: " + result.getData().size());
            System.out.println("tor.size: " + torSize);
            modelMap.addAttribute("total", result.getData().size());
            modelMap.addAttribute("tor_size", torSize);
            modelMap.addAttribute("result", result);
        } catch (IllegalStateException | IOException e) {
            log.error(e.toString());
        }
        System.out.println("文件上传2");
        modelMap.addAttribute("show_table", true);
        return Const.CLASSIFY_PAGE;
    }
}
