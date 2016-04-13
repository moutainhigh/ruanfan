package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Gams;
import com.sixmac.entity.Image;
import com.sixmac.entity.Journal;
import com.sixmac.service.*;
import com.sixmac.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Iterator;
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

    @Autowired
    private CommentService commentService;

    /**
     * @api {post} /api/journal/list 日志列表
     * @apiName journal.list
     * @apiGroup journal
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
     * @apiSuccess {Object} list 日志列表
     * @apiSuccess {Integer} list.id 日志id
     * @apiSuccess {String} list.content 内容
     * @apiSuccess {Integer} list.forwardNum 转发数
     * @apiSuccess {Integer} list.shareNum 分享数
     * @apiSuccess {String} list.createTime 发布时间
     * @apiSuccess {Integer} list.gamsNum 点赞数
     * @apiSuccess {Integer} list.commentNum 评论数
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

        // 获取杂志的详情图片列表
        for (Journal journal : page.getContent()) {
            journal.setImageList(imageService.iFindList(journal.getId(), Constant.IMAGE_JOURNAL));
            journal.setGamsNum(gamsService.iFindList(journal.getId(), Constant.GAM_JOURNAL, Constant.GAM_LOVE, Constant.SORT_TYPE_DESC).size());
            journal.setCommentNum(commentService.iFindList(journal.getId(), Constant.COMMENT_JOURNAL).size());
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/journal/info 日志详情
     * @apiName journal.info
     * @apiGroup journal
     * @apiParam {Integer} journalId 日志id       <必传 />
     * @apiParam {Integer} userId 用户id
     * @apiSuccess {Object} journalInfo 日志详情
     * @apiSuccess {Integer} journalInfo.id 日志id
     * @apiSuccess {String} journalInfo.content 内容
     * @apiSuccess {Integer} journalInfo.forwardNum 转发数
     * @apiSuccess {Integer} journalInfo.shareNum 分享数
     * @apiSuccess {String} journalInfo.createTime 发布时间
     * @apiSuccess {String} journalInfo.imageList 日志图片列表
     * @apiSuccess {Integer} journalInfo.imageList.id 图片id
     * @apiSuccess {String} journalInfo.imageList.path 图片路径
     * @apiSuccess {String} journalInfo.imageList.description 图片描述
     * @apiSuccess {String} journalInfo.imageList.demo 图片备注
     * @apiSuccess {String} journalInfo.imageList.createTime 创建时间
     * @apiSuccess {Integer} journalInfo.gamsNum 点赞数
     * @apiSuccess {Integer} journalInfo.commentNum 评论数
     * @apiSuccess {Integer} journalInfo.isGam 是否点赞 0=是，1=否
     * @apiSuccess {Integer} journalInfo.isForward 是否转发 0=是，1=否
     */
    @RequestMapping(value = "/info")
    public void info(HttpServletResponse response, Integer journalId, Integer userId) {
        if (null == journalId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Journal journal = journalService.getById(journalId);

        if (null == journal) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        journal.getUser().setHeadPath(PathUtils.getRemotePath() + journal.getUser().getHeadPath());
        journal.setImageList(imageService.iFindList(journal.getId(), Constant.IMAGE_JOURNAL));
        journal.setGamsNum(gamsService.iFindList(journalId, Constant.GAM_JOURNAL, Constant.GAM_LOVE, Constant.SORT_TYPE_DESC).size());
        journal.setCommentNum(commentService.iFindList(journalId, Constant.COMMENT_JOURNAL).size());
        journal.setCommentList(commentService.iFindList(journalId, Constant.COMMENT_JOURNAL));
        journal.setGamsList(gamsService.iFindList(journalId, Constant.GAM_JOURNAL, Constant.GAM_LOVE, Constant.SORT_TYPE_DESC));

        if (null != userId) {
            Gams gams = gamsService.iFindOne(userId, journalId, Constant.GAM_JOURNAL, Constant.GAM_LOVE);
            journal.setIsGam(null != gams ? Constant.GAM_LOVE_YES : Constant.GAM_LOVE_NO);

            Gams gams1 = gamsService.iFindOne(userId, journalId, Constant.GAM_JOURNAL, Constant.GAM_FORWARD);
            journal.setIsForward(null != gams1 ? Constant.GAM_LOVE_YES : Constant.GAM_LOVE_NO);
        }

        Result obj = new Result(true).data(createMap("journalInfo", journal));
        String result = JsonUtil.obj2ApiJson(obj, "user", "labelList");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/journal/addJournal 发布日志
     * @apiName journal.addJournal
     * @apiGroup journal
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {String} content 内容       <必传 />
     * @apiParam {Object} imagesMap 图片数组Map
     */
    @RequestMapping(value = "/addJournal")
    public void addJournal(ServletRequest request, HttpServletResponse response, Integer userId, String content, MultipartHttpServletRequest multipartRequest) {
        if (null == userId || null == content) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

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

            // 获取图片集合
            Iterator<String> fileList = multipartRequest.getFileNames();
            while (fileList.hasNext()) {
                String fileName = fileList.next();
                MultipartFile file = multipartRequest.getFile(fileName);
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
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * @api {post} /api/journal/forward 转发日志
     * @apiName journal.forward
     * @apiGroup journal
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {Integer} journalId 日志id    <必传 />
     * @apiParam {String} content 内容
     */
    @RequestMapping(value = "/forward")
    public void forward(HttpServletResponse response, Integer userId, Integer journalId, String content) {
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
        gam.setDescription(content);

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
