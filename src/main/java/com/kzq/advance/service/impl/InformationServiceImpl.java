package com.kzq.advance.service.impl;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.TNewTrades;
import com.kzq.advance.domain.TNewTradesOrder;
import com.kzq.advance.domain.TShop;
import com.kzq.advance.domain.Trades;
import com.kzq.advance.mapper.TNewTradesMapper;
import com.kzq.advance.mapper.TNewTradesOrderMapper;
import com.kzq.advance.mapper.TShopMapper;
import com.kzq.advance.mapper.TradesMapper;
import com.kzq.advance.service.InformationService;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class InformationServiceImpl implements InformationService {

    Logger logger =  LoggerFactory.getLogger(InformationService.class);

    @Autowired
    private TNewTradesMapper newTradesMapper;

    @Resource
    private TradesMapper tradesMapper;

    @Resource
    private TNewTradesOrderMapper tNewTradesOrderMapper;

    @Resource
    private TShopMapper shopMapper;


    /**
     * 通知修改退款的或取消退款的
     *
     * @param topic   主题，也就是消息类型
     * @param content 消息内容
     */
    @Async
    @Transactional(isolation = Isolation.REPEATABLE_READ)//不可重复读
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
                String sellerNick = parameter.get("seller_nick");
                changeTrade(tid, sellerNick);
                break;
            default:
                logger.info("收到其他消息：topic=[{}],content=[{}]", topic, content);
                break;
        }
    }


    /**
     * 订单状态更改
     */
    public void changeTrade(Long tid, String seller_nick) {
        TNewTrades newTrades = new TNewTrades();

        logger.info("订单状态更改，tid={}", tid);
        //查店铺
        TShop tShop = new TShop();
        tShop.setShopName(seller_nick);
        tShop = shopMapper.selectShop(tShop);
        String token = tShop.getShopToken();
        //得到淘宝订单
        Trade trade = TbaoUtils.findOneOrder( String.valueOf(tid), token).getTrade();
//        trade.setStatus("WAIT_SELLER_SEND_GOODS");
        //等待卖家发货
        switch (trade.getStatus()) {
            case "WAIT_SELLER_SEND_GOODS":   //WAIT_SELLER_SEND_GOODS 等待买家发货
                logger.info("插入已付款订单，id=[{}]", tid);
                //判断是否存在这条数据，因为如果我这边没有及时处理淘宝消息，淘宝会重发，一般间隔为10s
                Example example = Example.builder(TNewTrades.class)
                        .select().where(Sqls.custom().andEqualTo("tid", tid)).build();
                if (newTradesMapper.selectByExample(example).size()!=0) {
                    logger.info("数据库中已有此订单,拒绝重复插入,tid={}", tid);
                    break;
                }
                BeanUtils.copyProperties(trade, newTrades);
                newTrades.setStatus("0");
                newTrades.setCreatedTime(new Date());
                newTrades.setShopId(tShop.getId());
                if (trade.getOrders() != null) {
                    TNewTradesOrder tNewTradesOrder = new TNewTradesOrder();
                    for (Order order : trade.getOrders()) {
                        BeanUtils.copyProperties(order, tNewTradesOrder);
                        tNewTradesOrder.setTid(String.valueOf(trade.getTid()));
                        tNewTradesOrder.setNum(Math.toIntExact(order.getNum()));
                        tNewTradesOrder.setNumIid(String.valueOf(order.getNumIid()));
                        tNewTradesOrder.setPayment(order.getPayment());
                        tNewTradesOrderMapper.insertSelective(tNewTradesOrder);
                    }
                }
                newTradesMapper.insertSelective(newTrades);
                break;
            case "SELLER_CONSIGNED_PART": //SELLER_CONSIGNED_PART  卖家部分发货
                logger.info("买家部分发货；tid={}", tid);
                newTrades.setTid(trade.getTid());
                newTrades.setStatus("5");
                newTrades.setModifyTime(new Date());
                newTradesMapper.updateByPrimaryKeySelective(newTrades);
                break;
            case "WAIT_BUYER_CONFIRM_GOODS": //WAIT_BUYER_CONFIRM_GOODS  等待卖家确认收货
                logger.info("等待买家确认收货；tid={}", tid);
                newTrades.setTid(trade.getTid());
                newTrades.setStatus("2");
                newTrades.setModifyTime(new Date());
                newTradesMapper.updateByPrimaryKeySelective(newTrades);
                break;
            case "TRADE_BUYER_SIGNED":  // TRADE_BUYER_SIGNED  买家收货
                logger.info("买家收货；tid={}", tid);
                newTrades.setTid(trade.getTid());
                newTrades.setStatus("3");
                newTrades.setModifyTime(new Date());
                newTradesMapper.updateByPrimaryKeySelective(newTrades);
                break;
            case "TRADE_FINISHED":   //TRADE_FINISHED 交易成功
                logger.info("交易成功；tid={}", tid);
                newTrades.setTid(trade.getTid());
                newTrades.setStatus("4");
                newTrades.setModifyTime(new Date());
                newTradesMapper.updateByPrimaryKeySelective(newTrades);
                break;
            case "TRADE_CLOSED_BY_TAOBAO":   // 淘宝关闭交易，付款以前，卖家或买家主动关闭交易
                logger.info("淘宝关闭交易；tid={}", tid);
                newTrades.setTid(trade.getTid());
                newTrades.setStatus("TRADE_CLOSED_BY_TAOBAO");
                newTrades.setModifyTime(new Date());
                newTradesMapper.updateByPrimaryKeySelective(newTrades);
                break;
            case "TRADE_CLOSED":   // 付款以后退款成功
                logger.info("付款以后退款成功；tid={}", tid);
                newTrades.setTid(trade.getTid());
                newTrades.setStatus("TRADE_CLOSED");
                newTrades.setModifyTime(new Date());
                newTradesMapper.updateByPrimaryKeySelective(newTrades);
                break;
            default:
                logger.info("其他状态:{},tid:{}", trade.getStatus(),tid);
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
        // tNewTrades
        TNewTrades tNewTrades = new TNewTrades();
        tNewTrades.setTid(tid);
        tNewTrades.setStatus("1");
        trades.setModifyTime(new Date());
        newTradesMapper.updateByPrimaryKeySelective(tNewTrades);
    }

    /**
     * 退款成功
     * @param tid tid
     */
    private void refundSucceed(Long tid) {
        Trades trades = new Trades();
        logger.info("退款成功,tid = [{}]", tid);
        trades.setTid(tid);
        trades.setIsRefund("2");
        tradesMapper.updateByPrimaryKeySelective(trades);
        // tNewTrades
        TNewTrades tNewTrades = new TNewTrades();
        tNewTrades.setTid(tid);
        tNewTrades.setStatus("2");
        trades.setModifyTime(new Date());
        newTradesMapper.updateByPrimaryKeySelective(tNewTrades);
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
        // tNewTrades
        TNewTrades tNewTrades = new TNewTrades();
        tNewTrades.setTid(tid);
        tNewTrades.setStatus("0");
        trades.setModifyTime(new Date());
        newTradesMapper.updateByPrimaryKeySelective(tNewTrades);
    }

    /**
     * 交易备注修改
     */
    private void tradeMemoChange(Long tid, String sellermemo) {
        if (org.apache.commons.lang.StringUtils.isBlank(sellermemo)) {
            //某些情况下这一条订单没有备注，比如
            // String tid = "284704838052170693";
            // String token = "620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742";
            logger.info("没有备注，不做操作，tid=【{}】", tid);
        } else {
            Trades trades = new Trades();
            logger.info("交易备注修改,tid= [{}],seller_memo = [{}]", tid, sellermemo);
            trades.setTid(tid);
            trades.setSellerMemo(sellermemo);
            tradesMapper.updateByPrimaryKeySelective(trades);
            // tNewTrades
            TNewTrades tNewTrades = new TNewTrades();
            tNewTrades.setTid(tid);
            tNewTrades.setSellerMemo(sellermemo);
            trades.setModifyTime(new Date());
            newTradesMapper.updateByPrimaryKeySelective(tNewTrades);
        }
    }


    /**
     * 得到message.content 的tid
     *
     * @param infoContent message.content
     * @return 返回字段
     */
    private static Map<String, String> getMapFromContent(String infoContent) {
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
