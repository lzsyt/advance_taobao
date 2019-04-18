package com.kzq.advance.mapper;

import com.kzq.advance.domain.TUpdateLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TUpdateLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUpdateLog record);

    int insertSelective(TUpdateLog record);

    TUpdateLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUpdateLog record);

    int updateByPrimaryKey(TUpdateLog record);
}