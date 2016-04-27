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
                <h1 class="page-header">管理设计师</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增设计师</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="designersForm" method="post" enctype="multipart/form-data" action="backend/designers/save" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${designers.id}">
                            <input type="hidden" id="provinceId" value="${designers.city.province.id}">
                            <input type="hidden" id="cityId" value="${designers.city.id}">
                            <input type="hidden" id="tempHead" value="${designers.head}"/>
                            <input type="hidden" id="tempTypeId" value="${designers.type}"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">头像:</label>
                                <div class="col-sm-10">
                                    <input type="file" name="mainImage2" id="mainImage2" style="display:none;" onchange="designers.fn.changeStatus2()"/>
                                    <a href="javascript:void(0);" onclick="designers.fn.AddImg2()">
                                        <img id="mainPicture2" src="${designers.head}" style="height: 320px; width: 320px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">手机号:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="mobile" name="mobile" maxlength="11" data-rule="required" value="${designers.mobile}" placeholder="请输入手机号"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">密码:</label>
                                <div class="col-sm-4">
                                    <input type="password" class="form-control" id="password" name="password" maxlength="16" data-rule="required" value="${designers.password}" placeholder="请输入密码"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">设计师类型:</label>
                                <div class="col-sm-4">
                                    <select id="typeList" style="width: 200px;" class="form-control">
                                        <option value="">请选择设计师类型</option>
                                        <option value="1">独立设计师</option>
                                        <option value="2">设计公司</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">昵称:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="nickName" name="nickName" maxlength="500" value="${designers.nickName}" placeholder="请输入昵称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">资质证明:</label>
                                <div class="col-sm-10">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;" onchange="designers.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="designers.fn.AddImg()">
                                        <img id="mainPicture" src="${designers.proof}" style="height: 320px; width: 320px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">价格:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="price" name="price" maxlength="20" value="${designers.price}" placeholder="请输入价格"/>
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
                                <label class="col-sm-2 control-label">签名:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="sign" name="sign" maxlength="10" value="${designers.sign}" placeholder="请输入签名"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">介绍:</label>
                                <div class="col-sm-6">
                                    <textarea name="content" id="content" cols="40" rows="6" maxlength="200" class="form-control" style="resize: none">${designers.content}</textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <button type="button" class="btn btn-primary" onclick="designers.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="designers.fn.goBack()">返回</button>
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
    var designers = {
        v: {
            id: "designers",
            list: [],
            dTable: null,
            mainImageStatus: 0,
            mainImageStatus2: 0
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                if ($("#id").val() != "") {
                    $("#showH").text("——编辑设计师");
                }

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                $("#mainImage2").uploadPreview({
                    Img: "mainPicture2",
                    Width: 200,
                    Height: 170
                });

                // 页面加载时，自动加载下拉框
                designers.fn.getSelectList();

                // 加载数据
                designers.fn.loadData();

                $('#provinceList').change(function () {
                    $('#cityId').val('');

                    var provinceId = $(this).val();
                    designers.fn.loadCity(provinceId);
                });
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    designers.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }

                var mainImagePath2 = $('#mainPicture2').attr('src');
                if (null != mainImagePath2 && mainImagePath2 != '') {
                    designers.v.mainImageStatus2 = 1;
                } else {
                    $('#mainPicture2').attr('src', 'static/images/add.jpg');
                }

                // 选择设计师类型
                var type = $('#tempTypeId').val();
                if (null != type && type != '') {
                    $('#typeList').val(type);
                }
            },
            changeStatus: function () {
                designers.v.mainImageStatus = 1;
            },
            changeStatus2: function () {
                designers.v.mainImageStatus2 = 1;
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

                designers.fn.loadCity(sourceId);
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
            AddImg2: function () {
                // a标签绑定onclick事件
                $('#mainImage2').click();
            },
            checkData: function () {
                var flag = true;
                var mobile = $('#mobile').val();
                var password = $('#password').val();
                var nickName = $('#nickName').val();
                var typeId = $('#typeList option:selected').val();
                var cityId = $('#cityList option:selected').val();
                var price = $('#price').val();
                var sign = $('#sign').val();
                var content = $('#content').val();

                if (designers.v.mainImageStatus2 == 0) {
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
                    $sixmac.notify("请选择设计师类型", "error");
                    flag = false;
                    return;
                }

                if (null == nickName || nickName == '') {
                    $sixmac.notify("请输入昵称", "error");
                    flag = false;
                    return;
                }

                if (designers.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传资质证明", "error");
                    flag = false;
                    return;
                }

                if (null == price || price == '') {
                    $sixmac.notify("请输入价格", "error");
                    flag = false;
                    return;
                }

                if (cityId == '') {
                    $sixmac.notify("请选择所在城市", "error");
                    flag = false;
                    return;
                }

                if (null == sign || sign == '') {
                    $sixmac.notify("请输入签名", "error");
                    flag = false;
                    return;
                }

                if (null == content || content == '') {
                    $sixmac.notify("请输入介绍", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (designers.fn.checkData()) {
                    $("#designersForm").ajaxSubmit({
                        url: _basePath + "backend/designers/save",
                        dataType: "json",
                        data: {
                            "type": $('#typeList option:selected').val(),
                            "cityId": $('#cityList option:selected').val()
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "backend/designers/index";
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                window.location.href = "backend/designers/index";
            }
        }
    }

    $(document).ready(function () {
        designers.fn.init();
    });

</script>

</html>