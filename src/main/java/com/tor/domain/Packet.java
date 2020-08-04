package com.tor.domain;

public class Packet {
    //id
    private Integer id;
    //数据包名称
    private String packetName;
    //数据包路径
    private String packetPath;
    //数据包类型，本地/服务器
    private String type;
    //数据包对应的csv文件存储路径
    private String csvPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPacketName() {
        return packetName;
    }

    public void setPacketName(String packetName) {
        this.packetName = packetName;
    }

    public String getPacketPath() {
        return packetPath;
    }

    public void setPacketPath(String packetPath) {
        this.packetPath = packetPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCsvPath() {
        return csvPath;
    }

    public void setCsvPath(String csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "id=" + id +
                ", packetName='" + packetName + '\'' +
                ", packetPath='" + packetPath + '\'' +
                ", type='" + type + '\'' +
                ", csvPath='" + csvPath + '\'' +
                '}';
    }
}
