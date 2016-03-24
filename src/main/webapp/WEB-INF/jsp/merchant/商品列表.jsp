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
                <h1 class="page-header">商品列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="merchant/product/add" class="btn btn-outline btn-primary btn-lg" role="button">新增商品</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="name" maxlength="20" placeholder="商品名称"/>
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
                                    <col class="gradeA even"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>商品名称</th>
                                    <th>单价</th>
                                    <th>类别</th>
                                    <th>审核状态</th>
                                    <th>上架状态</th>
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
    <div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">提示</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="hiddenProductId"/>
                    确定删除该商品？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="masterProductList.fn.subDelInfo()" class="btn btn-primary">确定</button>
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
                        "url": "merchant/product/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "price"},
                        {"data": "type"},
                        {"data": "status"},
                        {"data": "isAdd"},
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
                            "<button type='button' title='上下架' class='btn btn-circle changeStatus'>" +
                            "<i class='fa fa-recycle'></i>" +
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
                        if (data.isAdd == 1) {
                            $('td', row).eq(4).html("下架");
                            $('td', row).last().find(".changeStatus").prop('title', '上架');
                            $('td', row).last().find(".changeStatus").addClass("btn-success");
                        } else {
                            $('td', row).eq(4).html("上架");
                            $('td', row).last().find(".changeStatus").prop('title', '下架');
                            $('td', row).last().find(".changeStatus").addClass("btn-warning");
                        }

                        $('td', row).last().find(".edit").prop("href", 'merchant/product/add?id=' + data.id);

                        $('td', row).last().find(".delete").click(function () {
                            // 删除
                            masterProductList.fn.delInfo(data.id);
                        });

                        $('td', row).last().find(".changeStatus").click(function () {
                            // 上/下架
                            if (data.isAdd == 0) {
                                masterProductList.fn.changeAdd(data.id, 1);
                            } else {
                                masterProductList.fn.changeAdd(data.id, 0);
                            }
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.name = $('#name').val();
                        aoData.isCheck = $('#checkList option:selected').val();
                        aoData.type = $('#typeList option:selected').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            changeAdd: function (id, isAdd) {
                $sixmac.ajax("merchant/product/changeAdd", {
                    "productId": id,
                    "isAdd": isAdd
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        masterProductList.v.dTable.ajax.reload(null, false);
                    } else if (result == -1) {
                        $sixmac.notify("必须审核通过才能上架", "error");
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            delInfo: function (id) {
                $('#hiddenProductId').val(id);

                $("#delModal").modal("show");
            },
            subDelInfo: function () {
                $sixmac.ajax("merchant/product/delete", {
                    "productId": $('#hiddenProductId').val()
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
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