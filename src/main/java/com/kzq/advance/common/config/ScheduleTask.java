package com.kzq.advance.common.config;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.domain.TShop;
import com.kzq.advance.domain.Trades;
import com.kzq.advance.mapper.TShopMapper;
import com.kzq.advance.mapper.TradesMapper;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradeMemoUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class ScheduleTask {

    @Resource
    private TShopMapper tShopMapper;

    @Resource
    private ScheduleUtil scheduleUtil;

    public static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    //每一分钟执行一次
    @Async
    @Scheduled(cron = "59 59 23 * * ?")
    public void Task(){
        logger.info("定时任务开始");
        List<TShop> tShopList = tShopMapper.selectAll();
        String sessionkey;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR,-1);//日期加10天
        Date start=calendar.getTime();

        logger.info("开始时间={}", new SimpleDateFormat("yyyy-MM-dd:HH:mm:sss").format(start));

        for (TShop t :tShopList) {
            sessionkey = t.getShopToken();
            scheduleUtil.compare(sessionkey, start);
        }
        logger.info("定时任务结束");
    }
}
