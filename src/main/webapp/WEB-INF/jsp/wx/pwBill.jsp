<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>采购入库</title>

    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>

</head>
<body>
<section class="g-flexview m-tab"  data-ydui-tab>
    <div class="m-cell demo-small-pitch">
        <form id="searchForm" action="${path}/pwBillList" type="post">

            <div class="cell-item">
                <div class="cell-left cell-width">
                    <input type="text" id="keyword"  name="keyword" class="cell-input" placeholder="输入订单号、仓库关键词" autocomplete="off" />
                </div>
                <div class="cell-right">
                    <a href="javascript:;" id="searchBtn" class="btn btn-primary">查询</a>
                </div>
            </div>

        </form>

    </div>
    <ul class="tab-nav">
        <li class="tab-nav-item tab-active"><a href="javascript:;">全部</a></li>
        <li class="tab-nav-item"><a href="javascript:;">待入库</a></li>
        <li class="tab-nav-item"><a href="javascript:;">已入库</a></li>
        <li class="tab-nav-item"><a href="javascript:;">已退货</a></li>
    </ul>

    <div class="g-scrollview">
        <div class="tab-panel">
            <div class="tab-panel-item tab-active">
                <div class="m-cell">
                    <c:forEach items="${pwBills}" var="wsbill">
                        <a href="${path}/pwBillInfo/${wsbill.id}" class="cell-item">
                            <div class="list-mes">
                                <div class="cell-left">订单号:${wsbill.ref}</div>
                                <div class="list-mes-item">
                                        ${wsbill.warehouseName}
                                </div>
                                <div class="list-mes-item">
                                    <fmt:formatDate value="${wsbill.bizDt}" type="both"/>
                                </div>

                            </div>

                            <div class="cell-right cell-arrow">
                                <c:if test="${wsbill.billStatus==0}"><font style="color:red;">待入库</font></c:if>
                                <c:if test="${wsbill.billStatus==1000}">已入库</c:if>
                                <c:if test="${wsbill.billStatus==2000}">已退货</c:if>
                            </div>
                        </a>
                    </c:forEach>



                </div>
            </div>
            <div class="tab-panel-item">
                <div class="m-cell">
                    <c:forEach items="${pwBills}" var="wsbill">
                        <c:if test="${wsbill.billStatus==0}">
                            <a href="${path}/pwBillInfo/${wsbill.id}" class="cell-item">
                                <div class="list-mes">
                                    <div class="cell-left">订单号:${wsbill.ref}</div>

                                    <div class="list-mes-item">
                                        <fmt:formatDate value="${wsbill.bizDt}" type="both"/>
                                    </div>
                                </div>
                                <div class="cell-right cell-arrow">
                                    <c:if test="${wsbill.billStatus==0}">待入库</c:if>

                                </div>
                            </a>
                        </c:if>
                    </c:forEach>

                </div>
            </div>
            <div class="tab-panel-item">
                <div class="m-cell">
                    <c:forEach items="${pwBills}" var="wsbill">
                        <c:if test="${wsbill.billStatus==1000}">
                            <a href="${path}/pwBillInfo/${wsbill.id}" class="cell-item">
                                <div class="list-mes">
                                    <div class="cell-left">订单号:${wsbill.ref}</div>

                                    <div class="list-mes-item">
                                        <fmt:formatDate value="${wsbill.bizDt}" type="both"/>
                                    </div>
                                </div>
                                <div class="cell-right cell-arrow">
                                    <c:if test="${wsbill.billStatus==1000}">已入库</c:if>
                                </div>
                            </a>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="tab-panel-item">
                <div class="m-cell">
                    <c:forEach items="${pwBills}" var="wsbill">
                        <c:if test="${wsbill.billStatus==2000}">
                            <a href="${path}/pwBillInfo/${wsbill.id}" class="cell-item">
                                <div class="list-mes">
                                    <div class="cell-left">订单号:${wsbill.ref}</div>

                                    <div class="list-mes-item">
                                        <fmt:formatDate value="${wsbill.bizDt}" type="both"/>
                                    </div>
                                </div>
                                <div class="cell-right cell-arrow">
                                    <c:if test="${wsbill.billStatus==2000}">已退货</c:if>
                                </div>
                            </a>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>


    <jsp:include   page="footer.jsp" flush="true"/>



</section>

<style>
    .cell-width{
        width: 80%;
    }
</style>
<script type="application/javascript">
$('#search').bind('keypress', function(event) {   //回车事件绑定 
if (event.keyCode == "13") {  //js监测到为为回车事件时 触发
var form = document.getElementById('searchForm');
form.submit();
}
});
$("#searchBtn").click(function () {
var form = document.getElementById('searchForm');
form.submit();
})

</script>
</body>
</html>