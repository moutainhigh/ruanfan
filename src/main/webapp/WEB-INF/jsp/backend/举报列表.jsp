<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>举报列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">举报列表</h1>
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
                                <input type="text" class="form-control" id="userId" maxlength="20" placeholder="举报人"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="sourceId" maxlength="20" placeholder="举报对象"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="type" maxlength="20" placeholder="预约对象"/>
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
                        {"data": "userId"},
                        {"data": "createTime"},
                        {"data": "mobile"},
                        {"data": "type"},
                        {"data": "sourceId"},
                        {"data": "description"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='删除评论' class='btn btn-primary btn-circle edit'>" +
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

                        if (data.description.length > 10) {
                            $('td', row).eq(6).html('<a href="javascript:void(0)" onclick="reportList.fn.printInfo(' + data.id + ')">' + data.description.substring(0, 10) + '...' + '</a>');
                        } else {
                            $('td', row).eq(6).html('<a href="javascript:void(0)" onclick="reportList.fn.printInfo(' + data.id + ')">' + data.description + '</a>');
                        }

                        if (data.type == 0) {
                            $('td', row).eq(4).html("有害信息");
                        }
                        if (data.type == 1) {
                            $('td', row).eq(4).html("垃圾营销");
                        }
                        if (data.type == 2) {
                            $('td', row).eq(4).html("违法信息");
                        }
                        if (data.type == 3) {
                            $('td', row).eq(4).html("淫秽色情");
                        }
                        if (data.type == 4) {
                            $('td', row).eq(4).html("人身攻击");
                        }
                        if (data.type == 5) {
                            $('td', row).eq(4).html("其他");
                        }

                        if (data.isCut == 1) {
                            $('td', row).eq(7).html("已删除评论");
                        }
                        if (data.isIgnore == 1) {
                            $('td', row).eq(7).html("已忽略");
                        }

                    },
                    rowCallback: function (row, data) {
                        $('td', row).last().find(".edit").click(function () {
                            reportList.fn.updateStatus(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.userId = $('#userId').val();
                        aoData.sourceId = $('#sourceId').val();
                        aoData.type = $('#type').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
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
                        console.log(item.description);
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        reportList.fn.init();
    });

</script>

</html>