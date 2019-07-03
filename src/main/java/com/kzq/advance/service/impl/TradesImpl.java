package com.kzq.advance.service.impl;


import com.kzq.advance.common.utils.BeanUtils;
import com.kzq.advance.common.utils.HttpUtils;
import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.*;
import com.kzq.advance.mapper.*;
import com.kzq.advance.service.ITradesService;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.SellerCat;
import com.taobao.api.domain.Sku;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.response.TradeFullinfoGetResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zmn
 * @since 2018-08-16
 */

@Service("ITradesService")
public class TradesImpl implements ITradesService {


    @Resource
    private TGoodsLinkMapMapper tGoodsLinkMapMapper;
    @Resource
    private TradesMapper tradesMapper;
    @Resource
    private TtradesOrderMapper tradesOrderMapper;
    @Resource
    private TGoodsSkuMapper goodsSkuMapper;
    @Resource
    private TGoodsLinkMapper goodsLinkMapper;
    @Resource
    private TShopMapper shopMapper;
    @Resource
    private TUpdateLogMapper tUpdateLogMapper;
    @Resource
    private TUpdateLogDetailMapper tUpdateLogDetailMapper;
    @Resource
    private TSellerCatMapper tSellerCatMapper;

    @Resource
    private TUserMapper userMapper;

    private List<TGoodsSku> TGoodsSkuList;

    protected Logger logger = LogManager.getLogger(TradesImpl.class);


    @Override
    public String getShopTokenByTid(String tid) {
        return tradesMapper.getShopTokenByTid(tid);
    }

    @Override
    public TUser getUser(String userId) {
        return userMapper.findUserByUserId(userId);
    }

    public List<TGoodsSku> setLongTGoodsSkuList(List<Long> numIIds) {
        long star = System.currentTimeMillis();
        TGoodsSkuList = goodsSkuMapper.selectByNumIds(numIIds);
        long end = System.currentTimeMillis();
        logger.info("查询goodSku的时间：" + (end - star));
        return TGoodsSkuList;
    }

    public List<TGoodsSku> getTGoodsSkuByNumId(Long numId) {
        List<TGoodsSku> tGoodsSkus = new ArrayList<>();
        for (TGoodsSku tgoodsku : TGoodsSkuList) {
            if (tgoodsku.getNumIid().toString().equals(numId.toString())) {
                tGoodsSkus.add(tgoodsku);
            }
        }
        return tGoodsSkus;
    }

    /**
     * 类型转换
     *
     * @param t
     * @return
     */
    public TtradesOrder formOrder(Order t) {
        TtradesOrder order = new TtradesOrder();

        order.setOid(t.getOid());
        order.setInvoiceNo(t.getInvoiceNo());
        order.setNum(t.getNum());
        order.setStatus(t.getStatus());
        order.setNumIid(t.getNumIid().toString());
        order.setLogisticsCompany(t.getLogisticsCompany());
        order.setOid(t.getOid());
        order.setTotalFee(t.getTotalFee());
        order.setPicPath(t.getPicPath());
        order.setTitle(t.getTitle());

        return order;
    }


    /**
     * 自动生成出库单
     *
     * @param param
     */
    public void addWsbill(String param) {//调用生成出库单的接口

        String sr = HttpUtils.sendPost("http://localhost/web/Home/Sale/addWSBill", "jsonStr: " + param);
        logger.info("生成出库单：" + sr);


    }

    /**
     * 添加产品链接
     *
     * @param item
     */
    public void addGoodLink(Item item) {


        TGoodsLink tGoodsLink = new TGoodsLink();
        tGoodsLink.setDetailUrl(item.getDetailUrl());
        tGoodsLink.setNumIid(item.getNumIid());
        tGoodsLink.setNick(item.getNick());
        tGoodsLink.setTitle(item.getTitle());
        tGoodsLink.setPicPath(item.getPicUrl());
        tGoodsLink.setState(0);
        //分割型号的字符串
        item.getPropertyAlias();
        //有型号
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getPropertyAlias())) {
            tGoodsLink.setState(1);

            //分割型号的字符串为map
            Map<String, String> map = getProperty(item.getPropertyAlias());
            List<Sku> skus = item.getSkus();
            for (Sku s : skus) {
                TGoodsSku goodsSku = new TGoodsSku();
                goodsSku.setPropertiesAlias(map.get(s.getProperties()));
                goodsSku.setNumIid(item.getNumIid());
                goodsSku.setSkuId(s.getSkuId());
                //插入型号
                goodsSkuMapper.insertSelective(goodsSku);

            }

        } else {
            tGoodsLink.setState(0);
        }

        //插入产品链接
        goodsLinkMapper.insertSelective(tGoodsLink);
    }


    /**
     * 拆分属性字符串
     *
     * @param PropertyAlias
     * @return
     */

    public Map<String, String> getProperty(String PropertyAlias) {
        //拆分属性字段
        String[] properties = PropertyAlias.split(";");

        Map<String, String> map = new HashMap<>();

        for (int x = 0; x < properties.length; x++) {

            String prop = properties[x];
            String prop0 = prop.substring(0, prop.lastIndexOf(":"));
            String prop1 = prop.substring(prop.lastIndexOf(":") + 1, prop.length());
            map.put(prop0, prop1);

        }
        return map;

    }

    /**
     * 更新商品链接
     *
     * @param item
     */
    public void updateGoodLink(Item item) {
        goodsLinkMapper.updateByPrimaryKey(formatTGoodsLink(item));
    }


    /**
     * 类型转换
     *
     * @param item
     * @return
     */
    public TGoodsLink formatTGoodsLink(Item item) {
        TGoodsLink tGoodsLink = new TGoodsLink();
        tGoodsLink.setNumIid(item.getNumIid());
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getDetailUrl()))
            tGoodsLink.setDetailUrl(item.getDetailUrl());
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getNick()))
            tGoodsLink.setNick(item.getNick());
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getTitle()))
            tGoodsLink.setTitle(item.getTitle());
        return tGoodsLink;
    }


    /**
     * 更新链接
     *
     * @param item
     */

    public void updateLink(Item item) {
        TGoodsLink tGoodsLink = new TGoodsLink();
        tGoodsLink.setDetailUrl(item.getDetailUrl());
        tGoodsLink.setNumIid(item.getNumIid());
        tGoodsLink.setNick(item.getNick());
        tGoodsLink.setTitle(item.getTitle());
        tGoodsLink.setState(0);
        //分割型号的字符串
        item.getPropertyAlias();
        //有型号
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getPropertyAlias())) {
            tGoodsLink.setState(1);
            Map<String, String> map = getProperty(item.getPropertyAlias());
            List<Sku> skus = item.getSkus();
            for (Sku s : skus) {
                TGoodsSku goodsSku = new TGoodsSku();

                goodsSku.setPropertiesAlias(map.get(s.getProperties()));
                goodsSku.setNumIid(item.getNumIid());
                goodsSku.setSkuId(s.getSkuId());


                goodsSkuMapper.deleteByPrimaryKey(s.getSkuId());
                goodsSkuMapper.insertSelective(goodsSku);

            }

        } else {
            tGoodsLink.setState(0);
        }

        //插入产品链接
        goodsLinkMapper.updateByPrimaryKey(tGoodsLink);
    }


    /**
     * 插入sku属性
     *
     * @param sku
     */
    public void addGoodSku(TGoodsSku sku) {
        goodsSkuMapper.insertSelective(sku);

    }

    /**
     * 更新sku属性
     *
     * @param sku
     */

    public void updateGoodSku(TGoodsSku sku) {
        goodsSkuMapper.updateByPrimaryKey(sku);

    }

    /**
     * 根据id查询link
     *
     * @param numIid
     * @return
     */
    public TGoodsLink findLinkById(Long numIid) {
        TGoodsLink link = goodsLinkMapper.selectByPrimaryKey(numIid);
        return link;
    }

    /**
     * 根据id查询sku
     *
     * @param skuId
     * @return
     */
    public TGoodsSku findSkuById(Long skuId) {
        TGoodsSku sku = goodsSkuMapper.selectByPrimaryKey(skuId);
        return sku;
    }

    /**
     * 获取主图的链接
     */
    public List<TGoodsLink> selectByPic() {
        return goodsLinkMapper.selectByPic();
    }

    /**
     * 获取所有店铺的sessionkey信息
     */
    public List<TShop> selectAllShop() {
        return shopMapper.selectAll();
    }

    /**
     * 获取某个店铺的sessionkey信息
     */
    public TShop selectShop(TShop shop) {

        return shopMapper.selectShop(shop);
    }

    /**
     * 更新链接
     *
     * @param item
     */

    @Override
    public void updateLinkNotDele(Item item) {
        TGoodsLink tGoodsLink = new TGoodsLink();
        tGoodsLink.setDetailUrl(item.getDetailUrl());
        tGoodsLink.setNumIid(item.getNumIid());
        tGoodsLink.setNick(item.getNick());
        tGoodsLink.setTitle(item.getTitle());
        tGoodsLink.setPicPath(item.getPicUrl());

        //有型号
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getPropertyAlias())) {
            tGoodsLink.setIsSku(1);
            //拆分属性字段为map
            Map<String, String> map = getProperty(item.getPropertyAlias());
            List<Sku> skus = item.getSkus();
            for (Sku s : skus) {
                TGoodsSku goodsSku = new TGoodsSku();

                goodsSku.setPropertiesAlias(map.get(s.getProperties()));
                goodsSku.setNumIid(item.getNumIid());
                goodsSku.setSkuId(s.getSkuId());
                if (s.getQuantity() < 1) {
                    goodsSku.setIsDel(1);
                } else {

                    goodsSku.setIsDel(0);

                }

                //判断是否存在链接
                TGoodsSku sku = findSkuById(s.getSkuId());
                if (sku != null && sku.getSkuId() != 0) {
                    goodsSkuMapper.updateByPrimaryKey(goodsSku);
                } else {
                    goodsSkuMapper.insert(goodsSku);

                }


            }

        } else {
            tGoodsLink.setIsSku(0);
        }

        //插入产品链接
        goodsLinkMapper.updateByPrimaryKeySelective(tGoodsLink);
    }

    public TShop selectSessionKey(Integer id) {
        return shopMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取某个订单详情
     *
     * @param tid
     * @param shopId
     * @return
     */
    public TradeFullinfoGetResponse getOrder(String tid, String shopId) {

        TradeFullinfoGetResponse re = null;

        //订单号不为空
        if (!StringUtils.isEmpty(tid)) {
            if (!StringUtils.isEmpty(shopId)) {
                logger.info("shopId不为空：" + shopId);
                TShop shop = selectSessionKey(Integer.parseInt(shopId));
                re = TbaoUtils.findOneOrder(tid, shop.getShopToken());
                TbaoUtils.setCurrentSessionKey(shop.getShopToken());
                if (StringUtils.isEmpty(re.getSubMsg())) {
                    return re;
                }
            } else {
                logger.info("在所有店铺里查询订单" + tid);
                //获取所有sessionKey
                List<TShop> list = selectAllShop();
                for (TShop s : list) {
                    logger.info(s.getShopName());
                    if (!StringUtils.isEmpty(s.getShopToken())) {
                        //获取一个订单
                        re = TbaoUtils.findOneOrder(tid, s.getShopToken());
                        if (StringUtils.isEmpty(re.getSubMsg())) {
                            TbaoUtils.setCurrentSessionKey(s.getShopToken());
                            return re;
                        }
                    }
                }
            }


        }
        return null;

    }

    /**
     * 获取某个订单备注
     *
     * @param tid
     * @param session
     * @return
     */
    public String getMemo(String tid, String session) {

        String memo = null;

        //查询出所有店铺的session
        if (!StringUtils.isEmpty(tid)) {
            //获取所有sessionKey
            if (!StringUtils.isEmpty(session)) {
                logger.info("session不为空：" + session);

                memo = TbaoUtils.findOrderMemo(tid, session);
                return memo;

            } else {
                logger.info("在所有店铺里查询订单" + tid);

                List<TShop> list = selectAllShop();
                for (TShop s : list) {
                    if (!StringUtils.isEmpty(s.getShopToken())) {
                        //获取一个订单
                        memo = TbaoUtils.findOrderMemo(tid, s.getShopToken());
                        if (org.apache.commons.lang.StringUtils.isNotBlank(memo)) {
                            return memo;
                        }
                    }
                }
            }


        }
        return null;

    }

    public Trades findTrade(Long tid) {

        return tradesMapper.selectByPrimaryKey(tid);
    }

    @Override
    public List<TGoodsLink> selectByShop(String nick) {
        return goodsLinkMapper.selectByShop(nick);
    }

    @Override
    public int updateLinkAndSku(Item item, TGoodsLink tGoodsLink, Integer logId) {
        //调用GoodLink比对跟新的方法
        Boolean flag1 = compareGoodLink(item, tGoodsLink, logId);
        //调用GoodSku比对更新的方法
        Boolean flag2 = compareGoodSku(item, logId);

        return flag1 || flag2 ? 1 : 0;
    }


    private Boolean compareGoodLink(Item item, TGoodsLink tGoodsLink, Integer logId) {
        TGoodsLink gdlink = new TGoodsLink();
        gdlink.setNumIid(item.getNumIid());
        if (!item.getTitle().equals(tGoodsLink.getTitle())) {
            gdlink.setTitle(item.getTitle());
            if (logId != null) {
                StringBuffer stringBuffer = new StringBuffer("标题更新:").append(item.getTitle());
                insertLogDetail(logId, item.getNumIid(), item.getTitle(), stringBuffer.toString(), null);
            }
        }
        if (!item.getPicUrl().equals(tGoodsLink.getPicPath())) {
            gdlink.setPicPath(item.getPicUrl());
            if (logId != null) {
                StringBuffer stringBuffer = new StringBuffer("主图更新:").append(item.getPicUrl());
                insertLogDetail(logId, item.getNumIid(), item.getTitle(), stringBuffer.toString(), null);
            }
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(gdlink.getTitle()) ||
                org.apache.commons.lang.StringUtils.isNotBlank(gdlink.getPicPath())) {
            goodsLinkMapper.updateByPrimaryKeySelective(gdlink);
            return true;
        } else {
            return false;
        }
    }

    private Boolean compareGoodSku(Item item, Integer logId) {
        HashMap<Long, TGoodsSku> tGoodsSkuHashMap = getSkuByNumIId(item.getNumIid());
        Boolean flag = false;
        TGoodsSku tGoodsSku = new TGoodsSku();

        //说明淘宝的item中包含sku，将本地的sku与淘宝的aku进行比对，
        // 如果本地没有，就插入，有就比对，如果不一样就更新并插入日志
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getPropertyAlias())) {
            Map<String, String> map = getProperty(item.getPropertyAlias());
            List<Sku> skus = item.getSkus();
            for (Sku s : skus) {
                //淘宝的sku
                TGoodsSku itemSku = gettGoodsSku(item, map, s);
                //本地的sku
                TGoodsSku sku = tGoodsSkuHashMap.get(s.getSkuId());
                if (sku != null) {
                    boolean isUpdate = isUpdateGoodSku(itemSku, sku, logId, item.getTitle());
                    if (isUpdate)
                        flag = true;
                } else {
                    //插入sku
                    if (!existSku(itemSku)) {
                        goodsSkuMapper.insertSelective(itemSku);
                        StringBuffer stringBuffer = new StringBuffer("新增属性：").append(itemSku.getPropertiesAlias());
                        insertLogDetail(logId, item.getNumIid(), item.getTitle(), stringBuffer.toString(), itemSku.getSkuId());
                        flag = true;
                    }
                }
            }
            //删除方法
            delSku(item.getNumIid(), skus);
        }
        return flag;
    }

    /**
     * 根据skuId判断sku是否存在的
     *
     * @param itemSku
     */
    private Boolean existSku(TGoodsSku itemSku) {
        TGoodsSku TGS = goodsSkuMapper.selectByPrimaryKey(itemSku.getSkuId());
        if (TGS != null) {
            goodsSkuMapper.updateByPrimaryKeySelective(itemSku);
            TGoodsLinkMap GoodsLinkMap = tGoodsLinkMapMapper.selectBySkuId(itemSku.getSkuId());
            if (GoodsLinkMap != null) {
                if (!String.valueOf(GoodsLinkMap.getSkuId()).equals(itemSku.getSkuId()) &&
                        String.valueOf(GoodsLinkMap.getNumIid()).equals(itemSku.getNumIid())) {
                    TGoodsLinkMap tGoodsLinkMap = new TGoodsLinkMap();
                    tGoodsLinkMap.setSkuId(String.valueOf(itemSku.getSkuId()));
                    tGoodsLinkMap.setNumIid(String.valueOf(itemSku.getNumIid()));
                    tGoodsLinkMapMapper.updateBySkuIdSelective(tGoodsLinkMap);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 更新sku
     *
     * @param itemSku
     * @param goodsSku
     * @param logId
     * @param title
     * @return
     */
    private Boolean isUpdateGoodSku(TGoodsSku itemSku, TGoodsSku goodsSku, Integer logId, String title) {
        if (itemSku.getPropertiesAlias() == null) {
            if (goodsSku.getPropertiesAlias() == null)
                return false;
            else {
                goodsSkuMapper.updateByPrimaryKeySelective(itemSku);
                if (logId != null) {
                    insertLogDetail(logId, goodsSku.getNumIid(), title, "属性更新:空", goodsSku.getSkuId());
                }
                return true;
            }
        }
        if (!itemSku.getPropertiesAlias().equals(goodsSku.getPropertiesAlias())) {
            goodsSkuMapper.updateByPrimaryKeySelective(itemSku);
            if (logId != null) {
                StringBuffer stringBuffer = new StringBuffer("属性更新:").append(itemSku.getPropertiesAlias());
                insertLogDetail(logId, itemSku.getNumIid(), title, stringBuffer.toString(), itemSku.getSkuId());
            }
            return true;
        } else {
            return false;
        }
    }

    private TGoodsSku gettGoodsSku(Item item, Map<String, String> map, Sku s) {
        TGoodsSku goodsSku = new TGoodsSku();
        goodsSku.setPropertiesAlias(map.get(s.getProperties()));
        goodsSku.setNumIid(item.getNumIid());
        goodsSku.setSkuId(s.getSkuId());
        return goodsSku;
    }

    private void delSku(Long numId, List<Sku> skus) {
//        List<TGoodsSku> tGoodsSkus = getTGoodsSkuByNumId(numId);
//        int count = tGoodsSkus.size();

        for (Sku sku : skus) {
            if (sku.getQuantity() == 0) {
                goodsSkuMapper.delSku(sku.getSkuId());
            }
        }
//        if (skus.size() < count) {
//            HashMap<Long, TGoodsSku> longTGoodsSkuHashMap = formatSkusToHashMap(skus);
//            HashMap<Long, TGoodsSku> tGoodsSkuHashMap = formatGoodsSkusToHashMap(tGoodsSkus);
//            for (Map.Entry entry : tGoodsSkuHashMap.entrySet()) {
//                if (!longTGoodsSkuHashMap.containsKey(entry.getKey())) {
//                    goodsSkuMapper.delSku((Long) entry.getKey());
//                }
//            }
//        }
    }

    private void insertLogDetail(Integer logId, Long numIId, String title, String updateInfo, Long skuId) {
        TUpdateLogDetail tUpdateLogDetail = new TUpdateLogDetail();
        tUpdateLogDetail.setLogId(logId);
        tUpdateLogDetail.setNumIid(numIId);
        tUpdateLogDetail.setTitle(title);
        tUpdateLogDetail.setUpdateInfo(updateInfo);
        tUpdateLogDetail.setSkuId(skuId);
        tUpdateLogDetailMapper.insertSelective(tUpdateLogDetail);
    }

    public HashMap<Long, TGoodsSku> formatGoodsSkusToHashMap(List<TGoodsSku> tGoodsSkus) {
        HashMap<Long, TGoodsSku> hashMap = new HashMap<>();
        for (TGoodsSku tGoodsSku : tGoodsSkus) {
            hashMap.put(tGoodsSku.getSkuId(), tGoodsSku);
        }
        return hashMap;
    }

    public HashMap formatSkusToHashMap(List<Sku> tGoodsSkus) {
        HashMap<Long, Sku> hashMap = new HashMap<>();
        for (Sku sku : tGoodsSkus) {
            hashMap.put(sku.getSkuId(), sku);
        }
        return hashMap;
    }

    public HashMap<Long, TGoodsSku> getSkuByNumIId(Long numiid) {
        List<TGoodsSku> tGoodsSkus = getTGoodsSkuByNumId(numiid);
        return formatGoodsSkusToHashMap(tGoodsSkus);
    }

    @Override
    public void delGoodLink(Long numId) {
        goodsLinkMapper.deleteByNumId(numId);
    }

    @Override
    public void insertLog(TUpdateLog tUpdateLog) {
        tUpdateLogMapper.insertSelective(tUpdateLog);
    }

    @Override
    public void updateLog(TUpdateLog tUpdateLog) {
        tUpdateLogMapper.updateByPrimaryKeySelective(tUpdateLog);
    }

    @Override
    public void recoverGoodLink(TGoodsLink tGoodsLink) {
        goodsLinkMapper.updateByPrimaryKeySelective(tGoodsLink);
    }

    @Override
    public void compareUpdate(HashMap<Long, Item> itemHashMap, HashMap<Long, TGoodsLink> tGoodsLinkHashMap) {
        //先查看本地有没有这个link，如果有就对比跟新，如果没有插入
        for (Map.Entry entry : itemHashMap.entrySet()) {
            if (tGoodsLinkHashMap.containsKey(entry.getKey())) {
                compareGoodLink(itemHashMap.get(entry.getKey()), tGoodsLinkHashMap.get(entry.getKey()), null);
                compareUpdateGoodSkuAndItem(itemHashMap.get(entry.getKey()));
            } else {
                addGoodLink(itemHashMap.get(entry.getKey()));
            }
        }
    }

    /**
     * 跟新goodSku不加日志方法
     *
     * @param item
     * @return
     */
    private Boolean compareUpdateGoodSkuAndItem(Item item) {
        HashMap<Long, TGoodsSku> tGoodsSkuHashMap = getSkuByNumIId(item.getNumIid());
        //说明淘宝的item中包含sku，将本地的sku与淘宝的aku进行比对，
        // 如果本地没有，就插入，有就比对，如果不一样就更新并插入日志
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getPropertyAlias())) {
            Map<String, String> map = getProperty(item.getPropertyAlias());
            List<Sku> skus = item.getSkus();
            for (Sku s : skus) {
                TGoodsSku itemSku = gettGoodsSku(item, map, s);
                //判断是否存在sku
                TGoodsSku sku = tGoodsSkuHashMap.get(s.getSkuId());
                if (sku != null) {
                    isUpdateGoodSku(itemSku, sku, null, null);
                } else {
                    //插入sku
                    if (!existSku(itemSku)) {
                        goodsSkuMapper.insertSelective(itemSku);
                    }
                    ;
                }
            }
        }
        return false;
    }

    @Override
    public Integer downCategory(String shopId) {
        String shopName = shopMapper.findShopNameByShopId(Integer.parseInt(shopId));
        if (org.apache.commons.lang.StringUtils.isBlank(shopName)) {
            return -1;
        }
        HashMap<Long, SellerCat> sellerCatHashMap = formatSellerCatListToHashMap(TbaoUtils.getCategory(shopName));
        HashMap<Long, TSellerCat> tSellerCatHashMap = formatTSellerCatListToHashMap(tSellerCatMapper.find(Integer.parseInt(shopId)));
        for (Map.Entry entry : sellerCatHashMap.entrySet()) {
            TSellerCat tSellerCat = new TSellerCat();
            BeanUtils.copyProperties(entry.getValue(), tSellerCat);
            if (tSellerCatHashMap.containsKey(entry.getKey())) {
                if (!tSellerCatHashMap.get(entry.getKey()).equals(tSellerCat)) {
                    tSellerCatMapper.updateByPrimaryKeySelective(tSellerCat);
                }
            } else {
                tSellerCat.setShopId(Integer.parseInt(shopId));
                tSellerCat.setIsDel(Byte.parseByte("0"));
                tSellerCatMapper.insertSelective(tSellerCat);
            }
        }
        return 0;
    }

    public HashMap<Long, SellerCat> formatSellerCatListToHashMap(List<SellerCat> sellerCatList) {
        HashMap<Long, SellerCat> sellerCatHashMap = new HashMap<>();
        for (SellerCat sellerCat : sellerCatList) {
            sellerCatHashMap.put(sellerCat.getCid(), sellerCat);
        }
        return sellerCatHashMap;
    }

    public HashMap<Long, TSellerCat> formatTSellerCatListToHashMap(List<TSellerCat> sellerCats) {
        HashMap<Long, TSellerCat> sellerCatHashMap = new HashMap<>();
        for (TSellerCat sellerCat : sellerCats) {
            sellerCatHashMap.put(sellerCat.getCid(), sellerCat);
        }
        return sellerCatHashMap;
    }


    /**
     * 插入操作日志
     *
     * @param userid
     * @param shopId
     * @return
     */
    public TUpdateLog insetTUpdateLog(String userid, String shopId) {
        TUpdateLog tUpdateLog = new TUpdateLog();
        tUpdateLog.setShopId(Integer.parseInt(shopId));
        tUpdateLog.setUpdateTime(new Date());
        tUpdateLog.setUpdateUserId(userid);
        insertLog(tUpdateLog);
        return tUpdateLog;
    }

    /**
     * 根据店铺的token和cid 得到淘宝所有上架的Item
     *
     * @param session
     * @param cid
     * @return
     */
    public HashMap<Long, Item> getItems(String session, Long cid, String title) {
        //
        ArrayList<Long> numIdList = new ArrayList<>();

        StringBuffer stringBuffer = new StringBuffer();
        String substring = null;
        //得到上架商品
        long star = System.currentTimeMillis();
        List<Item> list = TbaoUtils.getItemsOnsale(title, cid, session);
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

        setLongTGoodsSkuList(numIdList);

        return getItemHashMap(session, strings, itemHashMap, items);
    }

    /**
     * @param session     商铺的token
     * @param strings     多个numids
     * @param itemHashMap 上架的商品列表
     * @param items       详细列表
     * @return 包含详细信息的上架商品列表
     */
    private HashMap<Long, Item> getItemHashMap(String session, ArrayList<String> strings, HashMap<Long, Item> itemHashMap, List<Item> items) {
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
    public HashMap<Long, TGoodsLink> formatTGoodsLinksToMap(List<TGoodsLink> goodsLinks) {
        HashMap<Long, TGoodsLink> tGoodsLinkHashMap = new HashMap<>();
        for (TGoodsLink i : goodsLinks) {
            tGoodsLinkHashMap.put(i.getNumIid(), i);
        }
        return tGoodsLinkHashMap;
    }

    /**
     * 如果线上有，线下被删掉了，那么就恢复
     *
     * @param tGoodsLinkHashMap
     * @param itemHashMap
     */
    public void recoverGoodLink(HashMap<Long, TGoodsLink> tGoodsLinkHashMap, HashMap<Long, Item> itemHashMap) {
        for (Map.Entry entry : itemHashMap.entrySet()) {
            TGoodsLink tGoodsLink = tGoodsLinkHashMap.get(entry.getKey());
            if (tGoodsLink != null && tGoodsLink.getIsDel() == 1) {
                //恢复方法
                tGoodsLink.setIsDel(0);
                recoverGoodLink(tGoodsLink);
            }
        }
    }

    /**
     * 根据goodLink得到shopName
     *
     * @param shopName
     * @return
     */
    public HashMap<Long, TGoodsLink> getTGoodSLinkByShopName(String shopName) {
        Long start = System.currentTimeMillis();
        List<TGoodsLink> goodsLinks = selectByShop(shopName);
        Long end = System.currentTimeMillis();
        logger.info("获取Tgoodlink的时间：" + (end - start));
        return formatTGoodsLinksToMap(goodsLinks);
    }

    /**
     * 通知修改退款的或取消退款的
     *
     * @param topic
     * @param content
     */
    public boolean infoRefund(String topic, String content) {

        topic = topic.trim();
        content = content.trim();
        boolean isSucceed = false;
        if (topic.equals("taobao_refund_RefundCreated")) {
            //退款
            Trades trades = new Trades();
            Long tid = Long.parseLong(getMapFromContent(content).get("tid"));
            logger.info("退款,tid = [{}]", tid);
            if (tid != null) {
                trades.setTid(tid);
                trades.setIsRefund("1");
                isSucceed = tradesMapper.updateByPrimaryKeySelective(trades) > 0;
            }

        } else if (topic.equals("taobao_refund_RefundClosed")) {
            //取消退款
            Trades trades = new Trades();
            Long tid = Long.parseLong(getMapFromContent(content).get("tid"));
            logger.info("取消退款,tid = [{}]", tid);
            if (tid != null) {
                trades.setTid(tid);
                trades.setIsRefund("0");
                isSucceed = tradesMapper.updateByPrimaryKeySelective(trades) > 0;
            }
        } else if (topic.equals("taobao_trade_TradeMemoModified")) {//交易备注修改
            //taobao的数据
            Trades trades = new Trades();
            Long taobaoTid = Long.parseLong(getMapFromContent(content).get("tid"));
            String taobaoSellermemo = getMapFromContent(content).get("seller_memo");
            if (org.apache.commons.lang.StringUtils.isBlank(taobaoSellermemo)) {
                //某些情况下这一条订单没有备注，比如
                // String tid = "284704838052170693";
                // String token = "620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742";
                logger.info("没有备注，不做操作，tid=【{}】", taobaoTid);
            } else {
                logger.info("交易备注修改,tid= [{}],seller_memo = [{}]", taobaoTid, taobaoSellermemo);
                trades.setTid(taobaoTid);
                trades.setSellerMemo(taobaoSellermemo);
                isSucceed = tradesMapper.updateByPrimaryKeySelective(trades) > 0;
            }
        } else {
            logger.info("收到其他消息：topic=[{}],content=[{}]", topic, content);
        }
        return isSucceed;
    }


    /**
     * 得到message.content 的tid
     *
     * @param infoContent message.content
     * @return
     */
    public static Map<String, String> getMapFromContent(String infoContent) {
        Map<String, String> map = new HashMap<>();
        infoContent = infoContent.replace("{", "");
        infoContent = infoContent.replace("}", "");
//        infoContent = infoContent.replaceAll("\"", "");
        List<String> stringArrayList = Arrays.asList(infoContent.split(",\""));
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
