<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>预约详情</title>
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
                <h1 class="page-header">预约列表</h1>
                <h4 style="margin-left: 10px;">——查看预约详情</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form method="post" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="hidden" id="tempCoverId" value="${afflatus.coverId}">
                            <input type="hidden" id="afflatusType" value="${afflatus.type}">

                            <div class="form-group">
                                <label class="col-sm-2 control-label">姓名:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${reserve.name}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">预约对象:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${reserve.designer.nickName}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">预约时间:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${reserve.reseTime}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">电话:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${reserve.mobile}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">邮箱:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${reserve.email}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">喜爱风格:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${reserve.style.name}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">地址:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${reserve.address}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">备注:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${reserve.remark}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">预约地点:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${reserve.reseAddress}
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="reserve.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <div id="tempDiv" style="display:none;float: left; height: 610px;width: 810px;margin-right:6px; z-index: 0;margin-bottom: 50px;">
                            <img class="imgs" alt="" src="" style="height: 600px;width: 800px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
                            <span class="settingCover" style="margin-left: 47%; display: none; line-height: 50px; font-size: large;">↑封面</span>
                            <a href="javascript:void(0)" target="_blank" style="cursor: hand; float: right;margin-right: 13px;line-height: 50px; font-size: large;">查看标签</a>
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
    var reserve = {
        v: {
            id: "reserve",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                // 查询
                $("#c_search").click(function () {
                    reserve.v.dTable.ajax.reload();
                });
            },
            goBack: function () {
                window.location.href = "backend/reserve/index";
            }
        }
    }

    $(document).ready(function () {
        reserve.fn.init();
    });

</script>

</html>