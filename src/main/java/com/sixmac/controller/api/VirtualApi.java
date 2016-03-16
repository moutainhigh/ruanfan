package com.sixmac.controller.api;

import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Virtuals;
import com.sixmac.service.VirtualsService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * @api {post} /api/virtual/list VR虚拟列表
     * @apiName  virtual.list
     * @apiGroup virtual
     *
     * @apiParam {String} name 名称
     * @apiParam {Integer} styleId 风格id
     * @apiParam {Integer} typeId 分类id
     * @apiParam {Integer} pageNum 页码
     * @apiParam {Integer} pageSize 每页显示条数
     *
     * @apiSuccess {Object} list VR虚拟列表
     * @apiSuccess {Integer} list.id VR虚拟id
     * @apiSuccess {String} list.name VR虚拟名称
     * @apiSuccess {String} list.labels 标签
     * @apiSuccess {String} list.cover 封面图
     * @apiSuccess {String} list.url 链接地址
     * @apiSuccess {String} list.createTime 创建时间
     *
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     String name,
                     Integer styleId,
                     Integer typeId,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Virtuals> page = virtualsService.iPage(name, styleId, typeId, pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "style", "type");
        WebUtil.printApi(response, result);
    }
}
