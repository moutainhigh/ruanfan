<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="navbar-default sidebar" role="navigation">
    <input type="hidden" id="sysUserInfo" value="${sessionScope.menu_sysUser}"/>
    <input type="hidden" id="roleId" value="${sessionScope.menu_sysUser.role.id}"/>
    <div class="sidebar-nav navbar-collapse">
        <ul class="nav" id="side-menu">
            <c:if test="${sessionScope.menu_sysUser.role.id == 1}">
                <li>
                    <a href="backend/dashboard"><i class="fa fa-gear fa-fw"></i>首页</a>
                </li>
            </c:if>

            <c:forEach var="n" items="${sessionScope.menu_moduleList}">
                <li>
                    <a href="${n.url}"><i class="${n.styles}"></i>${n.name}<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <c:forEach var="m" items="${n.moduleList}">
                            <li>
                                <a href="${m.url}"><i class="${m.styles}"></i>${m.name}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </div>
    <!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->