package com.sixmac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/5 0005.
 */
@Entity
@Table(name = "mallpic")
public class MallPic {

    private Integer id;
    private String name;
    private String cover;
    private Date updateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
