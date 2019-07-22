package com.dataservicios.ttauditbayertransferencista.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.ProductPlanSale;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.ProductPlanSaleRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductPlanFidelizationAdapterRecyclerView extends RecyclerView.Adapter<ProductPlanFidelizationAdapterRecyclerView.ProductPlanSaleViewHolder> {
    private ArrayList<ProductPlanSale> productPlanSales;
    private int                         resource;
    private Activity                    activity;
    private int                         store_id;
    private int                         audit_id;
    private Store                       store;
    private Product                     product ;
    private StoreRepo                   storeRepo;
    private ProductRepo                 productRepo ;
    private ProductPlanSaleRepo         productPlanSaleRepo;

    public ProductPlanFidelizationAdapterRecyclerView(ArrayList<ProductPlanSale> productPlanSales, int resource, Activity activity, int store_id, int audit_id) {
        this.productPlanSales   = productPlanSales;
        this.resource           = resource;
        this.activity           = activity;
        this.store_id           = store_id;
        this.audit_id           = audit_id;

        store               = new Store();
        product             = new Product();
        storeRepo           = new StoreRepo(activity);
        productRepo         = new ProductRepo(activity);
        productPlanSaleRepo = new ProductPlanSaleRepo(activity);
    }

    @Override
    public ProductPlanSaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
//
//        ProductPlanSaleViewHolder vh = new ProductPlanSaleViewHolder(view, new ProductPlanSaleViewHolder.ITextWatcher() {
//            @Override
//            public void beforeTextChanged(int position, CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(int position, CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(int position, Editable s) {
//
//            }
//        });
//
//        return vh ;
        return new ProductPlanFidelizationAdapterRecyclerView.ProductPlanSaleViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductPlanSaleViewHolder holder, int position) {

        final ProductPlanSale productPlanSale;
        productPlanSale = productPlanSales.get(position);


        ArrayList<Product> products = (ArrayList<Product>) productRepo.findAll();
        product = (Product) productRepo.findById(productPlanSale.getProduct_id());
        store   = (Store) storeRepo.findById(store_id);

        float cuota =  Float.valueOf(productPlanSale.getCuotames());
        float visitas = Float.valueOf(store.getVisit());
        float resul = cuota / visitas;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        float twoDigitsF = Float.valueOf(decimalFormat.format(resul)); // output is xxx.xx

        holder.tvProductName.setText(product.getFullname().toString());
        holder.tvCodigo.setText(String.valueOf(product.getCode().toString()));
//        holder.tvProm6m.setText(productPlanSale.getProm6m().toString());
        holder.tvCuotaMes.setText(productPlanSale.getCuotames().toString());
        holder.tvAvance.setText(productPlanSale.getAvance().toString());
        holder.tvSaldoVendedor.setText(String.valueOf(twoDigitsF));
        holder.tvStockMaximo.setText(String.valueOf(productPlanSale.getStock_max()));
        holder.tvStockMinimo.setText(String.valueOf(productPlanSale.getStock_min()));

        holder.etMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String min = s.toString().trim();
                ProductPlanSale objectProductPlanSale = (ProductPlanSale) productPlanSaleRepo.findByProductId(productPlanSale.getProduct_id());

                if(min.length() > 0){
                    objectProductPlanSale.setStock_min(Integer.valueOf(min));
                    productPlanSaleRepo.update(objectProductPlanSale);
                } else{
                    objectProductPlanSale.setStock_min(0);
                    productPlanSaleRepo.update(objectProductPlanSale);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        holder.etMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String max = s.toString().trim();
                ProductPlanSale objectProductPlanSale = (ProductPlanSale) productPlanSaleRepo.findByProductId(productPlanSale.getProduct_id());

                if(max.length() > 0){
                    objectProductPlanSale.setStock_max(Integer.valueOf(max));
                    productPlanSaleRepo.update(objectProductPlanSale);
                } else{
                    objectProductPlanSale.setStock_max(0);
                    productPlanSaleRepo.update(objectProductPlanSale);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return productPlanSales.size();
    }


    public void setFilter(ArrayList<ProductPlanSale> productPlanSales){
        this.productPlanSales = new ArrayList<>();
        this.productPlanSales.addAll(productPlanSales);
        notifyDataSetChanged();
    }
    public static class ProductPlanSaleViewHolder extends RecyclerView.ViewHolder {


        private TextView tvProductName;
        private TextView tvCodigo;
        private TextView tvCuotaMes ;
        private TextView tvAvance;
        private TextView tvSaldoVendedor;
        private TextView tvStockMaximo;
        private TextView tvStockMinimo;
        private EditText etMin;
        private EditText etMax;
//        private ITextWatcher mTextWatcher;


//        public interface ITextWatcher {
//            // you can add/remove methods as you please, maybe you dont need this much
//            void beforeTextChanged(int position, CharSequence s, int start, int count, int after);
//
//            void onTextChanged(int position, CharSequence s, int start, int before, int count);
//
//            void afterTextChanged(int position, Editable s);
//        }

//        public ProductPlanSaleViewHolder(View itemView, ITextWatcher textWatcher) {
        public ProductPlanSaleViewHolder(View itemView) {
            super(itemView);
            tvProductName       = (TextView) itemView.findViewById(R.id.tvProductName);
            tvCodigo            = (TextView) itemView.findViewById(R.id.tvCodigo);
            tvCuotaMes          = (TextView) itemView.findViewById(R.id.tvCuotaMes);
            tvAvance            = (TextView) itemView.findViewById(R.id.tvAvance);
            tvSaldoVendedor     = (TextView) itemView.findViewById(R.id.tvSaldoVendedor);
            tvStockMaximo       = (TextView) itemView.findViewById(R.id.tvStockMaximo);
            tvStockMinimo       = (TextView) itemView.findViewById(R.id.tvStockMinimo);
            etMin               = (EditText) itemView.findViewById(R.id.etMin);
            etMax               = (EditText) itemView.findViewById(R.id.etMax);
//
//            this.etMin.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    mTextWatcher.beforeTextChanged(getAdapterPosition(), s, start, count, after);
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    mTextWatcher.onTextChanged(getAdapterPosition(), s, start, before, count);
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    mTextWatcher.afterTextChanged(getAdapterPosition(), s);
//                }
//            });
//
//            this.etMax.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    mTextWatcher.beforeTextChanged(getAdapterPosition(), s, start, count, after);
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    mTextWatcher.onTextChanged(getAdapterPosition(), s, start, before, count);
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    mTextWatcher.afterTextChanged(getAdapterPosition(), s);
//                }
//            });
        }

    }
}
