package com.kzq.advance.mapper;

import com.kzq.advance.domain.LogisticsCompany;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogisticsCompanyMapper {
    int deleteByPrimaryKey(String cpCode);

    int insert(LogisticsCompany record);

    int insertSelective(LogisticsCompany record);

    LogisticsCompany selectByPrimaryKey(String cpCode);

    int updateByPrimaryKeySelective(LogisticsCompany record);

    int updateByPrimaryKey(LogisticsCompany record);
}