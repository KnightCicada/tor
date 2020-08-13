package com.tor.service;


import com.tor.algorithm.GenerateFeatures;
import com.tor.algorithm.GenerateModel;
import com.tor.domain.Feature;
import com.tor.domain.Model;
import com.tor.domain.Train;
import com.tor.util.AlgorithmUtil;
import com.tor.util.ArffUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeatureService {
    GenerateFeatures generateFeatures = new GenerateFeatures();
    AlgorithmUtil algorithmUtil = new AlgorithmUtil();
    ArffUtil arffUtil = new ArffUtil();
    @Autowired
    private ModelService modelService;


    public void getFeature(Feature feature) throws Exception {
        String trainFilePath = feature.getTrainPath();
        String traindeletePath = feature.getArffFilePath();
        String featureTxtPath = feature.getFeatureTxtPath();
        showFeature(trainFilePath, traindeletePath, featureTxtPath);
    }

    public void getMultiFeature(Feature feature) throws Exception {
        String trainFilePath = feature.getTrainPath();
        String traindeletePath = feature.getArffFilePath();
        String featureTxtPath = feature.getFeatureTxtPath();
        showMultiFeature(trainFilePath, traindeletePath, featureTxtPath);
    }

    /**
     * 读取保存的模型信息
     *
     * @param trainFilePath:作为训练集的数据路径
     * @param trainDelete:保存剩余特征的.arff文件路径
     * @param featureTxtPath:保存剩余特征的.txt文件路径，以便随后进行展示
     * @throws Exception
     */
    public void showFeature(String trainFilePath, String trainDelete, String featureTxtPath) throws Exception {
        String selectFeatures;
        try {
            //下面是调用算法得到一个训练集的剩下的特征。 字符串。
            selectFeatures = generateFeatures.getClassifyFeature(trainFilePath);
            //将选择的特征存入文本文件.txt：一个训练集对应一个自己的文件名+Feature.txt文件。存在quic/model中。
            algorithmUtil.saveFeatures(selectFeatures, featureTxtPath);
            //对csv训练集保留选择的特征，删除多余的特征，并存储为.arff文件。一个训练集对应一个自己的文件名+Feature.arff文件。
            arffUtil.delete(trainFilePath, selectFeatures, trainDelete);
            log.info("FeatureService 特征提取成功结束");
        } catch (Exception e) {
            log.error("特征提取失败");
        }
    }

    public void showMultiFeature(String trainFilePath, String trainDelete, String featureTxtPath) throws Exception {
        String selectFeatures;
        try {
            //下面是调用算法得到一个训练集的剩下的特征。 字符串。
            selectFeatures = generateFeatures.getClassifyFeatureMulit(trainFilePath);
            //将选择的特征存入文本文件.txt：一个训练集对应一个自己的文件名+Feature.txt文件。存在quic/model中。
            algorithmUtil.saveFeatures(selectFeatures, featureTxtPath);
            //对csv训练集保留选择的特征，删除多余的特征，并存储为.arff文件。一个训练集对应一个自己的文件名+Feature.arff文件。
            arffUtil.deleteMulti(trainFilePath, selectFeatures, trainDelete);
            log.info("FeatureService 特征提取成功结束");
        } catch (Exception e) {
            log.error("特征提取失败");
        }
    }


    public void training(Train train) throws Exception {
        GenerateModel traingData = new GenerateModel();
        traingData.showModel(train);
        log.info("FeatureService二分类 调用机器学习算法");
        //训练得到一个模型
        Model newModel = traingData.getModel();
        try {
            modelService.insertModel(newModel);
            log.info("FeatureService 成功将模型信息插入数据库");
        } catch (Exception e){
            log.info(e.getMessage());
        }
    }

    public void trainingMulti(Train train) throws Exception {
        GenerateModel traingData = new GenerateModel();
        traingData.showModel(train);
        log.info("FeatureService多分类 调用机器学习算法");
        //训练得到一个模型
        Model newModel = traingData.getModel();
        modelService.insertModel(newModel);
        log.info("FeatureService 成功将多分类模型信息插入数据库");
    }
}
