<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>预约列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">预约列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">

                        <div class="table-responsive">

                            <table class="table table-striped table-bordered table-hover" id="dataTables">
                                <colgroup>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>姓名</th>
                                    <th>预约时间</th>
                                    <th>预约地址</th>
                                    <th>喜爱风格</th>
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

    var reserveList = {
        v: {
            id: "reserveList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {

                reserveList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    reserveList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                reserveList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "designer/reserve/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "reseTime"},
                        {"data": "reseAddress"},
                        {"data": "styles.name"}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent":null,
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        reserveList.v.list.push(data);
                    },
                    rowCallback: function (row, data) {

                    },
                    "fnServerParams": function (aoData) {

                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        reserveList.fn.init();
    });

</script>

</html>