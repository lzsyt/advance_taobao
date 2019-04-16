<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>进销存系统</title>

    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>

    <style>
        .myheight{
            height: fit-content;
        }
    </style>
</head>
<body>


    <footer class="m-tabbar">

        <a href="${path}/index" class="tabbar-item">
            <span class="tabbar-icon">
                <i class="icon-order"></i>
            </span>
            <span class="tabbar-txt">发货单</span>
        </a>
        <a href="${path}/pwBillList" class="tabbar-item">
            <span class="tabbar-icon">
                <i class="icon-feedback"></i>
            </span>
            <span class="tabbar-txt">采购订单</span>
        </a>
        <a href="${path}/userInfo" class="tabbar-item">
            <span class="tabbar-icon">
                <i class="icon-ucenter-outline"></i>
            </span>
            <span class="tabbar-txt">个人中心</span>
        </a>
    </footer>






</body>
</html>