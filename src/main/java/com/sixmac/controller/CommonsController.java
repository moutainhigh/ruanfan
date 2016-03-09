package com.sixmac.controller;

import com.sixmac.entity.Areas;
import com.sixmac.entity.Styles;
import com.sixmac.service.AreasService;
import com.sixmac.service.StylesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/3/9 0009.
 */
@Controller
@RequestMapping(value = "common")
public class CommonsController {

    @Autowired
    private StylesService stylesService;

    @Autowired
    private AreasService areasService;

    /**
     * 风格列表
     * @return
     */
    @RequestMapping("/styleList")
    @ResponseBody
    public List<Styles> styleList() {
        return stylesService.findAll();
    }

    /**
     * 区域列表
     * @return
     */
    @RequestMapping("/areaList")
    @ResponseBody
    public List<Areas> areaList() {
        return areasService.findAll();
    }
}
