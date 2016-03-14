<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>灵感图集</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">灵感图集</h1>
                <h4 style="margin-left: 10px;">——查看灵感图集</h4>
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
                                <label class="col-sm-2 control-label">类型:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    <c:if test="${afflatus.type == 1}">单图</c:if>
                                    <c:if test="${afflatus.type == 2}">套图</c:if>
                                </div>
                            </div>

                            <div class="form-group" id="nameDiv" style="display: none;">
                                <label class="col-sm-2 control-label">灵感图名称:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${afflatus.name}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">发布人:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${afflatus.designer.nickName}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">风格:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${afflatus.style.name}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">区域:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${afflatus.area.name}
                                </div>
                            </div>

                            <div class="form-group" id="labelsDiv" style="display: none">
                                <label class="col-sm-2 control-label">标签:</label>
                                <div class="col-sm-3" style="padding-top: 6.5px;">
                                    ${afflatus.labels}
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
                                    <button type="button" class="btn btn-primary" onclick="afflatus.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;">
                            <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
                            <span class="settingCover" style="margin-left: 42%; display: none;">封面</span>
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
    var afflatus = {
        v: {
            id: "afflatus",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                var type = $('#afflatusType').val();
                if (type == 2) {
                    $('#nameDiv').css('display', '');
                    $('#labelsDiv').css('display', '');
                }

                // 加载灵感图集图片数组
                afflatus.fn.getSerImages();
            },
            getSerImages: function () {
                var imgList = ${imageList };

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        afflatus.fn.insertImage(item.path, item.id);
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
                window.location.href = "afflatus/index";
            }
        }
    }

    $(document).ready(function () {
        afflatus.fn.init();
    });

</script>

</html>