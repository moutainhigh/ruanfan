package com.sixmac.entity.vo;

/**
 * Created by Administrator on 2016/4/7 0007.
 */

import java.util.Date;

/**
 * 商品评价
 */
public class AppraisalVo {

    /**
     * 评价人id
     */
    private Integer userId;

    /**
     * 评价人名称
     */
    private String userName;

    /**
     * 评价星级
     */
    private Integer star;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价时间
     */
    private Date createTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
