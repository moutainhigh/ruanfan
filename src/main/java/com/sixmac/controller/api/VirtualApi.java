package com.sixmac.controller.api;

import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Collect;
import com.sixmac.entity.Gams;
import com.sixmac.entity.Virtuals;
import com.sixmac.service.CollectService;
import com.sixmac.service.GamsService;
import com.sixmac.service.VirtualsService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
@Controller
@RequestMapping(value = "api/virtual")
public class VirtualApi {

    @Autowired
    private VirtualsService virtualsService;

    @Autowired
    private GamsService gamsService;

    @Autowired
    private CollectService collectService;

    /**
     * @api {post} /api/virtual/list VR虚拟列表
     * @apiName virtual.list
     * @apiGroup virtual
     * @apiParam {String} name 名称
     * @apiParam {Integer} styleId 风格id
     * @apiParam {Integer} typeId 分类id
     * @apiParam {Integer} userId 用户id
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
     * @apiSuccess {Object} list VR虚拟列表
     * @apiSuccess {Integer} list.id VR虚拟id
     * @apiSuccess {String} list.name VR虚拟名称
     * @apiSuccess {String} list.labels 标签
     * @apiSuccess {String} list.cover 封面图
     * @apiSuccess {String} list.url 链接地址
     * @apiSuccess {String} list.createTime 创建时间
     * @apiSuccess {String} list.isGam 是否点赞  0=是，1=否
     * @apiSuccess {String} list.isCollect 是否收藏  0=是，1=否
     * @apiSuccess {Integer} list.shareNum 分享数
     * @apiSuccess {Integer} list.collectNum 收藏数
     * @apiSuccess {Object} list.gamsList 点赞列表
     * @apiSuccess {Integer} list.gamsList.id 点赞id
     * @apiSuccess {String} list.gamsList.description 描述
     * @apiSuccess {Integer} list.gamsList.gamUserId 点赞人id
     * @apiSuccess {String} list.gamsList.gamHead 点赞人头像
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     String name,
                     Integer styleId,
                     Integer typeId,
                     Integer userId,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Virtuals> page = virtualsService.iPage(name, styleId, typeId, pageNum, pageSize);

        for (Virtuals virtuals : page.getContent()) {
            virtuals.setCollectNum(collectService.iFindList(virtuals.getId(), Constant.COLLECT_VIRTUALS).size());

            virtuals.setGamsList(gamsService.iFindList(virtuals.getId(), Constant.GAM_VIRTUALS, Constant.GAM_LOVE, Constant.SORT_TYPE_DESC));

            // 如果userId不为空的话，查询是否评论过、点赞过
            if (null != userId) {
                Gams gams = gamsService.iFindOne(userId, virtuals.getId(), Constant.GAM_VIRTUALS, Constant.GAM_LOVE);
                virtuals.setIsGam(null == gams ? Constant.GAM_LOVE_NO : Constant.GAM_LOVE_YES);

                Collect collect = collectService.iFindOne(userId, virtuals.getId(), Constant.COLLECT_VIRTUALS);
                virtuals.setIsCollect(null == collect ? Constant.GAM_LOVE_NO : Constant.GAM_LOVE_YES);
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "style", "type");
        WebUtil.printApi(response, result);
    }
}
