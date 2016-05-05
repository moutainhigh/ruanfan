package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Merchants;
import com.sixmac.service.CityService;
import com.sixmac.service.MerchantsService;
import com.sixmac.service.OperatisService;
import com.sixmac.service.StylesService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.Md5Util;
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
 * Created by Administrator on 2016/3/14 0014.
 */
@Controller
@RequestMapping(value = "backend/merchants")
public class MerchantController extends CommonController {

    @Autowired
    private CityService cityService;

    @Autowired
    private MerchantsService merchantsService;

    @Autowired
    private StylesService stylesService;

    @Autowired
    private OperatisService operatisService;

    @RequestMapping("index")
    public String index() {
        return "backend/商户列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String email,
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
        Page<Merchants> page = merchantsService.page(email, nickName, status, isCheck, type, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Merchants merchants = merchantsService.getById(id);
            model.addAttribute("merchants", merchants);
        }

        return "backend/新增商户";
    }

    /**
     * 启用 or 禁用商户
     *
     * @param merchantId
     * @param status
     * @return
     */
    @RequestMapping("/changeStatus")
    @ResponseBody
    public Integer changeStatus(HttpServletRequest request, Integer merchantId, Integer status) {
        try {
            Merchants merchants = merchantsService.getById(merchantId);
            merchants.setStatus(status);

            merchantsService.update(merchants);

            operatisService.addOperatisInfo(request, status == 0 ? "启用" : "禁用" + "商户 " + merchants.getNickName());

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 审核商户
     *
     * @param merchantId
     * @param isCheck
     * @param reason
     * @return
     */
    @RequestMapping("/changeCheck")
    @ResponseBody
    public Integer changeCheck(HttpServletRequest request, Integer merchantId, Integer isCheck, String reason) {
        try {
            merchantsService.changeCheck(request, merchantId, isCheck, reason);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除商户信息
     *
     * @param merchantId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(HttpServletRequest request, Integer merchantId) {
        try {
            merchantsService.deleteById(request,merchantId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除商户信息
     *
     * @param ids
     * @return
     */
    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(HttpServletRequest request, String ids) {
        try {
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            merchantsService.deleteAll(request, arrayId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增商户信息
     *
     * @param id
     * @param email
     * @param password
     * @param type
     * @param nickName
     * @param cityId
     * @param description
     * @param multipartRequest
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        Integer id,
                        String email,
                        String url,
                        String password,
                        Integer type,
                        String nickName,
                        String labels,
                        Integer cityId,
                        Integer styleId,
                        String description,
                        MultipartRequest multipartRequest) {
        try {
            Merchants merchants = null;

            if (null == id) {
                merchants = new Merchants();
            } else {
                merchants = merchantsService.getById(id);
            }

            merchants.setNickName(nickName);
            merchants.setPassword(Md5Util.md5(password));
            merchants.setEmail(email);
            merchants.setUrl(url);
            merchants.setLabels(labels);
            merchants.setType(type);
            merchants.setStyle(stylesService.getById(styleId));
            merchants.setCity(cityService.getById(cityId));
            merchants.setDescription(description);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                String head = QiNiuUploadImgUtil.upload(multipartFile);
                merchants.setHead(head);
            }

            MultipartFile multipartFile2 = multipartRequest.getFile("mainImage2");
            if (null != multipartFile2) {
                String cover = QiNiuUploadImgUtil.upload(multipartFile2);
                merchants.setCover(cover);
            }

            MultipartFile multipartFile3 = multipartRequest.getFile("mainImage3");
            if (null != multipartFile3) {
                String license = QiNiuUploadImgUtil.upload(multipartFile3);
                merchants.setLicense(license);
            }

            if (null == id) {
                merchants.setIsCheck(Constant.CHECK_STATUS_SUCCESS);
                merchants.setIsCut(Constant.IS_CUT_NO);
                merchants.setCount(0);
                merchants.setShowNum(0);
                merchants.setStatus(Constant.BANNED_STATUS_YES);
                merchants.setCreateTime(new Date());
                merchantsService.create(merchants);
            } else {
                merchantsService.update(merchants);
            }

            operatisService.addOperatisInfo(request, null == id ? "新增" : "修改" + "商户 " + merchants.getNickName());

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
