package com.sixmac.controller.api;

import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Propertys;
import com.sixmac.service.PropertysService;
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
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/property")
public class PropertysApi {

    @Autowired
    private PropertysService propertysService;

    /**
     * @api {post} /api/property/list 地产列表
     * @apiName  property.list
     * @apiGroup property
     *
     * @apiParam {String} name 名称or地区
     * @apiParam {Integer} pageNum 页码
     * @apiParam {Integer} pageSize 每页显示条数
     *
     * @apiSuccess {Object} list 地产列表
     * @apiSuccess {Integer} list.id 地产id
     * @apiSuccess {String} list.name 地产名称
     * @apiSuccess {String} list.cover 地产封面图
     * @apiSuccess {String} list.address 地产地址
     * @apiSuccess {String} list.labels 地产标签
     * @apiSuccess {String} list.createTime 地产创建时间
     * @apiSuccess {Object} list.childList 楼盘列表
     * @apiSuccess {Integer} list.childList.id 楼盘id
     * @apiSuccess {String} list.childList.name 楼盘名称
     * @apiSuccess {String} list.childList.cover 楼盘封面图
     * @apiSuccess {String} list.childList.address 楼盘地址
     * @apiSuccess {String} list.childList.labels 楼盘标签
     * @apiSuccess {String} list.childList.createTime 楼盘创建时间
     * @apiSuccess {Object} list.childList.childList （无用字段）
     *
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     String name,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Propertys> page = propertysService.iPage(name, pageNum, pageSize);

        for (Propertys propertys : page.getContent()) {
            propertys.setChildList(propertysService.iPageByParentId(propertys.getId()));
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "parentId");
        WebUtil.printApi(response, result);
    }
}
