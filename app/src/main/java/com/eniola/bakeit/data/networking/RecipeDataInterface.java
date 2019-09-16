package com.eniola.bakeit.data.networking;

import com.eniola.bakeit.data.models.RecipeModel;

public interface RecipeDataInterface {

    interface OnRecipeFetchedListener{

        void  onRecipeSuccessful(RecipeModel recipeModel);
        void onRecipeFailed(String errorMessage);
    }

    void getRecipes(OnRecipeFetchedListener recipeFetchedListener);
}
