package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Gams;
import com.sixmac.entity.Image;
import com.sixmac.entity.Journal;
import com.sixmac.service.GamsService;
import com.sixmac.service.ImageService;
import com.sixmac.service.JournalService;
import com.sixmac.service.UsersService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.ImageUtil;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/journal")
public class JournalApi extends CommonController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private GamsService gamsService;

    /**
     * 日志列表
     *
     * @param response
     * @param userId
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer userId,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Journal> page = journalService.iPage(userId, pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user");
        WebUtil.printApi(response, result);
    }

    /**
     * 根据日志id查询日志信息
     *
     * @param response
     * @param journalId
     */
    @RequestMapping(value = "/info")
    public void info(HttpServletResponse response, Integer journalId) {
        if (null == journalId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Journal journal = journalService.getById(journalId);

        if (null == journal) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        Result obj = new Result(true).data(createMap("journalInfo", journal));
        String result = JsonUtil.obj2ApiJson(obj, "user");
        WebUtil.printApi(response, result);
    }

    /**
     * 发布日志
     *
     * @param request
     * @param response
     * @param userId
     * @param content
     * @param multipartRequest
     */
    @RequestMapping(value = "/addJournal")
    public void addJournal(ServletRequest request, HttpServletResponse response, Integer userId, String content, MultipartRequest multipartRequest) {
        if (null == userId || null == content) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 获取图片集合
        List<MultipartFile> list = multipartRequest.getFiles("imgList");

        Journal journal = new Journal();
        journal.setUser(usersService.getById(userId));
        journal.setContent(content);
        journal.setCreateTime(new Date());
        journal.setForwardNum(0);
        journal.setShareNum(0);

        // 保存日志信息
        journalService.create(journal);

        try {
            // 保存日志图片集合
            Map<String, Object> map = null;
            Image image = null;
            for (MultipartFile file : list) {
                if (null != file) {
                    map = ImageUtil.saveImage(request, file, false);
                    image = new Image();
                    image.setPath(map.get("imgURL").toString());
                    image.setWidth(map.get("imgWidth").toString());
                    image.setHeight(map.get("imgHeight").toString());
                    image.setObjectId(journal.getId());
                    image.setObjectType(Constant.IMAGE_JOURNAL);
                    image.setCreateTime(new Date());

                    imageService.create(image);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 转发日志
     *
     * @param response
     * @param userId
     * @param journalId
     */
    @RequestMapping(value = "/forward")
    public void forward(HttpServletResponse response, Integer userId, Integer journalId) {
        if (null == userId || null == journalId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 查询目标日志信息
        Journal journal = journalService.getById(journalId);

        if (null == journal) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        // 自己不能转发自己的日志
        if (journal.getUser().getId() == userId) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0011));
            return;
        }

        // 已转发过的日志，不能重复转发
        List<Gams> gamsList = gamsService.iFindList(journalId, Constant.GAM_JOURNAL, Constant.GAM_FORWARD, Constant.SORT_TYPE_ASC);
        Boolean flag = false;
        for (Gams gams : gamsList) {
            if (gams.getObjectId() == journalId) {
                flag = true;
            }
        }

        if (flag) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0012));
            return;
        }

        // 保存转发记录
        Gams gam = new Gams();
        gam.setUser(usersService.getById(userId));
        gam.setObjectId(journalId);
        gam.setObjectType(Constant.GAM_JOURNAL);
        gam.setType(Constant.GAM_FORWARD);

        gamsService.create(gam);

        // 复制目标日志信息
        Journal newJournal = new Journal();
        newJournal.setUser(usersService.getById(userId));
        newJournal.setContent(journal.getContent());
        newJournal.setShareNum(0);
        newJournal.setForwardNum(0);
        newJournal.setCreateTime(new Date());

        // 保存日志信息
        journalService.create(newJournal);

        // 将目标日志的图片信息一同复制
        List<Image> list = imageService.iFindList(journalId, Constant.IMAGE_JOURNAL);

        Image tempImage = null;
        for (Image image : list) {
            tempImage = new Image();
            tempImage.setPath(image.getPath());
            tempImage.setWidth(image.getWidth());
            tempImage.setHeight(image.getHeight());
            tempImage.setObjectId(newJournal.getId());
            tempImage.setObjectType(Constant.IMAGE_JOURNAL);
            tempImage.setCreateTime(new Date());

            imageService.create(tempImage);
        }

        // 修改目标日志的转发量
        journal.setForwardNum(journal.getForwardNum() + 1);
        journalService.update(journal);

        WebUtil.printApi(response, new Result(true));
    }
}
