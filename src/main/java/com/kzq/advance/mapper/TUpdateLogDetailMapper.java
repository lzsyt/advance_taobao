package com.kzq.advance.mapper;

import com.kzq.advance.domain.TUpdateLogDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TUpdateLogDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUpdateLogDetail record);

    int insertSelective(TUpdateLogDetail record);

    TUpdateLogDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUpdateLogDetail record);

    int updateByPrimaryKey(TUpdateLogDetail record);
}