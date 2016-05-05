<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>商品列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理商品单品</h1>
                <h4 style="margin-left: 10px;">——商品列表</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="backend/product/add" class="btn btn-outline btn-primary btn-lg" role="button">新增商品</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="name" maxlength="20" placeholder="商品名称"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="nickName" maxlength="20" placeholder="发布者"/>
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
                                <label>商品类别：</label>
                                <select id="typeList" style="width: 120px;" class="form-control">
                                    <option value="">全部</option>
                                    <option value="1">单品</option>
                                    <option value="2">艺术品</option>
                                    <option value="3">设计师品牌</option>
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
                                    <th>商品名称</th>
                                    <th>发布者</th>
                                    <th>类别</th>
                                    <th>状态</th>
                                    <th>提交时间</th>
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
    <div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">驳回原因</h4>
                </div>
                <div class="modal-body">
                    <form method="post" class="form-horizontal" role="form">
                        <input type="hidden" id="hiddenProductId"/>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <textarea cols="40" rows="5" id="reason" style="resize: none;" class="form-control"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="masterProductList.fn.subCheckInfo()" class="btn btn-primary">保存</button>
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

    var masterProductList = {
        v: {
            id: "masterProductList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                masterProductList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    masterProductList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                masterProductList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/product/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "merchant.nickName"},
                        {"data": "type"},
                        {"data": "status"},
                        {"data": "createTime"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": /*"<button type='button' title='推荐到MALL' class='btn btn-info btn-circle editHot'>" +
                            "<i class='fa fa-recycle'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +*/
                            "<a title='编辑' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-edit'></i>" +
                            "</a>" +
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
                        masterProductList.v.list.push(data);

                        if (data.type == 1) {
                            $('td', row).eq(2).html("单品");
                        } else if (data.type == 2) {
                            $('td', row).eq(2).html("艺术品");
                        } else {
                            $('td', row).eq(2).html("设计师品牌");
                        }

                        if (data.isCheck == 0) {
                            $('td', row).eq(3).html("待审核");
                        } else if (data.isCheck == 1) {
                            $('td', row).eq(3).html("审核通过");
                        } else {
                            $('td', row).eq(3).html("审核不通过");
                        }
                    },
                    rowCallback: function (row, data) {
                        var items = masterProductList.v.list;

                        if (data.isCheck == 0) {
                            // 未审核时，显示审核按钮
                            $('td', row).last().find(".checkyes").css("display", '');
                            $('td', row).last().find(".checkno").css("display", '');
                        }

                        //渲染样式
                        if (data.isHot == 0) {
                            $('td', row).last().find(".editHot").addClass("btn-success");
                            $('td', row).last().find(".editHot").attr("title", "推荐到MALL");
                        } else {
                            $('td', row).last().find(".editHot").addClass("btn-danger");
                            $('td', row).last().find(".editHot").attr("title", "取消推荐");
                        }

                        $('td', row).last().find(".edit").attr("href", 'backend/product/add?id=' + data.id);

                        $('td', row).last().find(".editHot").click(function () {
                            // 推荐到MALL or 取消推荐
                            if (data.isHot == 0) {
                                masterProductList.fn.changeHot(data.id, 1);
                            } else {
                                masterProductList.fn.changeHot(data.id, 0);
                            }
                        });

                        $('td', row).last().find(".delete").click(function () {
                            // 删除
                            masterProductList.fn.delInfo(data.id);
                        });

                        $('td', row).last().find(".checkyes").click(function () {
                            // 审核为通过
                            masterProductList.fn.changeCheck(data.id, 1);
                        });

                        $('td', row).last().find(".checkno").click(function () {
                            // 审核为不通过
                            masterProductList.fn.checkNo(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.name = $('#name').val();
                        aoData.merchantName = $('#nickName').val();
                        aoData.isCheck = $('#checkList option:selected').val();
                        aoData.type = $('#typeList option:selected').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            changeHot: function (id, isHot) {
                $sixmac.ajax("backend/product/changeHot", {
                    "productId": id,
                    "isHot": isHot
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#checkModal").modal("hide");
                        masterProductList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            checkNo: function (id) {
                $('#hiddenProductId').val(id);

                $("#checkModal").modal("show");
            },
            subCheckInfo: function () {
                var flag = true;
                var reason = $('#reason').val();
                var id = $('#hiddenProductId').val();

                if (null == reason || reason.trim().length == 0) {
                    $sixmac.notify("请输入驳回原因", "error");
                    flag = false;
                    return;
                }

                if (flag) {
                    $sixmac.ajax("backend/product/changeCheck", {
                        "productId": id,
                        "isCheck": 2,
                        "reason": reason
                    }, function (result) {
                        if (result == 1) {
                            $sixmac.notify("操作成功", "success");
                            $("#checkModal").modal("hide");
                            masterProductList.v.dTable.ajax.reload(null, false);
                        } else {
                            $sixmac.notify("操作失败", "error");
                        }
                    });
                }
            },
            changeCheck: function (id, isCheck) {
                $sixmac.ajax("backend/product/changeCheck", {
                    "productId": id,
                    "isCheck": isCheck
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        masterProductList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            changeStatus: function (id, status) {
                $sixmac.ajax("backend/product/changeStatus", {
                    "productId": id,
                    "status": status
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        masterProductList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        masterProductList.fn.init();
    });

</script>

</html>