package com.sixmac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/19 0019.
 */
@Entity
@Table(name = "letterreply")
public class Letterreply {

    private Integer id;
    private Privateletter privateletter;
    private Users users;
    private String content;
    private Date createTime;
    private Integer sendUserId;
    private String sendUserName;
    private String sendUserHead;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "letterId")
    public Privateletter getPrivateletter() {
        return privateletter;
    }

    public void setPrivateletter(Privateletter privateletter) {
        this.privateletter = privateletter;
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
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
    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    @Transient
    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    @Transient
    public String getSendUserHead() {
        return sendUserHead;
    }

    public void setSendUserHead(String sendUserHead) {
        this.sendUserHead = sendUserHead;
    }
}
