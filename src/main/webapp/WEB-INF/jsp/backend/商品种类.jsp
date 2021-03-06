<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>商品种类</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理商品种类</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="javascript:void(0)" class="btn btn-outline btn-primary btn-lg" role="button" onclick="productTypeList.fn.addInfo()">新增分类</a>
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
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>商品数量</th>
                                    <th>图标</th>
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
                    <form id="infoForm" method="post" action="backend/productType/save" class="form-horizontal" role="form" enctype="multipart/form-data">
                        <input type="hidden" id="hiddenProductTypeId" name="productTypeId"/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">分类名称:</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="productTypeName" maxlength="20" placeholder="请输入分类名称"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">图标:</label>
                            <div class="col-sm-5">
                                <input type="file" name="mainImage" id="mainImage" style="display:none;"/>
                                <a href="javascript:void(0);" onclick="productTypeList.fn.AddImg()">
                                    <img id="mainPicture" src="static/images/add.jpg" style="height: 200px; width: 200px; display: inline; margin-bottom: 5px;" border="1"/>
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="productTypeList.fn.subInfo()" class="btn btn-primary">确定</button>
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
                    <input type="hidden" id="productTypeId"/>
                    确定删除该商品种类？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="productTypeList.fn.subDelInfo()" class="btn btn-primary">确定</button>
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

    var productTypeList = {
        v: {
            id: "productTypeList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                productTypeList.fn.dataTableInit();

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });
            },
            dataTableInit: function () {
                productTypeList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/productType/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "productNum"},
                        {"data": null},
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
                        productTypeList.v.list.push(data);
                        if (null != data.url && data.url != '') {
                            $('td', row).eq(2).html("<img src='" + data.url + "' width='60px' height='60px' />");
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
                        var items = productTypeList.v.list;

                        $('td', row).last().find(".edit").click(function () {
                            // 编辑
                            productTypeList.fn.editInfo(data);
                        });

                        $('td', row).last().find(".delete").click(function () {
                            // 删除
                            if (data.productNum > 0) {
                                $sixmac.notify("该分类有关联商品，无法删除", "error");
                            } else {
                                productTypeList.fn.delInfo(data.id);
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
                $('#mainPicture').prop('src', 'static/images/add.jpg');

                $sixmac.clearForm($('#infoForm'));

                $("#infoModal").modal("show");
            },
            editInfo: function (data) {
                $('#hiddenProductTypeId').val(data.id);
                $('#productTypeName').val(data.name);

                if (null == data.url || data.url == '') {
                    $('#mainPicture').prop('src', 'static/images/add.jpg');
                } else {
                    $('#mainPicture').prop('src', data.url);
                }

                $("#infoModal").modal("show");
            },
            AddImg: function () {
                // a标签绑定onclick事件
                $('#mainImage').click();
            },
            delInfo: function (id) {
                $('#productTypeId').val(id);
                $("#delModal").modal("show");
            },
            subDelInfo: function () {
                var productTypeId = $('#productTypeId').val();

                $sixmac.ajax("backend/productType/delete", {
                    "productTypeId": productTypeId
                }, function (result) {
                    if (result == 1) {
                        $sixmac.notify("操作成功", "success");
                        $("#delModal").modal("hide");
                        productTypeList.v.dTable.ajax.reload(null, false);
                    } else {
                        $sixmac.notify("操作失败", "error");
                    }
                });
            },
            subInfo: function () {
                var flag = true;
                var productTypeId = $('#hiddenProductTypeId').val();
                var productName = $('#productTypeName').val();
                var url = $('#mainPicture').prop('src');

                if (null == productName || productName == '') {
                    $sixmac.notify("请输入分类名称", "error");
                    flag = false;
                    return;
                }

                if (null == url || url == '' || url == _basePath + 'static/images/add.jpg') {
                    $sixmac.notify("请上传分类图标", "error");
                    flag = false;
                    return;
                }

                // 所有的验证通过后，执行新增操作
                if (flag) {
                    $("#infoForm").ajaxSubmit({
                        url: _basePath + "backend/productType/save",
                        dataType: "json",
                        data: {
                            "id": productTypeId,
                            "name": productName
                        },
                        success: function (result) {
                            if (result > 0) {
                                $sixmac.notify("操作成功", "success");
                                $("#infoModal").modal("hide");
                                productTypeList.v.dTable.ajax.reload(null, false);
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
        productTypeList.fn.init();
    });

</script>

</html>