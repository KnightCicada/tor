package com.tor.util;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.tor.domain.Flow;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {
    public static void updateFullCSV(String fullCsvFile, ArrayList<String[]> csvList, ArrayList<String> classifyResult) throws IOException {
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

    public static void updateFullCSVTwoAu(String fullCsvFile, List<Flow> classifyResult) throws IOException {
        ArrayList<String[]> csvList = new ArrayList<String[]>();
        CsvReader reader = new CsvReader(fullCsvFile, ',', StandardCharsets.UTF_8);
        reader.readHeaders();//跳过表头。
        while (reader.readRecord()) {
            csvList.add(reader.getValues());
        }
        reader.close();//csvList中是除去表头的一个测试文件的全部内容。
        CsvWriter writer = new CsvWriter(fullCsvFile, ',', StandardCharsets.UTF_8);
        String header = "Source IP,Source Port,Destination IP,Destination Port,Protocol,Flow Duration,Flow Bytes/s,Flow Packets/s,Flow IAT Mean,Flow IAT Std,Flow IAT Max,Flow IAT Min,Fwd IAT Mean,Fwd IAT Std,Fwd IAT Max,Fwd IAT Min,Bwd IAT Mean,Bwd IAT Std,Bwd IAT Max,Bwd IAT Min,Active Mean,Active Std,Active Max,Active Min,Idle Mean,Idle Std,Idle Max,Idle Min,label";
        String[] headers = header.split(",");
        writer.writeRecord(headers);
        for (int i = 0; i < classifyResult.size(); i++) {
            String[] strings = csvList.get(i);
            strings[strings.length - 1] = classifyResult.get(i).getLabel() + "";
            writer.writeRecord(strings);
        }
        writer.close();
    }

    public static void saveTmpCsv(String fullCsvFile, List<Flow> torList) throws IOException {
        CsvWriter csvWriter = new CsvWriter(fullCsvFile, ',', Charset.forName("UTF-8"));
        String header = "Source IP,Source Port,Destination IP,Destination Port,Protocol,Flow Duration,Flow Bytes/s,Flow Packets/s,Flow IAT Mean,Flow IAT Std,Flow IAT Max,Flow IAT Min,Fwd IAT Mean,Fwd IAT Std,Fwd IAT Max,Fwd IAT Min,Bwd IAT Mean,Bwd IAT Std,Bwd IAT Max,Bwd IAT Min,Active Mean,Active Std,Active Max,Active Min,Idle Mean,Idle Std,Idle Max,Idle Min,label";
        String[] headers = header.split(",");
        csvWriter.writeRecord(headers);
        for (Flow f : torList) {
            csvWriter.writeRecord(f.toStringArray(f));
        }
        csvWriter.close();
    }
}
