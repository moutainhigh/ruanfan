<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>虚拟体验</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理虚拟体验</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增虚拟体验</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="virtualsForm" method="post" enctype="multipart/form-data" action="backend/virtuals/save" class="form-horizontal" role="form">
                            <input type="hidden" id="id" name="id" value="${virtuals.id}">
                            <input type="hidden" id="styleId" value="${virtuals.style.id}">
                            <input type="hidden" id="typeId" value="${virtuals.type.id}">
                            <input type="hidden" id="tempCover" value="${virtuals.cover}"/>
                            <input type="hidden" id="tempAfflatusId" value="${virtuals.afflatusId}"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">名称:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="20" data-rule="required" value="${virtuals.name}" placeholder="请输入虚拟体验名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">风格:</label>
                                <div class="col-sm-3">
                                    <select id="styleList" style="width: 200px;" class="form-control"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">区域:</label>
                                <div class="col-sm-3">
                                    <select id="typeList" style="width: 200px;" class="form-control"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">标签:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="labels" name="labels" maxlength="500" value="${virtuals.labels}" placeholder="使用空格隔开多个标签"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">封面:</label>
                                <div class="col-sm-3">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;" onchange="virtuals.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="virtuals.fn.AddImg()">
                                        <img id="mainPicture" src="${virtuals.cover}" style="height: 320px; width: 320px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">链接:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="url" name="url" maxlength="200" data-rule="required" value="${virtuals.url}" placeholder="请输入链接地址"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">灵感套图:</label>
                                <div class="col-sm-3">
                                    <select id="afflatusList" style="width: 300px;" class="form-control"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="virtuals.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="virtuals.fn.goBack()">返回</button>
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
    var virtuals = {
        v: {
            id: "virtuals",
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
                    $("#showH").text("——编辑虚拟体验");
                }

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                // 页面加载时，自动加载下拉框
                virtuals.fn.getSelectList();

                // 加载数据
                virtuals.fn.loadData();
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    virtuals.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }
            },
            changeStatus: function () {
                virtuals.v.mainImageStatus = 1;
            },
            getSelectList: function () {
                $sixmac.ajax("common/vrtypeList", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>请选择VR虚拟分类</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#typeList').append(content);
                    } else {
                        $sixmac.notify("获取分类信息失败", "error");
                    }

                    var typeId = $('#typeId').val();
                    if (null != typeId && typeId != '') {
                        $('#typeList').val(typeId);
                    }
                });
                $sixmac.ajax("common/styleList", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>请选择VR虚拟风格</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#styleList').append(content);
                    } else {
                        $sixmac.notify("获取风格信息失败", "error");
                    }

                    var styleId = $('#styleId').val();
                    if (null != styleId && styleId != '') {
                        $('#styleList').val(styleId);
                    }
                });
                $sixmac.ajax("common/afflatusList", null, function (result) {
                    if (null != result) {
                        // 获取返回的灵感套图信息，并循环绑定到label中
                        var content = "<option value=''>请选择灵感套图</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#afflatusList').append(content);
                    } else {
                        $sixmac.notify("获取灵感套图信息失败", "error");
                    }

                    var afflatusId = $('#tempAfflatusId').val();
                    if (null != afflatusId && afflatusId != '') {
                        $('#afflatusList').val(afflatusId);
                    }
                });
            },
            AddImg: function () {
                // a标签绑定onclick事件
                $('#mainImage').click();
            },
            subInfo: function () {
                var flag = true;
                var typeId = $('#typeList option:selected').val();
                var styleId = $('#styleList option:selected').val();
                var afflatusId = $('#afflatusList option:selected').val();
                var name = $('#name').val();
                var url = $('#url').val();

                if (null == name || name == '') {
                    $sixmac.notify("请输入虚拟体验名称", "error");
                    flag = false;
                    return;
                }

                if (styleId == '') {
                    $sixmac.notify("请选择VR虚拟风格", "error");
                    flag = false;
                    return;
                }

                if (typeId == '') {
                    $sixmac.notify("请选择VR虚拟区域", "error");
                    flag = false;
                    return;
                }

                if (virtuals.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传封面图", "error");
                    flag = false;
                    return;
                }

                if (null == url || url == '') {
                    $sixmac.notify("请输入链接地址", "error");
                    flag = false;
                    return;
                }

                if (afflatusId == '') {
                    $sixmac.notify("请选择灵感套图", "error");
                    flag = false;
                    return;
                }

                // 所有的验证通过后，执行新增操作
                if (flag) {
                    $("#virtualsForm").ajaxSubmit({
                        url: _basePath + "backend/virtuals/save",
                        dataType: "json",
                        data: {
                            "styleId": styleId,
                            "typeId": typeId,
                            "afflatusId": afflatusId
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "backend/virtuals/index";
                            } else if (result == -200) {
                                $sixmac.notify("该套图已绑定VR，请重新选择套图", "error");
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                window.location.href = "backend/virtuals/index";
            }
        }
    }

    $(document).ready(function () {
        virtuals.fn.init();
    });

</script>

</html>