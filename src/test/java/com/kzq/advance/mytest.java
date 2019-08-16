package com.kzq.advance;

import com.kzq.advance.common.utils.TbaoUtils;
import com.taobao.api.domain.Trade;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class mytest {

    @Test
    public void test(){
        List<Trade> trades = TbaoUtils.findOrders("6200824224b73677a8d4375add3237e3ZZ21bb86aa67d8c305543718", new ArrayList<Trade>(), 1L);
        System.out.println("");
    }

    @Test
    public void test2(){
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.DAY_OF_YEAR,-1);//日期加10天
//        Date dt1=calendar.getTime();
//        String reStr = new SimpleDateFormat("yyyy-MM-dd:HH:mm:sss").format(dt1);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR,-1);//日期加10天
        Date start=calendar.getTime();
        Date end = new Date();

        String reStr = new SimpleDateFormat("yyyy-MM-dd:HH:mm:sss").format(start);
        String reStr2 = new SimpleDateFormat("yyyy-MM-dd:HH:mm:sss").format(end);

        System.out.println(reStr);
        System.out.println(reStr2);
    }



    @Test
    public void test3(){
        String status = TbaoUtils.getTrade("status", "552213516686569304", "6200824224b73677a8d4375add3237e3ZZ21bb86aa67d8c305543718").getTrade().getStatus();
        System.out.println(status);
    }
}
