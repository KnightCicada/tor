package com.tor.controller;

import com.tor.domain.Flow;
import com.tor.result.CodeMsg;
import com.tor.result.Result;
import com.tor.service.ClassifyService;
import com.tor.util.PropertiesUtil;
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

    @RequestMapping(value = "/classify")
    public String classify(ModelMap modelMap) {
        modelMap.addAttribute("show_table", false);
        return "classify";
    }


    @RequestMapping(value = "/classify_pcap")
    public String classifyPcap(ModelMap modelMap, @RequestParam("file") MultipartFile file) {
        System.out.println("文件上传1");
        Result<List<Flow>> result = null;
        try {
            if (file.isEmpty()) {
                modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
                return "classify";
            }
            String filePcapName = file.getOriginalFilename();
            //TODO 文件名
            String suffixName = filePcapName.substring(filePcapName.lastIndexOf("."));
            if (!suffixName.equals(".pcap")) {
                modelMap.addAttribute("result", Result.error(CodeMsg.INVIVAD_FILE));
                return "classify";
            }
            //path为要保存的pcap地址拼接原始fileName
            String fullPcapName = PropertiesUtil.getPcapPath() + filePcapName;
            File fullPcapFile = new File(fullPcapName);
            //检测是否存在目标
            if (!fullPcapFile.getParentFile().exists()) {
                fullPcapFile.getParentFile().mkdirs();
            }
            //TODO 去数据库中查找，若重复，则直接返回结果
            if (!fullPcapFile.exists()) {
                file.transferTo(fullPcapFile);
                result = classifyService.getClassifyResult(fullPcapName, filePcapName);
            } else {
                List<Flow> list = classifyService.getFlowListFromDB(filePcapName);
                result = Result.success(list);
            }
            int torSize = 0;
            if (result.getCode() == 1) {
                List<Flow> flowList = result.getData();
                for (Flow flow : flowList) {
                    if (flow.getLabel().equals("TOR")) {
                        torSize++;
                    }
                }
            }
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
        return "classify";
    }
}
