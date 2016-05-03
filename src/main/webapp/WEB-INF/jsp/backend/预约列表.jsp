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
                <h1 class="page-header">管理预约</h1>
                <h4 style="margin-left: 10px;">——预约列表</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="javascript:void(0)" class="btn btn-outline btn-primary btn-lg"
                           onclick="reserveList.fn.batchDel()"
                           role="button">批量预约联系</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="name" maxlength="20" placeholder="姓名"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="mobile" maxlength="20" placeholder="电话"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="email" maxlength="20" placeholder="邮箱"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="designerName" maxlength="20"
                                       placeholder="预约对象"/>
                            </div>
                            <div class="form-group">
                                <label>状态：</label>
                                <select id="statusList" style="width: 120px;" class="form-control">
                                    <option value="">全部</option>
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
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th><input type="checkbox" onclick="$sixmac.checkAll(this)" class="checkall"/></th>
                                    <th>姓名</th>
                                    <th>预约时间</th>
                                    <th>电话</th>
                                    <th>邮箱</th>
                                    <th>预约对象</th>
                                    <th>预约类型</th>
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

<div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">预约信息</h4>
            </div>
            <div class="modal-body">
                <form id="infoForm" method="post" class="form-horizontal" role="form">
                    <input type="hidden" id="hiddenreserveId" name="reserveId"/>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">预约时间:</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control input-append date form_datetime"
                                   style="width: 180px;" readonly id="reseTime" name="reseTime" maxlength="20"
                                   data-rule="required" placeholder="请输入预约时间"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">预约地点:</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="reseAddress" maxlength="20" data-rule="required"
                                   placeholder="请输入预约地点"/>
                        </div>
                    </div>
                </form>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" id="confirmSub" class="btn btn-primary">确定
                    </button>
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
                        {"data": "objectName"},
                        {"data": "type"},
                        {"data": "status"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='查看详情' class='btn btn-info btn-circle edit'>" +
                            "<i class='fa fa-eye'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='确认联系' style='display: none' class='btn btn-success btn-circle check'>" +
                            "<i class='fa fa-check'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        reserveList.v.list.push(data);

                        $('td', row).eq(0).html("<input type='checkbox' value=" + data.id + ">");

                        if (data.status == 0) {
                            $('td', row).eq(7).html("未操作");
                        } else {
                            $('td', row).eq(7).html("已操作");
                        }

                        if (data.type == 1) {
                            $('td', row).eq(6).html("设计师");
                        } else {
                            $('td', row).eq(6).html("设计定制套餐");
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