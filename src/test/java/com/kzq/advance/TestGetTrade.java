package com.kzq.advance;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.Trades;
import com.kzq.advance.domain.TtradesOrder;
import com.kzq.advance.mapper.TShopMapper;
import com.kzq.advance.service.ITradesService;
import com.kzq.advance.service.impl.InformationServiceImpl;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGetTrade {


    @Autowired
    private ITradesService tradesService;

    @Resource
    private TShopMapper tShopMapper;

    @Autowired
    private InformationServiceImpl informationService;


    @Test
    public void test() {
        String tid = "222039342296450103";
        String token = "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914";
        String field = "tid,receiver_address,status,receiver_name,payment,receiver_mobile,pay_time,buyer_message,seller_memo,seller_nick,orders,order";
        Trade trade = TbaoUtils.getTrade(field, tid, token).getTrade();
        Trades trades = new Trades();
        BeanUtils.copyProperties(trade, trades);
        System.out.println(trades);

        TtradesOrder ttradesOrder = new TtradesOrder();
        List<Order> orderList = trade.getOrders();
        for (Order o : orderList) {
            BeanUtils.copyProperties(o, ttradesOrder);
            System.out.println(ttradesOrder);
        }
        System.out.println("");
    }

    @Test
    public void test2() {
        String topic = "taobao_trade_TradeChanged";
        String content = "{\"buyer_nick\":\"吴孟佳1996\",\"payment\":\"580.00\",\"status\":\"TRADE_FINISHED\",\"iid\":551143060058,\"oid\":581220578790936982,\"tid\":581220578790936982,\"type\":\"guarantee_trade\",\"seller_nick\":\"动力足旗舰店\"}";
        informationService.infoRefund(topic, content);
    }


    @Test
    public void test3() {

    }
}
