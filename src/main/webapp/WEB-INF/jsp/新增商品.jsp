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
                        <form id="productForm" method="post" enctype="multipart/form-data" action="product/save" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${product.id}">
                            <input type="hidden" id="merchantId" value="${product.merchant.id}">
                            <input type="hidden" id="sortId" value="${product.sort.id}">
                            <input type="hidden" id="tempHead" value="${product.head}"/>
                            <input type="hidden" id="tempTypeId" value="${product.type}"/>

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
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="colors" name="colors" data-rule="required" value="${product.colors}" placeholder="使用空格隔开"/>
                                    <input id="baseColor" style="width: 100px;" class="form-control jscolor" value="ab2567">
                                    <button type="button" class="btn btn-success" onclick="product.fn.confirmColor()">选择颜色</button>
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
                                <div class="col-sm-4">
                                    <!-- 百度富文本编辑框 -->
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <button type="button" class="btn btn-primary" onclick="product.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="product.fn.goBack()">返回</button>
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
    var product = {
        v: {
            id: "product",
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
                    $("#showH").text("——编辑商品");
                }

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

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
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    product.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }

                // 选择商品类型
                var type = $('#tempTypeId').val();
                if (null != type && type != '') {
                    $('#typeList').val(type);
                }
            },
            confirmColor: function () {
                var colorInfo = $('#baseColor').val();
                console.log(colorInfo);
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
                var mobile = $('#mobile').val();
                var password = $('#password').val();
                var nickName = $('#nickName').val();
                var typeId = $('#typeList option:selected').val();
                var cityId = $('#cityList option:selected').val();

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
                    $sixmac.notify("请选择商品类型", "error");
                    flag = false;
                    return;
                }

                if (null == nickName || nickName == '') {
                    $sixmac.notify("请输入昵称", "error");
                    flag = false;
                    return;
                }

                if (product.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传资质证明", "error");
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
                if (product.fn.checkData()) {
                    $("#productForm").ajaxSubmit({
                        url: _basePath + "product/save",
                        dataType: "json",
                        data: {
                            "type": $('#typeList option:selected').val(),
                            "cityId": $('#cityList option:selected').val()
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "product/index";
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
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