package com.kzq.advance.common.config;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.Trades;
import com.kzq.advance.mapper.TradesMapper;
import com.taobao.api.domain.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleUtil {

    private static final Logger getLogger = LoggerFactory.getLogger(ScheduleUtil.class);

    @Resource
    private TradesMapper tradesMapper;

    @Async
    public void compare(String sessionkey, Date start) {
        List<Trade> TaobaoTradesList = TbaoUtils.getTrades(sessionkey, new ArrayList<>(), 1L, start);
        for (Trade trade:TaobaoTradesList) {
            Trades dbTrades=tradesMapper.selectByPrimaryKey(trade.getTid());
            if (dbTrades == null) {
                //报错   没有插入
//                    throw new RuntimeException("订单号为[" + trade.getTid() + "]的订单没有插入");
                getLogger.info("订单号为[" + trade.getTid() + "]的订单没有插入");
            }else if(!dbTrades.getStatus().equals(trade.getStatus())){
                // 报错   状态不对
//                    throw new RuntimeException("订单号为[" + trade.getTid() + "]的订单状态异常，数据库中状态为" + dbTrades.getStatus() + "；淘宝状态为：" + trade.getStatus()+"；");
                getLogger.info("订单号为[{}]的订单状态异常，数据库中状态为[{}]；淘宝状态为：[{}]；", trade.getTid(), dbTrades.getStatus(), trade.getStatus());
            }
        }
    }
}
