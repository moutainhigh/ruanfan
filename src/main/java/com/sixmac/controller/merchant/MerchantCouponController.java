package com.sixmac.controller.merchant;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Coupon;
import com.sixmac.entity.Merchants;
import com.sixmac.entity.Usercoupon;
import com.sixmac.service.CouponService;
import com.sixmac.service.MerchantsService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
@Controller
@RequestMapping(value = "merchant/coupon")
public class MerchantCouponController extends CommonController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private MerchantsService merchantsService;

    @RequestMapping("index")
    public String index(ModelMap model, HttpServletRequest request) {
        Merchants merchants = MerchantIndexController.getMerchant(request, model, merchantsService);
        if (merchants.getIsCheck() != Constant.CHECK_STATUS_SUCCESS) {
            return "merchant/个人资料待审核";
        }

        return "merchant/范票列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     ModelMap model) {
        Merchants merchants = MerchantIndexController.getMerchant(request, model, merchantsService);

        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Coupon> page = couponService.pageByMerchantId(merchants.getId(), pageNum, length);

        for (Coupon coupon : page.getContent()) {
            coupon.setSourceName(coupon.getMerchant().getNickName());
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Coupon coupon = couponService.getById(id);
            model.addAttribute("coupon", coupon);
        }

        return "merchant/新增范票";
    }

    /**
     * 删除范票信息
     *
     * @param couponId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer couponId) {
        try {
            List<Usercoupon> list = couponService.findListByCouponId(couponId);

            if (null != list && list.size() > 0) {
                return -1;
            }

            couponService.deleteById(couponId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增范票信息
     *
     * @param request
     * @param id
     * @param name
     * @param money
     * @param type
     * @param maxMoney
     * @param startDate
     * @param endDate
     * @param multipartRequest
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        Integer id,
                        String name,
                        String money,
                        Integer type,
                        String maxMoney,
                        String startDate,
                        String endDate,
                        MultipartRequest multipartRequest,
                        ModelMap model) {
        try {
            Merchants merchants = MerchantIndexController.getMerchant(request, model, merchantsService);

            Coupon coupon = null;

            if (null == id) {
                coupon = new Coupon();
            } else {
                coupon = couponService.getById(id);
            }

            coupon.setMerchant(merchants);
            coupon.setName(name);
            coupon.setMoney(money);
            coupon.setType(type);
            coupon.setIsCheck(Constant.CHECK_STATUS_DEFAULT);

            if (type == 1) {
                coupon.setMaxMoney(maxMoney);
            } else {
                coupon.setMaxMoney("");
            }
            coupon.setStartDate(startDate);
            coupon.setEndDate(endDate);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                String url = QiNiuUploadImgUtil.upload(multipartFile);
                coupon.setCover(url);
            }

            if (null == id) {
                coupon.setCouponNum(System.currentTimeMillis() + "");
                coupon.setCreateTime(new Date());
                couponService.create(coupon);
            } else {
                // 商户后台修改范票信息时，清空所有已领取该范票的用户关联
                couponService.deleteAllListByCouponId(coupon.getId());

                couponService.update(coupon);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
