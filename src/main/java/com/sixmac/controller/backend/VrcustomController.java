package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Vrcustom;
import com.sixmac.service.OperatisService;
import com.sixmac.service.VrcustomService;
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
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
@Controller
@RequestMapping(value = "backend/vrcustom")
public class VrcustomController extends CommonController {

    @Autowired
    private VrcustomService vrcustomService;

    @Autowired
    private OperatisService operatisService;

    @RequestMapping(value = "/index")
    public String index() {
        return "backend/VR虚拟设计列表";
    }

    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Vrcustom> page = vrcustomService.find(pageNum, length);

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping(value = "/add")
    public String add(ModelMap model, Integer id) {
        if (id != null) {
            Vrcustom vrcustom = vrcustomService.getById(id);
            model.addAttribute("vrcustom", vrcustom);
        }

        return "backend/新增VR虚拟设计";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Integer delete(HttpServletRequest request, Integer id) {
        try {
            vrcustomService.deleteById(request, id);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        Integer id,
                        String urls,
                        MultipartRequest multipartRequest) {
        Vrcustom vrcustom = null;

        try {
            if (id != null) {
                vrcustom = vrcustomService.getById(id);
            } else {
                vrcustom = new Vrcustom();
            }

            vrcustom.setUrl(urls);
            vrcustom.setUpdateTime(new Date());

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                String url = QiNiuUploadImgUtil.upload(multipartFile);
                vrcustom.setCover(url);
            }

            if (null == id) {
                vrcustomService.create(vrcustom);
            } else {
                vrcustomService.update(vrcustom);
            }

            operatisService.addOperatisInfo(request, null == id ? "新增" : "修改" + "id为 " + vrcustom.getId() + " 的VR虚拟设计");

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
