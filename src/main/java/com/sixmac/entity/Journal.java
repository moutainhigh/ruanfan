package com.sixmac.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "journal")
public class Journal {
    private Integer id;
    private Users user;
    private String content;
    private Integer forwardNum;
    private Integer shareNum;
    private Date createTime;
    private List<Image> imageList = new ArrayList<Image>();
    private Integer gamsNum;
    private Integer isGam;
    private Integer isForward;
    private Integer commentNum;
    private List<Comment> commentList = new ArrayList<Comment>();
    private List<Gams> gamsList = new ArrayList<Gams>();

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getForwardNum() {
        return forwardNum;
    }

    public void setForwardNum(Integer forwardNum) {
        this.forwardNum = forwardNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Transient
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    @Transient
    public Integer getGamsNum() {
        return gamsNum;
    }

    public void setGamsNum(Integer gamsNum) {
        this.gamsNum = gamsNum;
    }

    @Transient
    public Integer getIsGam() {
        return isGam;
    }

    public void setIsGam(Integer isGam) {
        this.isGam = isGam;
    }

    @Transient
    public Integer getIsForward() {
        return isForward;
    }

    public void setIsForward(Integer isForward) {
        this.isForward = isForward;
    }

    @Transient
    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    @Transient
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Transient
    public List<Gams> getGamsList() {
        return gamsList;
    }

    public void setGamsList(List<Gams> gamsList) {
        this.gamsList = gamsList;
    }
}
