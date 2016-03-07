package com.sixmac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "reserve")
public class Reserve {
    private int id;
    private String name;
    private String mobile;
    private String email;
    private Users user;
    private Designers designer;
    private Date reseTime;
    private Styles style;
    private String address;
    private String remark;
    private String reseAddress;
    private Integer status;
    private Date createTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "designerId")
    public Designers getDesigner() {
        return designer;
    }

    public void setDesigner(Designers designer) {
        this.designer = designer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reseTime")
    public Date getReseTime() {
        return reseTime;
    }

    public void setReseTime(Date reseTime) {
        this.reseTime = reseTime;
    }

    @ManyToOne
    @JoinColumn(name = "styleId")
    public Styles getStyle() {
        return style;
    }

    public void setStyle(Styles style) {
        this.style = style;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "reseAddress")
    public String getReseAddress() {
        return reseAddress;
    }

    public void setReseAddress(String reseAddress) {
        this.reseAddress = reseAddress;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
