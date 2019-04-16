package com.kzq.advance.common.utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源（数据源切换）
 */


public class DynamicDataSource extends AbstractRoutingDataSource {


    public static ThreadLocal key = new ThreadLocal<String>();

    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("---DynamicDataSource---------"+DynamicDataSourceHolder.getDataSource());
               return DynamicDataSourceHolder.getDataSource();

    }

}


