package com.kzq.advance.mapper;

import com.kzq.advance.domain.TGoodsLinkMap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TGoodsLinkMapMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TGoodsLinkMap record);

    int insertSelective(TGoodsLinkMap record);

    TGoodsLinkMap selectBySkuId(Long skuId);

    int updateBySkuIdSelective(TGoodsLinkMap record);

    int updateByPrimaryKey(TGoodsLinkMap record);
}