package com.tor.controller;

import com.tor.domain.ItemStyle;
import com.tor.domain.Relay;
import com.tor.domain.WorldView;
import com.tor.service.RelayService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@Slf4j

public class RelayController {
    @Autowired
    private RelayService relayService;


    @RequestMapping(value = "/worldView")
    public String show(ModelMap modelMap) {
        ResourceBundleBasedAdapter resourceBundleBasedAdapter = ((ResourceBundleBasedAdapter) LocaleProviderAdapter.forJRE());
        OpenListResourceBundle resource = resourceBundleBasedAdapter.getLocaleData().getLocaleNames(Locale.UK);

        List<CountryCnt> countryCntList = relayService.findByCountry();
        List<WorldView> worldViewArrayList = new ArrayList<>();
        for (CountryCnt c : countryCntList) {
            String country = c.getCountryCode();
            try {
                country = resource.getString(country);
            } catch (Exception e) {
                System.out.println();
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
        return "Worldview";
//        return Const.WORLDVIEW_PAGE;
    }

    @RequestMapping(value = "/top")
    public String showTop(ModelMap modelMap) {
        ResourceBundleBasedAdapter resourceBundleBasedAdapter = ((ResourceBundleBasedAdapter) LocaleProviderAdapter.forJRE());
        OpenListResourceBundle resource = resourceBundleBasedAdapter.getLocaleData().getLocaleNames(Locale.UK);
        List<Relay> top = relayService.findTop10();
        for (Relay r : top) {
            try {
                r.setCountryCode(resource.getString(r.getCountryCode()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        modelMap.addAttribute("Top", top);
        return "Top1";
    }
}
