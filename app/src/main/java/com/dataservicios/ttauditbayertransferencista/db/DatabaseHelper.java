package com.dataservicios.ttauditbayertransferencista.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.dataservicios.ttauditbayertransferencista.model.ActivityPublicity;
import com.dataservicios.ttauditbayertransferencista.model.Audit;
import com.dataservicios.ttauditbayertransferencista.model.AuditRoadStore;
import com.dataservicios.ttauditbayertransferencista.model.CategoryProduct;
import com.dataservicios.ttauditbayertransferencista.model.Company;
import com.dataservicios.ttauditbayertransferencista.model.Departament;
import com.dataservicios.ttauditbayertransferencista.model.Distributor;
import com.dataservicios.ttauditbayertransferencista.model.District;
import com.dataservicios.ttauditbayertransferencista.model.Laboratory;
import com.dataservicios.ttauditbayertransferencista.model.Media;
import com.dataservicios.ttauditbayertransferencista.model.Order;
import com.dataservicios.ttauditbayertransferencista.model.OrderDetail;
import com.dataservicios.ttauditbayertransferencista.model.OrderDetailTemp;
import com.dataservicios.ttauditbayertransferencista.model.Poll;
import com.dataservicios.ttauditbayertransferencista.model.PollOption;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.ProductDetail;
import com.dataservicios.ttauditbayertransferencista.model.ProductDistributor;
import com.dataservicios.ttauditbayertransferencista.model.ProductPlanSale;
import com.dataservicios.ttauditbayertransferencista.model.ProductPublicity;
import com.dataservicios.ttauditbayertransferencista.model.Publicity;
import com.dataservicios.ttauditbayertransferencista.model.PublicityHistory;
import com.dataservicios.ttauditbayertransferencista.model.PurcharseOrder;
import com.dataservicios.ttauditbayertransferencista.model.Route;
import com.dataservicios.ttauditbayertransferencista.model.RouteStoreTime;
import com.dataservicios.ttauditbayertransferencista.model.StockProductPop;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.model.User;
import com.dataservicios.ttauditbayertransferencista.model.Visit;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();
	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "db_bayer_t";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 17;
    private Context myContext;
	// the DAO object we use to access the SimpleData table
    //pressure
	private Dao<User, Integer>                  UserDao                 = null;
	private Dao<Departament, Integer>           DepartamentDao          = null;
	private Dao<District, Integer>              DistrictDao             = null;
	private Dao<Route, Integer>                 RouteDao                = null;
	private Dao<Company, Integer>               CompanyDao              = null;
	private Dao<Store, Integer>                 StoreDao                = null;
	private Dao<Audit, Integer>                 AuditDao                = null;
	private Dao<AuditRoadStore,Integer>         AuditRoadStoreDao       = null;
	private Dao<Poll, Integer>                  PollDao                 = null;
	private Dao<PollOption, Integer>            PollOptionDao           = null;
	private Dao<Media, Integer>                 MediaDao                = null;
	private Dao<RouteStoreTime, Integer>        RouteStoreTimeDao       = null;
	private Dao<Publicity, Integer>             PublicityDao            = null;
	private Dao<PublicityHistory, Integer>      PublicityHistoryDao     = null;
	private Dao<ProductDetail, Integer>         ProductDetailDao        = null;
	private Dao<Product, Integer>               ProductDao              = null;
	private Dao<Visit, Integer>                 VisitDao                = null;
	private Dao<CategoryProduct, Integer>       CategoryProductDao      = null;
	private Dao<StockProductPop, Integer>       StockProductPopDao      = null;
    private Dao<ProductPublicity, Integer>      ProductPublicityDao     = null;
    private Dao<ProductDistributor, Integer>    ProductDistributorDao   = null;
    private Dao<PurcharseOrder, Integer>        PurcharseOrderDao       = null;
    private Dao<Distributor, Integer>           DistributorDao          = null;
    private Dao<Order, Integer>                 OrderDao                = null;
    private Dao<OrderDetail, Integer>           OrderDetailDao          = null;
    private Dao<OrderDetailTemp, Integer>       OrderDetailTempDao      = null;
    private Dao<ProductPlanSale, Integer>       ProductPlanSaleDao      = null;
    private Dao<Laboratory, Integer>            LaboratoryDao           = null;
    private Dao<ActivityPublicity, Integer>     ActivityPublicityDao    = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			
			TableUtils.createTable(connectionSource, User.class                 );
			TableUtils.createTable(connectionSource, Departament.class          );
			TableUtils.createTable(connectionSource, District.class             );
			TableUtils.createTable(connectionSource, Route.class                );
			TableUtils.createTable(connectionSource, Company.class              );
			TableUtils.createTable(connectionSource, Store.class                );
			TableUtils.createTable(connectionSource, Audit.class                );
			TableUtils.createTable(connectionSource, AuditRoadStore.class       );
			TableUtils.createTable(connectionSource, Poll.class                 );
			TableUtils.createTable(connectionSource, PollOption.class           );
			TableUtils.createTable(connectionSource, Media.class                );
			TableUtils.createTable(connectionSource, RouteStoreTime.class       );
			TableUtils.createTable(connectionSource, Publicity.class            );
			TableUtils.createTable(connectionSource, PublicityHistory.class     );
			TableUtils.createTable(connectionSource, ProductDetail.class        );
			TableUtils.createTable(connectionSource, Product.class              );
			TableUtils.createTable(connectionSource, Visit.class                );
			TableUtils.createTable(connectionSource, CategoryProduct.class      );
			TableUtils.createTable(connectionSource, StockProductPop.class      );
            TableUtils.createTable(connectionSource, ProductPublicity.class     );
            TableUtils.createTable(connectionSource, ProductDistributor.class   );
            TableUtils.createTable(connectionSource, PurcharseOrder.class       );
            TableUtils.createTable(connectionSource, Distributor.class          );
            TableUtils.createTable(connectionSource, Order.class                );
            TableUtils.createTable(connectionSource, OrderDetail.class          );
            TableUtils.createTable(connectionSource, OrderDetailTemp.class      );
            TableUtils.createTable(connectionSource, ProductPlanSale.class      );
            TableUtils.createTable(connectionSource, Laboratory.class           );
            TableUtils.createTable(connectionSource, ActivityPublicity.class    );

            Log.i(LOG_TAG, "execute method onCreate: Can't create Tables");
            preloadData(db,myContext);

		} catch (SQLException e) {
			Log.e(LOG_TAG, "Can't create database", e);
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			List<String> allSql = new ArrayList<String>();

			switch(oldVersion)
			{
				case 1:
				  //allSql.add("alter table AdData add column `new_col` VARCHAR");
				  //allSql.add("alter table AdData add column `new_col2` VARCHAR");

			}
			for (String sql : allSql) {
				db.execSQL(sql);
			}

            TableUtils.dropTable(connectionSource,User.class,true               );
            TableUtils.dropTable(connectionSource, Departament.class,true       );
            TableUtils.dropTable(connectionSource, District.class,true          );
            TableUtils.dropTable(connectionSource, Route.class,true             );
            TableUtils.dropTable(connectionSource, Company.class,true           );
            TableUtils.dropTable(connectionSource, Store.class,true             );
            TableUtils.dropTable(connectionSource, Audit.class,true             );
            TableUtils.dropTable(connectionSource, AuditRoadStore.class,true    );
            TableUtils.dropTable(connectionSource, Poll.class,true              );
            TableUtils.dropTable(connectionSource, PollOption.class,true        );
            TableUtils.dropTable(connectionSource, Media.class,true             );
            TableUtils.dropTable(connectionSource, RouteStoreTime.class,true    );
            TableUtils.dropTable(connectionSource, Publicity.class,true         );
            TableUtils.dropTable(connectionSource, PublicityHistory.class,true  );
            TableUtils.dropTable(connectionSource, ProductDetail.class,true     );
            TableUtils.dropTable(connectionSource, Product.class,true           );
            TableUtils.dropTable(connectionSource, Visit.class,true             );
            TableUtils.dropTable(connectionSource, CategoryProduct.class,true   );
            TableUtils.dropTable(connectionSource, StockProductPop.class,true   );
            TableUtils.dropTable(connectionSource, ProductPublicity.class,true  );
            TableUtils.dropTable(connectionSource, ProductDistributor.class,true);
            TableUtils.dropTable(connectionSource, PurcharseOrder.class,true    );
            TableUtils.dropTable(connectionSource, Distributor.class,   true    );
            TableUtils.dropTable(connectionSource, Order.class,         true    );
            TableUtils.dropTable(connectionSource, OrderDetail.class,   true    );
            TableUtils.dropTable(connectionSource, OrderDetailTemp.class,   true);
            TableUtils.dropTable(connectionSource, ProductPlanSale.class,   true);
            TableUtils.dropTable(connectionSource, Laboratory.class,true        );
            TableUtils.dropTable(connectionSource, ActivityPublicity.class,true );
            onCreate(db,connectionSource);

            Log.i(LOG_TAG, "execute method onUpgrade: drop Tables");

		} catch (SQLException e) {
			Log.e(LOG_TAG, "exception during onUpgrade", e);
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }


	public Dao<User, Integer> getUserDao() {
		if (null == UserDao) {
			try {
				UserDao = getDao(User.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return UserDao;
	}

    public Dao<Departament, Integer> getDepartamentDao() {
        if (null == DepartamentDao) {
            try {
                DepartamentDao = getDao(Departament.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return DepartamentDao;
    }

    public Dao<District, Integer> getDistrictDao() {
        if (null == DistrictDao) {
            try {
                DistrictDao = getDao(District.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return DistrictDao;
    }

    public Dao<Route, Integer> getRouteDao() {
        if (null == RouteDao) {
            try {
                RouteDao = getDao(Route.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return RouteDao;
    }

    public Dao<Company, Integer> getCompanyDao() {
        if (null == CompanyDao) {
            try {
                CompanyDao = getDao(Company.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return CompanyDao;
    }

    public Dao<Store, Integer> getStoreDao() {
        if (null == StoreDao) {
            try {
                StoreDao = getDao(Store.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return StoreDao;
    }

    public Dao<Audit, Integer> getAuditDao() {
        if (null == AuditDao) {
            try {
                AuditDao = getDao(Audit.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return AuditDao;
    }

    public Dao<AuditRoadStore, Integer> getAuditRoadStoreDao() {
        if (null == AuditRoadStoreDao) {
            try {
                AuditRoadStoreDao = getDao(AuditRoadStore.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return AuditRoadStoreDao;
    }
    public Dao<Poll, Integer> getPollDao() {
        if (null == PollDao) {
            try {
                PollDao = getDao(Poll.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return PollDao;
    }

    public Dao<PollOption, Integer> getPollOptionDao() {
        if (null == PollOptionDao) {
            try {
                PollOptionDao = getDao(PollOption.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return PollOptionDao;
    }
    public Dao<Media, Integer> getMediaDao() {
            if (null == MediaDao) {
                try {
                    MediaDao = getDao(Media.class);
                }catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
            return MediaDao;
    }
    public Dao<RouteStoreTime, Integer> getRouteStoreTimeDao() {
                if (null == RouteStoreTimeDao) {
                    try {
                        RouteStoreTimeDao = getDao(RouteStoreTime.class);
                    }catch (java.sql.SQLException e) {
                        e.printStackTrace();
                    }
                }
                return RouteStoreTimeDao;
    }

    public Dao<Publicity, Integer> getPublicityDao() {
        if (null == PublicityDao) {
            try {
                PublicityDao = getDao(Publicity.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return PublicityDao;
    }

    public Dao<PublicityHistory, Integer> getPublicityHistoryDao() {
        if (null == PublicityHistoryDao) {
            try {
                PublicityHistoryDao = getDao(PublicityHistory.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return PublicityHistoryDao;
    }

    public Dao<ProductDetail, Integer> getProductDetailDao() {
            if (null == ProductDetailDao) {
                try {
                    ProductDetailDao = getDao(ProductDetail.class);
                }catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
            return ProductDetailDao;
        }

    public Dao<Product, Integer> getProductDao() {
        if (null == ProductDao) {
            try {
                ProductDao = getDao(Product.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return ProductDao;
    }



    public Dao<Visit, Integer> getVisitDao() {
        if (null == VisitDao) {
            try {
                VisitDao = getDao(Visit.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return VisitDao;
    }

    public Dao<CategoryProduct, Integer> getCategoryProductDao() {
        if (null == CategoryProductDao) {
            try {
                CategoryProductDao = getDao(CategoryProduct.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return CategoryProductDao;
    }

    public Dao<StockProductPop, Integer> getStockProductPopDao() {
        if (null == StockProductPopDao) {
            try {
                StockProductPopDao = getDao(StockProductPop.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return StockProductPopDao;
    }


    public Dao<ProductPublicity, Integer> getProductPublicityDao() {
        if (null == ProductPublicityDao) {
            try {
                ProductPublicityDao = getDao(ProductPublicity.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return ProductPublicityDao;
    }

    public Dao<ProductDistributor, Integer> getProductDistributorDao() {
        if (null == ProductDistributorDao) {
            try {
                ProductDistributorDao = getDao(ProductDistributor.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return ProductDistributorDao;
    }

    public Dao<PurcharseOrder, Integer> getPurcharseOrderDao() {
        if (null == PurcharseOrderDao) {
            try {
                PurcharseOrderDao = getDao(PurcharseOrder.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return PurcharseOrderDao;
    }

    public Dao<Distributor, Integer> getDistributorDao() {
        if (null == DistributorDao) {
            try {
                DistributorDao = getDao(Distributor.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return DistributorDao;
    }

    public Dao<Order, Integer> getOrderDao() {
        if (null == OrderDao) {
            try {
                OrderDao = getDao(Order.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return OrderDao;
    }

    public Dao<OrderDetail, Integer> getOrderDetailDao() {
        if (null == OrderDetailDao) {
            try {
                OrderDetailDao = getDao(OrderDetail.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return OrderDetailDao;
    }
    public Dao<OrderDetailTemp, Integer> getOrderDetailTempDao() {
            if (null == OrderDetailTempDao) {
                try {
                    OrderDetailTempDao = getDao(OrderDetailTemp.class);
                }catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
            return OrderDetailTempDao;
	}
     public Dao<ProductPlanSale, Integer> getProductPlanSaleDao() {
                if (null == ProductPlanSaleDao) {
                    try {
                        ProductPlanSaleDao = getDao(ProductPlanSale.class);
                    }catch (java.sql.SQLException e) {
                        e.printStackTrace();
                    }
                }
                return ProductPlanSaleDao;
	}

	public Dao<Laboratory, Integer> getLaboratoryDao() {
                if (null == LaboratoryDao) {
                    try {
                        LaboratoryDao = getDao(Laboratory.class);
                    }catch (java.sql.SQLException e) {
                        e.printStackTrace();
                    }
                }
                return LaboratoryDao;
	}
    public Dao<ActivityPublicity, Integer> getActivityPublicityDao() {
                if (null == ActivityPublicityDao) {
                    try {
                        ActivityPublicityDao = getDao(ActivityPublicity.class);
                    }catch (java.sql.SQLException e) {
                        e.printStackTrace();
                    }
                }
                return ActivityPublicityDao;
	}

    private void preloadData(SQLiteDatabase db, Context context) {

        InputStream is = null;
        try {

            is = context.getAssets().open("insert.sql");
            if (is != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();
                while (!TextUtils.isEmpty(line)) {
                    db.execSQL(line);
                    line = reader.readLine();

                }
                db.setTransactionSuccessful();
            }

            is.close();

            Log.i(LOG_TAG,"Insert rows");
        } catch (IOException e) {
            // Muestra log
            Log.e(LOG_TAG, "Error in File insert.sql", e);

        } catch (Exception e) {
            // Muestra log
            Log.e(LOG_TAG, "Error preloadData", e);
        } finally {
            db.endTransaction();
        }
    }
}
