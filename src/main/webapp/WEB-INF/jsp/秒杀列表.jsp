<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>秒杀列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">秒杀管理</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="spikes/add" class="btn btn-outline btn-primary btn-lg" role="button">新增秒杀</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="name" maxlength="20" placeholder="秒杀名称"/>
                            </div>
                            <div class="form-group">
                                <label>秒杀状态：</label>
                                <select id="statusList" style="width: 120px;" class="form-control">
                                    <option value="">全部</option>
                                    <option value="0">待开始</option>
                                    <option value="1">进行中</option>
                                    <option value="2">已结束</option>
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
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>秒杀名称</th>
                                    <th>秒杀价格</th>
                                    <th>状态</th>
                                    <th>秒杀时间</th>
                                    <th>已售</th>
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
                        <input type="hidden" id="spikeId" />
                        确定删除该秒杀信息？
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" onclick="spikeList.fn.subInfo()" class="btn btn-primary">确定</button>
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

    var spikeList = {
        v: {
            id: "spikeList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                spikeList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    spikeList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                spikeList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "spikes/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "price"},
                        {"data": "status"},
                        {"data": null},
                        {"data": "count"},
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
                        spikeList.v.list.push(data);

                        if (data.status == 1) {
                            $('td', row).eq(2).html("进行中");
                        } else if (data.status == 2) {
                            $('td', row).eq(2).html("秒杀结束");
                        } else {
                            $('td', row).eq(2).html("待开始");
                        }

                        if (data.startTime == '' && data.endTime == '') {
                            $('td', row).eq(3).html("");
                        }
                        if (data.startTime != '' && data.endTime != '') {
                            $('td', row).eq(3).html(data.startTime + " - " + data.endTime);
                        }
                    },
                    rowCallback: function (row, data) {
                        var items = spikeList.v.list;

                        $('td', row).last().find(".edit").attr("href", 'spikes/add?id=' + data.id);

                        $('td', row).last().find(".delete").click(function () {
                            // 删除
                            spikeList.fn.delInfo(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.name = $('#name').val();
                        aoData.status = $('#statusList option:selected').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            delInfo: function (id) {
                $('#spikeId').val(id);
                $("#delModal").modal("show");
            },
            subInfo: function () {
                var spikeId = $('#spikeId').val();

                $sixmac.ajax("spikes/delete", {
                    "spikeId": spikeId,
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        spikeList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        spikeList.fn.init();
    });

</script>

</html>