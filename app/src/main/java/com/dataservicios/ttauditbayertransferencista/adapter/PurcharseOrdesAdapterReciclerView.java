package com.dataservicios.ttauditbayertransferencista.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.model.Product;
import com.dataservicios.ttauditbayertransferencista.model.ProductDistributor;
import com.dataservicios.ttauditbayertransferencista.model.PurcharseOrder;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.ProductDistributorRepo;
import com.dataservicios.ttauditbayertransferencista.repo.ProductRepo;
import com.dataservicios.ttauditbayertransferencista.repo.PurcharseOrderRepo;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;

import java.util.ArrayList;

public class PurcharseOrdesAdapterReciclerView extends RecyclerView.Adapter<PurcharseOrdesAdapterReciclerView.PurcharseOrdesViewHolder> {
    private ArrayList<PurcharseOrder>   purcharseOrders;
    private int                         resource;
    private Activity                    activity;
    private int                         store_id;
    private int                         audit_id;

    public PurcharseOrdesAdapterReciclerView(ArrayList<PurcharseOrder> purcharseOrdes, int resource, Activity activity, int store_id, int audit_id) {
        this.purcharseOrders    = purcharseOrdes;
        this.resource           = resource;
        this.activity           = activity;
        this.store_id           = store_id;
        this.audit_id           = audit_id;

    }

    @Override
    public PurcharseOrdesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PurcharseOrdesViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(PurcharseOrdesViewHolder holder, final int position) {
        final PurcharseOrder purcharseOrder = purcharseOrders.get(position);
        StoreRepo                       storeRepo               = new StoreRepo(activity);
        final ProductRepo                     productRepo             = new ProductRepo(activity);
        ProductDistributorRepo          productDistributorRepo  = new ProductDistributorRepo(activity);
        final PurcharseOrderRepo        purcharseOrderRepo      = new PurcharseOrderRepo(activity);


        final Store store                               = (Store) storeRepo.findById(store_id);
        final Product product                           = (Product) productRepo.findById(purcharseOrder.getProduct_id());
        ArrayList<ProductDistributor> productDistributors = (ArrayList<ProductDistributor>) productDistributorRepo.findAll();
        final ProductDistributor productDistributor     = (ProductDistributor)  productDistributorRepo.findByProvider(purcharseOrder.getProvider_id());

        holder.tvProducto.setText(product.getFullname());
        holder.tvDistribuidor.setText(productDistributor.getFullname());
        holder.tvCantidad.setText(String.valueOf(purcharseOrder.getQuantity()));
        holder.tvPrecio.setText(String.valueOf(purcharseOrder.getPrice()));

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.message_save);
                builder.setMessage(R.string.message_delete_information_product);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        product.setStatus(0);
                        productRepo.update(product);

                        purcharseOrderRepo.delete(purcharseOrder) ;
                        //  -----------------------------------------------------------
                        //  Eliminado elementos de un recicler view
                        //  -----------------------------------------------------------
                        purcharseOrders.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,purcharseOrders.size());
                        //  ----------------------------------
                        //  end elimination reclicler view
                        //  ----------------------------------------------------------
                        activity.finish();
                        activity.startActivity(activity.getIntent());
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



    @Override
    public int getItemCount() {
        return purcharseOrders.size();
    }

    public class PurcharseOrdesViewHolder extends RecyclerView.ViewHolder {

        private TextView    tvProducto;
        private TextView    tvDistribuidor;
        private TextView    tvCantidad;
        private TextView    tvPrecio;
        private Button      btDelete;


        public PurcharseOrdesViewHolder(View itemView) {
            super(itemView);
            tvProducto        = (TextView) itemView.findViewById(R.id.tvProducto);
            tvCantidad        = (TextView) itemView.findViewById(R.id.tvCantidad);
            tvDistribuidor    = (TextView) itemView.findViewById(R.id.tvDistribuidor);
            tvPrecio          = (TextView) itemView.findViewById(R.id.tvPrecio);

        }
    }

    public void setFilter(ArrayList<PurcharseOrder> purcharseOrders){
        this.purcharseOrders = new ArrayList<>();
        this.purcharseOrders.addAll(purcharseOrders);
        notifyDataSetChanged();
    }
}
