package com.sixmac.controller.api;

import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Attentions;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Users;
import com.sixmac.entity.vo.AttentionVo;
import com.sixmac.service.AttentionsService;
import com.sixmac.service.DesignersService;
import com.sixmac.service.UsersService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/attention")
public class AttentionApi {

    @Autowired
    private AttentionsService attentionsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private DesignersService designersService;

    /**
     * @api {post} /api/attention/fansList 粉丝列表
     * @apiName attention.fansList
     * @apiGroup attention
     * @apiParam {Integer} userId 用户id      <必传 />
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数      <必传 />
     * @apiSuccess {Object} list 粉丝列表
     * @apiSuccess {Integer} list.id 粉丝目标id
     * @apiSuccess {Integer} list.name 粉丝目标名称
     * @apiSuccess {Integer} list.path 粉丝目标头像
     */
    @RequestMapping(value = "/fansList")
    public void fansList(HttpServletResponse response,
                         Integer userId,
                         Integer pageNum,
                         Integer pageSize) {
        if (null == userId || null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Attentions> page = attentionsService.iPageFans(userId, pageNum, pageSize);

        List<AttentionVo> list = new ArrayList<AttentionVo>();
        AttentionVo attentionVo = null;
        for (Attentions attentions : page.getContent()) {
            attentionVo = new AttentionVo();
            attentionVo.setId(attentions.getUser().getId());
            attentionVo.setName(attentions.getUser().getNickName());
            attentionVo.setPath(attentions.getUser().getHeadPath());

            list.add(attentionVo);
        }

        Map<String, Object> dataMap = APIFactory.fittingPlus(page, list);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user", "objectId", "objectType", "type", "description");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/attention/attentionList 关注列表
     * @apiName attention.attentionList
     * @apiGroup attention
     * @apiParam {Integer} userId 用户id      <必传 />
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数      <必传 />
     * @apiSuccess {Object} list 关注列表
     * @apiSuccess {Integer} list.id 关注目标id
     * @apiSuccess {Integer} list.name 关注目标名称
     * @apiSuccess {Integer} list.path 关注目标头像
     * @apiSuccess {Integer} list.type 关注目标类型，1=用户，2=设计师
     */
    @RequestMapping(value = "/attentionList")
    public void attentionList(HttpServletResponse response,
                              Integer userId,
                              Integer pageNum,
                              Integer pageSize) {
        if (null == userId || null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Attentions> page = attentionsService.iPage(userId, pageNum, pageSize);

        List<AttentionVo> list = new ArrayList<AttentionVo>();
        AttentionVo attentionVo = null;
        Integer type = 0;
        Users users = null;
        Designers designers = null;
        for (Attentions attentions : page.getContent()) {
            attentionVo = new AttentionVo();
            type = attentions.getObjectType();

            if (type == 1) {
                // type为1代表用户
                users = usersService.getById(attentions.getObjectId());
                attentionVo.setId(users.getId());
                attentionVo.setName(users.getNickName());
                attentionVo.setPath(users.getHeadPath());
                attentionVo.setDescription("");
                attentionVo.setType(type);
            } else {
                // type为2代表设计师
                designers = designersService.getById(attentions.getObjectId());
                attentionVo.setId(designers.getId());
                attentionVo.setName(designers.getNickName());
                attentionVo.setPath(designers.getHead());
                attentionVo.setDescription(designers.getDescription());
                attentionVo.setType(type);
            }

            list.add(attentionVo);
        }

        Map<String, Object> dataMap = APIFactory.fittingPlus(page, list);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user", "objectId", "objectType");
        WebUtil.printApi(response, result);
    }
}
