/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.mmc.dubbo.doe.cache.RedisResolver;
import com.mmc.dubbo.doe.client.ProcessClient;
import com.mmc.dubbo.doe.context.Const;
import com.mmc.dubbo.doe.context.DoeClassLoader;
import com.mmc.dubbo.doe.context.TaskContainer;
import com.mmc.dubbo.doe.dto.PomDTO;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.exception.DoeException;
import com.mmc.dubbo.doe.model.PomModel;
import com.mmc.dubbo.doe.service.PomService;
import com.mmc.dubbo.doe.util.DOMUtil;
import com.mmc.dubbo.doe.util.FileUtil;
import com.mmc.dubbo.doe.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.validation.constraints.NotNull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * dependency service.
 *
 * @author Joey
 * @date 2018/6/17 9:42
 */
@Service("pomService")
@Slf4j
public class PomServiceImpl implements PomService {

    private Lock locker = new ReentrantLock();

    @Value("${doe.dependency.pom}")
    private String pomXml;

    @Value("${doe.dependency.lib}")
    private String libPath = null;

    @Autowired
    private RedisResolver redisResolver;

    @Override
    public ResultDTO<PomDTO> invoke() {

        PomDTO dto = new PomDTO();
        ProcessClient processClient = new ProcessClient(dto, redisResolver, pomXml, libPath);

        // just can only invoke one task to downloaded the jars.
        // we can invoke more task after we have finished all code actually.
        if (processClient.isRunning()) {
            return ResultDTO.createErrorResult("some task was already running at background, please try again for a few minutes later.", PomDTO.class);
        }

        try {

            locker.lock();

            // clear old directory
            deleteJars(libPath);

            // download jars asynchronously
            log.info("fork another thread to download jars.");
            TaskContainer.getTaskContainer().execute(processClient);
            log.info("success fork another thread to download jars.");

        } catch (Exception e) {
            throw e;
        } finally {
            locker.unlock();
        }

        // return the success signal and redirect another url to get real time information.
        return ResultDTO.createSuccessResult("the download task is running at background, please wait...", dto, PomDTO.class);
    }

    @Override
    public ResultDTO<PomDTO> invoke(@NotNull PomDTO dto) throws Exception {

        ProcessClient processClient = new ProcessClient(dto, redisResolver, pomXml, libPath);

        // just can only invoke one task to downloaded the jars.
        // we can invoke more task after we have finished all code actually.
        if (processClient.isRunning()) {
            return ResultDTO.createErrorResult("some task was already running at background, please try again for a few minutes later.", PomDTO.class);
        }

        // parse the pom
        log.info("begin to parse the pom.");
        List<PomModel> models = parsePom(dto.getPom());

        // check the model is good or not
        checkModels(models);

        // check maven configuration
        checkMaven(models);

        try {

            locker.lock();

            // append the parse content to the end of real pom.xml.
            log.info("begin to append the parse content to the end of {}.", pomXml);
            appendPom(models, pomXml);

            // download jars asynchronously
            log.info("fork another thread to download jars.");
            TaskContainer.getTaskContainer().execute(processClient);
            log.info("success fork another thread to download jars.");

        } catch (Exception e) {
            throw e;
        } finally {
            locker.unlock();
        }

        // return the success signal and redirect another url to get real time information.
        return ResultDTO.createSuccessResult("the download task is running at background, please wait...", dto, PomDTO.class);
    }

    // the mvn environment variable must be set.
    private void checkMaven(List<PomModel> models) {

    }

    @Override
    public void appendPom(List<PomModel> models, @NotNull String pomXml) throws Exception {

        File file = new File(pomXml);

        // 1.得到DOM解析器的工厂实例
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // 2.从DOM工厂里获取DOM解析器
        DocumentBuilder db = dbf.newDocumentBuilder();
        // 3.解析XML文档，得到document，即DOM树
        Document doc = db.parse(file);
        // root
        Element rootDependency = (Element) doc.getElementsByTagName("dependencies").item(0);

        for (PomModel model : models) {

            // 创建节点
            Element dependencyElement = doc.createElement("dependency");
            // 创建group节点
            Element groupElement = doc.createElement("groupId");
            groupElement.appendChild(doc.createTextNode(model.getGroupId()));
            // 创建artifactId节点
            Element artifactIdElement = doc.createElement("artifactId");
            artifactIdElement.appendChild(doc.createTextNode(model.getArtifactId()));
            // 创建version节点
            Element versionElement = doc.createElement("version");
            versionElement.appendChild(doc.createTextNode(model.getVersion()));
            // 添加父子关系
            dependencyElement.appendChild(groupElement);
            dependencyElement.appendChild(artifactIdElement);
            dependencyElement.appendChild(versionElement);
            // 追加节点
            rootDependency.appendChild(dependencyElement);
        }
        // 保存xml文件
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // 格式化
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        // 设置编码类型
        transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
        DOMSource domSource = new DOMSource(doc);
        StreamResult result = new StreamResult(new FileOutputStream(file));
        // 把DOM树转换为xml文件
        transformer.transform(domSource, result);
    }

    private void checkModels(List<PomModel> models) {

        if (CollectionUtils.isEmpty(models)) {
            throw new DoeException("Can't parse any dependency, please check your pom before you execute the do parse request.");
        }
        models.forEach(m -> {
            if (m.isBroken()) {
                throw new DoeException(StringUtil.format("The content of pom is Incomplete.[{}]", JSON.toJSONString(m)));
            }
        });

    }

    @Override
    public List<PomModel> parsePom(@NotNull String xml) throws IOException, SAXException {

        List<PomModel> models = new ArrayList<>();

        xml = "<root>" + xml + "</root>";

        Document document = DOMUtil.parse(xml);
        Node root = document.getElementsByTagName("root").item(0);

        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node dependencyNode = root.getChildNodes().item(i);
            String nodeName = dependencyNode.getNodeName();

            if ("dependency".equals(nodeName)) {

                PomModel model = new PomModel();
                for (int j = 0; j < dependencyNode.getChildNodes().getLength(); j++) {
                    Node childNode = dependencyNode.getChildNodes().item(j);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) childNode;

                        if ("groupId".equals(element.getNodeName())) {
                            model.setGroupId(element.getFirstChild().getNodeValue());
                        } else if ("artifactId".equals(element.getNodeName())) {
                            model.setArtifactId(element.getFirstChild().getNodeValue());
                        } else if ("version".equals(element.getNodeName())) {
                            model.setVersion(element.getFirstChild().getNodeValue());
                        }
                    }
                }
                models.add(model);
            }
        }
        return models;
    }

    @Override
    public ResultDTO<String> getRealTimeMsg(@NotNull String requestId) {

        String key = StringUtil.format(Const.DOE_DOWNLOAD_JAR_MESSAGE, requestId);

        ResultDTO<String> ret = ResultDTO.createSuccessResult("SUCCESS", String.class);

        boolean isRunning = redisResolver.hasKey(Const.DOE_DOWNLOAD_JAR_TASK);

        if (!isRunning) {

            // if the task was done, get all message prevent the task running too fast.
            List<Object> list = redisResolver.lGet(key, 0, -1);
            String data = list.stream().map(l -> l.toString()).collect(Collectors.joining("\r\n"));

            ret.setMsg("download completed!");
            ret.setData(data);
            ret.setCode(Const.COMPLETE_FLAG);

        } else {

            // loop time of queue length
            long size = redisResolver.lGetListSize(key);
            StringBuilder sb = new StringBuilder();
            while (--size > 0) {
                String value = (String) redisResolver.lPop(key);
                sb.append("\r\n");
                sb.append(value);
            }

            ret.setData(sb.toString());
            ret.setCode(Const.RUNNING_FlAG); // tell the jquery continue to ask message.
        }

        return ret;
    }

    @Override
    public ResultDTO<String> loadJars(String path) {

        String realPath = (StringUtils.isEmpty(path)) ? this.libPath : path;
        DoeClassLoader classLoader = new DoeClassLoader(realPath);
        try {
            classLoader.clearCache();
            classLoader.loadJars();
            return ResultDTO.createSuccessResult("load jars completely and successfully", String.class);
        } catch (Exception e) {
            return ResultDTO.handleException("occur an error when load jars", null, e);
        }

    }

    @Deprecated // since v1.1.0
    public ResultDTO<String> loadJars$$(String path) throws NoSuchMethodException, MalformedURLException {

        String fullLibPath = StringUtils.isEmpty(path) ? this.libPath : path;

        if (StringUtils.isEmpty(fullLibPath)) {
            return ResultDTO.createErrorResult(StringUtil.format("can't found the path {}", fullLibPath), String.class);
        }

        if (!new File(fullLibPath).exists()) {
            throw new DoeException(StringUtil.format("the path[{}] is not exists.", fullLibPath));
        }

        log.info("begin to load jars from {}.", fullLibPath);

        // check for changes prevent to do useless job.
        checkForChanges();

        // 系统类库路径
        File libPath = new File(fullLibPath);

        // 获取所有的.jar和.zip文件
        File[] jarFiles = libPath.listFiles((dir, name) -> name.endsWith(".jar") || name.endsWith(".zip"));

        if (jarFiles != null) {
            // 从URLClassLoader类中获取类所在文件夹的方法
            // 对于jar文件，可以理解为一个存放class文件的文件夹
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            boolean accessible = method.isAccessible();     // 获取方法的访问权限
            try {
                if (!accessible) {
                    method.setAccessible(true);     // 设置方法的访问权限
                }
                // 获取系统类加载器
                URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                for (File file : jarFiles) {
                    URL url = file.toURI().toURL();
                    try {
                        method.invoke(classLoader, url);
                        log.debug("读取jar文件[name={}]", file.getName());
                    } catch (Exception e) {
                        log.error("读取jar文件[name={}]失败", file.getName());
                    }
                }
                return ResultDTO.createSuccessResult("load jars completely and successfully", String.class);

            } finally {
                method.setAccessible(accessible);
            }
        } else {
            return ResultDTO.createErrorResult(StringUtil.format("Can't found any jars from {}.", fullLibPath), String.class);
        }

    }

    /**
     * list all dependency.
     *
     * @param dto
     * @return
     */
    @Override
    public List<PomModel> listJars(PomDTO dto) throws ParserConfigurationException, IOException, SAXException {

        List<PomModel> result = new ArrayList<>();

        String pomPath = (StringUtils.isEmpty(dto.getPath())) ? pomXml : dto.getPath();
        File file = new File(pomPath);

        // 1.得到DOM解析器的工厂实例
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // 2.从DOM工厂里获取DOM解析器
        DocumentBuilder db = dbf.newDocumentBuilder();
        // 3.解析XML文档，得到document，即DOM树
        Document doc = db.parse(file);
        // list
        NodeList list = doc.getElementsByTagName("dependency");

        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;
                PomModel model = new PomModel();
                model.setGroupId(element.getElementsByTagName("groupId").item(0).getTextContent());
                model.setArtifactId(element.getElementsByTagName("artifactId").item(0).getTextContent());
                model.setVersion(element.getElementsByTagName("version").item(0).getTextContent());

                result.add(model);
            }
        }

        return result;
    }

    private void checkForChanges() {

        // TODO
        // check if any changes
    }

    @Override
    public String loadPomFile(String pomXmlPath) {

        String pomPath = StringUtils.isEmpty(pomXmlPath) ? pomXml : pomXmlPath;
        return FileUtil.readToString(pomPath);

    }

    @Override
    public Boolean overridePomFile(String pomXmlPath, String content) {

        String pomPath = StringUtils.isEmpty(pomXmlPath) ? pomXml : pomXmlPath;

        FileUtil.WriteStringToFile(pomPath, content);

        return true;

    }

    @Override
    public ResultDTO<String> deleteJars(String path) {

        String realPath = (StringUtils.isEmpty(path)) ? this.libPath : path;

        if (StringUtils.isEmpty(realPath)) {
            throw new DoeException(StringUtil.format("can't found the path {}", path));
        }

        File libPath = new File(realPath);
        if (!libPath.exists()) {
            throw new DoeException(StringUtil.format("the path[{}] is not exists.", path));
        }

        File[] jarFiles = libPath.listFiles((dir, name) -> name.endsWith(".jar") || name.endsWith(".zip"));

        if (jarFiles != null) {
            for (File file : jarFiles) {
                log.info("begin to delete file {}.", file.getAbsolutePath());
                boolean ret = file.delete();
                if (!ret) {
                    try {
                        log.info("begin to force to delete file {}.", file.getAbsolutePath());
                        FileUtils.forceDelete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return ResultDTO.handleSuccess("delete sucess!", path);
    }


}
