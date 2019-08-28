package com.kzq.advance.controller;


import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.TShop;
import com.kzq.advance.mapper.TShopMapper;
import com.kzq.advance.service.impl.InformationServiceImpl;
import com.taobao.api.domain.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TNewsTradesController {

    @Resource
    private TShopMapper tShopMapper;

    @Autowired
    private InformationServiceImpl informationService;

    @RequestMapping("tnewstrade")
    public String data(){
        List<TShop> shopList = tShopMapper.selectAll();
        for (TShop shop : shopList) {
            List<Trade> tradeList = TbaoUtils.findOrders(shop.getShopToken(), new ArrayList<Trade>(), 1L);
            for (Trade trade:tradeList) {
                informationService.changeTrade(trade.getTid(), trade.getSellerNick());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "1";
    }
}
