<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="backend/inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="backend/inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>软范注册</title>

    <!-- Bootstrap Core CSS -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="static/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="static/css/sb-admin-2.css" rel="stylesheet">
</head>

<body style="background-image: url('static/images/background.jpg');">

<div id="user" class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">注册软范管理系统</h3>
                </div>
                <div class="panel-body">
                    <form id="registerInfoForm" method="post">
                        <fieldset>
                            <div class="form-group" style="text-align: center;">
                                <input id="designer" type="radio" name="type" checked onclick="registerInfo.fn.designerType()" value="3"/><label for="designer">设计师</label>
                                <input id="merchant" type="radio" name="type" onclick="registerInfo.fn.merchantType()" style="margin-left: 20%;" value="2"/><label for="merchant">商家</label>
                            </div>
                            <div class="form-group" id="changeDiv">
                                <input class="form-control" placeholder="手机号" data-rule="required;remote[login/mobileCheck];length[2~20]" maxlength="20" name="mobile" type="text"/>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="昵称" data-rule="required" maxlength="20" name="nickName" type="text"/>
                            </div>
                            <div class="form-group">
                                <input id="password" class="form-control" placeholder="密码" data-rule="密码: required; !digits; length[6~15]" maxlength="15" data-msg-digits="{0}不能使用纯数字" data-msg-length="{0}请输入6~15位" name="password" type="password"/>
                            </div>
                            <div class="form-group">
                                <input data-rule="确认密码: required;match(password);" class="form-control" placeholder="确认密码" data-rule="required" type="password"/>
                            </div>
                            <a href="javascript:void(0)" id="save" class="btn btn-lg btn-success btn-block" role="button">注册</a>
                            <a href="login" id="back" class="btn btn-lg btn-success btn-block" role="button">返回</a>
                        </fieldset>
                    </form>

                    <input class="form-control" placeholder="手机号" data-rule="required;remote[login/mobileCheck];length[2~20]" style="display: none" maxlength="20" name="mobile" type="text" id="tempMobile"/>
                    <input class="form-control" placeholder="邮箱" data-rule="required;remote[login/emailCheck];email" style="display: none" maxlength="20" name="email" type="text" id="tempEmail"/>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- jQuery Version 1.11.0 -->
<script src="static/js/jquery-1.11.0.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="static/js/bootstrap.min.js"></script>

<script src="static/js/plugins/jquery.form.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="static/js/plugins/metisMenu/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="static/js/sb-admin-2.js"></script>

<script src="static/js/plugins/notify-bootstap.min.js"></script>

<script src="static/js/plugins/validator-0.7.3/jquery.validator.js"></script>

<script src="static/js/plugins/validator-0.7.3/local/zh_CN.js"></script>
</body>

<script type="text/javascript">
    var registerInfo = {
        v: {
            id: "user"
        },
        fn: {
            init: function () {
                $("#save").click(function () {
                    if (!$('#registerInfoForm').isValid()) {
                        return false;
                    }

                    $("#registerInfoForm").ajaxSubmit({
                        url: _basePath + "login/registerInfo",
                        dataType: "json",
                        success: function (result) {
                            if (result > 0) {
                                window.location.href = _basePath + "login";
                            } else {
                                alert("操作失败");
                            }
                        }
                    });
                });
            },
            designerType: function () {
                $('#changeDiv').html('');
                var mobileInput = $('#tempMobile').clone();
                mobileInput.prop('id', '');
                mobileInput.css('display', '');
                $('#changeDiv').append(mobileInput);
            },
            merchantType: function () {
                $('#changeDiv').html('');
                var emailInput = $('#tempEmail').clone();
                emailInput.prop('id', '');
                emailInput.css('display', '');
                $('#changeDiv').append(emailInput);
            }
        }
    };

    $(document).ready(function () {
        registerInfo.fn.init();
    });
</script>

</html>