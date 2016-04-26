package com.sixmac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/19 0019.
 */
@Entity
@Table(name = "privateletter")
public class Privateletter {

    private Integer id;
    private Users sendUser;
    private Users receiveUser;
    private String content;
    private Date createTime;
    private Integer userId;
    private String userName;
    private String userHead;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "sendUserId")
    public Users getSendUser() {
        return sendUser;
    }

    public void setSendUser(Users sendUser) {
        this.sendUser = sendUser;
    }

    @ManyToOne
    @JoinColumn(name = "receiveUserId")
    public Users getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(Users receiveUser) {
        this.receiveUser = receiveUser;
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

    @Transient
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Transient
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Transient
    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }
}
