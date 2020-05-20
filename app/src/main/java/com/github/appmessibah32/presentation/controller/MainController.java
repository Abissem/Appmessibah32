package com.github.appmessibah32.presentation.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.github.appmessibah32.Constants;
import com.github.appmessibah32.Singletons;
import com.github.appmessibah32.presentation.model.Dragonball;
import com.github.appmessibah32.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {

    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences, Context context) {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
        this.context = context;
    }

    public void onStart(){
        List<Dragonball> characterList = getDataFromCache();

        if(characterList != null){
            view.showList(characterList);
        } else {
            makeApiCall();
        }
    }

    private void makeApiCall(){
        Call<List<Dragonball>> call = Singletons.getDragonApi().getCharacterResponse();
        call.enqueue(new Callback<List<Dragonball>>() {
            @Override
            public void onResponse(Call<List<Dragonball>> call, Response<List<Dragonball>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Dragonball> characterList = response.body();
                    saveList(characterList);
                    view.showList(characterList);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<List<Dragonball>> call, Throwable t) {

                view.showError();
            }
        });
    }

    private void saveList(List<Dragonball> characterList) {
        String jsonString = gson.toJson(characterList);

        sharedPreferences
                .edit()
                .putString(Constants.KEY_CHARACTER_LIST, jsonString)
                .apply();
        Toast.makeText(context, "List Saved", Toast.LENGTH_SHORT).show();
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

    public void onItemClick(Dragonball dragonball){
        view.navigateToDetails(dragonball);
    }

    public void onButtonAClick(){

    }

    public void onButtonBClick(){

    }


}
