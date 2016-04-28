<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>日志详情</title>
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
                <h1 class="page-header">管理日志</h1>
                <h4 style="margin-left: 10px;">——查看日志详情</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form method="post" enctype="multipart/form-data" class="form-horizontal" role="form">

                            <div class="form-group">
                                <label class="col-sm-2 control-label">发布者:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${journal.user.nickName}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">日志内容:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${journal.content}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">发布时间:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${journal.createTime}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">转发数:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${journal.forwardNum}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">点赞数:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${journal.gamNum}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">评论数:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${journal.commentNum}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">图片:</label>
                                <div class="col-sm-10" style="padding-top: 8px;">
                                    <div style="float: left;" id="lastImageDiv"></div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="journal.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <div id="tempDiv" style="display:none;float: left; height: 610px;width: 810px;margin-right:6px; z-index: 0;margin-bottom: 50px;">
                            <img class="imgs" alt="" src="" style="height: 600px;width: 800px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
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
    var journal = {
        v: {
            id: "journal",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                journal.fn.getSerImages();

                // 查询
                $("#c_search").click(function () {
                    journal.v.dTable.ajax.reload();
                });
            },
            getSerImages: function () {
                var imgList = ${imageList };

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        journal.fn.insertImage(item.path, item.id);
                    } else {
                        $('#lastImageDiv').html('暂无');
                    }
                });

                // 选中封面图
                var coverId = $('#tempCoverId').val();
                $('.settingCover').each(function () {
                    if ($(this).prev().val() == coverId) {
                        $(this).css('display', '');
                    }
                });
            },
            insertImage: function (path, id) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.insertBefore("#lastImageDiv");
            },
            goBack: function () {
                window.location.href = "backend/journal/index";
            }
        }
    }

    $(document).ready(function () {
        journal.fn.init();
    });

</script>

</html>