package com.tor.controller;

import com.tor.domain.*;
import com.tor.result.Const;
import com.tor.service.BridgeService;
import com.tor.service.RelayService;
import com.tor.service.TraceService;
import com.tor.util.RandomColorUtils;
import com.tor.vo.CountryCnt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.ResourceBundleBasedAdapter;
import sun.util.resources.OpenListResourceBundle;

import java.io.IOException;
import java.util.*;

@Controller
@Slf4j
@RequestMapping()
public class HomeController {
    @Autowired
    private TraceService traceService;
    @Autowired
    private BridgeService bridgeService;
    @Autowired
    private RelayService relayService;

    @RequestMapping("/home")
    public String index(ModelMap modelMap) throws IOException {
        ResourceBundleBasedAdapter resourceBundleBasedAdapter = ((ResourceBundleBasedAdapter) LocaleProviderAdapter.forJRE());
        OpenListResourceBundle resource = resourceBundleBasedAdapter.getLocaleData().getLocaleNames(Locale.UK);
        //trace
        Set<SourceAndDesIPVO> set = traceService.getIPs();
        List<IPVO> ipvos = getIPVO(set);
        modelMap.addAttribute("ips", ipvos);
        modelMap.addAttribute("trace", set);
        //bridge
        List<CountryCnt> bridgeList = bridgeService.findByCountry();
        List<WorldView> bridgeListConvert = new ArrayList<>();
        for (CountryCnt c : bridgeList) {
            String country = c.getCountryCode();
            try {
                country = resource.getString(country);
            } catch (Exception e) {
                e.printStackTrace();
            }
            WorldView worldView = new WorldView();
            worldView.setName(country);
            worldView.setValue(c.getCnt());

            ItemStyle i = new ItemStyle();
            i.setColor(RandomColorUtils.generateColor());

            worldView.setItemStyle(i);
            bridgeListConvert.add(worldView);
        }
        modelMap.addAttribute("result", bridgeListConvert);
        //relay
        List<CountryCnt> countryCntList = relayService.findByCountry();
        List<WorldView> worldViewArrayList = new ArrayList<>();
        for (CountryCnt c : countryCntList) {
            String country = c.getCountryCode();
            try {
                country = resource.getString(country);
            } catch (Exception e) {
                e.printStackTrace();
            }
            WorldView worldView = new WorldView();
            worldView.setName(country);
            worldView.setValue(c.getCnt());

            ItemStyle i = new ItemStyle();
            i.setColor(RandomColorUtils.generateColor());

            worldView.setItemStyle(i);
            worldViewArrayList.add(worldView);
        }
        modelMap.addAttribute("worldView", worldViewArrayList);
        //top10
        List<Relay> top = relayService.findTop10();
        for (Relay r : top) {
            try {
                r.setCountryCode(resource.getString(r.getCountryCode()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        System.out.println("1" + JSON.toJSONString(top));
//        System.out.println("2" + JSON.toJSONString(worldViewArrayList));
//        System.out.println("3" + JSON.toJSONString(bridgeList));
//        System.out.println("4" + JSON.toJSONString(ipvos));

        modelMap.addAttribute("Top", top);
        return Const.HOME_PAGE;
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
