package com.kzq.advance;

import com.kzq.advance.domain.TShop;
import com.kzq.advance.mapper.TShopMapper;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.TmcUserPermitRequest;
import com.taobao.api.request.TmcUserTopicsGetRequest;
import com.taobao.api.response.TmcUserPermitResponse;
import com.taobao.api.response.TmcUserTopicsGetResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoTest {


    @Resource
    private TShopMapper tShopMapper;

    //查看授权列表
    @Test
    public void xiaoxifuwuapi(){
        List<TShop> shopList = tShopMapper.selectAll();
        for (TShop t : shopList) {
            System.out.print(t.getShopName()+"  :");
            xiaoxifuwuapi(t.getShopName());
        }

    }


    public void xiaoxifuwuapi(String shopName) {
        DefaultTaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", "25500416", "25720ff4e7b9f8c5cfe95827c7e35479");

        TmcUserTopicsGetRequest req = new TmcUserTopicsGetRequest();
        req.setNick(shopName);
        TmcUserTopicsGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
    }



//    授权的
    @Test
    public void power(){
        List<TShop> shopList = tShopMapper.selectAll();
        for (TShop t : shopList) {
            primit(t.getShopToken());
            System.out.println(t.getShopToken() + "店铺的token");
            System.out.println(t.getShopName() + ";店铺已授权");
        }

    }


//    @Test
    public void primit(String token){

        //taobao_refund_RefundSuccess（退款成功消息）
        //taobao_refund_RefundClosed（退款关闭消息）
        //taobao_trade_TradePartlyRefund（子订单退款成功消息）
        //taobao_refund_RefundCreated（退款创建消息）

        //taobao_trade_TradeAlipayCreate（创建支付宝订单消息）



        //光合旗舰店
//        String token = "6200824224b73677a8d4375add3237e3ZZ21bb86aa67d8c305543718";

        DefaultTaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", "25500416", "25720ff4e7b9f8c5cfe95827c7e35479");
        TmcUserPermitRequest req = new TmcUserPermitRequest();
//        req.setTopics("taobao_refund_RefundCreated,taobao_refund_RefundClosed,taobao_trade_TradeMemoModified");
        req.setTopics("taobao_refund_RefundCreated,taobao_refund_RefundClosed,taobao_trade_TradeMemoModified,taobao_jds_TradeTrace");
        TmcUserPermitResponse rsp = null;
        try {
            rsp = client.execute(req, token);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
    }
}
