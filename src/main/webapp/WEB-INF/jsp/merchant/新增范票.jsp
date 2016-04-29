<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>编辑范票</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">范票列表</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增范票</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="couponForm" method="post" enctype="multipart/form-data" action="merchant/coupon/save" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${coupon.id}"/>
                            <input type="hidden" id="tempCover" value="${coupon.cover}"/>
                            <input type="hidden" id="tempType" value="${coupon.type}"/>

                            <c:if test="${null != coupon.id}">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">范票码:</label>
                                    <div class="col-sm-3" style="padding-top: 5.5px;">
                                            ${coupon.couponNum}
                                    </div>
                                </div>
                            </c:if>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">范票名称:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="20" data-rule="required" value="${coupon.name}" placeholder="请输入范票名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">金额:</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="money" name="money" maxlength="20" data-rule="required" value="${coupon.money}" placeholder="请输入金额"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">使用限制:</label>
                                <div class="col-sm-1">
                                    <select id="typeList" style="width: 120px;" class="form-control">
                                        <option value="0">无限制</option>
                                        <option value="1">满减</option>
                                    </select>
                                </div>
                                <div class="col-sm-3" style="display: none">
                                    <input type="text" class="form-control" style="width: 150px;" id="maxMoney" name="maxMoney" maxlength="20" value="${coupon.maxMoney}" placeholder="请输入满减金额"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">封面:</label>
                                <div class="col-sm-3">
                                    <input type="file" name="mainImage" id="mainImage" style="display:none;" onchange="coupon.fn.changeStatus()"/>
                                    <a href="javascript:void(0);" onclick="coupon.fn.AddImg()">
                                        <img id="mainPicture" src="${coupon.cover}" style="height: 200px; width: 200px; display: inline; margin-bottom: 5px;" border="1"/>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">有效期:</label>

                                <div class="col-sm-6">
                                    <input type="text" class="form-control input-append date form_datetime" style="width: 180px;" readonly id="startDate" name="startDate" maxlength="20" value="${coupon.startDate}" placeholder="开始时间">
                                    &nbsp; 至 &nbsp;
                                    <input type="text" class="form-control input-append date form_datetime" style="width: 180px;" readonly id="endDate" name="endDate" maxlength="20" value="${coupon.endDate}" placeholder="结束时间">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary" onclick="coupon.fn.subInfo()">提交</button>
                                    <button type="button" class="btn btn-primary" onclick="coupon.fn.goBack()">返回</button>
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
    var coupon = {
        v: {
            id: "coupon",
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
                    $("#showH").text("——编辑范票");
                }

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 2,
                    forceParse: 0,
                    showMeridian: 1,
                    format: 'yyyy-mm-dd'
                });

                $('#typeList').change(function () {
                    if ($(this).val() == 1) {
                        $('#maxMoney').parent().css('display', '');
                    } else {
                        $('#maxMoney').val('');
                        $('#maxMoney').parent().css('display', 'none');
                    }
                });

                // 加载数据
                coupon.fn.loadData();
            },
            loadData: function () {
                var mainImagePath = $('#mainPicture').attr('src');
                if (null != mainImagePath && mainImagePath != '') {
                    coupon.v.mainImageStatus = 1;
                } else {
                    $('#mainPicture').attr('src', 'static/images/add.jpg');
                }

                var typeId = $('#tempType').val();
                if (typeId != '') {
                    $('#typeList').val(typeId);
                    if (typeId == 1) {
                        $('#maxMoney').parent().css('display', '');
                    }
                }
            },
            changeStatus: function () {
                coupon.v.mainImageStatus = 1;
            },
            AddImg: function () {
                // a标签绑定onclick事件
                $('#mainImage').click();
            },
            checkData: function () {
                var flag = true;
                var name = $('#name').val();
                var money = $('#money').val();
                var type = $('#typeList option:selected').val();
                var maxMoney = $('#maxMoney').val();
                var startDate = $('#startDate').val();
                var endDate = $('#endDate').val();

                if (null == name || name == '') {
                    $sixmac.notify("请输入范票名称", "error");
                    flag = false;
                    return;
                }

                if (null == money || money == '') {
                    $sixmac.notify("请输入范票金额", "error");
                    flag = false;
                    return;
                }

                if (type == 1) {
                    if (null == maxMoney || maxMoney == '') {
                        $sixmac.notify("请输入满减金额", "error");
                        flag = false;
                        return;
                    }
                }

                if (coupon.v.mainImageStatus == 0) {
                    $sixmac.notify("请上传广告图", "error");
                    flag = false;
                    return;
                }

                if (null == startDate || startDate == '') {
                    $sixmac.notify("请输入开始时间", "error");
                    flag = false;
                    return;
                }

                if (null == endDate || endDate == '') {
                    $sixmac.notify("请输入结束时间", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (coupon.fn.checkData()) {
                    $("#couponForm").ajaxSubmit({
                        url: _basePath + "merchant/coupon/save",
                        dataType: "json",
                        data: {
                            "type": $('#typeList option:selected').val()
                        },
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "merchant/coupon/index";
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            goBack: function () {
                window.location.href = "merchant/coupon/index";
            }
        }
    }

    $(document).ready(function () {
        coupon.fn.init();
    });

</script>

</html>