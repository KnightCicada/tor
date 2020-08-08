package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.domain.Packet;
import com.tor.domain.Train;
import com.tor.result.Const;
import com.tor.service.FeatureService;
import com.tor.service.PacketService;
import com.tor.util.AlgorithmUtil;
import com.tor.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

//选择机器学习算法，选择训练集，进行训练，得到模型，存入数据库。info.txt和.model两个文件进行展示。
@Controller
@Slf4j
@RequestMapping("/trainMulti")
public class TrainMultiController {

    AlgorithmUtil algorithmUtil = new AlgorithmUtil();
    @Autowired
    private FeatureService featureService;
    @Autowired
    private PacketService packetService;
    private Train train = new Train();

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(ModelMap map, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn) {
        List<Packet> PacketList = new LinkedList<>();
        PageHelper.startPage(pn, 6);
        PacketList = packetService.findAllTrainPacket();
        map.addAttribute("data", PacketList);
        PageInfo<Packet> page = new PageInfo<>(PacketList);
        map.addAttribute("page", page);
        return Const.TRAIN_PAGE;
    }

    /**
     * @param traincsvPath:训练文件对应的csv文件路径
     * @param algorithm：特征提取算法
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/processMulti")
    public String feature(@RequestParam("trainFile") String traincsvPath, @RequestParam("algorithm") String algorithm, ModelMap map) throws Exception {
        //对traincsvPath进行处理，得到csv文件的名字
        String trainFileName = traincsvPath.substring(traincsvPath.lastIndexOf("/")).replace("/", "");
        String arffFilePath = PropertiesUtil.getArff() + trainFileName.replace(".csv", "") + ".arff";
        String modelInfo = PropertiesUtil.getModelInfo() + trainFileName.replace(".csv", "") + algorithm + "Info" + ".txt";
        String modelPath = PropertiesUtil.getModel() + trainFileName.replace(".csv", "") + algorithm + ".model";
        String modelname = trainFileName.replace(".csv", "") + algorithm + ".model";

        train.setClassifyAlgorithm(algorithm);
        train.setTrainFilePath(traincsvPath);
        train.setTrainFileName(trainFileName);
        train.setArffFilePath(arffFilePath);
        train.setModelInfo(modelInfo);
        train.setModelPath(modelPath);
        train.setModelName(modelname);

        featureService.trainingMulti(train);//进行机器学习，训练集训练出模型。
        String info = algorithmUtil.readModelInfo(train.getModelInfo());
        map.addAttribute("info", info);
        return Const.SHOW_RESULT_PAGE;
    }

}
