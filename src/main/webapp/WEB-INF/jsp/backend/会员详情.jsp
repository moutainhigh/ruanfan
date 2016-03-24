<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>会员详情</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">会员列表</h1>
                <h4 style="margin-left: 10px;" id="showH">——会员详情</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form class="form-horizontal">

                            <div class="form-group">
                                <label class="col-sm-2 control-label">头像:</label>

                                <div class="col-sm-4">
                                    <img id="mainPicture" src="${users.headPath}" style="height: 200px; width: 200px; display: inline; margin-bottom: 5px;" border="1"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">账号:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${users.mobile}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">昵称:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${users.nickName}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">状态:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    <c:if test="${users.status == 0}">启用</c:if>
                                    <c:if test="${users.status == 1}">禁用</c:if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">注册时间:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${users.createTime}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">粉丝数:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${fans}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">关注数:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${attentions}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">会员积分:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${users.score}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">会员类型:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    <c:if test="${users.type == 1}">马甲会员</c:if>
                                    <c:if test="${users.type == 2}">普通会员</c:if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">地理位置:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${users.city.province.name}&nbsp;&nbsp;${users.city.name}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">小区名:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${users.comName}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">建筑面积:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${users.comArea}
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <a href="backend/users/index" class="btn btn-primary" role="button">返回</a>
                                </div>
                            </div>
                        </form>
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

</html>