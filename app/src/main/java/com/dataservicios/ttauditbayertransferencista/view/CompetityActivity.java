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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.adapter.CompetityAdapterRecyclerView;
import com.dataservicios.ttauditbayertransferencista.adapter.CustomItemClickListener;
import com.dataservicios.ttauditbayertransferencista.adapter.PublicityAdapterReciclerView;
import com.dataservicios.ttauditbayertransferencista.adapter.PublicityHistoryAdapterReciclerView;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.Audit;
import com.dataservicios.ttauditbayertransferencista.model.AuditRoadStore;
import com.dataservicios.ttauditbayertransferencista.model.Company;
import com.dataservicios.ttauditbayertransferencista.model.Poll;
import com.dataservicios.ttauditbayertransferencista.model.Publicity;
import com.dataservicios.ttauditbayertransferencista.model.PublicityHistory;
import com.dataservicios.ttauditbayertransferencista.model.Route;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRepo;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRoadStoreRepo;
import com.dataservicios.ttauditbayertransferencista.repo.CompanyRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PublicityHistoryRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PublicityRepo;
import com.dataservicios.ttauditbayertransferencista.repo.RouteRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;
import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;
import com.dataservicios.ttauditbayertransferencista.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class CompetityActivity extends AppCompatActivity {
    private static final String LOG_TAG = PublicitiesActivity.class.getSimpleName();
    private ProgressDialog                          pDialog;
    private SessionManager                          session;
    private Activity                                activity =  this;
    private int                                     user_id;
    private int                                     store_id;
    private int                                     audit_id;
    private TextView                                tvPublicityHistoryTitle;
    private TextView                                tvTotal;
    private Button                                  btSave;
    private PublicityRepo                           publicityRepo ;
    private PublicityHistoryRepo                    publicityHistoryRepo;
    private AuditRepo                               auditRepo ;
    private StoreRepo                               storeRepo;
    private CompanyRepo                             companyRepo;
    private RouteRepo                               routeRepo;
    //private Ac                               routeRepo;
    private AuditRoadStoreRepo                      auditRoadStoreRepo ;
    private CompetityAdapterRecyclerView            competityAdapterRecyclerView;
    private RecyclerView                            competityRecyclerView;
    private Publicity                               publicity ;
    private Audit                                   audit ;
    private Store                                   store;
    private Company                                 company;
    private Route                                   route;
    private PublicityHistory                        publicityHistory;
    private AuditRoadStore                          auditRoadStore;
    private ArrayList<Publicity>                    publicities;
    private ArrayList<PublicityHistory>             publicitiesHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competity);
        tvPublicityHistoryTitle = (TextView) findViewById(R.id.tvPublicityHistoryTitle);
        tvTotal                 = (TextView) findViewById(R.id.tvTotal);
        btSave                  = (Button) findViewById(R.id.btSave);

        DatabaseManager.init(this);

        publicityRepo           = new PublicityRepo(activity);
        auditRepo               = new AuditRepo(activity);
        publicityHistoryRepo    = new PublicityHistoryRepo(activity);
        storeRepo               = new StoreRepo(activity);
        companyRepo             = new CompanyRepo(activity);
        routeRepo               = new RouteRepo(activity);
        auditRoadStoreRepo      = new AuditRoadStoreRepo(activity);

        Bundle bundle = getIntent().getExtras();
        store_id = bundle.getInt("store_id");
        audit_id = bundle.getInt("audit_id");


        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        audit               = (Audit) auditRepo.findById(audit_id);
        store               = (Store) storeRepo.findById(store_id);
        route               = (Route) routeRepo.findById(store.getRoute_id());
        company             = (Company) companyRepo.findFirstReg();
        auditRoadStore      = (AuditRoadStore)  auditRoadStoreRepo.findByStoreIdAndAuditId(store_id,audit_id);
        showToolbar(audit.getFullname().toString(),false);


//        publicityRecyclerView  = (RecyclerView) findViewById(R.id.publicity_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        publicityRecyclerView.setLayoutManager(linearLayoutManager);

        publicities = (ArrayList<Publicity>) publicityRepo.findAll();

        int contador = 0;
        // publicities.indexOf(m.getId());
        if(store.getType().equals("AASS")) {
            publicities.clear();
            publicities.add((Publicity) publicityRepo.findById(585));
            publicities.add((Publicity) publicityRepo.findById(586));
            publicities.add((Publicity) publicityRepo.findById(683));
        } else {
            for(int i=0; i<publicities.size(); i++) {
                if(publicities.get(i).getId() == 585 ) publicities.remove(i);
            }
            for(int i=0; i<publicities.size(); i++) {
                if(publicities.get(i).getId() == 586 ) publicities.remove(i);
            }
            for(int i=0; i<publicities.size(); i++) {
                if(publicities.get(i).getId() == 683 ) publicities.remove(i);
            }
        }


//        publicityAdapterReciclerView =  new PublicityAdapterReciclerView(publicities, R.layout.cardview_publicity, activity,store_id,audit_id);
//
//        publicityRecyclerView.setAdapter(publicityAdapterReciclerView);


//        publicityHistoryRecyclerView = (RecyclerView) findViewById(R.id.publicities_history_recycler_view);
        linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        publicityHistoryRecyclerView.setLayoutManager(linearLayoutManager);


        publicitiesHistory = (ArrayList<PublicityHistory>) publicityHistoryRepo.findAll();

        if(publicitiesHistory.size()>0){
            tvPublicityHistoryTitle.setText(R.string.text_publicity_history);
        }
//        competityAdapterRecyclerView =  new PublicityHistoryAdapterReciclerView(publicitiesHistory, R.layout.cardview_publicity_history, activity, new CustomItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//
//                int publicity_history_id = publicitiesHistory.get(position).getId();
//                //Toast.makeText(activity,publicity_history_id,Toast.LENGTH_SHORT).show();
//                Bundle bundle = new Bundle();
//                bundle.putInt("publicity_history_id", publicity_history_id);
//                Intent intent = new Intent(activity,PublicityHistoryPhotoActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//
//            }
//        });
//        publicityHistoryRecyclerView.setAdapter(publicityHistoryAdapterReciclerView);

        int total               = publicities.size();
        int publicitiesAudits   = 0;

//        for(Publicity p: publicities){
//            if(p.getStatus()==1) publicitiesAudits ++;
//        }
//
//        tvTotal.setText(String.valueOf(publicitiesAudits) + " de " + String.valueOf(total));
//
//        if(publicities.size() == 0) {
//            btSave.setVisibility(View.INVISIBLE);
//        }


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (Publicity p:publicities ){
//
//                    if(p.getStatus()==0){
//                        alertDialogBasico(getString(R.string.message_audit_material_pop) + ": \n " + p.getFullname().toString());
//                        return;
//                    }
//                }
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.message_save);
                builder.setMessage(R.string.message_save_information);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(store.getStatus_change() ==1) {
                            finish();
                        } else if(store.getStatus_change() == 0) {
                            if(store.getType().equals("AASS")) {
                                new savePoll().execute();
                            } else {
                                Poll poll = new Poll();
                                poll.setPublicity_id(0);
                                poll.setOrder(28);
                                PollPublicityActivity.createInstance((Activity) activity, store_id,audit_id,poll);
                                finish();
                            }
                        }

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



    class savePoll extends AsyncTask<Void, Integer , Boolean> {
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


            if (!AuditUtil.closeAuditStore(audit_id, store_id, company.getId(), route.getId())) return false;
            if(store.getVisit_id()==1){
                if (!AuditUtil.sendAlertPlanningPop(company.getId(), store_id,route.getId())) return false;
            }


            return true;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Boolean result) {
            // dismiss the dialog once productDetail deleted
            if (result){

                //AuditRoadStore auditRoadStore = (AuditRoadStore) auditRoadStoreRepo.findByStoreIdAndAuditId(store_id,audit_id);
                AuditRoadStore auditRoadStore = (AuditRoadStore) auditRoadStoreRepo.findByStoreIdAndAuditIdAndVisitId(store_id,audit_id,store.getVisit_id());
                auditRoadStore.setAuditStatus(1);
                auditRoadStoreRepo.update(auditRoadStore);
                finish();
            } else {
                Toast.makeText(activity , R.string.message_no_save_data , Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
    }


    public void showToolbar(String title, boolean upButton){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        finish();
        startActivity(getIntent());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            //Toast.makeText(MyActivity, "No se puede volver atras, los datos ya fueron guardado, para modificar pongase en contácto con el administrador", Toast.LENGTH_LONG).show();
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {

//        if(publicities.size() == 0 ) {
//            super.onBackPressed ();
//        } else {
//            for (Publicity p:publicities ){
//
//                if(p.getStatus()==0){
//                    alertDialogBasico(getString(R.string.message_audit_material_pop) + ": \n " + p.getFullname().toString());
//                    return;
//                }
//            }
//            alertDialogBasico(getString(R.string.message_save_audit_material_pop));
//        }

        if(publicities.size() == 0 ) {
            super.onBackPressed ();
        } else {
            alertDialogBasico(getString(R.string.message_save_audit_products));
        }

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
