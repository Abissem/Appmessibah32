package com.github.appmessibah32.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.appmessibah32.R;
import com.github.appmessibah32.Singletons;
import com.github.appmessibah32.presentation.model.Dragonball;

public class DetailActivity extends AppCompatActivity {

    private TextView txtDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtDetail = findViewById(R.id.firstLine);
        Intent intent = getIntent();
        String dragonballJson = intent.getStringExtra("dragonballKey");
        Dragonball dragonball = Singletons.getGson().fromJson(dragonballJson, Dragonball.class);
        showDetail(dragonball);
    }

    private void showDetail(Dragonball dragonball) {
        txtDetail.setText(dragonball.getSpecies());
    }
}