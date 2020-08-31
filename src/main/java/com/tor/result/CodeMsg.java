package com.tor.result;

public class CodeMsg {

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500104, "访问太频繁！");
    //判别模块
    public static CodeMsg NULL_DATA = new CodeMsg(500200, "未读取到数据，请稍后刷新！");
    public static CodeMsg INVIVAD_FILE = new CodeMsg(500201, "数据包后缀必须为\".pcap\"！");
    public static CodeMsg TRANSFER_EXCEPT = new CodeMsg(500202, "PCAP转换为CSV异常！");
    public static CodeMsg LABEL_ERROR = new CodeMsg(500202, "pcap转换遇到问题！");
    public static CodeMsg NULL_MODEL = new CodeMsg(500203, "数据库中没有模型，无法判别！");
    public static CodeMsg DUPLICATE_FILE = new CodeMsg(500204, "文件已经存在！");
    public static CodeMsg FILE_NOT_EXIST = new CodeMsg(500205, "文件不存在！");
    public static CodeMsg DELETE_FILE_ERROR = new CodeMsg(500205, "文件删除失败！");
    public static final CodeMsg DELETE_MODEL_ERROR = new CodeMsg(500206, "删除模型失败");
    public static final CodeMsg WINDOWS_ERROR = new CodeMsg(500206, "windows平台无法抓包");
    public static final CodeMsg TCPDUMP_ERROR = new CodeMsg(500206, "tcpdump命令出现错误，请输入正确的tcpdump命令");
    public static final CodeMsg PACKET_UPLOAD_ERROR = new CodeMsg(500207, "数据包上传失败");
    public static final CodeMsg TRAIN_ERROR = new CodeMsg(500208, "模型训练失败");
    public static final CodeMsg TEST_ERROR = new CodeMsg(500209, "分类失败");


    private int code;
    private String msg;

    private CodeMsg() {
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }


}
