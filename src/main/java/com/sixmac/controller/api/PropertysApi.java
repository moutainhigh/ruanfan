package com.sixmac.controller.api;

import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Propertyinfo;
import com.sixmac.entity.Propertys;
import com.sixmac.service.PropertyinfoService;
import com.sixmac.service.PropertysService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.PathUtils;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/property")
public class PropertysApi {

    @Autowired
    private PropertysService propertysService;

    @Autowired
    private PropertyinfoService propertyinfoService;

    /**
     * @api {post} /api/property/list 地产列表
     *
     * @apiName property.list
     * @apiGroup property
     *
     * @apiParam {String} name 名称
     * @apiParam {String} address 地区
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
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
     * @apiSuccess {Object} list.childList.childList ...（无用字段）
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     String name,
                     String address,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Propertys> page = propertysService.iPage(name, address, pageNum, pageSize);

        for (Propertys propertys : page.getContent()) {
            propertys.setChildList(propertysService.iPageByParentId(propertys.getId()));
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "parentId", "childNum");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/property/imageList 根据楼盘id查询对应的户型列表
     *
     * @apiName property.imageList
     * @apiGroup property
     *
     * @apiParam {Integer} propertyId 楼盘id       <必传 />
     *
     * @apiSuccess {Object} list 户型列表
     * @apiSuccess {Integer} list.id 户型id
     * @apiSuccess {String} list.path 户型图片
     * @apiSuccess {String} list.serverPath 客服头像
     * @apiSuccess {String} list.url 展示链接地址
     * @apiSuccess {String} list.qq 客服QQ
     */
    @RequestMapping(value = "/imageList")
    public void imageList(HttpServletResponse response,
                          Integer propertyId) {
        if (null == propertyId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        List<Propertyinfo> list = propertyinfoService.findListByPropertyId(propertyId);

        for (Propertyinfo propertyInfo : list) {
            propertyInfo.setPath(PathUtils.getRemotePath() + propertyInfo.getPath());
            propertyInfo.setServerPath(PathUtils.getRemotePath() + propertyInfo.getServerPath());
        }

        Result obj = new Result(true).data(list);
        String result = JsonUtil.obj2ApiJson(obj, "property");
        WebUtil.printApi(response, result);
    }
}
