package com.sixmac.controller.common;

import com.sixmac.controller.editor.*;
import com.sixmac.core.Constant;
import com.sixmac.utils.DateUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.*;

/**
 * Created by wangbin on 2014/12/10.
 */
public class CommonController {

    public static Map<String, Object> emptyData = null;

    static {
        emptyData = new HashMap<String, Object>();
        emptyData.put("data", new ArrayList());
        emptyData.put("iTotalRecords", 0);
        emptyData.put("iTotalDisplayRecords", 0);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // binder.registerCustomEditor(String.class, new CustomStringEditor());
        binder.registerCustomEditor(MultipartFile.class, new CustomFileEditor());
        binder.registerCustomEditor(Double.class, new CustomDoubleEditor());
        binder.registerCustomEditor(Float.class, new CustomFloatEditor());
        binder.registerCustomEditor(Integer.class, new CustomIntegerEditor());
        binder.registerCustomEditor(Long.class, new CustomLongEditor());
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
    }

    public Map<String, Object> createMap(String key, Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, obj);
        return map;
    }

    public Integer getPageNum(Integer start, Integer length) {
        if (start == null) {
            start = 0;
        }
        if (length == null) {
            length = 10;
        }

        int pageNum = (start / length) + 1;
        return pageNum;
    }

    /**
     * 获取指定天数的旧天数
     *
     * @return
     */
    public static Date getOldDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, Constant.days * -1);

        return calendar.getTime();
    }

    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date finallyDate = null;
        try {
            String yesterday = DateUtils.dateToStringWithFormat(calendar.getTime(), "yyyy-MM-dd hh:mm:ss");
            String days = yesterday.substring(0, 10);

            finallyDate = DateUtils.stringToDateWithFormat(days + " 00:00:00", "yyyy-MM-dd hh:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finallyDate;
    }
}
