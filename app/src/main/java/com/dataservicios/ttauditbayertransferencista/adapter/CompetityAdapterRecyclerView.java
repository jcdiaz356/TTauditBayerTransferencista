package com.dataservicios.ttauditbayertransferencista.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.model.ActivityPublicity;
import com.dataservicios.ttauditbayertransferencista.model.Poll;
import com.dataservicios.ttauditbayertransferencista.model.Publicity;
import com.dataservicios.ttauditbayertransferencista.model.Store;
import com.dataservicios.ttauditbayertransferencista.repo.StoreRepo;
import com.dataservicios.ttauditbayertransferencista.view.PollPublicityActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CompetityAdapterRecyclerView extends RecyclerView.Adapter<CompetityAdapterRecyclerView.CompetityViewHolder> {

    private ArrayList<ActivityPublicity>    activityPublicities;
    private int                             resource;
    private Activity                        activity;
    private int                             store_id;
    private int                             audit_id;

    public CompetityAdapterRecyclerView(ArrayList<ActivityPublicity> activityPublicities, int resource, Activity activity, int store_id, int audit_id) {
        this.activityPublicities    = activityPublicities;
        this.resource               = resource;
        this.activity               = activity;
        this.store_id               = store_id;
        this.audit_id               = audit_id;
    }
    @NonNull
    @Override
    public CompetityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new CompetityAdapterRecyclerView.CompetityViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull CompetityViewHolder holder, int position) {

        final ActivityPublicity activityPublicity = activityPublicities.get(position);


        holder.tvFullName.setText(activityPublicity.getFullname());
        //holder.tvDescription.setText(publicity.getDescription());

        if(activityPublicity.getStatus() == 0){
            holder.imgStatus.setVisibility(View.INVISIBLE);
            holder.btAudit.setVisibility(View.VISIBLE);
        } else {
            holder.imgStatus.setVisibility(View.VISIBLE);
            holder.btAudit.setVisibility(View.INVISIBLE);
        }
        holder.btAudit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoreRepo storeRepo = new StoreRepo(activity);
                Store store = (Store) storeRepo.findById(store_id);
                if(store.getStatus_change() == 0) {

//                    Poll poll = new Poll();
//                    poll.setPublicity_id(publicity.getId());
//                    poll.setOrder(23);
//                    PollPublicityActivity.createInstance((Activity) activity, store_id,audit_id,poll);
                    Toast.makeText(activity,String.valueOf(activityPublicity.getId()),Toast.LENGTH_LONG);
                } else  if( store.getStatus_change() == 1) {
//                    Poll poll = new Poll();
//                    poll.setPublicity_id(publicity.getId());
//                    poll.setOrder(212);
//                    PollPublicityActivity.createInstance((Activity) activity, store_id,audit_id,poll);
                    Toast.makeText(activity,String.valueOf(activityPublicity.getId()),Toast.LENGTH_LONG);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CompetityViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFullName;
        //private TextView    tvDescription;
        private Button btAudit;
        private ImageView imgStatus;

        public CompetityViewHolder(View itemView) {
            super(itemView);
            tvFullName      = (TextView) itemView.findViewById(R.id.tvFullName);
            // tvDescription   = (TextView) itemView.findViewById(R.id.tvDescription);
            btAudit         = (Button)  itemView.findViewById(R.id.btAudit);
            imgStatus       = (ImageView)  itemView.findViewById(R.id.imgStatus);
        }
    }
}
