package com.eniola.bakeit;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

public class RecipeIngredientService extends IntentService {

    private static final String ACTION_DISPLAY_RECIPE_INGREDIENT_LIST =
            "com.eniola.bakeit.action.display_recipe_ingredients_list";

    public RecipeIngredientService() {
        super("RecipeIngredientService");
    }


    public static void startActionDisplayRecipeIngredientList(Context context) {
        Intent intent = new Intent(context, RecipeIngredientService.class);
        intent.setAction(ACTION_DISPLAY_RECIPE_INGREDIENT_LIST);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DISPLAY_RECIPE_INGREDIENT_LIST.equals(action)) {
                handleActionDisplayIngredientsList();
            }
        }
    }

    private void handleActionDisplayIngredientsList() {
        //fetches desired recipes and displays its ingredient list

    }

}
