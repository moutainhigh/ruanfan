package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Coupon;
import com.sixmac.service.CouponService;
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
@RequestMapping(value = "coupon")
public class CouponController extends CommonController {

    @Autowired
    private CouponService couponService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "范票列表";
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
        Page<Coupon> page = couponService.find(pageNum, length);

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

        return "新增范票";
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
    public Integer save(ServletRequest request,
                        Integer id,
                        String name,
                        String money,
                        Integer type,
                        String maxMoney,
                        String startDate,
                        String endDate,
                        MultipartRequest multipartRequest) {
        try {
            Coupon coupon = null;

            if (null == id) {
                coupon = new Coupon();
            } else {
                coupon = couponService.getById(id);
            }

            coupon.setName(name);
            coupon.setMoney(money);
            coupon.setType(type);
            if (type == 1) {
                coupon.setMaxMoney(maxMoney);
            } else {
                coupon.setMaxMoney("");
            }
            coupon.setStartDate(startDate);
            coupon.setEndDate(endDate);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                Map<String, Object> map = ImageUtil.saveImage(request, multipartFile, false);
                coupon.setCover(map.get("imgURL").toString());
            }

            if (null == id) {
                coupon.setCouponNum(System.currentTimeMillis() + "");
                coupon.setCreateTime(new Date());
                couponService.create(coupon);
            } else {
                couponService.update(coupon);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
