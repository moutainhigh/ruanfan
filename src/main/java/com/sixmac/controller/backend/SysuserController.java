package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Sysusers;
import com.sixmac.service.RoleModulesService;
import com.sixmac.service.SysusersService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/2 0002.
 */
@Controller
@RequestMapping(value = "backend/sysUser")
public class SysuserController extends CommonController {

    @Autowired
    private SysusersService sysusersService;

    @Autowired
    private RoleModulesService roleModulesService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "backend/管理员列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String account,
                     Integer roleId,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Sysusers> page = sysusersService.page(account, roleId, pageNum, length);

        // 循环查找每个管理员的角色权限列表
        for (Sysusers sysusers : page.getContent()) {
            sysusers.setRolemodulesList(roleModulesService.findListByRoleId(sysusers.getRole().getId()));
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }
}
