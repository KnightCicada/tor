package com.tor.util;

import com.csvreader.CsvReader;
import com.tor.domain.Flow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArffUtil {

    /**
     * 将CSV文件转换成Arff文件
     * @param filePath1：CSV文件路径
     * @param filePath2：生成的arff存储路径和文件名
     *
     * 特征没有加入源ip 目的ip 源端口 目的端口，和协议。是从下标为5的属性开始的。
     * 进行二分类的文件头{TOR,NONTOR}
     */
    public void csvToArff(String filePath1, String filePath2) {
        ArrayList<String> arrayList = new ArrayList<>();
        String head;
        head = new StringBuffer().
                append("@relation feature.arff" + System.lineSeparator())
                .append("@attribute duration numeric" + System.lineSeparator())
                .append("@attribute flowBytsPsec numeric" + System.lineSeparator())
                .append("@attribute flowPktsPsec numeric" + System.lineSeparator())
                .append("@attribute flowIATMean numeric" + System.lineSeparator())
                .append("@attribute flowIATStd numeric" + System.lineSeparator())
                .append("@attribute flowIATMax numeric" + System.lineSeparator())
                .append("@attribute flowIATMin numeric" + System.lineSeparator())
                .append("@attribute fwdIATMean numeric" + System.lineSeparator())
                .append("@attribute fwdIATStd numeric" + System.lineSeparator())
                .append("@attribute fwdIATMax numeric" + System.lineSeparator())
                .append("@attribute fwdIATMin numeric" + System.lineSeparator())
                .append("@attribute bwdIATMean numeric" + System.lineSeparator())
                .append("@attribute bwdIATStd numeric" + System.lineSeparator())
                .append("@attribute bwdIATMax numeric" + System.lineSeparator())
                .append("@attribute bwdIATMin numeric" + System.lineSeparator())
                .append("@attribute activeMean numeric" + System.lineSeparator())
                .append("@attribute activeStd numeric" + System.lineSeparator())
                .append("@attribute activeMax numeric" + System.lineSeparator())
                .append("@attribute activeMin numeric" + System.lineSeparator())
                .append("@attribute idleMean numeric" + System.lineSeparator())
                .append("@attribute idleStd numeric" + System.lineSeparator())
                .append("@attribute idleMax numeric" + System.lineSeparator())
                .append("@attribute idleMin numeric" + System.lineSeparator())
                .append("@attribute label {TOR,NONTOR}" + System.lineSeparator())
                .append(System.lineSeparator())
                .append("@data" + System.lineSeparator())
                .toString();
        try {
            //把训练集csv中的数据(不带头部)全部读入csvList中。然后把后面24个属性存在了stringBuilder中，在到了arrayList中。
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            String csvFilePath = filePath1;
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));//csv是以逗号进行分隔。
            reader.readHeaders();//跳过头部
            while (reader.readRecord()) {
                csvList.add(reader.getValues());
            }
            reader.close();
//            System.out.println("csvlist:"+csvList.size());
            for (int row = 0; row < csvList.size(); row++) {
//                System.out.println();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(csvList.get(row)[5] + ",")
                        .append(csvList.get(row)[6] + ",")
                        .append(csvList.get(row)[7] + ",")
                        .append(csvList.get(row)[8] + ",").append(csvList.get(row)[9] + ",").append(csvList.get(row)[10] + ",")
                        .append(csvList.get(row)[11] + ",").append(csvList.get(row)[12] + ",").append(csvList.get(row)[13] + ",")
                        .append(csvList.get(row)[14] + ",").append(csvList.get(row)[15] + ",").append(csvList.get(row)[16] + ",")
                        .append(csvList.get(row)[17] + ",").append(csvList.get(row)[18] + ",").append(csvList.get(row)[19] + ",")
                        .append(csvList.get(row)[20] + ",").append(csvList.get(row)[21] + ",").append(csvList.get(row)[22] + ",")
                        .append(csvList.get(row)[23] + ",").append(csvList.get(row)[24] + ",").append(csvList.get(row)[25] + ",")
                        .append(csvList.get(row)[26] + ",").append(csvList.get(row)[27] + ",").append(csvList.get(row)[28] + ",")
                        .append(System.lineSeparator()).toString();
                arrayList.add(stringBuilder.toString());
            }
//          System.out.println(arrayList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        try {
            File mergeFile = new File(filePath2);

            if (!mergeFile.exists()) {
                mergeFile.createNewFile();
            }
            FileWriter fw = new FileWriter(mergeFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(head);//先把头部写入.arff文件
            for (String s : arrayList) {
//                System.out.println(s);
                bw.write(s);//再把剩下的24个属性写入文件。
            }
            bw.close();
            fw.close();
//          System.out.println("change done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void csvToArffMulti(String filePath1, String filePath2) {
        ArrayList<String> arrayList = new ArrayList<>();
        String head;
        head = new StringBuffer().
                append("@relation feature.arff" + System.lineSeparator())
                .append("@attribute duration numeric" + System.lineSeparator())
                .append("@attribute flowBytsPsec numeric" + System.lineSeparator())
                .append("@attribute flowPktsPsec numeric" + System.lineSeparator())
                .append("@attribute flowIATMean numeric" + System.lineSeparator())
                .append("@attribute flowIATStd numeric" + System.lineSeparator())
                .append("@attribute flowIATMax numeric" + System.lineSeparator())
                .append("@attribute flowIATMin numeric" + System.lineSeparator())
                .append("@attribute fwdIATMean numeric" + System.lineSeparator())
                .append("@attribute fwdIATStd numeric" + System.lineSeparator())
                .append("@attribute fwdIATMax numeric" + System.lineSeparator())
                .append("@attribute fwdIATMin numeric" + System.lineSeparator())
                .append("@attribute bwdIATMean numeric" + System.lineSeparator())
                .append("@attribute bwdIATStd numeric" + System.lineSeparator())
                .append("@attribute bwdIATMax numeric" + System.lineSeparator())
                .append("@attribute bwdIATMin numeric" + System.lineSeparator())
                .append("@attribute activeMean numeric" + System.lineSeparator())
                .append("@attribute activeStd numeric" + System.lineSeparator())
                .append("@attribute activeMax numeric" + System.lineSeparator())
                .append("@attribute activeMin numeric" + System.lineSeparator())
                .append("@attribute idleMean numeric" + System.lineSeparator())
                .append("@attribute idleStd numeric" + System.lineSeparator())
                .append("@attribute idleMax numeric" + System.lineSeparator())
                .append("@attribute idleMin numeric" + System.lineSeparator())
                .append("@attribute label {CHAT,VIDEO,VOIP,P2P,FILE-TRANSFER,MAIL,BROWSING,AUDIO}" + System.lineSeparator())
                .append(System.lineSeparator())
                .append("@data" + System.lineSeparator())
                .toString();
        try {
            //把训练集csv中的数据(不带头部)全部读入csvList中。然后把后面24个属性存在了stringBuilder中，在到了arrayList中。
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            String csvFilePath = filePath1;
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));//csv是以逗号进行分隔。
            reader.readHeaders();//跳过头部
            while (reader.readRecord()) {
                csvList.add(reader.getValues());
            }
            reader.close();
//            System.out.println("csvlist:"+csvList.size());
            for (int row = 0; row < csvList.size(); row++) {
//                System.out.println();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(csvList.get(row)[5] + ",")
                        .append(csvList.get(row)[6] + ",")
                        .append(csvList.get(row)[7] + ",")
                        .append(csvList.get(row)[8] + ",").append(csvList.get(row)[9] + ",").append(csvList.get(row)[10] + ",")
                        .append(csvList.get(row)[11] + ",").append(csvList.get(row)[12] + ",").append(csvList.get(row)[13] + ",")
                        .append(csvList.get(row)[14] + ",").append(csvList.get(row)[15] + ",").append(csvList.get(row)[16] + ",")
                        .append(csvList.get(row)[17] + ",").append(csvList.get(row)[18] + ",").append(csvList.get(row)[19] + ",")
                        .append(csvList.get(row)[20] + ",").append(csvList.get(row)[21] + ",").append(csvList.get(row)[22] + ",")
                        .append(csvList.get(row)[23] + ",").append(csvList.get(row)[24] + ",").append(csvList.get(row)[25] + ",")
                        .append(csvList.get(row)[26] + ",").append(csvList.get(row)[27] + ",").append(csvList.get(row)[28] + ",")
                        .append(System.lineSeparator()).toString();
                arrayList.add(stringBuilder.toString());
            }
//          System.out.println(arrayList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        try {
            File mergeFile = new File(filePath2);

            if (!mergeFile.exists()) {
                mergeFile.createNewFile();
            }
            FileWriter fw = new FileWriter(mergeFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(head);//先把头部写入.arff文件
            for (String s : arrayList) {
//                System.out.println(s);
                bw.write(s);//再把剩下的24个属性写入文件。
            }
            bw.close();
            fw.close();
//          System.out.println("change done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 将特征提取之外的特征删除，降低维度
     *
     * @param filePath：CSV文件路径(拥有全部的特征属性)
     * @param features：选择的特征，以字符串形式传入，中间逗号分隔
     * @param newFilePath：每个训练集对应生成的.arff存储路径+文件名
     */
    public void delete(String filePath, String features, String newFilePath) {
        String[] feature = features.split(",");
//        System.out.println(feature.length);
        List<Flow> flowList = new ArrayList<Flow>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@relation ").append(new File(newFilePath).getName()).append(System.lineSeparator()).append(System.lineSeparator());
        //将剩余的特征属性读入stringBuilder中,作为头.
        for (int i = 0; i < feature.length - 1; i++) {
            stringBuilder.append("@attribute ").append(feature[i]).append(" numeric").append(System.lineSeparator());
        }
        //二分类对应的表头
        stringBuilder.append("@attribute ").append(feature[feature.length - 1]).append(" {TOR,NONTOR}").append(System.lineSeparator()).append(System.lineSeparator());
        stringBuilder.append("@data").append(System.lineSeparator());
        try {
            //ArrayList<String[]> csvList = new ArrayList<String[]>(); // 用来保存数据
            String csvFilePath = filePath;
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8")); // 一般用这编码读就可以了
            reader.readHeaders(); // 跳过表头 如果需要表头的话，不要写这句。
            while (reader.readRecord()) { // 逐行读入除表头的数据
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
                flowList.add(flow);//将训练集的没有表头的读入flowList中。没有删除的所有的特征。
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//      insert(flowList);
        StringBuilder stringBuilder1 = new StringBuilder();
        for (Flow flow : flowList) {
            //java中map集合用于存储键值对，即key-value,String字符串这里充当key键，Object对象这里是value值。每个键对应一个值。
            Map<String, Object> fieldMap = new HashMap<String, Object>();
            //getClass方法可以获取一个对象的类型类，然后在调用该类的方法可以获取该类的相关信息，比如父类的名字，该类的名字。得到类对象
            Class flowClass = flow.getClass();
            //得到类中的所有属性集合
            Field[] fs = flowClass.getDeclaredFields();
            for (Field field : fs) {
                String fieldName = field.getName();//获得属性名称
                String firstLetter = fieldName.substring(0, 1).toUpperCase();//根据属性名称获得对应的属性值。
                String getMethodName = "get" + firstLetter + fieldName.substring(1);
                try {
                    Method getMethod = flowClass.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(flow, new Object[]{}); //这个对象字段get方法的值
                    fieldMap.put(fieldName, value);//一个fileName 对应一个value。
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < feature.length; i++) {
                if (fieldMap.get(feature[i]) != null) {
                    stringBuilder1.append(fieldMap.get(feature[i])).append(",");
                }
            }

            stringBuilder1.deleteCharAt(stringBuilder1.length() - 1);
            stringBuilder1.append(System.lineSeparator());
        }
        try {
            File mergeFile = new File(newFilePath);
            //文件不存在时候，主动创建文件。
            if (!mergeFile.exists()) {
                mergeFile.createNewFile();
            }
            FileWriter fw = new FileWriter(mergeFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(stringBuilder.toString());//先写入了头部。
            bw.write(stringBuilder1.toString());//后写入了下面每一行的内容。写在了每个训练集对应的一个文件名+Feature+".arff"文件中。
            bw.close();
            fw.close();
//          System.out.println("write done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据模型使用的特征，将测试文件中多余的特征进行删除
     *
     * @param filePath：测试文件csv路径
     * @param features           模型使用的特征
     * @param newFilePath        新文件的路径
     */
    public void deleteMulti(String filePath, String features, String newFilePath) {
        String[] feature = features.split(",");
        List<Flow> flowList = new ArrayList<Flow>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@relation ").append(new File(newFilePath).getName()).append(System.lineSeparator()).append(System.lineSeparator());
        //将剩余的特征属性读入stringBuilder中,作为头.
        for (int i = 0; i < feature.length - 1; i++) {
            stringBuilder.append("@attribute ").append(feature[i]).append(" numeric").append(System.lineSeparator());
        }
        //多分类对应的表头
        stringBuilder.append("@attribute ").append(feature[feature.length - 1]).append(" {CHAT,VIDEO,VOIP,P2P,FILE-TRANSFER,MAIL,BROWSING,AUDIO}").append(System.lineSeparator()).append(System.lineSeparator());
        stringBuilder.append("@data").append(System.lineSeparator());
        try {
            //ArrayList<String[]> csvList = new ArrayList<String[]>(); // 用来保存数据
            String csvFilePath = filePath;
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8")); // 一般用这编码读就可以了
            reader.readHeaders(); // 跳过表头 如果需要表头的话，不要写这句。
            while (reader.readRecord()) { // 逐行读入除表头的数据
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
                flowList.add(flow);//将训练集的没有表头的读入flowList中。没有删除的所有的特征。
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//      insert(flowList);
        StringBuilder stringBuilder1 = new StringBuilder();
        for (Flow flow : flowList) {
            //java中map集合用于存储键值对，即key-value,String字符串这里充当key键，Object对象这里是value值。每个键对应一个值。
            Map<String, Object> fieldMap = new HashMap<String, Object>();
            //getClass方法可以获取一个对象的类型类，然后在调用该类的方法可以获取该类的相关信息，比如父类的名字，该类的名字。得到类对象
            Class flowClass = flow.getClass();
            //得到类中的所有属性集合
            Field[] fs = flowClass.getDeclaredFields();
            for (Field field : fs) {
                String fieldName = field.getName();//获得属性名称
                String firstLetter = fieldName.substring(0, 1).toUpperCase();//根据属性名称获得对应的属性值。
                String getMethodName = "get" + firstLetter + fieldName.substring(1);
                try {
                    Method getMethod = flowClass.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(flow, new Object[]{}); //这个对象字段get方法的值
                    fieldMap.put(fieldName, value);//一个fileName 对应一个value。
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < feature.length; i++) {
                if (fieldMap.get(feature[i]) != null) {
                    stringBuilder1.append(fieldMap.get(feature[i])).append(",");
                }
            }

            stringBuilder1.deleteCharAt(stringBuilder1.length() - 1);
            stringBuilder1.append(System.lineSeparator());
        }
        try {
            File mergeFile = new File(newFilePath);
            //文件不存在时候，主动创建文件。
            if (!mergeFile.exists()) {
                mergeFile.createNewFile();
            }
            FileWriter fw = new FileWriter(mergeFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(stringBuilder.toString());//先写入了头部。
            bw.write(stringBuilder1.toString());//后写入了下面每一行的内容。写在了每个训练集对应的一个文件名+Feature+".arff"文件中。
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将得到的标签和其余特征进行拼接
     *
     * @param boundary       测试文件除去头部的行数
     * @param display        一个数组，用来装结果
     * @param csvList        测试文件
     * @param classifyResult 最后一列的标签值
     * @return
     */
    public ArrayList<Flow> attach(int boundary, ArrayList<Flow> display, ArrayList<String[]> csvList, ArrayList<String> classifyResult) {
        for (int row = 0; row < boundary; row++) {
            Flow flow = new Flow();
            // 将csv中的特征给Features中对应的变量赋值
            flow.setSrcIP(csvList.get(row)[0]);
            flow.setSrcPort(csvList.get(row)[1]);
            flow.setDstIP(csvList.get(row)[2]);
            flow.setDstPort(csvList.get(row)[3]);
            flow.setProtocol(csvList.get(row)[4]);
            flow.setDuration(csvList.get(row)[5]);
            flow.setFlowBytsPsec(csvList.get(row)[6]);
            flow.setFlowPktsPsec(csvList.get(row)[7]);
            flow.setFlowIATMean(csvList.get(row)[8]);
            flow.setFlowIATStd(csvList.get(row)[9]);
            flow.setFlowIATMax(csvList.get(row)[10]);
            flow.setFlowIATMin(csvList.get(row)[11]);
            flow.setFwdIATMean(csvList.get(row)[12]);
            flow.setFwdIATStd(csvList.get(row)[13]);
            flow.setFwdIATMax(csvList.get(row)[14]);
            flow.setFwdIATMin(csvList.get(row)[15]);
            flow.setBwdIATMean(csvList.get(row)[16]);
            flow.setBwdIATStd(csvList.get(row)[17]);
            flow.setBwdIATMax(csvList.get(row)[18]);
            flow.setBwdIATMin(csvList.get(row)[19]);
            flow.setActiveMean(csvList.get(row)[20]);
            flow.setActiveStd(csvList.get(row)[21]);
            flow.setActiveMax(csvList.get(row)[22]);
            flow.setActiveMin(csvList.get(row)[23]);
            flow.setIdleMean(csvList.get(row)[24]);
            flow.setIdleStd(csvList.get(row)[25]);
            flow.setIdleMax(csvList.get(row)[26]);
            flow.setIdleMin(csvList.get(row)[27]);
            // 为标签属性赋值分类结果标签
            flow.setLabel(classifyResult.get(row));
            // 将该Features存入ArrayList
            display.add(flow);
        }
        return display;
    }
}

