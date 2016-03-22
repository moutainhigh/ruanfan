<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>编辑广告banner</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">广告banner</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增广告banner</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="bannerForm" method="post" enctype="multipart/form-data" action="backend/banner/save" class="form-horizontal" role="form">
                            <input type="hidden" id="id" name="id" value="${banner.id}">
                            <input type="hidden" id="tempCover" value="${banner.cover}"/>
                            <input type="hidden" id="tempType" value="${banner.type}"/>
                            <input type="hidden" id="tempSourceId" value="${banner.sourceId}"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">封面:</label>
                                <div class="col-sm-3">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;" onchange="banner.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="banner.fn.AddImg()">
                                        <img id="mainPicture" src="${banner.cover}" style="height: 200px; width: 200px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">广告类型:</label>
                                <div class="col-sm-3">
                                    <select id="typeList" style="width: 200px;" class="form-control">
                                        <option value="1">商品</option>
                                        <option value="2">套餐商品</option>
                                        <option value="3">特价商品</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">链接目标:</label>
                                <div class="col-sm-3">
                                    <select id="sourceList" style="width: 200px;" class="form-control"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="banner.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="banner.fn.goBack()">返回</button>
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
    var banner = {
        v: {
            id: "banner",
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
                    $("#showH").text("——编辑广告banner");
                }

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                $('#typeList').change(function () {
                    $('#tempSourceId').val('');

                    banner.fn.getSelectList($(this).val());
                });

                // 加载数据
                banner.fn.loadData();
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    banner.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }

                var typeId = $('#tempType').val();
                if (null != typeId && typeId != '') {
                    $('#typeList').val(typeId);

                    banner.fn.getSelectList(typeId);
                } else {
                    banner.fn.getSelectList(1);
                }
            },
            changeStatus: function () {
                banner.v.mainImageStatus = 1;
            },
            getSelectList: function (typeId) {
                $('#sourceList').html('');
                var sourceId = $('#tempSourceId').val();
                var url = '';

                switch (Number(typeId)) {
                    case 1:
                        // 查询商品列表
                        url = "common/productList";
                        break;
                    case 2:
                        // 查询套餐列表
                        url = "common/packageList";
                        break;
                    case 3:
                        // 查询秒杀列表
                        url = "common/spikeList";
                        break;
                }

                $sixmac.ajax(url, null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>请选择链接目标</option>";
                        jQuery.each(result, function (i, item) {
                            if (item.id == sourceId) {
                                content += "<option selected value='" + item.id + "'>" + item.name + "</option>";
                            } else {
                                content += "<option value='" + item.id + "'>" + item.name + "</option>";
                            }
                        });
                        $('#sourceList').append(content);
                    } else {
                        $sixmac.notify("获取链接目标信息失败", "error");
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
                var sourceId = $('#sourceList option:selected').val();

                if (banner.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传广告图", "error");
                    flag = false;
                    return;
                }

                if (typeId == '') {
                    $sixmac.notify("请选择广告分类", "error");
                    flag = false;
                    return;
                }

                if (sourceId == '') {
                    $sixmac.notify("请选择链接目标", "error");
                    flag = false;
                    return;
                }

                // 所有的验证通过后，执行新增操作
                if (flag) {
                    $("#bannerForm").ajaxSubmit({
                        url: _basePath + "backend/banner/save",
                        dataType: "json",
                        data: {
                            "sourceId": sourceId,
                            "typeId": typeId
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "backend/banner/index";
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                window.location.href = "backend/banner/index";
            }
        }
    }

    $(document).ready(function () {
        banner.fn.init();
    });

</script>

</html>