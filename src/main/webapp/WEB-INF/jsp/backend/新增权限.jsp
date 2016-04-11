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
                <h1 class="page-header">管理管理人员</h1>
                <h4 style="margin-left: 10px;" id="showH">——新增权限</h4>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="productForm" method="post" action="backend/roles/save"
                              class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="id" name="id" value="${roles.id}"/>
                            <input type="hidden" id="tempType" value="${roleModuleIds}"/>

                            <div class="form-group">
                                <label class="col-sm-1 control-label">角色名称:</label>

                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="name" name="name" maxlength="50"
                                           data-rule="required" value="${roles.name}" placeholder="请输入角色名称"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-1 control-label">权限名称:</label>

                                <div class="col-sm-11">
                                    <table border="1" style="line-height: 30px;">
                                        <tr width="100px">
                                            <th width="6%;" style="text-align: center;">序号</th>
                                            <th width="12%;" style="text-align: center;">功能名称</th>
                                            <th width="80%">

                                            </th>
                                        </tr>
                                        <c:forEach var="n" items="${moduleList}" varStatus="index">
                                            <tr>
                                                <td style="text-align: center;">${index.index + 1}</td>
                                                <td style="text-align: center;">${n.name}</td>
                                                <td>
                                                    <div style="padding-left: 10px; padding-right: 10px;">
                                                        <c:forEach var="m" items="${n.moduleList}">
                                                            <input type="checkbox" name="roles" value="${m.id}" />${m.name}
                                                        </c:forEach>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <a href="javascript:void(0)" type="button" class="btn btn-primary"
                                       onclick="roles.fn.subInfo()">确定添加</a>
                                    <a href="backend/roles/index" type="button" class="btn btn-primary">返回</a>
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
    var roles = {
        v: {
            id: "roles",
            list: [],
            dTable: null,
            types: []
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                if ($("#id").val() != "") {
                    $("#showH").text("——编辑权限");
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

                roles.fn.loadData();
            },
            loadData: function () {
                // 界面加载时，选中复选框
                var id = $('#id').val();
                if (null != id && id != '') {
                    // 回选复选框
                    var tempType = $('#tempType').val();
                    var array = tempType.split(',');
                    for (var i = 0; i < array.length; i++) {
                        $('input:checkbox[name="roles"]').each(function () {
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
                var name = $('#name').val();

                if (null == name || name == '') {
                    $sixmac.notify('角色名称不能为空', "error");
                    flag = false;
                    return;
                }

                $('input:checkbox[name="roles"]').each(function () {
                    if ($(this).prop("checked")) {
                        result = true;
                        return;
                    }
                });

                if (!result) {
                    $sixmac.notify('权限不能为空', "error");
                    return;
                }

                // 到此处的时候，说明前面的校验都通过，此时保存需要提交的复选框的值
                $('input:checkbox[name="roles"]:checked').each(function () {
                    roles.v.types.push($(this).val());
                });

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (roles.fn.checkData()) {
                    $.post(_basePath + "backend/roles/save",
                            {
                                "id": $('#id').val(),
                                "name": $('#name').val(),
                                "types": JSON.stringify(roles.v.types)
                            },
                            function (data) {
                                if (data > 0) {
                                    window.location.href = _basePath + "backend/roles/index";
                                } else {
                                    $sixmac.notify("操作失败", "error");
                                }
                            });
                }
            },
            checkChkBox: function (data) {
                if (data.checked == false) {
                    document.getElementsByName('roles')[0].checked = false;
                }
                var arrChk = document.getElementsByName('roles');
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
                    document.getElementsByName('roles')[0].checked = flag;
                }
            },
            goBack: function () {
                window.location.href = "backend/roles/index";
            }
        }
    }

    $(document).ready(function () {
        roles.fn.init();
    });

</script>

</html>