<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>MALL首页图片列表</title>
    <%@ include file="inc/css.jsp" %>
</head>

<body>
<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理MALL首页图</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="dataTables">
                                <colgroup>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>图片</th>
                                    <th>修改时间</th>
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
                    <h4 class="modal-title">查看图片</h4>
                </div>
                <div class="modal-body">
                    <form id="mallPicForm" method="post" enctype="multipart/form-data" action="backend/mallPic/update" class="form-horizontal" role="form">
                        <input type="hidden" id="mallPicId" name="id"/>

                        <div class="form-group">
                            <label class="col-sm-2"></label>
                            <div class="col-sm-10">
                                <input type="file" name="mainImage" id="mainImage" style="display:none;"/>
                                <a href="javascript:void(0);" onclick="mallPicList.fn.AddImg()">
                                    <img id="mainPicture" src="" style="height: 400px; width: 400px; display: inline; margin-bottom: 5px;" border="1"/>
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="mallPicList.fn.subInfo()">保存</button>
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

    var mallPicList = {
        v: {
            id: "mallPicList",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $("#mainImage").uploadPreview({
                    Img: "mainPicture",
                    Width: 200,
                    Height: 170
                });

                mallPicList.fn.dataTableInit();
            },
            AddImg: function () {
                // a标签绑定onclick事件
                $('#mainImage').click();
            },
            dataTableInit: function () {
                mallPicList.v.dTable = $sixmac.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/mallPic/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": null},
                        {"data": "updateTime"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<button type='button' title='编辑' class='btn btn-info btn-circle edit'>" +
                            "<i class='fa fa-recycle'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        mallPicList.v.list.push(data);
                        $('td', row).eq(1).html("<img src='" + data.cover + "' width='100px' height='100px' />");

                        $('td', row).eq(0).css('line-height', '120px');
                        $('td', row).eq(1).css('line-height', '120px');
                        $('td', row).eq(2).css('line-height', '120px');
                        $('td', row).eq(3).css('line-height', '120px');
                    },
                    rowCallback: function (row, data) {
                        $('td', row).last().find(".edit").click(function () {
                            // 编辑
                            $('#mallPicId').val(data.id);
                            $('#mainPicture').prop('src', data.cover);

                            $("#infoModal").modal("show");
                        });
                    },
                    "fnServerParams": function (aoData) {

                    },
                    "fnDrawCallback": function (row) {
                        $sixmac.uiform();
                    }
                });
            },
            subInfo: function () {
                var picSrc = $('#mainPicture').prop('src');

                if (null == picSrc || picSrc == '') {
                    $sixmac.notify("图片不能为空", "error");
                    return;
                }

                $("#mallPicForm").ajaxSubmit({
                    url: _basePath + "backend/mallPic/update",
                    dataType: "json",
                    success: function (result) {
                        if (result > 0) {
                            $sixmac.notify("操作成功", "success");
                            $("#infoModal").modal("hide");
                            mallPicList.v.dTable.ajax.reload(null, false);
                        } else {
                            $sixmac.notify("操作失败", "error");
                        }
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        mallPicList.fn.init();
    });

</script>

</html>