package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.model.Contact;
import com.dataservicios.ttauditbayertransferencista.model.Credit;
import com.dataservicios.ttauditbayertransferencista.model.Distributor;
import com.dataservicios.ttauditbayertransferencista.repo.DistributorRepo;
import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class CreditLineActivity extends AppCompatActivity {
    private static final String LOG_TAG = CreditLineActivity.class.getSimpleName();
    private Activity activity =  this;
    private ProgressDialog pDialog;

    private int store_id;
    private TextView tvTotal;
    private TextView[] textViewArray;
    private LinearLayout lyContent;
    private DistributorRepo distributorRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_line);

        Bundle bundle = getIntent().getExtras();
        store_id = bundle.getInt("store_id");


        tvTotal      = (TextView) findViewById(R.id.tvTotal);
        lyContent   = (LinearLayout) findViewById(R.id.lyContent);

        distributorRepo = new DistributorRepo(activity);


        showToolbar(getString(R.string.text_line_credit),false);

        new loadContact().execute();




        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fabMenuLineCredit);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.action_add_line_credit:
                        Bundle bundle = new Bundle();
                        bundle.putInt("store_id", Integer.valueOf(store_id));

                        Intent intent = new Intent(activity,CreditActivity.class);
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                        finish();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }



    class loadContact extends AsyncTask<Void, Integer, ArrayList<Credit>> {

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
        protected ArrayList<Credit> doInBackground(Void... params) {
            // TODO Auto-generated method stub

            ArrayList<Credit> credits;
            credits = AuditUtil.getListCreditsStore(store_id);

            return credits;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(ArrayList<Credit> credits) {
            // dismiss the dialog once product deleted

            if (credits.size() > 0) {
                textViewArray = new TextView[credits.size()];
                lyContent.removeAllViews();
                tvTotal.setText(getString(R.string.message_found) + " " + String.valueOf(credits.size()) +  " distribuidores. " + getString(R.string.text_credito));

                for (Credit m:credits) {
                    int counter =0;
                    Distributor distributor =  new Distributor();
                    distributor = (Distributor) distributorRepo.findById(m.getUser_id());
                    textViewArray[counter] = new TextView(activity);
                    textViewArray[counter].setPadding(0,0,0,12);
                    textViewArray[counter].setText(Html.fromHtml("<b>Distribuidor:</b> " + distributor.getFullName()+ "<br><b>Crédito:</b> " + m.getLinea()+ "<br><b>Plazo:</b> " + m.getPlazo()  ));
                    lyContent.addView(textViewArray[counter]);
                    counter ++;
                }

            } else {
                tvTotal.setText(getString(R.string.message_no_found));
            }

            pDialog.dismiss();
        }


        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }



    public void showToolbar(String title, boolean upButton){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
}
