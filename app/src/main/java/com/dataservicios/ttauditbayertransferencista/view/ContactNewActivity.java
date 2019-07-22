package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;

import java.util.Calendar;

public class ContactNewActivity extends AppCompatActivity {
    private static final String LOG_TAG = ContactNewActivity.class.getSimpleName();
    private Activity activity =  this;
    private ProgressDialog pDialog;
    private int store_id;


    private static final String CERO = "0";
    private static final String BARRA = "-";
    private String fullname = "" , cargo = "", phono = "", celular = "", email = "", newDate = "" ;

    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    private Button btSave;
    private EditText etDate;
    private EditText etFullname;
    private EditText etCargo;
    private EditText etPhono;
    private EditText etCelular;
    private EditText etEmail;
    private ImageButton ibGetDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_new);

        Bundle bundle = getIntent().getExtras();
        store_id = bundle.getInt("store_id");

        etDate          = (EditText) findViewById(R.id.etDate);
        etFullname      = (EditText) findViewById(R.id.etFullname);
        etCargo         = (EditText) findViewById(R.id.etCargo);
        etPhono         = (EditText) findViewById(R.id.etPhono);
        etCelular       = (EditText) findViewById(R.id.etCelular);
        etEmail         = (EditText) findViewById(R.id.etEmail);
        ibGetDate       = (ImageButton) findViewById(R.id.ibGetDate);
        btSave          = (Button) findViewById(R.id.btSave);

        ibGetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });


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

                        if (etFullname.getText().toString().trim().equals("")  ) {
                            Toast.makeText(activity, R.string.message_requiere_nombre , Toast.LENGTH_SHORT).show();
                            return;
                        } else if( etDate.getText().toString().trim().equals("")  ) {
                            Toast.makeText(activity, R.string.message_requiere_date , Toast.LENGTH_SHORT).show();
                            return;
                        }  else {

                            newDate =   etDate.getText().toString();
                            fullname =   etFullname.getText().toString();
                            cargo =   etCargo.getText().toString();
                            phono =   etPhono.getText().toString();
                            celular =   etCelular.getText().toString();
                            email =   etEmail.getText().toString();
                            new saveContact().execute();
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

        showToolbar(getString(R.string.text_contcts),false);
    }

    /**
     * Obteniendo apartir del Widget la fecha
     */
    private void getDate(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
//                etDate.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
//                newDate = diaFormateado + BARRA + mesFormateado + BARRA + year ;
                etDate.setText(year + BARRA +   mesFormateado + BARRA + diaFormateado);
                newDate = year + BARRA +   mesFormateado + BARRA + diaFormateado ;

            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    private class saveContact extends AsyncTask<Void, String, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result;
            result = AuditUtil.saveContactStore(store_id,fullname,cargo,0,phono,celular,email,newDate);
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
