<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>商户列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">商户列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="backend/merchants/add" class="btn btn-outline btn-primary btn-lg" role="button">新增商户</a>
                        <a href="javascript:void(0)" onclick="merchantList.fn.batchDel()" class="btn btn-outline btn-danger btn-lg" role="button">批量删除</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="email" maxlength="20" placeholder="账号"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="nickName" maxlength="20" placeholder="昵称"/>
                            </div>
                            <div class="form-group">
                                <label>状态：</label>
                                <select id="statusList" style="width: 120px;" class="form-control">
                                    <option value="">全部</option>
                                    <option value="0">启用</option>
                                    <option value="1">禁用</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>审核状态：</label>
                                <select id="checkList" style="width: 120px;" class="form-control">
                                    <option value="">全部</option>
                                    <option value="0">待审核</option>
                                    <option value="1">审核通过</option>
                                    <option value="2">审核不通过</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>身份：</label>
                                <select id="typeList" style="width: 120px;" class="form-control">
                                    <option value="">全部</option>
                                    <option value="1">品牌卖家</option>
                                    <option value="2">独立卖家</option>
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
                                    <col class="gradeA odd" style="width: 6%"/>
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
                                    <th>账号</th>
                                    <th>昵称</th>
                                    <th>身份</th>
                                    <th>审核状态</th>
                                    <th>状态</th>
                                    <th>注册时间</th>
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
                    <h4 class="modal-title">提示</h4>
                </div>
                <div class="modal-body" id="infoBody">
                    确定禁用该商户？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="merchantList.fn.subInfo()" class="btn btn-primary">确定</button>
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
                    <input type="hidden" id="merchantId"/>
                    确定删除该商户？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="merchantList.fn.subDelInfo()" class="btn btn-primary">确定</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- Modal end -->

    <!-- Modal -->
    <div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">驳回原因</h4>
                </div>
                <div class="modal-body">
                    <form method="post" class="form-horizontal" role="form">
                        <input type="hidden" id="hiddenMerchantId"/>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <textarea cols="40" rows="5" id="reason" style="resize: none;" class="form-control"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="merchantList.fn.subCheckInfo()" class="btn btn-primary">保存</button>
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

    var merchantList = {
        v: {
            id: "merchantList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                merchantList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    merchantList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                merchantList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/merchants/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": null},
                        {"data": "email"},
                        {"data": "nickName"},
                        {"data": "type"},
                        {"data": "isCheck"},
                        {"data": "status"},
                        {"data": "createTime"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<button type='button' title='禁用' class='btn btn-info btn-circle editStatus'>" +
                            "<i class='fa fa-recycle'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" + "<a title='编辑' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-edit'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='删除' class='btn btn-danger btn-circle delete'>" +
                            "<i class='fa fa-minus'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='审核通过' style='display: none' class='btn btn-primary btn-circle checkyes'>" +
                            "<i class='fa fa-check'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='审核不通过' style='display: none' class='btn btn-danger btn-circle checkno'>" +
                            "<i class='fa fa-close'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        merchantList.v.list.push(data);
                        $('td', row).eq(0).html("<input type='checkbox' value=" + data.id + ">");

                        if (data.type == 1) {
                            $('td', row).eq(3).html("品牌商家");
                        } else {
                            $('td', row).eq(3).html("独立商家");
                        }

                        if (data.isCheck == 0) {
                            $('td', row).eq(4).html("待审核");
                        } else if (data.isCheck == 1) {
                            $('td', row).eq(4).html("审核通过");
                        } else {
                            $('td', row).eq(4).html("审核不通过");
                        }
                    },
                    rowCallback: function (row, data) {
                        var items = merchantList.v.list;

                        if (data.isCheck == 0) {
                            // 未审核时，显示审核按钮
                            $('td', row).last().find(".checkyes").css("display", '');
                            $('td', row).last().find(".checkno").css("display", '');
                        }

                        //渲染样式
                        if (data.status == "0") {
                            $('td', row).eq(5).html('启用');
                            $('td', row).last().find(".editStatus").addClass("btn-danger");
                            $('td', row).last().find(".editStatus").attr("title", "禁用");
                        } else {
                            $('td', row).eq(5).html('禁用');
                            $('td', row).last().find(".editStatus").addClass("btn-success");
                            $('td', row).last().find(".editStatus").attr("title", "启用");
                        }

                        $('td', row).last().find(".edit").attr("href", 'backend/merchants/add?id=' + data.id);

                        $('td', row).last().find(".editStatus").click(function () {
                            // 禁用 or 启用
                            if (data.status == 0) {
                                merchantList.fn.changeStatus(data.id, 1);
                            } else {
                                merchantList.fn.changeStatus(data.id, 0);
                            }
                        });

                        $('td', row).last().find(".delete").click(function () {
                            // 删除
                            merchantList.fn.delInfo(data.id);
                        });

                        $('td', row).last().find(".checkyes").click(function () {
                            // 审核为通过
                            merchantList.fn.changeCheck(data.id, 1);
                        });

                        $('td', row).last().find(".checkno").click(function () {
                            // 审核为不通过
                            merchantList.fn.checkNo(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.email = $('#email').val();
                        aoData.nickName = $('#nickName').val();
                        aoData.status = $('#statusList option:selected').val();
                        aoData.isCheck = $('#checkList option:selected').val();
                        aoData.type = $('#typeList option:selected').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            checkNo: function (id) {
                $('#hiddenMerchantId').val(id);

                $("#checkModal").modal("show");
            },
            subCheckInfo: function () {
                var flag = true;
                var reason = $('#reason').val();
                var id = $('#hiddenMerchantId').val();

                if (null == reason || reason.trim().length == 0) {
                    $sixmac.notify("请输入驳回原因", "error");
                    flag = false;
                    return;
                }

                if (flag) {
                    $sixmac.ajax("backend/merchants/changeCheck", {
                        "merchantId": id,
                        "isCheck": 2,
                        "reason": reason
                    }, function (result) {
                        if (result == 1) {
                            $sixmac.notify("操作成功", "success");
                            $("#checkModal").modal("hide");
                            merchantList.v.dTable.ajax.reload(null, false);
                        } else {
                            $sixmac.notify("操作失败", "error");
                        }
                    });
                }
            },
            changeCheck: function (id, isCheck) {
                $sixmac.ajax("backend/merchants/changeCheck", {
                    "merchantId": id,
                    "isCheck": isCheck
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        merchantList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            changeStatus: function (id, status) {
                $sixmac.ajax("backend/merchants/changeStatus", {
                    "merchantId": id,
                    "status": status
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        merchantList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            delInfo: function (id) {
                $('#merchantId').val(id);
                $("#delModal").modal("show");
            },
            subDelInfo: function () {
                var merchantId = $('#merchantId').val();

                $sixmac.ajax("backend/merchants/delete", {
                    "merchantId": merchantId
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        merchantList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            batchDel: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                merchantList.fn.deleteRow(checkBox, ids)
            },
            deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    $sixmac.optNotify(function () {
                        $sixmac.ajax("backend/merchants/batchDel", {ids: JSON.stringify(ids)}, function (result) {
                            if (result > 0) {
                                $sixmac.notify("操作成功", "success");
                                merchantList.v.dTable.ajax.reload();
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        })
                    }, '确定删除选中的所有商户？', '确定');
                }
            }
        }
    }

    $(document).ready(function () {
        merchantList.fn.init();
    });

</script>

</html>