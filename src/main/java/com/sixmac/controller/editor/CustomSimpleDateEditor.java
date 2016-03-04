package com.sixmac.controller.editor;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangbin on 2015/9/16.
 */
public class CustomSimpleDateEditor  extends PropertyEditorSupport {


    public String getAsText() {
        Date value = (Date) getValue();
        if (null == value) {
            value = new Date();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(value);
    }

    public void setAsText(String text) throws IllegalArgumentException {
        Date value = null;
        if (null != text && !text.equals("")) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                value = df.parse(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setValue(value);
    }
}
