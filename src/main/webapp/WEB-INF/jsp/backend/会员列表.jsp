<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>会员列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理会员</h1>
                <h4 style="margin-left: 10px;">——会员列表</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="backend/users/add" class="btn btn-outline btn-primary btn-lg" role="button">新增会员</a>
                        <a href="javascript:void(0)" onclick="masterusersList.fn.batchDel()" class="btn btn-outline btn-danger btn-lg" role="button">批量禁用</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="mobile" maxlength="20" placeholder="账号"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="nickName" maxlength="20" placeholder="昵称"/>
                            </div>
                            <div class="form-group">
                                <label>状态：</label>
                                <select id="statusList" style="width: 120px;" class="form-control">
                                    <option value="">全部</option>
                                    <option value="0">启用</option>
                                    <option value="1">禁用</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>类型：</label>
                                <select id="typeList" style="width: 120px;" class="form-control">
                                    <option value="">全部</option>
                                    <option value="1">马甲会员</option>
                                    <option value="2">普通会员</option>
                                </select>
                            </div>
                            <button type="button" id="c_search" class="btn btn-primary btn-sm">搜索</button>
                        </form>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">

                        <div class="table-responsive">

                            <table class="table table-striped table-bordered table-hover" id="dataTables">
                                <colgroup>
                                    <col class="gradeA even" style="width: 6%"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th><input type="checkbox" onclick="$sixmac.checkAll(this)" class="checkall"/></th>
                                    <th>账号</th>
                                    <th>昵称</th>
                                    <th>城市</th>
                                    <th>状态</th>
                                    <th>类型</th>
                                    <th>注册时间</th>
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
    <div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">驳回原因</h4>
                </div>
                <div class="modal-body">
                    <form method="post" class="form-horizontal" role="form">
                        <input type="hidden" id="hiddenusersId"/>

                        <div class="form-group">
                            <div class="col-sm-12">
                                <textarea cols="40" rows="5" id="reason" style="resize: none;" class="form-control"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="masterusersList.fn.subCheckInfo()" class="btn btn-primary">保存</button>
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

    var masterusersList = {
        v: {
            id: "masterusersList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                masterusersList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    masterusersList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                masterusersList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/users/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "id"},
                        {"data": "mobile"},
                        {"data": "nickName"},
                        {"data": "city.name"},
                        {"data": "status"},
                        {"data": "type"},
                        {"data": "createTime"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='查看详情' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-eye'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='启用' class='btn btn-success btn-circle changeStatus'>" +
                            "<i class='fa fa-recycle'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        masterusersList.v.list.push(data);
                        $('td', row).eq(0).html("<input type='checkbox' value=" + data.id + ">");

                        if (data.type == 1) {
                            $('td', row).eq(5).html("马甲会员");
                        } else {
                            $('td', row).eq(5).html("普通会员");
                        }
                    },
                    rowCallback: function (row, data) {
                        //渲染样式
                        if (data.status == "0") {
                            $('td', row).eq(4).html('启用');
                            $('td', row).last().find(".changeStatus").addClass("btn-danger");
                            $('td', row).last().find(".changeStatus").attr("title", "禁用");
                        } else {
                            $('td', row).eq(4).html('禁用');
                            $('td', row).last().find(".changeStatus").addClass("btn-success");
                            $('td', row).last().find(".changeStatus").attr("title", "启用");
                        }

                        $('td', row).last().find(".edit").attr("href", 'backend/users/add?id=' + data.id);

                        $('td', row).last().find(".changeStatus").click(function () {
                            // 启用 or 禁用
                            if (data.status == 0) {
                                masterusersList.fn.changeStatus(data.id, 1);
                            } else {
                                masterusersList.fn.changeStatus(data.id, 0);
                            }
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.mobile = $('#mobile').val();
                        aoData.nickName = $('#nickName').val();
                        aoData.status = $('#statusList option:selected').val();
                        aoData.type = $('#typeList option:selected').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            changeStatus: function (id, status) {
                $sixmac.ajax("backend/users/changeStatus", {
                    "userId": id,
                    "status": status
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        masterusersList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            batchDel: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                masterusersList.fn.deleteRow(checkBox, ids)
            },
            deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    $sixmac.optNotify(function () {
                        $sixmac.ajax("backend/users/batchDel", {ids: JSON.stringify(ids)}, function (result) {
                            if (result > 0) {
                                $sixmac.notify("操作成功", "success");
                                masterusersList.v.dTable.ajax.reload();
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        })
                    }, '确定禁用选中的所有用户？', '确定');
                }
            }
        }
    }

    $(document).ready(function () {
        masterusersList.fn.init();
    });

</script>

</html>