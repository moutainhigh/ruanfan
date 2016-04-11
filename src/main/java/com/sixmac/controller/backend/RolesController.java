package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Modules;
import com.sixmac.entity.Rolemodules;
import com.sixmac.entity.Roles;
import com.sixmac.service.ModulesService;
import com.sixmac.service.RoleModulesService;
import com.sixmac.service.RolesService;
import com.sixmac.service.SysusersService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
@Controller
@RequestMapping(value = "backend/roles")
public class RolesController extends CommonController {

    @Autowired
    private RolesService rolesService;

    @Autowired
    private ModulesService modulesService;

    @Autowired
    private RoleModulesService roleModulesService;

    @Autowired
    private SysusersService sysusersService;

    @RequestMapping("/index")
    public String index(ModelMap model) {
        return "backend/权限列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Roles> page = rolesService.page(pageNum, length);

        for (Roles roles : page.getContent()) {
            // 循环查询每个角色的人员数量
            roles.setCount(sysusersService.findListByRoleId(roles.getId()).size());
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("/add")
    public String add(ModelMap model, Integer id) {
        List<Modules> moduleList = modulesService.findListByParentId(0);

        for (Modules module : moduleList) {
            module.setModuleList(modulesService.findListByParentId(module.getId()));
        }

        model.addAttribute("moduleList", moduleList);

        StringBuffer sb = new StringBuffer("");

        if (id != null) {
            Roles roles = rolesService.getById(id);
            model.addAttribute("roles", roles);

            if (null != roles) {
                List<Rolemodules> rolemodulesList = roleModulesService.findListByRoleId(roles.getId());

                for (Rolemodules rolemodules : rolemodulesList) {
                    sb.append(rolemodules.getModule().getId() + ",");
                }

                if (sb.length() > 0) {
                    model.addAttribute("roleModuleIds", sb.toString().substring(0, sb.length() - 1));
                } else {
                    model.addAttribute("roleModuleIds", "");
                }
            } else {
                model.addAttribute("roleModuleIds", "");
            }
        }
        return "backend/新增权限";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Integer save(Integer id, String name, String types) {
        try {
            // 解析权限id
            int[] moduleIds = JsonUtil.json2Obj(types, int[].class);

            Roles roles = null;
            Rolemodules rolemodules = null;

            if (null == id) {
                roles = new Roles();
                roles.setName(name);

                rolesService.create(roles);
            } else {
                roles = rolesService.getById(id);
                roles.setName(name);
                roles.setUpdateTime(new Date());
                rolesService.update(roles);
            }

            // 先删除该角色对应的所有权限
            roleModulesService.deleteByRoleId(roles.getId());

            // 然后逐个保存该角色的权限
            for (int moduleId : moduleIds) {
                rolemodules = new Rolemodules();
                rolemodules.setRole(roles);
                rolemodules.setModule(modulesService.getById(moduleId));

                roleModulesService.create(rolemodules);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @RequestMapping("delete")
    @ResponseBody
    public Integer delete(HttpServletResponse response, Integer id) {
        if (id != null) {
            rolesService.deleteById(id);
            return 1;
        } else {
            WebUtil.printJson(response, new Result(false).msg("权限不存在"));
        }
        return 0;
    }
}
