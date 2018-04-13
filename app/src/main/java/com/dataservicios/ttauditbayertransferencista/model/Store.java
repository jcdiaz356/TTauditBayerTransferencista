package com.dataservicios.ttauditbayertransferencista.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jcdia on 12/05/2017.
 */

public class Store {

    @DatabaseField(id = true)
    private int    id;
    @DatabaseField
    private int    route_id;
    @DatabaseField
    private int    visit_id;
    @DatabaseField
    private String cadenRuc;
    @DatabaseField
    private String codCliente;
    @DatabaseField
    private String document;
    @DatabaseField
    private String typo_document;
    @DatabaseField
    private String type;
    @DatabaseField
    private String chanel;
    @DatabaseField
    private String visit;
    @DatabaseField
    private String fullname;
    @DatabaseField
    private String region;
    @DatabaseField
    private String typeBodega;
    @DatabaseField
    private String address;
    @DatabaseField
    private String district;
    @DatabaseField
    private String urbanization;
    @DatabaseField
    private String ejecutivo;
    @DatabaseField
    private double latitude;
    @DatabaseField
    private double longitude;
    @DatabaseField
    private String telephone;
    @DatabaseField
    private String cell;
    @DatabaseField
    private String owner;
    @DatabaseField
    private String fnac;
    @DatabaseField
    private int    status;
    @DatabaseField
    private int    status_change;
    @DatabaseField
    private String comment;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public int getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(int visit_id) {
        this.visit_id = visit_id;
    }

    public String getCadenRuc() {
        return cadenRuc;
    }

    public void setCadenRuc(String cadenRuc) {
        this.cadenRuc = cadenRuc;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getTypo_document() {
        return typo_document;
    }

    public void setTypo_document(String typo_document) {
        this.typo_document = typo_document;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTypeBodega() {
        return typeBodega;
    }

    public void setTypeBodega(String typeBodega) {
        this.typeBodega = typeBodega;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUrbanization() {
        return urbanization;
    }

    public void setUrbanization(String urbanization) {
        this.urbanization = urbanization;
    }

    public String getEjecutivo() {
        return ejecutivo;
    }

    public void setEjecutivo(String ejecutivo) {
        this.ejecutivo = ejecutivo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFnac() {
        return fnac;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus_change() {
        return status_change;
    }

    public void setStatus_change(int status_change) {
        this.status_change = status_change;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
