package com.kzq.advance;

import com.kzq.advance.service.ITradesService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemSkusGetRequest;
import com.taobao.api.response.ItemSkusGetResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGoodLink {

    @Autowired
    private ITradesService iTradesService;


    @Test
    public void test(){


        String session = "620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742";

        HashMap<Long, Item> itemHashMap = iTradesService.getItems(session, null, null);


        for (Map.Entry entry:itemHashMap.entrySet()) {
            if ((String.valueOf(entry.getKey())).equals("584375462366")) {
                System.out.println("true");
            }
//            String title = ((Item) entry.getValue()).getTitle();
//            if (title.equals("光合硅能12v8ah蓄电池地摊喷雾器免维护UPS太阳能电瓶12伏小电瓶")) {
//                System.out.println("true");
//            }
//            System.out.println("title：" + title);
        }
    }

    /**
     * SELECT * from t_goods_link_map WHERE num_iid ='584375462366';
     * SELECT * from t_goods_sku WHERE num_iid ='584375462366';
     */

    @Test
    public void tset(){
        DefaultTaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", "25500416", "25720ff4e7b9f8c5cfe95827c7e35479");
        String sessionKey = "620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742";
        ItemSkusGetRequest req = new ItemSkusGetRequest();
        req.setFields("sku_id,num_iid,properties_name,properties");
        req.setNumIids("584375462366");
        ItemSkusGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
    }
}
