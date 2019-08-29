package com.kzq.advance.controller;


import com.kzq.advance.common.singleton.TmcClientUtil;
import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.TShop;
import com.kzq.advance.mapper.TShopMapper;
import com.kzq.advance.service.impl.InformationServiceImpl;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.tmc.TmcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String data(@RequestParam("pwd")String pwd){
        //设置一个密码，防止破解
        if (!pwd.equals("3311300zmn3311300")){
            return "-1";
        }
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


    @RequestMapping("closeTmcClient")
    public String closeTmcClient(@RequestParam("pwd")String pwd){
        //设置一个密码，防止破解
        if (!pwd.equals("3311300zmn3311300")){
            return "-1";
        }
        TmcClient client = TmcClientUtil.getTmcClientUtil().getClient();
        client.close();
        return "1";
    }
}
