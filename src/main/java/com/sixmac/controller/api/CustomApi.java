package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Custom;
import com.sixmac.entity.Custominfo;
import com.sixmac.service.CustomService;
import com.sixmac.service.CustominfoService;
import com.sixmac.service.CustompackagesService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * @api {post} /api/custom/findList 根据楼盘名称查询户型列表
     * @apiName custom.findList
     * @apiGroup custom
     * @apiParam {String} name 楼盘名称      </必传>
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
        Custom custom = customService.findOneByParams(name);
        List<Custominfo> custominfoList = custominfoService.findListByCustomId(custom.getId());

        Result obj = new Result(true).data(createMap("list", custominfoList));
        String result = JsonUtil.obj2ApiJson(obj, "custom", "merchant", "brand", "sort");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/custom/info 根据户型id和区域id查询户型详情
     * @apiName custom.info
     * @apiGroup custom
     * @apiParam {Integer} customInfoId  户型id    </必传>
     * @apiParam {Integer} areaId 区域id
     * @apiSuccess {Object} customInfo 户型详情
     * @apiSuccess {Integer} customInfo.id 户型id
     * @apiSuccess {String} customInfo.name 户型名称
     * @apiSuccess {String} customInfo.path 户型封面图
     * @apiSuccess {String} customInfo.createTime 创建时间
     * @apiSuccess {Object} customInfo.packageList 创建时间
     */
    @RequestMapping(value = "/info")
    public void info(HttpServletResponse response,
                     Integer customInfoId,
                     Integer areaId) {
        Custominfo custominfo = custominfoService.getById(customInfoId);
        custominfo.setPackageList(customPackageService.findListByCustominfoId(customInfoId, areaId));

        Result obj = new Result(true).data(createMap("customInfo", custominfo));
        String result = JsonUtil.obj2ApiJson(obj, "custom", "merchant", "brand", "sort");
        WebUtil.printApi(response, result);
    }
}
