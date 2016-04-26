package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Message;
import com.sixmac.service.MessageService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by yesong on 2016/3/28 0028.
 * 书籍管理
 */
@Controller
@RequestMapping(value = "backend/message")
public class MessageController extends CommonController {

    @Autowired
    private MessageService service;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "backend/消息列表";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String title,
                     String type,
                     String description,
                     Integer draw,
                     Integer start,
                     Integer length) {

        int pageNum = getPageNum(start, length);

        Page<Message> page = service.page(title, type, description, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap model, Integer id) {
        // 如果id不为空，则代表编辑
        if (null != id) {
            Message message = service.getById(id);
            model.addAttribute("message", message);
        }

        return "backend/添加消息";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Integer save(Integer id, String title, String types, String desc, String description) {
        try {
            Message message = null;
            if (id == null) {
                message = new Message();
            } else {
                message = service.getById(id);
            }

            message.setTitle(title);
            message.setTypes(types.substring(0, types.length() - 1));
            message.setDesc(desc);
            message.setDescription(description);

            if (null == id) {
                message.setCreateTime(new Date());
                service.create(message);
            } else {
                message.setUpdateTime(new Date());
                service.update(message);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(ServletRequest request, HttpServletResponse response, Integer id) {
        if (id != null) {
            service.deleteById(id);
            return 1;
        } else {
            WebUtil.printJson(response, new Result(false).msg("日志不存在"));
        }
        return 0;
    }

    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(String ids) {
        try {
            // 将界面上的id数组格式的字符串解析成int类型的数组
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            service.deleteAll(arrayId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}

