<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>收入列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理收入</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading" style="height: 70px;">

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="orderNum" maxlength="20" placeholder="编号"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="mobile" maxlength="20" placeholder="用户手机"/>
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
                                    <th>订单编号</th>
                                    <th>成交时间</th>
                                    <th>用户手机</th>
                                    <th>用户昵称</th>
                                    <th>订单金额</th>
                                    <th>实付金额</th>
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

    var incomeList = {
        v: {
            id: "incomeList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                incomeList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    incomeList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                incomeList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/income/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "orderNum"},
                        {"data": "payTime"},
                        {"data": "user.mobile"},
                        {"data": "user.nickName"},
                        {"data": "price"},
                        {"data": "realPrice"},
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        incomeList.v.list.push(data);

                    },
                    "fnServerParams": function (aoData) {
                        aoData.orderNum = $('#orderNum').val();
                        aoData.mobile = $('#mobile').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
        }
    }

    $(document).ready(function () {
        incomeList.fn.init();
    });

</script>

</html>