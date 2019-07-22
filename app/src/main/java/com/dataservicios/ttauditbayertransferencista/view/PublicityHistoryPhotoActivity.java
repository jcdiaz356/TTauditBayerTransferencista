package com.dataservicios.ttauditbayertransferencista.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.dataservicios.ttauditbayertransferencista.R;
import com.dataservicios.ttauditbayertransferencista.db.DatabaseManager;
import com.dataservicios.ttauditbayertransferencista.model.PublicityHistory;
import com.dataservicios.ttauditbayertransferencista.repo.PublicityHistoryRepo;
import com.dataservicios.ttauditbayertransferencista.util.GlobalConstant;
import com.squareup.picasso.Picasso;

public class PublicityHistoryPhotoActivity extends AppCompatActivity {
    private static final String LOG_TAG = PublicityHistoryPhotoActivity.class.getSimpleName();
    private Activity activity;
    private ImageView imgPhoto;
    private int             publicity_history_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicity_history_photo);

        activity = (Activity) this;
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);

        DatabaseManager.init(activity);

        Bundle bundle = getIntent().getExtras();
        publicity_history_id = bundle.getInt("publicity_history_id");

        PublicityHistoryRepo publicityHistoryRepo = new PublicityHistoryRepo(activity);
        PublicityHistory publicityHistory;
        publicityHistory = (PublicityHistory) publicityHistoryRepo.findById(publicity_history_id);

//        Picasso.with(activity)
//                .load(publicityHistory.getImagen())
//                .placeholder(R.drawable.loading_image)
//                .error(R.drawable.thumbs_ttaudit)
//                .into(imgPhoto);
        Picasso.get()
                .load(publicityHistory.getImagen())
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.thumbs_ttaudit)
                .into(imgPhoto);

    }
}
