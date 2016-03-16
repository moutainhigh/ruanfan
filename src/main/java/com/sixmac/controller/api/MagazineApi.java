package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Image;
import com.sixmac.entity.Magazine;
import com.sixmac.service.ImageService;
import com.sixmac.service.MagazineService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.PathUtils;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
@Controller
@RequestMapping(value = "api/magazine")
public class MagazineApi extends CommonController {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private ImageService imageService;

    /**
     * @api {post} /api/magazine/list 杂志列表
     *
     * @apiName magazine.list
     * @apiGroup magazine
     *
     * @apiParam {Integer} month 月份
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
     *
     * @apiSuccess {Object} list 杂志列表
     * @apiSuccess {Integer} list.id 杂志id
     * @apiSuccess {String} list.name 杂志名称
     * @apiSuccess {String} list.cover 封面图
     * @apiSuccess {Integer} list.month 月份
     * @apiSuccess {String} list.createTime 创建时间
     * @apiSuccess {Object} list.imageList 图片集合
     * @apiSuccess {Integer} list.imageList.id 图片id
     * @apiSuccess {String} list.imageList.path 路径
     * @apiSuccess {String} list.imageList.description 描述
     * @apiSuccess {String} list.imageList.demo 备注
     * @apiSuccess {String} list.imageList.createTime 创建时间
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer month,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Magazine> page = magazineService.iPage(month, pageNum, pageSize);

        // 获取杂志的详情图片列表
        for (Magazine magazine : page.getContent()) {
            magazine.setImageList(imageService.iFindList(magazine.getId(), Constant.IMAGE_MAGAZINE));
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "objectId", "objectType", "thuPath", "width", "height", "labelList");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/magazine/info 杂志详情
     *
     * @apiName magazine.info
     * @apiGroup magazine
     *
     * @apiParam {Integer} magazineId 杂志id       <必传 />
     *
     * @apiSuccess {Object} magazineInfo 杂志列表
     * @apiSuccess {Integer} magazineInfo.id 杂志id
     * @apiSuccess {String} magazineInfo.name 杂志名称
     * @apiSuccess {String} magazineInfo.cover 封面图
     * @apiSuccess {Integer} magazineInfo.month 月份
     * @apiSuccess {String} magazineInfo.createTime 创建时间
     * @apiSuccess {Object} magazineInfo.imageList 图片集合
     * @apiSuccess {Integer} magazineInfo.imageList.id 图片id
     * @apiSuccess {String} magazineInfo.imageList.path 路径
     * @apiSuccess {String} magazineInfo.imageList.description 描述
     * @apiSuccess {String} magazineInfo.imageList.demo 备注
     * @apiSuccess {String} magazineInfo.imageList.createTime 创建时间
     */
    @RequestMapping("info")
    public void info(HttpServletResponse response,
                     Integer magazineId) {
        if (null == magazineId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        Magazine magazine = magazineService.getById(magazineId);

        if (null == magazine) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        magazine.setCover(PathUtils.getRemotePath() + magazine.getCover());

        // 查询杂志对应的图片集合
        magazine.setImageList(imageService.iFindList(magazineId, Constant.IMAGE_MAGAZINE));

        Result obj = new Result(true).data(createMap("magazineInfo", magazine));
        String result = JsonUtil.obj2ApiJson(obj, "objectId", "objectType", "thuPath", "width", "height", "labelList");
        WebUtil.printApi(response, result);
    }
}
