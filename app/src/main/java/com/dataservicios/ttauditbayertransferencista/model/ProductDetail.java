package com.dataservicios.ttauditbayertransferencista.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jcdia on 6/06/2017.
 */

public class ProductDetail {
    @DatabaseField(generatedId = true)
    private int     id;
    @DatabaseField
    private int     product_id;
    @DatabaseField
    private int     company_id;
    @DatabaseField
    private String fullname;
    @DatabaseField
    private int     category_product_id;
    @DatabaseField
    private String precio;
    @DatabaseField
    private String imagen;
    @DatabaseField
    private String composicion;
    @DatabaseField
    private String fabricante;
    @DatabaseField
    private String presentacion;
    @DatabaseField
    private String unidad;
    @DatabaseField
    private String type;
    @DatabaseField
    private int     status;
    @DatabaseField
    private String created_at;
    @DatabaseField
    private String updated_at;

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getCategory_product_id() {
        return category_product_id;
    }

    public void setCategory_product_id(int category_product_id) {
        this.category_product_id = category_product_id;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getComposicion() {
        return composicion;
    }

    public void setComposicion(String composicion) {
        this.composicion = composicion;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
