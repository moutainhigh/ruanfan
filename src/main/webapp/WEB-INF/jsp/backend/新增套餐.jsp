<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>套餐详情</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理商品套餐</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增套餐</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="packagesForm" method="post" action="backend/packages/save" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="packagesId" name="id" value="${packages.id}">
                            <input type="hidden" id="tempCoverId" value="${packages.coverId}">
                            <input type="hidden" id="tempBrandId" value="${packages.brand.id}"/>
                            <span id="tempAddImageIds" style="display: none">${imageIds}</span>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">套餐名称:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="50" data-rule="required" value="${packages.name}" placeholder="请输入套餐名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">现价:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="price" name="price" maxlength="20" data-rule="required" value="${packages.price}" placeholder="请输入现价"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">原价:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="oldPrice" name="oldPrice" maxlength="20" data-rule="required" value="${packages.oldPrice}" placeholder="请输入原价"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">品牌:</label>

                                <div class="col-sm-4">
                                    <select id="brandList" style="width: 200px;" class="form-control"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">标签:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="labels" name="labels" maxlength="200" data-rule="required" value="${packages.labels}" placeholder="使用空格隔开"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">图片:</label>

                                <div class="col-sm-10">
                                    <div style="float: left;margin-bottom: 30px;" id="lastImageDiv"></div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"></label>

                                <div class="col-sm-8" style="margin-bottom: -8px;">
                                    <select id="productList" style="width: 200px;" class="form-control"></select>
                                    <select id="colorList" style="width: 150px;" class="form-control"></select>
                                    <select id="sizeList" style="width: 150px;" class="form-control"></select>
                                    <select id="materialList" style="width: 150px;" class="form-control"></select>
                                    <button type="button" class="btn btn-success" onclick="packages.fn.confirmProduct()">选择商品</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">套餐描述:</label>

                                <div class="col-sm-8">
                                    <!-- 百度富文本编辑框 -->
                                    <script id="container" name="content" type="text/plain" style="width:100%; height:150px; line-height: 0px;"></script>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <button type="button" class="btn btn-primary" onclick="packages.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="packages.fn.goBack()">返回</button>
                                </div>
                            </div>
                        </form>

                        <span style="display: none;" id="spqq">${packages.description}</span>

                        <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 30px;">
                            <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
                            <input type="radio" name="settingCover"/>设为封面
                            <button type="button" class="btn btn-danger btn-sm" style="margin-top: 5px;" onclick="packages.fn.removeProduct(this)">删除</button>
                            <input name="tempProductColor" type="hidden"/>
                            <input name="tempProductSize" type="hidden"/>
                            <input name="tempProductMaterial" type="hidden"/>
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
    var id = $("#packagesId").val();
    var editor1 = new baidu.editor.ui.Editor();
    editor1.render('container');
    if (null != id && id != '') {
        editor1.ready(function () {
            this.setContent($("#spqq").html());
        });
    }
</script>
<script type="text/javascript">
    var packages = {
        v: {
            id: "packages",
            list: [],
            dTable: null,
            imageSize: 0,
            tempColors: "",
            tempSizes: "",
            tempMaterials: ""
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                if ($("#packagesId").val() != "") {
                    $("#showH").text("——编辑套餐");
                }

                // 页面加载时，自动加载下拉框
                packages.fn.getSelectList();

                // 加载数据
                packages.fn.loadData();

                $('#productList').change(function () {
                    var productId = $(this).val();
                    packages.fn.findColors(productId);
                    packages.fn.findSizes(productId);
                    packages.fn.findMaterials(productId);
                });
            },
            loadData: function () {
                // 加载套餐包含的商品图片数组
                packages.fn.getSerImages();
            },
            getSerImages: function () {
                var imgList = ${imageList };

                // 计算当前灵感图数量
                packages.v.imageSize = imgList.length;

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        packages.fn.insertImage(item.path, item.id, item.colors, item.sizes, item.materials);
                    } else {
                        $('#lastImageDiv').html('暂无');
                    }
                });

                // 选中封面图
                var id = $('#packagesId').val();
                var coverId = $('#tempCoverId').val();
                if (null != id && id != '') {
                    $('input:radio[name="settingCover"]').each(function () {
                        if ($(this).prev().val() == coverId) {
                            $(this).prop('checked', true);
                        }
                    });
                }
            },
            confirmProduct: function () {
                var productId = $('#productList option:selected').val();
                if (null == productId || productId == '') {
                    $sixmac.notify("请先选择商品", "error");
                    return;
                }
                if ($('#colorList option:selected').val() == '') {
                    $sixmac.notify("暂无颜色", "error");
                    return;
                }
                if ($('#sizeList option:selected').val() == '') {
                    $sixmac.notify("暂无尺寸", "error");
                    return;
                }
                if ($('#materialList option:selected').val() == '') {
                    $sixmac.notify("暂无材质", "error");
                    return;
                }
                // 根据商品id查询对应的封面图信息
                $sixmac.ajax("common/findCoverByProductId", {productId: productId}, function (result) {
                    if (null != result) {
                        packages.v.imageSize = packages.v.imageSize + 1;
                        packages.fn.insertImage(result.path, result.id, $('#colorList option:selected').val(), $('#sizeList option:selected').val(), $('#materialList option:selected').val());
                        $('#tempAddImageIds').html($('#tempAddImageIds').html() + result.id + ',');
                    } else {
                        $sixmac.notify("获取封面图信息失败", "error");
                    }
                });
            },
            insertImage: function (path, id, colors, sizes, materials) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.children(":first").next().next().next().next().prop("value", colors);
                tempDiv.children(":first").next().next().next().next().next().prop("value", sizes);
                tempDiv.children(":first").next().next().next().next().next().next().prop("value", materials);
                tempDiv.insertBefore("#lastImageDiv");
            },
            removeProduct: function (self) {
                var imageId = $(self).prev().prev().val();
                packages.v.imageSize = packages.v.imageSize - 1;

                // 删除时，将图片id从缓存span中清除
                var idArray = $('#tempAddImageIds').html().split(',');
                var tempArray = new Array();

                for (var i = 0; i < idArray.length; i++) {
                    if (null != idArray[i] && idArray[i] != '' && idArray[i] != imageId) {
                        tempArray.push(idArray[i]);
                    }
                }

                var result = '';
                for (var m = 0; m < tempArray.length; m++) {
                    result += tempArray[m] + ',';
                }

                $('#tempAddImageIds').html(result);

                $(self).parent().remove();
            },
            getSelectList: function () {
                var brandId = $('#tempBrandId').val();

                // 加载品牌列表
                $sixmac.ajax("common/brandList", null, function (result) {
                    if (null != result) {
                        // 获取返回的省份列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择套餐品牌</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#brandList').append(content);
                    } else {
                        $sixmac.notify("获取品牌信息失败", "error");
                    }

                    if (null != brandId && brandId != '') {
                        $('#brandList').val(brandId);
                    }
                });

                // 加载商品列表
                $sixmac.ajax("common/productList", null, function (result) {
                    if (null != result) {
                        // 获取返回的省份列表信息，并循环绑定到select中
                        var content = "";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#productList').append(content);

                        var productId = $('#productList option:selected').val();
                        packages.fn.findColors(productId);
                        packages.fn.findSizes(productId);
                        packages.fn.findMaterials(productId);
                    } else {
                        $('#productList').append("<option value=''>暂无商品</option>");
                    }
                });
            },
            findColors: function (productId) {
                $('#colorList').html('');

                // 加载商品颜色列表
                $sixmac.ajax("common/findInfoListByProductId", {productId: productId, type: 1}, function (result) {
                    if (null != result) {
                        // 获取返回的列表信息，并循环绑定到select中
                        var content = "";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item + "'>" + item + "</option>";
                        });
                        $('#colorList').append(content);
                    } else {
                        $('#colorList').append("<option value=''>暂无颜色</option>");
                    }
                });
            },
            findSizes: function (productId) {
                $('#sizeList').html('');

                // 加载商品尺寸列表
                $sixmac.ajax("common/findInfoListByProductId", {productId: productId, type: 2}, function (result) {
                    if (null != result) {
                        // 获取返回的列表信息，并循环绑定到select中
                        var content = "";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item + "'>" + item + "</option>";
                        });
                        $('#sizeList').append(content);
                    } else {
                        $('#sizeList').append("<option value=''>暂无尺寸</option>");
                    }
                });
            },
            findMaterials: function (productId) {
                $('#materialList').html('');

                // 加载商品材质列表
                $sixmac.ajax("common/findInfoListByProductId", {productId: productId, type: 3}, function (result) {
                    if (null != result) {
                        // 获取返回的列表信息，并循环绑定到select中
                        var content = "";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item + "'>" + item + "</option>";
                        });
                        $('#materialList').append(content);
                    } else {
                        $('#materialList').append("<option value=''>暂无材质</option>");
                    }
                });
            },
            checkData: function () {
                var flag = true;
                var name = $('#name').val();
                var price = $('#price').val();
                var brandId = $('#brandList option:selected').val();
                var content = editor1.getContent();

                if (null == name || name == '') {
                    $sixmac.notify("请输入套餐名称", "error");
                    flag = false;
                    return;
                }

                if (null == price || price == '') {
                    $sixmac.notify("请输入套餐现价", "error");
                    flag = false;
                    return;
                }

                if (null == brandId || brandId == '') {
                    $sixmac.notify("请选择套餐品牌", "error");
                    flag = false;
                    return;
                }

                if (packages.v.imageSize == 0) {
                    $sixmac.notify("请至少添加一个商品", "error");
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
                    $sixmac.notify("请输入套餐描述", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            subInfo: function () {
                console.log(0);
                // 所有的验证通过后，执行新增操作
                if (packages.fn.checkData()) {
                    $("input[name='tempProductColor']").each(function () {
                        packages.v.tempColors += $(this).val().trim() + ',';
                    });
                    $("input[name='tempProductSize']").each(function () {
                        packages.v.tempSizes += $(this).val().trim() + ',';
                    });
                    $("input[name='tempProductMaterial']").each(function () {
                        packages.v.tempMaterials += $(this).val().trim() + ',';
                    });
                    console.log(1);
                    $.post(_basePath + "backend/packages/save",
                            {
                                "id": $('#packagesId').val(),
                                "name": $('#name').val(),
                                "price": $('#price').val(),
                                "oldPrice": $('#oldPrice').val(),
                                "brandId": $('#brandList option:selected').val(),
                                "labels": $('#labels').val(),
                                "coverId": $("input[type='radio']:checked").prev().val(),
                                "tempAddImageIds": $("#tempAddImageIds").html(),
                                "tempColors": packages.v.tempColors,
                                "tempSizes": packages.v.tempSizes,
                                "tempMaterials": packages.v.tempMaterials,
                                "content": editor1.getContent()
                            },
                            function (data) {
                                if (data > 0) {
                                    window.location.href = _basePath + "backend/packages/index";
                                } else {
                                    $sixmac.notify("操作失败", "error");
                                }
                            });
                }
            },
            goBack: function () {
                window.location.href = "backend/packages/index";
            }
        }
    }

    $(document).ready(function () {
        packages.fn.init();
    });

</script>

</html>