package com.feidao.dingzk.h2server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    public String filePath;
    private String fullPath;
    private final Properties properties = new Properties();

    public PropertyUtil() {
    }

    /**
     * 使用默认类路径下找到的第一个具有该名称的文件进行初始化
     *
     * @param filePath 文件名称
     */
    public PropertyUtil(String filePath) {
        init(filePath);
    }

//    public PropertyUtil(InputStream in) {
//        try {
//            this.properties.clear();
//            this.properties.load(in);
//        } catch (Exception e) {
//            logger.error("PropertyUtil初始化>>>导入properties文件出错, {}", e);
//        } finally {
//            try {
//                in.close();
//            } catch (IOException e) {
//                logger.error("PropertyUtil初始化>>>关闭流出错, {}", e);
//            }
//        }
//    }

    private void init(String path) {
        this.filePath = path;
        this.fullPath = Objects.requireNonNull(getClass().getClassLoader().getResource(this.filePath)).getFile();
        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream(this.filePath);
            this.properties.clear();
            this.properties.load(in);
        } catch (IOException e) {
            logger.error("PropertyUtil初始化>>>导入properties文件出错, {}", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("PropertyUtil初始化>>>关闭流出错, {}", e);
            }
        }
    }

    public String get(String key) {
        return this.properties.getProperty(key);
    }

    @SuppressWarnings({"unused"})
    public String getOrElse(String key, String defaultValue) {
        String v = this.get(key);
        return null == v ? defaultValue : v;
    }

    /**
     * 使用默认类路径下找到的第一个具有该名称的文件进行初始化, 因此整个类路径不能有同名的资源文件<br/>
     * 不同IDE编译代码后的运行时类路径会有差异, 如IDEA中编译过的文件在target/classes或target/test-classes<br/>
     * 由于maven打包时设置了类路径包含resources，因此在resources下不能有同名的资源文件<br/>
     *
     * @param values properties key value (and comment)
     */
    public void set(String... values) {
        this.properties.setProperty(values[0], values[1]);
        OutputStream out = null;
        try {
            out = new FileOutputStream(this.fullPath, false);
            String comments = "";
            if (values.length >= 3) {
                comments = values[2];
            }
            if ("".equals(comments) || null == comments)
                this.properties.store(out, "");
            else
                this.properties.store(out, comments);
        } catch (Exception e) {
            logger.error("PropertyUtil设置值>>>保存出错, {}", e);
        }

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                logger.error("PropertyUtil设置值>>>关闭流出错, {}", e);
            }
        }
    }

    @SuppressWarnings({"unused"})
    public boolean containsKey(String key) {
        return this.properties.containsKey(key);
    }

    @SuppressWarnings({"unused"})
    public boolean containsValue(String value) {
        return this.properties.containsValue(value);
    }

    public String getFullPath() {
        return this.fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
