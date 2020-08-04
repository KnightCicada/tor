package com.tor.controller;


import com.tor.domain.Bridge;
import com.tor.service.BridgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
public class QuickViewController {

    @Autowired
    private BridgeService bridgeService;


    @RequestMapping("/quick_view")
    public String quickView(ModelMap modelMap) {
        List<Bridge> bridgeList = bridgeService.selectBridges();
        return "QuickView";
    }

}
