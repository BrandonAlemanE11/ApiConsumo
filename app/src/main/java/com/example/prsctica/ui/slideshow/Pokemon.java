package com.example.prsctica.ui.slideshow;

import com.example.prsctica.DTO.APokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Pokemon {
    @GET("{id}")
    Call<APokemon> find(
            @Path("id") String id
    );
}
