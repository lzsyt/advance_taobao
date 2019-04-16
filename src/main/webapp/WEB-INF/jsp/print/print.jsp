<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../commons/global.jsp" %>
<html>
<head>

    <title>print</title>
</head>
<script>
    function doConnect()
    {
        socket = new WebSocket('ws://localhost:13528');
        //如果是https的话，端口是13529
        //socket = new WebSocket('wss://localhost:13529');
        // 打开Socket
        socket.onopen = function(event)
        {
            // 监听消息
            socket.onmessage = function(event)
            {
                console.log('Client received a message',event);
            };
            // 监听Socket的关闭
            socket.onclose = function(event)
            {
                console.log('Client notified socket has closed',event);
            };
        };
    }
    $(function () {
        doConnect();
    })
    /***
     *
     * 获取请求的UUID，指定长度和进制,如
     * getUUID(8, 2)   //"01001010" 8 character (base=2)
     * getUUID(8, 10) // "47473046" 8 character ID (base=10)
     * getUUID(8, 16) // "098F4D35"。 8 character ID (base=16)
     *
     */
    function getUUID(len, radix) {
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var uuid = [], i;
        radix = radix || chars.length;
        if (len) {
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
        } else {
            var r;
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random()*16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }
        return uuid.join('');
    }
    /***
     * 构造request对象
     */
    function getRequestObject(cmd) {
        var request  = new Object();
        request.requestID=getUUID(8, 16);
        request.version="1.0";
        request.cmd=cmd;
        return request;
    }
    /***
     * 获取自定义区数据以及模板URL
     * waybillNO 电子面单号
     */
    function getCustomAreaData( waybillNO){
        //获取waybill对应的自定义区的JSON object，此处的ajaxGet函数是伪代码
        var jsonObject = ajaxGet(waybillNO);
        var ret = new Object();
        ret.templateURL=jsonObject.content.templateURL;
        ret.data=jsonObject.content.data;
        return ret;
    }
    /***
     * 获取电子面单Json 数据
     * waybillNO 电子面单号
     */
    function getWaybillJson(waybillNO) {
        //获取waybill对应的json object，此处的ajaxGet函数是伪代码
        var jsonObject = ajaxGet(waybillNO);
        return jsonObject;
    }

    /**
     * 打印电子面单
     * printer 指定要使用那台打印机
     * waybillArray 要打印的电子面单的数组
     */
    function doPrint( printer, waybillArray)
    {
        var request = getRequestObject("print");
        request.task = new Object();
        request.task.taskID = getUUID(8,10);
        request.task.preview = false;
        request.task.printer = printer;
        var documents = new Array();
        for(i=0;i<waybillArray.length;i++) {
            var doc = new Object();
            doc.documentID = waybillArray[i];
            var content = new Array();
            var waybillJson = getWaybillJson(waybillArray[i]);
            var customAreaData = getCustomAreaData(waybillArray[i]);
            content.push(waybillJson,customAreaData);
            doc.content = content;
            documents.push(doc);
        }
        socket.send(JSON.stringify(request));
    }
    /**
     * 响应请求demo
     * */
    websocket.onmessage = function (event) {
        var response = eval(event.data);
        if (response.cmd == 'getPrinters') {
            getPrintersHandler(response);//处理打印机列表
        } else if (response.cmd == 'printerConfig') {
            printConfigHandler(response);
        }
    };
</script>
<body>

</body>
</html>
