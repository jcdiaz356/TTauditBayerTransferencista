package com.dataservicios.ttauditbayertransferencista.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.model.Distributor;
import com.dataservicios.ttauditbayertransferencista.model.OrderDetailTemp;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.ProductDistributor;
import com.dataservicios.ttauditbayertransferencista.repo.DistributorRepo;
import com.dataservicios.ttauditbayertransferencista.repo.OrderDetailTempRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductDistributorRepo;


import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by jcdia on 7/06/2017.
 */

public class ProductAdapterRecyclerView extends RecyclerView.Adapter<ProductAdapterRecyclerView.ProductViewHolder> {
    private ArrayList<Product>          products;
    private int                         resource;
    private Activity                    activity;
    private int                         store_id;
    private int                         audit_id;
    private int                         distributor_id;
    private Product                     product;
    private Distributor                 distributor;
    private ProductDistributor          productDistributor;
    private OrderDetailTemp             orderDetailTemp;
    private DistributorRepo             distributorRepo;
    private ProductDistributorRepo      productDistributorRepo;
    private OrderDetailTempRepo         orderDetailTempRepo;
    private OnEditTextChanged           onEditTextChanged;

    public ProductAdapterRecyclerView(ArrayList<Product> products, int resource, Activity activity, int store_id, int audit_id, int distributor_id,OnEditTextChanged onEditTextChanged) {
        this.products = products;
        this.resource           = resource;
        this.activity           = activity;
        this.store_id           = store_id;
        this.audit_id           = audit_id;
        this.distributor_id     = distributor_id;

        distributorRepo         = new DistributorRepo(activity);
        productDistributorRepo  = new ProductDistributorRepo(activity);
        orderDetailTempRepo     = new OrderDetailTempRepo(activity);

        this.onEditTextChanged = onEditTextChanged;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        final TextView tvPrecio         = (TextView) view.findViewById(R.id.tvPrecio);
        final TextView tvTotal          = (TextView) view.findViewById(R.id.tvTotal);
        ProductViewHolder vh = new ProductViewHolder(view, new ProductViewHolder.ITextWatcher() {

            @Override
            public void beforeTextChanged(int position, CharSequence s, int start, int count, int after) {
                // do something
            }

            @Override
            public void onTextChanged(int position, CharSequence s, int start, int before, int count) {


                String cantidad = s.toString().trim();
                if(cantidad.length() > 0){
                    product             = products.get(position);
                    productDistributor  = (ProductDistributor) productDistributorRepo.findByPriceInRank(product.getId(),distributor_id,Integer.valueOf(cantidad));
                    orderDetailTemp     = (OrderDetailTemp) orderDetailTempRepo.findByProductId(product.getId());

                    float price = 0,resul;

                    if(productDistributor != null) {
                        tvPrecio.setText("S/." + productDistributor.getPrice().toString());
                        tvPrecio.setTag(productDistributor.getPrice().toString());
                        price =  Float.valueOf(productDistributor.getPrice());
                    } else{
                        tvPrecio.setText("0");
                        price =  0;
                    }

                    resul = price * Float.valueOf(cantidad);

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    float twoDigitsF = Float.valueOf(decimalFormat.format(resul)); // output is xxx.xx

                    tvTotal.setText("S/." + String.valueOf(twoDigitsF));
                    tvTotal.setTag(String.valueOf(twoDigitsF));

                    orderDetailTemp.setQuantity(Integer.valueOf(cantidad));
                    orderDetailTemp.setTotal(twoDigitsF);
                    orderDetailTemp.setPrice(price);
                    orderDetailTempRepo.update(orderDetailTemp);


                    ArrayList<OrderDetailTemp> orderDetailTemps = (ArrayList<OrderDetailTemp>) orderDetailTempRepo.findAll();
                    float mountTotal=0;
                    for(OrderDetailTemp m: orderDetailTemps){
                        if(m.getQuantity() > 0){
                            mountTotal += m.getTotal();
                        }
                    }

                    twoDigitsF = Float.valueOf(decimalFormat.format(mountTotal));
                    onEditTextChanged.onTextChanged(position, String.valueOf(twoDigitsF));
                }
            }

            @Override
            public void afterTextChanged(int position, Editable s) {
                // do something
            }
        });

        return vh ;
    }

    @Override
    public void onBindViewHolder( ProductViewHolder holder, int position) {
        product = products.get(position);

        distributor = (Distributor) distributorRepo.findById(distributor_id);


        holder.tvProduct.setText(product.getFullname());
        holder.tvCodigo.setText(product.getCode());
        holder.tvDitributor.setText(distributor.getFullName());
//        holder.tvCodigo.setText(product.getUnidad());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProduct;
        private TextView tvCodigo;
        private TextView tvDitributor;
        private TextView tvPrecio;
        private TextView tvTotal;
        private EditText etCantidad;
        private ITextWatcher mTextWatcher;

        public interface ITextWatcher {
            // you can add/remove methods as you please, maybe you dont need this much
            void beforeTextChanged(int position, CharSequence s, int start, int count, int after);

            void onTextChanged(int position, CharSequence s, int start, int before, int count);

            void afterTextChanged(int position, Editable s);
        }

        public ProductViewHolder(View itemView, ITextWatcher textWatcher) {
            super(itemView);
            tvProduct       = (TextView) itemView.findViewById(R.id.tvProduct);
            tvDitributor    = (TextView) itemView.findViewById(R.id.tvDitributor);
            tvCodigo        = (TextView) itemView.findViewById(R.id.tvCodigo);
            tvPrecio        = (TextView)  itemView.findViewById(R.id.tvPrecio);
            etCantidad      = (EditText)  itemView.findViewById(R.id.etCantidad);
            mTextWatcher    = textWatcher;

            this.etCantidad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    mTextWatcher.beforeTextChanged(getAdapterPosition(), s, start, count, after);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mTextWatcher.onTextChanged(getAdapterPosition(), s, start, before, count);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    mTextWatcher.afterTextChanged(getAdapterPosition(), s);
                }
            });

        }
    }


    public void setFilter(ArrayList<Product> products){
        this.products = new ArrayList<>();
        this.products.addAll(products);
        notifyDataSetChanged();
    }


}
