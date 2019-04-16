package com.kzq.advance.service.impl;


import com.kzq.advance.common.utils.HttpUtils;
import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.*;
import com.kzq.advance.domain.Trades;
import com.kzq.advance.mapper.*;
import com.kzq.advance.service.ITradesService;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Sku;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.response.TradeFullinfoGetResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zmn
 * @since 2018-08-16
 */

@Service("ITradesService")
public class TradesImpl implements ITradesService {
    @Autowired
    private TradesMapper tradesMapper;
    @Autowired
    private TtradesOrderMapper tradesOrderMapper;
    @Autowired
    private TGoodsSkuMapper goodsSkuMapper;
    @Autowired
    private TGoodsLinkMapper goodsLinkMapper;
    @Autowired
    private TShopMapper shopMapper;


    protected Logger logger = LogManager.getLogger(TradesImpl.class);




    /**
     * 类型转换
     * @param t
     * @return
     */
    public TtradesOrder formOrder(Order t){
        TtradesOrder order=new TtradesOrder();

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
     *  自动生成出库单
     * @param param
     */
    public void addWsbill(String param) {//调用生成出库单的接口

            String sr=HttpUtils.sendPost("http://localhost/web/Home/Sale/addWSBill", "jsonStr: "+param);
            logger.info("生成出库单："+sr);

        

    }

    /**
     * 添加产品链接
     * @param item
     */
    public void addGoodLink(Item item) {


        TGoodsLink tGoodsLink=new TGoodsLink();
        tGoodsLink.setDetailUrl(item.getDetailUrl());
        tGoodsLink.setNumIid(item.getNumIid());
        tGoodsLink.setNick(item.getNick());
        tGoodsLink.setTitle(item.getTitle());
        tGoodsLink.setState(0);
        //分割型号的字符串
        item.getPropertyAlias();
        //有型号
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getPropertyAlias())){
            tGoodsLink.setState(1);

            //分割型号的字符串为map
            Map<String,String> map = getProperty(item.getPropertyAlias());
            List<Sku> skus = item.getSkus();
            for (Sku s:skus){
                TGoodsSku goodsSku=new TGoodsSku();
                goodsSku.setPropertiesAlias(map.get(s.getProperties()));
                goodsSku.setNumIid(item.getNumIid());
                goodsSku.setSkuId(s.getSkuId());
                //插入型号
                goodsSkuMapper.insertSelective(goodsSku);

            }

        }else {
            tGoodsLink.setState(0);
        }

        //插入产品链接
        goodsLinkMapper.insertSelective(tGoodsLink);
    }



    /**
     * 拆分属性字符串
     * @param PropertyAlias
     * @return
     */

    public  Map<String,String> getProperty(String PropertyAlias){
        //拆分属性字段
            String [] properties=  PropertyAlias.split(";");
            Map<String,String> map=new HashMap<>();

            for (int x=0;x<properties.length;x++){

                String prop=properties[x];
                String  prop0=prop.substring(0,prop.lastIndexOf(":")) ;
                String  prop1=prop.substring(prop.lastIndexOf(":")+1,prop.length()) ;
                map.put(prop0,prop1);

            }
            return map;

    }

    /**
     * 更新商品链接
     * @param item
     */
    public void updateGoodLink(Item item) {

        goodsLinkMapper.updateByPrimaryKey(formatTGoodsLink(item));
    }


    /**
     * 类型转换
     * @param item
     * @return
     */
    public TGoodsLink formatTGoodsLink(Item item){
        TGoodsLink tGoodsLink=new TGoodsLink();
        tGoodsLink.setDetailUrl(item.getDetailUrl());
        tGoodsLink.setNumIid(item.getNumIid());
        tGoodsLink.setNick(item.getNick());
        tGoodsLink.setTitle(item.getTitle());
        return tGoodsLink;
    }


    /**
     * 更新链接
     * @param item
     */

    public void updateLink(Item item){
        TGoodsLink tGoodsLink=new TGoodsLink();
        tGoodsLink.setDetailUrl(item.getDetailUrl());
        tGoodsLink.setNumIid(item.getNumIid());
        tGoodsLink.setNick(item.getNick());
        tGoodsLink.setTitle(item.getTitle());
        tGoodsLink.setState(0);
        //分割型号的字符串
        item.getPropertyAlias();
        //有型号
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getPropertyAlias())){
            tGoodsLink.setState(1);
            Map<String,String> map = getProperty(item.getPropertyAlias());
            List<Sku> skus = item.getSkus();
            for (Sku s:skus){
                TGoodsSku goodsSku=new TGoodsSku();

                goodsSku.setPropertiesAlias(map.get(s.getProperties()));
                goodsSku.setNumIid(item.getNumIid());
                goodsSku.setSkuId(s.getSkuId());


                goodsSkuMapper.deleteByPrimaryKey(s.getSkuId());
                goodsSkuMapper.insertSelective(goodsSku);

            }

        }else {
            tGoodsLink.setState(0);
        }

        //插入产品链接
        goodsLinkMapper.updateByPrimaryKey(tGoodsLink);
    }
    /**
     * 只更新不删除
     * @param item
     */

/*    public void updateLinkNotDele(Item item){
        TGoodsLink tGoodsLink=new TGoodsLink();
        tGoodsLink.setDetailUrl(item.getDetailUrl());
        tGoodsLink.setNumIid(item.getNumIid());
        tGoodsLink.setNick(item.getNick());
        tGoodsLink.setTitle(item.getTitle());
        //有型号
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getPropertyAlias())){
            tGoodsLink.setState(1);
            //拆分属性字段为map
            Map<String,String> map = getProperty(item.getPropertyAlias());
            List<Sku> skus = item.getSkus();
            for (Sku s:skus){
                TGoodsSku goodsSku=new TGoodsSku();

                goodsSku.setPropertiesAlias(map.get(s.getProperties()));
                goodsSku.setNumIid(item.getNumIid());
                goodsSku.setSkuId(s.getSkuId());
               //判断是否存在链接
                TGoodsSku sku=findSkuById(s.getSkuId());
                if (sku!=null&&sku.getSkuId()!=0){
                    goodsSkuMapper.updateByPrimaryKey(sku);
                }else {
                    goodsSkuMapper.insert(sku);

                }


            }

        }else {
            tGoodsLink.setState(0);
        }

        //插入产品链接
        goodsLinkMapper.updateByPrimaryKey(tGoodsLink);
    }*/

    /**
     * 插入sku属性
     * @param sku
     */
    public void addGoodSku(TGoodsSku sku){
        goodsSkuMapper.insertSelective(sku);

    }

    /**
     * 更新sku属性
     * @param sku
     */

    public void updateGoodSku(TGoodsSku sku){
        goodsSkuMapper.updateByPrimaryKey(sku);

    }

    /**
     * 根据id查询link
     * @param numIid
     * @return
     */
    public TGoodsLink findLinkById(Long numIid){
        TGoodsLink link=goodsLinkMapper.selectByPrimaryKey(numIid);
        return link;
    }

    /**
     * 根据id查询sku
     * @param skuId
     * @return
     */
    public TGoodsSku findSkuById(Long skuId){
        TGoodsSku sku=goodsSkuMapper.selectByPrimaryKey(skuId);
        return sku;
    }

    /**
     *  获取主图的链接
     */
    public List<TGoodsLink> selectByPic(){
        return goodsLinkMapper.selectByPic();
    }

    /**
     *  获取所有店铺的sessionkey信息
     */
    public List<TShop> selectAllShop() {return shopMapper.selectAll();
    }

    /**
     *  获取某个店铺的sessionkey信息
     */
    public TShop selectShop(TShop shop) {

        return shopMapper.selectShop(shop);
    }
    /**
     * 更新链接
     * @param item
     */

    @Override
    public void updateLinkNotDele(Item item) {
        TGoodsLink tGoodsLink=new TGoodsLink();
        tGoodsLink.setDetailUrl(item.getDetailUrl());
        tGoodsLink.setNumIid(item.getNumIid());
        tGoodsLink.setNick(item.getNick());
        tGoodsLink.setTitle(item.getTitle());
        tGoodsLink.setPicPath(item.getPicUrl());

        //有型号
        if (org.apache.commons.lang.StringUtils.isNotBlank(item.getPropertyAlias())){
            tGoodsLink.setIsSku(1);
            //拆分属性字段为map
            Map<String,String> map = getProperty(item.getPropertyAlias());
            List<Sku> skus = item.getSkus();
            for (Sku s:skus){
                TGoodsSku goodsSku=new TGoodsSku();

                goodsSku.setPropertiesAlias(map.get(s.getProperties()));
                goodsSku.setNumIid(item.getNumIid());
                goodsSku.setSkuId(s.getSkuId());
                //判断是否存在链接
                TGoodsSku sku=findSkuById(s.getSkuId());
                if (sku!=null&&sku.getSkuId()!=0){
                    goodsSkuMapper.updateByPrimaryKey(goodsSku);
                }else {
                    goodsSkuMapper.insert(goodsSku);

                }


            }

        }else {
            tGoodsLink.setIsSku(0);
        }

        //插入产品链接
        goodsLinkMapper.updateByPrimaryKeySelective(tGoodsLink);
    }

    public TShop selectSessionKey(Integer id ){
        return shopMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取某个订单详情
     * @param tid
     * @param shopId
     * @return
     */
    public TradeFullinfoGetResponse getOrder(String tid,String shopId){

        TradeFullinfoGetResponse re=null;

       //订单号不为空
        if(!StringUtils.isEmpty(tid)){
            if(!StringUtils.isEmpty(shopId)){
                logger.info("shopId不为空："+shopId);
                TShop shop=selectSessionKey(Integer.parseInt(shopId));
                re=TbaoUtils.findOneOrder(tid,shop.getShopToken());
                TbaoUtils.setCurrentSessionKey(shop.getShopToken());
                if(StringUtils.isEmpty(re.getSubMsg())){
                    return re;
                }
            }else {
                logger.info("在所有店铺里查询订单"+tid);
                //获取所有sessionKey
                List<TShop> list= selectAllShop();
                for (TShop s:list){
                    logger.info(s.getShopName());
                    if (!StringUtils.isEmpty(s.getShopToken())){
                        //获取一个订单
                        re=TbaoUtils.findOneOrder(tid,s.getShopToken());
                        if(StringUtils.isEmpty(re.getSubMsg())){
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
     * @param tid
     * @param session
     * @return
     */
    public String getMemo(String tid,String session){

        String  memo=null;

        //查询出所有店铺的session
        if(!StringUtils.isEmpty(tid)){
            //获取所有sessionKey
            if(!StringUtils.isEmpty(session)){
                logger.info("session不为空："+session);

                memo=TbaoUtils.findOrderMemo(tid,session);
                return memo;

            }else {
                logger.info("在所有店铺里查询订单"+tid);

                List<TShop> list= selectAllShop();
                for (TShop s:list){
                    if (!StringUtils.isEmpty(s.getShopToken())){
                        //获取一个订单
                        memo=TbaoUtils.findOrderMemo(tid,s.getShopToken());
                        if(org.apache.commons.lang.StringUtils.isNotBlank(memo)){
                            return memo;
                        }
                    }
                }
            }


        }
        return null;

    }
    public Trades findTrade(Long tid){

        return tradesMapper.selectByPrimaryKey(tid);
    }

    @Override
    public List<TGoodsLink> selectByShop(String nick) {

        return goodsLinkMapper.selectByShop(nick);
    }


}
