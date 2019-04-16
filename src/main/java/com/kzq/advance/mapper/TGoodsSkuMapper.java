package com.kzq.advance.mapper;

import com.kzq.advance.domain.TGoodsSku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TGoodsSkuMapper {
    int deleteByPrimaryKey(Long skuId);

    int insert(TGoodsSku record);

    int insertSelective(TGoodsSku record);

    TGoodsSku selectByPrimaryKey(Long skuId);

    int updateByPrimaryKeySelective(TGoodsSku record);

    int updateByPrimaryKey(TGoodsSku record);
}