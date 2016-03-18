package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
@Entity
@Table(name = "propertyinfo")
public class Propertyinfo {
    private Integer id;
    private Propertys property;
    private String path;
    private String serverPath;
    private String url;
    private String qq;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "propertyId")
    public Propertys getProperty() {
        return property;
    }

    public void setProperty(Propertys property) {
        this.property = property;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
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
