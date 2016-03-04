package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "rolemodules")
public class Rolemodules {
    private int id;
    private int roleId;
    private int moduleId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "roleId")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Column(name = "moduleId")
    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }
}
