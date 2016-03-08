package com.sixmac.controller.api;

import com.sixmac.common.exception.GeneralException;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Collect;
import com.sixmac.service.AfflatusService;
import com.sixmac.service.CollectService;
import com.sixmac.service.UsersService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/common")
public class CommonApi {

    @Autowired
    private CollectService collectService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private AfflatusService afflatusService;

    @RequestMapping("/collect")
    public void collect(HttpServletResponse response, Integer userId, Integer objectId, Integer objectType, Integer action) {
        // action（收藏状态字段）,0表示收藏，1表示取消收藏
        String msg = "";
        if (null == userId || null == objectId || null == objectType || null == action) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        if (action == 0) {
            try {
                collectService.iCreate(usersService.getById(userId), objectId, objectType);
            } catch (GeneralException e) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
            }
            msg = "收藏成功";
        } else {
            Collect collect = collectService.iFindOne(userId, objectId, objectType);
            if (null != collect) {
                collectService.deleteById(collect.getId());
            }
            msg = "取消收藏成功";
        }

        WebUtil.printApi(response, new Result(true).msg(msg));
    }

    @RequestMapping("/share")
    public void share(HttpServletResponse response, Integer objectId, Integer objectType) {
        if (null == objectId || null == objectType) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 根据传入的类型判断分享的类型
        switch (objectType) {
            case 1:
                break;
            case 2:
                break;
        }

        // 增加灵感集的分享数
        Afflatus afflatus = afflatusService.getById(objectId);

        if (null == afflatus) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
        }

        afflatus.setShareNum(afflatus.getShareNum() + 1);

        afflatusService.update(afflatus);

        WebUtil.printApi(response, new Result(true));
    }
}
