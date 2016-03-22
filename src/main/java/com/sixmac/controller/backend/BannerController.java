package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Banner;
import com.sixmac.service.BannerService;
import com.sixmac.utils.ImageUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
@Controller
@RequestMapping(value = "backend/banner")
public class BannerController extends CommonController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "backend/广告banner列表";
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
        Page<Banner> page = bannerService.find(pageNum, length);

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Banner banner = bannerService.getById(id);
            model.addAttribute("banner", banner);
        }

        return "backend/新增广告banner";
    }

    /**
     * 删除广告banner信息
     *
     * @param bannerId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer bannerId) {
        try {
            bannerService.deleteById(bannerId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增广告banner信息
     *
     * @param request
     * @param id
     * @param typeId
     * @param sourceId
     * @param multipartRequest
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(ServletRequest request,
                        Integer id,
                        Integer typeId,
                        Integer sourceId,
                        MultipartRequest multipartRequest) {
        try {
            Banner banner = null;

            if (null == id) {
                banner = new Banner();
            } else {
                banner = bannerService.getById(id);
            }

            banner.setType(typeId);
            banner.setSourceId(sourceId);
            banner.setUpdateTime(new Date());

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                Map<String, Object> map = ImageUtil.saveImage(request, multipartFile, false);
                banner.setCover(map.get("imgURL").toString());
            }

            if (null == id) {
                bannerService.create(banner);
            } else {
                bannerService.update(banner);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
