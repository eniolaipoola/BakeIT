package com.eniola.bakeit.data;

import androidx.annotation.NonNull;

import com.eniola.bakeit.models.RecipeModel;

import java.util.List;

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
        apiService.getAllRecipes().enqueue(new Callback<List<RecipeModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<RecipeModel>> call, @NonNull Response<List<RecipeModel>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        List<RecipeModel> recipeModel = response.body();
                        recipeFetchedListener.onRecipeSuccessful(recipeModel);

                    } else {
                        recipeFetchedListener.onRecipeFailed("No recipe was found");
                    }

                } else {
                    recipeFetchedListener.onRecipeFailed("Recipes couldn't be fetched");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RecipeModel>> call, @NonNull Throwable t) {
                recipeFetchedListener.onRecipeFailed("A fatal error occurred");
            }
        });
    }
}
