package com.sixmac.controller.merchant;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Merchants;
import com.sixmac.entity.Messageplus;
import com.sixmac.service.CityService;
import com.sixmac.service.MerchantsService;
import com.sixmac.service.StylesService;
import com.sixmac.utils.ImageUtil;
import com.sixmac.utils.QiNiuUploadImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/23 0023.
 */
@Controller
@RequestMapping(value = "merchant")
public class MerchantIndexController extends CommonController {

    @Autowired
    private MerchantsService merchantsService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StylesService stylesService;

    @RequestMapping(value = "/dashboard")
    public String dashboard(HttpServletRequest request,
                            ModelMap model) {
        Merchants merchants = getMerchant(request, model, merchantsService);
        if (merchants.getIsCheck() != Constant.CHECK_STATUS_SUCCESS) {
            return "merchant/个人资料待审核";
        }

        return "merchant/控制面板";
    }

    @RequestMapping(value = "/info")
    public String info(HttpServletRequest request, ModelMap model) {
        Merchants merchants = getMerchant(request, model, merchantsService);

        if (merchants.getIsCheck() == Constant.CHECK_STATUS_SUCCESS) {
            return "merchant/个人资料审核通过";
        } else {
            return "merchant/个人资料待审核";
        }
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(Integer id,
                        Integer type,
                        String nickName,
                        Integer cityId,
                        String content,
                        Integer styleId,
                        String url,
                        String labels,
                        MultipartRequest multipartRequest) {
        try {
            Merchants merchants = merchantsService.getById(id);
            merchants.setNickName(nickName);
            merchants.setUrl(url);
            merchants.setLabels(labels);
            merchants.setType(type);
            merchants.setStyle(stylesService.getById(styleId));
            merchants.setCity(cityService.getById(cityId));
            merchants.setDescription(content);
            merchants.setIsCheck(Constant.CHECK_STATUS_DEFAULT);
            merchants.setIsCut(Constant.IS_CUT_NO);

            // 保存头像
            MultipartFile head = multipartRequest.getFile("mainImage");
            if (null != head) {
                merchants.setHead(QiNiuUploadImgUtil.upload(head));
            }

            // 保存营业执照
            MultipartFile license = multipartRequest.getFile("mainImage2");
            if (null != license) {
                merchants.setLicense(QiNiuUploadImgUtil.upload(license));
            }

            // 保存封面
            MultipartFile cover = multipartRequest.getFile("mainImage3");
            if (null != cover) {
                merchants.setCover(QiNiuUploadImgUtil.upload(cover));
            }

            merchantsService.update(merchants);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping(value = "/savePlus")
    @ResponseBody
    public Integer savePlus(Integer id,
                            Integer cityId,
                            Integer styleId,
                            String content,
                            MultipartRequest multipartRequest) {
        try {
            Merchants merchants = merchantsService.getById(id);
            merchants.setCity(cityService.getById(cityId));
            merchants.setDescription(content);
            merchants.setStyle(stylesService.getById(styleId));

            // 保存头像
            MultipartFile head = multipartRequest.getFile("mainImage");
            if (null != head) {
                merchants.setHead(QiNiuUploadImgUtil.upload(head));
            }

            // 保存封面
            MultipartFile cover = multipartRequest.getFile("mainImage3");
            if (null != cover) {
                merchants.setCover(QiNiuUploadImgUtil.upload(cover));
            }

            merchantsService.update(merchants);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前登录的商家信息
     *
     * @param request
     * @param model
     * @return
     */
    public static Merchants getMerchant(HttpServletRequest request, ModelMap model, MerchantsService merchantsService) {
        Integer merchantId = (Integer) request.getSession().getAttribute(Constant.CURRENT_USER_ID);
        Merchants merchants = merchantsService.getById(merchantId);
        model.addAttribute("merchant", merchants);

        // 查询审核失败的原因
        Messageplus messageplus = merchantsService.findReasonByMerchantId(merchantId);
        if (null != messageplus) {
            model.addAttribute("reasonInfo", messageplus.getDescription());
        }

        return merchants;
    }
}
