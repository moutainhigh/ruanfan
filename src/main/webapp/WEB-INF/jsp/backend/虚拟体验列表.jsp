<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>虚拟体验列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理虚拟体验</h1>
                <h4 style="margin-left: 10px;">——虚拟体验列表</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="backend/virtuals/add" class="btn btn-outline btn-primary btn-lg" role="button">新增VR虚拟</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="virtualsName" maxlength="20" placeholder="名称"/>
                            </div>
                            <div class="form-group">
                                <label>区域：</label>
                                <select id="typeList" style="width: 120px;" class="form-control"></select>
                            </div>
                            <div class="form-group">
                                <label>风格：</label>
                                <select id="styleList" style="width: 120px;" class="form-control"></select>
                            </div>
                            <button type="button" id="c_search" class="btn btn-primary btn-sm">搜索</button>
                        </form>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">

                        <div class="table-responsive">

                            <table class="table table-striped table-bordered table-hover" id="dataTables">
                                <colgroup>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>提交时间</th>
                                    <th>区域</th>
                                    <th>风格</th>
                                    <th>认证状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>

                    </div>
                    <!-- /.panel-body -->

                </div>
                <!-- /.panel -->
            </div>
        </div>


    </div>
    <!-- /#page-wrapper -->

    <!-- Modal -->
    <div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">删除提示</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="virtualId" />
                    确定删除该虚拟体验？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="virtualsList.fn.subInfo()" class="btn btn-primary">确定</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- Modal end -->

</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp" %>
</body>

<script type="text/javascript">

    var virtualsList = {
        v: {
            id: "virtualsList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                // 页面加载时，自动加载下拉框
                virtualsList.fn.getSelectList();

                virtualsList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    virtualsList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                virtualsList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/virtuals/list",
                        "type": "POST"
                    },
                    "columns": [
                        /*{"data": "id"},*/
                        {"data": "name"},
                        {"data": "createTime"},
                        {"data": "type.name"},
                        {"data": "style.name"},
                        {"data": "isAuth"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='查看详情' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-eye'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='删除' class='btn btn-danger btn-circle delete'>" +
                            "<i class='fa fa-remove'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='认证' style='display: none' class='btn btn-primary btn-circle changeAuth'>" +
                            "<i class='fa fa-check'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        virtualsList.v.list.push(data);

                        if (data.isAuth == 0) {
                            $('td', row).eq(4).html("未认证");
                        } else {
                            $('td', row).eq(4).html("已认证");
                        }
                    },
                    rowCallback: function (row, data) {
                        if (data.isAuth == 0) {
                            // 已认证的VR虚拟，不显示认证按钮
                            $('td', row).last().find(".changeAuth").css("display", '');
                        }

                        $('td', row).last().find(".edit").attr("href", 'backend/virtuals/add?id=' + data.id);

                        $('td', row).last().find(".delete").click(function () {
                            // 删除
                            virtualsList.fn.delInfo(data.id);
                        });

                        $('td', row).last().find(".changeAuth").click(function () {
                            // 认证VR虚拟
                            virtualsList.fn.changeAuth(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.name = $("#virtualsName").val();
                        aoData.typeId = $("#typeList option:selected").val();
                        aoData.styleId = $("#styleList option:selected").val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            changeAuth: function (id) {
                $sixmac.ajax("backend/virtuals/changeAuth", {
                    "virtualId": id
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        virtualsList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            delInfo: function (id) {
                $('#virtualId').val(id);
                $("#delModal").modal("show");
            },
            subInfo: function () {
                var virtualId = $('#virtualId').val();

                $sixmac.ajax("backend/virtuals/delete", {
                    "virtualId": virtualId,
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        virtualsList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            getSelectList: function () {
                $sixmac.ajax("common/vrtypeList", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>全部</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#typeList').append(content);
                    } else {
                        $sixmac.notify("获取区域信息失败", "error");
                    }
                });
                $sixmac.ajax("common/styleList", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>全部</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#styleList').append(content);
                    } else {
                        $sixmac.notify("获取风格信息失败", "error");
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        virtualsList.fn.init();
    });

</script>

</html>