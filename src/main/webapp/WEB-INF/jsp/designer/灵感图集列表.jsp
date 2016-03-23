<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>灵感图列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">灵感图片</h1>
                <h4 style="margin-left: 10px;">——灵感图列表</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="designer/afflatus/add" class="btn btn-outline btn-primary btn-lg" role="button">新增灵感图片</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="afflatusName" maxlength="20" placeholder="灵感名称"/>
                            </div>
                            <div class="form-group">
                                <label>状态：</label>
                                <select id="statusList" style="width: 120px;" class="form-control">
                                    <option value="">全部</option>
                                    <option value="0">待审核</option>
                                    <option value="1">审核通过</option>
                                    <option value="2">审核不通过</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>区域：</label>
                                <select id="areaList" style="width: 120px;" class="form-control"></select>
                            </div>
                            <div class="form-group">
                                <label>风格：</label>
                                <select id="styleList" style="width: 120px;" class="form-control"></select>
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
                                    <th>名称</th>
                                    <th>类型</th>
                                    <th>状态</th>
                                    <th>区域</th>
                                    <th>风格</th>
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
                    <input type="hidden" id="hiddenAfflatusId" name="afflatusId"/>
                    确定要删除该灵感图么？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="afflatusList.fn.subCheckInfo()" class="btn btn-primary">确定</button>
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

    var afflatusList = {
        v: {
            id: "afflatusList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                // 页面加载时，自动加载下拉框
                afflatusList.fn.getSelectList();

                afflatusList.fn.dataTableInit();

                // 查询
                $("#c_search").click(function () {
                    afflatusList.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                afflatusList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "designer/afflatus/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "type"},
                        {"data": "status"},
                        {"data": "area.name"},
                        {"data": "style.name"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<a title='查看详情' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-eye'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='删除' class='btn btn-danger btn-circle delete'>" +
                            "<i class='fa fa-remove'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='提交审核' class='btn btn-warning btn-circle checkStatus'>" +
                            "<i class='fa fa-recycle'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        afflatusList.v.list.push(data);

                        if (data.type == 1) {
                            $('td', row).eq(1).html("单图");
                        } else {
                            $('td', row).eq(1).html("套图");
                        }

                        if (data.status == 0) {
                            $('td', row).eq(2).html("待审核");
                        } else if (data.status == 1) {
                            $('td', row).eq(2).html("审核成功");
                        } else {
                            $('td', row).eq(2).html("审核失败");
                        }
                    },
                    rowCallback: function (row, data) {
                        $('td', row).last().find(".edit").attr("href", 'designer/afflatus/add?id=' + data.id);

                        $('td', row).last().find(".delete").click(function () {
                            // 删除
                            afflatusList.fn.delInfo(data.id);
                        });

                        $('td', row).last().find(".checkStatus").click(function () {
                            // 提交审核
                            afflatusList.fn.subToCheck(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.afflatusName = $("#afflatusName").val();
                        aoData.status = $("#statusList option:selected").val();
                        aoData.areaId = $("#areaList option:selected").val();
                        aoData.styleId = $("#styleList option:selected").val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            subToCheck: function (id) {
                $sixmac.ajax("designer/afflatus/subToCheck", {
                    "afflatusId": id
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        afflatusList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            delInfo: function (id) {
                $('#hiddenAfflatusId').val(id);

                $("#delModal").modal("show");
            },
            subCheckInfo: function () {
                $sixmac.ajax("designer/afflatus/delete", {
                    "afflatusId": id
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        afflatusList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            getSelectList: function () {
                $sixmac.ajax("common/areaList", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>全部</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#areaList').append(content);
                    } else {
                        $sixmac.notify("获取区域信息失败", "error");
                    }
                });
                $sixmac.ajax("common/styleList", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>全部</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#styleList').append(content);
                    } else {
                        $sixmac.notify("获取风格信息失败", "error");
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        afflatusList.fn.init();
    });

</script>

</html>