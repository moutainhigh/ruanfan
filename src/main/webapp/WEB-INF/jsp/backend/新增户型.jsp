<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>编辑户型</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理在线设计定制</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增户型</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="customInfoForm" method="post" enctype="multipart/form-data" action="backend/custom/addChildInfo" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${customInfo.id}">
                            <input type="hidden" id="parentId" name="parentId" value="${parentId}"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">户型名称:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="20" data-rule="required" value="${customInfo.name}" placeholder="请输入户型名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">户型图片:</label>
                                <div class="col-sm-3">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;" onchange="customInfo.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="customInfo.fn.AddImg()">
                                        <img id="mainPicture" src="${customInfo.path}" style="height: 320px; width: 320px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">套餐:</label>
                                <div class="col-sm-4" id="parentDiv">
                                    <div id="lastBaseDiv" style="display: none;"></div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"></label>
                                <div class="col-sm-4">
                                    <button type="button" class="btn btn-success" onclick="customInfo.fn.insertBaseDiv()">添加套餐</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="customInfo.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="customInfo.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!-- /.panel-body -->

                    <form id="tempImageForm1" method="post" action="common/addTempImage" enctype="multipart/form-data" class="form-horizontal" role="form">
                        <input type="file" name="tempImage" id="tempImage1" data-rule="required" style="display:none;" onchange="customInfo.fn.saveTempImage1()"/>
                    </form>

                    <div id="tempDiv" style="display: none;margin-bottom: 10px;border: 1px dashed black;padding-top: 5px;padding-left: 5px;">
                        <select class="form-control" name="areaList" style="width: 150px"></select>
                        <button type="button" style="margin-left: 45%" class="btn btn-danger" onclick="customInfo.fn.removeDiv(this)">删除</button>
                        <img id="imgTemp" src="static/images/add.jpg" style="height: 200px; width: 200px; display: block; margin-bottom: 5px;margin-top: 10px; cursor: hand;" border="1" onclick="customInfo.fn.AddImg1(this)"/>
                        <input name="imageIdTemp" type="hidden"/>
                        <a href="javascript:void(0)" target="_blank" class="btn btn-primary btn-sm" role="button" style="color: white; display: none;margin-bottom: 5px;">添加锚点</a>
                        <input name="nameTemp" maxlength="4" class="form-control" placeholder="套餐名称，最多四个字" style="width: 220px;margin-bottom: 5px;"/>
                    </div>

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
    var customInfo = {
        v: {
            id: "customInfo",
            list: [],
            dTable: null,
            mainImageStatus: 0,
            packageSize: 0,
            tempImageId: 0
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                if ($("#id").val() != "") {
                    $("#showH").text("——编辑户型");
                }

                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                // 加载数据
                customInfo.fn.loadData();
            },
            changeStatus: function () {
                customInfo.v.mainImageStatus = 1;
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    customInfo.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }

                // 加载套餐数组
                customInfo.fn.getSerImages();
            },
            getSelectList: function (self, id) {
                // 加载区域列表
                $sixmac.ajax("common/areaList", null, function (result) {
                    if (null != result) {
                        // 获取返回的区域列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择区域</option>";
                        jQuery.each(result, function (i, item) {
                            if (id != 0 && id == item.id) {
                                content += "<option value='" + item.id + "' selected>" + item.name + "</option>";
                            } else {
                                content += "<option value='" + item.id + "'>" + item.name + "</option>";
                            }
                        });
                        $(self).append(content);
                    } else {
                        $sixmac.notify("获取区域信息失败", "error");
                    }
                });
            },
            getSerImages: function () {
                var packageList = ${packageList };

                // 计算当前户型数量
                customInfo.v.packageSize = packageList.length;

                $.each(packageList, function (i, item) {
                    if (null != item) {
                        var tempDiv = $("#tempDiv").clone();
                        tempDiv.prop('id', 'div' + i);
                        tempDiv.css("display", "block");

                        // 获取套餐区域下拉框信息
                        customInfo.fn.getSelectList(tempDiv.children(":first"), item.areaId);

                        tempDiv.children(":first").next().next().prop("id", 'img' + i);
                        tempDiv.children(":first").next().next().prop("src", item.path);
                        tempDiv.children(":first").next().next().next().val(item.id);
                        tempDiv.children(":first").next().next().next().next().css('display', '');
                        tempDiv.children(":first").next().next().next().next().prop("href", "backend/custom/addTempLabel?id=" + item.id);
                        tempDiv.children(":first").next().next().next().next().next().val(item.name);

                        tempDiv.insertBefore("#lastBaseDiv");
                    } else {
                        $('#laseBaseDiv').html('暂无');
                    }
                });
            },
            insertBaseDiv: function () {
                customInfo.v.packageSize = customInfo.v.packageSize + 1;

                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', 'div' + customInfo.v.packageSize);
                tempDiv.css("display", "block");
                tempDiv.children(":first").next().next().prop("id", 'img' + customInfo.v.packageSize);

                // 获取套餐区域下拉框信息
                customInfo.fn.getSelectList(tempDiv.children(":first"), 0);

                tempDiv.insertBefore("#lastBaseDiv");
            },
            removeDiv: function (self) {
                $(self).parent().remove();

                customInfo.v.packageSize = customInfo.v.packageSize - 1;
            },
            AddImg: function () {
                // a标签绑定onclick事件
                $('#mainImage').click();
            },
            AddImg1: function (self) {
                customInfo.v.tempImageId = $(self).prop('id');

                // a标签绑定onclick事件
                $('#tempImage1').click();
            },
            saveTempImage1: function () {
                $("#tempImageForm1").ajaxSubmit({
                    dataType: "json",
                    success: function (data) {
                        if (null != data.path && data.path != '') {
                            $('#' + customInfo.v.tempImageId).prop('src', data.path);
                            $('#' + customInfo.v.tempImageId).next().prop("value", data.id);
                            $('#' + customInfo.v.tempImageId).next().next().css("display", '');
                            $('#' + customInfo.v.tempImageId).next().next().prop("href", "backend/custom/addTempLabel?id=" + data.id);
                        } else {
                            $sixmac.notify("图片格式不正确", "error");
                        }
                    }
                });
            },
            checkData: function () {
                var flag = true;
                var name = $('#name').val();

                if (null == name || name == '') {
                    $sixmac.notify("请输入户型名称", "error");
                    flag = false;
                    return;
                }

                if (customInfo.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传户型图", "error");
                    flag = false;
                    return;
                }

                if (customInfo.v.packageSize == 0) {
                    $sixmac.notify("请增加至少一个套餐", "error");
                    flag = false;
                    return;
                }

                // 检测套餐数据是否合格
                var selectVal = '';
                var imgVal = '';
                var nameVal = '';
                for (var i = 0; i < customInfo.v.packageSize; i++) {
                    selectVal = $('#div' + (Number(i) + Number(1))).children(':first').val();
                    imgVal = $('#div' + (Number(i) + Number(1))).children(':first').next().next().attr('src');
                    nameVal = $('#div' + (Number(i) + Number(1))).children(':first').next().next().next().next().next().val();

                    if (selectVal == '') {
                        $sixmac.notify("套餐区域不能为空", "error");
                        flag = false;
                        return;
                    }

                    if (imgVal == '' || imgVal == 'static/images/add.jpg') {
                        $sixmac.notify("套餐图片不能为空", "error");
                        flag = false;
                        return;
                    }

                    if (nameVal == '') {
                        $sixmac.notify("套餐名称不能为空", "error");
                        flag = false;
                        return;
                    }
                }

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (customInfo.fn.checkData()) {
                    $("#customInfoForm").ajaxSubmit({
                        url: _basePath + "backend/custom/addChildInfo",
                        dataType: "json",
                        success: function (result) {
                            if (result > 0) {
                                customInfo.fn.goBack();
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                window.location.href = "backend/custom/childInfo?id=" + $('#parentId').val();
            }
        }
    }

    $(document).ready(function () {
        customInfo.fn.init();
    });

</script>

</html>