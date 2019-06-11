package com.kzq.advance;

import com.kzq.advance.common.utils.TbaoUtils;
import com.kzq.advance.service.ITradesService;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.internal.toplink.LinkException;
import com.taobao.api.request.TradeMemoUpdateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvanceApplicationTests {

    @Autowired
    ITradesService iTradesService;

    @Test
    public void contextLoads() {
//        ApiConfig config = new ApiConfig();
//        //服务地址
//        config.setServerUrl("http://192.168.1.125:8080/");
//        //生成到一个文档
//        config.setAllInOne(false);
//        //采用严格模式
//        config.isStrict();
//        //文档输出路径
//        config.setOutPath("/doc");
//        ApiDocBuilder.builderControllersApi(config);
//        //将生成的文档输出到/Users/dujf/Downloads/md目录下，严格模式下api-doc会检测Controller的接口注释
    }


//        Sku sku = TbaoUtils.getSkuBySkuId(4242355374163L);
//        String string = sku.getProperties();

//    @Autowired
//    private JdbcBean jdbcBean;
//
//    @Test
//    public void backup(){
////        DataBaseBackUp dataBaseBackUp = new DataBaseBackUp();
////        dataBaseBackUp.backup(jdbcBean);
////        JdbcBean jdbcBean = this.jdbcBean;
////        System.out.println(jdbcBean.toString());
//        TShop shop = iTradesService.selectSessionKey(Integer.parseInt("9"));
//        List<Trade> tradeList = new ArrayList<>();
//        List<Trade> trades = TbaoUtils.findOrders(shop.getShopToken(), tradeList,1L);
//        for (Trade t:trades) {
//            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t.getPayTime()));
//        }
//        System.out.println(trades.size());
//
//    }

    @Test
    public void testGetTrade() {
//        String files = "tid,type,status,payment,orders";
//        String orders = "222039342296450103";
//        String sessionkey = "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914";
//        String string = TbaoUtils.getTrade(files, orders, sessionkey).getTrade().getStatus();
//        System.out.println(TradeStatus.getValueByKey(string));
    }


    @Test
    public void printTradleStatus() {
//        for (TradeStatus t : TradeStatus.values()) {
//            System.out.println(t.getValues());
//        }
    }

    @Test
    public void refund() {
//        Long start = System.currentTimeMillis();
//
//        Trade trade = TbaoUtils.getBillDetail("470596321371328882", "created,modified,end_time", "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914");
//
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        System.out.println("created：" + dateFormat.format(trade.getCreated()));
//        System.out.println("modified：" + dateFormat.format(trade.getModified()));
//        System.out.println("end_time：" + dateFormat.format(trade.getEndTime()));
//
//
//        List<Refund> refundArrayList = TbaoUtils.getRefund("6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914");
//
//        for (Refund refund:refundArrayList) {
//            if (refund.getTid().equals("450548994078385285")){
//                System.out.println("..............................");
//            }
//            System.out.println("tid："+refund.getTid() + "      created ：" + dateFormat.format(refund.getCreated()) + "         modified：" + dateFormat.format(refund.getModified()));
//        }
//        System.out.println(refundArrayList.size());
//        Long end = System.currentTimeMillis();
//        System.out.println(end - start);
    }


    @Test
    public void test() {
//        String sessionKey = "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914";
//        String refundid = "27820354954388552";
//        Refund refund = TbaoUtils.getRefundDetail(refundid, sessionKey);
//        System.out.println("tid：" + refund.getTid());
//        System.out.println("refund：" + refund.getRefundId());
    }


//    @Test
//    public void testdate() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        System.out.println(dateFormat.format(new Date()));
//    }

//    @Test
//    public void getrefunddetail() {
////        Refund refund = TbaoUtils.getRefundDetail("27820354954388552", "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914");
////        Date created = refund.getCreated();
////        Date modify2 = refund.getModified();
////
////        long start = System.currentTimeMillis();
////        Trade trade = TbaoUtils.getTrade("modified", String.valueOf(refund.getTid()), "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914").getTrade();
////        long end = System.currentTimeMillis();
////        System.out.println("查询的时间为：" + (end - start));
////
////
////        Date modify = trade.getModified();
////
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
////        TbaoUtils.getRefund("620192999bded03c32cb6d579d53619524170ZZ3d62d8f22231644742",new List<Refund>());
////        Long start2 = System.currentTimeMillis();
////        List<Refund> refundList = TbaoUtils.getRefund("6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914", new ArrayList<Refund>(), 1L, dateFormat.format(modify), dateFormat.format(new Date()));
////        Long end2 = System.currentTimeMillis();
////        System.out.println("查询的时间为：" + (end2 - start2));
////
////
////        refundList.size();
////
////
////        Refund refund = TbaoUtils.getRefundDetail("28046019787024588", "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914");
////        System.out.println("创建时间：" + dateFormat.format(refund.getCreated()) + "修改时间：" + dateFormat.format(refund.getModified()));
//        //<editor-fold desc="测试分类">
//        String sessionKey2 = "6200824224b73677a8d4375add3237e3ZZ21bb86aa67d8c305543718";//7
//        String sessionKey = "6201e18676d89175cfea2e4f59ZZ7e66d179cbad513a6ff1739075914";//8
//
//        String tid = "470596321371328882";
//
//        Trade trade = TbaoUtils.getTrade("created,modified", tid, sessionKey2).getTrade();
//
//        Date tradeModified = trade.getModified();
//
//        Date start, end = null;
//        Calendar c = Calendar.getInstance();
//        c.setTime(tradeModified);
//        c.add(Calendar.MINUTE, -1);
//        start = c.getTime();
//        c.add(Calendar.HOUR, 1);
//        end = c.getTime();
//
//        List<Refund> refundList = TbaoUtils.getRefund(sessionKey2, new ArrayList<Refund>(), 1L, start, end);
//
//        for (Refund refund : refundList) {
//            if (String.valueOf(refund.getTid()).equals(tid)) {
//                System.out.println("tid为：" + tid + "的存在");
//            }
//        }
//
//        System.out.println(dateFormat.format(trade.getCreated()));
//        System.out.println(dateFormat.format(tradeModified));
//        System.out.println(dateFormat.format(start));
//        System.out.println(dateFormat.format(end));
//        //</editor-fold>
//
//    }


    @Test
    public void testXiaoXi() {

        String tid = "425524353967012217";
        String token = "6200824224b73677a8d4375add3237e3ZZ21bb86aa67d8c305543718";


        //<editor-fold desc="这是接收消息的">

        TmcClient client = new TmcClient("25500416", "25720ff4e7b9f8c5cfe95827c7e35479", "default"); // 关于default参考消息分组说明
        client.setMessageHandler(new MessageHandler() {
            public void onMessage(Message message, MessageStatus status) {
                try {
                    System.out.println("getTopic：" + message.getTopic());
                    System.out.println("getContent" + message.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                    status.fail(); // 消息处理失败回滚，服务端需要重发
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
//        //<editor-fold desc="原备注">
//        //光合硅能旗舰店
        String memo = TbaoUtils.findOrderMemo(tid, token);
//        System.out.println("原备注");
//        System.out.println(memo);
//        System.out.println("...................");
//        //</editor-fold>


        TradeMemoUpdateRequest request = new TradeMemoUpdateRequest();
        request.setTid(Long.parseLong(tid));
        request.setMemo(memo+";添加测试备注");
        TbaoUtils.updateTradeMemo(request, token);


//        //<editor-fold desc="原备注">
//        //光合硅能旗舰店
//        String memo2 = TbaoUtils.findOrderMemo(tid, token);
//        System.out.println("修改之后的备注");
//        System.out.println(memo2);
//        System.out.println("..................");
//        //</editor-fold>
//
//        System.out.println("还原");
//        TradeMemoUpdateRequest replaceRequest = new TradeMemoUpdateRequest();
//        String originalMemo = TbaoUtils.findOrderMemo(tid, token);
//        replaceRequest.setTid(Long.parseLong(tid));
//        String newMome = originalMemo.replace(";添加测试备注;添加测试备注;添加测试备注", "");
//        replaceRequest.setMemo(newMome);
//        TbaoUtils.updateTradeMemo(replaceRequest, token);
//        System.out.println("............................");
//
//        String memo3 = TbaoUtils.findOrderMemo(tid, token);
//        System.out.println("还原之后的备注");
//        System.out.println(memo3);
//        System.out.println("..................");

        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
        //

//    //查看授权列表
//    @Test
//    public void xiaoxifuwuapi(){
//
//        DefaultTaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", "25500416", "25720ff4e7b9f8c5cfe95827c7e35479");
//
//        TmcUserTopicsGetRequest req = new TmcUserTopicsGetRequest();
//        req.setNick("光合旗舰店");
//        TmcUserTopicsGetResponse rsp = null;
//        try {
//            rsp = client.execute(req);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        System.out.println(rsp.getBody());
//    }
//
//
//    public void primit(String token){
//        //光合旗舰店
////        String token = "6200824224b73677a8d4375add3237e3ZZ21bb86aa67d8c305543718";
//        DefaultTaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", "25500416", "25720ff4e7b9f8c5cfe95827c7e35479");
//        TmcUserPermitRequest req = new TmcUserPermitRequest();
////        req.setTopics("taobao_trade_TradeMemoModified");
////        req.setTopics("taobao_trade_TradeMemoModified,taobao_refund_RefundSuccess");
//        req.setTopics("taobao_refund_RefundCreated,taobao_refund_RefundClosed");
//        TmcUserPermitResponse rsp = null;
//        try {
//            rsp = client.execute(req, token);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        System.out.println(rsp.getBody());
//    }
//
//
//添加测试备注添加测试备注添加测试备注添加测试备注添加测试备注添加测试备注

    @Test
    public void replace(){
//        String tid = "425524353967012217";
//        String token = "6200824224b73677a8d4375add3237e3ZZ21bb86aa67d8c305543718";
//
//        //光合硅能旗舰店
//        String memo2 = TbaoUtils.findOrderMemo(tid, token);
//        System.out.println("原备注");
//        System.out.println(memo2);
//        System.out.println("..................");
//
//        System.out.println("还原");
//        TradeMemoUpdateRequest replaceRequest = new TradeMemoUpdateRequest();
//        String originalMemo = TbaoUtils.findOrderMemo(tid, token);
//        replaceRequest.setTid(Long.parseLong(tid));
//        String newMome = originalMemo.replace(";添加测试备注;添加测试备注;添加测试备注", "");
//        replaceRequest.setMemo(newMome);
//        TbaoUtils.updateTradeMemo(replaceRequest, token);
//        System.out.println("............................");
//
//
//        String memo3 = TbaoUtils.findOrderMemo(tid, token);
//        System.out.println("还原之后的备注");
//        System.out.println(memo3);
//        System.out.println("..................");

    }
//
//
//    //查询备注
//    @Test
//    public void findmem(){
//        String tid = "425524353967012217";
//        String token = "6200824224b73677a8d4375add3237e3ZZ21bb86aa67d8c305543718";
//        String memo2 = TbaoUtils.findOrderMemo(tid, token);
//        System.out.println("修改之后的备注");
//        System.out.println(memo2);
//        System.out.println("..................");
//    }
}