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
import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;


public class ContactsActivity extends AppCompatActivity {

    private static final String LOG_TAG = ContactsActivity.class.getSimpleName();
    private Activity activity =  this;
    private ProgressDialog pDialog;

    private int store_id;
    private TextView tvTotal;
    private TextView[] textViewArray;
    private LinearLayout lyContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Bundle bundle = getIntent().getExtras();
        store_id = bundle.getInt("store_id");

        tvTotal      = (TextView) findViewById(R.id.tvTotal);
        lyContent   = (LinearLayout) findViewById(R.id.lyContent);


        showToolbar(getString(R.string.text_contcts),false);

        new loadContact().execute();




        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fabMenuContact);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.action_add_contact:
                        Bundle bundle = new Bundle();
                        bundle.putInt("store_id", Integer.valueOf(store_id));
                        Intent intent = new Intent(activity,ContactNewActivity.class);
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



    class loadContact extends AsyncTask<Void, Integer, ArrayList<Contact>> {

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
        protected ArrayList<Contact> doInBackground(Void... params) {
            // TODO Auto-generated method stub

            ArrayList<Contact> contacts;
            contacts = AuditUtil.getListContact(store_id);

            return contacts;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(ArrayList<Contact> contacts) {
            // dismiss the dialog once product deleted

            if (contacts.size() > 0) {
                textViewArray = new TextView[contacts.size()];
                lyContent.removeAllViews();
                tvTotal.setText(getString(R.string.message_found) + " " + String.valueOf(contacts.size()) + " " + getString(R.string.text_contcts));
                for (Contact m:contacts) {
                        int counter =0;
                        textViewArray[counter] = new TextView(activity);
                        textViewArray[counter].setPadding(0,0,0,12);
                        textViewArray[counter].setText(Html.fromHtml("<b>ID:</b> " + m.getId() + "<br><b>Nombre:</b> " + m.getFullname()+ "<br><b>Teléfono:</b> " + m.getPhone() + "<br><b>Cel:</b> " + m.getCelular() +  "<br><b>Nacimiento:</b> " +  m.getF_nac().substring(0,10) ));
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
