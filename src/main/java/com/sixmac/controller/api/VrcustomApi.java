package com.sixmac.controller.api;

import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Vrcustom;
import com.sixmac.service.VrcustomService;
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
 * Created by Administrator on 2016/4/19 0019.
 */
@Controller
@RequestMapping(value = "api/vrcustom")
public class VrcustomApi {

    @Autowired
    private VrcustomService vrcustomService;

    /**
     * @api {post} /api/vrcustom/list VR虚拟定制列表
     * @apiName vrcustom.list
     * @apiGroup vrcustom
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
     * @apiSuccess {Object} list VR虚拟定制列表
     * @apiSuccess {Integer} list.id VR虚拟定制id
     * @apiSuccess {String} list.cover 封面图
     * @apiSuccess {String} list.content 内容
     * @apiSuccess {String} list.updateTime 更新时间
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Vrcustom> page = vrcustomService.find(pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }
}
