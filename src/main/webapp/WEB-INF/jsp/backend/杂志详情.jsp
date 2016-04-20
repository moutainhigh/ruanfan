<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>杂志详情</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理杂志</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增杂志</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="magazineForm" method="post" enctype="multipart/form-data" action="backend/magazine/save" class="form-horizontal" role="form">
                            <input type="hidden" id="id" name="id" value="${magazine.id}">
                            <input type="hidden" id="tempMonth" value="${magazine.month}">
                            <input type="hidden" id="tempCover" value="${magazine.cover}"/>
                            <span id="tempAddImageIds"></span>
                            <span id="tempDelImageIds"></span>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">名称:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="20" data-rule="required" value="${magazine.name}" placeholder="请输入杂志名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">月份:</label>
                                <div class="col-sm-3">
                                    <select id="monthList" style="width: 200px;" class="form-control">
                                        <option value="">请选择月份</option>
                                        <option value="1">1月</option>
                                        <option value="2">2月</option>
                                        <option value="3">3月</option>
                                        <option value="4">4月</option>
                                        <option value="5">5月</option>
                                        <option value="6">6月</option>
                                        <option value="7">7月</option>
                                        <option value="8">8月</option>
                                        <option value="9">9月</option>
                                        <option value="10">10月</option>
                                        <option value="11">11月</option>
                                        <option value="12">12月</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">封面:</label>
                                <div class="col-sm-3">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;" onchange="magazine.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="magazine.fn.AddImg()">
                                        <img id="mainPicture" src="${magazine.cover}" style="height: 200px; width: 200px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">详情图:</label>
                                <div class="col-sm-10">
                                    <div style="float: left;margin-bottom: 30px;" id="lastImageDiv">
                                        <a href="javascript:void(0);" onclick="magazine.fn.AddTempImg()">
                                            <img id="tempPicture" src="static/images/add.jpg" style="height: 200px; width: 200px; display: inherit; margin-bottom: 6px;" border="1"/>
                                        </a>
                                        <span class="msg-box n-right" style="margin-top: 80px;" for="tempImage"></span>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label style="color: red;margin-left: 18%;">提示:图片比例须为16:9</label>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="magazine.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="magazine.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <form id="tempImageForm" method="post" action="common/addTempImage" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="file" name="tempImage" id="tempImage" data-rule="required" style="display:none;" onchange="magazine.fn.saveTempImage()"/>
                        </form>

                        <div id="tempDiv" style="display:none;float: left; height: 200px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 30px;">
                            <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
                            <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 203px; left: 184px; display: none;" class="axx" onclick="magazine.fn.deleteImage(this)">
                                <img id="pic" src="static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                            </a>
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
    var magazine = {
        v: {
            id: "magazine",
            list: [],
            dTable: null,
            mainImageStatus: 0,
            imageSize: 0
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                if ($("#id").val() != "") {
                    $("#showH").text("——编辑杂志");
                }

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                // 加载数据
                magazine.fn.loadData();
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    magazine.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }

                // 选中月份
                var month = $('#tempMonth').val();
                if (null != month && month != '') {
                    $('#monthList').val(month);
                }

                // 加载详情图
                magazine.fn.getSerImages();
            },
            changeStatus: function () {
                magazine.v.mainImageStatus = 1;
            },
            AddTempImg: function () {
                // a标签绑定onclick事件
                $('#tempImage').click();
            },
            getSerImages: function () {
                var imgList = ${imageList };

                // 计算当前灵感图数量
                magazine.v.imageSize = imgList.length;

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        magazine.fn.insertImage(item.path, item.id);
                    } else {
                        $('#lastImageDiv').html('暂无');
                    }
                });
            },
            insertImage: function (path, id) {
                $('#tempPicture').prop('src', "static/images/add.jpg");

                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.children(":first").next().next().next().prop("href", "backend/magazine/addTempLabel?id=" + id);
                tempDiv.children(":first").next().next().next().next().val(id);
                tempDiv.insertBefore("#lastImageDiv");

                // 让所有的克隆出来的
                tempDiv.hover(function () {
                    magazine.fn.mouseover($(this));
                }, function () {
                    magazine.fn.mouseOut($(this));
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
                            magazine.fn.insertImage(data.path, data.id);

                            magazine.v.imageSize = magazine.v.imageSize + 1;
                        } else {
                            $sixmac.notify("图片格式不正确", "error");
                        }
                    }
                });
            },
            deleteImage: function (self) {
                magazine.v.imageSize = magazine.v.imageSize - 1;
                var imageId = $(self).prev().val();
                $('#tempDelImageIds').html($('#tempDelImageIds').html() + imageId + ',');
                $(self).parent().remove();
            },
            AddImg: function () {
                // a标签绑定onclick事件
                $('#mainImage').click();
            },
            subInfo: function () {
                var flag = true;
                var month = $('#monthList option:selected').val();
                var name = $('#name').val();

                if (null == name || name == '') {
                    $sixmac.notify("请输入杂志名称", "error");
                    flag = false;
                    return;
                }

                if (month == '') {
                    $sixmac.notify("请选择月份", "error");
                    flag = false;
                    return;
                }

                if (magazine.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传封面图", "error");
                    flag = false;
                    return;
                }

                if (magazine.v.imageSize == 0) {
                    $sixmac.notify("请至少上传一张详情图", "error");
                    flag = false;
                    return;
                }

                // 所有的验证通过后，执行新增操作
                if (flag) {
                    $("#magazineForm").ajaxSubmit({
                        url: _basePath + "backend/magazine/save",
                        dataType: "json",
                        data: {
                            "month": month,
                            "tempAddImages": $('#tempAddImageIds').html(),
                            "tempDelImages": $('#tempDelImageIds').html()
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "backend/magazine/index";
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                window.location.href = "backend/magazine/index";
            }
        }
    }

    $(document).ready(function () {
        magazine.fn.init();
    });

</script>

</html>