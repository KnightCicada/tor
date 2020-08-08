package com.tor.service;

import com.csvreader.CsvReader;
import com.tor.domain.Flow;
import com.tor.util.AlgorithmUtil;
import com.tor.util.ArffUtil;
import com.tor.util.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TestService {

    AlgorithmUtil algorithmUtil = new AlgorithmUtil();
    ArffUtil arffUtil = new ArffUtil();

    /**
     * 使用保存的模型对测试集进行分类
     *
     * @param testPath：测试集路径
     * @param featurePath：分类器对应的特征文件路径
     * @param modelPath:分类器路径
     * @return
     */
    public List<Flow> getModelClassifyList(String testname, String testPath, String modelPath, String featurePath) {
        ArrayList<Flow> display = new ArrayList<Flow>();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            String csvFilePath = testPath;
            CsvReader reader = new CsvReader(csvFilePath, ',', StandardCharsets.UTF_8);
            reader.readHeaders();//跳过表头。
            while (reader.readRecord()) {
                csvList.add(reader.getValues());
            }
            reader.close();//csvList中是除去表头的一个测试文件的全部内容。
            int boundary = csvList.size();
            ArrayList<String> classifyResult = algorithmUtil.useModelclassify(testname, testPath, modelPath, featurePath);//只是很多行数据的最后一列。
            //更新csv
            UpdateResult.updateFullCSV(csvFilePath, csvList, classifyResult);
            display = arffUtil.attach(boundary, display, csvList, classifyResult);
            log.info("测试成功结束！");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return display;
    }


    public List<Flow> getModelClassifyListMulti(String testname, String testPath, String modelPath, String featurePath) {
        ArrayList<Flow> display = new ArrayList<Flow>();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            String csvFilePath = testPath;
            CsvReader reader = new CsvReader(csvFilePath, ',', StandardCharsets.UTF_8);
            reader.readHeaders();//跳过表头。
            while (reader.readRecord()) {
                csvList.add(reader.getValues());
            }
            reader.close();//csvList中是除去表头的一个测试文件的全部内容。
            int boundary = csvList.size();
            ArrayList<String> classifyResult = algorithmUtil.useModelclassifyMulti(testname, testPath, modelPath, featurePath);
            UpdateResult.updateFullCSV(csvFilePath, csvList, classifyResult);
            display = arffUtil.attach(boundary, display, csvList, classifyResult);
            log.info("分类完成");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return display;
    }
}
