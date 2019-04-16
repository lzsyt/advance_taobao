<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>个人中心</title>

    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>

</head>
<body>
<section class="g-flexview">



    <div class="g-scrollview">

    <div class="m-cell demo-small-pitch">

        <div class="cell-item">
            <div class="cell-left">登录名：</div>
            <div class="cell-right"/>${loginName}</div>
        </div>
        <div class="cell-item">
            <div class="cell-left">真实姓名： </div>
            <div class="cell-right">
                ${name}
            </div>
        </div>


    </div>

        <a href="${path}/logout" class="btn-block btn-primary">退出登陆</a>



    </div>
    <jsp:include   page="footer.jsp" flush="true"/>

</section>



</body>
</html>