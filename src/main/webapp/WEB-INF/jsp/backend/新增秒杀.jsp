<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>秒杀详情</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">秒杀管理</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增秒杀</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="spikesForm" method="post" action="backend/spikes/save" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="spikesId" name="id" value="${spikes.id}">
                            <input type="hidden" id="tempCoverId" value="${spikes.coverId}">
                            <span id="tempAddImageIds" style="display: none"></span>
                            <span id="tempDelImageIds" style="display: none"></span>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">秒杀名称:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="50" data-rule="required" value="${spikes.name}" placeholder="请输入秒杀名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">秒杀价:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="price" name="price" maxlength="20" data-rule="required" value="${spikes.price}" placeholder="请输入现价"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">原价:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="oldPrice" name="oldPrice" maxlength="20" data-rule="required" value="${spikes.oldPrice}" placeholder="请输入原价"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">秒杀时间:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control input-append date form_datetime" style="width: 180px;" readonly id="startTime" name="startTime" maxlength="20" data-rule="required" value="${spikes.startTime}" placeholder="开始时间">
                                    &nbsp; 至 &nbsp;
                                    <input type="text" class="form-control input-append date form_datetime" style="width: 180px;" readonly id="endTime" name="endTime" maxlength="20" data-rule="required" value="${spikes.endTime}" placeholder="结束时间">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">图片:</label>

                                <div class="col-sm-10">
                                    <div style="float: left;margin-bottom: 30px;" id="lastImageDiv">
                                        <a href="javascript:void(0);" onclick="spikes.fn.AddTempImg()">
                                            <img id="tempPicture" src="static/images/add.jpg" style="height: 200px; width: 200px; display: inherit; margin-bottom: 6px;" border="1"/>
                                        </a>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label style="color: red;margin-left: 18%;">提示:图片尺寸待定</label>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">标签:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="labels" name="labels" maxlength="200" data-rule="required" value="${spikes.labels}" placeholder="使用空格隔开"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">颜色:</label>

                                <div class="col-sm-4" style="margin-bottom: -8px;">
                                    <input type="text" class="form-control" id="colors" name="colors" style="margin-bottom: 5px;" readonly data-rule="required" value="${spikes.colors}" placeholder="点击左下方色块，选择颜色，然后点旁边按钮确定"/>
                                    <input id="baseColor" style="width: 100px;" class="form-control jscolor" value="ab2567">
                                    <button type="button" class="btn btn-success" onclick="spikes.fn.confirmColor()">选择颜色</button>
                                    <button type="button" class="btn btn-danger" onclick="spikes.fn.clearColors()">清空</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">尺寸:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="sizes" name="sizes" maxlength="200" data-rule="required" value="${spikes.sizes}" placeholder="使用空格隔开"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">材质:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="materials" name="materials" maxlength="200" data-rule="required" value="${spikes.materials}" placeholder="使用空格隔开"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">产品描述:</label>

                                <div class="col-sm-8">
                                    <!-- 百度富文本编辑框 -->
                                    <script id="container" name="content" type="text/plain" style="width:100%; height:150px; line-height: 0px;"></script>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <button type="button" class="btn btn-primary" onclick="spikes.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="spikes.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <span style="display: none;" id="spqq">${spikes.description}</span>

                        <form id="tempImageForm" method="post" action="common/addTempImage" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="file" name="tempImage" id="tempImage" data-rule="required" style="display:none;" onchange="spikes.fn.saveTempImage()"/>
                        </form>

                        <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 15px;">
                            <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
                            <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 203px; left: 184px; display: none;" class="axx" onclick="spikes.fn.deleteImage(this)">
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

<!-- 实例化编辑器 -->
<script type="text/javascript">
    var id = $("#spikesId").val();
    var editor1 = new baidu.editor.ui.Editor();
    editor1.render('container');
    if (null != id && id != '') {
        editor1.ready(function () {
            this.setContent($("#spqq").html());
        });
    }
</script>

<script type="text/javascript">
    var spikes = {
        v: {
            id: "spikes",
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

                if ($("#spikesId").val() != "") {
                    $("#showH").text("——编辑秒杀");
                }

                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 2,
                    forceParse: 0,
                    showMeridian: 1,
                    format: 'yyyy-mm-dd hh:ii:ss'
                });

                // 加载数据
                spikes.fn.loadData();
            },
            loadData: function () {
                // 加载秒杀图片
                spikes.fn.getSerImages();
            },
            getSerImages: function () {
                var imgList = ${imageList };

                // 计算当前秒杀图片数量
                spikes.v.imageSize = imgList.length;

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        spikes.fn.insertImage(item.path, item.id);
                    } else {
                        $('#lastImageDiv').html('暂无');
                    }
                });

                // 选中封面图
                var id = $('#spikesId').val();
                var coverId = $('#tempCoverId').val();
                if (null != id && id != '') {
                    $('input:radio[name="settingCover"]').each(function () {
                        if ($(this).val() == coverId) {
                            $(this).prop('checked', true);
                        }
                    });
                }
            },
            AddTempImg: function () {
                // a标签绑定onclick事件
                $('#tempImage').click();
            },
            saveTempImage: function () {
                $("#tempImageForm").ajaxSubmit({
                    dataType: "json",
                    success: function (data) {
                        if (null != data.path && data.path != '') {
                            $('#tempAddImageIds').html($('#tempAddImageIds').html() + data.id + ',');
                            spikes.fn.insertImage(data.path, data.id);

                            spikes.v.imageSize = spikes.v.imageSize + 1;
                        } else {
                            $sixmac.notify("图片格式不正确", "error");
                        }
                    }
                });
            },
            insertImage: function (path, id) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.children(":first").next().next().next().prop("value", id);
                tempDiv.insertBefore("#lastImageDiv");

                // 让所有的克隆出来的
                tempDiv.hover(function () {
                    spikes.fn.mouseover($(this));
                }, function () {
                    spikes.fn.mouseOut($(this));
                });
            },
            mouseover: function (mouse) {
                $(mouse).children("a").fadeIn(300);
            },
            mouseOut: function (mouse) {
                $(mouse).children("a").fadeOut(300);
            },
            deleteImage: function (self) {
                spikes.v.imageSize = spikes.v.imageSize - 1;
                var imageId = $(self).prev().val();
                $('#tempDelImageIds').html($('#tempDelImageIds').html() + imageId + ',');
                $(self).parent().remove();
            },
            confirmColor: function () {
                var colorInfo = $('#baseColor').val();
                var colorVal = $('#colors').val();

                // 先检测该色值是否已经添加，如果已经添加，则提示用户已经添加，无法再次添加
                var result = true;
                var colorArray = colorVal.split(',');

                if (colorArray.length >= 4) {
                    $sixmac.notify("最多添加三种颜色", "error");
                    return;
                }

                for (var i = 0; i < colorArray.length; i++) {
                    if (colorArray[i] == colorInfo) {
                        result = false;
                    }
                }

                if (result) {
                    $('#colors').val($('#colors').val() + colorInfo + ",");
                } else {
                    $sixmac.notify("已有相同颜色", "error");
                }
            },
            clearColors: function () {
                $('#colors').val('');
            },
            changeStatus: function () {
                spikes.v.mainImageStatus = 1;
            },
            AddImg: function () {
                // a标签绑定onclick事件
                $('#mainImage').click();
            },
            checkData: function () {
                var flag = true;
                var name = $('#name').val();
                var price = $('#price').val();
                var content = editor1.getContent();
                var startTime = $('#startTime').val();
                var endTime = $('#endTime').val();

                if (null == name || name == '') {
                    $sixmac.notify("请输入秒杀名称", "error");
                    flag = false;
                    return;
                }

                if (null == price || price == '') {
                    $sixmac.notify("请输入秒杀价格", "error");
                    flag = false;
                    return;
                }

                if (null == startTime || startTime == '') {
                    $sixmac.notify("请输入开始时间", "error");
                    flag = false;
                    return;
                }
                if (null == endTime || endTime == '') {
                    $sixmac.notify("请输入结束时间", "error");
                    flag = false;
                    return;
                }


                if (spikes.v.imageSize == 0) {
                    $sixmac.notify("请至少上传一张图片", "error");
                    flag = false;
                    return;
                }

                var val = $('input:radio[name="settingCover"]:checked').val();
                if (null == val || val == 'undefined') {
                    $sixmac.notify("请选择一张封面图", "error");
                    flag = false;
                    return;
                }

                if (null == content || content == '') {
                    $sixmac.notify("请输入秒杀描述", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (spikes.fn.checkData()) {
                    $.post(_basePath + "backend/spikes/save",
                            {
                                "id": $('#spikesId').val(),
                                "name": $('#name').val(),
                                "price": $('#price').val(),
                                "oldPrice": $('#oldPrice').val(),
                                "startTime": $('#startTime').val(),
                                "endTime": $('#endTime').val(),
                                "coverId": $("input[type='radio']:checked").val(),
                                "labels": $('#labels').val(),
                                "colors": $('#colors').val(),
                                "sizes": $('#sizes').val(),
                                "materials": $('#materials').val(),
                                "tempAddImageIds": $("#tempAddImageIds").html(),
                                "tempDelImageIds": $("#tempDelImageIds").html(),
                                "content": editor1.getContent()
                            },
                            function (data) {
                                if (data > 0) {
                                    window.location.href = _basePath + "backend/spikes/index";
                                } else {
                                    $sixmac.notify("操作失败", "error");
                                }
                            });
                }
            },
            goBack: function () {
                window.location.href = "backend/spikes/index";
            }
        }
    }

    $(document).ready(function () {
        spikes.fn.init();
    });

</script>

</html>