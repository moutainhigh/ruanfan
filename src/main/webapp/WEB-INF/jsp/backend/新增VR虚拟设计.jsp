<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>编辑VR虚拟设计</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理VR虚拟设计</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增VR虚拟设计</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="bannerForm" method="post" enctype="multipart/form-data"
                              action="/backend/vrcustom/save" class="form-horizontal" role="form">
                            <input type="hidden" id="id" name="id" value="${vrcustom.id}">
                            <input type="hidden" id="tempCover" value="${vrcustom.cover}"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">封面:</label>
                                <div class="col-sm-3">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;"
                                           onchange="vrcustomList.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="vrcustomList.fn.AddImg()">
                                        <img id="mainPicture" src="${vrcustom.cover}" style="height: 200px; width: 200px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">链接地址:</label>

                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="url" name="url" maxlength="200" data-rule="required" value="${vrcustom.url}" placeholder="请输入链接地址"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="vrcustomList.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="vrcustomList.fn.goBack()"> 返回</button>
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

<script type="text/javascript">
    var vrcustomList = {
        v: {
            id: "vrcustomList",
            list: [],
            dTable: null,
            mainImageStatus: 0
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                if ($("#id").val() != "") {
                    $("#showH").text("——编辑VR虚拟设计");
                }

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                // 加载数据
                vrcustomList.fn.loadData();
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    vrcustomList.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }
            },
            changeStatus: function () {
                vrcustomList.v.mainImageStatus = 1;
            },
            AddImg: function () {
                // a标签绑定onclick事件
                $('#mainImage').click();
            },
            subInfo: function () {
                var flag = true;
                var urls = $('#url').val();

                if (vrcustomList.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传VR虚拟设计图", "error");
                    flag = false;
                    return;
                }

                if (null == urls || urls == '') {
                    $sixmac.notify("请输入链接地址", "error");
                    flag = false;
                    return;
                }

                // 所有的验证通过后，执行新增操作
                if (flag) {
                    $("#bannerForm").ajaxSubmit({
                        url: _basePath + "backend/vrcustom/save",
                        dataType: "json",
                        data: {
                            urls: urls
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "backend/vrcustom/index";
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                window.location.href = "backend/vrcustom/index";
            }
        }
    }

    $(document).ready(function () {
        vrcustomList.fn.init();
    });

</script>

</html>