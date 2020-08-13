package com.tor.service;

import com.jcraft.jsch.*;
import com.tor.domain.Packet;
import com.tor.exception.GlobalException;
import com.tor.result.CodeMsg;
import com.tor.util.LabelUtil;
import com.tor.util.MyUserInfo;
import com.tor.util.PropertiesUtil;
import iscx.cs.unb.ca.ifm.ISCXFlowMeter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@Service
public class GrabPacketsService {

    @Autowired
    private PacketService testPacketService;

//    @Async("taskExecutor")
//    public CompletableFuture<Integer> insert() throws InterruptedException {
//        Packet packet = new Packet();
//        packet.setPacketPath("111");
//        packet.setType("2222");
//        packet.setPacketName("333333");
//        Thread.sleep(10000);
//        return CompletableFuture.completedFuture(packetService.insertPacket(packet));
//    }

    //多线程同步执行
    @Async("taskExecutor")
    public void grabPackets(String fileName, String grabPalce, String cmd, int packetCount, String protocol, int selectWay) throws InterruptedException, JSchException, SftpException, IOException {
        if (grabPalce.equals("server")) { //服务器抓包
            if (selectWay == 0) { //自己选择参数
                String command = "tcpdump " + protocol + " -c " + packetCount;
                log.info("grabPackets：构造的cmd命令:{}", command);
                connectRemote(command, fileName);
            } else { //通过自己输入的tcpdump命令
                connectRemote(cmd, fileName);
            }
            if (ISCXFlowMeter.singlePcap(PropertiesUtil.getRemoteToLocalPath() + fileName, PropertiesUtil.getPcapCsvPath())) {
                log.info("grabPackets：数据包转换成功");
            } else {
                log.info("grabPackets：数据包转换失败");
            }
            String csvFullPath = PropertiesUtil.getPcapCsvPath() + "ISCX_" + fileName + ".csv";
            LabelUtil.singleCsvLabel(csvFullPath, "train"); //训练集打标签
            Packet packet = new Packet();
            packet.setPacketName(fileName);
            packet.setType("train");
            packet.setPacketPath(PropertiesUtil.getRemoteToLocalPath() + fileName);
            packet.setCsvPath(csvFullPath);
            int row = testPacketService.insertPacket(packet);
            log.info("数据包插入数据库受影响行数：{}", row);
        } else { //本地抓包
            if (isOSLinux()) {
                try {
                    grabPacketsLocalLinux(fileName, cmd, packetCount, protocol, selectWay);
                } catch (Exception e) {
                    throw new GlobalException(CodeMsg.TCPDUMP_ERROR);
                }
            } else {
                log.info("windows 平台无法捕获数据包");
                throw new GlobalException(CodeMsg.WINDOWS_ERROR);
            }
        }
    }

    public void grabPacketsLocalLinux(String fileName, String cmd, int packetCount, String protocol, int selectWay) {
        String fullFile = PropertiesUtil.getPcapPath() + fileName;
        String command;
        if (selectWay == 0) { //自己选择参数
            command = "tcpdump " + protocol + " -c " + packetCount + " -w " + fullFile;
        } else {
            command = cmd + " -w " + fullFile;
        }
        log.info("grabPackets：构造的cmd命令:{}", command);
        try {
            LabelUtil.execute(command);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.TCPDUMP_ERROR);
        }
        if (ISCXFlowMeter.singlePcap(fullFile, PropertiesUtil.getPcapCsvPath())) {
            log.info("grabPackets local：数据包转换成功");
        } else {
            log.info("grabPackets local：数据包转换失败");
        }
        String csvFullPath = PropertiesUtil.getPcapCsvPath() + "ISCX_" + fileName + ".csv";
        LabelUtil.singleCsvLabel(csvFullPath, "test"); //测试集打标签
        Packet packet = new Packet();
        packet.setPacketName(fileName);
        packet.setType("test");
        packet.setPacketPath(PropertiesUtil.getPcapPath() + fileName);
        packet.setCsvPath(csvFullPath);
        int row = testPacketService.insertPacket(packet);
        log.info("数据包插入数据库受影响行数：{}", row);
    }


    private boolean isOSLinux() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        System.out.println(os);
        return os != null && !os.toLowerCase().contains("windows");
    }

    private boolean isWindows() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        System.out.println(os);
        return os != null && os.toLowerCase().contains("windows");
    }


    /**
     * 连接远程服务器，执行抓包命令，并且将抓取的包下载到本地
     *
     * @throws JSchException
     * @throws IOException
     * @throws SftpException
     */
    private void connectRemote(String cmd, String fileName) throws JSchException, IOException, SftpException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(PropertiesUtil.getRemoteRootName(), PropertiesUtil.getremoteIP(), 22);

        ProxyHTTP proxyHTTP = new ProxyHTTP(PropertiesUtil.getHttpProxy(), PropertiesUtil.getHttpProxyPort());
        session.setProxy(proxyHTTP);

        session.setPassword(PropertiesUtil.getRemoteRootPassword()); // 设置密码
        session.setUserInfo(new MyUserInfo()); //需要实现Jsch包中的UserInfo,UIKeyboardInteractive接口，用以保存用户信息，以及进行键盘交互式认证并执行命令。
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");//在代码里需要跳过检测。否则会报错找不到主机
        session.setConfig(config); // 为Session对象设置properties
        int timeout = 30000;
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立与远程服务器的连接回话
        String command = cmd + " -w " + PropertiesUtil.getRemotePcapPath() + fileName;
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);
        InputStream in = channel.getInputStream();
        channel.connect();
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) {
                    break;
                }
                System.out.print(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) {
                    continue;
                }
                System.out.println("exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        log.info("start download channel file!");
        channelSftp.cd(PropertiesUtil.getRemotePcapPath());
        File file = new File(PropertiesUtil.getRemoteToLocalPath() + fileName);
        channelSftp.get(fileName, new FileOutputStream(file));
        log.info("Download Success!");
        channelSftp.disconnect();
        log.info("end execute channel sftp!");
        channel.disconnect();
        session.disconnect();
    }
}

