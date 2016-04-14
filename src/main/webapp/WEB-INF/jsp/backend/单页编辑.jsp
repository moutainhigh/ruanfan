<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>单页编辑</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理单页</h1>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form id="productForm" method="post" action="backend/singlepage/save"
                              class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                            <input type="hidden" id="singlepageId" name="id" value="${singlepage.id}"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">单页名称:</label>

                                <div class="col-sm-4" style="padding-top: 5.5px;">
                                    “范”介绍
                                </div>
                            </div>

                            <span style="display: none;" id="spqq">${singlepage.content}</span>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">单页详情:</label>
                                <div class="col-sm-4">
                                    <!-- 百度富文本编辑框 -->
                                    <script id="container" name="content" type="text/plain"
                                            style="width:100%; height:150px; line-height: 0px;"></script>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                    <button type="button" class="btn btn-primary" onclick="singlepage.fn.subInfo()">确认编辑
                                    </button>
                                    <button type="button" class="btn btn-primary" onclick="singlepage.fn.goBack()">返回
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!-- /.panel-body -->

                </div>
                <!-- /.panel -->
            </div>
        </div>

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp" %>

</body>

<!-- 实例化编辑器 -->
<script type="text/javascript">
    var editor1 = new baidu.editor.ui.Editor();
    editor1.render('container');
    editor1.ready(function () {
        this.setContent($("#spqq").html());
    });
</script>
<script type="text/javascript">
    var singlepage = {
        fn: {
            checkData: function () {
                var flag = true;
                var content = editor1.getContent();

                if (null == content || content == '') {
                    $sixmac.notify('消息详情不能为空', "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (singlepage.fn.checkData()) {
                    $.post(_basePath + "backend/singlepage/save",
                            {
                                "id": $('#singlepageId').val(),
                                "content": editor1.getContent()
                            },
                            function (data) {
                                if (data > 0) {
                                    $sixmac.notify("操作成功", "success");
                                } else {
                                    $sixmac.notify("操作失败", "error");
                                }
                            });
                }
            },
            goBack: function () {
                window.location.href = "backend/singlepage/index";
            }
        }
    }
</script>
</html>