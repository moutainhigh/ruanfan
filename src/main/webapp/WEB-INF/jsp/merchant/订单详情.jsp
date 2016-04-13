<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>商品管理</title>
    <%@ include file="inc/css.jsp" %>
    <style type="text/css">
        /*模块拖拽*/
        .drag {
            position: absolute;
            width: 150px;
            height: 30px;
            border: 1px solid #ddd;
            background: #FBF2BD;
            text-align: center;
            padding: 5px;
            opacity: 0.7;
            border-radius: 5px;
        }
    </style>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">订单列表</h1>
                <h4 style="margin-left: 10px;">——查看订单详情</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form method="post" enctype="multipart/form-data" class="form-horizontal" role="form">

                            <div class="form-group">
                                <label class="col-sm-2 control-label">订单号:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.orderNum}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">状态:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    <c:if test="${orders.status == 0}">待付款</c:if>
                                    <c:if test="${orders.status == 1}">待发货</c:if>
                                    <c:if test="${orders.status == 2}">待确认</c:if>
                                    <c:if test="${orders.status == 3}">待评价</c:if>
                                    <c:if test="${orders.status == 4}">已完成</c:if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">订单金额:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.price}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">实付金额:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.realPrice}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">创建时间:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.createTime}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">支付单号:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.orderNum}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">用户:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.user.mobile}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">商户:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.merchant.nickName}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">成交时间:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.payTime}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">商品清单:</label>
                                <div class="col-sm-5" style="padding-top: 6.5px;">
                                    <table style="text-align: left;" width="100%">
                                        <c:if test="${orderInfoList.size() == 0}">暂无</c:if>
                                        <c:forEach var="n" items="${orderInfoList}">
                                            <tr>
                                                <td rowspan="2"><img src="${n.productPath}" width="70px;"
                                                                     height="70px;"></td>
                                                <td>${n.productName}</td>
                                                <td></td>
                                                <td></td>
                                                <td style="text-align: right">￥${n.price}</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <span style="float: left;">颜色：</span>
                                                    <div style="width:20px;height:20px; float: left;background-color: #${n.colors}"></div>
                                                    <div style="clear: both;"></div>
                                                </td>
                                                <td>尺寸：${n.sizes}</td>
                                                <td>材质：${n.materials}</td>
                                                <td style="text-align: right">*${n.count}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">收货人姓名:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.consignee}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">收货人电话:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.mobile}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">收货人地址:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.address}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">留言:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${orders.demo}
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="orders.fn.goBack()">返回
                                    </button>
                                </div>
                            </div>
                        </form>

                        <div id="tempDiv"
                             style="display:none;float: left; height: 610px;width: 810px;margin-right:6px; z-index: 0;margin-bottom: 50px;">
                            <img class="imgs" alt="" src="" style="height: 600px;width: 800px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
                            <span class="settingCover"
                                  style="margin-left: 47%; display: none; line-height: 50px; font-size: large;">↑封面</span>
                            <a href="javascript:void(0)" target="_blank"
                               style="cursor: hand; float: right;margin-right: 13px;line-height: 50px; font-size: large;">查看标签</a>
                        </div>

                        <!-- 隐藏区域--标签 -->
                        <div id="temp" class="drag" style="display: none">
                            <span></span>
                        </div>
                    </div>
                    <!-- /.panel-body -->

                </div>
                <!-- /.panel -->
            </div>
        </div>


    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp" %>

</body>

<script type="text/javascript">
    var orders = {
        v: {
            id: "orders",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                // 查询
                $("#c_search").click(function () {
                    orders.v.dTable.ajax.reload();
                });
            },
            goBack: function () {
                window.location.href = "merchant/orders/index";
            }
        }
    }

    $(document).ready(function () {
        orders.fn.init();
    });

</script>

</html>