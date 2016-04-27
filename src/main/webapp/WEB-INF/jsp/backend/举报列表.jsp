<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>管理举报</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理举报</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="javascript:void(0)" onclick="reportList.fn.batchDel()"
                           class="btn btn-outline btn-danger btn-lg" role="button">批量删除</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="userName" maxlength="20" placeholder="举报人"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="sourceName" maxlength="20" placeholder="举报对象"/>
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
                                    <th>举报人</th>
                                    <th>举报时间</th>
                                    <th>电话</th>
                                    <th>举报类型</th>
                                    <th>举报对象</th>
                                    <th>举报详情</th>
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
    <div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">举报内容详情</h4>
                </div>
                <div class="modal-body">
                    <span id="infoSpan"></span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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

    var reportList = {
        v: {
            id: "reportList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                reportList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    reportList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                reportList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/report/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": null},
                        {"data": "user.nickName"},
                        {"data": "createTime"},
                        {"data": "user.mobile"},
                        {"data": "type"},
                        {"data": "comment.user.nickName"},
                        {"data": "content"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='删除评论' class='btn btn-primary btn-circle deleteComment'>" +
                            "<i class='fa fa-edit'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='忽略' class='btn btn-danger btn-circle edit'>" +
                            "<i class='fa fa-minus'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        reportList.v.list.push(data);

                        $('td', row).eq(0).html("<input type='checkbox' value=" + data.id + ">");

                        if (data.content.length > 10) {
                            $('td', row).eq(6).html('<a href="javascript:void(0)" onclick="reportList.fn.printInfo(' + data.id + ')">' + data.content.substring(0, 10) + '...' + '</a>');
                        } else {
                            $('td', row).eq(6).html('<a href="javascript:void(0)" onclick="reportList.fn.printInfo(' + data.id + ')">' + data.content + '</a>');
                        }

                        if (data.isIgnore == 1) {
                            $('td', row).eq(7).html("已忽略");
                        }

                    },
                    rowCallback: function (row, data) {
                        $('td', row).last().find(".deleteComment").click(function () {
                            reportList.fn.deleteComment(data.comment.id);
                        });

                        $('td', row).last().find(".edit").click(function () {
                            reportList.fn.updateStatus(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.userName = $('#userName').val();
                        aoData.sourceName = $('#sourceName').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            deleteComment: function (id) {
                $sixmac.ajax("backend/comment/delete", {
                    "id": id
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        reportList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            updateStatus: function (id) {
                $sixmac.ajax("backend/report/update", {
                    "id": id
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        reportList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            printInfo: function (id) {
                $.each(reportList.v.list, function (i, item) {
                    if (item.id == id) {
                        $('#infoSpan').html(item.content);
                        $("#infoModal").modal("show");
                    }
                });
            },
            batchDel: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                reportList.fn.deleteRow(checkBox, ids)
            },
            deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    $sixmac.optNotify(function () {
                        $sixmac.ajax("backend/report/batchDel", {ids: JSON.stringify(ids)}, function (result) {
                            if (result > 0) {
                                $sixmac.notify("操作成功", "success");
                                reportList.v.dTable.ajax.reload();
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        })
                    }, '确定删除选中的举报信息？', '确定');
                }
            }
        }
    }

    $(document).ready(function () {
        reportList.fn.init();
    });

</script>

</html>