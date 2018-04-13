package com.dataservicios.ttauditbayertransferencista.repo;

import android.content.Context;

import com.dataservicios.ttauditbayertransferencista.db.DatabaseHelper;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.ActivityPublicity;
import com.dataservicios.ttauditbayertransferencista.model.Laboratory;

import java.sql.SQLException;
import java.util.List;

public class LaboratoryRepo   implements Crud {
    private DatabaseHelper helper;

    public LaboratoryRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        Laboratory object = (Laboratory) item;
        try {
            index = helper.getLaboratoryDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        Laboratory object = (Laboratory) item;

        try {
            helper.getLaboratoryDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        Laboratory object = (Laboratory) item;

        try {
            helper.getLaboratoryDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<Laboratory> items = null;
        int counter = 0;
        try {
            items = helper.getLaboratoryDao().queryForAll();

            for (Laboratory object : items) {
                // do something with object
                helper.getLaboratoryDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        Laboratory wishList = null;
        try {
            wishList = helper.getLaboratoryDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<Laboratory> items = null;

        try {
            items = helper.getLaboratoryDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getLaboratoryDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getLaboratoryDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }




}