package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Merchants;
import com.sixmac.service.CityService;
import com.sixmac.service.MerchantsService;
import com.sixmac.utils.ImageUtil;
import com.sixmac.utils.Md5Util;
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
@RequestMapping(value = "backend/merchants")
public class MerchantController extends CommonController {

    @Autowired
    private CityService cityService;

    @Autowired
    private MerchantsService merchantsService;

    @RequestMapping("index")
    public String index(ModelMap model) {
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
    public Integer changeStatus(Integer merchantId, Integer status) {
        try {
            Merchants merchants = merchantsService.getById(merchantId);
            merchants.setStatus(status);

            merchantsService.update(merchants);
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
    public Integer changeCheck(Integer merchantId, Integer isCheck, String reason) {
        try {
            merchantsService.changeCheck(merchantId, isCheck, reason);
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
    public Integer delete(Integer merchantId) {
        try {
            merchantsService.deleteById(merchantId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增商户信息
     *
     * @param request
     * @param id
     * @param email
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
                        String email,
                        String password,
                        Integer type,
                        String nickName,
                        Integer cityId,
                        String content,
                        MultipartRequest multipartRequest) {
        try {
            Merchants merchants = null;

            if (null == id) {
                merchants = new Merchants();
            } else {
                merchants = merchantsService.getById(id);
            }

            merchants.setEmail(email);
            merchants.setPassword(Md5Util.md5(password));
            merchants.setType(type);
            merchants.setNickName(nickName);
            merchants.setCity(cityService.getById(cityId));

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                Map<String, Object> map = ImageUtil.saveImage(request, multipartFile, false);
                merchants.setLicense(map.get("imgURL").toString());
            }

            if (null == id) {
                merchants.setDescription("");
                merchants.setIsCheck(Constant.CHECK_STATUS_SUCCESS);
                merchants.setStatus(Constant.BANNED_STATUS_YES);
                merchants.setCreateTime(new Date());
                merchants.setHead(Constant.DEFAULT_HEAD_PATH);
                merchantsService.create(merchants);
            } else {
                merchantsService.update(merchants);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
