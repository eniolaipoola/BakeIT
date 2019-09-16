package com.eniola.bakeit.data.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.eniola.bakeit.data.models.RecipeModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeData implements RecipeDataInterface{

    private APIService apiService;
    private APIClient apiClient;

    public RecipeData(APIService apiService){
        this.apiService = apiService;
        this.apiClient = new APIClient();
    }

    @Override
    public void getRecipes(final OnRecipeFetchedListener recipeFetchedListener) {
        apiService.getAllRecipes().enqueue(new Callback<RecipeModel>() {
            @Override
            public void onResponse(@NonNull Call<RecipeModel> call, @NonNull Response<RecipeModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        RecipeModel recipeModel = response.body();
                        Log.d("debug", "each recipe is" + recipeModel.getName());
                        Log.d("debug", "each recipe is" + recipeModel.getImage());
                        recipeFetchedListener.onRecipeSuccessful(recipeModel);

                    } else {
                        Log.d("debug", "recipe body is null" + response.message());
                        recipeFetchedListener.onRecipeFailed("No recipe was found");
                    }

                } else {
                    Log.d("debug", "recipe  api call fails/ is not successful " + response.message());
                    recipeFetchedListener.onRecipeFailed("Recipes couldn't be fetched");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeModel> call, @NonNull Throwable t) {
                recipeFetchedListener.onRecipeFailed("A fatal error occurred");
            }
        });
    }
}
