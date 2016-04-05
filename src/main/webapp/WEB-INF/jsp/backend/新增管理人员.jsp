<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>用户管理</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理人员</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增管理人员</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="productForm" method="post" action="backend/sysUser/save"
                              class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${sysUser.id}"/>
                            <input type="hidden" id="tempRoleId" value="${sysUser.role.id}"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">账号:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="account" name="account" maxlength="50"
                                           data-rule="required" value="${sysUser.account}" placeholder="请输入账号"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">密码:</label>

                                <div class="col-sm-4">
                                    <input type="password" class="form-control" id="password" name="password" maxlength="50"
                                           data-rule="required" value="${sysUser.password}" placeholder="请输入密码"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">权限:</label>
                                <div class="col-sm-4">
                                    <select id="sysUserList" style="width: 150px;" class="form-control"></select>
                                </div>

                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <button type="button" class="btn btn-primary" onclick="sysUser.fn.subInfo()">确定添加
                                    </button>
                                    <button type="button" class="btn btn-primary" onclick="sysUser.fn.goBack()">返回
                                    </button>
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
    var sysUser = {
        v: {
            id: "sysUser",
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

                sysUser.fn.getSelectList();
            },
            checkData: function () {
                var flag = true;
                var account = $('#account').val();
                var password = $('#password').val();

                if (null == account || account == '') {
                    $sixmac.notify('账号不能为空', "error");
                    flag = false;
                    return;
                }

                if (null == password || password == '') {
                    $sixmac.notify('密码不能为空', "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (sysUser.fn.checkData()) {
                    $.post(_basePath + "backend/sysUser/save",
                            {
                                "id": $('#id').val(),
                                "account": $('#account').val(),
                                "password": $('#password').val(),
                                "roleId": $('#sysUserList option:selected').val()
                            },
                            function (data) {
                                if (data > 0) {
                                    window.location.href = _basePath + "backend/sysUser/index";
                                } else {
                                    $sixmac.notify("操作失败", "error");
                                }
                            });
                }
            },
            getSelectList: function () {
                $sixmac.ajax("common/roleList", null, function (result) {
                    if (null != result) {
                        // 获取返回的角色列表信息，并循环绑定到label中
                        var content = "<option value=''>请选择权限</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#sysUserList').append(content);
                    } else {
                        $sixmac.notify("获取角色信息失败", "error");
                    }

                    var roleId = $('#tempRoleId').val();
                    if (null != roleId && roleId != '') {
                        $('#sysUserList').val(roleId);
                    }
                });
            },
            goBack: function () {
                window.location.href = "backend/sysUser/index";
            }
        }
    }

    $(document).ready(function () {
        sysUser.fn.init();
    });

</script>

</html>