package com.kzq.advance.mapper;

import com.kzq.advance.domain.TSellerCat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TSellerCatMapper {

    int insertSelective(TSellerCat record);

    int updateByPrimaryKeySelective(TSellerCat record);

    List<TSellerCat> find(Integer shopId);

}