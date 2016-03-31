package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Coupon;
import com.sixmac.entity.Usercoupon;
import com.sixmac.service.CouponService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/coupon")
public class CouponApi extends CommonController {

    @Autowired
    private CouponService couponService;

    /**
     * @api {post} /api/coupon/add 添加优惠券
     *
     * @apiName coupon.add
     * @apiGroup coupon
     *
     * @apiParam {Integer} userId 用户id      <必传 />
     * @apiParam {String} couponNum 优惠券号        <必传 />
     */
    @RequestMapping("/add")
    public void add(HttpServletResponse response, Integer userId, String couponNum) {
        try {
            if (null == userId || null == couponNum) {
                WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
                return;
            }

            Coupon coupon = couponService.getByNum(couponNum);

            if (null == coupon) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0016));
                return;
            }

            // 先判断是否已经领取了，如果已经领取了，则返回码错误码
            Usercoupon usercoupon = couponService.getByUserIdAndCouponId(userId, coupon.getId());
            if (null != usercoupon) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0017));
                return;
            }

            // 添加用户优惠券关联
            couponService.addUserCouponInfo(userId, coupon);

            WebUtil.printApi(response, new Result(true).data(createMap("coupon", coupon)));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
        }
    }
}
