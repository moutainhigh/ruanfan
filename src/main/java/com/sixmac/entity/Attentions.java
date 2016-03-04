package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "attentions")
public class Attentions {
    private int id;
    private int userId;
    private int objectId;
    private int objectType;

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

    @Column(name = "objectId")
    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    @Column(name = "objectType")
    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }
}
