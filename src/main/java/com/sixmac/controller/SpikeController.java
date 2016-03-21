package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Spikes;
import com.sixmac.service.SpikesService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
@Controller
@RequestMapping(value = "spikes")
public class SpikeController extends CommonController {

    @Autowired
    private SpikesService spikesService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "秒杀列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String name,
                     Integer status,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);

        name = "%" + name + "%";

        Page<Spikes> page = spikesService.page(name, status, pageNum, length);

        // 判断当前秒杀的状态
        for (Spikes spike : page.getContent()) {
            if (spike.getStartTime().after(new Date())) {
                spike.setStatus(0);
            }
            if (spike.getStartTime().before(new Date()) && spike.getEndTime().after(new Date())) {
                spike.setStatus(1);
            }
            if (spike.getEndTime().before(new Date())) {
                spike.setStatus(2);
            }
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Spikes spikes = spikesService.getById(id);
            model.addAttribute("spikes", spikes);
        }

        return "新增秒杀";
    }

    /**
     * 删除秒杀信息
     *
     * @param spikeId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer spikeId) {
        try {
            spikesService.deleteById(spikeId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
