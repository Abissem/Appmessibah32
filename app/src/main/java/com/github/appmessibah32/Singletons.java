package com.github.appmessibah32;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.appmessibah32.data.DragonApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static DragonApi dragonApiInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson(){
        if(gsonInstance == null){
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }

        return gsonInstance;
    }

    public static DragonApi getDragonApi(){
        if(dragonApiInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            dragonApiInstance = retrofit.create(DragonApi.class);

        }

        return dragonApiInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        if(sharedPreferencesInstance == null){
            sharedPreferencesInstance = context.getSharedPreferences("application_esiea", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
