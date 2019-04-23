package com.kzq.advance.common.utils;

import com.kzq.advance.controller.ClientController;
import com.kzq.advance.domain.TGoodsLink;
import com.kzq.advance.domain.TUpdateLog;
import com.kzq.advance.service.ITradesService;
import com.taobao.api.domain.Item;
import org.apache.logging.log4j.LogManager;

import java.util.*;

public class ControllerUtils {

    protected static org.apache.logging.log4j.Logger logger = LogManager.getLogger(ClientController.class);

    /**
     * 插入操作日志
     * @param userid
     * @param shopId
     * @return
     */
    public static TUpdateLog insetTUpdateLog(String userid, String shopId,ITradesService iTradesService) {
        TUpdateLog tUpdateLog = new TUpdateLog();
        tUpdateLog.setShopId(Integer.parseInt(shopId));
        tUpdateLog.setUpdateTime(new Date());
        tUpdateLog.setUpdateUserId(userid);
        iTradesService.insertLog(tUpdateLog);
        return tUpdateLog;
    }

    /**
     * 根据店铺的token和cid 得到淘宝所有上架的Item
     * @param session
     * @param cid
     * @return
     */
    public static HashMap<Long, Item> getItems(String session, Long cid, ITradesService iTradesService) {
        //
        ArrayList<Long> numIdList = new ArrayList<>();

        StringBuffer stringBuffer = new StringBuffer();
        String substring = null;
        //得到上架商品
        long star = System.currentTimeMillis();
        List<Item> list = TbaoUtils.getItemsOnsale("", cid, session);
        long end = System.currentTimeMillis();
        logger.info("获取上架商品的时间为：" + (end - star));
        ArrayList<String> strings = new ArrayList<>();

        HashMap<Long, Item> itemHashMap = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {

            numIdList.add(list.get(i).getNumIid());

            itemHashMap.put(list.get(i).getNumIid(), list.get(i));
            //拼接numiid
            stringBuffer.append(list.get(i).getNumIid()).append(",");
            //如果stringBuffer中的numiid达到20个存入strings
            if (i % 19 == 0 && i > 1) {
                substring = stringBuffer.substring(0, stringBuffer.length() - 1);
                strings.add(substring);
                stringBuffer.setLength(0);
            }
            //如果到结尾了但是不到20个也把nums赋给strings
            if ((i == list.size() - 1) && (i % 19 != 0)) {
                substring = stringBuffer.substring(0, stringBuffer.length() - 1);
                strings.add(substring);
                stringBuffer.setLength(0);
            }
        }
        List<Item> items = new ArrayList<>();

        iTradesService.setLongTGoodsSkuList(numIdList);

        return getItemHashMap(session, strings, itemHashMap, items);
    }

    /**
     * @param session     商铺的token
     * @param strings     多个numids
     * @param itemHashMap 上架的商品列表
     * @param items       详细列表
     * @return 包含详细信息的上架商品列表
     */
    private static HashMap<Long, Item> getItemHashMap(String session, ArrayList<String> strings, HashMap<Long, Item> itemHashMap, List<Item> items) {
        Long star = System.currentTimeMillis();
        for (String s : strings) {
            List<Item> itemList = TbaoUtils.getProducts(s, session);
            items.addAll(itemList);
        }
        Long end = System.currentTimeMillis();
        logger.info("获取详细信息的时间为：" + (end - star));
        //将详细信息赋给上架的商品列表里
        for (Item i : items) {
            Item item = itemHashMap.get(i.getNumIid());
            item.setPropertyAlias(i.getPropertyAlias());
            item.setSkus(i.getSkus());
            itemHashMap.put(i.getNumIid(), item);
        }
        return itemHashMap;
    }

    /**
     * 格式化TGoodsLinks，变成HashMap
     *
     * @param goodsLinks
     * @return
     */
    public static HashMap<Long, TGoodsLink> formatTGoodsLinksToMap(List<TGoodsLink> goodsLinks) {
        HashMap<Long, TGoodsLink> tGoodsLinkHashMap = new HashMap<>();
        for (TGoodsLink i : goodsLinks) {
            tGoodsLinkHashMap.put(i.getNumIid(), i);
        }
        return tGoodsLinkHashMap;
    }

    /**
     * 如果线上有，线下被删掉了，那么就恢复
     * @param tGoodsLinkHashMap
     * @param itemHashMap
     */
    public static void recoverGoodLink(HashMap<Long, TGoodsLink> tGoodsLinkHashMap, HashMap<Long, Item> itemHashMap,ITradesService iTradesService) {
        for (Map.Entry entry : itemHashMap.entrySet()) {
            TGoodsLink tGoodsLink = tGoodsLinkHashMap.get(entry.getKey());
            if (tGoodsLink != null && tGoodsLink.getIsDel() == 1) {
                //恢复方法
                tGoodsLink.setIsDel(0);
                iTradesService.recoverGoodLink(tGoodsLink);
            }
        }
    }

    /**
     * 根据goodLink得到shopName
     * @param shopName
     * @return
     */
    public static HashMap<Long, TGoodsLink> getTGoodSLinkByShopName(String shopName,ITradesService iTradesService) {
        Long start = System.currentTimeMillis();
        List<TGoodsLink> goodsLinks = iTradesService.selectByShop(shopName);
        Long end = System.currentTimeMillis();
        logger.info("获取Tgoodlink的时间：" + (end - start));
        return formatTGoodsLinksToMap(goodsLinks);
    }
}
