package com.kzq.advance.mapper;

import com.kzq.advance.domain.TGoodsSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TGoodsSkuMapper {
    int deleteByPrimaryKey(Long skuId);

    int insert(TGoodsSku record);

    int insertSelective(TGoodsSku record);

    TGoodsSku selectByPrimaryKey(Long skuId);

    List<TGoodsSku> selectByNumId(Long numId);

    int countByNumId(Long numId);

    int updateByPrimaryKeySelective(TGoodsSku record);

    int updateByPrimaryKey(TGoodsSku record);

    int delSku(long skuId);

    List<TGoodsSku> selectByNumIds(List<Long> list);

}