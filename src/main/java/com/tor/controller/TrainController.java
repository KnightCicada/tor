package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.domain.Packet;
import com.tor.domain.Train;
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

import java.util.LinkedList;
import java.util.List;

//选择机器学习算法，选择训练集，进行训练，得到模型，存入数据库。info.txt和.model两个文件进行展示。
@Controller
@Slf4j
@RequestMapping("/train")
public class TrainController {

    AlgorithmUtil algorithmUtil = new AlgorithmUtil();
    @Autowired
    private FeatureService featureService;
    @Autowired
    private TrainPacketService trainPacketService;
    private Train train = new Train();

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(ModelMap map, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn) {
        //写一个函数将fileInforList分成两个List，才能完成分页功能
        List<Packet> PacketList = new LinkedList<>();
        PageHelper.startPage(pn, 6);
        PacketList = trainPacketService.findAllPacket();
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
    @RequestMapping(value = "/process")
    public String feature(@RequestParam("trainFile") String traincsvPath, @RequestParam("algorithm") String algorithm, ModelMap map) throws Exception {
        //对traincsvPath进行处理，得到csv文件的名字
        String trainFileName = traincsvPath.substring(traincsvPath.lastIndexOf("/")).replace("/", "");

        System.out.println("===!!=" + traincsvPath);
        train.setClassifyAlgorithm(algorithm);
        train.setTrainFileName(trainFileName);
        train.setTrainFilePath(traincsvPath);

        String arffFilePath = PropertiesUtil.getArff() + trainFileName.replace(".csv", "") + ".arff";
        train.setArffFilePath(arffFilePath);

        String modelInfo = PropertiesUtil.getModelInfo() + trainFileName.replace(".csv", "") + algorithm + "Info" + ".txt";
        train.setModelInfo(modelInfo);

        String modelPath = PropertiesUtil.getModel() + trainFileName.replace(".csv", "") + algorithm + ".model";
        train.setModelPath(modelPath);

        String modelname = trainFileName.replace(".csv", "") + algorithm + ".model";
        train.setModelName(modelname);

        featureService.training(train);//进行机器学习，训练集训练出模型。
        String info = algorithmUtil.readModelInfo(train.getModelInfo());
        map.addAttribute("info", info);
        return Const.SHOW_RESULT_PAGE;
    }

}
