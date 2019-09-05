package com.kzq.advance.service.impl;

import com.kzq.advance.domain.TShop;
import com.kzq.advance.mapper.TShopMapper;
import com.kzq.advance.service.TShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class TShopServiceImpl implements TShopService {

    @Resource
    private TShopMapper tShopMapper;

    @Override
    public TShop findBySellerNick(String sellerNick) {
        return tShopMapper.findBySellerNick(sellerNick);
    }

    @Override
    public TShop findById(String shopId) {
        return tShopMapper.selectByPrimaryKey(shopId);
    }
}
