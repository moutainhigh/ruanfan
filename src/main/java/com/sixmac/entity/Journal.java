package com.sixmac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "journal")
public class Journal {
    private int id;
    private int userId;
    private String content;
    private Integer forwardNum;
    private Date createTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "userId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "forwardNum")
    public Integer getForwardNum() {
        return forwardNum;
    }

    public void setForwardNum(Integer forwardNum) {
        this.forwardNum = forwardNum;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
