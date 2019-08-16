package com.kzq.advance.service.impl;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.TShop;
import com.kzq.advance.domain.Trades;
import com.kzq.advance.domain.TtradesOrder;
import com.kzq.advance.mapper.TShopMapper;
import com.kzq.advance.mapper.TradesMapper;
import com.kzq.advance.mapper.TtradesOrderMapper;
import com.kzq.advance.service.InformationService;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class InformationServiceImpl implements InformationService {

    Logger logger =  LoggerFactory.getLogger(InformationService.class);

    @Resource
    private TradesMapper tradesMapper;

    @Resource
    private TtradesOrderMapper tradesOrderMapper;

    @Resource
    private TShopMapper shopMapper;


    /**
     * 通知修改退款的或取消退款的
     *
     * @param topic   主题，也就是消息类型
     * @param content 消息内容
     */
    @Async
    public void infoRefund(String topic, String content) {

        topic = topic.trim();
        content = content.trim();
        Map<String, String> parameter = getMapFromContent(content);

        if (parameter.get("tid") == null) {
            throw new RuntimeException("tid 为空");
        }
        Long tid = Long.valueOf(parameter.get("tid"));

        switch (topic) {
            case "taobao_refund_RefundCreated":   //申请退款
                refundCreated(tid);
                break;
            case "taobao_refund_RefundSuccess":   //申请退款
                refundSucceed(tid);
                break;
            case "taobao_refund_RefundClosed":   //取消退款
                refundClose(tid);
                break;
            case "taobao_trade_TradeMemoModified":  //taobao的数据交易备注修改
                tradeMemoChange(tid, parameter.get("seller_memo"));  //交易备注修改
                break;
            case "taobao_trade_TradeChanged":  //订单状态修改
//                String sellerNick = parameter.get("seller_nick");
//                if (sellerNick == null) {
//                    throw new RuntimeException("sellerNick 为空");
//                }
//                changeTrade(tid, sellerNick);
                break;
            default:
                logger.info("收到其他消息：topic=[{}],content=[{}]", topic, content);
                break;
        }
    }


    /**
     * 订单状态更改
     */
    private void changeTrade(Long tid, String seller_nick) {
        Trades trades = new Trades();

        logger.info("订单状态更改，tid={}", tid);
        //查店铺
        TShop tShop = new TShop();
        tShop.setShopName(seller_nick);
        String token = shopMapper.selectShop(tShop).getShopToken();
        //得到淘宝订单
        Trade trade = TbaoUtils.getTrade("tid,receiver_address,status,receiver_name,payment,receiver_mobile,pay_time,buyer_memo,seller_memo,seller_nick,orders,order", String.valueOf(tid), token).getTrade();
        //等待卖家发货
        switch (trade.getStatus()) {
            case "WAIT_SELLER_SEND_GOODS":   //WAIT_SELLER_SEND_GOODS 等待买家发货
                logger.info("插入已付款订单，id=[{}]", tid);
                BeanUtils.copyProperties(trade, trades);
                trades.setStatus("1");
                if (trade.getOrders() != null) {
                    TtradesOrder ttradesOrder = new TtradesOrder();
                    for (Order order : trade.getOrders()) {
                        BeanUtils.copyProperties(order, ttradesOrder);
                        tradesOrderMapper.insertSelective(ttradesOrder);
                    }
                }
                tradesMapper.insertSelective(trades);
                break;
            case "SELLER_CONSIGNED_PART": //SELLER_CONSIGNED_PART  买家部分发货     WAIT_BUYER_CONFIRM_GOODS 等待买家确认收货
                logger.info("买家部分发货；tid={}", tid);
                trades.setTid(trade.getTid());
                trades.setStatus("5");
                tradesMapper.updateByPrimaryKeySelective(trades);
                break;
            case "WAIT_BUYER_CONFIRM_GOODS": //WAIT_BUYER_CONFIRM_GOODS  等待买家确认收货
                logger.info("等待买家确认收货；tid={}", tid);
                trades.setTid(trade.getTid());
                trades.setStatus("2");
                tradesMapper.updateByPrimaryKeySelective(trades);
                break;
            case "TRADE_BUYER_SIGNED":  // TRADE_BUYER_SIGNED  买家收货
                logger.info("买家收货；tid={}", tid);
                trades.setTid(trade.getTid());
                trades.setStatus("3");
                tradesMapper.updateByPrimaryKeySelective(trades);
                break;
            case "TRADE_FINISHED":   //TRADE_FINISHED 交易成功
                logger.info("交易成功；tid={}", tid);
                trades.setTid(trade.getTid());
                trades.setStatus("4");
                tradesMapper.updateByPrimaryKeySelective(trades);
                break;
            default:
                logger.info("其他状态:{}", trade.getStatus());
                break;
        }
    }

    /**
     * 申请退款
     */
    private void refundCreated(Long tid) {
        Trades trades = new Trades();
        logger.info("申请退款,tid = [{}]", tid);
        trades.setTid(tid);
        trades.setIsRefund("1");
        tradesMapper.updateByPrimaryKeySelective(trades);
    }

    /**
     * 退款成功
     * @param tid
     */
    private void refundSucceed(Long tid) {
        Trades trades = new Trades();
        logger.info("退款成功,tid = [{}]", tid);
        trades.setTid(tid);
        trades.setIsRefund("2");
        tradesMapper.updateByPrimaryKeySelective(trades);
    }

    /**
     * 取消退款
     */
    private void refundClose(Long tid) {
        Trades trades = new Trades();
        logger.info("取消退款,tid = [{}]", tid);
        trades.setTid(tid);
        trades.setIsRefund("0");
        tradesMapper.updateByPrimaryKeySelective(trades);
    }

    /**
     * 交易备注修改
     */
    private void tradeMemoChange(Long tid, String sellermemo) {
        Trades trades = new Trades();
        if (org.apache.commons.lang.StringUtils.isBlank(sellermemo)) {
            //某些情况下这一条订单没有备注，比如
            // String tid = "284704838052170693";
            // String token = "620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742";
            logger.info("没有备注，不做操作，tid=【{}】", tid);
        } else {
            logger.info("交易备注修改,tid= [{}],seller_memo = [{}]", tid, sellermemo);
            trades.setTid(tid);
            trades.setSellerMemo(sellermemo);
            tradesMapper.updateByPrimaryKeySelective(trades);
        }
    }


    /**
     * 得到message.content 的tid
     *
     * @param infoContent message.content
     * @return 返回字段
     */
    public static Map<String, String> getMapFromContent(String infoContent) {
        Map<String, String> map = new HashMap<>();
        infoContent = infoContent.replace("{", "");
        infoContent = infoContent.replace("}", "");
//        infoContent = infoContent.replaceAll("\"", "");
        String[] stringArrayList = infoContent.split(",\"");
        for (String string : stringArrayList) {
            string = string.replaceAll("\"", "");
            if (!StringUtils.isEmpty(string)) {
                String value = string.substring(string.indexOf(":") + 1);
                String key = string.substring(0, string.indexOf(":"));
                map.put(key, value);
            }
        }
        return map;
    }
}
