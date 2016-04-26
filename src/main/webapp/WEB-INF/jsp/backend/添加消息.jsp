<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>消息管理</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理消息</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增消息</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="productForm" method="post" action="backend/message/save"
                              class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="messageId" name="id" value="${message.id}"/>
                            <input type="hidden" id="tempType" value="${message.types}"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">消息名称:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="title" name="title" maxlength="50"
                                           data-rule="required" value="${message.title}" placeholder="请输入消息名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">发送对象:</label>

                                <div class="col-sm-4">
                                    <p>
                                        <input type="checkbox" name="sendto"
                                               style="vertical-align: middle; margin-top: 0px;"
                                               id="sendto_alls" value="全部"/> <label for="sendto_alls">全部</label>
                                    </p>
                                    <p>
                                        <input type="checkbox" name="sendto"
                                               style="vertical-align: middle; margin-top: 0px;"
                                               onclick="message.fn.checkChkBox(this)" id="sendto_users"
                                               value="设计师"/> <label for="sendto_users">设计师</label>
                                    </p>
                                    <p>
                                        <input type="checkbox" name="sendto"
                                               style="vertical-align: middle; margin-top: 0px;"
                                               onclick="message.fn.checkChkBox(this)" id="sendto_company"
                                               value="商户"/> <label for="sendto_company">商户</label>
                                    </p>
                                    <p>
                                        <input type="checkbox" name="sendto"
                                               style="vertical-align: middle; margin-top: 0px;"
                                               onclick="message.fn.checkChkBox(this)" id="sendto_mall"
                                               value="用户"/> <label for="sendto_mall">用户</label>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">消息简介:</label>
                                <div class="col-sm-4">
                                    <textarea cols="30" rows="4" class="form-control" maxlength="25" placeholder="最多25个字" name="desc" id="desc">${message.desc}</textarea>
                                </div>
                            </div>

                            <span style="display: none;" id="spqq">${message.description}</span>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">消息详情:</label>
                                <div class="col-sm-6">
                                    <!-- 百度富文本编辑框 -->
                                    <script id="container" name="content" type="text/plain"
                                            style="width:100%; height:150px; line-height: 0px;"></script>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <button type="button" class="btn btn-primary" onclick="message.fn.subInfo()">提交
                                    </button>
                                    <button type="button" class="btn btn-primary" onclick="message.fn.goBack()">返回
                                    </button>
                                </div>
                            </div>
                        </form>

                        <form id="tempImageForm" method="post" action="common/addTempImage"
                              enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="file" name="tempImage" id="tempImage" data-rule="required"
                                   style="display:none;" onchange="product.fn.saveTempImage()"/>
                        </form>

                        <div id="tempDiv"
                             style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 15px;">
                            <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                            <input name="imageIdTemp" type="hidden"/>
                            <a href="javascript:void(0);"
                               style="float: none; z-index: 10; position: relative; bottom: 203px; left: 184px; display: none;"
                               class="axx" onclick="product.fn.deleteImage(this)">
                                <img id="pic" src="static/images/xx.png"
                                     style="height: 16px; width: 16px; display: inline;" border="1"/>
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
    var id = $("#messageId").val();
    var editor1 = new baidu.editor.ui.Editor();
    editor1.render('container');
    if (null != id && id != '') {
        editor1.ready(function () {
            this.setContent($("#spqq").html());
        });
    }
</script>
<script type="text/javascript">
    var message = {
        v: {
            id: "message",
            list: [],
            dTable: null,
            mainImageStatus: 0,
            imageSize: 0,
            types: ""
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                if ($("#productId").val() != "") {
                    $("#showH").text("——编辑消息");
                }

                $('#sendto_alls').click(function () {
                    if (this.checked) {
                        $(":checkbox").each(function () {
                            this.checked = true;
                        });
                    } else {
                        $(":checkbox").each(function () {
                            this.checked = false;
                        });
                    }
                });

                message.fn.loadData();
            },
            loadData: function () {
                // 界面加载时，选中复选框
                var messageId = $('#messageId').val();
                if (null != messageId && messageId != '') {
                    // 回选复选框
                    var tempType = $('#tempType').val();
                    var array = tempType.split(',');
                    for (var i = 0; i < array.length; i++) {
                        $('input:checkbox[name="sendto"]').each(function () {
                            if ($(this).val() == array[i]) {
                                $(this).prop("checked", true);
                            }
                        });
                    }
                }
            },
            checkData: function () {
                var flag = true;
                var result = false;
                var title = $('#title').val();
                var desc = $('#desc').val();
                var content = editor1.getContent();

                if (null == title || title == '') {
                    $sixmac.notify('消息名称不能为空', "error");
                    flag = false;
                    return;
                }

                $('input:checkbox[name="sendto"]').each(function () {
                    if ($(this).prop("checked")) {
                        result = true;
                        return;
                    }
                });

                if (!result) {
                    $sixmac.notify('发送对象不能为空', "error");
                    return;
                }

                if (null == desc || desc == '') {
                    $sixmac.notify('消息简介不能为空', "error");
                    flag = false;
                    return;
                }

                if (null == content || content == '') {
                    $sixmac.notify('消息详情不能为空', "error");
                    flag = false;
                    return;
                }

                // 到此处的时候，说明前面的校验都通过，此时保存需要提交的复选框的值
                $('input:checkbox[name="sendto"]:checked').each(function () {
                    message.v.types += $(this).val() + ',';
                });

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (message.fn.checkData()) {
                    $.post(_basePath + "backend/message/save",
                            {
                                "id": $('#messageId').val(),
                                "title": $('#title').val(),
                                "types": message.v.types,
                                "desc": $('#desc').val(),
                                "description": editor1.getContent()
                            },
                            function (data) {
                                if (data > 0) {
                                    window.location.href = _basePath + "backend/message/index";
                                } else {
                                    $sixmac.notify("操作失败", "error");
                                }
                            });
                }
            },
            checkChkBox: function (data) {
                if (data.checked == false) {
                    document.getElementsByName('sendto')[0].checked = false;
                }
                var arrChk = document.getElementsByName('sendto');
                var flag = true;
                for (var i = 0; i < arrChk.length; i++) {
                    if (i == 0) {
                        continue;
                    } else {
                        if (arrChk[i].checked == false) {
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    document.getElementsByName('sendto')[0].checked = flag;
                }
            },
            goBack: function () {
                window.location.href = "backend/message/index";
            }
        }
    }

    $(document).ready(function () {
        message.fn.init();
    });

</script>

</html>