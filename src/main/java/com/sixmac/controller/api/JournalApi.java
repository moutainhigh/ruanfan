package com.sixmac.controller.api;

import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Image;
import com.sixmac.entity.Journal;
import com.sixmac.service.ImageService;
import com.sixmac.service.JournalService;
import com.sixmac.service.UsersService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.ImageUtil;
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
public class JournalApi {

    @Autowired
    private JournalService journalService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UsersService usersService;

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
        WebUtil.printApi(response, new Result(true).data(dataMap));
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
        }

        WebUtil.printApi(response, new Result(true).data(journal));
    }

    /**
     * 发布日志
     *
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
}
