package com.tor.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

//        ProxyHTTP proxyHTTP= new  ProxyHTTP(PropertiesUtil.getHttpProxy(),PropertiesUtil.getHttpProxyPort());
//        session.setProxy(proxyHTTP);
        File file = new File(localDir + fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
        }

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
}
