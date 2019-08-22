package com.kzq.advance;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.StandardTemplateResult;
import com.kzq.advance.mapper.StandardTemplateResultMapper;
import com.taobao.api.response.CainiaoCloudprintStdtemplatesGetResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

    @Resource
    private StandardTemplateResultMapper standardTemplateResultMapper;

    @Test
    public void t(){
        List<CainiaoCloudprintStdtemplatesGetResponse.StandardTemplateResult> standardTemplateResults = TbaoUtils.stdtemplatesGet();
        for (CainiaoCloudprintStdtemplatesGetResponse.StandardTemplateResult s : standardTemplateResults) {
            for (CainiaoCloudprintStdtemplatesGetResponse.StandardTemplateDo t: s.getStandardTemplates()) {
                StandardTemplateResult standardTemplateResult = new StandardTemplateResult();
                BeanUtils.copyProperties(t, standardTemplateResult);
                standardTemplateResult.setCpCode(s.getCpCode());
                standardTemplateResultMapper.insertUseGeneratedKeys(standardTemplateResult);
                System.out.println(standardTemplateResult.getId());
            }

        }
    }

    String momo = "需要开发票，抬头：大连理工大学，税号：12100000422436090K\n" +
            "8.3聚光明阿里,孙蓉采购振荡器下单成本：410元(340+70运费=410),订单号：564254082183015367\n" +
            "8.3李娟核实付款410.00元\n" +
            "8.6张梅  顾客不是要这种\n" +
            "0817周艳鑫皓开票46725980，圆通YT4035729012850\n" +
            "0819系统管理员鑫皓开票46725981，圆通YT4035729012850\n" +
            "0819系统管理员鑫皓开票46725982，圆通YT4035729012850\n" +
            "0819系统管理员鑫皓开票46725984，圆通YT4035729012850\n" +
            "0819系统管理员鑫皓开票46725985，圆通YT4035729012850\n" +
            "0819系统管理员鑫皓开票46725986，圆通YT4035729012850\n" +
            "0819系统管理员鑫皓开票46725998，圆通YT4035729012850\n" +
            "0819系统管理员鑫皓开票46725996，圆通YT4035729012850\n" +
            "0819系统管理员鑫皓开票46725993，圆通YT4035729012850";


    @Test
    public void tst(){
        String token = "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914";
        String tid = "550435343932168510";
        String mome = TbaoUtils.findOrderMemo(tid, token);
        System.out.println(mome);


//        String deletemomo = "0819系统管理员鑫皓开票46725981，圆通YT4035729012850\n" +
//                "0819系统管理员鑫皓开票46725982，圆通YT4035729012850\n" +
//                "0819系统管理员鑫皓开票46725984，圆通YT4035729012850\n" +
//                "0819系统管理员鑫皓开票46725985，圆通YT4035729012850\n" +
//                "0819系统管理员鑫皓开票46725986，圆通YT4035729012850\n" +
//                "0819系统管理员鑫皓开票46725998，圆通YT4035729012850\n" +
//                "0819系统管理员鑫皓开票46725996，圆通YT4035729012850\n" +
//                "0819系统管理员鑫皓开票46725993，圆通YT4035729012850";
//        TbaoUtils.deleteMemo(tid, deletemomo, token);
//        System.out.println(TbaoUtils.findOrderMemo(tid, token));
    }
}
