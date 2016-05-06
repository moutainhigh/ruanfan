package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/5/6 0006.
 */
@Entity
@Table(name = "recordCount")
public class RecordCount {

    private Integer id;
    private Integer visitCount;
    private Integer onlineCount;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }
}
