<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>个人资料</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">个人资料</h1>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="merchantForm" method="post" enctype="multipart/form-data" action="merchant/save" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${merchant.id}">
                            <input type="hidden" id="provinceId" value="${merchant.city.province.id}">
                            <input type="hidden" id="cityId" value="${merchant.city.id}">
                            <input type="hidden" id="tempTypeId" value="${merchant.type}"/>
                            <input type="hidden" id="tempStyleId" value="${merchant.style.id}"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">头像:</label>
                                <div class="col-sm-10" style="padding-top: 5.5px;">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;" onchange="merchant.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="merchant.fn.AddImg()">
                                        <img id="mainPicture" src="${merchant.head}" style="height: 200px; width: 200px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">封面:</label>
                                <div class="col-sm-10" style="padding-top: 5.5px;">
                                    <input type="file" name="mainImage3" id="mainImage3" style="display:none;" onchange="merchant.fn.changeStatus3()"/>
                                    <a href="javascript:void(0);" onclick="merchant.fn.AddImg3()">
                                        <img id="mainPicture3" src="${merchant.cover}" style="height: 200px; width: 200px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">资质证明:</label>
                                <div class="col-sm-4">
                                    <img src="${merchant.license}" style="height: 200px; width: 200px; display: inline; margin-bottom: 5px;" border="1"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">账号:</label>
                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${merchant.email}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">昵称:</label>
                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${merchant.nickName}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">商家类型:</label>
                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    <c:if test="${merchant.type == 1}">品牌商家</c:if>
                                    <c:if test="${merchant.type == 2}">独立商家</c:if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">链接:</label>
                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    ${merchant.url}
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">风格:</label>
                                <div class="col-sm-2">
                                    <select id="styleList" style="width: 150px;" class="form-control"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">地理位置:</label>
                                <div class="col-sm-2">
                                    <select id="provinceList" style="width: 150px;" class="form-control"></select>
                                </div>
                                <div class="col-sm-2">
                                    <select id="cityList" style="width: 200px;" class="form-control"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">介绍:</label>
                                <div class="col-sm-6">
                                    <textarea name="content" cols="40" rows="6" class="form-control" style="resize: none">${merchant.description}</textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <button type="button" class="btn btn-primary" onclick="merchant.fn.subInfo()">保存</button>
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
    var merchant = {
        v: {
            id: "merchant",
            list: [],
            dTable: null,
            mainImageStatus: 0,
            mainImageStatus3: 0
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                $("#mainImage3").uploadPreview({
                    Img: "mainPicture3",
                    Width: 200,
                    Height: 170
                });

                // 页面加载时，自动加载下拉框
                merchant.fn.getSelectList();

                merchant.fn.loadData();

                $('#provinceList').change(function () {
                    $('#cityId').val('');

                    var provinceId = $(this).val();
                    merchant.fn.loadCity(provinceId);
                });
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    merchant.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }

                var mainImagePath3 = $('#mainPicture3').attr('src');
                if (null != mainImagePath3 && mainImagePath3 != '') {
                    merchant.v.mainImageStatus3 = 1;
                } else {
                    $('#mainPicture3').attr('src', 'static/images/add.jpg');
                }

                // 选择商家类型
                var type = $('#tempTypeId').val();
                if (null != type && type != '') {
                    $('#typeList').val(type);
                }
            },
            changeStatus: function () {
                merchant.v.mainImageStatus = 1;
            },
            changeStatus3: function () {
                merchant.v.mainImageStatus3 = 1;
            },
            getSelectList: function () {
                $sixmac.ajax("common/styleList", null, function (result) {
                    if (null != result) {
                        // 获取返回的风格列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择风格</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#styleList').append(content);
                    } else {
                        $sixmac.notify("获取风格信息失败", "error");
                    }

                    var styleId = $('#tempStyleId').val();
                    if (null != styleId && styleId != '') {
                        $('#styleList').val(styleId);
                    }
                });

                var provinceId = $('#provinceId').val();

                $sixmac.ajax("common/provinceList", null, function (result) {
                    if (null != result) {
                        // 获取返回的省份列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择所在省份</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#provinceList').append(content);
                    } else {
                        $sixmac.notify("获取省份信息失败", "error");
                    }

                    if (null != provinceId && provinceId != '') {
                        $('#provinceList').val(provinceId);
                    }
                });

                var sourceId = 0;
                if (null == provinceId || provinceId == '') {
                    sourceId = $('#provinceList option:selected').val();
                } else {
                    sourceId = provinceId;
                }

                merchant.fn.loadCity(sourceId);
            },
            loadCity: function (sourceId) {
                $('#cityList').html('');

                $sixmac.ajax("common/cityList", {
                    provinceId: sourceId
                }, function (result) {
                    if (null != result) {
                        // 获取返回的城市列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择所在城市</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#cityList').append(content);
                    } else {
                        $('#cityList').append("<option value=''>请选择所在城市</option>");
                    }

                    var cityId = $('#cityId').val();
                    if (null != cityId && cityId != '') {
                        $('#cityList').val(cityId);
                    }
                });
            },
            AddImg: function () {
                // a标签绑定onclick事件
                $('#mainImage').click();
            },
            AddImg3: function () {
                // a标签绑定onclick事件
                $('#mainImage3').click();
            },
            checkData: function () {
                var flag = true;
                var cityId = $('#cityList option:selected').val();
                var styleId = $('#styleList option:selected').val();

                if (merchant.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传头像", "error");
                    flag = false;
                    return;
                }

                if (merchant.v.mainImageStatus3 == 0) {
                    $sixmac.notify("请上传封面", "error");
                    flag = false;
                    return;
                }

                if (styleId == '') {
                    $sixmac.notify("请选择风格", "error");
                    flag = false;
                    return;
                }

                if (cityId == '') {
                    $sixmac.notify("请选择所在城市", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (merchant.fn.checkData()) {
                    $("#merchantForm").ajaxSubmit({
                        url: _basePath + "merchant/savePlus",
                        dataType: "json",
                        data: {
                            "cityId": $('#cityList option:selected').val(),
                            "styleId": $('#styleList option:selected').val()
                        },
                        success: function (result) {
                            if (result > 0) {
                                $sixmac.notify("操作成功", "success");
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            }
        }
    }

    $(document).ready(function () {
        merchant.fn.init();
    });

</script>

</html>