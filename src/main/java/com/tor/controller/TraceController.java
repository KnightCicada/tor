package com.tor.controller;


import com.tor.domain.IPVO;
import com.tor.domain.SourceAndDesIPVO;
import com.tor.result.Const;
import com.tor.service.TraceService;
import com.tor.util.RandomColorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@Slf4j
public class TraceController {


    @Autowired
    private TraceService traceService;


    @RequestMapping("/trace")
    public String trace(ModelMap modelMap) throws IOException {
        Set<SourceAndDesIPVO> set = traceService.getIPs();
        List<IPVO> ipvos = getIPVO(set);
        modelMap.addAttribute("ips", ipvos);
        modelMap.addAttribute("trace", set);
        return Const.TRACE_PAGE;
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
