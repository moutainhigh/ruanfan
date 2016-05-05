package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Attentions;
import com.sixmac.entity.Users;
import com.sixmac.service.AttentionsService;
import com.sixmac.service.CityService;
import com.sixmac.service.OperatisService;
import com.sixmac.service.UsersService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.Md5Util;
import com.sixmac.utils.QiNiuUploadImgUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
@Controller
@RequestMapping(value = "backend/users")
public class UsersController extends CommonController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AttentionsService attentionsService;

    @Autowired
    private OperatisService operatisService;

    @RequestMapping("index")
    public String index() {
        return "backend/会员列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String mobile,
                     String nickName,
                     Integer status,
                     Integer type,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Users> page = usersService.page(mobile, nickName, status, type, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Users users = usersService.getById(id);
            model.addAttribute("users", users);

            // 查询粉丝数
            List<Attentions> fans = attentionsService.iFindList(id, Constant.ATTENTION_USERS);
            model.addAttribute("fans", fans.size());

            // 查询关注数
            List<Attentions> attentions = attentionsService.iFindListByUserId(id);
            model.addAttribute("attentions", attentions.size());

            return "backend/会员详情";
        }

        return "backend/新增会员";
    }

    /**
     * 启用 or 禁用会员
     *
     * @param userId
     * @param status
     * @return
     */
    @RequestMapping("/changeStatus")
    @ResponseBody
    public Integer changeStatus(HttpServletRequest request, Integer userId, Integer status) {
        try {
            Users users = usersService.getById(userId);
            users.setStatus(status);

            usersService.update(users);

            operatisService.addOperatisInfo(request, status == 0 ? "启用" : "禁用" + "用户 " + users.getNickName());

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除会员信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(HttpServletRequest request, Integer userId) {
        try {
            usersService.deleteById(userId);

            operatisService.addOperatisInfo(request, "删除用户 " + usersService.getById(userId).getNickName());

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除会员信息
     *
     * @param ids
     * @return
     */
    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(HttpServletRequest request, String ids) {
        try {
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            usersService.deleteAll(arrayId);

            // 拼接用户昵称
            StringBuffer sb = new StringBuffer("");
            for (int id : arrayId) {
                sb.append(usersService.getById(id).getNickName() + "、");
            }

            operatisService.addOperatisInfo(request, "批量删除用户 " + sb.toString().substring(0, sb.toString().length() - 1));

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增会员信息
     *
     * @param request
     * @param id
     * @param mobile
     * @param password
     * @param type
     * @param nickName
     * @param cityId
     * @param comName
     * @param comArea
     * @param multipartRequest
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        Integer id,
                        String mobile,
                        String password,
                        Integer type,
                        String nickName,
                        Integer cityId,
                        String comName,
                        String comArea,
                        MultipartRequest multipartRequest) {
        try {
            Users users = null;

            if (null == id) {
                users = new Users();
            } else {
                users = usersService.getById(id);
            }

            users.setMobile(mobile);
            users.setPassword(Md5Util.md5(password));
            users.setType(type);
            users.setNickName(nickName);
            users.setCity(cityService.getById(cityId));
            users.setComName(comName);
            users.setComArea(comArea);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                String url = QiNiuUploadImgUtil.upload(multipartFile);
                users.setHeadPath(url);
            }

            if (null == id) {
                users.setScore(0);
                users.setStatus(Constant.BANNED_STATUS_YES);
                users.setCreateTime(new Date());
                usersService.create(users);
            } else {
                usersService.update(users);
            }

            operatisService.addOperatisInfo(request, null == id ? "新增" : "修改" + "用户 " + users.getNickName());

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
