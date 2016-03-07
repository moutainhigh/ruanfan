package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
@Entity
@Table(name = "collect")
public class Collect {
    private int id;
    private Users user;
    private Integer objectId;
    private Integer objectType;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
