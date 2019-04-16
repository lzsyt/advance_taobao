package com.kzq.advance.mapper;

import com.kzq.advance.domain.Trades;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TradesMapper {
    int deleteByPrimaryKey(Long tid);

    int insert(Trades record);

    int insertSelective(Trades record);

    Trades selectByPrimaryKey(Long tid);

    int updateByPrimaryKeySelective(Trades record);

    int updateByPrimaryKey(Trades record);
}