<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>发货单详情</title>

    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>

</head>
<body>
<section class="g-flexview">



    <div class="g-scrollview">
        <form name="serForm" action="${path}/saveWsBill" id="serForm" method="post"  enctype="multipart/form-data">

    <div class="m-cell demo-small-pitch">

        <div class="cell-item">
            <div class="cell-left">订单号：</div>
            <div class="cell-right">${wsBill.shopNum}<input name="id" type="hidden" value="${wsBill.id}"></div>
        </div>


        <div class="cell-item">
            <div class="cell-left">收货地址：</div>
            <div class="cell-right">${wsBill.dealAddress}</div>
        </div>
        <div class="cell-item">
            <div class="cell-left">收货人：</div>
            <div class="cell-right">${customer.name}</div>
        </div>
        <div class="cell-item">
            <div class="cell-left">手机号：</div>
            <div class="cell-right">${customer.mobile01}</div>
        </div>
        <div class="cell-item">
            <div class="cell-left">备注：</div>
            <div class="cell-right">${wsBill.memo}</div>
        </div>
    </div>
      <div class="m-celltitle">发货内容</div>

    <c:forEach items="${goodsList}" var="goods" varStatus="status">
     <div class="m-cell">
            <a class="cell-item" href="javascript:;">
                <div class="cell-left"><i class="cell-icon icon-order"></i>${goods.goodsName}</div>
                <div class="cell-right cell-arrow">${goods.goodsCount}  ${goods.unitName}</div>
            </a>
         <a class="cell-item" href="javascript:;">
             <div class="cell-left"></i>发货仓库</div>
             <div class="cell-right cell-arrow">${goods.warehouse} </div>
         </a>
        </div>
    </c:forEach>

    <div class="m-cell demo-small-pitch">
        <c:if test="${wsBill.billStatus==3000}">

                <div class="cell-item">
                    <div class="cell-left">快递公司：</div>
                    <div class="cell-right"><input type="text" name="expressCompany"  value="${wsBill.expressCompany}" class="cell-input" disabled/></div>

                </div>
                <div class="cell-item">
                    <div class="cell-left">发货单号：</div>
                    <div class="cell-right"><input type="text" name="expressNum"  value="${wsBill.expressNum}" class="cell-input" disabled/></div>
                </div>

                <div class="cell-item">
                    <div class="cell-left"><c:if test="${not empty wsBill.filePath}"><img id="expressImg" src="${path}/static${wsBill.filePath}"></c:if></div>
                    <div class="cell-right"></div>
                </div>
        </c:if>
        <c:if test="${wsBill.billStatus!=3000}">
            <div class="cell-item">
                <div class="cell-left">快递公司：</div>
                <div class="cell-right"><input type="text" name="expressCompany" id="expressCompany" value="${wsBill.expressCompany}" class="cell-input" /></div>

            </div>
            <div class="cell-item">
                <div class="cell-left">发货单号：</div>
                <div class="cell-right"><input type="text" name="expressNum" id="expressNum" value="${wsBill.expressNum}" class="cell-input" /></div>
            </div>

            <div class="cell-item">
                <div class="cell-left"><c:if test="${not empty wsBill.filePath}"><img id="expressImg" src="${path}/static${wsBill.filePath}"></c:if></div>
                <div class="cell-right"><input type="file" name="fileName" id="fileName"    value="上传" /><input type="hidden" name="file" /></div>
            </div>
        </c:if>
    </div>




        <c:if test="${wsBill.billStatus!=3000}">
            <input type="button" class="btn-block btn-primary" onclick="check()" id="sub" value="提交"/>
        </c:if>
            <input type="button" class="btn-block btn-warning" onclick="back()" id="sub" value="返回"/>

        </form>
    </div>

    <jsp:include   page="wFooter.jsp" flush="true"/>
</section>
<script type="application/javascript">
    function back() {
     location.href="${path}/index";
    }
function check() {
    var dialog = window.YDUI.dialog;


    var expressCompany = $("#expressCompany").val();
    var expressNum = $("#expressNum").val();
    var fileName = $("#fileName").val();
    var form = document.serForm;
    console.log("fileName:" + fileName);
    var flag = 0;
    if (isNull(fileName) && isNull(expressNum) && isNull(expressCompany)) {
        dialog.alert("请填写或者上传单号");
        return false;

    } else if (!isNull(fileName)) {


    }
    else if (isNull(expressNum) && !isNull(expressCompany)) {
        dialog.alert("请填写单号");
        return false;


    } else if (!isNull(expressNum) && isNull(expressCompany)) {
        dialog.alert("请填写快递公司");
        return false;

    }

    dialog.confirm("确定提交发货信息？", function () {
         form.submit();


    });
    return false;

}

        function isNull(str) {
            if (str==null||$.trim(str)==""){
                return true;
            }
            return false;

        }


</script>


</body>
</html>