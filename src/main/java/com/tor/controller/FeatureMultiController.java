package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.domain.Feature;
import com.tor.domain.Packet;
import com.tor.result.Const;
import com.tor.service.FeatureService;
import com.tor.service.PacketService;
import com.tor.util.AlgorithmUtil;
import com.tor.util.FeaturesToCh;
import com.tor.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@Slf4j
@RequestMapping("/featureMulti")
public class FeatureMultiController {

    AlgorithmUtil algorithmUtil = new AlgorithmUtil();
    @Autowired
    private FeatureService featureService;
    @Autowired
    private PacketService packetService;
    private Feature feature = new Feature();

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(ModelMap map, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn) {
        //写一个函数将fileInforList分成两个List，才能完成分页功能
        List<Packet> TrainFileInforList;

        PageHelper.startPage(pn, 6);
        List<Packet> packetList = packetService.findAllTrainPacket();
        map.addAttribute("Feature", packetList);
        PageInfo<Packet> pageList = new PageInfo<>(packetList);
        map.addAttribute("page", pageList);
        return Const.FEATURE_MUTLI_PAGE;
    }


    @RequestMapping(value = "/getMultiFeature")
    public String multiFeature(@RequestParam("trainFile") String trainFileName, ModelMap map) throws Exception {
        //对trainFileName进行处理，得到csv文件的名字
        String trainFileNameReplace = trainFileName.substring(trainFileName.lastIndexOf("/")).replace("/", "");

        feature.setTrainName(trainFileNameReplace);
        feature.setTrainPath(trainFileName);

        String arffFilePath = PropertiesUtil.getArff() + trainFileNameReplace.replace(".csv", "") + ".arff";
        feature.setArffFilePath(arffFilePath);

        String featureTxtPath = PropertiesUtil.getFeature() + trainFileNameReplace.replace(".csv", "") + "Features" + ".txt";
        feature.setFeatureTxtPath(featureTxtPath);

        featureService.getMultiFeature(feature);//选择特征算法，得到降维版的csv训练集。
        String featureResult = algorithmUtil.readFeature(featureTxtPath);
        String[] feature = featureResult.replace("label", "").split(",");
        Set<String> set = new HashSet<>();
        Collections.addAll(set, feature);
        set.add("idleMin");
        set.add("activeMax");
        set.add("bwdIATStd");
        set.add("bwdIATMean");
        set.add("fwdIATMax");
        set.add("flowIATMean");

        String featureResultEn = String.valueOf(set).replace("[", "").replace("]", "");
        map.addAttribute("featureResultEn", featureResultEn);
        map.addAttribute("featureResultCh", FeaturesToCh.toChinese(set));
        return Const.SHOW_FEATURE_PAGE;
    }
}
