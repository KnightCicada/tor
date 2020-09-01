package com.tor.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties props;

    static {
        try {
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application.properties");
            props = new Properties();
            props.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getlocalDir() {
        return props.getProperty("localDir");
    }

    public static String getPcapPath() {
        return props.getProperty("localDir") + props.getProperty("raw_pcap_path");
    }

    public static String getPcapCsvPath() {
        return props.getProperty("localDir") + props.getProperty("pcap_csv_path");
    }

    public static String getPython() {
        return props.getProperty("python");
    }

    public static String getMakeLabelPy() {
        return props.getProperty("localDir") + props.getProperty("makeLabelPy");
    }

    public static String getArff() {
        return props.getProperty("localDir") + props.getProperty("arff_path");
    }

    public static String getFeature() {
        return props.getProperty("localDir") + props.getProperty("feature_path");
    }

    public static String getModel() {
        return props.getProperty("localDir") + props.getProperty("model");
    }

    public static String getModelInfo() {
        return props.getProperty("localDir") + props.getProperty("model_info");
    }

    public static String getremoteIP() {
        return props.getProperty("remoteIP");
    }

    public static String getRemoteRootName() {
        return props.getProperty("remote_name");
    }

    public static String getRemoteRootPassword() {
        return props.getProperty("remote_password");
    }

    public static String getRemotePcapPath() {
        return props.getProperty("remote_pcap_path");
    }

    public static String getRemoteToLocalPath() {
        return props.getProperty("localDir") + props.getProperty("remote_pcap_to_local_path");
    }

    public static String getHttpProxy() {
        return props.getProperty("http_proxy");
    }

    public static int getHttpProxyPort() {
        return Integer.parseInt(props.getProperty("http_proxy_port"));
    }

    public static String getRemoteFilePath() {
        return props.getProperty("remote_file_path");
    }

    public static String getLocalFilePath() {
        return props.getProperty("localDir") + props.getProperty("local_file_path");
    }
}
