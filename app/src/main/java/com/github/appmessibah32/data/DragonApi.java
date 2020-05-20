package com.github.appmessibah32.data;

import com.github.appmessibah32.presentation.model.Dragonball;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DragonApi {
    @GET("/api/character")
    Call<List<Dragonball>> getCharacterResponse();
}
