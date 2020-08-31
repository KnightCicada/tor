package com.tor.algorithm;


import com.tor.domain.Model;
import com.tor.domain.Train;
import com.tor.util.AlgorithmUtil;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenerateModel {
    Model model = new Model();
    AlgorithmUtil algorithmUtil = new AlgorithmUtil();

    public Model getModel() {
        return this.model;
    }


    /**
     * 创建模型,保存模型
     *
     * @param train：存储着模型信息的对象。
     */
    public void showModel(Train train) throws Exception {
        String arffFilePath = train.getArffFilePath();
        String algorithm = train.getClassifyAlgorithm();
        int flag = 0;
        ConverterUtils.DataSource sourceTrain = new ConverterUtils.DataSource(arffFilePath);
        Instances trainFile = sourceTrain.getDataSet();
        if (trainFile.classIndex() == -1) {
            trainFile.setClassIndex(trainFile.numAttributes() - 1);
        }

        if ("C4.5".equals(algorithm)) {
            flag = 1;
        } else if ("RandomForest".equals(algorithm)) {
            flag = 2;
        }
        try {
            // 1.C4.5（J48）
            if (flag == 1) {
                FilteredClassifier fc = new FilteredClassifier();
                J48 j48 = new J48();
                fc.setClassifier(j48);
                fc.buildClassifier(trainFile);
                //保存模型
                algorithmUtil.saveModel(fc, train);
                //保存模型十折交叉验证信息
                algorithmUtil.saveModelInfo(fc, trainFile, train);
                model = algorithmUtil.getModel();
            }
            // 2.RandomForest
            else if (flag == 2) {
                FilteredClassifier fc = new FilteredClassifier();
                RandomForest rf = new RandomForest();
                fc.setClassifier(rf);
                fc.buildClassifier(trainFile);
                //保存模型,flag指示模型类别
                //AlgorithmUtil b = new AlgorithmUtil();
                algorithmUtil.saveModel(fc, train);
                //保存模型十折交叉验证信息
                algorithmUtil.saveModelInfo(fc, trainFile, train);
                model = algorithmUtil.getModel();
            }
        }catch (Exception e){
            log.error("生成模型失败");
        }
    }
}
