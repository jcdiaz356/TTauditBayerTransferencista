package com.dataservicios.ttauditbayertransferencista.model;

import com.j256.ormlite.field.DatabaseField;

public class PurcharseOrder {

    @DatabaseField(generatedId = true)
    private int     id;
    @DatabaseField
    private String  code;
    @DatabaseField
    private int     company_id;
    @DatabaseField
    private int     store_id;
    @DatabaseField
    private int     visit_id;
    @DatabaseField
    private int     product_id;
    @DatabaseField
    private int     provider_id;
    @DatabaseField
    private int     user_id;
    @DatabaseField
    private int     quantity;
    @DatabaseField
    private String  price;
    @DatabaseField
    private String  mount;
    @DatabaseField
    private String  created_at;
    @DatabaseField
    private String  updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(int visit_id) {
        this.visit_id = visit_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMount() {
        return mount;
    }

    public void setMount(String mount) {
        this.mount = mount;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
