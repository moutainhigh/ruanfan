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
                <h1 class="page-header">在线定制</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增户型</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="propertyInfoForm" method="post" enctype="multipart/form-data" action="backend/custom/addChildInfo" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${propertyInfo.id}">
                            <input type="hidden" id="tempCover" value="${propertyInfo.cover}"/>
                            <input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
                            <span id="tempAddImages" style="display: none"></span>
                            <span id="tempDelImages" style="display: none"></span>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">户型名称:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="20" data-rule="required" value="${propertyInfo.name}" placeholder="请输入楼盘名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">户型图片:</label>
                                <img class="imgs" name="hxImage" src="static/images/add.jpg" style="height: 200px;width: 200px; z-index: 1;" onclick="propertyInfo.fn.AddImg1()"/>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">套餐:</label>
                                <select id="statusList" style="width: 120px;" class="form-control">
                                    <option value="">请选择套餐</option>
                                    <option value="1">玄关</option>
                                    <option value="2">厨房</option>
                                    <option value="3">书房</option>
                                    <option value="4">客厅</option>
                                    <option value="5">走廊</option>
                                </select>
                                <img class="imgs" name="kfImage" src="static/images/add.jpg" style="height: 100px;width: 100px; z-index: 1;" onclick="propertyInfo.fn.AddImg2()"/>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"></label>
                                <div class="col-sm-10">
                                    <button type="button" class="btn btn-success" onclick="custom.fn.insertBaseImage()">增加户型</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="custom.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="custom.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <form id="tempImageForm1" method="post" action="common/addTempImage" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="file" name="tempImage" id="tempImage1" data-rule="required" style="display:none;" onchange="propertyInfo.fn.saveTempImage1()"/>
                        </form>

                        <form id="tempImageForm2" method="post" action="common/addTempImage" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="file" name="tempImage" id="tempImage2" data-rule="required" style="display:none;" onchange="propertyInfo.fn.saveTempImage2()"/>
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
    var custom = {
        v: {
            id: "custom",
            list: [],
            dTable: null,
            mainImageStatus: 0,
            imageSize: 0,
            hxImages: '',
            kfImages: '',
            urls: '',
            qqs: ''
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
                propertyInfo.fn.loadData();
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    propertyInfo.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }

                // 加载灵感图集图片数组
                custom.fn.getSerImages();
            },
            changeStatus: function () {
                custom.v.mainImageStatus = 1;
            },
            clearDiv: function (self) {
                $(self).parent().parent().remove();

                custom.v.imageSize = propertyInfo.v.imageSize - 1;
            },
            getSerImages: function () {
                var imgList = ${imageList };

                // 计算当前户型数量
                custom.v.imageSize = imgList.length;

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        propertyInfo.fn.insertImage(item.path, item.id, item.serverPath, item.url, item.qq);
                    } else {
                        $('#laseBaseDiv').html('暂无');
                    }
                });
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
            saveTempImage2: function () {
                $("#tempImageForm2").ajaxSubmit({
                    dataType: "json",
                    success: function (data) {
                        if (null != data.path && data.path != '') {
                            var lengthInfo = $("img").length;
                            $("img").each(function (index, item) {
                                if (index == lengthInfo - 3) {
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
            AddImg2: function () {
                // a标签绑定onclick事件
                $('#tempImage2').click();
            },
            insertBaseImage: function () {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.insertBefore("#lastBaseDiv");

                propertyInfo.v.imageSize = propertyInfo.v.imageSize + 1;
            },
            insertImage: function (path, id, pathK, url, qq) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").next().children(":first").prop("src", path);
                tempDiv.children(":first").next().children(":first").next().prop("src", pathK);
                tempDiv.children(":first").next().children(":first").next().next().prop("value", id);
                tempDiv.children(":first").next().children(":first").next().next().next().prop("value", url);
                tempDiv.children(":first").next().children(":first").next().next().next().next().prop("value", qq);
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

                if (propertyInfo.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传楼盘图片", "error");
                    flag = false;
                    return;
                }

                if (propertyInfo.v.imageSize == 0) {
                    $sixmac.notify("请增加至少一个户型", "error");
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
                    $sixmac.notify("户型图片不能为空", "error");
                    return;
                }

                $("img[name='kfImage']").each(function (index, item) {
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
                        propertyInfo.v.hxImages += $(this).prop('src') + ',';
                    }
                });

                $("img[name='kfImage']").each(function (index, item) {
                    if ($(this).parent().parent().prop('id') != 'tempDiv') {
                        propertyInfo.v.kfImages += $(this).prop('src') + ',';
                    }
                });

            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (custom.fn.checkData()) {
                    custom.fn.getData();

                    $("#propertyInfoForm").ajaxSubmit({
                        url: _basePath + "backend/custom/addChildInfo",
                        dataType: "json",
                        data: {
                            "hxImages": custom.v.hxImages,
                            "kfImages": custom.v.kfImages
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
                var parentId = $('#parentId').val();
                window.location.href = 'backend/custom/childInfo?id=' + parentId;
            }
        }
    }

    $(document).ready(function () {
        custom.fn.init();
    });

</script>

</html>