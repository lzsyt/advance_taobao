<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>进销存系统</title>

    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <link rel="stylesheet" href="${path}/style/css/ydui.css?rev=@@hash"/>
    <link rel="stylesheet" href="${path}/style/css/demo.css"/>
    <script src="${path}/js/ydui.flexible.js"></script>
    <script type="text/javascript" src="${path}/js/jquery.min.js"></script>




</head>
<body>
<header class="m-navbar">
    <div class="navbar-center"><span class="navbar-title">登录</span></div>
</header>

<div class="m-cell demo-small-pitch">

    <div class="cell-item">
        <div class="cell-left">姓名：</div>
        <div class="cell-right"><input type="text" id="name" name="name" class="cell-input"
                                       placeholder="请输入您的姓名" autocomplete="off" /></div>
    </div>
    <div class="cell-item">
        <div class="cell-left">密码：</div>
        <div class="cell-right"><input type="password" id="password" name="password"
                                       class="cell-input" placeholder="请输入您的密码" autocomplete="off" /></div>
    </div>
</div>
<input type="submit" id="submit"class="btn-block btn-primary" value="提交"/>

<script src="${staticPath }/js/ydui.js"></script>

<script type="text/javascript">


    !function (win, $) {
        var dialog = win.YDUI.dialog;

        //普通确认框

        $("#submit").click(function () {
            var name=$("#name").val();
            var password=$("#password").val();

            if (name==null||$.trim(name)==''){
                dialog.alert("用户名不能为空");
                return false;
            }
            if (password==null||$.trim(password)==''){
                dialog.alert("密码不能为空");
                return false;
            }
            $.post( '${path }/doLogin', {
                "name":name,"password":password
            }, function(result) {
                console.log("result:"+result);
                if (result.success) {
                    location.href="${path }/index";
                }else {
                    dialog.alert(result.msg);
                }

            }, 'json')
        }); }(window, jQuery);




</script>
</body>
</html>