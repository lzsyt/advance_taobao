package com.kzq.advance.service.impl;

import com.kzq.advance.domain.TNewTrades;
import com.kzq.advance.domain.TNewTradesOrder;
import com.kzq.advance.mapper.TNewTradesMapper;
import com.kzq.advance.mapper.TNewTradesOrderMapper;
import com.kzq.advance.service.TNewsTradeService;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TNewsTradeServiceImpl implements TNewsTradeService {

    @Autowired
    private TNewTradesMapper tNewTradesMapper;

    @Autowired
    private TNewTradesOrderMapper tNewTradesOrderMapper;

    @Override
    public List<Trade> findOrders(String shopId) {

        List<Trade> tradeList = new ArrayList<>();

        //<editor-fold desc="编写查询条件">
        Example example = Example.builder(TNewTrades.class)
                .select()
                .where(Sqls.custom().andEqualTo("status", "0").andEqualTo("shopId", shopId).andEqualTo("isDel", 0))
                .build();
        //</editor-fold>
        List<TNewTrades> tNewTradesList = tNewTradesMapper.selectByExample(example);
        Trade trade = null;
        for (TNewTrades tNewTrades : tNewTradesList) {
            trade = new Trade();
            BeanUtils.copyProperties(tNewTrades, trade);
            // 下载过就逻辑删除
            tNewTrades.setIsDel(Byte.parseByte("1"));
            tNewTrades.setDelTime(new Date());
            tNewTradesMapper.updateByPrimaryKeySelective(tNewTrades);

            //插入子订单
            trade.setOrders(getOrder(tNewTrades.getTid()));

            tradeList.add(trade);
        }

        return tradeList;
    }

    private List<Order> getOrder(Long tid) {
        List<Order> orderList = null;
        //<editor-fold desc="查询条件">
        Example tNewTradesOrderExample = Example.builder(TNewTradesOrder.class)
                .select()
                .where(Sqls.custom().andEqualTo("tid", tid))
                .build();
        //</editor-fold>
        List<TNewTradesOrder> tNewTradesOrderList = tNewTradesOrderMapper.selectByExample(tNewTradesOrderExample);
        //如果不为空就插入
        if (tNewTradesOrderList != null) {
            orderList = new ArrayList<>();
            for (TNewTradesOrder tNewTradesOrder : tNewTradesOrderList) {
                Order order = new Order();
                BeanUtils.copyProperties(tNewTradesOrder, order);
                order.setNum(Long.valueOf(tNewTradesOrder.getNum()));
                order.setNumIid(Long.valueOf(tNewTradesOrder.getNumIid()));
                orderList.add(order);
            }
        }
        return orderList;
    }
}
