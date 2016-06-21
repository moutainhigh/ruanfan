package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "versions")
public class Versions {
    private Integer id;
    private String content;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
