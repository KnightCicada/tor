package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.domain.Feature;
import com.tor.domain.Packet;
import com.tor.result.Const;
import com.tor.service.FeatureService;
import com.tor.service.TrainPacketService;
import com.tor.util.AlgorithmUtil;
import com.tor.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/feature")
public class FeatureController {

    AlgorithmUtil algorithmUtil = new AlgorithmUtil();
    @Autowired
    private FeatureService featureService;
    @Autowired
    private TrainPacketService trainPacketService;
    private Feature feature = new Feature();

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(ModelMap map, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn) {
        //写一个函数将fileInforList分成两个List，才能完成分页功能
        List<Packet> TrainFileInforList;

        PageHelper.startPage(pn, 6);
        List<Packet> packetList = trainPacketService.findAllPacket();
        map.addAttribute("Feature", packetList);
        PageInfo<Packet> pageList = new PageInfo<>(packetList);
        map.addAttribute("page", pageList);
        return Const.FEATURE_PAGE;
    }

    /*
        trainFileName实际为得到的数据包对应的csv文件路径
     */
    @RequestMapping(value = "/getFeature")
    public String feature(@RequestParam("trainFile") String trainFileName, ModelMap map) throws Exception {
        //对trainFileName进行处理，得到csv文件的名字
        String trainFileNameReplace = trainFileName.substring(trainFileName.lastIndexOf("/")).replace("/", "");

        feature.setTrainName(trainFileNameReplace);
        feature.setTrainPath(trainFileName);

        String arffFilePath = PropertiesUtil.getArff() + trainFileNameReplace.replace(".csv", "") + ".arff";
        feature.setArffFilePath(arffFilePath);

        String featureTxtPath = PropertiesUtil.getFeature() + trainFileNameReplace.replace(".csv", "") + "Features" + ".txt";
        feature.setFeatureTxtPath(featureTxtPath);

        featureService.getFeature(feature);//选择特征算法，得到降维版的csv训练集。
        String featureResult = algorithmUtil.readFeature(featureTxtPath);
        map.addAttribute("featureResultEn", featureResult.replace(",label", ""));

        String[] feature = featureResult.split(",");

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("srcIP", "源IP");
        hashMap.put("srcPort", "源端口号");
        hashMap.put("dstIP", "目的IP");
        hashMap.put("dstPort", "目的端口号");
        hashMap.put("protocol", "协议");
        hashMap.put("duration", "流持续时间");
        hashMap.put("flowBytsPsec", "流中每秒比特数");
        hashMap.put("flowPktsPsec", "流中每秒数据包个数");
        hashMap.put("flowIATMean", "发送和接收两个数据包之间的时间间隔的平均值");
        hashMap.put("flowIATStd", "发送和接收两个数据包之间的时间间隔的标准值");
        hashMap.put("flowIATMax", "发送和接收两个数据包之间的时间间隔的最大值");
        hashMap.put("flowIATMin", "发送和接收两个数据包之间的时间间隔的最小值");
        hashMap.put("fwdIATMean", "发送两个数据包的时间间隔的平均值");
        hashMap.put("fwdIATStd", "发送两个数据包的时间间隔的标准值");
        hashMap.put("fwdIATMax", "发送两个数据包的时间间隔的最大值");
        hashMap.put("fwdIATMin", "发送两个数据包的时间间隔的最小值");
        hashMap.put("bwdIATMean", "接收两个数据包的时间间隔的平均值");
        hashMap.put("bwdIATStd", "接收两个数据包的时间间隔的标准值");
        hashMap.put("bwdIATMax", "接收两个数据包的时间间隔的最大值");
        hashMap.put("bwdIATMin", "接收两个数据包的时间间隔的最小值");
        hashMap.put("activeMean", "流停止之前活跃时间的平均值");
        hashMap.put("activeStd", "流停止之前活跃时间的标准值");
        hashMap.put("activeMax", "流停止之前活跃时间的最大值");
        hashMap.put("activeMin", "流停止之前活跃时间的最小值");
        hashMap.put("idleMean", "流活跃之前停止时间的平均值");
        hashMap.put("idleStd", "流活跃之前停止时间的最大值");
        hashMap.put("idleMax", "流活跃之前停止时间的最大值");
        hashMap.put("idleMin", "流活跃之前停止时间的最小值");

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : feature) {
            if (hashMap.containsKey(s)) {
                stringBuilder.append(hashMap.get(s)).append(", ");
            }
        }
        map.addAttribute("featureResultCh", stringBuilder.toString());

        return Const.SHOW_FEATURE_PAGE;
    }
}
