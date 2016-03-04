<%--
  Created by IntelliJ IDEA.
  User: wangbin
  Date: 2015/3/3
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
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

<body>

<div id="posts" class="wrapper">
    <%@ include file="inc/nav.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">控制面板</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-beer fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"></div>
                                <div></div>
                            </div>
                        </div>
                    </div>
                    <a href="admin/product/index">
                        <div class="panel-footer">
                            <span class="pull-left">商品管理</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-green">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-users fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"></div>
                                <div></div>
                            </div>
                        </div>
                    </div>
                    <a href="admin/member/index">
                        <div class="panel-footer">
                            <span class="pull-left">商家管理</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-yellow">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-ticket fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"></div>
                                <div></div>
                            </div>
                        </div>
                    </div>
                    <a href="admin/coupon/index">
                        <div class="panel-footer">
                            <span class="pull-left">发放优惠券</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-red">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-file-text fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"></div>
                                <div></div>
                            </div>
                        </div>
                    </div>
                    <a href="admin/order/index">
                        <div class="panel-footer">
                            <span class="pull-left">订单管理</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp" %>
</body>
<!-- 实例化编辑器 -->


</html>