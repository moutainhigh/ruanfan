package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Merchants;
import com.sixmac.entity.Styles;
import com.sixmac.service.MerchantsService;
import com.sixmac.service.StylesService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/17 0017.
 */
@Controller
@RequestMapping(value = "api/merchant")
public class MerchantApi extends CommonController {

    @Autowired
    private MerchantsService merchantsService;

    @Autowired
    private StylesService stylesService;

    /**
     * @api {post} /api/merchant/list 商户列表
     * @apiName merchant.list
     * @apiGroup merchant
     * @apiParam {Integer} styleId 风格id
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数      <必传 />
     * @apiSuccess {Object} list 商户列表
     * @apiSuccess {Integer} list.id 商户id
     * @apiSuccess {String} list.nickName 商户昵称
     * @apiSuccess {String} list.email 商户邮箱
     * @apiSuccess {String} list.url 商户链接url
     * @apiSuccess {String} list.head 商户头像
     * @apiSuccess {String} list.labels 标签
     * @apiSuccess {String} list.type 类型
     * @apiSuccess {String} list.description 描述
     * @apiSuccess {Integer} list.showNum 人气
     * @apiSuccess {Integer} list.count 销量
     * @apiSuccess {String} list.createTime 创建时间
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer styleId,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Merchants> page = merchantsService.page(styleId, pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "isCut", "isCheck", "style", "city", "password", "license");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/merchant/listPlus 商户列表
     * @apiName merchant.listPlus
     * @apiGroup merchant
     * @apiSuccess {Object} list 商户列表
     * @apiSuccess {Integer} list.id 商户id
     * @apiSuccess {String} list.nickName 商户昵称
     * @apiSuccess {String} list.email 商户邮箱
     * @apiSuccess {String} list.url 商户链接url
     * @apiSuccess {String} list.head 商户头像
     * @apiSuccess {String} list.labels 标签
     * @apiSuccess {String} list.type 类型
     * @apiSuccess {String} list.description 描述
     * @apiSuccess {Integer} list.showNum 人气
     * @apiSuccess {Integer} list.count 销量
     * @apiSuccess {String} list.createTime 创建时间
     */
    @RequestMapping(value = "/listPlus")
    public void listPlus(HttpServletResponse response) {
        List<Styles> list = stylesService.findAll();

        for (Styles styles : list) {
            styles.setMerchantsList(merchantsService.findListByStyleId(styles.getId()));
        }

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj, "isCut", "isCheck", "style", "city", "password", "license");
        WebUtil.printApi(response, result);
    }
}
