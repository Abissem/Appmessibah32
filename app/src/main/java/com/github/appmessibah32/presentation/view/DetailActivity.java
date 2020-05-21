package com.github.appmessibah32.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.appmessibah32.Constants;
import com.github.appmessibah32.R;
import com.github.appmessibah32.Singletons;
import com.github.appmessibah32.presentation.controller.MainController;
import com.github.appmessibah32.presentation.model.Dragonball;

public class DetailActivity extends AppCompatActivity  {

    private TextView txtSpecies;
    private TextView txtStatus;
    private TextView txtSeries;
    private TextView txtName;
    private ImageView Image;
    private ImageButton returnButton;
    private ConstraintLayout DetailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DetailLayout = findViewById(R.id.DetailLayout);
        txtName = findViewById(R.id.firstLine);
        txtSpecies = findViewById(R.id.secondLine);
        txtStatus = findViewById(R.id.thirdLine);
        txtSeries = findViewById(R.id.forthLine);

        Image = findViewById(R.id.icon);
        returnButton = findViewById(R.id.returnButton);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                finish();
            }
        });

        //txtName = findViewById(R.id.secondLine);
        Intent intent = getIntent();
        String dragonballJson = intent.getStringExtra("dragonballKey");
        Dragonball dragonball = Singletons.getGson().fromJson(dragonballJson, Dragonball.class);
        showDetail(dragonball);
    }

    private void showDetail(Dragonball dragonball) {

        txtName.setText(dragonball.getName());
        txtSpecies.setText(dragonball.getSpecies());
        txtStatus.setText(dragonball.getStatus());
        txtSeries.setText("Series : " + dragonball.getSeries());

        if(dragonball.getImage().charAt(0) == '.') {
            Glide.with(this).applyDefaultRequestOptions(
                    new RequestOptions()
                            .placeholder(R.drawable.ic_refresh_green_100dp)
                            .error(R.drawable.ic_healing_red_100dp))
                            .load(Constants.BASE_URL + dragonball.getImage())
                            .into(Image);


        }else Glide.with(this).applyDefaultRequestOptions(
                new RequestOptions()
                            .placeholder(R.drawable.ic_refresh_green_100dp)
                            .error(R.drawable.ic_healing_red_100dp))
                            .load(dragonball.getImage())
                            .into(Image);
    }
}