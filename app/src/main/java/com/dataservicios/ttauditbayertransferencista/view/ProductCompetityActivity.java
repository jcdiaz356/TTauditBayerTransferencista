package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.dataservicios.ttauditbayertransferencista.adapter.OnEditTextChanged;
import com.dataservicios.ttauditbayertransferencista.adapter.ProductAdapterRecyclerView;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.Audit;
import com.dataservicios.ttauditbayertransferencista.model.AuditRoadStore;
import com.dataservicios.ttauditbayertransferencista.model.Company;
import com.dataservicios.ttauditbayertransferencista.model.Distributor;
import com.dataservicios.ttauditbayertransferencista.model.Order;
import com.dataservicios.ttauditbayertransferencista.model.OrderDetail;
import com.dataservicios.ttauditbayertransferencista.model.OrderDetailTemp;
import com.dataservicios.ttauditbayertransferencista.model.Poll;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.Route;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRepo;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRoadStoreRepo;
import com.dataservicios.ttauditbayertransferencista.repo.CompanyRepo;
import com.dataservicios.ttauditbayertransferencista.repo.DistributorRepo;
import com.dataservicios.ttauditbayertransferencista.repo.OrderDetailRepo;
import com.dataservicios.ttauditbayertransferencista.repo.OrderDetailTempRepo;
import com.dataservicios.ttauditbayertransferencista.repo.OrderRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductRepo;
import com.dataservicios.ttauditbayertransferencista.repo.RouteRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;
import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;
import com.dataservicios.ttauditbayertransferencista.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductCompetityActivity extends AppCompatActivity {

    private static final String LOG_TAG = ProductPriceActivity.class.getSimpleName();
    private SessionManager              session;
    private Activity                    activity =  this;
    private ProgressDialog              pDialog;
    private int                         user_id;
    private int                         store_id;
    private int                         visit_id;
    private int                         company_id;
    private int                         audit_id;
    private int                         distributor_id;
    private float                       montoTotal;
    private TextView                    tvTotal;
    private TextView                    tvDistributor;
    private Button                      btSave;
    private ProductRepo                 productRepo;
    private StoreRepo                   storeRepo ;
    private RouteRepo                   routeRepo ;
    private CompanyRepo                 companyRepo ;
    private AuditRepo                   auditRepo ;
    private AuditRoadStoreRepo          auditRoadStoreRepo ;
    private OrderDetailTempRepo         orderDetailTempRepo ;
    private OrderRepo                   orderRepo;
    private OrderDetailRepo             orderDetailRepo ;
    private DistributorRepo             distributorRepo;
    private ProductAdapterRecyclerView  productAdapterRecyclerView;
    private RecyclerView                productRecyclerView;
    private Audit                       audit ;
    private Product                     product;
    private Company                     company ;
    private Route                       route ;
    private Store                       store ;
    private Distributor                 distributor;
    private ArrayList<Product>          products;
    private Order                       order;
    private OrderDetail                 orderDetail;
    private OrderDetailTemp             orderDetailTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_competity);
        tvTotal                 = (TextView) findViewById(R.id.tvTotal);
        tvDistributor           = (TextView) findViewById(R.id.tvDistributor);
        btSave                  = (Button) findViewById(R.id.btSave);
        DatabaseManager.init(this);

        storeRepo           = new StoreRepo(activity);
        productRepo         = new ProductRepo(activity);
        auditRepo           = new AuditRepo(activity);
        companyRepo         = new CompanyRepo(activity);
        routeRepo           = new RouteRepo(activity);
        auditRoadStoreRepo  = new AuditRoadStoreRepo(activity);
        orderRepo           = new OrderRepo(activity);
        orderDetailRepo     = new OrderDetailRepo(activity);
        orderDetailTempRepo = new OrderDetailTempRepo(activity);
        distributorRepo     = new DistributorRepo(activity);

        Bundle bundle = getIntent().getExtras();
        store_id        = bundle.getInt("store_id");
        audit_id        = bundle.getInt("audit_id");
        distributor_id  = bundle.getInt("distributor_id");

        company     = (Company)companyRepo.findFirstReg();
        store       = (Store) storeRepo.findById(store_id);
        route       = (Route) routeRepo.findById(store.getRoute_id());
        distributor = (Distributor) distributorRepo.findById(distributor_id);
        audit       = (Audit) auditRepo.findById(audit_id);

        company_id  = company.getId();
        visit_id    = store.getVisit_id();

        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        tvDistributor.setText(distributor.getFullName().toString());

        showToolbar(audit.getFullname().toString(),false);

        productRecyclerView  = (RecyclerView) findViewById(R.id.product_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productRecyclerView.setLayoutManager(linearLayoutManager);

        products = (ArrayList<Product>) productRepo.findByTypeCompetity(1);

        productAdapterRecyclerView =  new ProductAdapterRecyclerView(products, R.layout.cardview_product, activity, store_id, audit_id, distributor_id, new OnEditTextChanged() {
            @Override
            public void onTextChanged(int position, String charSeq) {
                montoTotal = Float.valueOf(charSeq);
                tvTotal.setText(charSeq);
            }
        });
        productRecyclerView.setAdapter(productAdapterRecyclerView);

        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setItemViewCacheSize(products.size());

        int total               = products.size();
        int productsAudits      = 0;

        //Clear limpia las ordenes temporales
        orderDetailTempRepo.deleteAll();

        for(Product p: products){
            if(p.getStatus()==1) productsAudits ++;
            orderDetailTemp = new OrderDetailTemp();
            orderDetailTemp.setProduct_id(p.getId());
            orderDetailTemp.setPrice(0);
            orderDetailTemp.setQuantity(0);
            orderDetailTemp.setTotal(0);

            orderDetailTempRepo.create(orderDetailTemp);
        }
        ArrayList<OrderDetailTemp> orderDetailTemps = (ArrayList<OrderDetailTemp>) orderDetailTempRepo.findAll();


        tvTotal.setText(String.valueOf(productsAudits) + " de " + String.valueOf(total));
        if(products.size() == 0) {
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

//                        if( store.getChanel().equals("TRADICIONAL") ){
//                            Bundle bundle = new Bundle();
//                            bundle.putInt("store_id",store_id);
//                            bundle.putInt("audit_id",audit_id);
//                            // bundle.putInt("product_id",product.getId());
//                            Intent intent = new Intent(activity, ProductOrdersTotalActivity.class);
//                            intent.putExtras(bundle);
//                            activity.startActivity(intent);
//                            finish();
//                        } else if(store.getChanel().equals("MODERNO")) {
//
//                            Poll poll = new Poll();
//                            poll.setOrder(5);
//                            PollActivity.createInstance((Activity) activity, store_id,audit_id,poll);
//                            finish();
//                        }

                         new savOrder().execute();
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

    private ArrayList<Product> filter(ArrayList<Product> models, String query) {

        query = query.toLowerCase();
        final ArrayList<Product> filteredModelList = new ArrayList<>();
        for (Product s : models) {
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
                final ArrayList<Product> filteredMStoreList = filter(products, newText);
                //adapter.setFilter(filteredModelList);
                productAdapterRecyclerView.setFilter(filteredMStoreList);
                return false;
            }
        });
        return true;
    }

    class savOrder extends AsyncTask<Void, Integer , Boolean> {
        /**
         * Antes de comenzar en el hilo determinado, Mostrar progresión
         * */
        @Override
        protected void onPreExecute() {
            //tvCargando.setText("Cargando Product...");
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getString(R.string.text_save_order));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            super.onPreExecute();

        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub

//            if (!AuditUtil.closeAuditStore(audit_id, store_id, company.getId(), route.getId())) return false;
            ArrayList<OrderDetailTemp> orderDetailTemps = (ArrayList<OrderDetailTemp>) orderDetailTempRepo.findAll();
            float mountTotal=0;
            for(OrderDetailTemp m: orderDetailTemps){
                if(m.getQuantity() > 0){
                    if (!AuditUtil.saveOrder(company_id,store_id,m.getProduct_id(),visit_id,distributor_id,m.getQuantity(),String.valueOf(m.getTotal()),user_id, String.valueOf(m.getPrice()))) return false;
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
                //AuditRoadStore auditRoadStore = (AuditRoadStore) auditRoadStoreRepo.findByStoreIdAndAuditId(store_id,audit_id);
//                AuditRoadStore auditRoadStore = (AuditRoadStore) auditRoadStoreRepo.findByStoreIdAndAuditIdAndVisitId(store_id,audit_id,store.getVisit_id());
//                auditRoadStore.setAuditStatus(1);
//                auditRoadStoreRepo.update(auditRoadStore);
                order = new Order();
                order.setCode("0");
                order.setCompany_id(company_id);
                order.setDistributor_id(distributor_id);
                order.setStore_id(store_id);
                order.setUser_id(user_id);
                order.setVisit_id(visit_id);
                order.setMount_total(montoTotal);

                orderRepo.create(order);
                int order_id = order.getId();

                ArrayList<OrderDetailTemp> orderDetailTemps = (ArrayList<OrderDetailTemp>) orderDetailTempRepo.findAll();
                for(OrderDetailTemp m: orderDetailTemps){
                    if(m.getQuantity() > 0){
                        orderDetail = new OrderDetail();
                        orderDetail.setOrder_id(order_id);
                        orderDetail.setPrice(m.getPrice());
                        orderDetail.setProduct_id(m.getProduct_id());
                        orderDetail.setQuantity(m.getQuantity());
                        orderDetail.setTotal(m.getTotal());
                        orderDetailRepo.create(orderDetail);
                    }
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

//        if(products.size() == 0 ) {
//            super.onBackPressed ();
//        } else {
         //   alertDialogBasico(getString(R.string.message_save_audit_products));
//        }
//        super.onBackPressed ();
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