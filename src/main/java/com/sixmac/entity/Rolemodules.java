package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "rolemodules")
public class Rolemodules {
    private int id;
    private Roles role;
    private Modules module;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "roleId")
    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "moduleId")
    public Modules getModule() {
        return module;
    }

    public void setModule(Modules module) {
        this.module = module;
    }
}
