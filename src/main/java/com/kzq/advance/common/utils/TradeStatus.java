package com.kzq.advance.common.utils;

public enum TradeStatus {
    WAIT_BUYER_PAY("WAIT_BUYER_PAY", "等待买家付款"),
    WAIT_SELLER_SEND_GOODS("WAIT_SELLER_SEND_GOODS", "等待卖家发货"),
    SELLER_CONSIGNED_PART("SELLER_CONSIGNED_PART", "卖家部分发货"),
    WAIT_BUYER_CONFIRM_GOODS("WAIT_BUYER_CONFIRM_GOODS", "等待买家确认收货"),
    TRADE_BUYER_SIGNED("TRADE_BUYER_SIGNED", "买家已签收（货到付款专用）"),
    TRADE_FINISHED("TRADE_FINISHED", "交易成功"),
    TRADE_CLOSED("TRADE_CLOSED", "交易关闭"),
    TRADE_CLOSED_BY_TAOBAO("TRADE_CLOSED_BY_TAOBAO", "交易被淘宝关闭"),
    TRADE_NO_CREATE_PAY("TRADE_NO_CREATE_PAY", "没有创建外部交易（支付宝交易）"),
    WAIT_PRE_AUTH_CONFIRM("WAIT_PRE_AUTH_CONFIRM", "余额宝0元购合约中"),
    PAY_PENDING("PAY_PENDING", "外卡支付付款确认中"),
    ALL_WAIT_PAY("ALL_WAIT_PAY", "所有买家未付款的交易"),
    ALL_CLOSED("ALL_CLOSED", "所有关闭的交易"),
    PAID_FORBID_CONSIGN("PAID_FORBID_CONSIGN", "该状态代表订单已付款但是处于禁止发货状态");


    public static String getValueByKey(String key) {
        if (null == key) {
            return null;
        }

        for (TradeStatus status : TradeStatus.values()) {
            if (status.getKey().equals(key)){
                return status.getValues();
            }
        }
        return null;
    }

    private final String key;
    private final String values;


    TradeStatus(String key, String values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public String getValues() {
        return values;
    }
}
