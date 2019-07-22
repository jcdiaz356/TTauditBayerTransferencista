package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.model.Company;
import com.dataservicios.ttauditbayertransferencista.repo.CompanyRepo;
import com.dataservicios.ttauditbayertransferencista.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchOrdersActivity extends AppCompatActivity {
    private static final String LOG_TAG = SearchOrdersActivity.class.getSimpleName();
    private SessionManager  session;
    private Activity        activity =  this;
    private int             user_id;
    private int             company_id;
    private CompanyRepo     companyRepo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_orders);

        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        companyRepo             = new CompanyRepo(activity);

        showToolbar(getString(R.string.text_search_orders),false);

        final ArrayList<Company> companies = (ArrayList<Company>) companyRepo.findAll();
        for (Company c: companies){
            company_id = c.getId();
        }


        WebView wvOrdersSearch = (WebView) this.findViewById(R.id.wvOrdersSearch);
//        wvOrdersSearch.loadUrl("http://desarrollolibre.net/blog/tema/150/javascript/como-hacer-una-sencilla-galeria-con-css-y-6-lineas-de-javascript");
        WebSettings webSettings = wvOrdersSearch.getSettings();
        wvOrdersSearch.getSettings().setJavaScriptEnabled(true); //Habilitar Javascript
        String url ="http://ttaudit.com/searchOrders/" + company_id + "/" + user_id;
        wvOrdersSearch.loadUrl(url);
        wvOrdersSearch.setWebViewClient(new WebViewClient());
    }


    private void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

}
