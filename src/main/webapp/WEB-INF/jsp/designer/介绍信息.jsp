<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>介绍信息</title>
    <%@ include file="inc/css.jsp" %>
</head>
<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">介绍信息</h1>
            </div>
        </div>

        <ul class="nav nav-tabs" id="myTab">
            <li><a href="#panel1" id="descriptionPanel">软装</a></li>
            <li><a href="#panel2" id="descsPanel">个性装修定制</a></li>
        </ul>

        <div class="tab-content">
            <div class="tab-pane" id="panel1">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <form id="infoPlusForm1" method="post" action="designer/saveInfo" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">产品描述:</label>

                                        <div class="col-sm-8">
                                            <!-- 百度富文本编辑框 -->
                                            <script id="container1" name="content" type="text/plain" style="width:100%; height:150px; line-height: 0px;"></script>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                            <button type="button" class="btn btn-primary" onclick="infoPlus.fn.subInfo()">提交</button>
                                        </div>
                                    </div>
                                </form>

                                <span style="display: none;" id="spqq1">${designer.description}</span>
                            </div>
                            <!-- /.panel-body -->

                        </div>
                        <!-- /.panel -->
                    </div>
                </div>
            </div>
            <div class="tab-pane" id="panel2">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <form id="infoPlusForm2" method="post" action="designer/saveInfo" class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">
                                    <input type="hidden" id="designerId" value="${designer.id}">

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">产品描述:</label>

                                        <div class="col-sm-8">
                                            <!-- 百度富文本编辑框 -->
                                            <script id="container2" name="content" type="text/plain" style="width:100%; height:150px; line-height: 0px;"></script>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-6" style="text-align: center">
                                            <button type="button" class="btn btn-primary" onclick="infoPlus.fn.subInfo()">保存</button>
                                        </div>
                                    </div>
                                </form>

                                <span style="display: none;" id="spqq2">${designer.descs}</span>
                            </div>
                            <!-- /.panel-body -->

                        </div>
                        <!-- /.panel -->
                    </div>
                </div>
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
    var id = $("#designerId").val();
    var editor1 = new baidu.editor.ui.Editor();
    editor1.render('container1');
    if (null != id && id != '') {
        editor1.ready(function () {
            this.setContent($("#spqq1").html());
        });
    }

    var editor2 = new baidu.editor.ui.Editor();
    editor2.render('container2');
    if (null != id && id != '') {
        editor2.ready(function () {
            this.setContent($("#spqq2").html());
        });
    }
</script>
<script type="text/javascript">
    var infoPlus = {
        v: {
            id: "infoPlus",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                $('#myTab a:first').tab('show');//初始化显示哪个tab

                $('#myTab a').click(function(e) {
                    e.preventDefault();//阻止a链接的跳转行为
                    $(this).tab('show');//显示当前选中的链接及关联的content
                });

                // 加载数据
                infoPlus.fn.loadData();
            },
            checkData: function () {
                var flag = true;
                var content1 = editor1.getContent();
                var content2 = editor2.getContent();

                if (null == content1 || content1 == '') {
                    $sixmac.notify("请输入软装信息", "error");
                    flag = false;
                    return;
                }

                if (null == content2 || content2 == '') {
                    $sixmac.notify("请输入个性装修定制信息", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (infoPlus.fn.checkData()) {
                    $.post(_basePath + "designer/saveInfo",
                        {
                            "id": $('#designerId').val(),
                            "description": editor1.getContent(),
                            "descs": editor2.getContent()
                        },
                        function (data) {
                            if (data > 0) {
                                $sixmac.notify("操作成功", "success");
                            } else {
                                $sixmac.notify("操作失败", "error");
                            }
                        });
                }
            }
        }
    }

    $(document).ready(function () {
        infoPlus.fn.init();
    });

</script>

</html>