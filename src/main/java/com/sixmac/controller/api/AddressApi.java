package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Address;
import com.sixmac.service.AddressService;
import com.sixmac.service.UsersService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
@Controller
@RequestMapping(value = "api/address")
public class AddressApi extends CommonController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UsersService usersService;

    /**
     * @api {post} /api/address/list 收货地址列表
     * @apiName address.list
     * @apiGroup address
     * @apiParam {Integer} userId 用户id      <必传 />
     * @apiSuccess {Object} list 收货地址列表
     * @apiSuccess {Integer} list.id 收货地址id
     * @apiSuccess {Integer} list.name 收货人姓名
     * @apiSuccess {Integer} list.mobile 收货人手机号
     * @apiSuccess {Integer} list.address 收货地址
     * @apiSuccess {Integer} list.isDefault 是否为默认收货地址：0=否，1=是
     * @apiSuccess {Integer} list.createTime 创建时间
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer userId) {
        if (null == userId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        List<Address> list = addressService.findListByUserId(userId);

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj, "user");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/address/updateInfo 修改收货地址
     * @apiName address.updateInfo
     * @apiGroup address
     * @apiParam {Integer} addressId 收货地址id       <必传 />
     * @apiParam {String} name 收货人姓名
     * @apiParam {String} mobile 收货人手机号
     * @apiParam {String} address 收货地址
     * @apiParam {Integer} isDefault 是否为默认收货地址：0=否，1=是
     */
    @RequestMapping(value = "/updateInfo")
    public void updateInfo(HttpServletResponse response,
                           Integer addressId,
                           String name,
                           String mobile,
                           String address,
                           Integer isDefault) {
        if (null == addressId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Address addressInfo = addressService.getById(addressId);

        if (null == addressInfo) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0023));
            return;
        }

        addressInfo.setName(name);
        addressInfo.setMobile(mobile);
        addressInfo.setAddress(address);

        Address defaultAddress = addressService.findDefaultByUserId(addressInfo.getUser().getId());

        if (isDefault == 0) {
            if (null == defaultAddress) {
                addressInfo.setIsDefault(1);
            }
        } else {
            if (null != defaultAddress) {
                defaultAddress.setIsDefault(0);
                addressService.update(defaultAddress);
            }

            addressInfo.setIsDefault(1);
        }

        addressService.update(addressInfo);

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * @api {post} /api/address/addInfo 新增收货地址
     * @apiName address.addInfo
     * @apiGroup address
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {String} name 收货人姓名       <必传 />
     * @apiParam {String} mobile 收货人手机号       <必传 />
     * @apiParam {String} address 收货地址       <必传 />
     * @apiParam {Integer} isDefault 是否为默认收货地址：0=否，1=是
     */
    @RequestMapping(value = "/addInfo")
    public void addInfo(HttpServletResponse response,
                        Integer userId,
                        String name,
                        String mobile,
                        String address,
                        Integer isDefault) {
        if (null == userId || null == name || null == mobile || null == address) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Address addressInfo = new Address();
        addressInfo.setUser(usersService.getById(userId));
        addressInfo.setName(name);
        addressInfo.setMobile(mobile);
        addressInfo.setAddress(address);

        Address defaultAddress = addressService.findDefaultByUserId(userId);

        if (null == isDefault || isDefault == 0) {
            if (null == defaultAddress) {
                addressInfo.setIsDefault(1);
            }
        } else {
            if (null != defaultAddress) {
                defaultAddress.setIsDefault(0);
                addressService.update(defaultAddress);
            }

            addressInfo.setIsDefault(1);
        }

        addressService.create(addressInfo);

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * @api {post} /api/address/delInfo 删除收货地址
     * @apiName address.delInfo
     * @apiGroup address
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {Integer} addressId 收货地址id       <必传 />
     */
    @RequestMapping(value = "/delInfo")
    public void delInfo(HttpServletResponse response,
                        Integer userId,
                        Integer addressId) {
        if (null == userId || null == addressId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 判断是否是该用户的收货地址
        Address address = addressService.findOneByIdAndUserId(addressId, userId);

        if (null == address) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0024));
            return;
        }

        // 判断当前收货地址是否是默认地址，如果是，则将该用户的第一个收货地址更改为默认地址
        Address oldAddress = addressService.getById(addressId);

        if (oldAddress.getIsDefault() == 1) {
            List<Address> list = addressService.findListByUserId(userId);
            if (null != list && list.size() > 0) {
                oldAddress = list.get(0);
                oldAddress.setIsDefault(0);

                addressService.update(oldAddress);
            }
        }

        addressService.deleteById(addressId);

        WebUtil.printApi(response, new Result(true));
    }
}
