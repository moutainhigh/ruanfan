package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Custom;
import com.sixmac.entity.Custominfo;
import com.sixmac.service.CustomService;
import com.sixmac.service.CustominfoService;
import com.sixmac.service.CustompackagesService;
import com.sixmac.service.ImageService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.PathUtils;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
@Controller
@RequestMapping(value = "api/custom")
public class CustomApi extends CommonController {

    @Autowired
    private CustomService customService;

    @Autowired
    private CustominfoService custominfoService;

    @Autowired
    private CustompackagesService customPackageService;

    @Autowired
    private ImageService imageService;

    /**
     * @api {post} /api/custom/list 设计定制列表
     * @apiName custom.list
     * @apiGroup custom
     * @apiParam {String} name 楼盘名称
     * @apiSuccess {Object} list 楼盘列表
     * @apiSuccess {Integer} list.id 楼盘id
     * @apiSuccess {String} list.name 楼盘名称
     * @apiSuccess {String} list.cover 楼盘封面图
     * @apiSuccess {String} list.address 楼盘地址
     * @apiSuccess {String} list.createTime 创建时间
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     String name) {
        if (null == name) {
            name = "";
        }
        name = "%" + name + "%";

        List<Custom> list = customService.findListByParams(name);

        for (Custom custom : list) {
            custom.setCover(PathUtils.getRemotePath() + custom.getCover());
        }

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/custom/customList 根据楼盘id查询户型列表
     * @apiName custom.customList
     * @apiGroup custom
     * @apiParam {Integer} customId 楼盘id
     * @apiSuccess {Object} list 户型列表
     * @apiSuccess {Integer} list.id 户型id
     * @apiSuccess {String} list.name 户型名称
     * @apiSuccess {String} list.path 户型封面图
     * @apiSuccess {String} list.createTime 创建时间
     * @apiSuccess {Object} list.packageList 创建时间
     */
    @RequestMapping(value = "/customList")
    public void customList(HttpServletResponse response, Integer customId) {
        if (null == customId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        List<Custominfo> list = custominfoService.findListByCustomId(customId);

        for (Custominfo customInfo : list) {
            customInfo.setPackageList(customPackageService.findListByCustominfoId(customInfo.getId()));
        }

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj, "custom", "merchant", "brand", "sort");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/custom/findList 根据楼盘名称查询户型列表
     * @apiName custom.findList
     * @apiGroup custom
     * @apiParam {String} name 楼盘名称
     * @apiSuccess {Object} list 户型列表
     * @apiSuccess {Integer} list.id 户型id
     * @apiSuccess {String} list.name 户型名称
     * @apiSuccess {String} list.path 户型封面图
     * @apiSuccess {String} list.createTime 创建时间
     * @apiSuccess {Object} list.packageList 创建时间
     */
    @RequestMapping(value = "/findList")
    public void findList(HttpServletResponse response,
                         String name) {
        List<Custom> list = customService.findListByParams(name);
        List<Custominfo> custominfoList = new ArrayList<Custominfo>();

        for (Custom custom : list) {
            custominfoList = custominfoService.findListByCustomId(custom.getId());

            for (Custominfo customInfo : custominfoList) {
                customInfo.setPackageList(customPackageService.findListByCustominfoId(customInfo.getId()));
            }
        }

        Result obj = new Result(true).data(createMap("list", custominfoList));
        String result = JsonUtil.obj2ApiJson(obj, "custom", "merchant", "brand", "sort");
        WebUtil.printApi(response, result);
    }
}
