package com.kzq.advance;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.TSellerCat;
import com.kzq.advance.mapper.TSellerCatMapper;
import com.kzq.advance.mapper.TShopMapper;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.SellerCat;
import com.taobao.api.domain.ShopCat;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvanceApplicationTests {
    @Resource
    private TSellerCatMapper tSellerCatMapper ;
    @Resource
    private TShopMapper shopMapper;
    @Test
    public void testShopCat(){
        List<TSellerCat> sellerCats = tSellerCatMapper.find(8);
        String shopName = shopMapper.findShopNameByShopId(8);
        List<ShopCat> shopCats = TbaoUtils.getShopCat();
    }

    @Test
    public void testSellerCat(){
        List<SellerCat> sellerCatList = TbaoUtils.getCategory("");
    }

    @Test
    public void contextLoads() {
        List<Item> tGoodsLinks = TbaoUtils.getItemsOnsale("", null, "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914");
        for (Item i:tGoodsLinks ) {
            if (StringUtils.isNotBlank((i.getPropertyAlias()))) {
                System.out.println("getPropertyAlias" + i.getPropertyAlias() + "..................");
            } else {
                System.out.println("null");
            }
        }


    }

}
