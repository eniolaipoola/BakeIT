package com.eniola.bakeit;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.eniola.bakeit.UIs.RecipeActivity;
import com.eniola.bakeit.UIs.RecipeInformationActivity;

public class RecipeIngredientService extends IntentService {

    public static final String ACTION_DISPLAY_RECIPE_ACTIVITY_PAGE =
            "com.eniola.bakeit.action.display_recipe_activity_page";

    public static final String ACTION_DISPLAY_RECIPE_INGRDIENT_LIST =
            "com.eniola.bakeit.action.display_recipe_ingredients_list";

    public static String RECIPE_ID;

    public RecipeIngredientService() {
        super("RecipeIngredientService");
    }

    public static void startActionDisplayRecipeActivityPage(Context context) {
        Intent intent = new Intent(context, RecipeIngredientService.class);
        intent.setAction(ACTION_DISPLAY_RECIPE_ACTIVITY_PAGE);
        context.startService(intent);
    }

    public static void startActionDisplayRecipeIngredientList(Context context){
        Intent intent = new Intent(context, RecipeIngredientService.class);
        intent.setAction(ACTION_DISPLAY_RECIPE_INGRDIENT_LIST);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            int recipeId = intent.getIntExtra("recipeId", 0);
            if (ACTION_DISPLAY_RECIPE_ACTIVITY_PAGE.equals(action)) {
                handleActionDisplayRecipeActivityPage();
            } else if(ACTION_DISPLAY_RECIPE_INGRDIENT_LIST.equals(action)){
                handleActionDisplayIngredientsList();
            }
        }
    }

    private void handleActionDisplayRecipeActivityPage() {
        //for now, just launch the main activity.
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);

        //fetches desired recipes and displays its ingredient list
       /* AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appwidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, BakingAppWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appwidgetIds, R.id.appwidget_imageview);*/
    }

    private void handleActionDisplayIngredientsList(){

        //make api call to fetch all recipes


        //launch ingredient activity list given a specific id
        Intent intent = new Intent(this, RecipeInformationActivity.class);
        //intent.putExtra("RECIPE", recipeModel);
        startActivity(intent);
    }

}
