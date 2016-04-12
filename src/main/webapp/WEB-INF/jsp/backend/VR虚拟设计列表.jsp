<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>VR虚拟设计定制列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理VR虚拟设计</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="backend/vrcustom/add" class="btn btn-outline btn-primary btn-lg" role="button">新增VR虚拟设计</a>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="dataTables">
                                <colgroup>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>广告图</th>
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

        <!-- Modal -->
        <div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">删除提示</h4>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="id"/>
                        确定删除该VR虚拟设计信息？
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" onclick="vrcustomList.fn.subInfo()" class="btn btn-primary">确定</button>
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

    var vrcustomList = {
        v: {
            id: "bannerList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                vrcustomList.fn.dataTableInit();
            },
            dataTableInit: function () {
                vrcustomList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/vrcustom/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": null},
                        {"data": "updateTime"},
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
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        vrcustomList.v.list.push(data);

                        if (data.cover == '') {
                            $('td', row).eq(0).html("暂无");
                        } else {
                            $('td', row).eq(0).html("<img src='" + data.cover + "' width='100px' height='100px' />");
                        }

                        $('td', row).eq(0).css('line-height', '102px');
                        $('td', row).eq(1).css('line-height', '102px');
                        $('td', row).eq(2).css('line-height', '102px');
                    },
                    rowCallback: function (row, data) {
                        var items = vrcustomList.v.list;

                        $('td', row).last().find(".edit").attr("href", 'backend/vrcustom/add?id=' + data.id);

                        $('td', row).last().find(".delete").click(function () {
                            // 删除
                            vrcustomList.fn.delInfo(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {

                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            delInfo: function (id) {
                $('#id').val(id);
                $("#delModal").modal("show");
            },
            subInfo: function () {
                var id = $('#id').val();

                $sixmac.ajax("backend/vrcustom/delete", {
                    "id": id,
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        vrcustomList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        vrcustomList.fn.init();
    });

</script>

</html>