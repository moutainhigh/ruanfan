<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>控制面板</title>
    <%@ include file="inc/css.jsp" %>
</head>

<style>
    .textAling {
        text-align: center;
    }
</style>

<body>

<div id="posts" class="wrapper">
    <%@ include file="inc/nav.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header" style="text-align: center;">${sessionScope.menu_sysUser.account}，欢迎进入软范后台管理系统</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <c:if test="${sessionScope.menu_sysUser.role.id == 1}">
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <a href="backend/users/index" class="btn btn-outline btn-primary btn-lg" role="button">新增会员（${addUserNum}）</a>
                            <a href="backend/orders/index1" class="btn btn-outline btn-primary btn-lg" role="button">新增订单（${addOrdersNum}）</a>
                            <a href="backend/ordersinfo/index" class="btn btn-outline btn-primary btn-lg" role="button">新增评价（${addCommentsNum}）</a>
                            <a href="backend/reserve/index" class="btn btn-outline btn-primary btn-lg" role="button">新增预约（${addReserveNum}）</a>
                            <a href="backend/product/index" class="btn btn-outline btn-primary btn-lg" role="button">待审核商品（${checkNum}）</a>
                            <a href="backend/afflatus/index" class="btn btn-outline btn-primary btn-lg" role="button">待审核灵感图（${afflausesNum}）</a>
                            <a href="backend/journal/index" class="btn btn-outline btn-primary btn-lg" role="button">新增日志（${journalsNum}）</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="panel-body">
                <h3>订单统计信息:</h3>

                <div class="col-sm-12">
                    <table class="table table-striped table-bordered table-hover">
                        <colgroup>
                            <col class="gradeA odd"/>
                            <col class="gradeA even"/>
                            <col class="gradeA odd"/>
                            <col class="gradeA even"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <td width="25%;" class="textAling">待发货订单</td>
                            <td width="25%;" class="textAling">${num1}</td>
                            <td width="25%;" class="textAling">待支付订单</td>
                            <td width="25%;" class="textAling">${num2}</td>
                        </tr>
                        <tr>
                            <td width="25%;" class="textAling">待确认订单</td>
                            <td width="25%;" class="textAling">${num3}</td>
                            <td width="25%;" class="textAling">已完成订单</td>
                            <td width="25%;" class="textAling">${num4}</td>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>

            <div class="panel-body">
                <h3>商品统计信息:</h3>

                <div class="col-sm-12">
                    <table class="table table-striped table-bordered table-hover">
                        <colgroup>
                            <col class="gradeA odd"/>
                            <col class="gradeA even"/>
                            <col class="gradeA odd"/>
                            <col class="gradeA even"/>
                        </colgroup>
                        <thead>
                        <tr width="100px">
                            <td width="25%;" class="textAling">商品总数</td>
                            <td width="25%;" class="textAling">${productTotalNum}</td>
                            <td width="25%;" class="textAling">商品套餐</td>
                            <td width="25%;" class="textAling">${packagesNum}</td>
                        </tr>
                        <tr>
                            <td width="25%;" class="textAling">待审核商品</td>
                            <td width="25%;" class="textAling">${checkNum}</td>
                            <td width="25%;" class="textAling">秒杀商品</td>
                            <td width="25%;" class="textAling">${spikesNum}</td>
                        </tr>
                        <tr width="100px">
                            <td width="25%;" class="textAling">上架</td>
                            <td width="25%;" class="textAling">${addNum}</td>
                            <td width="25%;" class="textAling">昨日新增商品</td>
                            <td width="25%;" class="textAling">${productNewNum}</td>
                        </tr>
                        <tr>
                            <td width="25%;" class="textAling">下架</td>
                            <td width="25%;" class="textAling">${downNum}</td>
                            <td width="25%;" class="textAling"></td>
                            <td width="25%;" class="textAling"></td>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>

            <div class="panel-body">
                <h3>统计访问:</h3>

                <div class="col-sm-12">
                    <table class="table table-striped table-bordered table-hover">
                        <colgroup>
                            <col class="gradeA odd"/>
                            <col class="gradeA even"/>
                            <col class="gradeA odd"/>
                            <col class="gradeA even"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <td width="25%;" class="textAling">今日访问</td>
                            <td width="25%;" class="textAling">${visitCount}</td>
                            <td width="25%;" class="textAling">在线人数</td>
                            <td width="25%;" class="textAling">${onlineCount}</td>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </c:if>

    </div>

</div>

<%@ include file="inc/footer.jsp" %>
</body>
</html>