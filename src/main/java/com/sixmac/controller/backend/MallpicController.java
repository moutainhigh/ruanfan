package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.MallPic;
import com.sixmac.service.MallPicService;
import com.sixmac.service.OperatisService;
import com.sixmac.utils.QiNiuUploadImgUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/15 0015.
 */
@Controller
@RequestMapping(value = "backend/mallPic")
public class MallpicController extends CommonController {

    @Autowired
    private MallPicService mallPicService;

    @Autowired
    private OperatisService operatisService;

    @RequestMapping("index")
    public String index() {
        return "backend/首页图片列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<MallPic> page = mallPicService.page(pageNum, length);

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    /**
     * 修改杂志
     *
     * @param request
     * @param id
     * @param multipartRequest
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Integer update(HttpServletRequest request,
                          Integer id,
                          MultipartRequest multipartRequest) {
        try {
            MallPic mallPic = mallPicService.getById(id);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                mallPic.setCover(QiNiuUploadImgUtil.upload(multipartFile));
            }

            mallPicService.update(mallPic);

            operatisService.addOperatisInfo(request, "修改MALL首页 " + mallPic.getName() + " 图片");

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
