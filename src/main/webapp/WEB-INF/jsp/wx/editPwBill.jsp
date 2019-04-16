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

</head>
<body>
<section class="g-flexview">



    <div class="g-scrollview">
        <form name="serForm" action="${path}/pwBillInfo" id="serForm" method="post"  enctype="multipart/form-data">

    <div class="m-cell demo-small-pitch">

        <div class="cell-item">
            <div class="cell-left">入库单号：</div>
            <div class="cell-right">${pwBill.ref}<input name="id" id="pwBillId" type="hidden" value="${pwBill.id}"></div>
        </div>


        <div class="cell-item">
            <div class="cell-left">发货商品：</div>
            <div class="cell-right">
                <c:forEach items="${goodsList}" var="goods">
                    ${goods.goodsName} * ${goods.goodsCount}  ${goods.unitName};

                </c:forEach>
            </div>
        </div>
        <div class="cell-item">
            <div class="cell-left">状态：</div>
            <div class="cell-right">
                <c:if test="${pwBill.billStatus==0}">待入库</c:if>
                <c:if test="${pwBill.billStatus==1000}">已入库</c:if>
                <c:if test="${pwBill.billStatus==2000}">已退货</c:if>
            </div>
        </div>
        <div class="cell-item">
            <div class="cell-left">业务日期：</div>
            <div class="cell-right"> <fmt:formatDate value="${pwBill.bizDt}" type="both"/></div>
        </div>
        <div class="cell-item">
            <div class="cell-left">业务员：</div>
            <div class="cell-right">${pwBill.userName}</div>
        </div>
        <div class="cell-item">
            <div class="cell-left">供应商名称：</div>
            <div class="cell-right">${pwBill.supplierName}</div>
        </div>
        <div class="cell-item">
            <div class="cell-left">采购金额：</div>
            <div class="cell-right">${pwBill.goodsMoney}</div>
        </div>
        <div class="cell-item">
            <div class="cell-left">仓库名称：</div>
            <div class="cell-right">${pwBill.warehouseName}</div>
        </div>
        <div class="cell-item">
            <div class="cell-left">备注：</div>
            <div class="cell-right">${pwBill.billMemo}</div>
        </div>
    </div>



        <c:if test="${pwBill.billStatus==0}">
            <input type="button" class="btn-block btn-primary" onclick="check()" id="sub" value="提交入库"/>
        </c:if>
            <input type="button" class="btn-block btn-warning" onclick="back()" id="sub" value="返回"/>

        </form>
    </div>
    <jsp:include   page="footer.jsp" flush="true"/>

</section>
<script src="${staticPath }/static/js/ydui.js"></script>
<script type="application/javascript">
    function back() {
        location.href="${path}/pwBillList";
    }
function check(status) {
    var dialog = window.YDUI.dialog;

    dialog.confirm("确定提交入库？", function () {

        var id=$("#pwBillId").val();
        console.log("id="+id);

        var data={"id":id}
        $.ajax({
            url: "${path}/subPwBill",
            data:data,
            success: function(data){
            if (data==1){
                dialog.alert("提交成功");
                location.href="${path}/pwBillList";

            }
            if (data==-1){
                console.log("fail")
                location.href="${path}/login";

            }
            }});
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