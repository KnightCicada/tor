package com.tor.domain;

public class Feature {
    //提取特征的算法
    private String trainName;
    private String trainPath;
    //保留提取的特征，删除多于特征的 arff文件路径
    private String arffFilePath;
    //提取的特征
    private String featureTxtPath;

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainPath() {
        return trainPath;
    }

    public void setTrainPath(String trainPath) {
        this.trainPath = trainPath;
    }

    public String getArffFilePath() {
        return arffFilePath;
    }

    public void setArffFilePath(String arffFilePath) {
        this.arffFilePath = arffFilePath;
    }

    public String getFeatureTxtPath() {
        return featureTxtPath;
    }

    public void setFeatureTxtPath(String featureTxtPath) {
        this.featureTxtPath = featureTxtPath;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "trainName='" + trainName + '\'' +
                ", trainPath='" + trainPath + '\'' +
                ", arffFilePath='" + arffFilePath + '\'' +
                ", featureTxtPath='" + featureTxtPath + '\'' +
                '}';
    }
}
