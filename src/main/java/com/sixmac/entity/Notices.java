package com.sixmac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "notices")
public class Notices {
    private int id;
    private Integer sourceId;
    private Integer sourceType;
    private Integer objectId;
    private String objectName;
    private Integer objectType;
    private Date createTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "sourceId")
    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "sourceType")
    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    @Column(name = "objectId")
    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    @Column(name = "objectName")
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Column(name = "objectType")
    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
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
