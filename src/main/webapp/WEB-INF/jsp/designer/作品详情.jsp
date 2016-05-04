<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>作品列表</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">作品列表</h1>
                <h4 style="margin-left: 10px;">——查看作品详情</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form method="post" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="hidden" id="tempCoverId" value="${works.coverId}">

                            <div class="form-group" id="nameDiv">
                                <label class="col-sm-2 control-label">作品名称:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${works.name}
                                </div>
                            </div>

                            <div class="form-group" id="labelsDiv">
                                <label class="col-sm-2 control-label">标签:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${works.labels}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">描述:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${works.description}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">图片:</label>
                                <div class="col-sm-10" style="padding-top: 8px;">
                                    <div style="float: left;display: none;" id="lastImageDiv"></div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="works.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <div id="tempDiv" style="display:none;float: left; height: 610px;width: 810px;margin-right:6px; z-index: 0;margin-bottom: 50px;">
                            <img class="imgs" alt="" src="" style="height: 600px;width: 800px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
                            <span class="settingCover" style="margin-left: 47%; display: none; line-height: 50px; font-size: large;">↑封面</span>
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
    var works = {
        v: {
            id: "works",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                // 加载灵感图集图片数组
                works.fn.getSerImages();


            },
            getSerImages: function () {
                var imgList = ${imageList };

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        works.fn.insertImage(item.path, item.id);
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
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.insertBefore("#lastImageDiv");
            },
            goBack: function () {
                window.location.href = "designer/works/index";
            }
        }
    }

    $(document).ready(function () {
        works.fn.init();
    });

</script>

</html>