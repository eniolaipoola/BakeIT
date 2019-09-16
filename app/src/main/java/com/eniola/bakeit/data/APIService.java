package com.eniola.bakeit.data;

import com.eniola.bakeit.models.RecipeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<RecipeModel>> getAllRecipes();
}
