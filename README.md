# Doe 发布 [V1.0.0]


前段时间做某个需求的时候，想要快速知道某些dubbo接口（三无）的响应结果，但不想启动项目（因为这些项目不是你负责的，不会部署而且超级笨重），也不想新建一个dubbo客户端项目（占地方），也不想开telnet客户端连接口（麻烦而且有限制）。所以扣了dubbo的netty模块源码，封装了个收发客户端集成一个工具，可以快速调试dubbo接口。

![极简模式](https://github.com/VIPJoey/doe/blob/master/deploy/easy.png)
![普通模式](https://github.com/VIPJoey/doe/blob/master/deploy/normal.png)


------------------------------

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
-   jdk1.8
-   maven3.5.3
-   idea2018


## 安装步骤
    
