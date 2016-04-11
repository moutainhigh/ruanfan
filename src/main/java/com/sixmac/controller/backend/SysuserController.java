package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Sysusers;
import com.sixmac.service.RoleModulesService;
import com.sixmac.service.RolesService;
import com.sixmac.service.SysusersService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.Md5Util;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
    private RolesService rolesService;

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

    @RequestMapping("/add")
    public String add(ModelMap model, Integer id) {
        if (id != null) {
            Sysusers sysusers = sysusersService.getById(id);
            model.addAttribute("sysUser", sysusers);
        }
        return "backend/新增管理人员";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Integer save(Integer id, Integer roleId, String account, String password) {
        try {
            if (roleId == Constant.ADMIN_ID) {
                return -1;
            }

            Sysusers sysusers = null;

            if (null == id) {
                sysusers = new Sysusers();
            } else {
                sysusers = sysusersService.getById(id);
            }

            sysusers.setAccount(account);
            sysusers.setPassword(Md5Util.md5(password));
            sysusers.setRole(rolesService.getById(roleId));

            if (null == id) {
                sysusers.setCreateTime(new Date());
                sysusersService.create(sysusers);
            } else {
                sysusersService.update(sysusers);
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
            sysusersService.deleteById(id);
            return 1;
        } else {
            WebUtil.printJson(response, new Result(false).msg("管理人员不存在"));
        }
        return 0;
    }

    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(String ids) {
        try {
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            sysusersService.deleteAll(arrayId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
