<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>会员详情</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理会员</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增会员</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="usersForm" method="post" enctype="multipart/form-data" action="backend/users/save" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${users.id}">
                            <input type="hidden" id="provinceId" value="${users.city.province.id}">
                            <input type="hidden" id="cityId" value="${users.city.id}">
                            <input type="hidden" id="tempTypeId" value="${users.type}"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">头像:</label>

                                <div class="col-sm-4">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;" onchange="users.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="users.fn.AddImg()">
                                        <img id="mainPicture" src="${users.headPath}" style="height: 200px; width: 200px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">账号:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="mobile" name="mobile" maxlength="11" data-rule="required" value="${users.mobile}" placeholder="请输入手机号"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">密码:</label>

                                <div class="col-sm-4">
                                    <input type="password" class="form-control" id="password" name="password" maxlength="16" data-rule="required" value="${users.password}" placeholder="请输入密码"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">会员类型:</label>

                                <div class="col-sm-4">
                                    <select id="typeList" style="width: 200px;" class="form-control">
                                        <option value="">请选择会员类型</option>
                                        <option value="1">马甲会员</option>
                                        <option value="2">普通会员</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">昵称:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="nickName" name="nickName" maxlength="20" value="${users.nickName}" placeholder="请输入昵称"/>
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
                                <label class="col-sm-2 control-label">小区名:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="comName" name="comName" maxlength="20" value="${users.comName}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">建筑面积:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="comArea" name="comArea" maxlength="20" value="${users.comArea}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <button type="button" class="btn btn-primary" onclick="users.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="users.fn.goBack()">返回</button>
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
    var users = {
        v: {
            id: "users",
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
                    $("#showH").text("——编辑会员");
                }

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                // 页面加载时，自动加载下拉框
                users.fn.getSelectList();

                // 加载数据
                users.fn.loadData();

                $('#provinceList').change(function () {
                    $('#cityId').val('');

                    var provinceId = $(this).val();
                    users.fn.loadCity(provinceId);
                });
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    users.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }

                // 选择会员类型
                var type = $('#tempTypeId').val();
                if (null != type && type != '') {
                    $('#typeList').val(type);
                }
            },
            changeStatus: function () {
                users.v.mainImageStatus = 1;
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

                users.fn.loadCity(sourceId);
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
                var mobile = $('#mobile').val();
                var password = $('#password').val();
                var nickName = $('#nickName').val();
                var typeId = $('#typeList option:selected').val();
                var cityId = $('#cityList option:selected').val();

                if (users.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传头像", "error");
                    flag = false;
                    return;
                }

                if (null == mobile || mobile == '') {
                    $sixmac.notify("请输入手机号", "error");
                    flag = false;
                    return;
                }

                if (null == password || password == '') {
                    $sixmac.notify("请输入密码", "error");
                    flag = false;
                    return;
                }

                if (typeId == '') {
                    $sixmac.notify("请选择会员类型", "error");
                    flag = false;
                    return;
                }

                if (null == nickName || nickName == '') {
                    $sixmac.notify("请输入昵称", "error");
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
                if (users.fn.checkData()) {
                    $("#usersForm").ajaxSubmit({
                        url: _basePath + "backend/users/save",
                        dataType: "json",
                        data: {
                            "type": $('#typeList option:selected').val(),
                            "cityId": $('#cityList option:selected').val()
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "backend/users/index";
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                window.location.href = "backend/users/index";
            }
        }
    }

    $(document).ready(function () {
        users.fn.init();
    });

</script>

</html>