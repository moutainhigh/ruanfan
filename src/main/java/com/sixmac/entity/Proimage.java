package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Entity
@Table(name = "proimage")
public class Proimage {
    private int id;
    private Propertys propertys;
    private String path;
    private String head;
    private String url;
    private String qq;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "propertysId")
    public Propertys getPropertys() {
        return propertys;
    }

    public void setPropertys(Propertys propertys) {
        this.propertys = propertys;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
