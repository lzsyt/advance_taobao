package com.kzq.advance;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.common.utils.TradeUtil;
import com.kzq.advance.domain.TNewTrades;
import com.kzq.advance.domain.TShop;
import com.kzq.advance.domain.t_new_trades_copy;
import com.kzq.advance.mapper.TNewTradesMapper;
import com.kzq.advance.mapper.TShopMapper;
import com.kzq.advance.mapper.t_new_trades_copyMapper;
import com.kzq.advance.service.ITradesService;
import com.kzq.advance.service.impl.InformationServiceImpl;
import com.taobao.api.domain.Trade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class debugTest {

    Logger logger = LoggerFactory.getLogger(debugTest.class);


    @Autowired
    ITradesService iTradesService;

    @Autowired
    private TShopMapper tShopMapper;


    @Autowired
    private t_new_trades_copyMapper tNewTradesCopyMapper;


    @Autowired
    private TNewTradesMapper tNewTradesMapper;



    @Autowired
    private InformationServiceImpl informationService;


    @Test
    public void test1() {
//        Example example = Example.builder(t_new_trades_copy.class).where(Sqls.custom().andEqualTo("status", 0).orEqualTo("status", 5)).build();

        List<t_new_trades_copy> t_new_trades_copyList = tNewTradesCopyMapper.selectByExample(null);
        List<TNewTrades> tNewTradesList = tNewTradesMapper.selectByExample(null);
        int count = 0;
//        for (t_new_trades_copy t_new_trades_copy:t_new_trades_copyList) {
//            boolean flag = false;
//            for (TNewTrades tnewTrades:tNewTradesList) {
//                if (String.valueOf(t_new_trades_copy.getTid()).equals(String.valueOf(tnewTrades.getTid()))){
//                    flag = true;
//                    count++;
//                }
//            }
//            if (!flag){
//                System.out.println("tid:" + t_new_trades_copy.getTid() + "线上有线下没有");
//            }
//        }
//        System.out.println(count);


        for (TNewTrades TNewTrades : tNewTradesList) {
            boolean flag = false;
            for (t_new_trades_copy t : t_new_trades_copyList) {
                if (String.valueOf(t.getTid()).equals(String.valueOf(TNewTrades.getTid()))) {
                    if (!t.getStatus().equals(TNewTrades.getStatus())){
                        logger.info("tid={}的状态不对，接口查出来的状态为{}，数据库中的状态为{}", t.getTid(), TNewTrades.getStatus(), t.getStatus());
                    }


                    flag = true;
                    count++;
                }
            }
            if (!flag) {
                System.out.println("tid:" + TNewTrades.getTid() + "线上没有线下有");
            }
        }
        System.out.println(count);

    }


    @Test
    public void test() throws InterruptedException {

        int count = 0;

        Example example = Example.builder(TNewTrades.class).where(Sqls.custom().andEqualTo("status", 0).orEqualTo("status", 5)).build();

        List<TNewTrades> tNewTrades = tNewTradesMapper.selectByExample(example);

        for (TNewTrades t : tNewTrades) {
            Trade trade = new Trade();
            BeanUtils.copyProperties(t,trade);

            TShop selectShop = tShopMapper.findBySellerNick(trade.getSellerNick());
            TradeUtil.isRefund(trade, selectShop);

            if (!t.getStatus().equals(trade.getStatus())){
                logger.info("tid={} ,状态不一致，退款接口查出来的状态为{},数据库中的状态为{},创建时间{},",trade.getTid(),t.getStatus(),trade.getStatus(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t.getCreatedTime()));

                count++;
            }
            Thread.sleep(3000L);
        }
        System.out.println(count);
    }

    @Test
    public void test2(){
        List<TShop> tShopList = tShopMapper.selectAll();
        for (TShop t : tShopList) {
            iTradesService.downCategory(String.valueOf(t.getId()));
        }
    }



    @Test
    public void test3(){
//        System.out.println(TbaoUtils.getTrade("buyer_nick,buyer_memo,buyer_message", "560632321404214825", "6202830f0fe0d7624a0e1a6d5d1dbf854ede57ZZ05efca32455269330").getTrade());
//        System.out.println(TbaoUtils.getTrade("buyer_nick,buyer_memo,buyer_message", "560632321404214825", "6202830f0fe0d7624a0e1a6d5d1dbf854ede57ZZ05efca32455269330").getTrade().getBuyerMemo());
//        System.out.println(TbaoUtils.getTrade("buyer_nick,buyer_memo,buyer_message", "560632321404214825", "6202830f0fe0d7624a0e1a6d5d1dbf854ede57ZZ05efca32455269330").getTrade().getBuyerMessage());
        System.out.println(TbaoUtils.findOneOrder("560632321404214825", "6202830f0fe0d7624a0e1a6d5d1dbf854ede57ZZ05efca32455269330").getTrade().getBuyerMessage());
        System.out.println(TbaoUtils.findOneOrder("560632321404214825", "6202830f0fe0d7624a0e1a6d5d1dbf854ede57ZZ05efca32455269330").getTrade().getBuyerNick());
        System.out.println(TbaoUtils.findOneOrder("560632321404214825", "6202830f0fe0d7624a0e1a6d5d1dbf854ede57ZZ05efca32455269330").getTrade().getSellerNick());
    }

}
