package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "gams")
public class Gams {
    private int id;
    private Integer userId;
    private Integer objectId;
    private Integer objectType;
    private int type;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "userId")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "objectId")
    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    @Column(name = "objectType")
    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
