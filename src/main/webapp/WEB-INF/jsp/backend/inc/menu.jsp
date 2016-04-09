<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="navbar-default sidebar" role="navigation">
    <input type="hidden" id="sysUserInfo" value="${sessionScope.menu_sysUser}"/>
    <input type="hidden" id="roleId" value="${sessionScope.menu_sysUser.role.id}"/>
    <div class="sidebar-nav navbar-collapse">
        <ul class="nav" id="side-menu">
            <li style="display: none" id="admin_dashboard">
                <a href="backend/dashboard"><i class="fa fa-gear fa-fw"></i>首页</a>
            </li>

            <li>
                <a href="javascript:void(0)" style="display: none" id="admin_allUserManage"><i class="fa fa-beer fa-fw"></i>用户管理<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li style="display: none" id="admin_designerManage">
                        <a href="backend/designers/index"><i class="fa fa-plus-square fa-fw"></i>设计师列表</a>
                    </li>
                    <li style="display: none" id="admin_merchantManage">
                        <a href="backend/merchants/index"><i class="fa fa-cart-plus fa-fw"></i>商户列表</a>
                    </li>
                    <li style="display: none" id="admin_userManage">
                        <a href="backend/users/index"><i class="fa fa-map-marker fa-fw"></i>会员列表</a>
                    </li>
                    <li style="display: none" id="admin_sysUserManage">
                        <a href="backend/sysUser/index"><i class="fa fa-cubes fa-fw"></i>管理人员</a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="javascript:void(0)" style="display: none" id="admin_allProductManage"><i class="fa fa-beer fa-fw"></i>商品管理<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li style="display: none" id="admin_productManage">
                        <a href="backend/product/index"><i class="fa fa-plus-square fa-fw"></i>商品列表</a>
                    </li>
                    <li style="display: none" id="admin_packageManage">
                        <a href="backend/packages/index"><i class="fa fa-cart-plus fa-fw"></i>商品套餐</a>
                    </li>
                    <li style="display: none" id="admin_spikeManage">
                        <a href="backend/spikes/index"><i class="fa fa-map-marker fa-fw"></i>秒杀管理</a>
                    </li>
                    <li style="display: none" id="admin_orderManage">
                        <a href="backend/orders/index1"><i class="fa fa-cubes fa-fw"></i>订单列表</a>
                    </li>
                    <li style="display: none" id="admin_orderInfoManage">
                        <a href="backend/ordersinfo/index"><i class="fa fa-cubes fa-fw"></i>评价列表</a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="javascript:void(0)" style="display: none" id="admin_allAfflatusManage"><i class="fa fa-beer fa-fw"></i>灵感集管理<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li style="display: none" id="admin_afflatusManage">
                        <a href="backend/afflatus/index"><i class="fa fa-plus-square fa-fw"></i>灵感图集</a>
                    </li>
                    <li style="display: none" id="admin_virtualManage">
                        <a href="backend/virtuals/index"><i class="fa fa-cart-plus fa-fw"></i>虚拟体验</a>
                    </li>
                    <li style="display: none" id="admin_magazineManage">
                        <a href="backend/magazine/index"><i class="fa fa-map-marker fa-fw"></i>杂志列表</a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="javascript:void(0)" style="display: none" id="admin_allSortManage"><i class="fa fa-beer fa-fw"></i>分类管理<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li style="display: none" id="admin_productTypeManage">
                        <a href="backend/productType/index"><i class="fa fa-plus-square fa-fw"></i>商品种类</a>
                    </li>
                    <li style="display: none" id="admin_brandManage">
                        <a href="backend/brand/index"><i class="fa fa-cart-plus fa-fw"></i>品牌分类</a>
                    </li>
                    <li style="display: none" id="admin_styleManage">
                        <a href="backend/style/index"><i class="fa fa-map-marker fa-fw"></i>风格分类</a>
                    </li>
                    <li style="display: none" id="admin_areaManage">
                        <a href="backend/afflatusArea/index"><i class="fa fa-cubes fa-fw"></i>灵感图区域</a>
                    </li>
                    <li style="display: none" id="admin_virtualTypeManage">
                        <a href="backend/virtualType/index"><i class="fa fa-cubes fa-fw"></i>虚拟体验分类</a>
                    </li>
                    <li style="display: none" id="admin_propertyManage">
                        <a href="backend/propertys/index"><i class="fa fa-cubes fa-fw"></i>地产类型</a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="javascript:void(0)" style="display: none" id="admin_allManager"><i class="fa fa-beer fa-fw"></i>站长工具<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li style="display: none" id="admin_bannerManage">
                        <a href="backend/banner/index"><i class="fa fa-plus-square fa-fw"></i>广告banner</a>
                    </li>
                    <li style="display: none" id="admin_couponManage">
                        <a href="backend/coupon/index"><i class="fa fa-cart-plus fa-fw"></i>范票</a>
                    </li>
                    <li style="display: none" id="admin_reserveManage">
                        <a href="backend/reserve/index"><i class="fa fa-map-marker fa-fw"></i>预约列表</a>
                    </li>
                    <li style="display: none" id="admin_feedbackManage">
                        <a href="backend/feedback/index"><i class="fa fa-cubes fa-fw"></i>反馈列表</a>
                    </li>
                    <li style="display: none" id="admin_reportManage">
                        <a href="backend/report/index"><i class="fa fa-cubes fa-fw"></i>举报列表</a>
                    </li>
                    <li style="display: none" id="admin_moneyManage">
                        <a href="backend/income/index"><i class="fa fa-cubes fa-fw"></i>收入列表</a>
                    </li>
                    <li style="display: none" id="admin_operatisManage">
                        <a href="backend/operatis/index"><i class="fa fa-cubes fa-fw"></i>操作日志</a>
                    </li>
                    <li style="display: none" id="admin_singlePageManage">
                        <a href="backend/singlepage/index"><i class="fa fa-cubes fa-fw"></i>单页列表</a>
                    </li>
                    <li style="display: none" id="admin_customManage">
                        <a href="backend/custom/index"><i class="fa fa-cubes fa-fw"></i>在线设计定制</a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="javascript:void(0)" style="display: none" id="admin_allMessageManage"><i class="fa  fa-beer fa-fw"></i>消息管理<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li style="display: none" id="admin_messageManage">
                        <a href="backend/message/index"><i class="fa fa-plus-square fa-fw"></i>消息列表</a>
                    </li>
                    <li style="display: none" id="admin_addMessage">
                        <a href="backend/message/add"><i class="fa fa-cart-plus fa-fw"></i>添加消息</a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="javascript:void(0)" style="display: none" id="admin_allJournalManage"><i class="fa  fa-beer fa-fw"></i>日志管理<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li style="display: none" id="admin_journalManage">
                        <a href="backend/journal/index"><i class="fa fa-plus-square fa-fw"></i>日志列表</a>
                    </li>
                    <li style="display: none" id="admin_commentManage">
                        <a href="backend/comment/index"><i class="fa fa-cart-plus fa-fw"></i>评论列表</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
    <!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->

<script type="text/javascript">
    // 根据用户角色和权限列表加载对应的菜单选项
    $(function () {
        // 循环读取权限信息
        var list = ${sessionScope.menu_roleModuleList };
        $.each(list, function (i, item) {
            if ($('#roleId').val() == 1) {
                $('#admin_dashboard').css('display', '');
            }
            if (item.id == 8) {
                $('#admin_designerManage').css('display', '');
                $('#admin_designerManage').parent().prev().css('display', '');
            }
            if (item.id == 9) {
                $('#admin_merchantManage').css('display', '');
                $('#admin_merchantManage').parent().prev().css('display', '');
            }
            if (item.id == 10) {
                $('#admin_userManage').css('display', '');
                $('#admin_userManage').parent().prev().css('display', '');
            }
            if (item.id == 11) {
                $('#admin_sysUserManage').css('display', '');
                $('#admin_sysUserManage').parent().prev().css('display', '');
            }
            if (item.id == 12) {
                $('#admin_productManage').css('display', '');
                $('#admin_productManage').parent().prev().css('display', '');
            }
            if (item.id == 13) {
                $('#admin_packageManage').css('display', '');
                $('#admin_packageManage').parent().prev().css('display', '');
            }
            if (item.id == 14) {
                $('#admin_spikeManage').css('display', '');
                $('#admin_spikeManage').parent().prev().css('display', '');
            }
            if (item.id == 15) {
                $('#admin_orderManage').css('display', '');
                $('#admin_orderManage').parent().prev().css('display', '');
            }
            if (item.id == 16) {
                $('#admin_orderInfoManage').css('display', '');
                $('#admin_orderInfoManage').parent().prev().css('display', '');
            }
            if (item.id == 17) {
                $('#admin_afflatusManage').css('display', '');
                $('#admin_afflatusManage').parent().prev().css('display', '');
            }
            if (item.id == 18) {
                $('#admin_virtualManage').css('display', '');
                $('#admin_virtualManage').parent().prev().css('display', '');
            }
            if (item.id == 19) {
                $('#admin_magazineManage').css('display', '');
                $('#admin_magazineManage').parent().prev().css('display', '');
            }
            if (item.id == 20) {
                $('#admin_productTypeManage').css('display', '');
                $('#admin_productTypeManage').parent().prev().css('display', '');
            }
            if (item.id == 21) {
                $('#admin_brandManage').css('display', '');
                $('#admin_brandManage').parent().prev().css('display', '');
            }
            if (item.id == 22) {
                $('#admin_styleManage').css('display', '');
                $('#admin_styleManage').parent().prev().css('display', '');
            }
            if (item.id == 23) {
                $('#admin_areaManage').css('display', '');
                $('#admin_areaManage').parent().prev().css('display', '');
            }
            if (item.id == 24) {
                $('#admin_virtualTypeManage').css('display', '');
                $('#admin_virtualTypeManage').parent().prev().css('display', '');
            }
            if (item.id == 25) {
                $('#admin_bannerManage').css('display', '');
                $('#admin_bannerManage').parent().prev().css('display', '');
            }
            if (item.id == 26) {
                $('#admin_couponManage').css('display', '');
                $('#admin_couponManage').parent().prev().css('display', '');
            }
            if (item.id == 27) {
                $('#admin_reserveManage').css('display', '');
                $('#admin_reserveManage').parent().prev().css('display', '');
            }
            if (item.id == 28) {
                $('#admin_feedbackManage').css('display', '');
                $('#admin_feedbackManage').parent().prev().css('display', '');
            }
            if (item.id == 29) {
                $('#admin_moneyManage').css('display', '');
                $('#admin_moneyManage').parent().prev().css('display', '');
            }
            if (item.id == 30) {
                $('#admin_operatisManage').css('display', '');
                $('#admin_operatisManage').parent().prev().css('display', '');
            }
            if (item.id == 31) {
                $('#admin_messageManage').css('display', '');
                $('#admin_messageManage').parent().prev().css('display', '');
            }
            if (item.id == 32) {
                $('#admin_journalManage').css('display', '');
                $('#admin_journalManage').parent().prev().css('display', '');
            }
        });
    });
</script>