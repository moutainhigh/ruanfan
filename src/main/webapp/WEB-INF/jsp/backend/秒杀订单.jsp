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
                <h1 class="page-header">管理订单</h1>
                <h4 style="margin-left: 10px;">——秒杀订单列表</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="backend/orders/index1" class="btn btn-outline btn-primary btn-lg" role="button">商品订单</a>
                        <a href="backend/orders/index2" class="btn btn-outline btn-primary btn-lg" role="button">套餐订单</a>
                        <a href="backend/orders/index3" class="btn btn-outline btn-primary btn-lg" role="button">秒杀订单</a>
                        <input type="hidden" id="tempType" value="3"/>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="orderNum" maxlength="20" placeholder="订单号"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="mobile" maxlength="20" placeholder="用户账号"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="nickName" maxlength="20"
                                       placeholder="商户名称"/>
                            </div>
                            <div class="form-group">
                                <label>状态：</label>
                                <select id="statusList" style="width: 120px;" class="form-control">
                                    <option value="">全部</option>
                                    <option value="0">待付款</option>
                                    <option value="1">待发货</option>
                                    <option value="2">待确认</option>
                                    <option value="3">待评价</option>
                                    <option value="4">已完成</option>
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
                                    <col class="gradeA even"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>订单号</th>
                                    <th>用户账号</th>
                                    <th>提交时间</th>
                                    <th>订单金额</th>
                                    <th>状态</th>
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

<!-- /#wrapper -->
<%@ include file="inc/footer.jsp" %>
</body>

<script type="text/javascript">

    var ordersList = {
        v: {
            id: "ordersList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                ordersList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    ordersList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                ordersList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/orders/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "orderNum"},
                        {"data": "user.mobile"},
                        {"data": "createTime"},
                        {"data": "price"},
                        {"data": "status"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='查看详情' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-eye'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='已发货' style='display: none' class='btn btn-success btn-circle check'>" +
                            "<i class='fa fa-check'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        ordersList.v.list.push(data);

                        if (data.status == 0) {
                            $('td', row).eq(4).html("待付款");
                        }
                        if (data.status == 1) {
                            $('td', row).eq(4).html("待发货");
                            $('td', row).last().find(".check").css('display', '');
                        }
                        if (data.status == 2) {
                            $('td', row).eq(4).html("待确认");
                        }
                        if (data.status == 3) {
                            $('td', row).eq(4).html("待评价");
                        }
                        if (data.status == 4) {
                            $('td', row).eq(4).html("已完成");
                        }

                    },
                    rowCallback: function (row, data) {
                        $('td', row).last().find(".edit").attr("href", 'backend/orders/detail?id=' + data.id);

                        $('td', row).last().find(".check").click(function () {
                            // var checkbox = $('td', row).first().find("input[type='checkbox']");
                            // ordersList.fn.deleteRow(checkbox, [data.id]);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.orderNum = $('#orderNum').val();
                        aoData.mobile = $('#mobile').val();
                        aoData.nickName = $('#nickName').val();
                        aoData.type = $('#tempType').val();
                        aoData.status = $('#statusList option:selected').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        ordersList.fn.init();
    });

</script>

</html>