package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.Contact;
import com.dataservicios.ttauditbayertransferencista.model.Credit;
import com.dataservicios.ttauditbayertransferencista.model.Distributor;
import com.dataservicios.ttauditbayertransferencista.repo.DistributorRepo;
import com.dataservicios.ttauditbayertransferencista.repo.RouteRepo;
import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class CreditActivity extends AppCompatActivity {
    private static final String LOG_TAG = ContactsActivity.class.getSimpleName();
    private Activity activity =  this;
    private ProgressDialog pDialog;

    private int store_id;
    private TextView tvTotal;
    private TextView[] textViewArray;
    private CheckBox[] checkBoxesDistributors;
    private EditText[] editTextsPlazos;
    private EditText[] editTextsLines;
    private Button btSave;

    private LinearLayout lyContent;

    private DistributorRepo distributorRepo;
    private ArrayList<Distributor> distributors;
    private ArrayList<Credit> credits;



    // int counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        DatabaseManager.init(this);

        Bundle bundle = getIntent().getExtras();
        store_id = bundle.getInt("store_id");

        tvTotal      = (TextView) findViewById(R.id.tvTotal);
        lyContent   = (LinearLayout) findViewById(R.id.lyContent);
        btSave = (Button) findViewById(R.id.btSave) ;

        distributorRepo  = new DistributorRepo(activity);

        distributors = (ArrayList<Distributor>) distributorRepo.findAll();


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.save);
                builder.setMessage(R.string.saveInformation);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //new saveAniversary().execute();
                        if(checkBoxesDistributors != null && checkBoxesDistributors.length > 0) {
                            int counter = 0;
                            credits = new ArrayList<Credit>();
                            credits.clear();
                            for(CheckBox r:checkBoxesDistributors ) {

                                if(r.isChecked()){
                                   if(editTextsLines[counter].getText().toString().trim().equals(""))  {
                                        Toast.makeText(activity,"Ingrese el crédito",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if(editTextsPlazos[counter].getText().toString().trim().equals(""))  {
                                        Toast.makeText(activity,"Ingrese el plazo",Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    Credit credit = new Credit();
                                    credit.setStore_id(store_id);
                                    credit.setUser_id(Integer.valueOf(r.getTag().toString()));
                                    credit.setPlazo(String.valueOf(editTextsPlazos[counter].getText().toString()));
                                    credit.setLinea(String.valueOf(editTextsLines[counter].getText().toString()));
                                    credits.add(credit);
                                }
                                counter ++ ;
                            }

                            new saveCredit().execute();
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

//                if (etDate.getText().toString().trim().equals("") ) {
//                    Toast toast = Toast.makeText(activity, R.string.message_requiere_date , Toast.LENGTH_SHORT);
//                    return;
//                } else {
//                    new saveAniversary().execute();
//                }

            }
        });


        if (distributors.size() > 0) {
            textViewArray = new TextView[distributors.size()];

            checkBoxesDistributors = new CheckBox[distributors.size()];
            editTextsLines  = new EditText[distributors.size()];
            editTextsPlazos  = new EditText[distributors.size()];
            lyContent.removeAllViews();
            int counter = 0;
            for (Distributor m:distributors) {

//                textViewArray[counter] = new TextView(activity);
//                textViewArray[counter].setPadding(0,0,0,12);
//                textViewArray[counter].setText(Html.fromHtml("<b>ID:</b> " + m.getId() + "<br><b>Nombre:</b> " + m.getFullName()));
//
//                lyContent.addView(textViewArray[counter]);

                checkBoxesDistributors[counter] = new CheckBox(activity);
                checkBoxesDistributors[counter].setPadding(0,0,0,12);
                checkBoxesDistributors[counter].setText(Html.fromHtml( m.getFullName()));
                checkBoxesDistributors[counter].setTag( m.getId());

                editTextsLines[counter] = new EditText(activity);
                editTextsLines[counter].setPadding(0,0,0,12);
                editTextsLines[counter].setLayoutParams((new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 180)));
                editTextsLines[counter].setHint("Plazo");
                editTextsLines[counter].setInputType(InputType.TYPE_CLASS_NUMBER);
                editTextsLines[counter].setEnabled(false);
                editTextsLines[counter].setVisibility(View.GONE);


                editTextsPlazos[counter] = new EditText(activity);
                editTextsPlazos[counter].setPadding(0,0,0,12);
                editTextsPlazos[counter].setLayoutParams((new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 180)));
                editTextsPlazos[counter].setEnabled(false);
                editTextsPlazos[counter].setInputType(InputType.TYPE_CLASS_NUMBER);
                editTextsPlazos[counter].setHint("Crédito");
                editTextsPlazos[counter].setVisibility(View.GONE);

                lyContent.addView(checkBoxesDistributors[counter]);
                lyContent.addView(editTextsLines[counter]);
                lyContent.addView(editTextsPlazos[counter]);
               // final int finalCounter = counter;
                final int finalCounter = counter;
                checkBoxesDistributors[counter].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (buttonView.isChecked()) {
                            editTextsPlazos[finalCounter].setEnabled(true);
                            editTextsPlazos[finalCounter].setVisibility(View.VISIBLE);
                            editTextsPlazos[finalCounter].setText("");



                            editTextsLines[finalCounter].setEnabled(true);
                            editTextsLines[finalCounter].setText("");
                            editTextsLines[finalCounter].setVisibility(View.VISIBLE);


                            //Toast.makeText(activity,String.valueOf(finalCounter),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            editTextsPlazos[finalCounter].setEnabled(false);
                            editTextsPlazos[finalCounter].setVisibility(View.GONE);

                            editTextsLines[finalCounter].setEnabled(false);
                            editTextsLines[finalCounter].setVisibility(View.GONE);

                           // Toast.makeText(activity,String.valueOf(finalCounter),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                counter ++;
            }

        } else {
            tvTotal.setText(getString(R.string.message_no_found));
        }

        showToolbar(getString(R.string.text_line_credit),false);


    }



    private class saveCredit extends AsyncTask<Void, String, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = true ;

            if( credits.size() > 0) {
                for(Credit m:credits) {
                    result =  AuditUtil.saveCreditDistributor(store_id, m.getUser_id(),m.getPlazo(),m.getLinea());
                    if(result == false ){
                        return false;
                    }
                }
            }

            return result;
        }

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
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Toast.makeText(activity , R.string.saveSuccess, Toast.LENGTH_LONG).show();
                finish();

            } else {
                Toast.makeText(activity , R.string.message_no_save_data, Toast.LENGTH_LONG).show();
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
}
