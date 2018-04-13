package com.dataservicios.ttauditbayertransferencista.repo;

import android.content.Context;
import java.sql.SQLException;

import com.dataservicios.ttauditbayertransferencista.db.DatabaseHelper;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.ProductDistributor;

import java.util.List;

public class ProductDistributorRepo  implements Crud {
    private DatabaseHelper helper;

    public ProductDistributorRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        ProductDistributor object = (ProductDistributor) item;
        try {
            index = helper.getProductDistributorDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        ProductDistributor object = (ProductDistributor) item;

        try {
            helper.getProductDistributorDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        ProductDistributor object = (ProductDistributor) item;

        try {
            helper.getProductDistributorDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<ProductDistributor> items = null;
        int counter = 0;
        try {
            items = helper.getProductDistributorDao().queryForAll();

            for (ProductDistributor object : items) {
                // do something with object
                helper.getProductDistributorDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        ProductDistributor wishList = null;
        try {
            wishList = helper.getProductDistributorDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<ProductDistributor> items = null;

        try {
            items = helper.getProductDistributorDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getProductDistributorDao().queryBuilder().queryForFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getProductDistributorDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Busca una lista de ProductDistributor por su company_id
     * @param company_id
     * @return Retorna lista
     */
    public List<ProductDistributor> findByCompanyId(int company_id) {

        List<ProductDistributor> wishList = null;
        try {
            wishList = helper.getProductDistributorDao().queryBuilder().where().eq("company_id",company_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    /**
     * Busca una lista de ProductDistributor por su product_id
     * @param product_id product id
     * @return Retorna lista
     */
    public List<ProductDistributor> findByProductIdType(int product_id) {

        List<ProductDistributor> wishList = null;
        try {
            wishList = helper.getProductDistributorDao().queryBuilder().where().eq("product_id",product_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    /**
     * Obtien
     * @param product_id
     * @param provider_id
     * @return
     */
    public List<ProductDistributor> findByProductIdAndDistributor(int product_id, int provider_id) {

        List<ProductDistributor> wishList = null;
        try {
            wishList = helper.getProductDistributorDao().queryBuilder().where().eq("product_id",product_id).and().eq("provider_id",provider_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    /**
     * Busca Proveedores de un producto determinado
     * @param product_id
     * @return
     */
    public Object findByProviderProduct(int product_id) {

        List<ProductDistributor> wishList = null;
        try {
            wishList = helper.getProductDistributorDao().queryBuilder().groupBy("provider_id").where().eq("product_id",product_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    public Object findByProvider(int provider_id) {

        Object wishList = null;
        try {
            wishList = helper.getProductDistributorDao().queryBuilder().groupBy("provider_id").where().eq("provider_id",provider_id).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    /**
     * Busca precio segun rango
     * @param product_id
     * @param provider_id
     * @param quantity
     * @return
     */
    public Object findByPriceInRank(int product_id,int provider_id,int quantity) {

        Object  wishList = null;
        try {
            wishList = helper.getProductDistributorDao().queryBuilder().orderBy("quantity",false).where().eq("product_id",product_id).and().eq("provider_id",provider_id).and().le("quantity",quantity).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

}