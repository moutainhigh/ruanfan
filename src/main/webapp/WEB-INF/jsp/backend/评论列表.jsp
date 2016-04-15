<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>评论列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理评论</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading" style="height: 70px;">
                        <input type="hidden" id="tempType" value="1"/>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="mobile" maxlength="20" placeholder="账号"/>
                            </div>
                            <div class="form-group">
                                <label>请选择评论对象：</label>
                                <select id="statusList" name="statusList" style="width: 150px;" class="form-control">
                                    <option value="">请选择评论对象</option>
                                    <option value="1">设计师</option>
                                    <option value="2">设计作品</option>
                                    <option value="3">灵感集</option>
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
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>账号</th>
                                    <th>评论时间</th>
                                    <th>评论对象</th>
                                    <th>评论内容</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
                <!-- /.panel -->
            </div>
        </div>
    </div>

    <div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">删除提示</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id"/>
                    确定删除该评论？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="comment.fn.subDelInfo()" class="btn btn-primary">确定</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>

    <!-- /#page-wrapper -->
</div>
<!-- /#wrapper -->

<!-- /#wrapper -->
<%@ include file="inc/footer.jsp" %>
</body>

<script type="text/javascript">

    var comment = {
        v: {
            id: "comment",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                comment.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    comment.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                comment.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/comment/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "user.mobile"},
                        {"data": "createTime"},
                        {"data": "objectType"},
                        {"data": "content"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent":  "<button type='button' title='删除' class='btn btn-danger btn-circle delete'>" +
                            "<i class='fa fa-remove'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        comment.v.list.push(data);

                        if (data.objectType == 1) {
                            $('td', row).eq(2).html("设计师");
                        }
                        if (data.objectType == 2) {
                            $('td', row).eq(2).html("设计作品");
                        }
                        if (data.objectType == 3) {
                            $('td', row).eq(2).html("灵感集");
                        }

                    },
                    rowCallback: function (row, data) {
                        $('td', row).last().find(".delete").click(function () {

                            comment.fn.delInfo(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.mobile = $('#mobile').val();
                        aoData.type = $('#statusList').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            delInfo: function (id) {
                $('#id').val(id);
                $("#delModal").modal("show");
            },
            subDelInfo: function () {
                var id = $('#id').val();

                $sixmac.ajax("backend/comment/delete", {
                    "id": id
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        comment.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
        }
    }

    $(document).ready(function () {
        comment.fn.init();
    });

</script>

</html>