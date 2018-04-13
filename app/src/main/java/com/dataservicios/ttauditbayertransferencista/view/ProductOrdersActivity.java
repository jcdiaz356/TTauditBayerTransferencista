package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.AuditRoadStore;
import com.dataservicios.ttauditbayertransferencista.model.Company;
import com.dataservicios.ttauditbayertransferencista.model.Distributor;
import com.dataservicios.ttauditbayertransferencista.model.Poll;
import com.dataservicios.ttauditbayertransferencista.model.PollDetail;
import com.dataservicios.ttauditbayertransferencista.model.PollOption;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.ProductDistributor;
import com.dataservicios.ttauditbayertransferencista.model.ProductPlanSale;
import com.dataservicios.ttauditbayertransferencista.model.PurcharseOrder;
import com.dataservicios.ttauditbayertransferencista.model.Route;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRoadStoreRepo;
import com.dataservicios.ttauditbayertransferencista.repo.CompanyRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PollOptionRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PollRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductDistributorRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PurcharseOrderRepo;
import com.dataservicios.ttauditbayertransferencista.repo.RouteRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;
import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;
import com.dataservicios.ttauditbayertransferencista.util.GPSTracker;
import com.dataservicios.ttauditbayertransferencista.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductOrdersActivity extends AppCompatActivity {
    private static final String LOG_TAG = ProductOrdersActivity.class.getSimpleName();
    private SessionManager                  session;
    private Activity                        activity =  this;
    private ProgressDialog                  pDialog;
    private TextView                        tvStoreFullName,tvStoreId,tvAddress ,tvReferencia,tvDistrict,tvAuditoria, tvType ;
    private TextView                        tvProductName,tvProm6m,tvCuotaMes ,tvAvance,tvPedidoSugerido,tvDistibutorPrice, tvSubTotal ;
    private EditText                        etCantidad;
    private Button                          btSaveGeo;
    private Button                          btSave;
    private CheckBox[]                      checkBoxArray;
    private RadioButton[]                   radioButtonArray;
    private RadioGroup                      radioGroup;
    private Switch                          swYesNo;
    private ImageButton                     btPhoto;
    private LinearLayout                    lyComment;
    private LinearLayout                    lyOptions;
    private LinearLayout                    lyDistributorPrice;
    private int                             user_id;
    private int                             store_id;
    private int                             visit_id;
    private int                             audit_id;
    private int                             quantity;
    private int                             company_id;
    private int                             category_product_id;
    private int                             publicity_id;
    private int                             product_id;
    private int                             provider_id = 0;
    private float                           total;
    private float                           price;
    private RouteRepo                       routeRepo ;
    private AuditRoadStoreRepo              auditRoadStoreRepo ;
    private StoreRepo                       storeRepo ;
    private CompanyRepo                     companyRepo ;
    private ProductRepo                     productRepo;
    private ProductDistributorRepo          productDistributorRepo;
    private PurcharseOrderRepo              purcharseOrderRepo;
    private PurcharseOrder                  purcharseOrder;
    private Product                         product;
    private Route                           route ;
    private Store                           store ;
    private AuditRoadStore                  auditRoadStore;
    private ProductPlanSale                 productPlanSale;
    private ProductDistributor              productDistributor;
    private ArrayList<ProductDistributor>   productDistributors;
    private GPSTracker                      gpsTracker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_orders);

        DatabaseManager.init(this);

        gpsTracker = new GPSTracker(activity);
        if(!gpsTracker.canGetLocation()){
            gpsTracker.showSettingsAlert();
        }

        Bundle bundle = getIntent().getExtras();
        store_id            = bundle.getInt("store_id");
        audit_id            = bundle.getInt("audit_id");
        product_id          = bundle.getInt("product_id");

        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        routeRepo               = new RouteRepo(activity);
        storeRepo               = new StoreRepo(activity);
        companyRepo             = new CompanyRepo(activity);
        auditRoadStoreRepo      = new AuditRoadStoreRepo(activity);
        productRepo             = new ProductRepo(activity);
        productDistributorRepo  = new ProductDistributorRepo(activity);
        purcharseOrderRepo      = new PurcharseOrderRepo(activity);


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
        tvSubTotal          = (TextView)    findViewById(R.id.tvSubTotal) ;

        tvProductName      = (TextView)    findViewById(R.id.tvProductName) ;
        tvProm6m           = (TextView)    findViewById(R.id.tvProm6m) ;
        tvCuotaMes         = (TextView)    findViewById(R.id.tvCuotaMes) ;
        tvAvance           = (TextView)    findViewById(R.id.tvAvance) ;
        tvPedidoSugerido   = (TextView)    findViewById(R.id.tvPedidoSugerido) ;
        tvDistibutorPrice  = (TextView)    findViewById(R.id.tvDistibutorPrice) ;

        etCantidad          = (EditText)    findViewById(R.id.etCantidad);

        btSaveGeo           = (Button)      findViewById(R.id.btSaveGeo);
        btSave              = (Button)      findViewById(R.id.btSave);
        btPhoto             = (ImageButton) findViewById(R.id.btPhoto);
        swYesNo             = (Switch)      findViewById(R.id.swYesNo);
        lyComment           = (LinearLayout)findViewById(R.id.lyComment);
        lyOptions           = (LinearLayout)findViewById(R.id.lyOptions);
        lyDistributorPrice  = (LinearLayout)findViewById(R.id.lyDistributorPrice);

        store               = (Store)           storeRepo.findById(store_id);
        route               = (Route)           routeRepo.findById(store.getRoute_id());
        auditRoadStore      = (AuditRoadStore)  auditRoadStoreRepo.findByStoreIdAndAuditId(store_id,audit_id);
        product             = (Product)         productRepo.findById(product_id);

        visit_id= store.getVisit_id();


        tvStoreFullName.setText(String.valueOf(store.getFullname()));
        tvStoreId.setText(String.valueOf(store.getId()));
        tvAddress.setText(String.valueOf(store.getAddress()));
        tvReferencia.setText(String.valueOf(store.getUrbanization()));
        tvDistrict.setText(String.valueOf(store.getDistrict()));

        tvType.setText(String.valueOf(store.getType()) + " (" + store.getCadenRuc() + ")");

        etCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) {
//                    tvSubTotal.setText(s + " - " + String.valueOf(start) + " - " + String.valueOf(before) + " - " + String.valueOf(count));
                    quantity = Integer.valueOf(s.toString());
                    productDistributors.clear();
                   // productDistributors = (ArrayList<ProductDistributor>) productDistributorRepo.findByProductIdAndDistributor(product_id,provider_id);
                    productDistributor = (ProductDistributor) productDistributorRepo.findByPriceInRank(product_id,provider_id,quantity);
                    price = Float.valueOf(productDistributor.getPrice().toString()) ;
                    total = quantity * price ;
                    tvSubTotal.setText(getString(R.string.text_simbol_soles) + " " + String.valueOf(total));
                } else {
                    tvSubTotal.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String cantidad ;

                cantidad = etCantidad.getText().toString();

                if(cantidad.length() < 1) {
                    Toast.makeText(activity,getString(R.string.text_requiere_catidad),Toast.LENGTH_LONG).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.message_save);
                builder.setMessage(R.string.message_save_information);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        product.setStatus(1);
                        productRepo.update(product);

                        purcharseOrder = new PurcharseOrder();
                        purcharseOrder.setCompany_id(company_id);
                        purcharseOrder.setMount(String.valueOf(total));
                        purcharseOrder.setPrice(String.valueOf(price));
                        purcharseOrder.setProvider_id(provider_id);
                        purcharseOrder.setProduct_id(product_id);
                        purcharseOrder.setQuantity(quantity);
                        purcharseOrder.setStore_id(store_id);
                        purcharseOrder.setUser_id(user_id);
                        purcharseOrder.setVisit_id(visit_id);

                        purcharseOrderRepo.create(purcharseOrder);

                        ArrayList<PurcharseOrder> purcharseOrders = (ArrayList<PurcharseOrder>) purcharseOrderRepo.findAll();

                        finish();
                        //new savePoll().execute();
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


        showToolbar(auditRoadStore.getList().getFullname().toString(),false);

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
            productPlanSale = AuditUtil.getProductPlanSale(company_id,store_id,visit_id,product_id);
            if ( productPlanSale.getId()==0 ) return false;

            //pDialog.setMessage(getString(R.string.text_obteniendo_distribuidores_precio));
            productDistributors = AuditUtil.getListProductDistributors(company_id,product_id);
            if ( productDistributors.isEmpty() ) return false;

            return true;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Boolean result) {
            // dismiss the dialog once product deleted

            if( productPlanSale.getId() == 0) {
                Toast.makeText(activity, R.string.message_prospeccion_venta_error, Toast.LENGTH_LONG).show();
            } else {

                tvProductName.setText(product.getFullname());
                tvProm6m.setText(String.valueOf(productPlanSale.getProm6m()));
                tvCuotaMes.setText(String.valueOf(productPlanSale.getCuotames()));
                tvAvance.setText(String.valueOf(productPlanSale.getAvance()));
                double cuota =  Double.valueOf(productPlanSale.getCuotames());
                double visitas = Double.valueOf(store.getVisit());
                double resul = cuota / visitas;
                tvPedidoSugerido.setText(String.valueOf(resul));

            }

            productDistributorRepo.deleteAll();
            //ArrayList<AuditRoadStore> auditRoadStores = (ArrayList<AuditRoadStore>) auditRoadStoreRepo.findByStoreId(store_id);
            for (ProductDistributor m: productDistributors){
                productDistributorRepo.create(m);
            }
            productDistributors.clear();
            productDistributors = (ArrayList<ProductDistributor>) productDistributorRepo.findByProviderProduct(product_id);
            //ArrayList<Distributor> distributors =

            if(radioGroup != null ){
                radioGroup.clearCheck();
            }

            lyOptions.removeAllViews();

            radioGroup = new RadioGroup(activity);
            radioGroup.setOrientation(LinearLayout.VERTICAL);

            if(productDistributors.size() > 0) {
                radioButtonArray = new RadioButton[productDistributors.size()];

                int counter =0;

                for (ProductDistributor po: productDistributors){
                    radioButtonArray[counter] = new RadioButton(activity);
                    radioButtonArray[counter].setText(po.getFullname());
                    radioButtonArray[counter].setTag(po.getProvider_id());
//                    if(po.getComment()==1) {
                        radioButtonArray[counter].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                lyDistributorPrice.setVisibility(View.VISIBLE);
                                provider_id= Integer.valueOf(v.getTag().toString());
                                etCantidad.setText("");
                                tvDistibutorPrice.setText("");
                                etCantidad.setEnabled(true);
                                etCantidad.setFocusable(true);

                                ArrayList<ProductDistributor> productDistributorPrices = (ArrayList<ProductDistributor>) productDistributorRepo.findByProductIdAndDistributor(product_id,provider_id);
                                for (ProductDistributor m: productDistributorPrices){
                                    tvDistibutorPrice.append("(Escala: "+ String.valueOf(m.getQuantity()) + " - " + String.valueOf(m.getQuantityMax()) + ") " + " S/." + String.valueOf(m.getPrice()) + "\n");
                                }

                            }
                        });

                    radioGroup.addView(radioButtonArray[counter]);
                    counter ++;
                }
                lyOptions.addView(radioGroup);

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
