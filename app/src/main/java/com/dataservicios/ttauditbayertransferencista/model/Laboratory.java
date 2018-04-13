package com.dataservicios.ttauditbayertransferencista.model;

import com.j256.ormlite.field.DatabaseField;

public class Laboratory {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String fullname ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
