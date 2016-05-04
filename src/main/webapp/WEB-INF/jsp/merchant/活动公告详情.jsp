<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>活动公告详情</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">活动公告</h1>
                <h4 style="margin-left: 10px;">——查看活动公告详情</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">公告标题:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${message.title}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">公告描述:</label>
                                <div class="col-sm-8" style="padding-top: 6.5px;">
                                    ${message.des}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">创建时间:</label>
                                <div class="col-sm-4" style="padding-top: 6.5px;">
                                    ${message.createTime}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">修改时间:</label>
                                <div class="col-sm-4" style="padding-top: 6.5px;">
                                    ${message.updateTime}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">公告详情:</label>
                                <div class="col-sm-9" style="padding-top: 6.5px;border: 1px dashed #bbbbbb">
                                    ${message.description}
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-12" style="text-align: center">
                                    <a href="merchant/message/index" class="btn btn-primary" role="button">返回</a>
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