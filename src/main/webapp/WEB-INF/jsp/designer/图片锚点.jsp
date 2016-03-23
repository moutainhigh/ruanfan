<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>添加标签</title>
    <%@ include file="inc/css.jsp" %>
    <style type="text/css">
        /*模块拖拽*/
        .drag {
            position: absolute;
            width: 150px;
            height: 30px;
            border: 1px solid #ddd;
            background: #FBF2BD;
            text-align: center;
            padding: 5px;
            cursor: move;;
            opacity: 0.7;
            border-radius: 5px;
        }
    </style>
</head>

<body>

<div id="designerWork" class="wrapper">
    <jsp:include page="inc/nav.jsp"/>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">灵感图片</h1>
                <h4 style="margin-left: 10px;" id="showH">——添加标签</h4>
            </div>
        </div>

        <!-- 隐藏信息 -->
        <div id="oldWidth" style="display: none;">${imageInfo.width }</div>
        <div id="oldHeight" style="display: none;">${imageInfo.height }</div>
        <div id="resource_id" style="display: none;">${imageInfo.id }</div>
        <div id="labelId" style="display: none;"></div>
        <div id="height" style="display: none;"></div>
        <div id="width" style="display: none;"></div>
        <div id="left" style="display: none;"></div>
        <div id="top" style="display: none;"></div>
        <div id="proName" style="display: none;"></div>
        <div id="productId" style="display: none;"></div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-success">
                    <div class="panel-heading">图片</div>
                    <div id="panelBody" class="panel-body">
                        <table>
                            <tr>
                                <td>
                                    <img id="mainImg" style="position: relative;" src="${imageInfo.path }"/>
                                    <div id="baseImg"></div>
                                </td>

                                <td>
                                    <!--模块拖拽-->
                                    <div>
                                        <select id="productList" style="position: absolute;left: 800px;top:60px;width: 200px;" class="form-control"></select>
                                        <button type="button" onclick="Label.fn.addLabel()" style="position: absolute;left: 800px;top:120px;" class="btn btn-primary">添加标签</button>
                                    </div>
                                    <div id="tempLabels" style="border: 1px;"></div>
                                </td>
                            </tr>
                        </table>

                        <!-- 隐藏区域--标签 -->
                        <div id="temp" style="display: none;" class="drag" onmousedown="Label.fn.dragMove(this,event)">
                            <div></div>
                            <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 22px; left: 65px;display: none;" onclick="Label.fn.deleteLabel(this)">
                                <img id="pic1" src="static/images/xx.png" style="height: 16px; width: 16px; display: inherit;" border="1"/>
                            </a>
                            <div style="display: none;"></div>
                            <div style="display: none;"></div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<jsp:include page="inc/footer.jsp"/>
</body>
</html>

<script type="text/javascript">
    var x = 0;
    var y = 0;
    var z = 0;
    var topValue = 100;
    var Label = {
        v: {},
        fn: {
            init: function () {
                //设置图片路径和显示高宽
                $("#mainImg").css("width", $("#oldWidth").html() / 2);
                $("#mainImg").css("height", $("#oldHeight").html() / 2);

                // 初始化商品下拉框列表
                Label.fn.getSelectList();

                //初始化已经添加的标签
                Label.fn.getLabels();
            },
            getSelectList: function () {
                $sixmac.ajax("common/productList", null, function (result) {
                    if (null != result && result.length > 0) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#productList').append(content);
                    } else {
                        $('#productList').append("<option value=''>暂无商品</option>");
                    }
                });
            },
            getLabels: function () {
                var labelList = ${labelList };
                if (labelList[0] !== null) {
                    $.each(labelList, function (i, item) {
                        Label.fn.insertLabel(item.labelId, item.leftPoint, item.topPoint, item.name, item.product.id);
                    });
                }
            },
            insertLabel: function (id, leftPoint, topPoint, name, productId) {
                var tempDiv = $("#temp").clone();
                tempDiv.css("display", "block");
                tempDiv.attr("id", id);
                tempDiv.css("left", leftPoint + "px");
                tempDiv.css("top", topPoint + "px");
                tempDiv.css("background", "#ABF2BD");
                tempDiv.children(":first").html(name);
                tempDiv.children(":first").next().next().next().html(productId);

                tempDiv.hover(function () {
                    Label.fn.mouseover($(this));
                }, function () {
                    Label.fn.mouseout($(this));
                });
                $("#baseImg").append(tempDiv);
            },
            deleteLabel: function (self) {
                var labelId = $(self).parent().attr("id");
                // 调用ajax函数实现删除标签操作
                $sixmac.ajax("common/deleteLabel", {
                    labelId: labelId
                }, function (result) {
                    if (result == 1) {
                        $(self).parent().remove();
                    } else {
                        $sixmac.notify("操作失败!", "error");
                    }
                });
            },
            mouseover: function (mouse) {
                $(mouse).children("a").fadeIn(300);
            },
            mouseout: function (mouse) {
                $(mouse).children("a").fadeOut(300);
            },
            addLabel: function () {
                var productId = $('#productList option:selected').val();
                if (null == productId || productId == '') {
                    $sixmac.notify("暂无商品", "error");
                } else {
                    // 添加标签
                    $sixmac.ajax("common/getProductInfo", {
                        productId: productId
                    }, function (result) {
                        if (result != null) {
                            var tempDiv = $("#temp").clone();
                            tempDiv.css("display", "block");
                            tempDiv.attr("id", "tempDiv" + (new Date()).valueOf());
                            tempDiv.css("left", 755 + "px");
                            tempDiv.css("top", topValue + "px");
                            tempDiv.children(":first").html(result.name);
                            $("#tempLabels").append(tempDiv);
                            topValue = topValue + 40;
                            if (topValue > $("#oldHeight").html() / 2) {
                                $("#panelBody").css("padding-bottom", (topValue - $("#oldHeight").html() / 2 - 40) + "px");
                            }

                            // 存产品信息
                            tempDiv.children(":first").next().next().next().html(result.id);
                            // 让所有的克隆出来的
                            tempDiv.hover(function () {
                                Label.fn.mouseover($(this));
                            }, function () {
                                Label.fn.mouseout($(this));
                            });
                        } else {
                            $sixmac.notify("操作失败!", "error");
                        }
                    });
                }
            },
            dragMove: function (self, e) {
                var _x, _y;//鼠标离控件左上角的相对位置
                $("#labelId").html($(self).attr("id"));
                var oldxx = parseInt($(self).css("left"));
                var oldyy = parseInt($(self).css("top"));
                var _move = true;
                _x = e.pageX - parseInt($(self).css("left"));
                _y = e.pageY - parseInt($(self).css("top"));
                $(self).fadeTo(20, 0.5);//点击后开始拖动并透明显示
                $(self).mousemove(function (e) {
                    if (_move) {
                        x = e.pageX - _x;//移动时根据鼠标位置计算控件左上角的绝对位置
                        y = e.pageY - _y;
                        $(self).css({top: y, left: x});//控件新位置
                    }
                });
                $(self).mouseup(function () {
                    if (parseInt($(self).offset().left) > ($("#mainImg").width() + parseInt($("#mainImg").offset().left) - parseInt($(self).width()) - 12) || parseInt($(self).offset().left) < parseInt($("#mainImg").offset().left) || parseInt($(self).offset().top) > ($("#mainImg").height() + parseInt($("#mainImg").offset().top) - parseInt($(self).height()) - 12) || parseInt($(self).offset().top) < parseInt($("#mainImg").offset().top)) {
                        $sixmac.ajax("common/queryLastPosition", {
                            //装视界id
                            objectId: $("#resource_id").html(),
                            //标签id
                            labelId: $("#labelId").html()
                        }, function (result) {
                            if (result.leftPoint != "out") {
                                var oldx = parseInt(result.leftPoint);
                                var oldy = parseInt(result.topPoint);
                                $(self).css({top: oldy, left: oldx});
                            } else {
                                $(self).css({top: oldyy, left: oldxx});
                            }
                        });
                    } else {
                        //改变div颜色
                        $(self).css("background", "#ABF2BD");
                        $(self).css("opacity", "0.7");
                        //保存高度信息
                        var height = parseInt($(self).offset().top) - parseInt($("#mainImg").offset().top) + ($(self).height() + 12) / 2;
                        $("#height").html(height);
                        //保存宽度信息
                        var width = parseInt($(self).offset().left) - parseInt($("#mainImg").offset().left) + ($(self).width() + 12) / 2;
                        $("#width").html(width);
                        $("#left").html(parseInt($(self).css("left")));
                        $("#top").html(parseInt($(self).css("top")));
                        $("#proName").html($(self).children(":first").html());
                        $("#productId").html($(self).children(":first").next().next().next().html());
                        //将标签位置信息保存在数据库
                        if (x != z) {
                            z = x;
                            Label.fn.saveLabelInfo();
                        }
                    }

                    _move = false;
                    $(self).fadeTo("fast", 1);//松开鼠标后停止移动并恢复成不透明
                });
            },
            saveLabelInfo: function () {
                $.ajax({
                    type: "post",
                    async: false,
                    url: _basePath + 'common/saveLabelInfo',
                    data: {
                        //装视界id
                        objectId: $("#resource_id").html(),
                        //标签id
                        labelId: $("#labelId").html(),
                        //标签名称
                        name: $("#proName").html(),
                        //数据来源
                        objectType: 1,
                        //偏移高度(相对于原图)
                        height: $("#height").html() * 2,
                        //偏移宽度(想对于原图)
                        width: $("#width").html() * 2,
                        leftPoint: $("#left").html(),
                        topPoint: $("#top").html(),
                        productId: $("#productId").html()
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result != 1) {
                            $sixmac.notify("操作失败!", "error");
                        }
                    }
                });
            }

        }
    }
    $(document).ready(function () {
        Label.fn.init();
    });
</script>