<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>灵感图集</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理灵感图集</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增灵感图集</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="afflatusForm" method="post" enctype="multipart/form-data" action="designer/afflatus/save" class="form-horizontal" role="form">
                            <input type="hidden" id="id" name="id" value="${afflatus.id}">
                            <input type="hidden" id="tempCoverId" value="${afflatus.coverId}">
                            <input type="hidden" id="tempType" value="${afflatus.type}">
                            <input type="hidden" id="tempName" value="${afflatus.name}">
                            <input type="hidden" id="tempDesignerId" value="${afflatus.designer.id}">
                            <input type="hidden" id="tempStyleId" value="${afflatus.style.id}">
                            <input type="hidden" id="tempAreaId" value="${afflatus.area.id}">
                            <input type="hidden" id="tempLabels" value="${afflatus.labels}">
                            <span id="tempAddImageIds" style="display: none"></span>
                            <span id="tempDelImageIds" style="display: none"></span>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">类型:</label>
                                <div class="col-sm-3">
                                    <select id="typeList" name="type" style="width: 120px;" class="form-control">
                                        <option value="1">单图</option>
                                        <option value="2">套图</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group" id="nameDiv" style="display: none;">
                                <label class="col-sm-2 control-label">灵感图名称:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="20" data-rule="required" value="${afflatus.name}" placeholder="请输入灵感图名称"/>
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
                                    <select id="areaList" style="width: 200px;" class="form-control"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">标签:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="labels" name="labels" maxlength="500" value="${afflatus.labels}" placeholder="使用空格隔开多个标签"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">链接:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="url" name="url" maxlength="500" value="${afflatus.url}" placeholder="请输入链接"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">描述:</label>
                                <div class="col-sm-5">
                                    <textarea cols="40" rows="8" class="form-control" id="description" style="resize: none" name="description" maxlength="2000" placeholder="灵感集描述，最多2000字">${afflatus.description}</textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">图片:</label>
                                <div class="col-sm-10">
                                    <div style="float: left;margin-bottom: 30px;display: none;" id="lastImageDiv">
                                        <a href="javascript:void(0);" onclick="afflatus.fn.AddTempImg()">
                                            <img id="tempPicture" src="static/images/add.jpg" style="height: 200px; width: 200px; display: inherit; margin-bottom: 6px;" border="1"/>
                                        </a>
                                        <span class="msg-box n-right" style="margin-top: 80px;" for="tempImage"></span>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label style="color: red;margin-left: 18%;margin-top: 45px;">提示:图片尺寸待定</label>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="afflatus.fn.subInfo()">提交</button>
                                    <a href="designer/afflatus/index" type="button" class="btn btn-primary">返回</a>
                                </div>
                            </div>
                        </form>

                        <form id="tempImageForm" method="post" action="common/addTempImage" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="file" name="tempImage" id="tempImage" data-rule="required" style="display:none;" onchange="afflatus.fn.saveTempImage()"/>
                        </form>

                        <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 105px;">
                            <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
                            <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 207px; left: 184px; display: none;" class="axx" onclick="afflatus.fn.deleteImage(this)">
                                <img id="pic" src="static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                            </a>
                            <a href="javascript:void(0)" target="_blank" class="btn btn-primary btn-sm" role="button" style="color: white; display: none; margin-left: -19px;">添加锚点</a>
                            <input type="radio" name="settingCover"/>设为封面
                            <textarea cols="20" rows="4" style="display: none;resize: none" class="form-control textDesc" name="textDesc"></textarea>
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
    var afflatus = {
        v: {
            id: "afflatus",
            list: [],
            dTable: null,
            imageSize: 0
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                if ($("#id").val() != "") {
                    $("#showH").text("——编辑灵感图集");
                }

                // 页面加载时，自动加载下拉框
                afflatus.fn.getSelectList();

                // 加载数据
                afflatus.fn.loadData();

                $("#typeList").change(function () {
                    if ($(this).val() == 1) {
                        $('#name').val('');
                        $('#labels').val('');

                        // 单图时，隐藏名称和标签
                        $('#nameDiv').css('display', 'none');

                        if (afflatus.v.imageSize == 0) {
                            $("#lastImageDiv").css('display', '');
                        } else {
                            $("#lastImageDiv").css('display', 'none');
                        }

                        // 隐藏图片的文字描述
                        $('.textDesc').val('');
                        $('.textDesc').css('display', 'none');

                        afflatus.fn.removeOtherImage();
                    } else {
                        $('#name').val('');
                        $('#labels').val('');

                        // 套图时，显示名称和标签
                        $('#nameDiv').css('display', '');

                        $("#lastImageDiv").css('display', '');

                        // 显示图片的文字描述
                        $('.textDesc').css('display', '');
                    }
                });
            },
            removeOtherImage: function () {
                // 切换为单图时，移除除了封面之外的所有图片
                var id = $('#id').val();
                if (null != id && id != '') {
                    $('input:radio[name="settingCover"]').each(function () {
                        if (!$(this).prop('checked') && $(this).parent().prop('id') != 'tempDiv') {
                            $('#tempDelImageIds').html($('#tempDelImageIds').html() + $(this).val() + ',');
                            $(this).parent().remove();
                        }
                    });
                }

                afflatus.v.imageSize = 1;
            },
            loadData: function () {
                var id = $('#id').val();
                if (null != id && id != '') {
                    // 设置下拉框选中值
                    var type = $('#tempType').val();
                    $('#typeList').val(type);
                    if (type == 2) {
                        // 套图时，显示名称和标签
                        $('#nameDiv').css('display', '');

                        $('#name').val($('#tempName').val());
                        $('#labels').val($('#tempLabels').val());

                        $("#lastImageDiv").css('display', '');
                    } else {
                        if (afflatus.v.imageSize == 0) {
                            $("#lastImageDiv").css('display', '');
                        }

                        $('#tempDiv').css('margin-bottom', '10px');
                    }

                    // 加载灵感图集图片数组
                    afflatus.fn.getSerImages();
                } else {
                    $("#lastImageDiv").css('display', '');
                }
            },
            AddTempImg: function () {
                // a标签绑定onclick事件
                $('#tempImage').click();
            },
            getSerImages: function () {
                var imgList = ${imageList };

                // 计算当前灵感图数量
                afflatus.v.imageSize = imgList.length;

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        afflatus.fn.insertImage(item.path, item.id, item.description);
                    } else {
                        $('#lastImageDiv').html('暂无');
                    }
                });

                var type = $('#typeList option:selected').val();
                if (type == 1 && imgList.length == 1) {
                    $("#lastImageDiv").css('display', 'none');
                }

                // 选中封面图
                var id = $('#id').val();
                var coverId = $('#tempCoverId').val();
                if (null != id && id != '') {
                    $('input:radio[name="settingCover"]').each(function () {
                        if ($(this).val() == coverId) {
                            $(this).prop('checked', true);
                        }
                    });
                }
            },
            insertImage: function (path, id, description) {
                var typeId = $("#typeList option:selected").val();

                $('#tempPicture').prop('src', "static/images/add.jpg");

                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.children(":first").next().next().next().prop("href", "designer/afflatus/addTempLabel?id=" + id);
                tempDiv.children(":first").next().next().next().next().val(id);
                tempDiv.children(":first").next().next().next().next().next().val(description);

                if (typeId == 2) {
                    tempDiv.children(":first").next().next().next().next().next().css('display', '');
                }

                tempDiv.insertBefore("#lastImageDiv");

                var type = $('#typeList option:selected').val();
                if (type == 1) {
                    $("#lastImageDiv").css('display', 'none');
                } else {
                    $("#lastImageDiv").css('display', '');
                }

                // 让所有的克隆出来的
                tempDiv.hover(function () {
                    afflatus.fn.mouseover($(this));
                }, function () {
                    afflatus.fn.mouseOut($(this));
                });
            },
            mouseover: function (mouse) {
                $(mouse).children("a").fadeIn(300);
            },
            mouseOut: function (mouse) {
                $(mouse).children("a").fadeOut(300);
            },
            saveTempImage: function () {
                $("#tempImageForm").ajaxSubmit({
                    dataType: "json",
                    success: function (data) {
                        if (null != data.path && data.path != '') {
                            $('#tempAddImageIds').html($('#tempAddImageIds').html() + data.id + ',');
                            afflatus.fn.insertImage(data.path, data.id, '');

                            afflatus.v.imageSize = afflatus.v.imageSize + 1;

                            var type = $('#typeList option:selected').val();
                            if (type == 1 && afflatus.v.imageSize == 1) {
                                $("#lastImageDiv").css('display', 'none');
                            }
                        } else {
                            $sixmac.notify("图片格式不正确", "error");
                            $("#lastImageDiv").css('display', '');
                        }
                    }
                });
            },
            deleteImage: function (self) {
                afflatus.v.imageSize = afflatus.v.imageSize - 1;
                var imageId = $(self).prev().val();
                $('#tempDelImageIds').html($('#tempDelImageIds').html() + imageId + ',');
                $(self).parent().remove();
                var type = $('#typeList option:selected').val();
                if (type == 1) {
                    if (afflatus.v.imageSize == 0) {
                        $("#lastImageDiv").css('display', '');
                    }
                } else {
                    $("#lastImageDiv").css('display', '');
                }
            },
            getSelectList: function () {
                $sixmac.ajax("common/areaList", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>请选择灵感图区域</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#areaList').append(content);
                    } else {
                        $sixmac.notify("获取区域信息失败", "error");
                    }

                    var areaId = $('#tempAreaId').val();
                    if (null != areaId && areaId != '') {
                        $('#areaList').val(areaId);
                    }
                });
                $sixmac.ajax("common/styleList", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>请选择灵感图风格</option>";
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
            },
            subInfo: function () {
                var flag = true;
                var type = $('#typeList option:selected').val();
                var styleId = $('#styleList option:selected').val();
                var areaId = $('#areaList option:selected').val();
                var name = $('#name').val();
                var labels = $('#labels').val();
                var description = $('#description').val();

                // 当灵感集的类型为套图时，检查名称是否为空
                if (type == 2) {
                    if (null == name || name == '') {
                        $sixmac.notify("请输入灵感图名称", "error");
                        flag = false;
                        return;
                    }
                }

                if (styleId == '') {
                    $sixmac.notify("请选择灵感图风格", "error");
                    flag = false;
                    return;
                }

                if (areaId == '') {
                    $sixmac.notify("请选择灵感图区域", "error");
                    flag = false;
                    return;
                }

                if (labels == '') {
                    $sixmac.notify("请输入标签", "error");
                    flag = false;
                    return;
                }

                if (description == '') {
                    $sixmac.notify("请输入描述", "error");
                    flag = false;
                    return;
                }

                if (afflatus.v.imageSize == 0) {
                    $sixmac.notify("请至少上传一张灵感图", "error");
                    flag = false;
                    return;
                }

                if (type == 1 && afflatus.v.imageSize > 1) {
                    $sixmac.notify("单图只能上传一张灵感图", "error");
                    flag = false;
                    return;
                }

                var val = $('input:radio[name="settingCover"]:checked').val();
                if (null == val) {
                    $sixmac.notify("请选择一张封面图", "error");
                    flag = false;
                    return;
                }

                // 所有的验证通过后，执行新增操作
                if (flag) {
                    $("#afflatusForm").ajaxSubmit({
                        url: _basePath + "designer/afflatus/save",
                        dataType: "json",
                        data: {
                            "styleId": styleId,
                            "areaId": areaId,
                            "tempAddImageIds": $("#tempAddImageIds").html(),
                            "tempDelImageIds": $("#tempDelImageIds").html()
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "designer/afflatus/index";
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
        afflatus.fn.init();
    });

</script>

</html>