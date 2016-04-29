<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>反馈列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理反馈</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="javascript:void(0)" onclick="feedbackList.fn.batchDel()"
                           class="btn btn-outline btn-danger btn-lg" role="button">批量删除</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="nickName" maxlength="20" placeholder="姓名"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="mobile" maxlength="20" placeholder="电话"/>
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
                                    <th>姓名</th>
                                    <th>反馈时间</th>
                                    <th>电话</th>
                                    <th>意见类型</th>
                                    <th>图片</th>
                                    <th>意见详情</th>
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

        <!-- Modal -->
        <div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">反馈内容详情</h4>
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
    <!-- /#page-wrapper -->
</div>
<!-- /#wrapper -->


<%@ include file="inc/footer.jsp" %>
</body>

<script type="text/javascript">

    var feedbackList = {
        v: {
            id: "feedbackList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                feedbackList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    feedbackList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                feedbackList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/feedback/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": null},
                        {"data": "user.nickName"},
                        {"data": "createTime"},
                        {"data": "user.mobile"},
                        {"data": "type"},
                        {"data": "path"},
                        {"data": "description"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='标记为已处理' class='btn btn-info btn-circle edit'>" +
                            "<i class='fa fa-check'></i>" +
                            "</a>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        feedbackList.v.list.push(data);

                        $('td', row).eq(0).html("<input type='checkbox' value=" + data.id + ">");

                        if (data.description.length > 10) {
                            $('td', row).eq(6).html('<a href="javascript:void(0)" onclick="feedbackList.fn.printInfo(' + data.id + ')">' + data.description.substring(0, 10) + '...' + '</a>');
                        } else {
                            $('td', row).eq(6).html('<a href="javascript:void(0)" onclick="feedbackList.fn.printInfo(' + data.id + ')">' + data.description + '</a>');
                        }

                        if (null != data.path && data.path != '') {
                            $('td', row).eq(5).html("<img src='" + data.path + "' width='60px' height='60px' />");
                        } else {
                            $('td', row).eq(5).html("暂无");
                        }
                        $('td', row).eq(0).css('line-height', '65px');
                        $('td', row).eq(1).css('line-height', '65px');
                        $('td', row).eq(2).css('line-height', '65px');
                        $('td', row).eq(3).css('line-height', '65px');
                        $('td', row).eq(4).css('line-height', '65px');
                        $('td', row).eq(5).css('line-height', '65px');
                        $('td', row).eq(6).css('line-height', '65px');
                        $('td', row).eq(7).css('line-height', '65px');

                        if (data.status == 1) {
                            $('td', row).eq(7).html("已处理");
                        }
                    },
                    rowCallback: function (row, data) {
                        $('td', row).last().find(".edit").click(function () {
                            feedbackList.fn.updateStatus(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.nickName = $('#nickName').val();
                        aoData.mobile = $('#mobile').val();
                        aoData.name = $('#name').val();
                        aoData.description = $('#description').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            updateStatus: function (id) {
                $sixmac.ajax("backend/feedback/update", {
                    "id": id
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        feedbackList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            printInfo: function (id) {
                $.each(feedbackList.v.list, function (i, item) {
                    if (item.id == id) {
                        $('#infoSpan').html(item.description);
                        $("#infoModal").modal("show");
                    }
                });
            },
            batchDel: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                feedbackList.fn.deleteRow(checkBox, ids)
            },
            deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    $sixmac.optNotify(function () {
                        $sixmac.ajax("backend/feedback/batchDel", {ids: JSON.stringify(ids)}, function (result) {
                            if (result > 0) {
                                $sixmac.notify("操作成功", "success");
                                feedbackList.v.dTable.ajax.reload();
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        })
                    }, '确定删除选中的反馈信息？', '确定');
                }
            }
        }
    }

    $(document).ready(function () {
        feedbackList.fn.init();
    });

</script>

</html>