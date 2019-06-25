package com.kzq.advance;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.ItemSkuGetRequest;
import com.taobao.api.response.ItemSkuGetResponse;
import org.junit.Test;

public class TestSku {


    @Test
    public void test(){
        DefaultTaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", "25500416", "25720ff4e7b9f8c5cfe95827c7e35479");
        String sessionKey = "6200824224b73677a8d4375add3237e3ZZ21bb86aa67d8c305543718";
        ItemSkuGetRequest req = new ItemSkuGetRequest();
        req.setFields("sku_id,iid,properties,quantity,price,outer_id,created,modified,status,properties_name,num_iid");
        req.setSkuId(4266817362291L);
        ItemSkuGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
    }
}
