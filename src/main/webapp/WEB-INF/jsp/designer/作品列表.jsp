<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>灵感集管理</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">灵感集管理</h1>
                <h4 style="margin-left: 10px;">——作品列表</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="name" maxlength="20" placeholder="作品名称"/>
                            </div>
                            <div class="form-group">
                                <select id="statusList" style="width: 120px;" class="form-control">
                                    <option value="">请选择灵感图状态</option>
                                    <option value="0">未操作</option>
                                    <option value="1">已操作</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <select id="areasList" style="width: 120px;" class="form-control">
                                    <option value="">请选择区域</option>
                                    <option value="1">玄关</option>
                                    <option value="2">厨房</option>
                                    <option value="3">书房</option>
                                    <option value="4">客厅</option>
                                    <option value="5">走廊</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <select id="styleList" style="width: 120px;" class="form-control">
                                    <option value="">请选择风格</option>
                                    <option value="0">未操作</option>
                                    <option value="1">已操作</option>
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
                                    <col class="gradeA even" style="width: 4%"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th><input type="checkbox" onclick="$sixmac.checkAll(this)" class="checkall"/></th>
                                    <th>名称</th>
                                    <th>发布时间</th>
                                    <th>评论数量</th>
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

    var reserveList = {
        v: {
            id: "reserveList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                reserveList.fn.dataTableInit();

                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 2,
                    forceParse: 0,
                    showMeridian: 1,
                    format: 'yyyy-mm-dd hh:ii:ss'
                });

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
                        "url": "backend/reserve/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": null},
                        {"data": "name"},
                        {"data": "reseTime"},
                        {"data": "mobile"},
                        {"data": "email"},
                        {"data": "designer.nickName"},
                        {"data": "status"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='查看详情' class='btn btn-primary btn-circle eye'>" +
                            "<i class='fa fa-eye'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<a title='编辑' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-edit'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='删除' style='display: none' class='btn btn-success btn-circle delete'>" +
                            "<i class='fa fa-delete'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        reserveList.v.list.push(data);

                        if (data.status == 0) {
                            $('td', row).eq(0).html("<input type='checkbox' value=" + data.id + ">");
                        } else {
                            $('td', row).eq(0).html("");
                        }

                        if (data.status == 0) {
                            $('td', row).eq(6).html("未操作");
                        } else {
                            $('td', row).eq(6).html("已操作");
                        }

                    },
                    rowCallback: function (row, data) {
                        if (data.status == 0) {
                            $('td', row).last().find(".check").css('display', '');
                        }

                        $('td', row).last().find(".edit").attr("href", 'backend/reserve/detail?id=' + data.id);

                        $('td', row).last().find(".check").click(function () {
                            var checkbox = $('td', row).first().find("input[type='checkbox']");
                            reserveList.fn.deleteRow(checkbox, [data.id]);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.name = $('#name').val();
                        aoData.mobile = $('#mobile').val();
                        aoData.email = $('#email').val();
                        aoData.nickName = $('#designerName').val();
                        aoData.status = $('#statusList option:selected').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            batchDel: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                reserveList.fn.deleteRow(checkBox, ids);
            },
            deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    $sixmac.clearForm($('#infoForm'));
                    $('#confirmSub').click(function () {
                        reserveList.fn.batchConfirm(ids);
                    });
                    $("#infoModal").modal("show");
                }
            },
            batchConfirm: function (ids) {
                if (!$('#infoForm').isValid()) {
                    return false;
                }

                $sixmac.ajax("backend/reserve/batchConfirm", {
                    ids: JSON.stringify(ids),
                    reserveTime: $('#reseTime').val(),
                    reserveAddress: $('#reseAddress').val()
                }, function (result) {
                    if (result > 0) {
                        $sixmac.notify("操作成功", "success");
                        $("#infoModal").modal("hide");
                        reserveList.v.dTable.ajax.reload();
                    } else {
                        $sixmac.notify("操作失败", "error");
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