<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>设计师详情</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">商户列表</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增商户</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="designersForm" method="post" enctype="multipart/form-data" action="backend/designers/save" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${merchants.id}">
                            <input type="hidden" id="provinceId" value="${merchants.city.province.id}">
                            <input type="hidden" id="cityId" value="${merchants.city.id}">
                            <input type="hidden" id="tempTypeId" value="${merchants.type}"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">邮箱:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="email" name="email" maxlength="11" data-rule="required" value="${merchants.email}" placeholder="请输入邮箱"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">密码:</label>
                                <div class="col-sm-4">
                                    <input type="password" class="form-control" id="password" name="password" maxlength="16" data-rule="required" value="${merchants.password}" placeholder="请输入密码"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">名称:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="nickName" name="nickName" maxlength="16" data-rule="required" value="${merchants.nickName}" placeholder="请输入名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">营业执照:</label>
                                <div class="col-sm-10">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;" onchange="merchants.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="merchants.fn.AddImg()">
                                        <img id="mainPicture" src="${merchants.license}" style="height: 320px; width: 320px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">商家类型:</label>
                                <div class="col-sm-4">
                                    <select id="typeList" style="width: 200px;" class="form-control">
                                        <option value="">请选择商家类型</option>
                                        <option value="1">品牌商家</option>
                                        <option value="2">独立商家</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">风格类型:</label>
                                <div class="col-sm-4">
                                    <select id="stileList" style="width: 200px;" class="form-control">
                                        <option value="">请选择风格类型</option>
                                        <option value="1">欧式</option>
                                        <option value="2">美式</option>
                                        <option value="3">日式</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">地理位置:</label>
                                <div class="col-sm-2">
                                    <select id="provinceList" style="width: 150px;" class="form-control"></select>
                                </div>
                                <div class="col-sm-2">
                                    <select id="cityList" style="width: 150px;" class="form-control"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">介绍:</label>
                                <div class="col-sm-6">
                                    <textarea name="description" cols="40" rows="6" class="form-control" style="resize: none">${merchants.description}</textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <button type="button" class="btn btn-primary" onclick="merchants.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="merchants.fn.goBack()">返回</button>
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
    var merchants = {
        v: {
            id: "merchants",
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
                    $("#showH").text("——编辑商户");
                }

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                // 页面加载时，自动加载下拉框
                merchants.fn.getSelectList();

                // 加载数据
                merchants.fn.loadData();

                $('#provinceList').change(function () {
                    $('#cityId').val('');

                    var provinceId = $(this).val();
                    merchants.fn.loadCity(provinceId);
                });
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    merchants.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }

                // 选择商户类型
                var type = $('#tempTypeId').val();
                if (null != type && type != '') {
                    $('#typeList').val(type);
                }

                // 选择风格类型
                var type = $('#tempTypeId').val();
                if (null != type && type != '') {
                    $('#stileList').val(type);
                }
            },
            changeStatus: function () {
                merchants.v.mainImageStatus = 1;
            },
            getSelectList: function () {
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

                merchants.fn.loadCity(sourceId);
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
                        $sixmac.notify("获取城市信息失败", "error");
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
            checkData: function () {
                var flag = true;
                var email = $('#email').val();
                var password = $('#password').val();
                var nickName = $('#nickName').val();
                var typeId = $('#typeList option:selected').val();
                var styleId = $('#styleList option:selected').val();
                var cityId = $('#cityList option:selected').val();

                if (null == email || email == '') {
                    $sixmac.notify("请输入邮箱", "error");
                    flag = false;
                    return;
                }

                if (null == password || password == '') {
                    $sixmac.notify("请输入密码", "error");
                    flag = false;
                    return;
                }

                if (null == nickName || nickName == '') {
                    $sixmac.notify("请输入昵称", "error");
                    flag = false;
                    return;
                }

                if (typeId == '') {
                    $sixmac.notify("请选择商户类型", "error");
                    flag = false;
                    return;
                }

                if (styleId == '') {
                    $sixmac.notify("请选择风格类型", "error");
                    flag = false;
                    return;
                }

                if (merchants.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传营业执照", "error");
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
                if (merchants.fn.checkData()) {
                    $("#designersForm").ajaxSubmit({
                        url: _basePath + "backend/merchants/save",
                        dataType: "json",
                        data: {
                            "type": $('#typeList option:selected').val(),
                            "style": $('#styleList option:selected').val(),
                            "cityId": $('#cityList option:selected').val()
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "backend/merchants/index";
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                window.location.href = "backend/merchants/index";
            }
        }
    }

    $(document).ready(function () {
        merchants.fn.init();
    });

</script>

</html>