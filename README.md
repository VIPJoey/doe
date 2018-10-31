# Doe 发布 [V1.1.0]

## 功能特性
-   修复grid序号问题
-   修复spring 版本过低问题
-   增加注册中心管理页面
-   增加接口version和group支持
-   provider 修改为starter方式
-   增加守护程序，停止、重启、发布
        * 需要python2 环境支持
        * 进入deploy目录，
            执行 chmod +x SimpleHttpServer.py && ./SimpleHttpServer.py 命令启动守护进程. 

# Doe 发布 [V1.0.0]

前段时间排查某问题的时候，想要快速知道某些dubbo接口（三无）的响应结果，但不想启动项目（因为这些项目不是你负责的，不会部署而且超级笨重），也不想新建一个dubbo客户端项目（占地方），也不想开telnet客户端连接口（麻烦而且有限制）。所以扣了dubbo的netty模块源码，封装了个收发客户端集成一个工具，可以快速调试dubbo接口。

![极简模式](https://github.com/VIPJoey/doe/blob/master/deploy/easy.png)
![普通模式](https://github.com/VIPJoey/doe/blob/master/deploy/normal.png)


## 目录结构

-   mmc-dubbo-api 接口项目，主要用于测试。
-   mmc-dubbo-provider dubbo提供者项目，主要用于测试。
-   mmc-dubbo-doe 主项目，实现dubbo接口调试。
-   deploy 部署文档


## 功能特性

-   极简模式：通过dubbo提供的telnet协议收发数据。
-   普通模式：通过封装netty客户端收发数据。
-   用例模式：通过缓存数据，方便下一次操作，依赖普通模式。
-   增加依赖：通过调用maven命令，下载jar包和热加载到系统，主要用来分析接口方法参数，主要作用在普通模式。
-   依赖列表：通过分析pom文件，展示已经加载的jar包。


## 其它特性

-   springboot 整合 redis，支持spring el 表达式。
-   springboot 整合 thymeleaf。
-   springboot 整合 logback。
-   netty rpc 实现原理。

## 开发环境

-   jdk 1.8
-   maven 3.5.3
-   dubbo 2.6.1
-   lombok 1.16.20
-   idea 2018
-   windows 7

## 安装步骤

-   安装jdk
-   安装maven，并设置好环境变量，仓库目录。
-   进入mmc-dubbo-api目录，执行mvn clean install命令，省api的jar包。
-   进入mmc-dubbo-doe目录，执行mvn clean install 命令，在target目录生成dubbo-doe.jar
-   在F盘（可以任意盘）创建目录F:\app\doe
-   把dubbo-doe.jar拷贝到F:\app\doe
-   把deploy目录中的所有文件拷贝到F:\app\doe
-   如果您电脑安装了git bash，可以在bash窗口运行 ./deploy.sh start，否则如果没有安装git bash，只能打开cmd切换到F:\app\doe目录，然后执行java -jar dubbo-doe.jar --spring.profiles.active=prd
-   打开浏览器，访问地址：http://localhost:9876/doe/home/index
-   日志目录：/app/applogs/doe
-   全剧终
