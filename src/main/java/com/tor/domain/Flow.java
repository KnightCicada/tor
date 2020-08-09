package com.tor.domain;

import java.util.ArrayList;

public class Flow {
    //源IP
    private String srcIP;
    //源端口号
    private String srcPort;
    //目的IP
    private String dstIP;
    //目的端口号
    private String dstPort;
    //协议
    private String protocol;
    //流持续时间
    private String duration;
    //每秒流的比特数
    private String flowBytsPsec;
    //每秒数据包的个数
    private String flowPktsPsec;
    //发送和接收两个数据包之间的时间间隔的平均值
    private String flowIATMean;
    //发送和接收两个数据包之间的时间间隔的标准值
    private String flowIATStd;
    //发送和接收两个数据包之间的时间间隔的最大值
    private String flowIATMax;
    //发送和接收两个数据包之间的时间间隔的最小值
    private String flowIATMin;
    //发送两个数据包的时间间隔的总和
    private String fwdIATMean;
    //发送两个数据包的时间间隔的标准值
    private String fwdIATStd;
    //发送两个数据包的时间间隔的最大值
    private String fwdIATMax;
    //发送两个数据包的时间间隔的最小值
    private String fwdIATMin;
    //接收两个数据包的时间间隔的总和
    private String bwdIATMean;
    //接收两个数据包的时间间隔的标准值
    private String bwdIATStd;
    //接收两个数据包的时间间隔的最大值
    private String bwdIATMax;
    //接收两个数据包的时间间隔的最小值
    private String bwdIATMin;
    //流停止之前活跃时间的平均值
    private String activeMean;
    //流停止之前活跃时间的标准值
    private String activeStd;
    //流停止之前活跃时间的最大值
    private String activeMax;
    //流停止之前活跃时间的最小值
    private String activeMin;
    //流活跃之前停止时间的平均值
    private String idleMean;
    //流活跃之前停止时间的标准值
    private String idleStd;
    //流活跃之前停止时间的最大值
    private String idleMax;
    //流活跃之前停止时间的最小值
    private String idleMin;
    //二分类标签
    private String label;


    public Flow() {

    }

    public Flow(String srcIP, String srcPort, String dstIP, String dstPort, String protocol, String duration, String flowBytsPsec, String flowPktsPsec, String flowIATMean, String flowIATStd, String flowIATMax, String flowIATMin, String fwdIATMean, String fwdIATStd, String fwdIATMax, String fwdIATMin, String bwdIATMean, String bwdIATStd, String bwdIATMax, String bwdIATMin, String activeMean, String activeStd, String activeMax, String activeMin, String idleMean, String idleStd, String idleMax, String idleMin, String label) {
        this.srcIP = srcIP;
        this.srcPort = srcPort;
        this.dstIP = dstIP;
        this.dstPort = dstPort;
        this.protocol = protocol;

        this.duration = duration;
        this.flowBytsPsec = flowBytsPsec;
        this.flowPktsPsec = flowPktsPsec;

        this.flowIATMean = flowIATMean;
        this.flowIATStd = flowIATStd;
        this.flowIATMax = flowIATMax;
        this.flowIATMin = flowIATMin;

        this.fwdIATMean = fwdIATMean;
        this.fwdIATStd = fwdIATStd;
        this.fwdIATMax = fwdIATMax;
        this.fwdIATMin = fwdIATMin;

        this.bwdIATMean = bwdIATMean;
        this.bwdIATStd = bwdIATStd;
        this.bwdIATMax = bwdIATMax;
        this.bwdIATMin = bwdIATMin;

        this.activeMean = activeMean;
        this.activeStd = activeStd;
        this.activeMax = activeMax;
        this.activeMin = activeMin;

        this.idleMean = idleMean;
        this.idleStd = idleStd;
        this.idleMax = idleMax;
        this.idleMin = idleMin;

        this.label = label;
    }


    public String getSrcIP() {
        return srcIP;
    }

    public void setSrcIP(String srcIP) {
        this.srcIP = srcIP;
    }

    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }

    public String getDstIP() {
        return dstIP;
    }

    public void setDstIP(String dstIP) {
        this.dstIP = dstIP;
    }

    public String getDstPort() {
        return dstPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFlowBytsPsec() {
        return flowBytsPsec;
    }

    public void setFlowBytsPsec(String flowBytsPsec) {
        this.flowBytsPsec = flowBytsPsec;
    }

    public String getFlowPktsPsec() {
        return flowPktsPsec;
    }

    public void setFlowPktsPsec(String flowPktsPsec) {
        this.flowPktsPsec = flowPktsPsec;
    }

    public String getFlowIATMean() {
        return flowIATMean;
    }

    public void setFlowIATMean(String flowIATMean) {
        this.flowIATMean = flowIATMean;
    }

    public String getFlowIATStd() {
        return flowIATStd;
    }

    public void setFlowIATStd(String flowIATStd) {
        this.flowIATStd = flowIATStd;
    }

    public String getFlowIATMax() {
        return flowIATMax;
    }

    public void setFlowIATMax(String flowIATMax) {
        this.flowIATMax = flowIATMax;
    }

    public String getFlowIATMin() {
        return flowIATMin;
    }

    public void setFlowIATMin(String flowIATMin) {
        this.flowIATMin = flowIATMin;
    }

    public String getFwdIATMean() {
        return fwdIATMean;
    }

    public void setFwdIATMean(String fwdIATMean) {
        this.fwdIATMean = fwdIATMean;
    }

    public String getFwdIATStd() {
        return fwdIATStd;
    }

    public void setFwdIATStd(String fwdIATStd) {
        this.fwdIATStd = fwdIATStd;
    }

    public String getFwdIATMax() {
        return fwdIATMax;
    }

    public void setFwdIATMax(String fwdIATMax) {
        this.fwdIATMax = fwdIATMax;
    }

    public String getFwdIATMin() {
        return fwdIATMin;
    }

    public void setFwdIATMin(String fwdIATMin) {
        this.fwdIATMin = fwdIATMin;
    }

    public String getBwdIATMean() {
        return bwdIATMean;
    }

    public void setBwdIATMean(String bwdIATMean) {
        this.bwdIATMean = bwdIATMean;
    }

    public String getBwdIATStd() {
        return bwdIATStd;
    }

    public void setBwdIATStd(String bwdIATStd) {
        this.bwdIATStd = bwdIATStd;
    }

    public String getBwdIATMax() {
        return bwdIATMax;
    }

    public void setBwdIATMax(String bwdIATMax) {
        this.bwdIATMax = bwdIATMax;
    }

    public String getBwdIATMin() {
        return bwdIATMin;
    }

    public void setBwdIATMin(String bwdIATMin) {
        this.bwdIATMin = bwdIATMin;
    }

    public String getActiveMean() {
        return activeMean;
    }

    public void setActiveMean(String activeMean) {
        this.activeMean = activeMean;
    }

    public String getActiveStd() {
        return activeStd;
    }

    public void setActiveStd(String activeStd) {
        this.activeStd = activeStd;
    }

    public String getActiveMax() {
        return activeMax;
    }

    public void setActiveMax(String activeMax) {
        this.activeMax = activeMax;
    }

    public String getActiveMin() {
        return activeMin;
    }

    public void setActiveMin(String activeMin) {
        this.activeMin = activeMin;
    }

    public String getIdleMean() {
        return idleMean;
    }

    public void setIdleMean(String idleMean) {
        this.idleMean = idleMean;
    }

    public String getIdleStd() {
        return idleStd;
    }

    public void setIdleStd(String idleStd) {
        this.idleStd = idleStd;
    }

    public String getIdleMax() {
        return idleMax;
    }

    public void setIdleMax(String idleMax) {
        this.idleMax = idleMax;
    }

    public String getIdleMin() {
        return idleMin;
    }

    public void setIdleMin(String idleMin) {
        this.idleMin = idleMin;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Flow{srcIP='" + srcIP + '\'' +
                ", srcPort='" + srcPort + '\'' +
                ", dstIP='" + dstIP + '\'' +
                ", dstPort='" + dstPort + '\'' +
                ", protocol='" + protocol + '\'' +
                ", duration='" + duration + '\'' +
                ", flowBytsPsec='" + flowBytsPsec + '\'' +
                ", flowPktsPsec='" + flowPktsPsec + '\'' +
                ", flowIATMean='" + flowIATMean + '\'' +
                ", flowIATStd='" + flowIATStd + '\'' +
                ", flowIATMax='" + flowIATMax + '\'' +
                ", flowIATMin='" + flowIATMin + '\'' +
                ", fwdIATMean='" + fwdIATMean + '\'' +
                ", fwdIATStd='" + fwdIATStd + '\'' +
                ", fwdIATMax='" + fwdIATMax + '\'' +
                ", fwdIATMin='" + fwdIATMin + '\'' +
                ", bwdIATMean='" + bwdIATMean + '\'' +
                ", bwdIATStd='" + bwdIATStd + '\'' +
                ", bwdIATMax='" + bwdIATMax + '\'' +
                ", bwdIATMin='" + bwdIATMin + '\'' +
                ", activeMean='" + activeMean + '\'' +
                ", activeStd='" + activeStd + '\'' +
                ", activeMax='" + activeMax + '\'' +
                ", activeMin='" + activeMin + '\'' +
                ", idleMean='" + idleMean + '\'' +
                ", idleStd='" + idleStd + '\'' +
                ", idleMax='" + idleMax + '\'' +
                ", idleMin='" + idleMin + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    public String[]toStringArray(Flow f){
        ArrayList<String> res=new ArrayList<>();
        res.add(f.getSrcIP());
        res.add(f.getSrcPort());
        res.add(f.getDstIP());
        res.add(f.getDstPort());
        res.add(f.getProtocol());
        res.add(f.getDuration());

        res.add(f.getFlowBytsPsec());
        res.add(f.getFlowPktsPsec());

        res.add(f.getFlowIATMean());
        res.add(f.getFlowIATStd());
        res.add(f.getFlowIATMax());
        res.add(f.getFlowIATMin());

        res.add(f.getFwdIATMean());
        res.add(f.getFwdIATStd());
        res.add(f.getFwdIATMax());
        res.add(f.getFwdIATMin());

        res.add(f.getBwdIATMean());
        res.add(f.getBwdIATStd());
        res.add(f.getBwdIATMax());
        res.add(f.getBwdIATMin());

        res.add(f.getActiveMean());
        res.add(f.getActiveStd());
        res.add(f.getActiveMax());
        res.add(f.getActiveMin());

        res.add(f.getIdleMean());
        res.add(f.getIdleStd());
        res.add(f.getIdleMax());
        res.add(f.getIdleMin());

        res.add("?");

        return res.toArray(new String[0]);
    }
}
