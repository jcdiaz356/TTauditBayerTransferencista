package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.CompoundButton;
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
import com.dataservicios.ttauditbayertransferencista.model.ActivityPublicity;
import com.dataservicios.ttauditbayertransferencista.model.AuditRoadStore;
import com.dataservicios.ttauditbayertransferencista.model.Company;
import com.dataservicios.ttauditbayertransferencista.model.Laboratory;
import com.dataservicios.ttauditbayertransferencista.model.Media;
import com.dataservicios.ttauditbayertransferencista.model.Poll;
import com.dataservicios.ttauditbayertransferencista.model.PollDetail;
import com.dataservicios.ttauditbayertransferencista.model.PollOption;

import com.dataservicios.ttauditbayertransferencista.model.Publicity;
import com.dataservicios.ttauditbayertransferencista.model.Route;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.ActivityPublicityRepo;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRoadStoreRepo;
import com.dataservicios.ttauditbayertransferencista.repo.CompanyRepo;
import com.dataservicios.ttauditbayertransferencista.repo.LaboratoryRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PollOptionRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PollRepo;

import com.dataservicios.ttauditbayertransferencista.repo.PublicityRepo;
import com.dataservicios.ttauditbayertransferencista.repo.RouteRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;

import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;
import com.dataservicios.ttauditbayertransferencista.util.GPSTracker;
import com.dataservicios.ttauditbayertransferencista.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class PollCompetitiesActivity extends AppCompatActivity {
    private static final String LOG_TAG = StoreAuditActivity.class.getSimpleName();
    private SessionManager session;
    private Activity activity =  this;
    private ProgressDialog pDialog;
    private TextView                tvStoreFullName,tvStoreId,tvAddress ,tvReferencia,tvDistrict,tvAuditoria,tvPollTwo,tvPublicity,tvType,tvPollNew ;
    private EditText                etComent,etPollNewComent;
    private EditText                etCommentOption;
    private Button                  btSaveGeo;
    private Button                  btSave;
    private CheckBox[]              checkBoxArray;
    private RadioButton[]           radioButtonArray;
    private RadioGroup              radioGroup;
    private Switch                  swYesNo;
    private ImageButton             btPhoto;
    private LinearLayout            lyComment;
    private LinearLayout            lyOptions;
    private LinearLayout            lyOptionComment;
    private LinearLayout            lyPublicity;
    private LinearLayout            lyPollNew;
    private int                     user_id;
    private int                     store_id;
    private int                     audit_id;
    private int                     company_id;
    private int                     orderPoll;
    private int                     category_product_id;
    private int                     publicity_id;
    private int                     product_id;
    private Route                   route ;
    private Store                   store ;
    private Poll                    poll;
    private Poll                    pollTwo;
    private PollOption              pollOption;
    private PollDetail              pollDetail;
    private RouteRepo               routeRepo ;
    private AuditRoadStoreRepo      auditRoadStoreRepo ;
    private StoreRepo               storeRepo ;
    private CompanyRepo             companyRepo ;
    private PollRepo                pollRepo ;
    private AuditRoadStore          auditRoadStore;
    private PollOptionRepo          pollOptionRepo;
    private ActivityPublicityRepo   activityPublicityRepo;
    private LaboratoryRepo          laboratoryRepo;
    private GPSTracker              gpsTracker;
    private ArrayList<PollOption>   pollOptions;
    private ActivityPublicity       activityPublicity;
    private ArrayList<Laboratory>   laboratories;
    private int                     isYesNo;
    private String                  comment,pollNewComent;
    private String                  selectedOptions;
    private String                  commentOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_competities);
        tvStoreFullName     = (TextView)    findViewById(R.id.tvStoreFullName) ;
        tvStoreId           = (TextView)    findViewById(R.id.tvStoreId) ;
        tvAddress           = (TextView)    findViewById(R.id.tvAddress) ;
        tvReferencia        = (TextView)    findViewById(R.id.tvReferencia) ;
        tvDistrict          = (TextView)    findViewById(R.id.tvDistrict) ;
        tvAuditoria         = (TextView)    findViewById(R.id.tvAuditoria) ;
        tvPollTwo           = (TextView)    findViewById(R.id.tvPollTwo) ;
        tvPublicity         = (TextView)    findViewById(R.id.tvPublicity) ;
        tvType              = (TextView)    findViewById(R.id.tvType) ;
        tvPollNew           = (TextView)    findViewById(R.id.tvPollNew) ;
        etPollNewComent     = (EditText)    findViewById(R.id.etPollNewComent) ;
        btSaveGeo           = (Button)      findViewById(R.id.btSaveGeo);
        btSave              = (Button)      findViewById(R.id.btSave);
        btPhoto             = (ImageButton) findViewById(R.id.btPhoto);
        swYesNo             = (Switch)      findViewById(R.id.swYesNo);
        lyComment           = (LinearLayout)findViewById(R.id.lyComment);
        lyOptions           = (LinearLayout)findViewById(R.id.lyOptions);
        lyOptionComment     = (LinearLayout)findViewById(R.id.lyOptionComment);
        lyPollNew           = (LinearLayout) findViewById(R.id.lyPollNew);
        DatabaseManager.init(this);
        gpsTracker = new GPSTracker(activity);
        if(!gpsTracker.canGetLocation()){
            gpsTracker.showSettingsAlert();
        }

        Bundle bundle = getIntent().getExtras();
        store_id            = bundle.getInt("store_id");
        audit_id            = bundle.getInt("audit_id");
        publicity_id        = bundle.getInt("publicity_id");
        orderPoll           = 10;

        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        routeRepo               = new RouteRepo(activity);
        storeRepo               = new StoreRepo(activity);
        companyRepo             = new CompanyRepo(activity);
        auditRoadStoreRepo      = new AuditRoadStoreRepo(activity);
        pollRepo                = new PollRepo(activity);
        pollOptionRepo          = new PollOptionRepo((activity));
        activityPublicityRepo   = new ActivityPublicityRepo(activity);
        laboratoryRepo          = new LaboratoryRepo(activity);


        etCommentOption     = new EditText(activity);
        etComent            = new EditText(activity);

        final ArrayList<Company> companies = (ArrayList<Company>) companyRepo.findAll();
        for (Company c: companies){
            company_id = c.getId();
        }

        store               = (Store)           storeRepo.findById(store_id);
        route               = (Route)           routeRepo.findById(store.getRoute_id());
        auditRoadStore      = (AuditRoadStore)  auditRoadStoreRepo.findByStoreIdAndAuditId(store_id,audit_id);
        poll                = (Poll)            pollRepo.findByCompanyAuditIdAndOrder(auditRoadStore.getList().getCompany_audit_id(),orderPoll);
        pollTwo             = (Poll)            pollRepo.findByCompanyAuditIdAndOrder(auditRoadStore.getList().getCompany_audit_id(),orderPoll + 1);
        activityPublicity   = (ActivityPublicity) activityPublicityRepo.findById(publicity_id);
        laboratories        = (ArrayList<Laboratory>) laboratoryRepo.findAll();

        tvPublicity.setText(activityPublicity.getFullname().toString());

        pollTwo.setCategory_product_id(category_product_id);
        pollTwo.setProduct_id(product_id);
        pollTwo.setPublicity_id(publicity_id);
        pollOptions = (ArrayList<PollOption>) pollOptionRepo.findByPollId(pollTwo.getId());

        //showToolbar(publicity.getFullname(),false);

        tvStoreFullName.setText(String.valueOf(store.getFullname()));
        tvStoreId.setText(String.valueOf(store.getId()));
        tvAddress.setText(String.valueOf(store.getAddress()));
        tvReferencia.setText(String.valueOf(store.getUrbanization()));
        tvDistrict.setText(String.valueOf(store.getDistrict()));
        tvAuditoria.setText(auditRoadStore.getList().getFullname().toString());
        tvPollTwo.setText(pollTwo.getQuestion().toString());
        tvType.setText(String.valueOf(store.getType()) + " (" + store.getCadenRuc() + ")");

        showToolbar(auditRoadStore.getList().getFullname().toString(),false);

        tvPublicity.setText(activityPublicity.getFullname().toString());


        if(activityPublicity.getId() == 793){
            lyPollNew.setVisibility(View.VISIBLE);
            tvPollNew.setText(poll.getQuestion().toString());
        }




        btPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        showPollOptionsControl(true);

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

                        commentOptions = "";
                        comment = "";
                        pollNewComent = etPollNewComent.getText().toString();
                        selectedOptions="";
                        int counterSelected =0;

                        if(checkBoxArray != null) {
                            for(CheckBox r:checkBoxArray ) {
                                if(r.isChecked()){
                                    counterSelected ++;
                                }
                            }

                            if(counterSelected==0){
                                Toast.makeText(activity, R.string.message_select_options, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        comment = etComent.getText().toString();
                        commentOptions = etCommentOption.getText().toString();

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

    private void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

//    private void takePhoto() {
//
//        Media media = new Media();
//        media.setStore_id(store_id);
//        media.setPoll_id(poll.getId());
//        media.setCompany_id(company_id);
//        media.setPublicity_id(publicity_id);
//        media.setVisit_id(store.getVisit_id());
//        media.setType(1);
//        AndroidCustomGalleryActivity.createInstance((Activity) activity, media);
//    }

    private void takePhoto() {

        Intent intent = new Intent(activity, AndroidCustomGalleryActivity.class);
        intent.putExtra("store_id"              ,store_id);
        intent.putExtra("poll_id"               ,poll.getId());
        intent.putExtra("company_id"            ,company_id);
        intent.putExtra("publicities_id"        ,publicity_id);
        intent.putExtra("product_id"            ,product_id);
        intent.putExtra("category_product_id"   ,category_product_id);
        intent.putExtra("monto"                 ,"");
        intent.putExtra("razon_social"          ,"");
        intent.putExtra("tipo"                  ,1);
        startActivity(intent);
    }

    private void showPollOptionsControl(boolean visibility) {
//        ArrayList<PollOption>  pollOptions;
//        pollOptions = (ArrayList<PollOption>) pollOptionRepo.findByPollId(poll.getId());


        if(radioGroup != null ){
            radioGroup.clearCheck();
        }
        if (visibility){
            lyOptions.removeAllViews();
            lyOptionComment.removeAllViews();

                    checkBoxArray = new CheckBox[laboratories.size()];
                    int  counter =0;
                    for ( final Laboratory po: laboratories){
                        checkBoxArray[counter] = new CheckBox(activity);
                        checkBoxArray[counter].setText(po.getFullname());
                        checkBoxArray[counter].setTag(po.getId());
                        if(po.getId()==477) {

                            checkBoxArray[counter].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (buttonView.isChecked()) {
                                        lyOptionComment.removeAllViews();
                                        etCommentOption.setHint(activity.getString(R.string.text_comment));
//                                        etCommentOption.setTag(po.getCodigo().toString());
                                        lyOptionComment.addView(etCommentOption);
                                    }
                                    else
                                    {
                                        //not checked
                                        lyOptionComment.removeAllViews();
                                    }
                                }
                            });

                        }

                        lyOptions.addView(checkBoxArray[counter]);
                        counter ++;
                    }
                    //lyOptions.addView(radioGroup);


        } else {

            lyOptions.removeAllViews();
            lyOptionComment.removeAllViews();
            radioButtonArray = null;
            checkBoxArray = null;
        }

    }


    /**
     * Guarda la pregunta
     */
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

            pollDetail = new PollDetail();
            pollDetail.setPoll_id(poll.getId());
            pollDetail.setStore_id(store_id);
            pollDetail.setSino(poll.getSino());
            pollDetail.setOptions(poll.getOptions());
            pollDetail.setLimits(0);
            pollDetail.setMedia(poll.getMedia());
            pollDetail.setComment(1);
            pollDetail.setResult(1);
            pollDetail.setLimite("0");
            pollDetail.setComentario(pollNewComent);
            pollDetail.setAuditor(user_id);
            pollDetail.setProduct_id(poll.getProduct_id());
            pollDetail.setCategory_product_id(poll.getCategory_product_id());
            pollDetail.setPublicity_id(publicity_id);
            pollDetail.setLaboratory_id(0);
            pollDetail.setCompany_id(company_id);
            pollDetail.setCommentOptions(poll.getComment());
            pollDetail.setSelectdOptions("");
            pollDetail.setVisit_id(store.getVisit_id());
            pollDetail.setSelectedOtionsComment("");
            pollDetail.setPriority(0);

            if (!AuditUtil.insertPollDetail(pollDetail)) return false;

            for(CheckBox r:checkBoxArray ) {
                if(r.isChecked()){
                    pollDetail.setPoll_id(pollTwo.getId());
                    int laboratory_id=(Integer) r.getTag();
                    pollDetail.setLaboratory_id(Integer.valueOf(laboratory_id));
                    if (laboratory_id == 477) {pollDetail.setComentario(commentOptions); }  else {pollDetail.setComentario("");}
                    if (!AuditUtil.insertPollDetail(pollDetail)) return false;

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
               // resulProcess(orderPoll);
                activityPublicity.setStatus(1);
                activityPublicityRepo.update(activityPublicity);
                finish();
            } else {
               // Toast.makeText(activity , R.string.message_no_save_data , Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
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
        //alertDialogBasico(getString(R.string.message_audit_init) );
        super.onBackPressed();
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
