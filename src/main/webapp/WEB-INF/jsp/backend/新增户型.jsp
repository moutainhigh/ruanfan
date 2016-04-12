<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>站长工具</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">在线设计定制</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增户型</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="custominfoForm" method="post" enctype="multipart/form-data" action="backend/custom/addChildInfo" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${custominfo.id}">
                            <input type="hidden" id="customId" value="${custominfo.path}"/>

                            <span id="tempAddImages" style="display: none"></span>
                            <span id="tempDelImages" style="display: none"></span>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"></label>
                                <div class="col-sm-10">
                                    <button type="button" class="btn btn-success" onclick="custominfo.fn.insertBaseImage()">增加户型</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">户型名称:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="20" data-rule="required" value="${custominfo.name}" placeholder="请输入户型名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">户型图片:</label>
                                <div class="col-sm-3">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;" onchange="custominfo.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="custominfo.fn.AddImg()">
                                        <img id="mainPicture" src="${custominfo.path}" style="height: 320px; width: 320px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">套餐:</label>
                                <div class="col-sm-3">
                                    <select id="statusList" style="width: 120px;" class="form-control">
                                        <option value="">请选择套餐</option>
                                        <option value="1">玄关</option>
                                        <option value="2">厨房</option>
                                        <option value="3">书房</option>
                                        <option value="4">客厅</option>
                                        <option value="5">走廊</option>
                                    </select>
                                    <img class="imgs" name="hxImage" src="static/images/add.jpg" style="height: 200px;width: 200px; z-index: 2;" onclick="custominfo.fn.AddImg1()"/>
                                    <input name="imageIdTemp" type="hidden"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="custominfo.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="custominfo.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <form id="tempImageForm1" method="post" action="common/addTempImage" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="file" name="tempImage" id="tempImage1" data-rule="required" style="display:none;" onchange="custominfo.fn.saveTempImage1()"/>
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
    var custominfo = {
        v: {
            id: "custominfo",
            list: [],
            dTable: null,
            mainImageStatus: 0,
            imageSize: 0,
            hxImages: ''
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                if ($("#id").val() != "") {
                    $("#showH").text("——编辑户型");
                }

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                // 加载数据
                custominfo.fn.loadData();
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    custominfo.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }

                // 加载灵感图集图片数组
                // custominfo.fn.getSerImages();

                //加载区域选择下拉框
                areaneme = $('#statusList option:selected').val();
            },
            changeStatus: function () {
                custominfo.v.mainImageStatus = 1;
            },
            clearDiv: function (self) {
                $(self).parent().parent().remove();

                custominfo.v.imageSize = custominfo.v.imageSize - 1;
            },
            saveTempImage1: function () {
                $("#tempImageForm1").ajaxSubmit({
                    dataType: "json",
                    success: function (data) {
                        if (null != data.path && data.path != '') {
                            var lengthInfo = $("img").length;
                            $("img").each(function (index, item) {
                                if (index == lengthInfo - 4) {
                                    $(this).prop('src', data.path);
                                }
                            });
                        } else {
                            $sixmac.notify("图片格式不正确", "error");
                        }
                    }
                });
            },
            AddImg1: function () {
                // a标签绑定onclick事件
                $('#tempImage1').click();
            },
            insertBaseImage: function () {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.insertBefore("#lastBaseDiv");

                custominfo.v.imageSize = custominfo.v.imageSize + 1;
            },
            insertImage: function (path, id, pathK, url, qq) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").next().children(":first").prop("src", name);
                tempDiv.children(":first").next().children(":first").next().prop("src", path);
                tempDiv.children(":first").next().children(":first").next().next().prop("value", id);
                tempDiv.insertBefore("#lastBaseDiv");
            },
            AddImg: function () {
                // a标签绑定onclick事件
                $('#mainImage').click();
            },
            checkData: function () {
                var flag = true;
                var name = $('#name').val();

                if (null == name || name == '') {
                    $sixmac.notify("请输入户型名称", "error");
                    flag = false;
                    return;
                }

                if (custominfo.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传户型图片", "error");
                    flag = false;
                    return;
                }

                // 检测户型数据是否合格
                $("img[name='hxImage']").each(function (index, item) {
                    if ($(this).parent().parent().prop('id') != 'tempDiv') {
                        if ($(this).prop('src') == _basePath + 'static/images/add.jpg') {
                            flag = false;
                            return;
                        }
                    }
                });

                if (!flag) {
                    $sixmac.notify("套餐图片不能为空", "error");
                    return;
                }

                return flag;
            },
            getData: function () {
                $("img[name='hxImage']").each(function (index, item) {
                    if ($(this).parent().parent().prop('id') != 'tempDiv') {
                        custominfo.v.hxImages += $(this).prop('src') + ',';
                    }
                });

            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (custominfo.fn.checkData()) {
                    custominfo.fn.getData();

                    $("#custominfoForm").ajaxSubmit({
                        url: _basePath + "backend/custom/addChildInfo",
                        dataType: "json",
                        data: {
                            "hxImages": custominfo.v.hxImages,
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "backend/custom/index";
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                var customId = $('#customId').val();
                window.location.href = "backend/custom/index";
            }
        }
    }

    $(document).ready(function () {
        custominfo.fn.init();
    });

</script>

</html>