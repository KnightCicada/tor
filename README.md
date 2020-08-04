1.需要配置filepath.properties  
2.需要下载python  
3.python安装pandas  
4.windows环境需要安装winpcap  
5.导入jpcapnet.dll 可以放到jdk的bin目录中  
6.需要将jpcap.dll放到jdk/jre/bin目录下  
7.python换源   
```
linux: 
修改 ~/.pip/pip.conf (没有就创建一个)， 内容如下：
[global]
index-url = https://pypi.tuna.tsinghua.edu.cn/simple

windows: 
直接在user目录中创建一个pip目录，如：C:\Users\xx\pip，在pip 目录下新建文件pip.ini，内容如下
或者：win+R 打开用户目录%HOMEPATH%，在此目录下创建 pip 文件夹，在 pip 目录下创建 pip.ini 文件, 内容如下
[global]
timeout = 6000
index-url = https://pypi.tuna.tsinghua.edu.cn/simple
trusted-host = pypi.tuna.tsinghua.edu.cn
```
```
linux 使用jpcap
使用命令：
git clone --recursive https://github.com/mgodave/Jpcap.git
将源文件都保存在了Jpcap的文件夹里。

编译
在目录Jpcap/src/main/c中，执行：
make
注：应该确保安装有build-essential和libpcap包，由于这些包一般都已经有了，所以没有列在过程中。
将生成的libjpcap.so拷贝到目录：$JAVA_HOME/jre/lib/<arch>目录中。其中，arch对应的是计算机架构，如i386、sparc、amd64等等。而$JAVA_HOME对于Debian系列的Linux，一般指/usr/lib/jvm/default-java目录。
也可以将这个文件拷贝到应用程序的目录下。
```

```
解决非root用户不能使用tcpdump问题：
centos/redhat下安装tcpdump
sudo yum install -y tcpdump
在tcpdump所在目录下执行
sudo chmod u+s tcpdump
```

还需要下载Geo-lite文件，放到resources
