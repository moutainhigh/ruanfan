<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>商品详情</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">商品列表</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增商品</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="productForm" method="post" action="product/save" class="form-horizontal" role="form">
                            <input type="hidden" id="productId" name="id" value="${product.id}">
                            <input type="hidden" id="tempCoverId" value="${product.coverId}">
                            <input type="hidden" id="merchantId" value="${product.merchant.id}">
                            <input type="hidden" id="sortId" value="${product.sort.id}">
                            <input type="hidden" id="tempTypeId" value="${product.type}"/>
                            <span id="tempAddImageIds" style="display: none"></span>
                            <span id="tempDelImageIds" style="display: none"></span>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">商品名称:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="20" data-rule="required" value="${product.name}" placeholder="请输入商品名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">现价:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="price" name="price" maxlength="11" data-rule="required" value="${product.price}" placeholder="请输入现价"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">原价:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="oldPrice" name="oldPrice" maxlength="11" data-rule="required" value="${product.oldPrice}" placeholder="请输入原价"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">选择发布者:</label>

                                <div class="col-sm-4">
                                    <select id="merchantList" style="width: 200px;" class="form-control"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">分类:</label>

                                <div class="col-sm-4">
                                    <select id="typeList" style="width: 200px;" class="form-control">
                                        <option value="">请选择分类</option>
                                        <option value="1">独立商品</option>
                                        <option value="2">设计公司</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">产品种类:</label>

                                <div class="col-sm-4">
                                    <select id="sortList" style="width: 200px;" class="form-control"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">图片:</label>

                                <div class="col-sm-10">
                                    <div style="float: left;margin-bottom: 30px;" id="lastImageDiv">
                                        <a href="javascript:void(0);" onclick="product.fn.AddTempImg()">
                                            <img id="tempPicture" src="static/images/add.jpg" style="height: 200px; width: 200px; display: inherit; margin-bottom: 6px;" border="1"/>
                                        </a>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label style="color: red;margin-left: 18%;">提示:图片尺寸待定</label>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">产地:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="place" name="place" maxlength="11" data-rule="required" value="${product.place}" placeholder="请输入产地"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">标签:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="labels" name="labels" maxlength="11" data-rule="required" value="${product.labels}" placeholder="使用空格隔开"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">颜色:</label>

                                <div class="col-sm-4" style="margin-bottom: -8px;">
                                    <input type="text" class="form-control" id="colors" name="colors" style="margin-bottom: 5px;width: 400px;" readonly data-rule="required" value="${product.colors}" placeholder="点击左下方色块，选择颜色，然后点旁边按钮确定"/>
                                    <input id="baseColor" style="width: 100px;" class="form-control jscolor" value="ab2567">
                                    <button type="button" class="btn btn-success" onclick="product.fn.confirmColor()">选择颜色</button>
                                    <button type="button" class="btn btn-danger" onclick="product.fn.clearColors()">清空</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">尺寸:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="sizes" name="sizes" maxlength="11" data-rule="required" value="${product.sizes}" placeholder="使用空格隔开"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">材质:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="materials" name="materials" maxlength="11" data-rule="required" value="${product.materials}" placeholder="使用空格隔开"/>
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
                                    <button type="button" class="btn btn-primary" onclick="product.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="product.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <span style="display: none;" id="spqq">${product.content}</span>

                        <form id="tempImageForm" method="post" action="common/addTempImage" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="file" name="tempImage" id="tempImage" data-rule="required" style="display:none;" onchange="product.fn.saveTempImage()"/>
                        </form>

                        <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 15px;">
                            <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
                            <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 203px; left: 184px; display: none;" class="axx" onclick="product.fn.deleteImage(this)">
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
    var id = $("#productId").val();
    var editor1 = new baidu.editor.ui.Editor();
    editor1.render('container');
    if (null != id && id != '') {
        editor1.ready(function () {
            this.setContent($("#spqq").html());
        });
    }
</script>
<script type="text/javascript">
    var product = {
        v: {
            id: "product",
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

                if ($("#productId").val() != "") {
                    $("#showH").text("——编辑商品");
                }

                // 页面加载时，自动加载下拉框
                product.fn.getSelectList();

                // 加载数据
                product.fn.loadData();

                $('#provinceList').change(function () {
                    var provinceId = $(this).val();
                    product.fn.loadCity(provinceId);
                });
            },
            loadData: function () {
                // 选择商品分类
                var type = $('#tempTypeId').val();
                if (null != type && type != '') {
                    $('#typeList').val(type);
                }

                // 选择发布者
                var merchantId = $('#merchantId').val();
                if (null != merchantId && merchantId != '') {
                    $('#merchantList').val(merchantId);
                }

                // 选择商品种类
                var sort = $('#sortId').val();
                if (null != sort && sort != '') {
                    $('#sortList').val(sort);
                }

                // 加载商品图片
                product.fn.getSerImages();
            },
            getSerImages: function () {
                var imgList = ${imageList };

                // 计算当前商品图片数量
                product.v.imageSize = imgList.length;

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        product.fn.insertImage(item.path, item.id);
                    } else {
                        $('#lastImageDiv').html('暂无');
                    }
                });

                // 选中封面图
                var id = $('#productId').val();
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
                            product.fn.insertImage(data.path, data.id);

                            product.v.imageSize = product.v.imageSize + 1;
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
                tempDiv.insertBefore("#lastImageDiv");

                // 让所有的克隆出来的
                tempDiv.hover(function () {
                    product.fn.mouseover($(this));
                }, function () {
                    product.fn.mouseOut($(this));
                });
            },
            mouseover: function (mouse) {
                $(mouse).children("a").fadeIn(300);
            },
            mouseOut: function (mouse) {
                $(mouse).children("a").fadeOut(300);
            },
            deleteImage: function (self) {
                product.v.imageSize = product.v.imageSize - 1;
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
                product.v.mainImageStatus = 1;
            },
            getSelectList: function () {
                var merchantId = $('#merchantId').val();
                var sortId = $('#sortId').val();

                // 加载发布者列表
                $sixmac.ajax("common/merchantList", null, function (result) {
                    if (null != result) {
                        // 获取返回的省份列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择发布人</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.nickName + "</option>";
                        });
                        $('#merchantList').append(content);
                    } else {
                        $sixmac.notify("获取发布人信息失败", "error");
                    }

                    if (null != merchantId && merchantId != '') {
                        $('#merchantList').val(merchantId);
                    }
                });

                // 加载商品分类列表
                $sixmac.ajax("common/sortList", null, function (result) {
                    if (null != result) {
                        // 获取返回的省份列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择商品分类</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#sortList').append(content);
                    } else {
                        $sixmac.notify("获取商品分类信息失败", "error");
                    }

                    if (null != sortId && sortId != '') {
                        $('#sortList').val(sortId);
                    }
                });
            },
            AddImg: function () {
                // a标签绑定onclick事件
                $('#mainImage').click();
            },
            checkData: function () {
                var flag = true;
                var name = $('#name').val();
                var price = $('#price').val();
                var merchantId = $('#merchantList option:selected').val();
                var type = $('#typeList option:selected').val();
                var sort = $('#sortList option:selected').val();
                var place = $('#place').val();
                var content = editor1.getContent();

                if (null == name || name == '') {
                    $sixmac.notify("请输入商品名称", "error");
                    flag = false;
                    return;
                }

                if (null == price || price == '') {
                    $sixmac.notify("请输入商品价格", "error");
                    flag = false;
                    return;
                }

                if (null == merchantId || merchantId == '') {
                    $sixmac.notify("请选择发布者", "error");
                    flag = false;
                    return;
                }

                if (null == type || type == '') {
                    $sixmac.notify("请选择分类", "error");
                    flag = false;
                    return;
                }

                if (null == sort || sort == '') {
                    $sixmac.notify("请选择商品种类", "error");
                    flag = false;
                    return;
                }

                if (product.v.imageSize == 0) {
                    $sixmac.notify("请至少上传一张图片", "error");
                    flag = false;
                    return;
                }

                var val = $('input:radio[name="settingCover"]:checked').val();
                if (null == val) {
                    $sixmac.notify("请选择一张封面图", "error");
                    flag = false;
                    return;
                }

                if (null == place || place == '') {
                    $sixmac.notify("请输入商品产地", "error");
                    flag = false;
                    return;
                }

                if (null == content || content == '') {
                    $sixmac.notify("请输入商品描述", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (product.fn.checkData()) {
                    $.post(_basePath + "product/save",
                            {
                                "id": $('#productId').val(),
                                "name": $('#name').val(),
                                "price": $('#price').val(),
                                "oldPrice": $('#oldPrice').val(),
                                "coverId": $('input:radio[name="settingCover"]:checked').val(),
                                "place": $('#place').val(),
                                "labels": $('#labels').val(),
                                "colors": $('#colors').val(),
                                "sizes": $('#sizes').val(),
                                "materials": $('#materials').val(),
                                "type": $('#typeList option:selected').val(),
                                "sort": $('#sortList option:selected').val(),
                                "merchantId": $('#merchantList option:selected').val(),
                                "tempAddImageIds": $("#tempAddImageIds").html(),
                                "tempDelImageIds": $("#tempDelImageIds").html(),
                                "content": editor1.getContent()
                            },
                            function (data) {
                                if (data > 0) {
                                    window.location.href = _basePath + "product/index";
                                } else {
                                    $sixmac.notify("操作失败", "error");
                                }
                            });
                }
            },
            goBack: function () {
                window.location.href = "product/index";
            }
        }
    }

    $(document).ready(function () {
        product.fn.init();
    });

</script>

</html>