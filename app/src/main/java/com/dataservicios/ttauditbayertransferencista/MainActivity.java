package com.dataservicios.ttauditbayertransferencista;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.ActivityPublicity;
import com.dataservicios.ttauditbayertransferencista.model.Audit;
import com.dataservicios.ttauditbayertransferencista.model.CategoryProduct;
import com.dataservicios.ttauditbayertransferencista.model.Company;
import com.dataservicios.ttauditbayertransferencista.model.Distributor;
import com.dataservicios.ttauditbayertransferencista.model.Laboratory;
import com.dataservicios.ttauditbayertransferencista.model.Poll;
import com.dataservicios.ttauditbayertransferencista.model.PollOption;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.ProductDetail;
import com.dataservicios.ttauditbayertransferencista.model.ProductDistributor;
import com.dataservicios.ttauditbayertransferencista.model.ProductPublicity;
import com.dataservicios.ttauditbayertransferencista.model.Publicity;
import com.dataservicios.ttauditbayertransferencista.model.StockProductPop;
import com.dataservicios.ttauditbayertransferencista.model.Visit;
import com.dataservicios.ttauditbayertransferencista.repo.ActivityPublicityRepo;
import com.dataservicios.ttauditbayertransferencista.repo.AuditRepo;
import com.dataservicios.ttauditbayertransferencista.repo.CategoryProductRepo;
import com.dataservicios.ttauditbayertransferencista.repo.CompanyRepo;
import com.dataservicios.ttauditbayertransferencista.repo.DistributorRepo;
import com.dataservicios.ttauditbayertransferencista.repo.LaboratoryRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PollOptionRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PollRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductDetailRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductDistributorRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductPublicityRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PublicityRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StockProductPopRepo;
import com.dataservicios.ttauditbayertransferencista.repo.VisitRepo;
import com.dataservicios.ttauditbayertransferencista.util.AuditUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Logcat tag
    private static final int    REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ProgressBar pbLoad;
    private TextView tvLoad, tv_Version ;
    private Activity activity;
    private String app_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = (Activity) this;
        pbLoad = (ProgressBar) findViewById(R.id.pbLoad);
        pbLoad.setIndeterminate(false);
        tvLoad = (TextView) findViewById(R.id.tvLoad);
        tv_Version = (TextView) findViewById(R.id.tvVersion);

        DatabaseManager.init(activity);
        PackageInfo pckInfo ;
        try {
            pckInfo= getPackageManager().getPackageInfo(getPackageName(),0);
            tv_Version.setText(pckInfo.versionName);
            app_id = pckInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(checkAndRequestPermissions()) loadLoginActivity();
    }

    private void loadLoginActivity() {


        new loadLogin().execute();


    }

    class loadLogin extends AsyncTask<Void, String, Boolean> {

        Company company = new Company();
        ArrayList<Audit> audits;
        ArrayList<Poll> polls;
        ArrayList<PollOption> pollOptions;
        @Override
        protected void onProgressUpdate(String... values) {
            //super.onProgressUpdate(values);
            tvLoad.setText(values[0].toString());
        }
        @Override
        protected void onPreExecute() {

            tvLoad.setText(getString(R.string.text_loading));
           // super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub

            publishProgress(activity.getString(R.string.text_download_init));
            publishProgress(activity.getString(R.string.text_download_companies));

            CompanyRepo companyRepo = new CompanyRepo(activity);
            company = AuditUtil.getCompany(1,1,app_id);
            if(company.getId() == 0) {
                return false;
            } else {
                if(companyRepo.countReg()>0) {
                    Company companyNew= (Company) companyRepo.findFirstReg();
                    if( company.getId() > companyNew.getId() ) {
                        companyRepo.deleteAll();
                        companyRepo.create(company);
                    }
                } else {
                    companyRepo.deleteAll();
                    companyRepo.create(company);
                }
            }


            if( company.getId() != 0 ) {



                publishProgress(activity.getString(R.string.text_download_visitis_company));
                ArrayList<Visit> visits = AuditUtil.getVisits(company.getId()) ;
                VisitRepo visitRepo = new VisitRepo(activity);
                visitRepo.deleteAll();
                for (Visit m: visits){
                    visitRepo.create(m);
                }

                publishProgress(activity.getString(R.string.text_download_audits));
                AuditRepo auditRepo= new AuditRepo(activity);
                ArrayList<Audit> audits = (ArrayList<Audit>) auditRepo.findAll();
                if(audits.size()==0){
                    auditRepo.deleteAll();
                    audits = AuditUtil.getAudits(company.getId());
                    if(audits.size()!=0){
                        for(Audit p: audits){
                            auditRepo.create(p);
                        }
                    } else  {
                        return  false;
                    }
                }


//
                publishProgress(activity.getString(R.string.text_download_polls));
                PollRepo pollRepo= new PollRepo(activity);
                pollRepo.deleteAll(); ///-----------------------SE AGREGÖ PARA LIMPIAR TEMPORALMENTE POR DEFECTO
                ArrayList<Poll> polls = (ArrayList<Poll>) pollRepo.findAll();
                if(polls.size()==0){
                    pollRepo.deleteAll();
                    polls = AuditUtil.getPolls(company.getId());
                    if(polls.size()!=0){
                        for(Poll p: polls){
                            pollRepo.create(p);
                        }
                    } else  {
                        return  false;
                    }
                }


                publishProgress(activity.getString(R.string.text_download_polls_otpions));
                PollOptionRepo pollOptionRepo= new PollOptionRepo(activity);
                ArrayList<PollOption> pollOptions = (ArrayList<PollOption>) pollOptionRepo.findAll();
                if(pollOptions.size()==0){
                    pollOptionRepo.deleteAll();
                    pollOptions = AuditUtil.getPollOptions(company.getId());
                    if(pollOptions.size()!=0){
                        for(PollOption p: pollOptions){
                            pollOptionRepo.create(p);
                        }
                    }
//                    else  {
//                        return  false;
//                    }
                }

                publishProgress(activity.getString(R.string.text_download_publicity));
                PublicityRepo publictyRepo= new PublicityRepo(activity);
                ArrayList<Publicity> publicities = (ArrayList<Publicity>) publictyRepo.findByCompanyId(company.getId());
                if(publicities.size()==0){
                    publictyRepo.deleteAll();
                    publicities = AuditUtil.getListPublicities(company.getId());
                    if(publicities.size()!=0){
                        for(Publicity p: publicities){
                            publictyRepo.create(p);
                        }
                    } else  {
                        return  false;
                    }
                }

//                publishProgress(activity.getString(R.string.text_download_products_detail));
//                ProductDetailRepo productDetailRepo = new ProductDetailRepo(activity);
//                ArrayList<ProductDetail> productDetails = (ArrayList<ProductDetail>) productDetailRepo.findByCompanyId(company.getId());
//
//                if(productDetails.size()==0){
//                    productDetailRepo.deleteAll();
//                    productDetails = AuditUtil.getListProducts(company.getId());
//                    if(productDetails.size()!=0){
//                        for(ProductDetail p: productDetails){
//                            productDetailRepo.create(p);
//                        }
//                    } else  {
//                        return  false;
//                    }
//                }

//                publishProgress(activity.getString(R.string.text_download_category_product));
//                CategoryProductRepo categoryProductRepo = new CategoryProductRepo(activity);
//                ArrayList<CategoryProduct> categoryProducts = (ArrayList<CategoryProduct>) categoryProductRepo.findByCompanyId(company.getId());
//
//                if(categoryProducts.size()==0){
//                    categoryProductRepo.deleteAll();
//                    categoryProducts = AuditUtil.getListCategoryProducts(company.getId());
//                    if(categoryProducts.size()!=0){
//                        for(CategoryProduct p: categoryProducts){
//                            categoryProductRepo.create(p);
//                        }
//                    } else  {
//                        return  false;
//                    }
//                }

                publishProgress(activity.getString(R.string.text_download_products_competity));
                ProductRepo productRepo = new ProductRepo(activity);
                ArrayList<Product> products = (ArrayList<Product>) productRepo.findByCompanyId(company.getId());

                if(products.size()==0){
                    productRepo.deleteAll();
                    products = AuditUtil.getListProductsCompetity(company.getId(),0);
                    if(products.size()!=0){
                        for(Product p: products){
                            productRepo.create(p);
                        }
                    } else  {
                        //return  false;
                    }
                }

                publishProgress(activity.getString(R.string.text_download_distributors));
                DistributorRepo distributorRepo = new DistributorRepo(activity);
                ArrayList<Distributor> distributors = (ArrayList<Distributor>) distributorRepo.findAll();

                if(distributors.size()==0){
                    productRepo.deleteAll();
                    distributors = AuditUtil.getListDistributors(company.getId());
                    if(distributors.size()!=0){
                        for(Distributor p: distributors){
                            distributorRepo.create(p);
                        }
                    } else  {
                        //return  false;
                    }
                }


                publishProgress(activity.getString(R.string.text_download_distributors_price));
                ProductDistributorRepo productDistributorRepo = new ProductDistributorRepo(activity);
                ArrayList<ProductDistributor> productDistributors = (ArrayList<ProductDistributor>) productDistributorRepo.findAll();

                if(productDistributors.size()==0){
                    productDistributorRepo.deleteAll();
                    productDistributors = AuditUtil.getListProductDistributors(company.getId(),0);
                    if(productDistributors.size()!=0){
                        for(ProductDistributor p: productDistributors){
                            productDistributorRepo.create(p);
                        }
                    } else  {
                        //return  false;
                    }
                }


                publishProgress(activity.getString(R.string.text_download_stock_product_pop));
                StockProductPopRepo stockProductPopRepo = new StockProductPopRepo(activity);
                ArrayList<StockProductPop> stockProductPops = (ArrayList<StockProductPop>) stockProductPopRepo.findAll();

                if(stockProductPops.size()==0){
                    stockProductPopRepo.deleteAll();
                    stockProductPops = AuditUtil.getListStockProductPop(company.getId());
                    if(stockProductPops.size()!=0){
                        for(StockProductPop m: stockProductPops){
                            stockProductPopRepo.create(m);
                        }
                    } else  {
                        return  false;
                    }
                }


                publishProgress(activity.getString(R.string.text_download_laboratories));
                LaboratoryRepo laboratoryRepo = new LaboratoryRepo(activity);
                ArrayList<Laboratory> laboratories = (ArrayList<Laboratory>) laboratoryRepo.findAll();

                if(laboratories.size()==0){
                    laboratoryRepo.deleteAll();
                    laboratories = AuditUtil.getListLaboratories(company.getId());
                    if(laboratories.size()!=0){
                        for(Laboratory p: laboratories){
                            laboratoryRepo.create(p);
                        }
                    } else  {
                        //return  false;
                    }
                }

                publishProgress(activity.getString(R.string.text_download_activity_competity));
                ActivityPublicityRepo activityPublicityRepo = new ActivityPublicityRepo(activity);
                ArrayList<ActivityPublicity> activityPublicities = (ArrayList<ActivityPublicity>) activityPublicityRepo.findAll();

                if(activityPublicities.size()==0){
                    activityPublicityRepo.deleteAll();
                    activityPublicities = AuditUtil.getListActivityPublicities(company.getId(),3);
                    if(activityPublicities.size()!=0){
                        for(ActivityPublicity p: activityPublicities){
                            activityPublicityRepo.create(p);
                        }
                    } else  {
                        //return  false;
                    }
                }


//------------ Creando registros solo para Vitaminas AASS --------------

                ProductPublicityRepo productPublicityRepo = new ProductPublicityRepo(activity);
                productPublicityRepo.deleteAll();

                ProductPublicity productPublicity = new ProductPublicity();
                productPublicity.setId(887);
                productPublicity.setCompany_id(company.getId());
                productPublicity.setPublicity_id(683);
                productPublicity.setFullname("Redoxon");
                productPublicity.setStatus(0);
                productPublicityRepo.create(productPublicity);


                productPublicity = new ProductPublicity();
                productPublicity.setId(888);
                productPublicity.setCompany_id(company.getId());
                productPublicity.setPublicity_id(683);
                productPublicity.setFullname("Supradyn Grageas");
                productPublicity.setStatus(0);
                productPublicityRepo.create(productPublicity);


                productPublicity = new ProductPublicity();
                productPublicity.setId(889);
                productPublicity.setCompany_id(company.getId());
                productPublicity.setPublicity_id(683);
                productPublicity.setFullname("Supradyn EEFF");
                productPublicity.setStatus(0);
                productPublicityRepo.create(productPublicity);


                productPublicity = new ProductPublicity();
                productPublicity.setId(890);
                productPublicity.setCompany_id(company.getId());
                productPublicity.setPublicity_id(683);
                productPublicity.setFullname("Berocca EEF");
                productPublicity.setStatus(0);
                productPublicityRepo.create(productPublicity);


                productPublicity = new ProductPublicity();
                productPublicity.setId(891);
                productPublicity.setCompany_id(company.getId());
                productPublicity.setPublicity_id(683);
                productPublicity.setFullname("Berocca Comprimidos");
                productPublicity.setStatus(0);
                productPublicityRepo.create(productPublicity);


                productPublicity = new ProductPublicity();
                productPublicity.setId(892);
                productPublicity.setCompany_id(company.getId());
                productPublicity.setPublicity_id(683);
                productPublicity.setFullname("Supradyn Capsulas");
                productPublicity.setStatus(0);
                productPublicityRepo.create(productPublicity);

//                ArrayList<PublicityHistory> publicityHistories1 = (ArrayList<PublicityHistory>) publicityHistoryRepo.findAll();
                ArrayList<Product> products1 = (ArrayList<Product>) productRepo.findByCompanyId(company.getId());

            }




            publishProgress(activity.getString(R.string.text_download_terminate));
            return true;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Boolean result) {
            // dismiss the dialog once product deleted

            if(result == true) {
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                String message  ;
                message = activity.getString(R.string.message_app_no_initialize) + "\n";

                if (company.getId() == 0) {
                    message = message.concat(activity.getString(R.string.message_no_get_company));
                    message = message.concat("\n");
                    //Toast.makeText(activity, R.string.message_no_get_company, Toast.LENGTH_LONG).show();
                    //activity.finish();

                    //-------------------------------------------
                    //Creandd Publicity de prueba
                    //-------------------------------------------

                }
                if (audits == null) {
                    message = message.concat(activity.getString(R.string.message_no_get_audit));
                    message = message.concat("\n");
                    //Toast.makeText(activity, R.string.message_no_get_audit, Toast.LENGTH_LONG).show();
                    //activity.finish();
                }
                alertDialogBasico(message);
            }
        }
    }

    //  Chequeando permisos de usuario Runtime
    private boolean checkAndRequestPermissions() {

        int locationPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int cameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int callPhonePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
        int readPhoneStatePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (callPhonePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }

        if (readPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity,listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean respuestas = false ;
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {


            if (grantResults.length > 0) {
                boolean permissionsApp = true ;
                for(int i=0; i < grantResults.length; i++) {
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //alertDialogBasico();
                        permissionsApp = false;
                        break;
                    }
                }
                if (permissionsApp==true)  loadLoginActivity();
                else alertDialogBasico(activity.getString(R.string.dialog_message_permission));
            }
        }
    }

    public void alertDialogBasico(String message) {

        // 1. Instancia de AlertDialog.Builder con este constructor
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        // 2. Encadenar varios métodos setter para ajustar las características del diálogo
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);
            }
        });
        builder.show();

    }


}
