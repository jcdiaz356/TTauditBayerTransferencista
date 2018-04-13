package com.dataservicios.ttauditbayertransferencista.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jcdia on 22/05/2017.
 */

public class AuditRoadStore {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private int road_id;
    @DatabaseField
    private int audit_id;
    @DatabaseField
    private int store_id;
    @DatabaseField
    private int visit_id;
    @DatabaseField
    private int auditStatus;
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private Audit list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoad_id() {
        return road_id;
    }

    public void setRoad_id(int road_id) {
        this.road_id = road_id;
    }

    public int getAudit_id() {
        return audit_id;
    }

    public void setAudit_id(int audit_id) {
        this.audit_id = audit_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public int getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(int visit_id) {
        this.visit_id = visit_id;
    }

    public Audit getList() {
        return list;
    }

    public void setList(Audit list) {
        this.list = list;
    }
}
