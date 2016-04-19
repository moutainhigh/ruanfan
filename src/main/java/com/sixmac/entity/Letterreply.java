package com.sixmac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/19 0019.
 */
@Entity
@Table(name = "letterreply")
public class Letterreply {

    private Integer id;
    private Privateletter privateletter;
    private Users users;
    private String content;
    private Date createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
