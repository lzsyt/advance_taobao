package com.kzq.advance.mapper;

import com.kzq.advance.common.base.BaseMapper;
import com.kzq.advance.domain.TShop;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TShopMapper extends BaseMapper<TShop> {

    TShop selectByPrimaryKey(Integer id);

    TShop selectShop(TShop shop);

    String findShopNameByShopId(Integer id);

    //查询所有sessionKey
    List<TShop> selectAll();

    //根据店铺名查询店铺
    public TShop findBySellerNick(String sellerNick);
}