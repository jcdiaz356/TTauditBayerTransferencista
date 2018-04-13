package com.dataservicios.ttauditbayertransferencista.repo;

import android.content.Context;

import com.dataservicios.ttauditbayertransferencista.db.DatabaseHelper;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.ProductPublicity;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jcdia on 10/12/2017.
 */

public class ProductPublicityRepo implements Crud {
    private DatabaseHelper helper;

    public ProductPublicityRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        ProductPublicity object = (ProductPublicity) item;
        try {
            index = helper.getProductPublicityDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        ProductPublicity object = (ProductPublicity) item;

        try {
            helper.getProductPublicityDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        ProductPublicity object = (ProductPublicity) item;

        try {
            helper.getProductPublicityDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<ProductPublicity> items = null;
        int counter = 0;
        try {
            items = helper.getProductPublicityDao().queryForAll();

            for (ProductPublicity object : items) {
                // do something with object
                helper.getProductPublicityDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        ProductPublicity wishList = null;
        try {
            wishList = helper.getProductPublicityDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<ProductPublicity> items = null;

        try {
            items = helper.getProductPublicityDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getProductPublicityDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getProductPublicityDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Busca una lista de ProductPublicity por su company_id
     * @param company_id
     * @return Retorna lista de ProductPublicitys
     */
    public List<ProductPublicity> findByCompanyId(int company_id) {

        List<ProductPublicity> wishList = null;
        try {
            wishList = helper.getProductPublicityDao().queryBuilder().where().eq("company_id",company_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    /**
     * Busca una lista de ProductPublicity por su type
     * @param type tipo pe producto: Propio o competencia
     * @return Retorna lista de ProductPublicitys
     */
    public List<ProductPublicity> findByTypeCompetity(int type) {

        List<ProductPublicity> wishList = null;
        try {
            wishList = helper.getProductPublicityDao().queryBuilder().where().eq("type",type).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    public List<ProductPublicity> findByPublicityId(int publicity_id) {

        List<ProductPublicity> wishList = null;
        try {
            wishList = helper.getProductPublicityDao().queryBuilder().where().eq("publicity_id",publicity_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


}