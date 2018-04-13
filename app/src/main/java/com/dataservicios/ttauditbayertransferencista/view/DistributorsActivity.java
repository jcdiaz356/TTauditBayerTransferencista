package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
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
import com.dataservicios.ttauditbayertransferencista.model.Order;
import com.dataservicios.ttauditbayertransferencista.model.OrderDetail;
import com.dataservicios.ttauditbayertransferencista.model.Poll;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.ProductDistributor;
import com.dataservicios.ttauditbayertransferencista.model.ProductPlanSale;
import com.dataservicios.ttauditbayertransferencista.model.PurcharseOrder;
import com.dataservicios.ttauditbayertransferencista.model.Route;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRoadStoreRepo;
import com.dataservicios.ttauditbayertransferencista.repo.CompanyRepo;
import com.dataservicios.ttauditbayertransferencista.repo.DistributorRepo;
import com.dataservicios.ttauditbayertransferencista.repo.OrderDetailRepo;
import com.dataservicios.ttauditbayertransferencista.repo.OrderRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductDistributorRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PurcharseOrderRepo;
import com.dataservicios.ttauditbayertransferencista.repo.RouteRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;
import com.dataservicios.ttauditbayertransferencista.util.GPSTracker;
import com.dataservicios.ttauditbayertransferencista.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class DistributorsActivity extends AppCompatActivity {
    private static final String LOG_TAG = DistributorsActivity.class.getSimpleName();
    private SessionManager session;
    private Activity activity =  this;
    private ProgressDialog pDialog;
    private String selectedOptions = "0";
    private TextView tvStoreFullName,tvStoreId,tvAddress ,tvReferencia,tvDistrict,tvAuditoria, tvType ;
    private Button                          btSave,btViewOrder,btOrder;
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
    private DistributorRepo                 distributorRepo;
    private PurcharseOrderRepo              purcharseOrderRepo;
    private PurcharseOrder                  purcharseOrder;
    private OrderRepo                       orderRepo;
    private OrderDetailRepo                 orderDetailRepo;
    private Product                         product;
    private Route                           route ;
    private Store                           store ;
    private AuditRoadStore                  auditRoadStore;
    private ProductPlanSale                 productPlanSale;
    private ProductDistributor              productDistributor;
    private ArrayList<Distributor>          distributors;
    private GPSTracker                      gpsTracker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributors);


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
        distributorRepo         = new DistributorRepo(activity);
        purcharseOrderRepo      = new PurcharseOrderRepo(activity);
        orderRepo               = new OrderRepo(activity);
        orderDetailRepo         = new OrderDetailRepo(activity);


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
        btViewOrder         = (Button)      findViewById(R.id.btViewOrder);
        btOrder             = (Button)      findViewById(R.id.btOrder);
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
//                        finish();
                        //new savePoll().execute();
                        Poll poll = new Poll();
                        poll.setOrder(13);
                        PollActivity.createInstance((Activity) activity, store_id,audit_id,poll);
                        finish();
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

        btOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counterSelected =0;
                if(radioButtonArray != null) {

                    for(RadioButton r:radioButtonArray ) {
                        if(r.isChecked()){
                            selectedOptions=r.getTag().toString();
                            counterSelected ++;
                        }
                    }
                    if(counterSelected==0){
                        Toast.makeText(activity, R.string.message_select_options, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putInt("store_id", Integer.valueOf(store_id));
                bundle.putInt("audit_id", Integer.valueOf(audit_id));
                bundle.putInt("distributor_id", Integer.valueOf(selectedOptions));
                Intent intent = new Intent(activity,ProductCompetityActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);

            }
        });

        btViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("store_id", Integer.valueOf(store_id));
                bundle.putInt("audit_id", Integer.valueOf(audit_id));
//                bundle.putInt("distributor_id", Integer.valueOf(selectedOptions));
                Intent intent = new Intent(activity,ProductOrdersTotalActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });


        showToolbar(auditRoadStore.getList().getFullname().toString(),false);

        loadDistributorControls();

    }


    private void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    private void loadDistributorControls(){

        distributors = (ArrayList<Distributor>) distributorRepo.findAll();
        //ArrayList<Distributor> distributors =

        if(radioGroup != null ){
            radioGroup.clearCheck();
        }

        lyOptions.removeAllViews();

        radioGroup = new RadioGroup(activity);
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        if(distributors.size() > 0) {
            radioButtonArray = new RadioButton[distributors.size()];

            int counter =0;

            for (Distributor po: distributors){
                radioButtonArray[counter] = new RadioButton(activity);
                radioButtonArray[counter].setText(po.getCode() + " - " +  po.getFullName());
                radioButtonArray[counter].setTag(po.getId());
//                    if(po.getComment()==1) {
                radioButtonArray[counter].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });

                radioGroup.addView(radioButtonArray[counter]);
                counter ++;
            }
            lyOptions.addView(radioGroup);

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
//            alertDialogBasico(getString(R.string.message_save_audit_products));
//        }

//        super.onBackPressed ();

        ArrayList<Order> orders = (ArrayList<Order>) orderRepo.findByOrder(company_id,store_id,visit_id);

        if(orders.size() >0 ) {
            alertDialog(getString(R.string.message_pending_order_finalize),0);
        } else  {
             super.onBackPressed ();
        }

    }


    /**
     * Alerta dialogo
     * @param message
     * @param type 0= typo ye, 1= yes no
     */
    private void alertDialog(String message,int type) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        switch (type){

            case 0:

                builder.setMessage(message);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                });
                builder.show();
                break;
            case 1:

                builder.setMessage(getString(R.string.message_exit_not_save_information));
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return ;
                    }
                });
                builder.show();
                break;
        }
    }
}
