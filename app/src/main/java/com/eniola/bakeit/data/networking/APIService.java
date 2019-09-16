package com.eniola.bakeit.data.networking;

import com.eniola.bakeit.data.models.RecipeModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<RecipeModel> getAllRecipes();
}
