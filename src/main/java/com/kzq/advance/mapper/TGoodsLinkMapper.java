package com.kzq.advance.mapper;

import com.kzq.advance.domain.TGoodsLink;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TGoodsLinkMapper {
    int deleteByPrimaryKey(Long numIid);

    int insert(TGoodsLink record);

    int insertSelective(TGoodsLink record);

    TGoodsLink selectByPrimaryKey(Long numIid);

    int updateByPrimaryKeySelective(TGoodsLink record);

    int updateByPrimaryKey(TGoodsLink record);

    List<TGoodsLink> selectByPic();

    List<TGoodsLink> selectByShop(String nick);


}