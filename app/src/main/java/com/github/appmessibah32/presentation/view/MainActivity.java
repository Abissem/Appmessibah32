package com.github.appmessibah32.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.github.appmessibah32.Constants;
import com.github.appmessibah32.data.DragonApi;
import com.github.appmessibah32.R;
import com.github.appmessibah32.presentation.model.Dragonball;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static final String BASE_URL = "https://dragon-ball-api.herokuapp.com/";

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("application_esiea", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Dragonball> characterList = getDataFromCache();

        if(characterList != null){
            showList(characterList);
        } else {
            makeApiCall();
        }
    }

    private List<Dragonball> getDataFromCache() {
        String jsonCharacter = sharedPreferences.getString(Constants.KEY_CHARACTER_LIST, null);

        if(jsonCharacter == null){
            return null;
        } else {
            Type listType = new TypeToken<List<Dragonball>>(){}.getType();
            return gson.fromJson(jsonCharacter, listType);
        }
    }

    private void showList(List<Dragonball> characterList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(characterList);
        recyclerView.setAdapter(mAdapter);
    }

    private void makeApiCall(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DragonApi dragonApi = retrofit.create(DragonApi.class);

        Call<List<Dragonball>> call = dragonApi.getCharacterResponse();
        call.enqueue(new Callback<List<Dragonball>>() {
            @Override
            public void onResponse(Call<List<Dragonball>> call, Response<List<Dragonball>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Dragonball> characterList = response.body();
                    saveList(characterList);
                    showList(characterList);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<Dragonball>> call, Throwable t) {

                showError();
            }
        });
    }

    private void saveList(List<Dragonball> characterList) {
        String jsonString = gson.toJson(characterList);

        sharedPreferences
                .edit()
                .putString(Constants.KEY_CHARACTER_LIST, jsonString)
                .apply();
        Toast.makeText(this, "List Saved", Toast.LENGTH_SHORT).show();

    }

    private void showError() {

        Toast.makeText(this, "API Error", Toast.LENGTH_SHORT).show();
    }
}