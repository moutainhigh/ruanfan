package com.sixmac.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "propertys")
public class Propertys {
    private Integer id;
    private String name;
    private String cover;
    private String serverHead;
    private String serverQQ;
    private String address;
    private String labels;
    private String description;
    private Integer parentId;
    private Integer showTurn;
    private Date createTime;
    private List<Propertys> childList = new ArrayList<Propertys>();
    private Integer childNum;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getServerHead() {
        return serverHead;
    }

    public void setServerHead(String serverHead) {
        this.serverHead = serverHead;
    }

    public String getServerQQ() {
        return serverQQ;
    }

    public void setServerQQ(String serverQQ) {
        this.serverQQ = serverQQ;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getShowTurn() {
        return showTurn;
    }

    public void setShowTurn(Integer showTurn) {
        this.showTurn = showTurn;
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
    public List<Propertys> getChildList() {
        return childList;
    }

    public void setChildList(List<Propertys> childList) {
        this.childList = childList;
    }

    @Transient
    public Integer getChildNum() {
        return childNum;
    }

    public void setChildNum(Integer childNum) {
        this.childNum = childNum;
    }
}
