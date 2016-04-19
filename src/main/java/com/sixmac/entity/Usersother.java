package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/9 0009.
 */
@Entity
@Table(name = "usersother")
public class Usersother {
    private Integer id;
    private Users user;
    private String openId;
    private Integer type;

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
