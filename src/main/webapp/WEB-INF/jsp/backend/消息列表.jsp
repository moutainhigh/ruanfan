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
                <h1 class="page-header">消息列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="backend/message/add" class="btn btn-outline btn-primary btn-lg" role="button">新增消息</a>
                        <a href="javascript:void(0)" onclick="messageList.fn.batchDel()"
                           class="btn btn-outline btn-danger btn-lg" role="button">批量删除</a>

                        <%--<form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="title" maxlength="20" placeholder="标题"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="type" maxlength="20" placeholder="对象"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="description" maxlength="20" placeholder="描述"/>
                            </div>
                            <button type="button" id="c_search" class="btn btn-primary btn-sm">搜索</button>
                        </form>--%>
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
                                </colgroup>
                                <thead>
                                <tr>
                                    <th><input type="checkbox" onclick="$sixmac.checkAll(this)" class="checkall"/></th>
                                    <th>标题</th>
                                    <th>对象</th>
                                    <th>描述</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
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
</div>
<!-- /#wrapper -->

<!-- Modal -->
<div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">删除提示</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="bookId"/>
                确定删除该消息？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" onclick="messageList.fn.subDelInfo()" class="btn btn-primary">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- Modal end -->

<%@ include file="inc/footer.jsp" %>
</body>

<script type="text/javascript">

    var messageList = {
        v: {
            id: "messageList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                messageList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    messageList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                messageList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/message/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": null},
                        {"data": "title"},
                        {"data": "types"},
                        {"data": "description"},
                        {"data": "createTime"},
                        {"data": "updateTime"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='编辑' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-edit'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='删除' class='btn btn-danger btn-circle delete'>" +
                            "<i class='fa fa-minus'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        messageList.v.list.push(data);

                        $('td', row).eq(0).html("<input type='checkbox' value=" + data.id + ">");
                    },
                    rowCallback: function (row, data) {
                        $('td', row).last().find(".edit").attr("href", 'backend/message/add?id=' + data.id);

                        $('td', row).last().find(".delete").click(function () {

                            messageList.fn.delInfo(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.title = $('#title').val();
                        aoData.type = $('#type').val();
                        aoData.description = $('#description').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            delInfo: function (id) {
                $('#bookId').val(id);
                $("#delModal").modal("show");
            },
            subDelInfo: function () {
                var bookId = $('#bookId').val();

                $sixmac.ajax("backend/message/delete", {
                    "id": bookId
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        messageList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            batchDel: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                messageList.fn.deleteRow(checkBox, ids)
            },
            deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    $sixmac.optNotify(function () {
                        $sixmac.ajax("backend/message/batchDel", {ids: JSON.stringify(ids)}, function (result) {
                            if (result > 0) {
                                $sixmac.notify("操作成功", "success");
                                messageList.v.dTable.ajax.reload();
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        })
                    }, '确定删除选中的所有消息？', '确定');
                }
            }
        }
    }

    $(document).ready(function () {
        messageList.fn.init();
    });

</script>

</html>