package com.kzq.advance;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.mapper.TradesMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testinfo {

    @Resource
    private TradesMapper tradesMapper;


    @Test
    public void test(){
        String topic = "taobao_refund_RefundClosed";
        String infoContent = "{\n" +
                "\t\"refund_id\":\"592823138\",\n" +
                "\t\"buyer_nick\":\"碎银子\",\n" +
                "\t\"refund_fee\":\"21.32\",\n" +
                "\t\"oid\":\"110770592823138\",\n" +
                "\t\"tid\":\"463843875447855161\",\n" +
                "\t\"refund_phase\":\"onsale\",\n" +
                "\t\"bill_type\":\"refund_bill\",\n" +
                "\t\"seller_nick\":\"麦包包\",\n" +
                "\t\"modified\":\"2000-12-30 12:32:20\"\n" +
                "}";


//        boolean flag = TbaoUtils.infoRefund(topic, infoContent, tradesMapper);

//        System.out.println(flag);

    }


}
