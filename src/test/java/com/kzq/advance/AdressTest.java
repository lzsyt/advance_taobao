package com.kzq.advance;

import com.kzq.advance.common.utils.TbaoUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdressTest {

    @Test
    public void test(){
//        TbaoUtils.waybillISearch("YTO");
//                URLUtils.addressResolution("上海  上海市  闵行区  七莘路1855号第七幢401A室");
        Date time =  TbaoUtils.findOneOrder("605596928346736714","620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742").getTrade().getConsignTime();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time));
    }

}
