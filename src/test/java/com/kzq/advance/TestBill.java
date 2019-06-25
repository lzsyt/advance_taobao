package com.kzq.advance;

import com.kzq.advance.service.impl.TradesImpl;
import com.taobao.api.response.TradeFullinfoGetResponse;
import org.junit.Test;

import java.util.Map;

public class TestBill {




    @Test
    public void test(){
//        TradeFullinfoGetResponse

        String content = "{\"buyer_nick\":\"龙岩刘兴\"," +
                "\"payment\":\"300.00\"," +
                "\"status\":\"WAIT_SELLER_SEND_GOODS\"," +
                "\"iid\":41310897757," +
                "\"oid\":498515520458585782," +
                "\"seller_memo\":\"6.21梦露,464612224575585782,这个这单一起,板子破了3块,补差价,发3个板子,\"," +
                "\"seller_flag\":0," +
                "\"tid\":498515520458585782," +
                "\"type\":\"guarantee_trade\"," +
                "\"seller_nick\":\"光合硅能旗舰店\"}";
        String content2 = "{\"buyer_nick\":\"梁聪0813\",\"payment\":\"3790.00\",\"status\":\"TRADE_CLOSED_BY_TAOBAO\",\"iid\":0,\"oid\":498590560186548436,\"seller_flag\":0,\"tid\":498590560186548436,\"type\":\"guarantee_trade\",\"seller_nick\":\"光合旗舰店\"}\n";
        Map<String, String> map = TradesImpl.getMapFromContent(content2);

    }




    @Test
    public void tset2(){
//        String string = "123456:789";
//        String s1 = string.substring(0, string.indexOf(":"));
//        String s = string.substring(string.indexOf(":"));
//        System.out.println();
    }



}
