package com.kzq.advance.common.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    /**
     * 获取配置文件
     *
     * @return 配置Props
     */
    public static Properties getProperties() {
        // 读取配置文件
        Resource resource = new ClassPathResource("/config/application.properties");
        Properties props = new Properties();
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
