package com.kzq.advance.common.utils;

import com.kzq.advance.domain.Trades;
import com.kzq.advance.mapper.TradesMapper;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.domain.*;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.LinkException;
import java.util.*;

public class TbaoUtils {

    static Logger logger = LoggerFactory.getLogger(TbaoUtils.class);

    // 正式环境

    static DefaultTaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", "25500416", "25720ff4e7b9f8c5cfe95827c7e35479");
    static final String sessionKey = "620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742";
    static final String sessionKey1 = "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914";
    static String currentSessionKey = "";

    static Map<String, String> session = new HashMap<>();

    /**
     * url:
     * http://gw.api.tbsandbox.com/router/rest
     * AppKey：1025500416
     * 沙箱AppSecret：
     * sandbox4e7b9f8c5cfe95827c7e35479
     */
    // DefaultTaobaoClient  client = new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest", "1025500416", "25720ff4e7b9f8c5cfe95827c7e35479");

    static {


        //采用精简化的JSON结构返回，去除多余JSON节点
        client.setUseSimplifyJson(true);

    }


    public static String getCurrentSessionKey() {
        return currentSessionKey;
    }

    public static void setCurrentSessionKey(String currentSessionKey) {
        TbaoUtils.currentSessionKey = currentSessionKey;
    }

    /**
     * 获取token
     */

    public static void createAuthToken(String code) {

        //采用精简化的JSON结构返回，去除多余JSON节点 code= AJL1Wnp5IhG9uSmV9P76iGrY282518
        TopAuthTokenCreateRequest req = new TopAuthTokenCreateRequest();
        req.setCode(code);
        TopAuthTokenCreateResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
    }


    public List<Trade> getSoldIncrement(Long offer,List<Trade> tradeList,String sessionKey,Date start ,Date end){
        TradesSoldIncrementvGetRequest req = new TradesSoldIncrementvGetRequest();
        req.setFields("tid,type,status,payment,orders");
        req.setStartCreate(start);
        req.setEndCreate(end);
        req.setPageNo(offer);
        req.setPageSize(40L);
        req.setUseHasNext(true);
        TradesSoldIncrementvGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        tradeList.addAll(rsp.getTrades());
        if (rsp.getHasNext()){
            getSoldIncrement(++offer, tradeList, sessionKey, start, end);
        }
        return tradeList;
    }


    /**
     *  获取订单的部分信息
     * @param tid
     * @param fields
     * @return
     */
    public static Trade getBillDetail(String tid, String fields,String sessionKey) {
        TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
        req.setFields(fields);
        // req.setTid(315968833434800371L);
        req.setTid(Long.parseLong(tid));
        TradeFullinfoGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());

        return rsp.getTrade();
    }

    /**
     *
     * @param refundId
     * @param sessionKey
     * @return
     */
    public static Refund getRefundDetail(String refundId,String sessionKey){
        RefundGetRequest req = new RefundGetRequest();
        req.setFields("refund_id,tid,title,address,good_return_time,created,modified,good_return_time");
        req.setRefundId(Long.parseLong(refundId));
        RefundGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp.getRefund();
    }


    /**
     * @param sessionKey
     * @return
     */
    public static List<Refund> getRefund(String sessionKey, List<Refund> refundList, Long offer, Date start, Date end) {
        RefundsReceiveGetRequest req = new RefundsReceiveGetRequest();
        req.setFields("refund_id,tid,status");
        req.setPageNo(offer);
        req.setPageSize(100L);
        req.setUseHasNext(true);
        req.setStartModified(start);
        req.setEndModified(end);
        RefundsReceiveGetResponse rsp = null;
        try {

            rsp = client.execute(req, sessionKey);
            System.out.println(rsp.getBody());

        } catch (ApiException e) {
            e.printStackTrace();
        }
        List<Refund> list = rsp.getRefunds();
        refundList.addAll(list);
        if (rsp.getHasNext()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getRefund(sessionKey, refundList, ++offer, start, end);
        }
        return refundList;
    }

    /**
     * 获取店铺类目
     */
    public static List<SellerCat> getCategory(String shopName) {
        SellercatsListGetRequest req = new SellercatsListGetRequest();
        req.setNick(shopName);
        req.setFields("cid,created,modified,name,parent_cid,pic_url,sort_order,type");
        SellercatsListGetResponse rsp = null;
        try {
            rsp = client.execute(req,sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rsp.getSellerCats();

    }


    /**
     * 获取某个店铺所有订单，返回list
     *
     * @param sessionKey
     */
    public static List<Trade> findOrders(String sessionKey, List<Trade> tradeList,Long offer) {

        TradesSoldGetRequest req = new TradesSoldGetRequest();
        req.setFields("tid,buyer_nick,buyer_open_uid,new_presell,buyer_memo,orders,pay_time,payment,receiver_address,receiver_city,receiver_country,receiver_district,receiver_mobile,receiver_name,receiver_state,receiver_town,seller_memo,seller_nick,snapshot_url,status,tid_str,type,buyer_message");
        //  req.setStartCreated(startCreated);
        //  req.setEndCreated(StringUtils.parseDateTime("2018-12-29 10:59:59"));
        TradesSoldGetResponse rsp = null;
        //620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742
        try {
            req.setStatus("WAIT_SELLER_SEND_GOODS");
            req.setPageNo(offer);
            req.setPageSize(40L);
            req.setUseHasNext(true);
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        List<Trade> trades = rsp.getTrades();
        for (Trade trade : trades) {
            Trade trade1= getTrade("seller_memo,buyer_message", String.valueOf(trade.getTid()), sessionKey).getTrade();
            trade.setBuyerMemo(trade1.getBuyerMemo());
            trade.setSellerMemo(trade1.getSellerMemo());
        }

        tradeList.addAll(trades);
        if (rsp.getHasNext()){
            findOrders(sessionKey, tradeList, ++offer);
        }else{
            return tradeList;
        }
        return tradeList;
    }



    public static List<Trade> getTrades(String sessionKey, List<Trade> tradeList,Long offer,Date start) {
        TradesSoldGetRequest req = new TradesSoldGetRequest();
        req.setFields("tid,status");
        TradesSoldGetResponse rsp = null;
        try {
            req.setPageNo(offer);
            req.setPageSize(40L);
            req.setStartCreated(start);
//            req.setStartCreated(end);
            req.setUseHasNext(true);
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        tradeList.addAll(rsp.getTrades());
        if (rsp.getHasNext()){
            getTrades(sessionKey, tradeList, ++offer,start);
        }else{
            return tradeList;
        }
        return tradeList;
    }


    /**
     * 获取某个店铺所有订单
     *
     * @param sessionKey
     * @return
     */
//    public static String findOrdersforStr(String sessionKey) {
//        TradesSoldGetRequest req = new TradesSoldGetRequest();
//
//        req.setFields("tid,created_time,modify_time,seller_memo,buyer_memo,pay_time,pic_path,post_fee,buyer_nick,orders,title,total_fee,trade_from,type,status,payment,receiver_address,receiver_name,receiver_state,receiver_town,receiver_city,receiver_district,receiver_country,receiver_mobile,receiver_phone,buyer_message");
//
//        //req.setStartCreated(startCreated);
//        // req.setEndCreated(StringUtils.parseDateTime("2018-12-29 10:59:59"));
//
//        TradesSoldGetResponse rsp = null;
//        //620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742
//        try {
//            rsp = client.execute(req, sessionKey);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        System.out.println(rsp.getBody());
//
//        return rsp.getBody();
//
//    }

    /**
     * 获取一个订单的详情
     */
    public static TradeFullinfoGetResponse findOneOrder(String tid, String sessionKey) {
        TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
        req.setFields("tid,type,status,payment,seller_memo,buyer_memo,seller_nick,buyer_nick,orders,receiver_name,receiver_address,snapshot_url,pay_time,receiver_state,receiver_town,receiver_city,receiver_district,receiver_country,receiver_mobile,receiver_phone,buyer_message");
        // req.setTid(315968833434800371L);
        req.setTid(Long.parseLong(tid));
        TradeFullinfoGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());

        return rsp;
    }

    /**
     * 获取一个订单的备注
     *
     * @param tid
     * @param sessionKey
     * @return
     */
    public static String findOrderMemo(String tid, String sessionKey) {

        TradeGetResponse rsp = getTrade("seller_memo", tid, sessionKey);
        String sellerMemo = null;
        try {
            Trade trade = rsp.getTrade();
            sellerMemo = trade.getSellerMemo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return sellerMemo;
        }

    }


    /**
     * 获取订单部份信息
     *
     * @param tid
     * @param sessionKey
     * @return
     */
    public static TradeGetResponse getTrade(String field, String tid, String sessionKey) {
        TradeGetRequest req = new TradeGetRequest();
        req.setFields(field);
        req.setTid(Long.parseLong(tid));
        TradeGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
//        System.out.println(rsp.getBody());

        return rsp;
    }

    /**
     * 接受消息接口
     */

    public static void acceptMessage() {
        TmcClient client = new TmcClient("25500416", "25720ff4e7b9f8c5cfe95827c7e35479", "default"); // 关于default参考消息分组说明
        client.setMessageHandler(new MessageHandler() {
            public void onMessage(Message message, MessageStatus status) {
                try {
                    System.out.println(message.getContent());
                    System.out.println(message.getTopic());

                } catch (Exception e) {
                    e.printStackTrace();
                    status.fail(); // 消息处理失败回滚，服务端需要重发
                    // 重试注意：不是所有的异常都需要系统重试。
                    // 对于字段不全、主键冲突问题，导致写DB异常，不可重试，否则消息会一直重发
                    // 对于，由于网络问题，权限问题导致的失败，可重试。
                    // 重试时间 5分钟不等，不要滥用，否则会引起雪崩
                }
            }
        });

        try {
            client.connect("ws://mc.api.taobao.com"); // 消息环境地址：ws://mc.api.tbsandbox.com/
        } catch (com.taobao.api.internal.toplink.LinkException e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取所有出售中的商品列表
     */

    public static List<Item> getItemsOnsale(String title, Long cid, String sessionKey) {
        ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
        req.setFields("num_iid,title,nick,price,approve_status,sku,pic_url,props");
        if (StringUtils.areNotEmpty(title)) {
            req.setQ(title);
        }
        if (StringUtils.areNotEmpty(String.valueOf(cid))) {
            req.setCid(cid);
        }
        req.setPageSize(10L);
        ItemsOnsaleGetResponse rsp = null;

        List<Item> items = new ArrayList<Item>();
        try {
            Long size = getCount(title, sessionKey);
            for (int i = 1; i <= size / 10 + 1; i++) {
                req.setPageNo(Long.valueOf(i));
                rsp = client.execute(req, sessionKey);
                items.addAll(rsp.getItems());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return items;
    }

    /**
     * 获取商品列表的总数
     *
     * @param title
     * @param sessionKey
     * @return
     */

    public static Long getCount(String title, String sessionKey) {
        ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
        req.setFields("num_iid");
        if (StringUtils.areNotEmpty(title)) {
            req.setQ(title);

        }
        req.setPageSize(10L);
        ItemsOnsaleGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);

        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rsp.getTotalResults();
    }

    /**
     * 获取某个产品详细信息
     */

    public static Item getProduct(long numIid, String sessionKey) {
        ItemSellerGetRequest req = new ItemSellerGetRequest();
        req.setFields("num_iid,detail_url,title,pic_url,nick,price,approve_status,sku,property_alias,properties_alias,skus");
        req.setNumIid(numIid);
        ItemSellerGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp.getItem();
    }
    /*
    * 根据numiid查询商品详情
    * */
    public static List<Item> getProducts(String numids, String sessionKey) {
        ItemsSellerListGetRequest req = new ItemsSellerListGetRequest();
        req.setFields("num_iid,sku,property_alias");
        req.setNumIids(numids);
        ItemsSellerListGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp.getItems();
    }

    /**
     * 物流发货
     *
     * @return
     */

    public static int sendLogistics() {
        LogisticsOnlineSendRequest req = new LogisticsOnlineSendRequest();
        req.setSubTid("1,2,3");
        //淘宝交易id
        req.setTid(123456L);
        req.setIsSplit(0L);
        req.setOutSid("123456789");
        //物流公司，代号
        req.setCompanyCode("POST");
        req.setSenderId(123456L);
        req.setCancelId(123456L);
        req.setFeature("identCode=tid:aaa,bbb;machineCode=tid2:aaa;retailStoreId=12345;retailStoreType=STORE");
        req.setSellerIp("192.168.1.10");
        LogisticsOnlineSendResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return 1;
    }

    /**
     * 更新备注
     *
     * @param sessionKey
     * @throws LinkException
     */
    public static String updateTradeMemo(TradeMemoUpdateRequest req, String sessionKey) {


        TradeMemoUpdateResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rsp.getBody();

    }

    /**
     * 添加备注
     *
     * @throws LinkException
     */
    public static String addTradeMemo(TradeMemoAddRequest req, String sessionKey) {

        TradeMemoAddResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rsp.getBody();

    }

    //删除备注
    public static String deleteMemo(String tid, String delete, String sessionKey) {
        String memo = findOrderMemo(tid, sessionKey);
        memo = memo.replace(delete, "");

        memo = memo.trim();
        TradeMemoUpdateRequest req = new TradeMemoUpdateRequest();
        req.setTid(347255267786949048L);
        req.setMemo(memo);
        updateTradeMemo(req, sessionKey);
        return memo;
    }

    /**
     * 获取电子面单
     */
    public static String getWallbill(String cpCode, CainiaoWaybillIiGetRequest.UserInfoDto sender, List<CainiaoWaybillIiGetRequest.TradeOrderInfoDto> orderInfos) {

        CainiaoWaybillIiGetRequest req = new CainiaoWaybillIiGetRequest();
        CainiaoWaybillIiGetRequest.WaybillCloudPrintApplyNewRequest obj1 = new CainiaoWaybillIiGetRequest.WaybillCloudPrintApplyNewRequest();
        obj1.setCpCode(cpCode);
        obj1.setSender(sender);
        obj1.setTradeOrderInfoDtos(orderInfos);
        req.setParamWaybillCloudPrintApplyNewRequest(obj1);
        CainiaoWaybillIiGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rsp.getBody();

    }

    /**
     * 获取电子面单
     */
    public static String getWallbillTest(String cpCode/*, CainiaoWaybillIiUpdateRequest.UserInfoDto sender*/) {

        CainiaoWaybillIiGetRequest req = new CainiaoWaybillIiGetRequest();
        CainiaoWaybillIiGetRequest.WaybillCloudPrintApplyNewRequest obj1 = new CainiaoWaybillIiGetRequest.WaybillCloudPrintApplyNewRequest();
        obj1.setCpCode(cpCode);
        //发货人信息
        CainiaoWaybillIiGetRequest.UserInfoDto sender = new CainiaoWaybillIiGetRequest.UserInfoDto();
        CainiaoWaybillIiGetRequest.AddressDto obj3 = new CainiaoWaybillIiGetRequest.AddressDto();
        obj3.setCity("长沙市");
        obj3.setDetail("湖南长沙岳麓区天马小区（光合硅能）");
        obj3.setDistrict("岳麓区");
        obj3.setProvince("湖南省");
        sender.setAddress(obj3);
        sender.setMobile("1326443654");
        sender.setName("Bar");
        sender.setPhone("057123222");
        obj1.setSender(sender);
        List<CainiaoWaybillIiGetRequest.TradeOrderInfoDto> list5 = new ArrayList<CainiaoWaybillIiGetRequest.TradeOrderInfoDto>();
        //收件信息
        CainiaoWaybillIiGetRequest.TradeOrderInfoDto obj6 = new CainiaoWaybillIiGetRequest.TradeOrderInfoDto();
        list5.add(obj6);
        obj6.setLogisticsServices("");
        obj6.setObjectId("1");
        CainiaoWaybillIiGetRequest.OrderInfoDto obj8 = new CainiaoWaybillIiGetRequest.OrderInfoDto();
        obj8.setOrderChannelsType("TB");
        List<String> list = new ArrayList<String>();
        list.add("12123123");
        obj8.setTradeOrderList(list);
        obj6.setOrderInfo(obj8);
        CainiaoWaybillIiGetRequest.PackageInfoDto obj10 = new CainiaoWaybillIiGetRequest.PackageInfoDto();
        obj10.setId("1");
        List<CainiaoWaybillIiGetRequest.Item> list12 = new ArrayList<CainiaoWaybillIiGetRequest.Item>();
        CainiaoWaybillIiGetRequest.Item obj13 = new CainiaoWaybillIiGetRequest.Item();
        list12.add(obj13);
        obj13.setCount(1L);
        obj13.setName("衣服");
        obj10.setItems(list12);
        obj10.setVolume(1L);
        obj10.setWeight(1L);
        obj10.setTotalPackagesCount(10L);
        obj10.setPackagingDescription("5纸3木2拖");
        obj10.setGoodsDescription("服装");
        obj6.setPackageInfo(obj10);
        //收件地址
        CainiaoWaybillIiGetRequest.UserInfoDto obj15 = new CainiaoWaybillIiGetRequest.UserInfoDto();
        CainiaoWaybillIiGetRequest.AddressDto obj16 = new CainiaoWaybillIiGetRequest.AddressDto();
        obj16.setCity("北京市");
        obj16.setDetail("花家地社区卫生服务站");
        obj16.setDistrict("朝阳区");
        obj16.setProvince("北京");
        obj16.setTown("望京街道");
        obj15.setAddress(obj16);
        obj15.setMobile("1326443654");
        obj15.setName("Bar");
        obj15.setPhone("057123222");
        obj6.setRecipient(obj15);
        //http:\/\/cloudprint.cainiao.com\/template\/standard\/101\/607
        obj6.setTemplateUrl("http://cloudprint.cainiao.com/template/standard/101");

        obj6.setUserId(12L);
        obj1.setTradeOrderInfoDtos(list5);
        obj1.setStoreCode("553323");
        obj1.setResourceCode("DISTRIBUTOR_978324");
        obj1.setDmsSorting(false);
        obj1.setThreePlTiming(false);
        obj1.setNeedEncrypt(false);
        req.setParamWaybillCloudPrintApplyNewRequest(obj1);
        CainiaoWaybillIiGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp.getBody();

    }

    /**
     * 查询面单服务订购及面单使用情况
     */

    public static List<WaybillApplySubscriptionInfo> waybillISearch() {
        WlbWaybillISearchRequest req = new WlbWaybillISearchRequest();
        WaybillApplyRequest obj1 = new WaybillApplyRequest();
        req.setWaybillApplyRequest(obj1);
        WlbWaybillISearchResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());

        List<WaybillApplySubscriptionInfo> subscription = rsp.getSubscribtions();
        return subscription;

    }


    public static List<ShopCat> getShopCat(){
        ShopcatsListGetRequest req = new ShopcatsListGetRequest();
        req.setFields("cid,is_parent,name,parent_cid");
        ShopcatsListGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rsp.getShopCats();
    }


    public static boolean shipments(String subTid,String outSid,String companyCode,Long tid,Long isSplit,String sessionKey){
        LogisticsOfflineSendRequest req = new LogisticsOfflineSendRequest();
        req.setSubTid(subTid);
        req.setTid(tid);
        req.setIsSplit(isSplit);
        req.setOutSid(outSid);
        req.setCompanyCode(companyCode);
        LogisticsOfflineSendResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rsp.getShipping().getIsSuccess();
    }




    /**
     * @param args
     * @throws LinkException
     */
    public static void main(String[] args) throws LinkException, ApiException {
//        CainiaoWaybillIiSearchRequest req = new CainiaoWaybillIiSearchRequest();
//        req.setCpCode("EYB");
//        CainiaoWaybillIiSearchResponse rsp = client.execute(req, sessionKey);
//        System.out.println(rsp.getBody());
     // getProduct(41211667580L,"620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742");
        List<Item> items= getProducts("584375462366","620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742");


            //光合硅能旗舰店
            String memo=TbaoUtils.findOrderMemo("425524353967012217","6200824224b73677a8d4375add3237e3ZZ21bb86aa67d8c305543718");
            System.out.println(memo);

            /*    CainiaoCloudprintStdtemplatesGetRequest req = new CainiaoCloudprintStdtemplatesGetRequest();
            CainiaoCloudprintStdtemplatesGetResponse rsp = client.execute(req);
            System.out.println(rsp.getBody());*/
          /*  WlbWaybillISearchRequest req = new WlbWaybillISearchRequest();
            WaybillApplyRequest obj1 = new WaybillApplyRequest();
            obj1.setCpCode("ZTO");
            req.setWaybillApplyRequest(obj1);
            WlbWaybillISearchResponse rsp = null;
            try {
                rsp = client.execute(req, sessionKey);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            System.out.println(rsp.getBody());*/
//            deleteMemo("347255267786949048","3.25聚光明阿里,系统管理员采购振荡器下单成本：410元(1+1+2)，订单号：346606688301015367","6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914");
//            String memo=findOrderMemo("347255267786949048","6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914");
//            System.out.println(memo);
    }

}
