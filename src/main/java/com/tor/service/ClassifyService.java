package com.tor.service;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.tor.domain.Flow;
import com.tor.domain.Model;
import com.tor.domain.Packet;
import com.tor.result.CodeMsg;
import com.tor.result.Result;
import com.tor.util.AlgorithmUtil;
import com.tor.util.ArffUtil;
import com.tor.util.LabelUtil;
import com.tor.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ClassifyService {

    @Autowired
    private ModelService modelService;

    @Autowired
    private PacketService packetService;


    /**
     * 将pcap数据包转换为对应的csv，并将csv最后一列打标签为“？”，读取数据库中最新的训练好的模型，然后测试，返回测试结果
     *
     * @param fullPcapPath pcap数据包的全路径
     * @param fileName     pcap数据包的单个名字，不带路径
     * @return
     */
    public Result<List<Flow>> getClassifyResult(String fullPcapPath, String fileName) {
        System.load(System.getProperty("user.dir") + "/lib/jnetpcap.dll");
        System.load(System.getProperty("user.dir") + "/lib/jnetpcap.so");
//        if (!ISCXFlowMeter.singlePcap(fullPcapPath, PropertiesUtil.getPcapCsvPath())) {
//            return Result.error(CodeMsg.TRANSFER_EXCEPT);
//        }
        //打标签
        String fullCsvPath = PropertiesUtil.getPcapCsvPath() + "ISCX_" + fileName + ".csv";
        boolean flag = LabelUtil.singleCsvLabel(fullCsvPath, "test");
        System.out.println(flag);
        Model model = modelService.findLastModel();
        if (model == null) {
            return Result.error(CodeMsg.NULL_MODEL);
        }
        String csvName = fileName + ".csv"; //csv的名字，例如tor1.pcap.csv
        String modelPath = model.getModelPath();//.model
        String featurePath = model.getFeaturePath();//Feature.txt

        //调用测试算法，得到一个表，表示测试结果。
        List<Flow> resultList = getModelClassifyList(csvName, fullCsvPath, modelPath, featurePath);

        if (resultList == null || resultList.size() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
//        for (Flow flow : resultList) {
//            System.out.println(flow.toString());
//        }
        //TODO 存数据库
        return Result.success(resultList);

    }

    private List<Flow> getModelClassifyList(String csvFileName, String fullCsvFile, String modelPath, String featurePath) {
        AlgorithmUtil algorithmUtil = new AlgorithmUtil();
        ArffUtil arffUtil = new ArffUtil();
        ArrayList<Flow> display = new ArrayList<Flow>();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(fullCsvFile, ',', StandardCharsets.UTF_8);
            reader.readHeaders();//跳过表头。
            while (reader.readRecord()) {
                csvList.add(reader.getValues());
            }
            reader.close();//csvList中是除去表头的一个测试文件的全部内容。
            int boundary = csvList.size();
            ArrayList<String> classifyResult = new ArrayList<String>();
            //获取分类结果
            classifyResult = algorithmUtil.useModelclassify(csvFileName, fullCsvFile, modelPath, featurePath);//只是很多行数据的最后一列。
            //获取拼接好的结果
            display = arffUtil.attach(boundary, display, csvList, classifyResult);
            //更新csv
            updateFullCSV(fullCsvFile, csvList, classifyResult);
            //TODO 插入数据库
            String packetName = fullCsvFile.substring(fullCsvFile.lastIndexOf("ISCX_") + 5, fullCsvFile.length() - 4);
            System.out.println(packetName);
            String packetPath = PropertiesUtil.getPcapPath() + packetName;
            if (display.size() > 0) {
                Packet packet = new Packet();
                packet.setPacketName(packetName);
                packet.setCsvPath(fullCsvFile);
                packet.setType("已判别Local");
                packet.setPacketPath(packetPath);
                if (packetService.insertPacket(packet) < 0) {
                    log.error("数据包插入失败:{}", packet.toString());
                }
            }
            System.out.println("测试成功结束！");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return display;
    }

    private void updateFullCSV(String fullCsvFile, ArrayList<String[]> csvList, ArrayList<String> classifyResult) throws IOException {
        CsvWriter writer = new CsvWriter(fullCsvFile, ',', StandardCharsets.UTF_8);
        String header = "Source IP,Source Port,Destination IP,Destination Port,Protocol,Flow Duration,Flow Bytes/s,Flow Packets/s,Flow IAT Mean,Flow IAT Std,Flow IAT Max,Flow IAT Min,Fwd IAT Mean,Fwd IAT Std,Fwd IAT Max,Fwd IAT Min,Bwd IAT Mean,Bwd IAT Std,Bwd IAT Max,Bwd IAT Min,Active Mean,Active Std,Active Max,Active Min,Idle Mean,Idle Std,Idle Max,Idle Min,label";
        String[] headers = header.split(",");
        writer.writeRecord(headers);
        for (int i = 0; i < csvList.size(); i++) {
            String[] strings = csvList.get(i);
            strings[strings.length - 1] = classifyResult.get(i);
            writer.writeRecord(strings);
        }
        writer.close();
    }

    public List<Flow> getFlowListFromDB(String pcapFileName) {
        List<Flow> list = new ArrayList<>();
        String csvFileName = PropertiesUtil.getPcapCsvPath() + "ISCX_" + pcapFileName + ".csv";
        File file = new File(csvFileName);
        if (!file.exists()) {
            return list;
        }
        return getFlowListFromCSV(csvFileName);
    }


    /**
     * 读取保存好的pcap csv，转换为list，并返回
     *
     * @param filePath1
     * @return
     */
    public List<Flow> getFlowListFromCSV(String filePath1) {
        List<Flow> list = new ArrayList<>();
        try {
            CsvReader reader = new CsvReader(filePath1, ',', StandardCharsets.UTF_8);//csv是以逗号进行分隔。
            reader.readHeaders();//跳过头部
            while (reader.readRecord()) {
                Flow flow = new Flow();
                flow.setSrcIP(reader.getValues()[0]);
                flow.setSrcPort(reader.getValues()[1]);
                flow.setDstIP(reader.getValues()[2]);
                flow.setDstPort(reader.getValues()[3]);
                flow.setProtocol(reader.getValues()[4]);
                flow.setDuration(reader.getValues()[5]);
                flow.setFlowBytsPsec(reader.getValues()[6]);
                flow.setFlowPktsPsec(reader.getValues()[7]);
                flow.setFlowIATMean(reader.getValues()[8]);
                flow.setFlowIATStd(reader.getValues()[9]);
                flow.setFlowIATMax(reader.getValues()[10]);
                flow.setFlowIATMin(reader.getValues()[11]);
                flow.setFwdIATMean(reader.getValues()[12]);
                flow.setFwdIATStd(reader.getValues()[13]);
                flow.setFwdIATMax(reader.getValues()[14]);
                flow.setFwdIATMin(reader.getValues()[15]);
                flow.setBwdIATMean(reader.getValues()[16]);
                flow.setBwdIATStd(reader.getValues()[17]);
                flow.setBwdIATMax(reader.getValues()[18]);
                flow.setBwdIATMin(reader.getValues()[19]);
                flow.setActiveMean(reader.getValues()[20]);
                flow.setActiveStd(reader.getValues()[21]);
                flow.setActiveMax(reader.getValues()[22]);
                flow.setActiveMin(reader.getValues()[23]);
                flow.setIdleMean(reader.getValues()[24]);
                flow.setIdleStd(reader.getValues()[25]);
                flow.setIdleMax(reader.getValues()[26]);
                flow.setIdleMin(reader.getValues()[27]);
                flow.setLabel(reader.getValues()[28]);
                list.add(flow);//将训练集的没有表头的读入flowList中。没有删除的所有的特征。
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
//        CsvReader reader = new CsvReader("D:/zyan/classifyData/ISCX_tor1.pcap.csv");
//        System.out.println(Arrays.toString(reader.getHeaders()));
//        reader.getValues();
//        String[] header = reader.getHeaders();
//        reader.close();
//        CsvWriter writer = new CsvWriter("D:/zyan/classifyData/ISCX_tor1.pcap111111.csv",',',StandardCharsets.UTF_8);
//        String string = "Source IP, Source Port, Destination IP, Destination Port, Protocol, Flow Duration, Flow Bytes/s, Flow Packets/s, Flow IAT Mean, Flow IAT Std, Flow IAT Max, Flow IAT Min,Fwd IAT Mean, Fwd IAT Std, Fwd IAT Max, Fwd IAT Min,Bwd IAT Mean, Bwd IAT Std, Bwd IAT Max, Bwd IAT Min,Active Mean, Active Std, Active Max, Active Min,Idle Mean, Idle Std, Idle Max, Idle Min,label\n";
//        String[] header = string.split(", ");
//        writer.writeRecord(header);
//        String[] s = new String[]{"222","333","33"};
//        writer.writeRecord(s);
//        writer.close();
        String csvFileName = "ISCX_tor1.pcap.csv";
        String packetName = csvFileName.substring(csvFileName.lastIndexOf("ISCX_") + 5, csvFileName.length() - 4);
        String packetPath = PropertiesUtil.getPcapPath() + packetName;
        System.out.println(packetName);
        System.out.println(packetPath);
    }
}
