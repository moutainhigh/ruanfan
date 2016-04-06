<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>日志管理</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">日志列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading" style="height: 70px;">

                        <form class="navbar-form navbar-right" role="search" >
                            <div class="form-group" >
                                <input type="text" class="form-control" id="nickName" maxlength="20" placeholder="发布者"/>
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
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th width="10%">用户</th>
                                    <th>创建时间</th>
                                    <th width="40%">内容</th>
                                    <th>转发数</th>
                                    <th>点赞数</th>
                                    <th>评论数</th>
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


<%@ include file="inc/footer.jsp" %>
</body>

<script type="text/javascript">

    var journal = {
        v: {
            id: "journal",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                journal.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    journal.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                journal.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/journal/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "user.nickName"},
                        {"data": "createTime"},
                        {"data": "content"},
                        {"data": "forwardNum"},
                        {"data": "gamsNum"},
                        {"data": "commentNum"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='查看详情' class='btn btn-primary btn-circle edit'>" +
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
                        journal.v.list.push(data);

                    },
                    rowCallback: function (row, data) {

                        $('td', row).last().find(".edit").attr("href", 'backend/journal/detail?id=' + data.id);


                        $('td', row).last().find(".delete").click(function () {

                            journal.fn.delInfo(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.nickName = $('#nickName').val();
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

                $sixmac.ajax("backend/journal/delete", {
                    "id": bookId
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        journal.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            }
        }
    }
    $(document).ready(function () {
        journal.fn.init();
    });

</script>

</html>