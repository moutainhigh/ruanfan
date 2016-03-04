package com.sixmac.controller.api;

import com.sixmac.core.Constant;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Afflatus;
import com.sixmac.service.AfflatusService;
import com.sixmac.utils.WebUtil;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Controller
@RequestMapping(value = "api/afflatus")
public class AfflatusApi {

    @Autowired
    private AfflatusService afflatusService;

    @RequestMapping(value = "/list")
    public void login(HttpServletRequest request,
                      HttpServletResponse response,
                      String type,
                      Integer styleId,
                      Integer areaId,
                      Integer pageNum,
                      Integer pageSize) {
        Page<Afflatus> page = afflatusService.iPage(type, styleId, areaId, pageNum, pageSize);

        WebUtil.print(response, new Result(true).data(page));
    }
}
