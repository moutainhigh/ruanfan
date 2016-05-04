<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>作品列表</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">作品列表</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增作品</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="worksForm" method="post" enctype="multipart/form-data" action="designer/works/save" class="form-horizontal" role="form">
                            <input type="hidden" id="id" name="id" value="${works.id}">
                            <input type="hidden" id="tempCoverId" value="${works.coverId}">
                            <input type="hidden" id="tempName" value="${works.name}">
                            <input type="hidden" id="tempLabels" value="${works.labels}">
                            <input type="hidden" id="tempDescription" value="${works.description}">
                            <span id="tempAddImageIds" style="display: none"></span>
                            <span id="tempDelImageIds" style="display: none"></span>

                            <div class="form-group" id="nameDiv">
                                <label class="col-sm-2 control-label">作品名称:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="20" data-rule="required" value="${works.name}" placeholder="请输入作品名称"/>
                                </div>
                            </div>

                            <div class="form-group" id="labelsDiv">
                                <label class="col-sm-2 control-label">标签:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="labels" name="labels" maxlength="500" value="${works.labels}" placeholder="使用空格隔开多个标签"/>
                                </div>
                            </div>

                            <div class="form-group" id="descriptionDiv">
                                <label class="col-sm-2 control-label">描述:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="description" name="description" maxlength="20" data-rule="required" value="${works.description}" placeholder="请输入描述"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">图片:</label>
                                <div class="col-sm-10">
                                    <div style="float: left;margin-bottom: 30px;display: none;" id="lastImageDiv">
                                        <a href="javascript:void(0);" onclick="works.fn.AddTempImg()">
                                            <img id="tempPicture" src="static/images/add.jpg" style="height: 200px; width: 200px; display: inherit; margin-bottom: 6px;" border="1"/>
                                        </a>
                                        <span class="msg-box n-right" style="margin-top: 80px;" for="tempImage"></span>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label style="color: red;margin-left: 18%;">提示:图片尺寸待定</label>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="works.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="works.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <form id="tempImageForm" method="post" action="common/addTempImage" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="file" name="tempImage" id="tempImage" data-rule="required" style="display:none;" onchange="works.fn.saveTempImage()"/>
                        </form>

                        <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 30px;">
                            <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
                            <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 204px; left: 184px; display: none;" class="axx" onclick="works.fn.deleteImage(this)">
                                <img id="pic" src="static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                            </a>
                            <input type="radio" name="settingCover"/>设为封面
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
    var works = {
        v: {
            id: "works",
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
                    $("#showH").text("——编辑作品");
                }

                $("#lastImageDiv").css('display', '');

                // 加载数据
                works.fn.loadData();
            },
            loadData: function () {
                var id = $('#id').val();
                if (null != id && id != '') {

                    $('#name').val($('#tempName').val());
                    $('#labels').val($('#tempLabels').val());
                    $('#description').val($('#tempDescription').val());

                    $("#lastImageDiv").css('display', '');

                    // 加载作品图片数组
                    works.fn.getSerImages();
                }
            },
            AddTempImg: function () {
                // a标签绑定onclick事件
                $('#tempImage').click();
            },
            getSerImages: function () {
                var imgList = ${imageList };

                // 计算当前灵感图数量
                works.v.imageSize = imgList.length;

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        works.fn.insertImage(item.path, item.id);
                    } else {
                        $('#lastImageDiv').html('暂无');
                    }
                });

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
            insertImage: function (path, id) {
                $('#tempPicture').prop('src', "static/images/add.jpg");

                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css('display', '');
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.children(":first").next().next().next().val(id);
                tempDiv.insertBefore("#lastImageDiv");

                $("#lastImageDiv").css('display', '');

                // 让所有的克隆出来的
                tempDiv.hover(function () {
                    works.fn.mouseover($(this));
                }, function () {
                    works.fn.mouseOut($(this));
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
                            works.fn.insertImage(data.path, data.id);

                            works.v.imageSize = works.v.imageSize + 1;
                        } else {
                            $sixmac.notify("图片格式不正确", "error");
                            $("#lastImageDiv").css('display', '');
                        }
                    }
                });
            },
            deleteImage: function (self) {
                works.v.imageSize = works.v.imageSize - 1;
                var imageId = $(self).prev().val();
                $('#tempDelImageIds').html($('#tempDelImageIds').html() + imageId + ',');
                $(self).parent().remove();
            },
            subInfo: function () {
                var flag = true;
                var name = $('#name').val();
                var labels = $('#labels').val();
                var description = $('#description').val();

                //检查名称是否为空
                if (null == name || name == '') {
                    $sixmac.notify("请输入作品名称", "error");
                    flag = false;
                    return;
                }

                if (null == labels || labels == '') {
                    $sixmac.notify("请输入作品标签", "error");
                    flag = false;
                    return;
                }

                if (null == description || description == '') {
                    $sixmac.notify("请输入作品描述", "error");
                    flag = false;
                    return;
                }

                if (works.v.imageSize == 0) {
                    $sixmac.notify("请至少上传一张作品图", "error");
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
                    $("#worksForm").ajaxSubmit({
                        url: _basePath + "designer/works/save",
                        dataType: "json",
                        data: {
                            "tempAddImageIds": $("#tempAddImageIds").html(),
                            "tempDelImageIds": $("#tempDelImageIds").html()
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "designer/works/index";
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                window.location.href = "designer/works/index";
            }
        }
    }

    $(document).ready(function () {
        works.fn.init();
    });

</script>

</html>