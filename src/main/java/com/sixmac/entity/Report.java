package com.sixmac.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
@Entity
@Table(name = "report")
public class Report {
    @javax.persistence.Id
    private Integer id;
    private Integer userId;
    private Integer sourceId;
    private Integer type;
    private String mobile;
    private Integer isCut;
    private Integer isIgnore;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMobile() { return mobile;   }

    public void setMobile(String mobile) { this.mobile = mobile; }

    public Integer getIsCut() { return isCut;    }

    public void setIsCut(Integer isCut) { this.isCut = isCut;    }

    public Integer getIsIgnore() { return isIgnore;    }

    public void setIsIgnore(Integer isIgnore) { this.isIgnore = isIgnore;   }

    public String getDescription() { return description;  }

    public void setDescription(String description) { this.description = description;  }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
