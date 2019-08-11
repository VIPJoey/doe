/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.context;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mmc.dubbo.doe.exception.DoeException;
import com.mmc.dubbo.doe.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 自定义类加载器，做沙箱隔离.
 *
 * @author Joey
 * @date 2019/6/28 14:20
 */
@Slf4j
public class DoeClassLoader extends ClassLoader {


    private final String path;

    private static Map<String, byte[]> classMap = new ConcurrentHashMap<>();


    /**
     * destroy the Parental Entrustment.
     */
    public DoeClassLoader(String path) {
        super(null);
        this.path = path;
    }


    private void scanJarFile(File file) throws Exception {

        JarFile jar = new JarFile(file);

        Enumeration<JarEntry> en = jar.entries();
        while (en.hasMoreElements()) {
            JarEntry je = en.nextElement();
            je.getName();
            String name = je.getName();
            if (name.endsWith(".class")) {

                String className = makeClassName(name);

                try (InputStream input = jar.getInputStream(je); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int bytesNumRead;
                    while ((bytesNumRead = input.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesNumRead);
                    }
                    addClass(className, baos.toByteArray());
                }
            }
        }
        jar.close();
    }

    private String makeClassName(String name) {
        String ret = name.replace("\\", ".")
                .replace("/", ".")
                .replace(".class", "");
        return ret;
    }

    /**
     * load jars from the Specified path.
     */
    public void loadJars() throws Exception {

        if (StringUtils.isEmpty(path)) {
            throw new DoeException(StringUtil.format("can't found the path {}", path));
        }

        File libPath = new File(path);
        if (!libPath.exists()) {
            throw new DoeException(StringUtil.format("the path[{}] is not exists.", path));
        }

        File[] files = libPath.listFiles((dir, name) -> name.endsWith(".jar") || name.endsWith(".zip"));

        if (files != null) {
            for (File file : files) {
                scanJarFile(file);
            }
        }
    }

    /**
     * Add one class dynamically.
     */
    public static boolean addClass(String className, byte[] byteCode) {
        if (!classMap.containsKey(className)) {
            classMap.put(className, byteCode);
            return true;
        }
        return false;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        name = makeClassName(name);

        byte[] stream = get(name);

        if (null != stream) {

            return defineClass(name, stream, 0, stream.length);
        }

        return super.loadClass(name, resolve);

    }

    /**
     * Get class in our classloader rather than system classloader.
     */
    public static Class<?> getClass(String name) throws ClassNotFoundException {
        return new DoeClassLoader("").loadClass(name, false);
    }

    private static byte[] get(String className) {
        return classMap.getOrDefault(className, null);
    }

    private void scanClassFile(File file) {
        if (file.exists()) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                try {
                    byte[] byteCode = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                    String className = file.getAbsolutePath().replace(this.path, "")
                            .replace(File.separator, ".");

                    className = makeClassName(className);

                    addClass(className, byteCode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (file.isDirectory()) {
                for (File f : Objects.requireNonNull(file.listFiles())) {
                    scanClassFile(f);
                }
            }
        }
    }

    /**
     * load classes from the Specified path.
     */
    public void loadClassFile() {
        File[] files = new File(path).listFiles();
        if (files != null) {
            for (File file : files) {
                scanClassFile(file);
            }
        }
    }

    /**
     * clear the class cache.
     */
    public void clearCache() {
        classMap.clear();
    }
}
