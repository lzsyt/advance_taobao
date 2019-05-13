package com.kzq.advance;

import com.kzq.advance.common.tool.JdbcBean;
import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.TShop;
import com.kzq.advance.service.ITradesService;
import com.power.doc.builder.ApiDocBuilder;
import com.power.doc.model.ApiConfig;
import com.taobao.api.domain.Trade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvanceApplicationTests {

    @Autowired
    ITradesService iTradesService;

    @Test
    public void contextLoads() {
        ApiConfig config = new ApiConfig();
        //服务地址
        config.setServerUrl("http://localhost:8010");
        //生成到一个文档
        config.setAllInOne(true);
        //采用严格模式
        config.isStrict();
        //文档输出路径
        config.setOutPath("/Users/dujf/Downloads/md");
        ApiDocBuilder.builderControllersApi(config);
        //将生成的文档输出到/Users/dujf/Downloads/md目录下，严格模式下api-doc会检测Controller的接口注释
    }


//        Sku sku = TbaoUtils.getSkuBySkuId(4242355374163L);
//        String string = sku.getProperties();

    @Autowired
    private JdbcBean jdbcBean;

    @Test
    public void backup(){
//        DataBaseBackUp dataBaseBackUp = new DataBaseBackUp();
//        dataBaseBackUp.backup(jdbcBean);
//        JdbcBean jdbcBean = this.jdbcBean;
//        System.out.println(jdbcBean.toString());
        TShop shop = iTradesService.selectSessionKey(Integer.parseInt("9"));
        List<Trade> tradeList = new ArrayList<>();
        List<Trade> trades = TbaoUtils.findOrders(shop.getShopToken(), tradeList,1L);
        for (Trade t:trades) {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t.getPayTime()));
        }
        System.out.println(trades.size());

    }

}
