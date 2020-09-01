package com.tor.controller;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.tor.domain.Bridge;
import com.tor.result.Const;
import com.tor.result.Result;
import com.tor.service.BridgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class BridgeController {
    @Autowired
    private BridgeService bridgeService;

    @RequestMapping("/bridge")
    public String bridge(ModelMap modelMap) {
        //TODO 处理amcharts格式
        List<Bridge> bridgeList = bridgeService.selectBridges();
        modelMap.addAttribute("result", Result.success(bridgeList));
        modelMap.addAttribute("total", bridgeList.size());
        return Const.BRIDGE_PAGE;
    }

    @RequestMapping("/bridge_table")
    @ResponseBody
    public List<Bridge> bridgeCharts() {
        return bridgeService.selectBridges();
    }


    @RequestMapping("/bridge_test")
    @ResponseBody
    public String bridgeCharts1() throws JSchException, SftpException, IOException {
        bridgeService.getBridgesFromRemote();
        return "ssss";
    }


}
