<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>楼盘信息</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理地产</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增楼盘</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="propertyInfoForm" method="post" enctype="multipart/form-data" action="backend/propertys/addChildInfo" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${propertyInfo.id}">
                            <input type="hidden" id="tempCover" value="${propertyInfo.cover}"/>
                            <input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
                            <span id="tempAddImages" style="display: none"></span>
                            <span id="tempDelImages" style="display: none"></span>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">楼盘名称:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="20" data-rule="required" value="${propertyInfo.name}" placeholder="请输入楼盘名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">楼盘图片:</label>
                                <div class="col-sm-3">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;" onchange="propertyInfo.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="propertyInfo.fn.AddImg()">
                                        <img id="mainPicture" src="${propertyInfo.cover}" style="height: 320px; width: 320px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">楼盘客服头像:</label>
                                <div class="col-sm-3">
                                    <input type="file" name="mainImage5" id="mainImage5" style="display:none;" onchange="propertyInfo.fn.changeStatus5()"/>
                                    <a href="javascript:void(0);" onclick="propertyInfo.fn.AddImg5()">
                                        <img id="mainPicture5" src="${propertyInfo.serverHead}" style="height: 160px; width: 160px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">楼盘客服QQ:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="serverQQ" name="serverQQ" maxlength="20" data-rule="required" value="${propertyInfo.serverQQ}" placeholder="请输入楼盘客服QQ"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">楼盘地址:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="address" name="address" maxlength="200" data-rule="required" value="${propertyInfo.address}" placeholder="请输入楼盘地址"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">楼盘标签:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="labels" name="labels" maxlength="500" value="${propertyInfo.labels}" placeholder="使用空格隔开多个标签"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">楼盘简介:</label>
                                <div class="col-sm-6">
                                    <textarea cols="40" rows="8" class="form-control" style="resize: none" id="description" name="description" maxlength="2000" placeholder="楼盘简介，最多2000字">${propertyInfo.description}</textarea>
                                </div>
                            </div>

                            <div class="form-group" id="lastBaseDiv" style="display: none">
                                <label class="col-sm-2 control-label">户型:</label>
                                <div class="col-sm-10"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"></label>
                                <div class="col-sm-10">
                                    <button type="button" class="btn btn-success" onclick="propertyInfo.fn.insertBaseImage()">增加户型</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="propertyInfo.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="propertyInfo.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <form id="tempImageForm1" method="post" action="common/addTempImage" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="file" name="tempImage" id="tempImage1" data-rule="required" style="display:none;" onchange="propertyInfo.fn.saveTempImage1()"/>
                        </form>

                        <form id="tempImageForm2" method="post" action="common/addTempImage" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="file" name="tempImage" id="tempImage2" data-rule="required" style="display:none;" onchange="propertyInfo.fn.saveTempImage2()"/>
                        </form>

                        <div class="form-group" id="tempDiv" style="display: none;">
                            <label class="col-sm-2 control-label">户型:</label>
                            <div class="col-sm-3">
                                <img class="imgs" name="hxImage" src="static/images/add.jpg" style="height: 200px;width: 200px; z-index: 1;" onclick="propertyInfo.fn.AddImg1()"/>
                                <img class="imgs" name="kfImage" src="static/images/add.jpg" style="height: 100px;width: 100px; z-index: 1;" onclick="propertyInfo.fn.AddImg2()"/>
                                <input name="imageIdTemp" type="hidden"/>
                                <input type="text" class="form-control" name="urlInfo" maxlength="200" style="width: 280px;margin-top: 5px;" placeholder="请输入展示链接"/>
                                <input type="text" class="form-control" name="qq" maxlength="20" style="width: 280px;margin-top: 5px;margin-bottom: 5px;" placeholder="客服QQ"/>
                                <button type="button" class="btn btn-primary" onclick="propertyInfo.fn.clearDiv(this)">删除</button>
                            </div>
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
    var propertyInfo = {
        v: {
            id: "propertyInfo",
            list: [],
            dTable: null,
            mainImageStatus: 0,
            mainImageStatus5: 0,
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
                    $("#showH").text("——编辑楼盘");
                }

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                $("#mainImage5").uploadPreview({
                    Img: "mainPicture5",
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

                var mainImagePath5 = $('#mainPicture5').attr('src');
                if (null != mainImagePath5 && mainImagePath5 != '') {
                    propertyInfo.v.mainImageStatus5 = 1;
                } else {
                    $('#mainPicture5').attr('src', 'static/images/add.jpg');
                }

                // 加载灵感图集图片数组
                propertyInfo.fn.getSerImages();
            },
            changeStatus: function () {
                propertyInfo.v.mainImageStatus = 1;
            },
            changeStatus5: function () {
                propertyInfo.v.mainImageStatus5 = 1;
            },
            clearDiv: function (self) {
                $(self).parent().parent().remove();

                propertyInfo.v.imageSize = propertyInfo.v.imageSize - 1;
            },
            getSerImages: function () {
                var imgList = ${imageList };

                // 计算当前户型数量
                propertyInfo.v.imageSize = imgList.length;

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
            AddImg5: function () {
                // a标签绑定onclick事件
                $('#mainImage5').click();
            },
            checkData: function () {
                var flag = true;
                var name = $('#name').val();
                var address = $('#address').val();
                var serverQQ = $('#serverQQ').val();
                var description = $('#description').val();

                if (null == name || name == '') {
                    $sixmac.notify("请输入楼盘名称", "error");
                    flag = false;
                    return;
                }

                if (propertyInfo.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传楼盘图片", "error");
                    flag = false;
                    return;
                }

                if (propertyInfo.v.mainImageStatus5 == 0) {
                    $sixmac.notify("请上传楼盘客服头像", "error");
                    flag = false;
                    return;
                }

                if (null == serverQQ || serverQQ == '') {
                    $sixmac.notify("请输入楼盘客服QQ", "error");
                    flag = false;
                    return;
                }

                if (null == address || address == '') {
                    $sixmac.notify("请输入楼盘地址", "error");
                    flag = false;
                    return;
                }

                if (null == description || description == '') {
                    $sixmac.notify("请输入楼盘简介", "error");
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
                    $sixmac.notify("客服头像不能为空", "error");
                    return;
                }

                $("input[name='urlInfo']").each(function (index, item) {
                    if ($(this).parent().parent().prop('id') != 'tempDiv') {
                        if (null == $(this).val() || $(this).val() == '') {
                            flag = false;
                            return;
                        }
                    }
                });

                if (!flag) {
                    $sixmac.notify("展示链接不能为空", "error");
                    return;
                }

                $("input[name='qq']").each(function (index, item) {
                    if ($(this).parent().parent().prop('id') != 'tempDiv') {
                        if (null == $(this).val() || $(this).val() == '') {
                            flag = false;
                            return;
                        }
                    }
                });

                if (!flag) {
                    $sixmac.notify("客服QQ不能为空", "error");
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

                $("input[name='urlInfo']").each(function (index, item) {
                    if ($(this).parent().parent().prop('id') != 'tempDiv') {
                        propertyInfo.v.urls += $(this).val().trim() + ',';
                    }
                });

                $("input[name='qq']").each(function (index, item) {
                    if ($(this).parent().parent().prop('id') != 'tempDiv') {
                        propertyInfo.v.qqs += $(this).val().trim() + ',';
                    }
                });
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (propertyInfo.fn.checkData()) {
                    propertyInfo.fn.getData();

                    $("#propertyInfoForm").ajaxSubmit({
                        url: _basePath + "backend/propertys/addChildInfo",
                        dataType: "json",
                        data: {
                            "hxImages": propertyInfo.v.hxImages,
                            "kfImages": propertyInfo.v.kfImages,
                            "urls": propertyInfo.v.urls,
                            "qqs": propertyInfo.v.qqs
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "backend/propertys/index";
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                var parentId = $('#parentId').val();
                window.location.href = 'backend/propertys/childInfo?id=' + parentId;
            }
        }
    }

    $(document).ready(function () {
        propertyInfo.fn.init();
    });

</script>

</html>