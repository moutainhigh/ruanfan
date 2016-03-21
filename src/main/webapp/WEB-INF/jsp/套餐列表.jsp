<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>商品套餐</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">商品套餐</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="packages/add" class="btn btn-outline btn-primary btn-lg" role="button">新增套餐</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="name" maxlength="20" placeholder="套餐名称"/>
                            </div>
                            <div class="form-group">
                                <label>品牌：</label>
                                <select id="brandList" style="width: 160px;" class="form-control"></select>
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
                                    <th>套餐名称</th>
                                    <th>商品数量</th>
                                    <th>品牌</th>
                                    <th>已售</th>
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
                    <input type="hidden" id="packagesId" />
                    确认删除该商品套餐？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="packagesList.fn.subDelInfo()" class="btn btn-primary">确定</button>
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

    var packagesList = {
        v: {
            id: "packagesList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                packagesList.fn.dataTableInit();

                packagesList.fn.getSelectList();

                // 查询
                $("#c_search").click(function () {
                    packagesList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                packagesList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "packages/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "productNum"},
                        {"data": "brand.name"},
                        {"data": "count"},
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
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        packagesList.v.list.push(data);
                    },
                    rowCallback: function (row, data) {
                        var items = packagesList.v.list;

                        $('td', row).last().find(".edit").attr("href", 'packages/add?id=' + data.id);

                        $('td', row).last().find(".delete").click(function () {
                            // 删除
                            packagesList.fn.delInfo(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.name = $('#name').val();
                        aoData.brandId = $('#brandList option:selected').val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            delInfo: function (id) {
                $('#packagesId').val(id);
                $("#delModal").modal("show");
            },
            subDelInfo: function () {
                var packagesId = $('#packagesId').val();

                $sixmac.ajax("packages/delete", {
                    "packagesId": packagesId
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        packagesList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            getSelectList: function () {
                $sixmac.ajax("common/brandList", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>请选择商品品牌</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#brandList').append(content);
                    } else {
                        $sixmac.notify("获取品牌信息失败", "error");
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        packagesList.fn.init();
    });

</script>

</html>