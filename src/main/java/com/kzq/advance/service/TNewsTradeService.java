package com.kzq.advance.service;

import com.taobao.api.domain.Trade;

import java.util.List;

public interface TNewsTradeService {

    public List<Trade> findOrders(String shopId);
}
