package com.tor.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class DownLoadUtil {

    /**
     * 连接远程服务器，进入目录，下载文件
     *
     * @param remoteDir 目录
     * @param fileName  文件名
     * @throws JSchException
     * @throws IOException
     * @throws SftpException
     */
    public static void downloadFromRemote(String remoteDir, String fileName, String localDir) throws JSchException, IOException, SftpException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(PropertiesUtil.getRemoteRootName(), PropertiesUtil.getremoteIP(), 22);

        ProxyHTTP proxyHTTP= new  ProxyHTTP(PropertiesUtil.getHttpProxy(),PropertiesUtil.getHttpProxyPort());
        session.setProxy(proxyHTTP);
        File file = new File(localDir + fileName);

        session.setPassword(PropertiesUtil.getRemoteRootPassword()); // 设置密码
        session.setUserInfo(new MyUserInfo()); //需要实现Jsch包中的UserInfo,UIKeyboardInteractive接口，用以保存用户信息，以及进行键盘交互式认证并执行命令。
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");//在代码里需要跳过检测。否则会报错找不到主机
        session.setConfig(config); // 为Session对象设置properties
        int timeout = 30000;
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立与远程服务器的连接回话
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        log.info("start download channel file：{}!", remoteDir + fileName);
        channelSftp.cd(remoteDir);
        channelSftp.get(fileName, new FileOutputStream(file));
        log.info("Download Success!");
        channelSftp.disconnect();
        log.info("end execute channel sftp!");
        session.disconnect();
        log.info("end session!");
    }


    /**
     * 下载csv,返回csv内容
     * @param address full地址，https://torstatus.rueckgr.at/query_export.php/Tor_query_EXPORT.csv
     * @return csv内容
     * @throws NoSuchAlgorithmException ssl算法
     * @throws KeyManagementException  证书
     */
    public static List<String> downloadCSV(String address) throws NoSuchAlgorithmException, KeyManagementException {

        List<String> list = new ArrayList<String>();

        //  直接通过主机认证
        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        //  配置认证管理器
        TrustManager[] trustAllCerts = {new TrustAllTrustManager()};
        SSLContext sc = SSLContext.getInstance("SSL");
        SSLSessionContext sslsc = sc.getServerSessionContext();
        sslsc.setSessionTimeout(0);
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        //  激活主机认证
        HttpsURLConnection.setDefaultHostnameVerifier(hv);

        URL url = null;
        InputStream is = null;
        InputStreamReader isr = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(address);
            String host = PropertiesUtil.getHttpProxy();
            int port = PropertiesUtil.getHttpProxyPort();
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
            conn = (HttpURLConnection)url.openConnection(proxy);
            // 读取服务器端返回的内容
            is = conn.getInputStream();
            //读取内容
            isr = new InputStreamReader(is,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                if (count > 1){
                    list.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (isr != null){
                    isr.close();
                }
                if (is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}
