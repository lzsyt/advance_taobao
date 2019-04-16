package com.kzq.advance.mapper;

import com.kzq.advance.domain.TWsBillDetail;

public interface TWsBillDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(TWsBillDetail record);

    int insertSelective(TWsBillDetail record);

    TWsBillDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TWsBillDetail record);

    int updateByPrimaryKey(TWsBillDetail record);
}