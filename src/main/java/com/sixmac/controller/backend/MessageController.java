package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Message;
import com.sixmac.service.MessageService;
import com.sixmac.service.OperatisService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 消息管理
 */
@Controller
@RequestMapping(value = "backend/message")
public class MessageController extends CommonController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private OperatisService operatisService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "backend/消息列表";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void list(HttpServletResponse response,
                     String title,
                     String type,
                     String description,
                     Integer draw,
                     Integer start,
                     Integer length) {
        int pageNum = getPageNum(start, length);

        Page<Message> page = messageService.page(title, type, description, pageNum, length);

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap model, Integer id) {
        // 如果id不为空，则代表编辑
        if (null != id) {
            Message message = messageService.getById(id);
            model.addAttribute("message", message);
        }

        return "backend/添加消息";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        Integer id,
                        String title,
                        String types,
                        String des,
                        String description) {
        try {
            Message message = null;
            if (id == null) {
                message = new Message();
            } else {
                message = messageService.getById(id);
            }

            message.setTitle(title);
            message.setTypes(types.substring(0, types.length() - 1));
            message.setDes(des);
            message.setDescription(description);

            if (null == id) {
                message.setCreateTime(new Date());
                messageService.create(message);
            } else {
                message.setUpdateTime(new Date());
                messageService.update(message);
            }

            operatisService.addOperatisInfo(request, null == id ? "新增" : "修改" + "消息 " + message.getTitle());

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(HttpServletRequest request, Integer id) {
        if (id != null) {
            messageService.deleteById(request, id);

            return 1;
        }
        return 0;
    }

    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(HttpServletRequest request, String ids) {
        try {
            // 将界面上的id数组格式的字符串解析成int类型的数组
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            messageService.deleteAll(request, arrayId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}

