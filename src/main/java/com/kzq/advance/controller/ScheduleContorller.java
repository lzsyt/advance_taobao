package com.kzq.advance.controller;

import com.kzq.advance.service.ITradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class ScheduleContorller {
    @Autowired
    ITradesService TradesService;
    @Scheduled(cron = "0/4 * * * * ?")//每天13.53分执行一次 文章的最后会说明一下 corn的用法
    public void test(){
        //获取淘宝订单
       // List<Trades> result=TbaoUtils.findOrders(StringUtils.parseDateTime("2018-12-29 02:59:59"),StringUtils.parseDateTime("2018-12-29 10:59:59"));
       /* String param=TbaoUtils.findOrdersforStr();
        TradesService.addWsbill(param);*/
       // TradesService.insertTrades(result);

    }

}
