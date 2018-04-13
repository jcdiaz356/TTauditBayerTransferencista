package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.adapter.ProductPlanSaleAdapterRecyclerView;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.Audit;
import com.dataservicios.ttauditbayertransferencista.model.AuditRoadStore;
import com.dataservicios.ttauditbayertransferencista.model.Company;
import com.dataservicios.ttauditbayertransferencista.model.Poll;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.ProductPlanSale;
import com.dataservicios.ttauditbayertransferencista.model.Route;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRepo;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRoadStoreRepo;
import com.dataservicios.ttauditbayertransferencista.repo.CompanyRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductRepo;
import com.dataservicios.ttauditbayertransferencista.repo.RouteRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;
import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;
import com.dataservicios.ttauditbayertransferencista.util.GPSTracker;
import com.dataservicios.ttauditbayertransferencista.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryStorePlanSalesActivity extends AppCompatActivity {
    private static final String LOG_TAG = HistoryStorePlanSalesActivity.class.getSimpleName();
    private SessionManager session;
    private Activity activity =  this;
    private ProgressDialog pDialog;
    private TextView tvStoreFullName,tvStoreId,tvAddress ,tvReferencia,tvDistrict,tvAuditoria, tvType ;

    private Button                          btSave;

    private int                             user_id;
    private int                             store_id;
    private int                             visit_id;
    private int                             audit_id;
    private int                             company_id;
    private RouteRepo                       routeRepo ;
    private AuditRepo                       auditRepo ;
    private AuditRoadStoreRepo              auditRoadStoreRepo ;
    private StoreRepo                       storeRepo ;
    private CompanyRepo                     companyRepo ;
    private ProductRepo                     productRepo;
    private Product                         product;
    private Route route ;
    private Store store ;
    private Audit audit ;
    private AuditRoadStore auditRoadStore;
    private ArrayList<ProductPlanSale> productPlanSales;
    private ProductPlanSaleAdapterRecyclerView productPlanSaleAdapterRecyclerView;
    private RecyclerView productPlanSaleRecyclerView;
    private GPSTracker gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_store_plan_sales);


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

        routeRepo               = new RouteRepo(activity);
        storeRepo               = new StoreRepo(activity);
        companyRepo             = new CompanyRepo(activity);
        auditRoadStoreRepo      = new AuditRoadStoreRepo(activity);
        auditRepo               = new AuditRepo(activity);
        productRepo             = new ProductRepo(activity);

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

        btSave              = (Button)      findViewById(R.id.btSave);

        store               = (Store)           storeRepo.findById(store_id);
        route               = (Route)           routeRepo.findById(store.getRoute_id());
        auditRoadStore      = (AuditRoadStore)  auditRoadStoreRepo.findByStoreIdAndAuditId(store_id,audit_id);
        audit               = (Audit)           auditRepo.findById(audit_id);

        visit_id= store.getVisit_id();

        tvStoreFullName.setText(String.valueOf(store.getFullname()));
        tvStoreId.setText(String.valueOf(store.getId()));
        tvAddress.setText(String.valueOf(store.getAddress()));
        tvReferencia.setText(String.valueOf(store.getUrbanization()));
        tvDistrict.setText(String.valueOf(store.getDistrict()));

        tvType.setText(String.valueOf(store.getType()) + " (" + store.getCadenRuc() + ")");

        showToolbar(audit.getFullname().toString(),false);


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        Poll poll = new Poll();
                        poll.setOrder(1);
                        PollActivity.createInstance((Activity) activity, store_id,audit_id,poll);
                        finish();


            }
        });

        new loadData().execute();

    }


    public void showToolbar(String title, boolean upButton){
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
            if(result) {
                productPlanSaleRecyclerView  = (RecyclerView) findViewById(R.id.product_plan_sales_recycler_view);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                productPlanSaleRecyclerView.setLayoutManager(linearLayoutManager);

                productPlanSaleAdapterRecyclerView =  new ProductPlanSaleAdapterRecyclerView(productPlanSales, R.layout.cardview_product_plan_sale, activity,store_id,audit_id);
                productPlanSaleRecyclerView.setAdapter(productPlanSaleAdapterRecyclerView);
            } else {

                Toast.makeText(activity,getString(R.string.message_no_found),Toast.LENGTH_LONG).show();
            }

            pDialog.dismiss();
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
}
