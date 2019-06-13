package com.kzq.advance;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.TShop;
import com.kzq.advance.domain.Trades;
import com.kzq.advance.mapper.TShopMapper;
import com.kzq.advance.mapper.TradesMapper;
import com.taobao.api.domain.Refund;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRefund {

    private final static Logger logger = LoggerFactory.getLogger(TestRefund.class);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Resource
    private TShopMapper tShopMapper;

    @Resource
    private TradesMapper tradesMapper;


    @Test
    public void xiaoxifuwuapi(){
        List<TShop> shopList = tShopMapper.selectAll();
        for (TShop t : shopList) {
            logger.info(t.getShopName() + "....................................");
            Date start = null;
            Date end = null;
            try {
                start = formatter.parse("2019-04-00 00:00:00");
                end = formatter.parse("2019-05-00 00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            refund("6201c2251aedcbaf8909faeb8b0ac0ZZbffae87097672592456823406", start, end);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }


    public void refund(String token,Date start,Date end){

        List<Refund> refundList =  TbaoUtils.getRefund(token, new ArrayList<Refund>(), 1L, start, end);


        for (Refund refund:refundList) {
            if (!refund.getStatus().equals("CLOSED")&&!refund.getStatus().equals("SELLER_REFUSE_BUYER")){
                Trades localTrades = new Trades();
                localTrades.setTid(refund.getTid());
                if (String.valueOf(refund.getTid()).equals("462452259333727525")||String.valueOf(refund.getTid()).equals("463843875447855161")){
                    System.out.println("tid：" + refund.getTid() + "的状态为：" + refund.getStatus());
                }

                localTrades.setIsRefund("1");
                tradesMapper.updateByPrimaryKeySelective(localTrades);
            }
        }

        if (end.getTime()<new Date().getTime()){
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(start);
            calendar.add(Calendar.MONTH, 1);
            start = calendar.getTime();
            calendar.setTime(end);
            calendar.add(Calendar.MONTH, 1);
            end = calendar.getTime();
            refund(token, start, end);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
