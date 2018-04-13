package com.dataservicios.ttauditbayertransferencista.repo;

import android.content.Context;

import com.dataservicios.ttauditbayertransferencista.db.DatabaseHelper;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.ActivityPublicity;

import java.sql.SQLException;
import java.util.List;

public class ActivityPublicityRepo  implements Crud {
    private DatabaseHelper helper;

    public ActivityPublicityRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        ActivityPublicity object = (ActivityPublicity) item;
        try {
            index = helper.getActivityPublicityDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        ActivityPublicity object = (ActivityPublicity) item;

        try {
            helper.getActivityPublicityDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        ActivityPublicity object = (ActivityPublicity) item;

        try {
            helper.getActivityPublicityDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<ActivityPublicity> items = null;
        int counter = 0;
        try {
            items = helper.getActivityPublicityDao().queryForAll();

            for (ActivityPublicity object : items) {
                // do something with object
                helper.getActivityPublicityDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        ActivityPublicity wishList = null;
        try {
            wishList = helper.getActivityPublicityDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<ActivityPublicity> items = null;

        try {
            items = helper.getActivityPublicityDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getActivityPublicityDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getActivityPublicityDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    /**
     * Busca una lista de ActivityPublicity por su company_id
     * @param company_id
     * @return Retorna lista de Products
     */
    public List<ActivityPublicity> findByCompanyId(int company_id) {

        List<ActivityPublicity> wishList = null;
        try {
            wishList = helper.getActivityPublicityDao().queryBuilder().where().eq("company_id",company_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

}