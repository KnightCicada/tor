package com.tor.domain;

public class Train {
    //分类算法
    private String classifyAlgorithm;
    //训练集名称
    private String trainFileName;
    //训练集路径
    private String trainFilePath;
    //删除多于特征的arff文件
    private String arffFilePath;
    //模型信息
    private String modelInfo;
    //模型存储路径
    private String modelPath;
    //模型名称
    private String modelName;

    public String getClassifyAlgorithm() {
        return classifyAlgorithm;
    }

    public void setClassifyAlgorithm(String classifyAlgorithm) {
        this.classifyAlgorithm = classifyAlgorithm;
    }

    public String getTrainFileName() {
        return trainFileName;
    }

    public void setTrainFileName(String trainFileName) {
        this.trainFileName = trainFileName;
    }

    public String getTrainFilePath() {
        return trainFilePath;
    }

    public void setTrainFilePath(String trainFilePath) {
        this.trainFilePath = trainFilePath;
    }

    public String getArffFilePath() {
        return arffFilePath;
    }

    public void setArffFilePath(String arffFilePath) {
        this.arffFilePath = arffFilePath;
    }

    public String getModelInfo() {
        return modelInfo;
    }

    public void setModelInfo(String modelInfo) {
        this.modelInfo = modelInfo;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
