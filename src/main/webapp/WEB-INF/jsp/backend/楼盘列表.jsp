<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>楼盘列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理地产</h1>
                <h4 style="margin-left: 10px;">——楼盘列表</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="javascript:void(0)" class="btn btn-outline btn-primary btn-lg" role="button" onclick="propertyList.fn.toAdd()">新增楼盘</a>
                        <a href="backend/propertys/index" class="btn btn-outline btn-success btn-lg" role="button">返回</a>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">

                        <div class="table-responsive">
                            <input type="hidden" id="parentId" value="${parentId}"/>
                            <table class="table table-striped table-bordered table-hover" id="dataTables">
                                <colgroup>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>楼盘名称</th>
                                    <th>户型数</th>
                                    <th>图标</th>
                                    <th>创建时间</th>
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
                    <input type="hidden" id="propertysId"/>
                    确定删除该楼盘？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="propertyList.fn.subDelInfo()" class="btn btn-primary">确定</button>
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

    var propertyList = {
        v: {
            id: "propertyList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                propertyList.fn.dataTableInit();
            },
            dataTableInit: function () {
                propertyList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/propertys/childList",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "childNum"},
                        {"data": null},
                        {"data": "createTime"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='编辑' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-edit'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='删除' class='btn btn-danger btn-circle delete'>" +
                            "<i class='fa fa-remove'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='上移' class='btn btn-warning btn-circle upTurn'>" +
                            "<i class='fa fa-arrow-up'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='下移' class='btn btn-warning btn-circle downTurn'>" +
                            "<i class='fa fa-arrow-down'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        propertyList.v.list.push(data);
                        if (null != data.cover && data.cover != '') {
                            $('td', row).eq(2).html("<img src='" + data.cover + "' width='60px' height='60px' />");
                        } else {
                            $('td', row).eq(2).html("暂无");
                        }
                        $('td', row).eq(0).css('line-height', '65px');
                        $('td', row).eq(1).css('line-height', '65px');
                        $('td', row).eq(2).css('line-height', '65px');
                        $('td', row).eq(3).css('line-height', '65px');
                        $('td', row).eq(4).css('line-height', '65px');
                    },
                    rowCallback: function (row, data) {
                        var items = propertyList.v.list;
                        var parentId = $('#parentId').val();

                        $('td', row).last().find(".edit").attr("href", 'backend/propertys/addChild?id=' + data.id + '&parentId=' + parentId);

                        $('td', row).last().find(".delete").click(function () {
                            propertyList.fn.delInfo(data.id);
                        });

                        $('td', row).last().find(".upTurn").click(function () {
                            // 上移
                            propertyList.fn.moveTurn(data.id, 0);
                        });

                        $('td', row).last().find(".downTurn").click(function () {
                            // 下移
                            propertyList.fn.moveTurn(data.id, 1);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.parentId = $('#parentId').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            moveTurn: function (propertyId, turn) {
                $sixmac.ajax("backend/propertys/moveProperty", {
                    "propertyId": propertyId,
                    "turn": turn
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        propertyList.v.dTable.ajax.reload(null, false);
                    } else if (result == -1) {
                        $sixmac.notify("已经是第一条了，无法上移", "error");
                    } else if (result == -2) {
                        $sixmac.notify("已经是最后一条了，无法下移", "error");
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            delInfo: function (id) {
                $('#propertysId').val(id);
                $("#delModal").modal("show");
            },
            subDelInfo: function () {
                var propertysId = $('#propertysId').val();

                $sixmac.ajax("backend/propertys/delete", {
                    "propertyId": propertysId
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        propertyList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            toAdd: function () {
                var parentId = $('#parentId').val();
                window.location.href = 'backend/propertys/addChild?parentId=' + parentId;
            }
        }
    }

    $(document).ready(function () {
        propertyList.fn.init();
    });

</script>

</html>