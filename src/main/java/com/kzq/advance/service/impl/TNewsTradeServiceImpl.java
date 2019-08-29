package com.kzq.advance.service.impl;

import com.kzq.advance.domain.TNewTrades;
import com.kzq.advance.domain.TNewTradesOrder;
import com.kzq.advance.mapper.TNewTradesMapper;
import com.kzq.advance.service.TNewsTradeService;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TNewsTradeServiceImpl implements TNewsTradeService {

    @Autowired
    private TNewTradesMapper tNewTradesMapper;

    @Override
    public List<Trade> findOrders(String shopId) {

        List<Trade> tradeList = new ArrayList<>();
        //根据shopid 查询已付款未发货且为下载过的订单
        List<TNewTrades> tNewTradesList = tNewTradesMapper.getTrades(shopId);
        Trade trade = null;
        for (TNewTrades tNewTrades : tNewTradesList) {
            trade = new Trade();
            BeanUtils.copyProperties(tNewTrades, trade);

            //插入子订单
            trade.setOrders(format(tNewTrades.gettNewTradesOrders()));
            tradeList.add(trade);

            // 下载过就逻辑删除，为了少更新几个字段，所以重新创建对象，只更新必要字段
            tNewTrades = new TNewTrades();
            tNewTrades.setTid(trade.getTid());
            tNewTrades.setIsDel(Byte.parseByte("1"));
            tNewTrades.setDelTime(new Date());
            tNewTradesMapper.updateByPrimaryKeySelective(tNewTrades);

        }
        return tradeList;
    }

    private List<Order> format(List<TNewTradesOrder> tNewTradesOrderList) {
        //如果不为空就插入
        List<Order> orderList = null;
        if (tNewTradesOrderList!=null&&tNewTradesOrderList.size() != 0) {
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
