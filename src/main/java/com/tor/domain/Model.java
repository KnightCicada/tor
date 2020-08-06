package com.tor.domain;

public class Model {
    private Integer id;
    //模型名称
    private String modelName;
    //模型存储路径
    private String modelPath;
    //模型交叉验证结果
    private String modelInfo;
    //训练模型的特征
    private String featurePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public String getModelInfo() {
        return modelInfo;
    }

    public void setModelInfo(String modelInfo) {
        this.modelInfo = modelInfo;
    }

    public String getFeaturePath() {
        return featurePath;
    }

    public void setFeaturePath(String featurePath) {
        this.featurePath = featurePath;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                ", modelPath='" + modelPath + '\'' +
                ", modelInfo='" + modelInfo + '\'' +
                ", featurePath='" + featurePath + '\'' +
                '}';
    }
}
