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
}
