package com.dataservicios.ttauditbayertransferencista.repo;

import android.content.Context;

import com.dataservicios.ttauditbayertransferencista.db.DatabaseHelper;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.AuditRoadStore;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jcdia on 22/05/2017.
 */

public class AuditRoadStoreRepo implements Crud {
    private DatabaseHelper helper;

    public AuditRoadStoreRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        AuditRoadStore object = (AuditRoadStore) item;
        try {
            index = helper.getAuditRoadStoreDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        AuditRoadStore object = (AuditRoadStore) item;

        try {
            helper.getAuditRoadStoreDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        AuditRoadStore object = (AuditRoadStore) item;

        try {
            helper.getAuditRoadStoreDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<AuditRoadStore> items = null;
        int counter = 0;
        try {
            items = helper.getAuditRoadStoreDao().queryForAll();

            for (AuditRoadStore object : items) {
                // do something with object
                helper.getAuditRoadStoreDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        AuditRoadStore wishList = null;
        try {
            wishList = helper.getAuditRoadStoreDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<AuditRoadStore> items = null;

        try {
            items = helper.getAuditRoadStoreDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }
    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getAuditRoadStoreDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }
    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getAuditRoadStoreDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Obtiene un List de AuditRoadStore por store_id
     * @param store_id
     * @return
     */
    public List<AuditRoadStore> findByStoreId(int store_id) {

        List<AuditRoadStore> wishList = null;
        try {
            wishList = helper.getAuditRoadStoreDao().queryBuilder().where().eq("store_id",store_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    /**
     * Obtiene un List de AuditRoadStore por store_id y audit_id
     * @param store_id
     * @param audit_id
     * @return
     */
    public Object findByStoreIdAndAuditId(int store_id, int audit_id) {

        AuditRoadStore wishList = null;
        try {
            wishList =  helper.getAuditRoadStoreDao().queryBuilder().where().eq("store_id",store_id).and().eq("audit_id",audit_id).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    public Object findByStoreIdAndAuditIdAndVisitId(int store_id, int audit_id, int visit_id) {

        AuditRoadStore wishList = null;
        try {
            wishList =  helper.getAuditRoadStoreDao().queryBuilder().where().eq("store_id",store_id).and().eq("audit_id",audit_id).and().eq("visit_id",visit_id).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    public Object findByStoreIdAndVisitId(int store_id, int visit_id) {

        List<AuditRoadStore> wishList = null;
        try {
            wishList =  helper.getAuditRoadStoreDao().queryBuilder().where().eq("store_id",store_id).and().eq("visit_id",visit_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }
}