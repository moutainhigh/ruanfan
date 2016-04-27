<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>灵感图集列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理灵感图集</h1>
                <h4 style="margin-left: 10px;">——灵感图集列表</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="backend/afflatus/add" class="btn btn-outline btn-primary btn-lg" role="button">新增灵感集</a>

                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" id="afflatusName" maxlength="20" placeholder="灵感名称"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="designerName" maxlength="20" placeholder="发布者"/>
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
                                    <th>发布者</th>
                                    <th>认证状态</th>
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
                    <h4 class="modal-title">转为虚拟体验</h4>
                </div>
                <div class="modal-body">
                    <form id="infoForm" method="post" action="backend/afflatus/changeVR" class="form-horizontal" role="form">
                        <input type="hidden" id="hiddenAfflatusId" name="afflatusId"/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">分类:</label>
                            <div class="col-sm-5">
                                <select id="typeList" style="width: 200px;" class="form-control"></select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="afflatusList.fn.subInfo()" class="btn btn-primary">保存</button>
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
                        <div class="form-group">
                            <div class="col-sm-12">
                                <textarea cols="40" rows="5" id="reason" style="resize: none;" class="form-control"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="afflatusList.fn.subCheckInfo()" class="btn btn-primary">保存</button>
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
                        "url": "backend/afflatus/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "type"},
                        {"data": "status"},
                        {"data": "area.name"},
                        {"data": "style.name"},
                        {"data": "designer.nickName"},
                        {"data": "isAuth"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<button type='button' title='认证' style='display: none' class='btn btn-primary btn-circle changeAuth'>" +
                            "<i class='fa fa-check'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +
                            "<a title='查看详情' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-eye'></i>" +
                            "</a>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='转为虚拟体验' class='btn btn-info btn-circle delete'>" +
                            "<i class='fa fa-recycle'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='审核通过' style='display: none' class='btn btn-primary btn-circle checkyes'>" +
                            "<i class='fa fa-check'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +
                            "&nbsp;&nbsp;" +
                            "<button type='button' title='审核不通过' style='display: none' class='btn btn-danger btn-circle checkno'>" +
                            "<i class='fa fa-close'></i>" +
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

                        if (data.isAuth == 0) {
                            $('td', row).eq(6).html("未认证");
                        } else {
                            $('td', row).eq(6).html("已认证");
                        }
                    },
                    rowCallback: function (row, data) {
                        if (data.status == 0) {
                            $('td', row).last().find(".checkyes").css("display", '');
                            $('td', row).last().find(".checkno").css("display", '');
                            $('td', row).last().find(".delete").css("display", 'none');
                        }

                        if (data.status == 2) {
                            $('td', row).last().find(".delete").css("display", 'none');
                        }

                        if (data.isAuth == 0) {
                            $('td', row).last().find(".changeAuth").css("display", '');
                        }

                        $('td', row).last().find(".edit").attr("href", 'backend/afflatus/show?id=' + data.id);

                        $('td', row).last().find(".delete").click(function () {
                            // 转化为虚拟体验
                            afflatusList.fn.changeType(data.id);
                        });

                        $('td', row).last().find(".checkyes").click(function () {
                            // 审核为通过
                            afflatusList.fn.changeCheck(data.id, 1);
                        });

                        $('td', row).last().find(".checkno").click(function () {
                            // 审核为不通过
                            afflatusList.fn.checkNo(data.id);
                        });

                        $('td', row).last().find(".changeAuth").click(function () {
                            // 认证灵感集
                            afflatusList.fn.changeAuth(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.afflatusName = $("#afflatusName").val();
                        aoData.designerName = $("#designerName").val();
                        aoData.status = $("#statusList option:selected").val();
                        aoData.areaId = $("#areaList option:selected").val();
                        aoData.styleId = $("#styleList option:selected").val();
                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            checkNo: function (id) {
                $('#hiddenAfflatusId').val(id);

                $("#checkModal").modal("show");
            },
            subCheckInfo: function () {
                var flag = true;
                var reason = $('#reason').val();
                var id = $('#hiddenAfflatusId').val();

                if (null == reason || reason.trim().length == 0) {
                    $sixmac.notify("请输入驳回原因", "error");
                    flag = false;
                    return;
                }

                if (flag) {
                    $sixmac.ajax("backend/afflatus/changeCheck", {
                        "afflatusId": id,
                        "status": 2,
                        "reason": reason
                    }, function (result) {
                        if (result == 1) {
                            $sixmac.notify("操作成功", "success");
                            $("#checkModal").modal("hide");
                            afflatusList.v.dTable.ajax.reload(null, false);
                        } else {
                            $sixmac.notify("操作失败", "error");
                        }
                    });
                }
            },
            changeType: function (id) {
                $('#hiddenAfflatusId').val(id);

                $('#typeList').val('');
                $("#infoModal").modal("show");
            },
            subInfo: function () {
                var flag = true;
                var typeId = $('#typeList option:selected').val();
                var afflatusId = $('#hiddenAfflatusId').val();

                if (null == typeId || typeId == '') {
                    $sixmac.notify("请选择虚拟体验分类", "error");
                    flag = false;
                    return;
                }

                if (flag) {
                    $sixmac.ajax("backend/afflatus/changeVR", {
                        "afflatusId": afflatusId,
                        "typeId": typeId
                    }, function (result) {
                        if (result == 1) {
                            $sixmac.notify("转换成功", "success");
                            $("#infoModal").modal("hide");
                            afflatusList.v.dTable.ajax.reload(null, false);
                        } else {
                            $sixmac.notify("转换失败", "error");
                        }
                    });
                }
            },
            changeAuth: function (id) {
                $sixmac.ajax("backend/afflatus/changeAuth", {
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
            changeCheck: function (id, status) {
                $sixmac.ajax("backend/afflatus/changeCheck", {
                    "afflatusId": id,
                    "status": status
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
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
                $sixmac.ajax("common/vrtypeList", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>请选择虚拟体验分类</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#typeList').append(content);
                    } else {
                        $sixmac.notify("获取虚拟体验分类信息失败", "error");
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