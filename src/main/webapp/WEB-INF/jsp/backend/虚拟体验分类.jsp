<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>虚拟体验分类</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">虚拟体验分类</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="javascript:void(0)" class="btn btn-outline btn-primary btn-lg" role="button" onclick="virtualTypeList.fn.addInfo()">新增分类</a>
                    </div>
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
                                    <th>名称</th>
                                    <th>商品数量</th>
                                    <th>更新时间</th>
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
                    <h4 class="modal-title">分类信息</h4>
                </div>
                <div class="modal-body">
                    <form id="infoForm" method="post" action="backend/virtualType/save" class="form-horizontal" role="form">
                        <input type="hidden" id="hiddenvirtualTypeId" name="virtualTypeId"/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">分类名称:</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="virtualTypeName" maxlength="20" placeholder="请输入分类名称"/>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="virtualTypeList.fn.subInfo()" class="btn btn-primary">确定</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- Modal end -->

    <!-- Modal -->
    <div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">删除提示</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="virtualTypeId"/>
                    确定删除该虚拟体验分类？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="virtualTypeList.fn.subDelInfo()" class="btn btn-primary">确定</button>
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

    var virtualTypeList = {
        v: {
            id: "virtualTypeList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                virtualTypeList.fn.dataTableInit();
            },
            dataTableInit: function () {
                virtualTypeList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/virtualType/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "productNum"},
                        {"data": "updateTime"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<button type='button' title='编辑' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-edit'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='删除' class='btn btn-danger btn-circle delete'>" +
                            "<i class='fa fa-remove'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        virtualTypeList.v.list.push(data);
                    },
                    rowCallback: function (row, data) {
                        var items = virtualTypeList.v.list;

                        $('td', row).last().find(".edit").click(function () {
                            // 编辑
                            virtualTypeList.fn.editInfo(data);
                        });

                        $('td', row).last().find(".delete").click(function () {
                            // 删除
                            if (data.productNum > 0) {
                                $sixmac.notify("该分类有关联物品，无法删除", "error");
                            } else {
                                virtualTypeList.fn.delInfo(data.id);
                            }
                        });
                    },
                    "fnServerParams": function (aoData) {

                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            addInfo: function () {
                $sixmac.clearForm($('#infoForm'));

                $("#infoModal").modal("show");
            },
            editInfo: function (data) {
                $('#hiddenvirtualTypeId').val(data.id);
                $('#virtualTypeName').val(data.name);

                $("#infoModal").modal("show");
            },
            delInfo: function (id) {
                $('#virtualTypeId').val(id);
                $("#delModal").modal("show");
            },
            subDelInfo: function () {
                var virtualTypeId = $('#virtualTypeId').val();

                $sixmac.ajax("backend/virtualType/delete", {
                    "virtualTypeId": virtualTypeId
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        virtualTypeList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            subInfo: function () {
                var flag = true;
                var virtualTypeId = $('#hiddenvirtualTypeId').val();
                var productName = $('#virtualTypeName').val();

                if (null == productName || productName == '') {
                    $sixmac.notify("请输入分类名称", "error");
                    flag = false;
                    return;
                }

                // 所有的验证通过后，执行新增操作
                if (flag) {
                    $("#infoForm").ajaxSubmit({
                        url: _basePath + "backend/virtualType/save",
                        dataType: "json",
                        data: {
                            "id": virtualTypeId,
                            "name": productName
                        },
                        success: function (result) {
                            if (result > 0) {
                                $sixmac.notify("操作成功", "success");
                                $("#infoModal").modal("hide");
                                virtualTypeList.v.dTable.ajax.reload(null, false);
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        }
                    });
                }
            }
        }
    }

    $(document).ready(function () {
        virtualTypeList.fn.init();
    });

</script>

</html>