package com.eniola.bakeit.data;

import com.eniola.bakeit.models.RecipeModel;

import java.util.List;

public interface RecipeDataInterface {

    interface OnRecipeFetchedListener{

        void  onRecipeSuccessful(List<RecipeModel> recipeModel);
        void onRecipeFailed(String errorMessage);
    }

    void getRecipes(OnRecipeFetchedListener recipeFetchedListener);
}
