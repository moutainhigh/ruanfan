package com.sixmac.controller.api;

import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
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
import java.util.Map;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
@Controller
@RequestMapping(value = "api/magazine")
public class MagazineApi {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private ImageService imageService;

    /**
     * 杂志列表
     *
     * @param response
     * @param month
     * @param pageNum
     * @param pageSize
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

        Map<String, Object> dataMap = APIFactory.fitting(page);
        WebUtil.printApi(response, new Result(true).data(dataMap));
    }

    /**
     * 查看杂志详情
     *
     * @param response
     * @param magazineId
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

        Result obj = new Result(true).data(magazine);
        String result = JsonUtil.obj2ApiJson(obj, "objectId", "objectType");
        WebUtil.printApi(response, result);
    }
}
