package com.dataservicios.ttauditbayertransferencista.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.model.Poll;
import com.dataservicios.ttauditbayertransferencista.model.ProductPublicity;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;
import com.dataservicios.ttauditbayertransferencista.view.PollPublicityActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jcdia on 10/12/2017.
 */

public class ProductAASSAdapterRecyclerView extends RecyclerView.Adapter<ProductAASSAdapterRecyclerView.ProductViewHolder> {
    private ArrayList<ProductPublicity> products;
    private int                         resource;
    private Activity activity;
    private int                         store_id;
    private int                         audit_id;
    private int                         publicity_id;

    public ProductAASSAdapterRecyclerView(ArrayList<ProductPublicity> products, int publicity_id, int resource, Activity activity, int store_id, int audit_id) {
        this.products       = products;
        this.resource       = resource;
        this.activity       = activity;
        this.store_id       = store_id;
        this.audit_id       = audit_id;
        this.publicity_id   = publicity_id;

    }

    @Override
    public ProductAASSAdapterRecyclerView.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new ProductAASSAdapterRecyclerView.ProductViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(ProductAASSAdapterRecyclerView.ProductViewHolder holder, int position) {
        final ProductPublicity product = products.get(position);

        holder.tvFullName.setText(product.getFullname());
        holder.tvComposicion.setText(product.getComposicion());
        holder.tvUnidad.setText(product.getUnidad());
//        Picasso.with(activity)
//                .load(product.getImagen())
//                .placeholder(R.drawable.loading_image)
//                .error(R.drawable.thumbs_ttaudit)
//                .into(holder.imgPhoto);
        Picasso.get()
                .load(product.getImagen())
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.thumbs_ttaudit)
                .into(holder.imgPhoto);

        if(product.getStatus() == 0){
            holder.imgStatus.setVisibility(View.INVISIBLE);
            holder.btAudit.setVisibility(View.VISIBLE);
        } else {
            holder.imgStatus.setVisibility(View.VISIBLE);
            holder.btAudit.setVisibility(View.INVISIBLE);
        }
        holder.btAudit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                PublicityRepo publicityRepo = new PublicityRepo(activity);
//                ArrayList<Publicity> publicities = (ArrayList<Publicity>) publicityRepo.findAll();
//
//                for (Publicity p: publicities){
//                    p.setStatus(0);
//                    publicityRepo.update(p);
//                }
//
//
//                Bundle bundle = new Bundle();
//                bundle.putInt("store_id",store_id);
//                bundle.putInt("audit_id",audit_id);
//                bundle.putInt("product_id",product.getId());
//                bundle.putInt("publicity_id",publicity_id);
//
//                Intent intent = new Intent(activity, ProductPublicityCompetityActivity.class);
//                intent.putExtras(bundle);
//                activity.startActivity(intent);

                StoreRepo storeRepo = new StoreRepo(activity);
                Store store = (Store) storeRepo.findById(store_id);
//                if(store.getStatus_change() == 0) {
                    Poll poll = new Poll();
                    poll.setPublicity_id(publicity_id);
                    poll.setProduct_id(product.getId());
                    poll.setOrder(3);
                    PollPublicityActivity.createInstance((Activity) activity, store_id,audit_id,poll);
//                } else  if( store.getStatus_change() == 1) {
//                    Poll poll = new Poll();
//                    poll.setPublicity_id(publicity.getId());
//                    poll.setOrder(12);
//                    PollPublicityActivity.createInstance((Activity) activity, store_id,audit_id,poll);
//                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFullName;
        private TextView tvUnidad;
        private TextView tvComposicion;
        private Button btAudit;
        private ImageView imgPhoto;
        private ImageView imgStatus;

        public ProductViewHolder(View itemView) {
            super(itemView);
            tvFullName      = (TextView) itemView.findViewById(R.id.tvFullName);
            tvComposicion   = (TextView) itemView.findViewById(R.id.tvComposicion);
            tvUnidad        = (TextView) itemView.findViewById(R.id.tvUnidad);
            btAudit         = (Button)  itemView.findViewById(R.id.btAudit);
            imgPhoto        = (ImageView)  itemView.findViewById(R.id.imgPhoto);
            imgStatus       = (ImageView)  itemView.findViewById(R.id.imgStatus);
        }
    }

    public void setFilter(ArrayList<ProductPublicity> products){
        this.products = new ArrayList<>();
        this.products.addAll(products);
        notifyDataSetChanged();
    }
}
