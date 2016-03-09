package com.sixmac.entity.vo;

/**
 * Created by Administrator on 2016/3/9 0009.
 */

import java.util.Date;

/**
 * 验证码Vo类
 */
public class CodeVo {

    /**
     * 关联手机号
     */
    private String mobile;

    /**
     * 验证码
     */
    private String code;

    /**
     * 验证码类型
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
