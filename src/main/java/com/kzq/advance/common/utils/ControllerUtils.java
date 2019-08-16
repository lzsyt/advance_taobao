package com.kzq.advance.common.utils;

import com.kzq.advance.domain.TShop;
import com.taobao.api.domain.Refund;
import com.taobao.api.domain.Trade;

import java.util.ArrayList;
import java.util.List;

public class ControllerUtils {
    public static void isRefund(Trade trade, TShop tShop) {
        List<Refund> refundList = TbaoUtils.getRefund(tShop.getShopToken(), new ArrayList<Refund>(), 1L, trade.getBuyerNick());
        for (Refund refund : refundList) {
            if (refund.getTid().equals(trade.getTid())) {
                switch (refund.getStatus()) {
                    case "CLOSED":// 退款关闭
                        trade.setStatus("0");
                        break;
                    case "WAIT_SELLER_AGREE": //买家已经申请退款，等待卖家同意
                        trade.setStatus("1");
                        break;
                    case "WAIT_BUYER_RETURN_GOODS":// 卖家已经同意退款，等待买家退货
                        trade.setStatus("2");
                        break;
                    case "WAIT_SELLER_CONFIRM_GOODS":// 买家已经退货，等待卖家确认收货
                        trade.setStatus("2");
                        break;
                    case "SELLER_REFUSE_BUYER":// 买家已经退货，等待卖家确认收货
                        trade.setStatus("2");
                        break;
                    case "SUCCESS":// 退款成功
                        trade.setStatus("2");
                        break;
                }
            }
        }
    }
}
