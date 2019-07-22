package com.dataservicios.ttauditbayertransferencista.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Jaime on 27/08/2016.
 */
public class Media {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private int store_id;
    @DatabaseField
    private int poll_id;
    @DatabaseField
    private int publicity_id;
    @DatabaseField
    private int category_product_id;
    @DatabaseField
    private int product_id;
    @DatabaseField
    private int company_id;
    @DatabaseField
    private String monto ;
    @DatabaseField
    private String razonSocial;
    @DatabaseField
    private int type ;
    @DatabaseField
    private String file ;
    @DatabaseField
    private String log ;
    @DatabaseField
    private int status; // 0->default, inicio de registo; 1-> no se encontro el archivo
    @DatabaseField
    private Boolean selectedFile;
    @DatabaseField
    private String pathFile;
    @DatabaseField
    private int status_send ;
    @DatabaseField
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(int poll_id) {
        this.poll_id = poll_id;
    }

    public int getPublicity_id() {
        return publicity_id;
    }

    public void setPublicity_id(int publicity_id) {
        this.publicity_id = publicity_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCategory_product_id() {
        return category_product_id;
    }

    public void setCategory_product_id(int category_product_id) {
        this.category_product_id = category_product_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Boolean getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(Boolean selectedFile) {
        this.selectedFile = selectedFile;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus_send() {
        return status_send;
    }

    public void setStatus_send(int status_send) {
        this.status_send = status_send;
    }
}
