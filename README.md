
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
-   python 2.6.6



# Doe 发布 [V1.1.0]

## 功能特性

##### 一、新功能
* 增加注册中心管理模块
* 增加编辑依赖模块
* 增加守护程序，停止、重启、重新发布

##### 二. 优化功能
* provider 修改为starter方式
* 增加接口version和group支持

##### 三. 缺陷修复
* 修复grid序号问题
* 修复spring 版本过低问题
* 优化菜单栏展示方式



# Doe 发布 [V1.2.0]

## 功能特性

##### 一、新功能
* 无

##### 二. 优化功能
* 增加mac系统判断
* 增加泛型接口测试
* 修改dubbo依赖为starter方式

##### 三. 缺陷修复
* 无




## 启动方式
* IDEA 启动
    - 安装JDK、并设置环境变量
    - 安装MAVEN，并设置好环境变量，仓库目录
    - 安装REDIS，设置相关配置
    - 安装IDEA，设置IDEA环境
    - 导入项目到IDEA，设置为maven工程，勾选profile环境
    - 根据各自需要，修改application-dev.yml或application-prd.yml配置文件，除了redis配置项，其它建议保持默认配置
    - 在当前IDEA的workspace所在根目录，创建/app/doe目录
        - 例如：application-*.yml为默认配置，且当前IDEA的workspace为F:\idea-workspaces\mmc-workspace\，则在F盘创建F:\app\doe
    - 进入mmc-dubbo-api目录，执行mvn clean install命令，生成api的jar包。
    - 进入mmc-dubbo-doe目录，执行mvn clean install 命令，在target目录生成dubbo-doe.jar
    - 打开mmc-dubbo-doe工程，找到DubboDoeApplication.java类，右键点击运行即可。
    - 默认日志目录：/app/applogs/doe
    - 打开浏览器，访问地址：http://localhost:9876/doe/home/index
* LINUX 启动
    - 安装JDK、并设置环境变量
    - 安装MAVEN，并设置好环境变量，仓库目录
    - 安装REDIS，设置相关配置
    - 安装PYTHON（可选）
    - 执行mkdir -p /app/doe，创建/app/doe目录，注意权限问题
    - 把deploy目录中的所有文件上传到/app/doe
    - 参考IDEA方式，下载DOE源码，并编译生成dubbo-doe.jar，并上传到/app/doe 目录
    - 进入/app/doe 目录，执行chmod +x deploy.sh  
    - 进入/app/doe 目录，执行 ./deploy.sh start 启动项目，支持(start/stop/reload/republish)参数，详细参数用途请阅读deploy.sh源码
    - 进入/app/doe 目录，执行chmod +x SimpleHttpServer.py （可选）
    - 进入/app/doe 目录，执行./SimpleHttpServer.py （可选）
    - 默认日志目录：/app/applogs/doe
    - 打开浏览器，访问地址：http://ip:9876/doe/home/index

## 项目介绍
- https://blog.csdn.net/hanyi_?t=1

## 特别说明
- 由于平时比较忙，仓促写下的代码未免有BUG，请见谅
- 如遇到问题，可以github上留言，或贡献您的代码
