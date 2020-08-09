package com.tor.util;

import java.util.HashMap;
import java.util.Set;

public class FeaturesToCh {
    public static String toChinese(Set<String> set) {
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
        for (String s : set) {
            stringBuilder.append(hashMap.get(s)).append(", ");
        }
        String str = stringBuilder.toString();
        return str.substring(0, str.length() - 2);
    }
}
