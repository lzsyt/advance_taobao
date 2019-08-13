package com.kzq.advance.controller;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.common.utils.URLUtils;
import com.kzq.advance.domain.*;
import com.kzq.advance.service.ILogisticsService;
import com.kzq.advance.service.ITradesService;
import com.kzq.advance.service.IWsBillService;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Refund;
import com.taobao.api.domain.Sku;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.CainiaoWaybillIiGetRequest;
import com.taobao.api.request.TradeMemoAddRequest;
import com.taobao.api.request.TradeMemoUpdateRequest;
import com.taobao.api.response.TradeFullinfoGetResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ClientController {
    @Autowired
    ITradesService iTradesService;
    @Autowired
    ILogisticsService logisticsService;
    @Autowired
    IWsBillService wsBillService;

    protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(ClientController.class);

    /**
     * 添加所有链接
     */
    @GetMapping("/addAllLinks")
    public String addAllLinks(HttpServletRequest request) {
        String token = request.getParameter("token");
        String session = token;
        //获取淘宝出售的产品
        List<Item> list = TbaoUtils.getItemsOnsale("", null, session);
        System.out.println(list.size());
        for (Item e : list) {
            TGoodsLink link = iTradesService.findLinkById(e.getNumIid());
            Item item = null;
            if (link == null || link.getNumIid() == 0) {
                System.out.println("增加NumIid=" + e.getNumIid());
                //获取单个产品的链接
                item = TbaoUtils.getProduct(e.getNumIid(), session);
                iTradesService.addGoodLink(item);

            } else {
                // System.out.println("NumIid="+e.getNumIid());

                continue;
            }

            //拆分属性字段
            if (StringUtils.areNotEmpty(item.getPropertyAlias())) {
                String[] properties = item.getPropertyAlias().split(";");
                Map<String, String> map = new HashMap<>();

                for (int x = 0; x < properties.length; x++) {

                    String prop = properties[x];
                    String prop0 = prop.substring(0, prop.lastIndexOf(":"));
                    String prop1 = prop.substring(prop.lastIndexOf(":") + 1, prop.length());
                    map.put(prop0, prop1);

                }
                List<Sku> list1 = item.getSkus();
                if (list1 != null && list1.size() > 0) {


                    for (Sku s : list1) {
                        TGoodsSku sku1 = new TGoodsSku();
                        sku1.setNumIid(e.getNumIid());
                        sku1.setPropertiesAlias(map.get(s.getProperties()));
                        sku1.setSkuId(s.getSkuId());
                        TGoodsSku sku = iTradesService.findSkuById(s.getSkuId());
                        if (sku == null || sku.getSkuId() == 0) {
                            iTradesService.addGoodSku(sku1);
                        }

                    }
                }

            }
        }

        return "official/index.html";

    }

    /**
     * 更新备注
     */
    @RequestMapping("/updateSellerMemo")
    public String updateSellerMemo(HttpServletRequest request) {
        //店铺号
        String shopId = request.getParameter("shopId");
        String session = "";
        //店订单号
        String tid = request.getParameter("tid");
        String sellerMemo = request.getParameter("sellerMemo");
        //删除备注
        String delete = request.getParameter("delete");
        String memoColor = request.getParameter("memoColor");
        long color = 4L;
        if (org.apache.commons.lang.StringUtils.isNotBlank(memoColor)) {

            color = Long.parseLong(memoColor);

        }
        String oldSellerMemo = null;

        //获取session
        if (StringUtils.isEmpty(shopId)) {
            //shopId为空
            return "-1";

        }

        TShop shop = iTradesService.selectSessionKey(Integer.parseInt(shopId));
        if (StringUtils.isEmpty(shop.getShopToken())) {
            return "-1";

        }
        session = shop.getShopToken();

        //有这个说明是已经添加需要修改
        String newSellerMemo = request.getParameter("newSellerMemo");
        logger.info(tid + " " + "newSellerMemo：" + newSellerMemo);

        logger.info("delete:" + delete);

        logger.info("旗子颜色" + memoColor);
        //查询这个订单的详情-备注
        try {
            oldSellerMemo = iTradesService.getMemo(tid, session);

        } catch (Exception e) {
            e.printStackTrace();
            oldSellerMemo = iTradesService.getMemo(tid, null);


        }

        if (org.apache.commons.lang.StringUtils.isNotBlank(newSellerMemo)) {
            //替换备注
            logger.info("原来的备注：" + sellerMemo);

            if (oldSellerMemo.contains(newSellerMemo)) {
                logger.info("不用更新了：" + newSellerMemo);
                return "success";

            } else if (oldSellerMemo.contains(sellerMemo)) {
                sellerMemo = oldSellerMemo.replace(sellerMemo, newSellerMemo);
                logger.info("更新的备注：" + sellerMemo);

            } else {
                sellerMemo = oldSellerMemo + "\r\n" + newSellerMemo;
                logger.info("更新备注变成添加备注：" + newSellerMemo);

            }

        } else if (delete != null && delete.equals("1")) {
            //删除备注
            logger.info("删除备注：" + sellerMemo);
            logger.info("原来的备注：" + oldSellerMemo);

            if (oldSellerMemo.contains(sellerMemo)) {
                sellerMemo = oldSellerMemo.replace("\r\n" + sellerMemo, "");
                sellerMemo = sellerMemo.trim();
            } else {
                logger.info("不用删除了：" + sellerMemo);

                return "success";

            }
        } else {
            //添加备注
            logger.info("添加的备注：" + sellerMemo);
            //防止重复添加的方式
            if (oldSellerMemo != null && oldSellerMemo.contains(sellerMemo)) {
                logger.info("不用添加了：" + sellerMemo);
                return "success";

            } else if (oldSellerMemo == null) {
                //没有备注就用添加备注的方法
                TradeMemoAddRequest addmemo = new TradeMemoAddRequest();
                addmemo.setMemo(sellerMemo);
                addmemo.setTid(Long.parseLong(tid));
                if (org.apache.commons.lang.StringUtils.isNotBlank(memoColor)) {

                    addmemo.setFlag(color);

                }
                return TbaoUtils.addTradeMemo(addmemo, session);
            } else {
                sellerMemo = oldSellerMemo + "\r\n" + sellerMemo;
            }
        }
        TradeMemoUpdateRequest req = new TradeMemoUpdateRequest();
        req.setTid(Long.parseLong(tid));
        logger.info("备注：" + sellerMemo);
        req.setMemo(sellerMemo);
        //卖家交易备注旗帜,可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色),默认值为0
        if (org.apache.commons.lang.StringUtils.isNotBlank(memoColor)) {
            req.setFlag(color);
        }
        req.setReset(false);
        /**
         * 更新备注的核心方法
         */
        return TbaoUtils.updateTradeMemo(req, session);

    }


    /**
     * 获取快递电子面单
     * 参数：cpcode 出库单id
     */
    @RequestMapping("/getWaybillCloudPrint")
    public String getWaybillCloudPrint(HttpServletRequest request, String cpCode, String tid) {

        if (StringUtils.isEmpty(cpCode)) {
            return "-1";

        }
        if (StringUtils.isEmpty(tid)) {
            return "-1";

        }
        //获取物流公司模板
        LogisticsCompany logisticsCompany = logisticsService.getLogisticsCompany(cpCode);
        //获取寄件人信息
        CainiaoWaybillIiGetRequest.AddressDto sendAddress = logisticsService.getSendAddress(cpCode);

        CainiaoWaybillIiGetRequest.UserInfoDto userInfoDto = new CainiaoWaybillIiGetRequest.UserInfoDto();
        //根据tid获取出库单详情
        TWsBill bill = wsBillService.getByTid(tid);


        //客户名称就是店铺名称
        String senderName = bill.getCustomer();

        userInfoDto.setAddress(sendAddress);
        userInfoDto.setName(senderName);

        userInfoDto.setMobile("17751515681");
        //请求面单信息
        CainiaoWaybillIiGetRequest.TradeOrderInfoDto orderInfoDto = new CainiaoWaybillIiGetRequest.TradeOrderInfoDto();
        orderInfoDto.setObjectId("1");

        //订单信息
        CainiaoWaybillIiGetRequest.OrderInfoDto orderInfo = new CainiaoWaybillIiGetRequest.OrderInfoDto();
        orderInfo.setOrderChannelsType("TB");
        List<String> list = new ArrayList<String>();
        list.add(tid);
        orderInfo.setTradeOrderList(list);
        orderInfoDto.setOrderInfo(orderInfo);

        //包裹信息
        CainiaoWaybillIiGetRequest.PackageInfoDto packageInfo = new CainiaoWaybillIiGetRequest.PackageInfoDto();
        List<CainiaoWaybillIiGetRequest.Item> itemlist = new ArrayList<>();

        CainiaoWaybillIiGetRequest.Item item = new CainiaoWaybillIiGetRequest.Item();
        item.setName("配件");
        item.setCount(1L);
        itemlist.add(item);
        packageInfo.setItems(itemlist);
        orderInfoDto.setPackageInfo(packageInfo);


        //收件人信息
        CainiaoWaybillIiGetRequest.UserInfoDto userInfo = new CainiaoWaybillIiGetRequest.UserInfoDto();
        CainiaoWaybillIiGetRequest.AddressDto address = new CainiaoWaybillIiGetRequest.AddressDto();


        String dealAddress = bill.getDealAddress();
        logger.info("收件人地址：" + dealAddress);

        Map<String, String> map = URLUtils.addressSplit(dealAddress);
        if (map == null || map.size() < 1) {
            logger.info("收件人地址分割失败：" + dealAddress);

            return "-1";

        }

        List<Map<String, String>> addressRes = URLUtils.addressResolution(map.get("address"));
        if (addressRes == null || addressRes.size() < 1) {
            logger.info("地址分割失败：" + map.get("address"));

            return "-1";

        }
        String province = addressRes.get(0).get("province");
        logger.info("电话：" + map.get("telephone"));

        address.setDetail(map.get("address"));
        address.setProvince(province);
        userInfo.setAddress(address);
        userInfo.setMobile(map.get("telephone"));
        userInfo.setName(map.get("name"));
        //加入收货人信息
        orderInfoDto.setRecipient(userInfo);
        String template_url = logisticsCompany.getStandardTemplateUrl();
        //加入模本信息
        //http://cloudprint.cainiao.com/template/standard/101
        logger.info("模板url：" +template_url);

        orderInfoDto.setTemplateUrl(template_url);
        orderInfoDto.setUserId(1L);
        List<CainiaoWaybillIiGetRequest.TradeOrderInfoDto> orderInfos = new ArrayList<CainiaoWaybillIiGetRequest.TradeOrderInfoDto>();
        orderInfos.add(orderInfoDto);
        String res = TbaoUtils.getWallbill(cpCode, userInfoDto, orderInfos);
        logger.info("获取电子面单返回值：" + res);
        return res;


    }

    /**
     * 更新某个链接
     *
     * @return
     */
    @RequestMapping("/updateLink")
    public String updateLink(HttpServletRequest request) {
        String numIid = request.getParameter("numIid");
        //String title=request.getParameter("title");
        if (numIid == null || numIid == "") {

            return "-1";

        }

        TGoodsLink GoodsLink = iTradesService.findLinkById(Long.parseLong(numIid));
        String nick = GoodsLink.getNick();
        //根据店铺名查询sessionkey
        TShop shop = new TShop();
        shop.setShopName(nick);
        String sessionKey = "";
        try {
            sessionKey = iTradesService.selectShop(shop).getShopToken();

        } catch (Exception e) {
            e.printStackTrace();
            return "-2";
        }
        Item item = TbaoUtils.getProduct(Long.parseLong(numIid), sessionKey);

        iTradesService.updateLinkNotDele(item);

        return numIid;

    }

    /**
     * 更新某个店铺所有链接
     *
     * @return
     */

    @RequestMapping("/updateAll")
    public String updateAll(HttpServletRequest request, @RequestParam("userId")String userid) {

        if (iTradesService.getUser(userid) == null) {
            return "-1";
        }
        long start = System.currentTimeMillis();
        String shopId = request.getParameter("shopId");
        //查询店铺的sessionKey
        TShop shop = iTradesService.selectSessionKey(Integer.parseInt(shopId));
        if (shop == null || StringUtils.isEmpty(shop.getShopToken())) {
            return "-1";
        }
        String session = shop.getShopToken();
        //查询出店铺的链接numiid
        List<TGoodsLink> goodsLinks = iTradesService.selectByShop(shop.getShopName());
        int i = 0;

        TUpdateLog tUpdateLog = iTradesService.insetTUpdateLog(userid, shopId);

        Integer logId = tUpdateLog.getId();

        HashMap<Long, Item> itemHashMap = iTradesService.getItems(session, null, "");

        for (TGoodsLink e : goodsLinks) {
            Item item = new Item();
            try {
                //获取单个产品的链接
                item = itemHashMap.get(e.getNumIid());
//                item = TbaoUtils.getProduct(e.getNumIid(), session);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if (item != null) {
                i += iTradesService.updateLinkAndSku(item, e, logId);
            } else {
                //不存在说明已经删除 添加删除方法
                iTradesService.delGoodLink(e.getNumIid());
            }
        }
        tUpdateLog.setUpdateTotal(i);
        iTradesService.updateLog(tUpdateLog);
        //如果线上有线下更新了，就恢复
        HashMap<Long, TGoodsLink> tGoodsLinkMap = iTradesService.formatTGoodsLinksToMap(goodsLinks);
        iTradesService.recoverGoodLink(tGoodsLinkMap, itemHashMap);
        long end = System.currentTimeMillis();
        logger.info("时间为" + (end - start));
        return Integer.toString(i);

    }


    /**
     * 根据订单号获取订单信息
     *
     * @return
     */
    @PostMapping("/getOrders")
    public String getOrders(HttpServletRequest request) {
        String shopNum = request.getParameter("shopNum");
        String shopId = request.getParameter("shopId");

        if (StringUtils.isEmpty(shopNum)) {
            logger.error("订单号为空");
            return "-1";
        }

        //单个下载
        TradeFullinfoGetResponse rsp = iTradesService.getOrder(shopNum, shopId);
        if (rsp != null) {
            //没有错误
            return rsp.getBody();
        }
        return null;


    }

    /**
     * 下载商品分类
     * @param shopId
     * @param model
     * @return
     */
    @RequestMapping("/downCategory")
    public String downCategory(@RequestParam("shopId")String shopId, Model model) {
        iTradesService.downCategory(shopId);
        return "0";
    }

    /**
     * 下载商品链接
     * @param shopId 店铺id
     * @param cid
     * @param title
     * @return
     */
    @PostMapping("/downloadGoodLink")
    public Integer downLoad(@RequestParam(value = "shopId",required = true)String shopId,
                            @RequestParam(value = "cid",required = false)String cid,
                            @RequestParam(value = "title",required = false)String title) {
        Long start = System.currentTimeMillis();
        //得到商铺
        TShop shop = iTradesService.selectSessionKey(Integer.parseInt(shopId));
        Long category = null;
        if (org.apache.commons.lang.StringUtils.isNotBlank(cid)) {
            category = Long.parseLong(cid);
        }
        //获得淘宝上的items
        HashMap<Long, Item> itemHashMap = iTradesService.getItems(shop.getShopToken(), category, title);
        //获得本地的items
        HashMap<Long, TGoodsLink> tGoodsLinkHashMap = iTradesService.getTGoodSLinkByShopName(shop.getShopName());
        //比较更新
        iTradesService.compareUpdate(itemHashMap, tGoodsLinkHashMap);
        //返回更新的条数
        Long end = System.currentTimeMillis();
        logger.info("时间为" + (end - start));
        return itemHashMap.size();
    }


    /**
     * 根据店铺id查询已付款且未发货的链接信息
     * @param shopId
     * @return
     */
    @RequestMapping("/findOrders")
    public List<Trade> findOrders(@RequestParam("shopid") String shopId) {
        TShop shop = iTradesService.selectSessionKey(Integer.parseInt(shopId));
        if (StringUtils.isEmpty(shop.getShopToken())) {
            return null;
        }
        List<Trade> tradeList = TbaoUtils.findOrders(shop.getShopToken(), new ArrayList<Trade>(), 1L);
        return tradeList;
    }

    /**
     * 根据店铺id和订单号查询订单状态
     * @param tid  订单号
     * @param shopId 店铺号
     * @return 订单的状态
     */
    @RequestMapping("/getStatusByTid")
    public String getStatusByTid(
            @RequestParam(value = "tid",required = true) String tid,
            @RequestParam(value = "shopId",required = true) String shopId) {
        TShop shop = iTradesService.selectSessionKey(Integer.parseInt(shopId));
        if (StringUtils.isEmpty(shop.getShopToken())) {
            return null;
        }
        String files = "tid,type,status,payment,orders";
        return TbaoUtils.getTrade(files, tid, shop.getShopToken()).getTrade().getStatus();
    }


    /**
     * 查询订单状态
     * @param tid tid
     * @return  订单状态
     */
    @PostMapping("getTradeStatus")
    public String getTradeStatus(@RequestParam("tid") String tid) {
        String session = iTradesService.getShopTokenByTid(tid);
        String status = TbaoUtils.getTrade("status", tid, session).getTrade().getStatus();
        switch (status) {
            case "SELLER_CONSIGNED_PART": //卖家部分发货
                return "部分发货";
            case "WAIT_SELLER_SEND_GOODS": //等待卖家发货,即:买家已付款
                return "未发货";
            case "WAIT_BUYER_CONFIRM_GOODS": //(等待买家确认收货,即:卖家已发货
                return "已发货";
            case "TRADE_NO_CREATE_PAY": //没有创建支付宝交易
                return "未发货";
            case "WAIT_BUYER_PAY": //等待买家付款
                return "未发货";
            case "TRADE_BUYER_SIGNED": //买家已签收,货到付款专用
                return "已完成";
            case "TRADE_FINISHED": //已完成
                return "已完成";
            case "TRADE_CLOSED": //已关闭
                return "已关闭";
            case "TRADE_CLOSED_BY_TAOBAO": //付款以前，卖家或买家主动关闭交易
                return "已关闭";
        }
        return null;
    }


    /**
     * 发货
     *
     * @param subtidlist   需要拆分的子订单
     * @param tid          交易订单
     * @param out_sid      物流单号
     * @param company_code 物流公司
     * @param shopId       店铺id
     * @return
     */
    @PostMapping("consignment")
    public Boolean consignment(@RequestParam(required = false) List<String> subtidlist,
                               @RequestParam Long tid,
                               @RequestParam String out_sid,
                               @RequestParam String company_code,
                               @RequestParam String shopId) {

        String token = iTradesService.getShopTokenByTid(shopId);
        Long is_split = 0L;
        StringBuilder stringBuilder = new StringBuilder();
        String sub_tid = null;
        if (subtidlist != null && subtidlist.size() != 0) {
            is_split = 1L;
            subtidlist.forEach(model -> {
                stringBuilder.append(model + ",");
            });
            sub_tid = stringBuilder.toString();
            sub_tid = sub_tid.substring(0, sub_tid.length() - 1);
        }

        return TbaoUtils.shipments(sub_tid, out_sid, company_code, tid, is_split, token);
    }


//    /**
//     *  传入tid判断是否退款，通过开始时间，和结束时间缩短时间
//     * @param ShopIdAndTidstr   传入的tid和和shopid
//     * @return 已经退款的tid
//     */
//    @RequestMapping("getRefund")
//    public List<String> getRefund(@RequestParam(required = true) String ShopIdAndTidstr,String startTime) throws ParseException {
//
//        long start = System.currentTimeMillis();
//
//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        String shopId = null;
//        String tid = null;
//
//        int count2 = 0;
//
//        List<String> ShopIdAndTids = Arrays.asList(ShopIdAndTidstr.split(";"));
//
//        for (String string: ShopIdAndTids) {
//            shopId = string.substring(string.indexOf(":")+1);
//            tid = string.substring(0, string.indexOf(":"));
//            TShop shop = iTradesService.selectSessionKey(Integer.parseInt(shopId));
//            count2++;
//
//            if (StringUtils.isEmpty(shop.getShopToken())) {
//                return null;
//            }
//            if (stringStringHashMap.containsKey(shop.getShopToken())){
//                stringStringHashMap.put(shop.getShopToken(), stringStringHashMap.get(shop.getShopToken()) + "," + tid);
//            }else{
//                stringStringHashMap.put(shop.getShopToken(), tid);
//            }
//        }
//        List<String> list = new ArrayList<>();
//
//        long sum = 0;
//
//        int count = 0;
//
//        int refundcount = 0;
//
//        for (Map.Entry entry:stringStringHashMap.entrySet()) {
//            List<Refund> refunds = new ArrayList<>();
//
//            long start2 = System.currentTimeMillis();
//                //原来的查询方法
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            TbaoUtils.getRefund(String.valueOf(entry.getKey()), refunds, 1L,dateFormat.parse(startTime) ,new Date());
//
////            List<String> tids2 = Arrays.asList(entry.getValue().toString().split(","));
////
////            for (String string:tids2) {
////                Trade trade = TbaoUtils.getTrade("created,modified", string, String.valueOf(entry.getKey())).getTrade();
////                Date tradeModified = trade.getModified();
////
////                Date startTime,endTime = null;
////                Calendar c = Calendar.getInstance();
////                c.setTime(tradeModified);
////
////
////
////                c.add(Calendar.DAY_OF_MONTH, -1);
////                startTime = c.getTime();
////                c.add(Calendar.DAY_OF_MONTH, 2);
////                endTime = c.getTime();
////
////                List<Refund> refundList = new ArrayList<>();
////
////                TbaoUtils.getRefund(String.valueOf(entry.getKey()), refundList, 1L, startTime, endTime);
////
////            }
//
//            refundcount += refunds.size();
//            count++;
//
//            long end2 = System.currentTimeMillis();
//            sum += (end2 - start2);
//
//            List<String> tids = Arrays.asList(entry.getValue().toString().split(","));
//            for (String string:tids) {
//                for (Refund refund:refunds) {
//                    if (string.equals(String.valueOf(refund.getTid()))) {
//                        if (!list.contains(string)){
//                            list.add(string);
//                        }
//                    }
//                }
//            }
//        }
//        long end = System.currentTimeMillis();
//        logger.info("方法执行时间是：" +(end - start));
//        logger.info("调用淘宝的时间为：" + sum);
//        logger.info("调用淘宝方法的次数为：" + count);
//        logger.info("查询数据库的时间是：" + count2);
//        logger.info("本地代码执行的时间是：" + ((end - start) - (sum)));
//        logger.info("本次一共从淘宝中查询数据：" + refundcount);
//        logger.info("本次要判断要退款的tid条数为：" + ShopIdAndTids.size());
//        logger.info("实际退款的条数为：" + list.size());
//        return list;
//    }
}

