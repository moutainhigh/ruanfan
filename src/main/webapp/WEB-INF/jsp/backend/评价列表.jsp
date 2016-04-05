<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>商品管理</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">评价列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">

                        <a href="javascript:void(0)" onclick="ordersinfoList.fn.batchDel()"
                           class="btn btn-outline btn-danger btn-lg" role="button">批量删除</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="mobile" maxlength="20" placeholder="账号"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="productName" maxlength="20" placeholder="商品名称"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="orderNum" maxlength="20" placeholder="订单号"/>
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
                                    <th>商品图片</th>
                                    <th>商品名称</th>
                                    <th>订单号</th>
                                    <th>满意度</th>
                                    <th>评价内容</th>
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
                    <input type="hidden" id="bookId"/>
                    确定删除该评价？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="ordersinfoList.fn.subDelInfo()" class="btn btn-primary">确定</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>

</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp" %>
</body>


<script type="text/javascript">

    var ordersinfoList = {
        v: {
            id: "ordersinfoList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                ordersinfoList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    ordersinfoList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                ordersinfoList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/ordersinfo/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": null},
                        {"data": "order.user.mobile"},
                        {"data": "productPath"},
                        {"data": "productName"},
                        {"data": "order.orderNum"},
                        {"data": "star"},
                        {"data": "comment"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent":
                            "<button type='button' title='删除' class='btn btn-danger btn-circle delete'>" +
                            "<i class='fa fa-minus'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        ordersinfoList.v.list.push(data);

                        $('td', row).eq(0).html("<input type='checkbox' value=" + data.id + ">");

                        if (data.description != null && data.description.length > 20) {
                            $('td', row).eq(6).html(data.description.substring(0, 20) + '...');
                        }

                        ordersinfoList.v.list.push(data);
                        if (null != data.productPath && data.productPath != '') {
                            $('td', row).eq(2).html("<img src='" + data.url + "' width='60px' height='60px' />");
                        } else {
                            $('td', row).eq(2).html("暂无");
                        }
                        $('td', row).eq(0).css('line-height', '65px');
                        $('td', row).eq(1).css('line-height', '65px');
                        $('td', row).eq(2).css('line-height', '65px');
                        $('td', row).eq(3).css('line-height', '65px');
                        $('td', row).eq(4).css('line-height', '65px');
                        $('td', row).eq(5).css('line-height', '65px');
                        $('td', row).eq(6).css('line-height', '65px');
                        $('td', row).eq(7).css('line-height', '65px');

                    },
                    rowCallback: function (row, data) {
                        $('td', row).last().find(".delete").click(function () {

                            ordersinfoList.fn.delInfo(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.mobile = $('#mobile').val();
                        aoData.productName = $('#productName').val();
                        aoData.orderNum = $('#orderNum').val();
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

                $sixmac.ajax("backend/ordersinfo/delete", {
                    "id": bookId
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        ordersinfoList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            batchDel: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                ordersinfoList.fn.deleteRow(checkBox, ids)
            },
            deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    $sixmac.optNotify(function () {
                        $sixmac.ajax("backend/ordersinfo/batchDel", {ids: JSON.stringify(ids)}, function (result) {
                            if (result > 0) {
                                $sixmac.notify("操作成功", "success");
                                ordersinfoList.v.dTable.ajax.reload();
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
        ordersinfoList.fn.init();
    });

</script>

</html>