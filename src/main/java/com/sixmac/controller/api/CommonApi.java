package com.sixmac.controller.api;

import com.sixmac.common.exception.GeneralException;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Collect;
import com.sixmac.entity.Journal;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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

    @Autowired
    private JournalService journalService;

    @Autowired
    private WorksService worksService;

    /**
     * 收藏列表
     *
     * @param response
     * @param userId
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/collectList")
    public void collectList(HttpServletResponse response,
                            Integer userId,
                            Integer pageNum,
                            Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Collect> page = collectService.iPage(userId, pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user");
        WebUtil.printApi(response, result);
    }

    /**
     * 收藏 or 取消收藏
     *
     * @param response
     * @param userId
     * @param objectId
     * @param objectType
     * @param action
     */
    @RequestMapping("/collect")
    public void collect(HttpServletResponse response, Integer userId, Integer objectId, Integer objectType, Integer action) {
        if (null == userId || null == objectId || null == objectType || null == action) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Collect collect = collectService.iFindOne(userId, objectId, objectType);

        // action（收藏状态字段）,0表示收藏，1表示取消收藏
        if (action == 0) {
            try {
                if (null != collect) {
                    WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0009));
                    return;
                }

                collectService.iCreate(usersService.getById(userId), objectId, objectType);
            } catch (GeneralException e) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
                return;
            }
        } else {
            if (null == collect) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0010));
                return;
            }

            collectService.deleteById(collect.getId());
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 分享
     *
     * @param response
     * @param objectId
     * @param objectType
     */
    @RequestMapping("/share")
    public void share(HttpServletResponse response, Integer objectId, Integer objectType) {
        if (null == objectId || null == objectType) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 根据传入的类型判断分享的类型
        // 分享类型，1=灵感集，2=日志
        switch (objectType) {
            case 1:
                // 增加灵感集的分享数
                Afflatus afflatus = afflatusService.getById(objectId);

                if (null == afflatus) {
                    WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
                    return;
                }

                afflatus.setShareNum(afflatus.getShareNum() + 1);

                afflatusService.update(afflatus);
                break;
            case 2:
                // 增加日志的分享数
                Journal journal = journalService.getById(objectId);

                if (null == journal) {
                    WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
                    return;
                }

                journal.setShareNum(journal.getShareNum() + 1);

                journalService.update(journal);
                break;
        }

        WebUtil.printApi(response, new Result(true));
    }
}
