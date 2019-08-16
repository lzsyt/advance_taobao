package com.kzq.advance;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.Trades;
import com.kzq.advance.domain.TtradesOrder;
import com.kzq.advance.service.ITradesService;
import com.kzq.advance.service.InformationService;
import com.kzq.advance.service.impl.TradesImpl;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.domain.TradeOrderInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGetTrade {


    @Autowired
    private ITradesService tradesService;

    @Autowired
    private InformationService informationService;


    @Test
    public void test(){
        String tid = "222039342296450103";
        String token = "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914";
        String field = "tid,receiver_address,status,receiver_name,payment,receiver_mobile,pay_time,buyer_message,seller_memo,seller_nick,orders,order";
        Trade trade = TbaoUtils.getTrade(field, tid, token).getTrade();
        Trades trades = new Trades();
        BeanUtils.copyProperties(trade, trades);
        System.out.println(trades);

        TtradesOrder ttradesOrder = new TtradesOrder();
        List<Order> orderList = trade.getOrders();
        for (Order o :orderList) {
            BeanUtils.copyProperties(o, ttradesOrder);
            System.out.println(ttradesOrder);
        }
        System.out.println("");
    }

    @Test
    public void test2(){
        String topic = "taobao_trade_TradeChanged";
        String content = "{\"buyer_nick\":\"若德斯满\",\"payment\":\"579.00\",\"status\":\"WAIT_SELLER_SEND_GOODS\",\"iid\":580897696635,\"oid\":577942721885407170,\"tid\":577942721885407170,\"type\":\"guarantee_trade\",\"seller_nick\":\"光合旗舰店\"}";
        informationService.infoRefund(topic, content);
    }
}
