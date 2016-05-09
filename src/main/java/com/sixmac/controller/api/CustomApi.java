package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Custom;
import com.sixmac.entity.Custominfo;
import com.sixmac.service.CustomService;
import com.sixmac.service.CustominfoService;
import com.sixmac.service.CustompackagesService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.apache.commons.lang.StringUtils;
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
     */
    @RequestMapping(value = "/findList")
    public void findList(HttpServletResponse response,
                         String name) {
        Custom custom = null;

        if (StringUtils.isNotEmpty(name)) {
            custom = customService.findOneByParams(name);
        } else {
            custom = customService.findOneByHot();
        }

        if (null == custom) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        List<Custominfo> custominfoList = custominfoService.findListByCustomId(custom.getId());

        Result obj = new Result(true).data(createMap("list", custominfoList));
        String result = JsonUtil.obj2ApiJson(obj, "custom", "merchant", "brand", "sort", "packageList", "isHot");
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
     * @apiSuccess {Object} customInfo.packageList 套餐列表
     * @apiSuccess {Integer} customInfo.packageList.id 套餐id
     * @apiSuccess {String} customInfo.packageList.name 套餐名称
     * @apiSuccess {String} customInfo.packageList.cover 套餐图片
     * @apiSuccess {Integer} customInfo.packageList.cover.id 图片id
     * @apiSuccess {String} customInfo.packageList.cover.path 图片路径
     * @apiSuccess {Object} customInfo.packageList.cover.labelList 图片标签列表
     * @apiSuccess {Integer} customInfo.packageList.cover.labelList.id 标签id
     * @apiSuccess {String} customInfo.packageList.cover.labelList.name 标签名称
     * @apiSuccess {String} customInfo.packageList.cover.labelList.productId 商品id
     * @apiSuccess {String} customInfo.packageList.cover.labelList.productCover 商品封面
     * @apiSuccess {String} customInfo.packageList.cover.labelList.description 标签描述
     * @apiSuccess {String} customInfo.packageList.cover.labelList.leftPoint 标签左边距（实际使用时，数值乘以二）
     * @apiSuccess {String} customInfo.packageList.cover.labelList.topPoint 标签上边距（实际使用时，数值乘以二）
     */
    @RequestMapping(value = "/info")
    public void info(HttpServletResponse response,
                     Integer customInfoId,
                     Integer areaId) {
        Custominfo custominfo = custominfoService.getById(customInfoId);

        if (null == custominfo) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        custominfo.setPackageList(customPackageService.findListByCustominfoId(customInfoId, areaId));

        Result obj = new Result(true).data(createMap("customInfo", custominfo));
        String result = JsonUtil.obj2ApiJson(obj, "product", "labelId", "custom", "merchant", "brand", "sort", "objectId", "objectType", "isHot", "isCheck", "isAdd", "thuPath", "width", "height", "similarList", "appraisalVoList");
        WebUtil.printApi(response, result);
    }
}
