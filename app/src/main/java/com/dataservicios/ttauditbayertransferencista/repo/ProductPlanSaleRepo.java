package com.dataservicios.ttauditbayertransferencista.repo;

import android.content.Context;

import com.dataservicios.ttauditbayertransferencista.db.DatabaseHelper;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.ProductPlanSale;

import java.sql.SQLException;
import java.util.List;

public class ProductPlanSaleRepo  implements Crud {
    private DatabaseHelper helper;

    public ProductPlanSaleRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        ProductPlanSale object = (ProductPlanSale) item;
        try {
//            helper.getProductPlanSaleDao().create(object);
//            index = ((ProductPlanSale) item).getId();
            index = helper.getProductPlanSaleDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        ProductPlanSale object = (ProductPlanSale) item;

        try {
            helper.getProductPlanSaleDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        ProductPlanSale object = (ProductPlanSale) item;

        try {
            helper.getProductPlanSaleDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<ProductPlanSale> items = null;
        int counter = 0;
        try {
            items = helper.getProductPlanSaleDao().queryForAll();

            for (ProductPlanSale object : items) {
                // do something with object
                helper.getProductPlanSaleDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        ProductPlanSale wishList = null;
        try {
            wishList = helper.getProductPlanSaleDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<ProductPlanSale> items = null;

        try {
            items = helper.getProductPlanSaleDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getProductPlanSaleDao().queryBuilder().queryForFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getProductPlanSaleDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     *
     * @param product_id
     * @return
     */
    public Object findByProductId(int product_id) {

        Object wishList = null;
        try {
            wishList = helper.getProductPlanSaleDao().queryBuilder().where().eq("product_id",product_id).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

}