package com.dataservicios.ttauditbayertransferencista.model;

import com.j256.ormlite.field.DatabaseField;

public class ProductPlanSale {
    @DatabaseField(id = true)
    private int     id;
    @DatabaseField
    private int     product_id;
    @DatabaseField
    private int     store_id;
    @DatabaseField
    private String  prom6m;
    @DatabaseField
    private String  cuotames ;
    @DatabaseField
    private String  avance ;
    @DatabaseField
    private int     stock_min;
    @DatabaseField
    private int     stock_max ;
    @DatabaseField
    private int     company_id;
    @DatabaseField
    private int     visit_id;
    @DatabaseField
    private int     list_prices;
    @DatabaseField
    private int     provider_id;
    @DatabaseField
    private int     quantity;
    @DatabaseField
    private String  price;
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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getProm6m() {
        return prom6m;
    }

    public void setProm6m(String prom6m) {
        this.prom6m = prom6m;
    }

    public String getCuotames() {
        return cuotames;
    }

    public void setCuotames(String cuotames) {
        this.cuotames = cuotames;
    }

    public String getAvance() {
        return avance;
    }

    public void setAvance(String avance) {
        this.avance = avance;
    }

    public int getStock_min() {
        return stock_min;
    }

    public void setStock_min(int stock_min) {
        this.stock_min = stock_min;
    }

    public int getStock_max() {
        return stock_max;
    }

    public void setStock_max(int stock_max) {
        this.stock_max = stock_max;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(int visit_id) {
        this.visit_id = visit_id;
    }

    public int getList_prices() {
        return list_prices;
    }

    public void setList_prices(int list_prices) {
        this.list_prices = list_prices;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
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
