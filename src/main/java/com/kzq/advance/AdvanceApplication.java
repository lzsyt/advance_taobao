package com.kzq.advance;

import com.kzq.advance.common.util.SpringUtil;
import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.mapper.TradesMapper;
import com.kzq.advance.service.ITradesService;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.internal.toplink.LinkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AdvanceApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AdvanceApplication.class);
    }


    public static void main(String[] args) throws Exception {

        SpringApplication.run(AdvanceApplication.class, args);

        Logger logger = LoggerFactory.getLogger(AdvanceApplication.class);
        ITradesService iTradesService = SpringUtil.getBean(ITradesService.class);
        TmcClient client = new TmcClient("25500416", "25720ff4e7b9f8c5cfe95827c7e35479", "default"); // 关于default参考消息分组说明

        client.setMessageHandler(new MessageHandler() {
            public void onMessage(Message message, MessageStatus status) {
                try {
                    logger.info("getTopic = [{}]", message.getTopic());
                    logger.info("getContent = [{}]", message.getContent());
                    iTradesService.infoRefund(message.getTopic(), message.getContent());

                } catch (Exception e) {
                    logger.info("淘宝消息接口发生异常",e.getMessage());
                    e.printStackTrace();
                    status.fail(); // 消息处理失败回滚，服务端需要重发
                    logger.info("消息重发");
                    // 重试注意：不是所有的异常都需要系统重试。
                    // 对于字段不全、主键冲突问题，导致写DB异常，不可重试，否则消息会一直重发
                    // 对于，由于网络问题，权限问题导致的失败，可重试。
                    // 重试时间 5分钟不等，不要滥用，否则会引起雪崩
                }
            }
        });

        try {
            client.connect("ws://mc.api.taobao.com"); // 消息环境地址：ws://mc.api.tbsandbox.com/
        } catch (LinkException e) {
            e.printStackTrace();
        }
    }

}