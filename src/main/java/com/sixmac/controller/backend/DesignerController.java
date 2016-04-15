package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Designers;
import com.sixmac.service.CityService;
import com.sixmac.service.DesignersService;
import com.sixmac.utils.*;
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
@RequestMapping(value = "backend/designers")
public class DesignerController extends CommonController {

    @Autowired
    private DesignersService designersService;

    @Autowired
    private CityService cityService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "backend/设计师列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String mobile,
                     String nickName,
                     Integer status,
                     Integer isCheck,
                     Integer type,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Designers> page = designersService.page(mobile, nickName, status, isCheck, type, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Designers designers = designersService.getById(id);
            model.addAttribute("designers", designers);
        }

        return "backend/新增设计师";
    }

    /**
     * 启用 or 禁用设计师
     *
     * @param designerId
     * @param status
     * @return
     */
    @RequestMapping("/changeStatus")
    @ResponseBody
    public Integer changeStatus(Integer designerId, Integer status) {
        try {
            Designers designers = designersService.getById(designerId);
            designers.setStatus(status);

            designersService.update(designers);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 审核设计师
     *
     * @param designerId
     * @param isCheck
     * @param reason
     * @return
     */
    @RequestMapping("/changeCheck")
    @ResponseBody
    public Integer changeCheck(Integer designerId, Integer isCheck, String reason) {
        try {
            designersService.changeCheck(designerId, isCheck, reason);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除设计师信息
     *
     * @param designerId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer designerId) {
        try {
            designersService.deleteById(designerId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除设计师信息
     *
     * @param ids
     * @return
     */
    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(String ids) {
        try {
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            designersService.deleteAll(arrayId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增设计师信息
     *
     * @param request
     * @param id
     * @param mobile
     * @param password
     * @param type
     * @param nickName
     * @param cityId
     * @param content
     * @param multipartRequest
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(ServletRequest request,
                        Integer id,
                        String mobile,
                        String password,
                        Integer type,
                        String nickName,
                        Integer cityId,
                        String content,
                        MultipartRequest multipartRequest) {
        try {
            Designers designers = null;

            if (null == id) {
                designers = new Designers();
            } else {
                designers = designersService.getById(id);
            }

            designers.setMobile(mobile);
            designers.setPassword(Md5Util.md5(password));
            designers.setType(type);
            designers.setNickName(nickName);
            designers.setCity(cityService.getById(cityId));
            designers.setContent(content);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                String url = QiNiuUploadImgUtil.upload(multipartFile);
                designers.setProof(url);
            }

            if (null == id) {
                designers.setStar(0);
                designers.setPrice("");
                designers.setDescription("");
                designers.setDescs("");
                designers.setIsCheck(Constant.CHECK_STATUS_SUCCESS);
                designers.setStatus(Constant.BANNED_STATUS_YES);
                designers.setCreateTime(new Date());
                designers.setHead(Constant.DEFAULT_HEAD_PATH);
                designersService.create(designers);
            } else {
                designersService.update(designers);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
