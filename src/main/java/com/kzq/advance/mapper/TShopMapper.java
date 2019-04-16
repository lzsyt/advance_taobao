package com.kzq.advance.mapper;

import com.kzq.advance.domain.TShop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TShop record);

    int insertSelective(TShop record);

    TShop selectByPrimaryKey(Integer id);

    TShop selectShop(TShop shop);


    int updateByPrimaryKeySelective(TShop record);

    int updateByPrimaryKey(TShop record);
    //查询所有sessionKey
    List<TShop> selectAll();
}