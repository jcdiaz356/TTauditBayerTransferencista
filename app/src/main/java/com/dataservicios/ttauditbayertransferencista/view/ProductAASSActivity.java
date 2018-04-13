package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.adapter.ProductAASSAdapterRecyclerView;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.Audit;
import com.dataservicios.ttauditbayertransferencista.model.AuditRoadStore;
import com.dataservicios.ttauditbayertransferencista.model.Company;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.ProductPublicity;
import com.dataservicios.ttauditbayertransferencista.model.Route;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRepo;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRoadStoreRepo;
import com.dataservicios.ttauditbayertransferencista.repo.CompanyRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductPublicityRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductRepo;
import com.dataservicios.ttauditbayertransferencista.repo.RouteRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;
import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;
import com.dataservicios.ttauditbayertransferencista.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductAASSActivity extends AppCompatActivity {
    private static final String LOG_TAG = ProductAASSActivity.class.getSimpleName();
    private SessionManager                          session;
    private Activity activity =  this;
    private ProgressDialog pDialog;
    private int                                     user_id;
    private int                                     store_id;
    private int                                     audit_id;
    private int                                     publicity_id;
    private TextView tvTotal;
    private Button btSave;
    private ProductRepo                             productRepo;
    private ProductPublicityRepo                    productPublicityRepo;
    private StoreRepo                               storeRepo ;
    private RouteRepo                               routeRepo ;
    private CompanyRepo                             companyRepo ;
    private AuditRepo                               auditRepo ;
    private AuditRoadStoreRepo                      auditRoadStoreRepo ;
    private ProductAASSAdapterRecyclerView          productAASSAdapterRecyclerView;
    private RecyclerView                            productRecyclerView;
    private Audit                                   audit ;
    private Product                                 product;
    private ProductPublicity                        productPublicity;
    private Company                                 company ;
    private Route                                   route ;
    private Store                                   store ;
    private ArrayList<Product> products;
    private ArrayList<ProductPublicity> productPublicities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_aass);

        tvTotal                 = (TextView) findViewById(R.id.tvTotal);
        btSave                  = (Button) findViewById(R.id.btSave);
        DatabaseManager.init(this);

        storeRepo               = new StoreRepo(activity);
        productRepo             = new ProductRepo(activity);
        productPublicityRepo    = new ProductPublicityRepo(activity);
        auditRepo               = new AuditRepo(activity);
        companyRepo             = new CompanyRepo(activity);
        routeRepo               = new RouteRepo(activity);
        auditRoadStoreRepo      = new AuditRoadStoreRepo(activity);

        Bundle bundle = getIntent().getExtras();
        store_id        = bundle.getInt("store_id");
        audit_id        = bundle.getInt("audit_id");
        publicity_id    = bundle.getInt("publicity_id");

        company = (Company)companyRepo.findFirstReg();
        store   = (Store) storeRepo.findById(store_id);
        route   = (Route) routeRepo.findById(store.getRoute_id());

        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        audit = (Audit) auditRepo.findById(audit_id);
        showToolbar(audit.getFullname().toString(),false);

        productRecyclerView  = (RecyclerView) findViewById(R.id.product_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productRecyclerView.setLayoutManager(linearLayoutManager);

        productPublicities = (ArrayList<ProductPublicity>) productPublicityRepo.findByPublicityId(publicity_id);
        // products =   lisProduct;

        productAASSAdapterRecyclerView =  new ProductAASSAdapterRecyclerView(productPublicities,publicity_id, R.layout.cardview_product, activity,store_id,audit_id);
        productRecyclerView.setAdapter(productAASSAdapterRecyclerView);

        int total            = productPublicities.size();
        int productsAudits   = 0;

        for(ProductPublicity p: productPublicities){
            if(p.getStatus()==1) productsAudits ++;
        }

        tvTotal.setText(String.valueOf(productsAudits) + " de " + String.valueOf(total));
        if(productPublicities.size() == 0) {
            btSave.setVisibility(View.INVISIBLE);
        }
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

                        new savePoll().execute();
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


    }

    private ArrayList<ProductPublicity> filter(ArrayList<ProductPublicity> models, String query) {

        query = query.toLowerCase();
        final ArrayList<ProductPublicity> filteredModelList = new ArrayList<>();
        for (ProductPublicity s : models) {
            final String fullName = s.getFullname().toLowerCase().trim();
            if (fullName.contains(query) ) {
                filteredModelList.add(s);
            }

        }
        return filteredModelList;
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //storeAdapterRecyclerView.getFilter().filter(newText.toString());
                final ArrayList<ProductPublicity> filteredMStoreList = filter(productPublicities, newText);
                //adapter.setFilter(filteredModelList);
                productAASSAdapterRecyclerView.setFilter(filteredMStoreList);
                return false;
            }
        });
        return true;
    }

    class savePoll extends AsyncTask<Void, Integer , Boolean> {
        /**
         * Antes de comenzar en el hilo determinado, Mostrar progresión
         * */
        @Override
        protected void onPreExecute() {
            //tvCargando.setText("Cargando Product...");
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

            if (!AuditUtil.closeAuditStore(audit_id, store_id, company.getId(), route.getId())) return false;

            return true;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Boolean result) {
            // dismiss the dialog once product deleted
            if (result){
                //AuditRoadStore auditRoadStore = (AuditRoadStore) auditRoadStoreRepo.findByStoreIdAndAuditId(store_id,audit_id);
                AuditRoadStore auditRoadStore = (AuditRoadStore) auditRoadStoreRepo.findByStoreIdAndAuditIdAndVisitId(store_id,audit_id,store.getVisit_id());
                auditRoadStore.setAuditStatus(1);
                auditRoadStoreRepo.update(auditRoadStore);

                for(ProductPublicity p: productPublicities){
                    p.setStatus(0);
                    productPublicityRepo.update(p);
                }

                finish();
            } else {
                Toast.makeText(activity , R.string.message_no_save_data , Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {

        if(productPublicities.size() == 0 ) {
            super.onBackPressed ();
        } else {
            alertDialogBasico(getString(R.string.message_save_audit_products));
        }

        //super.onBackPressed ();

    }


    private void alertDialogBasico(String message) {

        // 1. Instancia de AlertDialog.Builder con este constructor
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        // 2. Encadenar varios métodos setter para ajustar las características del diálogo
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return;
            }
        });
        builder.show();

    }
}