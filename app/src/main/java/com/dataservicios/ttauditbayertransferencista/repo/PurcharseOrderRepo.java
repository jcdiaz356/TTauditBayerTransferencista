package com.dataservicios.ttauditbayertransferencista.repo;

import android.content.Context;
import java.sql.SQLException;

import com.dataservicios.ttauditbayertransferencista.db.DatabaseHelper;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.PurcharseOrder;

import java.util.List;

public class PurcharseOrderRepo  implements Crud {
    private DatabaseHelper helper;

    public PurcharseOrderRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        PurcharseOrder object = (PurcharseOrder) item;
        try {
            index = helper.getPurcharseOrderDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        PurcharseOrder object = (PurcharseOrder) item;

        try {
            helper.getPurcharseOrderDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        PurcharseOrder object = (PurcharseOrder) item;

        try {
            helper.getPurcharseOrderDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<PurcharseOrder> items = null;
        int counter = 0;
        try {
            items = helper.getPurcharseOrderDao().queryForAll();

            for (PurcharseOrder object : items) {
                // do something with object
                helper.getPurcharseOrderDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        PurcharseOrder wishList = null;
        try {
            wishList = helper.getPurcharseOrderDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<PurcharseOrder> items = null;

        try {
            items = helper.getPurcharseOrderDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getPurcharseOrderDao().queryBuilder().queryForFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getPurcharseOrderDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Obtiene los pedidos de la orden
     * @param company_id
     * @param store_id
     * @param visit_id
     * @return
     */
    public List<PurcharseOrder> findByPurcharseOrder(int company_id,int store_id,int visit_id) {

        List<PurcharseOrder> wishList = null;
        try {
            wishList = helper.getPurcharseOrderDao().queryBuilder().where().eq("company_id",company_id).and().eq("store_id",store_id).and().eq("visit_id",visit_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }



}