package com.tor.algorithm;

import com.tor.util.ArffUtil;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;

public class GenerateFeatures {
    ArffUtil arffUtil = new ArffUtil();

    /**
     * @param trainFilePath:训练集路径csv文件路径
     * @return： 特征选择得到的结果
     */
    public String getClassifyFeature(String trainFilePath) {
        File file = new File(trainFilePath);
        //所有的训练集都用一个.arff文件过渡。存储.csv转换之后的结果
        String convertFile = file.getParent() + File.separator + "trainFilecsvToArff.arff";
        arffUtil.csvToArff(trainFilePath, convertFile);//将所选的训练集转化为.arff文件，存在convertFile文件中，所有的训练集公用一个convertFile文件。
        ConverterUtils.DataSource sourceTrain;
        try {
            sourceTrain = new ConverterUtils.DataSource(convertFile);//读入写有全部特征的.arff文件
            Instances train = sourceTrain.getDataSet();//读入.arff数据(24个属性)。
            if (train.classIndex() == -1)//判断是否为tor notor 用于二分类的一列。
            {
                train.setClassIndex(train.numAttributes() - 1);//将最后一列的下标记为属性总数-1.因为第一个属性的下标为0.
            }
            // Infogain+Ranker
            // 特征选择
            AttributeSelection attsel = new AttributeSelection();
            // attribute evaluator:Infogain
            InfoGainAttributeEval eval = new InfoGainAttributeEval();
            // search method:Ranker
            Ranker search = new Ranker();
            attsel.setEvaluator(eval);
            attsel.setSearch(search);
            attsel.SelectAttributes(train);
            // 特征选择：从0开始计数
            int[] attrIndex = search.search(eval, train);

            int avg = 0;
            for (int i = 1; i < attrIndex.length; i++) {
                avg += (attrIndex[i] - attrIndex[i - 1]) / 2;
            }

            StringBuilder attrInfoGainInfo = new StringBuilder();
            for (int i = 0; i < attrIndex.length; i++) {
                //TODO 删除对分类作用较小的特征
                if (i > 1) {
                    if (Math.abs(eval.evaluateAttribute(attrIndex[i]) - eval.evaluateAttribute(attrIndex[i - 1])) + 0.01 > Math.abs(eval.evaluateAttribute(attrIndex[i - 1]) - eval.evaluateAttribute(attrIndex[i - 2]))) {
                        break;
                    }
                }
                attrInfoGainInfo.append((train.attribute(attrIndex[i]).name()));
                attrInfoGainInfo.append(",");
            }
            if (attrInfoGainInfo.toString().length() == 0) {
                attrInfoGainInfo.append("no feature!");
            } else {
                attrInfoGainInfo.deleteCharAt(attrInfoGainInfo.toString().length() - 1);
                attrInfoGainInfo.append(",label");
            }
            return attrInfoGainInfo.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getClassifyFeatureMulit(String trainFilePath) {
        File file = new File(trainFilePath);
        //所有的训练集都用一个.arff文件过渡。存储.csv转换之后的结果
        String convertFile = file.getParent() + File.separator + "trainFilecsvToArff.arff";

        arffUtil.csvToArffMulti(trainFilePath, convertFile);//将所选的训练集转化为.arff文件，存在convertFile文件中，所有的训练集公用一个convertFile文件。
        int flag = 0;
        ConverterUtils.DataSource sourceTrain;
        try {
            sourceTrain = new ConverterUtils.DataSource(convertFile);//读入写有全部特征的.arff文件
            Instances train = sourceTrain.getDataSet();//读入.arff数据(24个属性)。
            if (train.classIndex() == -1)//判断是否为tor notor 用于二分类的一列。
            {
                train.setClassIndex(train.numAttributes() - 1);//将最后一列的下标记为属性总数-1.因为第一个属性的下标为0.
            }
            // Infogain+Ranker
            // 特征选择
            AttributeSelection attsel = new AttributeSelection();
            // attribute evaluator:Infogain
            InfoGainAttributeEval eval = new InfoGainAttributeEval();
            // search method:Ranker
            Ranker search = new Ranker();
            attsel.setEvaluator(eval);
            attsel.setSearch(search);
            attsel.SelectAttributes(train);
            // 特征选择：从0开始计数
            int[] attrIndex = search.search(eval, train);

            int avg = 0;
            for (int i = 1; i < attrIndex.length; i++) {
                avg += (attrIndex[i] - attrIndex[i - 1]) / 2;
            }

            StringBuilder attrInfoGainInfo = new StringBuilder();
            for (int i = 0; i < attrIndex.length; i++) {
                //TODO 删除对分类作用较小的特征
                if (i > 1) {
                    if (Math.abs(eval.evaluateAttribute(attrIndex[i]) - eval.evaluateAttribute(attrIndex[i - 1])) + 0.01 > Math.abs(eval.evaluateAttribute(attrIndex[i - 1]) - eval.evaluateAttribute(attrIndex[i - 2]))) {
                        break;
                    }
                }
                attrInfoGainInfo.append((train.attribute(attrIndex[i]).name()));
                attrInfoGainInfo.append(",");
            }
            if (attrInfoGainInfo.toString().length() == 0) {
                attrInfoGainInfo.append("no feature!");
            } else {
                attrInfoGainInfo.deleteCharAt(attrInfoGainInfo.toString().length() - 1);
                attrInfoGainInfo.append(",label");
            }
            return attrInfoGainInfo.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}