package com.kzq.advance.service;


import com.kzq.advance.domain.*;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Order;
import com.taobao.api.response.TradeFullinfoGetResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zmn
 * @since 2018-08-16
 */
public interface ITradesService {

    public TUser getUser(String userId);

    public List<TGoodsSku> setLongTGoodsSkuList(List<Long> numIIds);

    public TtradesOrder formOrder(Order t);

    public void addWsbill(String param);

    //插入商品链接
    public void addGoodLink(Item item);

    //插入sku属性
    public void addGoodSku(TGoodsSku sku);

    //更新sku属性
    public void updateGoodSku(TGoodsSku sku);

    //更新链接
    public void updateLink(Item item);

    //根据id查询link
    public TGoodsLink findLinkById(Long numIid);

    //根据id查询sku
    public TGoodsSku findSkuById(Long skuId);

    //获取没有主图的链接
    public List<TGoodsLink> selectByPic();

    //获取所有sessionKey
    public List<TShop> selectAllShop();

    //获取店铺的sessionKey
    public TShop selectSessionKey(Integer id);

    //分割型号的字符串
    public Map<String, String> getProperty(String PropertyAlias);

    //获取一个订单的详情
    public TradeFullinfoGetResponse getOrder(String tid, String shopId);

    //获取一个订单的备注
    public String getMemo(String tid, String session);

    /**
     * 查询一个店铺的信息
     *
     * @param shop
     * @return
     */
    public TShop selectShop(TShop shop);

    /**
     * 只更新不删除
     *
     * @param item
     */
    public void updateLinkNotDele(Item item);

    /**
     * 根据订单号查询订单信息
     */
    public Trades findTrade(Long tid);

    /**
     * 根据店铺名所有查询链接numiid
     */
    public List<TGoodsLink> selectByShop(String nick);


    public int updateLinkAndSku(Item item, TGoodsLink tGoodsLink, Integer logId);

    void delGoodLink(Long numId);

    void insertLog(TUpdateLog tUpdateLog);

    void updateLog(TUpdateLog tUpdateLog);

    void recoverGoodLink(TGoodsLink tGoodsLink);

    /**
     * 对比更新下载goodslink
     * @param itemHashMap
     * @param tGoodsLinkHashMap
     */
    void compareUpdate(HashMap<Long, Item> itemHashMap, HashMap<Long, TGoodsLink> tGoodsLinkHashMap);

    Integer downCategory(String ShopId);

    public  HashMap<Long, TGoodsLink> getTGoodSLinkByShopName(String shopName);

    public  void recoverGoodLink(HashMap<Long, TGoodsLink> tGoodsLinkHashMap, HashMap<Long, Item> itemHashMap);

    /**
     * 根据店铺的token和cid 得到淘宝所有上架的Item
     * @param session
     * @param cid
     * @return
     */
    public  HashMap<Long, Item> getItems(String session, Long cid,String title);

    public  TUpdateLog insetTUpdateLog(String userid, String shopId);

    public  HashMap<Long, TGoodsLink> formatTGoodsLinksToMap(List<TGoodsLink> goodsLinks);


    public boolean infoRefund(String topic, String content);
}
