package com.dataservicios.ttauditbayertransferencista.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.ProductPlanSale;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.ProductRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductPlanSaleAdapterRecyclerView extends RecyclerView.Adapter<ProductPlanSaleAdapterRecyclerView.ProductPlanSaleViewHolder> {
    private ArrayList<ProductPlanSale>  productPlanSales;
    private int                         resource;
    private Activity                    activity;
    private int                         store_id;
    private int                         audit_id;

    public ProductPlanSaleAdapterRecyclerView(ArrayList<ProductPlanSale> productPlanSales, int resource, Activity activity, int store_id, int audit_id) {
        this.productPlanSales   = productPlanSales;
        this.resource           = resource;
        this.activity           = activity;
        this.store_id           = store_id;
        this.audit_id           = audit_id;
    }

    @Override
    public ProductPlanSaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ProductPlanSaleViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductPlanSaleViewHolder holder, int position) {

        ProductPlanSale productPlanSale;

        Store           store;
        Product         product ;
        StoreRepo       storeRepo;
        ProductRepo     productRepo ;

        productPlanSale = productPlanSales.get(position);
        store           = new Store();
        product         = new Product();

        storeRepo   = new StoreRepo(activity);
        productRepo = new ProductRepo(activity);

        product = (Product) productRepo.findById(productPlanSale.getProduct_id());
        store   = (Store) storeRepo.findById(store_id);

        float cuota =  Float.valueOf(productPlanSale.getCuotames());
        float visitas = Float.valueOf(store.getVisit());
        float resul = cuota / visitas;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        float twoDigitsF = Float.valueOf(decimalFormat.format(resul)); // output is xxx.xx

        holder.tvProductName.setText(product.getFullname().toString());
        holder.tvCodigo.setText(String.valueOf(product.getCode().toString()));
        holder.tvProm6m.setText(productPlanSale.getProm6m().toString());
        holder.tvCuotaMes.setText(productPlanSale.getCuotames().toString());
        holder.tvAvance.setText(productPlanSale.getAvance().toString());
        holder.tvPedidoSugerido.setText(String.valueOf(twoDigitsF));
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
    public class ProductPlanSaleViewHolder extends RecyclerView.ViewHolder {


        private TextView tvProductName;
        private TextView tvCodigo;
        private TextView tvProm6m;
        private TextView tvCuotaMes ;
        private TextView tvAvance;
        private TextView tvPedidoSugerido;


        public ProductPlanSaleViewHolder(View itemView) {
            super(itemView);
            tvProductName       = (TextView) itemView.findViewById(R.id.tvProductName);
            tvCodigo            = (TextView) itemView.findViewById(R.id.tvCodigo);
            tvProm6m            = (TextView) itemView.findViewById(R.id.tvProm6m);
            tvCuotaMes          = (TextView) itemView.findViewById(R.id.tvCuotaMes);
            tvAvance            = (TextView) itemView.findViewById(R.id.tvAvance);
            tvPedidoSugerido    = (TextView) itemView.findViewById(R.id.tvPedidoSugerido);
        }
    }
}
