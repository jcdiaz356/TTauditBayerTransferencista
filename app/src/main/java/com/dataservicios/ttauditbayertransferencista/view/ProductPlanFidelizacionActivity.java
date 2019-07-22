package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.adapter.ProductPlanFidelizationAdapterRecyclerView;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.AuditRoadStore;
import com.dataservicios.ttauditbayertransferencista.model.Company;
import com.dataservicios.ttauditbayertransferencista.model.Poll;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.ProductPlanSale;
import com.dataservicios.ttauditbayertransferencista.model.Route;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRoadStoreRepo;
import com.dataservicios.ttauditbayertransferencista.repo.CompanyRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductPlanSaleRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductRepo;
import com.dataservicios.ttauditbayertransferencista.repo.RouteRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;
import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;
import com.dataservicios.ttauditbayertransferencista.util.GPSTracker;
import com.dataservicios.ttauditbayertransferencista.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductPlanFidelizacionActivity extends AppCompatActivity {
    private static final String LOG_TAG = ProductPlanFidelizacionActivity.class.getSimpleName();
    private SessionManager                              session;
    private Activity                                    activity = this;
    private ProgressDialog                              pDialog;
    private TextView                                    tvStoreFullName,tvStoreId,tvAddress ,tvReferencia,tvDistrict,tvAuditoria, tvType ;
    private Button                                      btSaveGeo;
    private Button                                      btSave;

    private int                                         user_id;
    private int                                         store_id;
    private int                                         visit_id;
    private int                                         audit_id;
    private int                                         company_id;
    private RouteRepo                                   routeRepo ;
    private AuditRoadStoreRepo                          auditRoadStoreRepo ;
    private StoreRepo                                   storeRepo ;
    private CompanyRepo                                 companyRepo ;
    private ProductRepo                                 productRepo;
    private ProductPlanSaleRepo                         productPlanSaleRepo;
    private Product                                     product;
    private Route                                       route ;
    private Store                                       store ;
    private AuditRoadStore                              auditRoadStore;
    private ProductPlanSale                             productPlanSale;
    private ArrayList<ProductPlanSale>                  productPlanSales;
    private ProductPlanFidelizationAdapterRecyclerView  productPlanFidelizationAdapterRecyclerView;
    private RecyclerView                                productPlanSaleRecyclerView;
    private GPSTracker                                  gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_plan_fidelizacion);


        DatabaseManager.init(this);

        gpsTracker = new GPSTracker(activity);
        if(!gpsTracker.canGetLocation()){
            gpsTracker.showSettingsAlert();
        }

        Bundle bundle = getIntent().getExtras();
        store_id            = bundle.getInt("store_id");
        audit_id            = bundle.getInt("audit_id");


        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        routeRepo           = new RouteRepo(activity);
        storeRepo           = new StoreRepo(activity);
        companyRepo         = new CompanyRepo(activity);
        auditRoadStoreRepo  = new AuditRoadStoreRepo(activity);
        productRepo         = new ProductRepo(activity);
        productPlanSaleRepo = new ProductPlanSaleRepo(activity);


        ArrayList<Company> companies = (ArrayList<Company>) companyRepo.findAll();
        for (Company c: companies){
            company_id = c.getId();
        }

        tvStoreFullName     = (TextView)    findViewById(R.id.tvStoreFullName) ;
        tvStoreId           = (TextView)    findViewById(R.id.tvStoreId) ;
        tvAddress           = (TextView)    findViewById(R.id.tvAddress) ;
        tvReferencia        = (TextView)    findViewById(R.id.tvReferencia) ;
        tvDistrict          = (TextView)    findViewById(R.id.tvDistrict) ;
        tvAuditoria         = (TextView)    findViewById(R.id.tvAuditoria) ;
        tvType              = (TextView)    findViewById(R.id.tvType) ;

        btSaveGeo           = (Button)      findViewById(R.id.btSaveGeo);
        btSave              = (Button)      findViewById(R.id.btSave);

        store               = (Store)           storeRepo.findById(store_id);
        route               = (Route)           routeRepo.findById(store.getRoute_id());
        auditRoadStore      = (AuditRoadStore)  auditRoadStoreRepo.findByStoreIdAndAuditId(store_id,audit_id);


        visit_id= store.getVisit_id();


        tvStoreFullName.setText(String.valueOf(store.getFullname()));
        tvStoreId.setText(String.valueOf(store.getId()));
        tvAddress.setText(String.valueOf(store.getAddress()));
        tvReferencia.setText(String.valueOf(store.getUrbanization()));
        tvDistrict.setText(String.valueOf(store.getDistrict()));
       // tvAuditoria.setText(auditRoadStore.getList().getFullname().toString());
        tvType.setText(String.valueOf(store.getType()) + " (" + store.getCadenRuc() + ")");


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.message_save);
                builder.setMessage(R.string.message_save_information);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        new updateStockMaxMin().execute();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                builder.setCancelable(false);
            }
        });


        showToolbar(auditRoadStore.getList().getFullname().toString(),true);

        new loadData().execute();
    }

    private void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }


    class loadData extends AsyncTask<Void, Integer, Boolean> {

        /**
         * Antes de comenzar en el hilo determinado, Mostrar progresión
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getString(R.string.text_loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub

            pDialog.setMessage(getString(R.string.text_obteniendo_prospecion));
            productPlanSales = AuditUtil.getProductPlanSales(company_id,store_id,visit_id,0);
            if (productPlanSales.isEmpty()) return false;
            return true;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Boolean result) {
            // dismiss the dialog once product deleted

            // dismiss the dialog once product deleted
            if(result) {

//                -----------------------------------------------
//                Eliminamos todos los registros del ProductPlanSale
//                para cargar con los nuevos datos estableciendo el stock minimo
//                y estok maximo a cero pra cargarlo despues con nuevos valores
//                ---------------------------------------------------------------



                for( int i = 0 ; i < productPlanSales.size() ; i++) {
                    if (productPlanSales.get(i).getProduct_id()== 715) {
                        productPlanSales.remove(i);
                    }
//                    if (productPlanSales.get(i).getProduct_id()== 716) {
//                        productPlanSales.remove(i);
//                    }
//                    if (productPlanSales.get(i).getProduct_id()== 749) {
//                        productPlanSales.remove(i);
//                    }
//                    if (productPlanSales.get(i).getProduct_id()== 888) {
//                        productPlanSales.remove(i);
//                    }
                }
                for( int i = 0 ; i < productPlanSales.size() ; i++) {
                    if (productPlanSales.get(i).getProduct_id()== 716) {
                        productPlanSales.remove(i);
                    }
                }
                for( int i = 0 ; i < productPlanSales.size() ; i++) {
                    if (productPlanSales.get(i).getProduct_id()== 749) {
                        productPlanSales.remove(i);
                    }
                }
                for( int i = 0 ; i < productPlanSales.size() ; i++) {
                    if (productPlanSales.get(i).getProduct_id()== 888) {
                        productPlanSales.remove(i);
                    }
                }

                productPlanSaleRepo.deleteAll();
                for (ProductPlanSale ps: productPlanSales){
                    ps.setStock_min(0);
                    ps.setStock_max(0);
                    productPlanSaleRepo.create(ps);
                }

                ArrayList<Product> products = (ArrayList<Product>) productRepo.findAll();

                productPlanSaleRecyclerView  = (RecyclerView) findViewById(R.id.product_recycler_view);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                productPlanSaleRecyclerView.setLayoutManager(linearLayoutManager);

                productPlanFidelizationAdapterRecyclerView =  new ProductPlanFidelizationAdapterRecyclerView(productPlanSales, R.layout.cardview_product_plan_fidelization, activity,store_id,audit_id);
                productPlanSaleRecyclerView.setAdapter(productPlanFidelizationAdapterRecyclerView);
                productPlanSaleRecyclerView.setHasFixedSize(true);
                productPlanSaleRecyclerView.setItemViewCacheSize(productPlanSales.size());

            } else {

                Toast.makeText(activity,getString(R.string.message_no_found),Toast.LENGTH_LONG).show();
            }

            pDialog.dismiss();


            //pDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //super.onProgressUpdate(values);
//            switch (values[10]) {
//
//
//                case 10:
//                    pDialog.setMessage(getString(R.string.text_load_route));
//                case 20:
//                    pDialog.setMessage(getString(R.string.text_load_stores));
//            }
        }
    }

    class updateStockMaxMin extends AsyncTask<Void, Integer , Boolean> {
        /**
         * Antes de comenzar en el hilo determinado, Mostrar progresión
         * */
        @Override
        protected void onPreExecute() {
            //tvCargando.setText("Cargando ProductDetail...");
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getString(R.string.text_loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            super.onPreExecute();

        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub


           ArrayList<ProductPlanSale> productPlanSales = (ArrayList<ProductPlanSale>) productPlanSaleRepo.findAll();

           for(ProductPlanSale m: productPlanSales){
               if(m.getStock_min() > 0 && m.getStock_max()>0){
                   if (!AuditUtil.updateStockMinMax(company_id,store_id,m.getProduct_id(),visit_id,m.getStock_min(),m.getStock_max())) return false;
               }
           }


            return true;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Boolean result) {
            // dismiss the dialog once product deleted

            if (result){
//                product.setStatus(1);
//                productRepo.update(product);
//                finish();
                Poll poll = new Poll();
                poll.setOrder(5);
                PollActivity.createInstance((Activity) activity, store_id,audit_id,poll);
                finish();
            } else {
                Toast.makeText(activity , R.string.message_no_save_data , Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
    }

}
