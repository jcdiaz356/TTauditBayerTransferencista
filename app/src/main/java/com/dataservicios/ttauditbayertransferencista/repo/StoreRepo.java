package com.dataservicios.ttauditbayertransferencista.repo;

import android.content.Context;

import com.dataservicios.ttauditbayertransferencista.db.DatabaseHelper;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.Store;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jcdia on 13/05/2017.
 */

public class StoreRepo implements Crud {
    private DatabaseHelper helper;

    public StoreRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        Store object = (Store) item;
        try {
            index = helper.getStoreDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        Store object = (Store) item;

        try {
            helper.getStoreDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        Store object = (Store) item;

        try {
            helper.getStoreDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<Store> items = null;
        int counter = 0;
        try {
            items = helper.getStoreDao().queryForAll();

            for (Store object : items) {
                // do something with object
                helper.getStoreDao().deleteById(object.getId());
                counter ++ ;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        Store wishList = null;
        try {
            wishList = helper.getStoreDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<Store> items = null;

        try {
            items = helper.getStoreDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getStoreDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getStoreDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Busca una lista de Stores por su Route
     * @param route_id
     * @return Retorna lista de stores
     */
    public List<Store> findByRouteId(int route_id) {

        List<Store> wishList = null;
        try {
            wishList = helper.getStoreDao().queryBuilder().where().eq("route_id",route_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    /**
     *
     * @param route_id
     * @param store_id
     * @return
     */
    public Object findByRouteAndStoreId(int route_id, int store_id) {

        Object wishList = null;
        try {
            wishList =  helper.getStoreDao().queryBuilder().where().eq("route_id",route_id).and().eq("id",store_id).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

}