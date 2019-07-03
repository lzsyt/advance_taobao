package com.kzq.advance.mapper;

import com.kzq.advance.domain.Trades;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradesMapper {
    int deleteByPrimaryKey(Long tid);

    int insert(Trades record);

    int insertSelective(Trades record);

    Trades selectByPrimaryKey(Long tid);

    int updateByPrimaryKeySelective(Trades record);

    int updateByPrimaryKey(Trades record);

    List<Trades> selectNOSellerMemo();

    String getShopTokenByTid(String tid);
}