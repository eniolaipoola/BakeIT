package com.eniola.bakeit;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.eniola.bakeit.UIs.RecipeActivity;
import com.eniola.bakeit.data.APIClient;
import com.eniola.bakeit.data.APIService;
import com.eniola.bakeit.data.RecipeData;
import com.eniola.bakeit.data.RecipeDataInterface;
import com.eniola.bakeit.models.RecipeModel;
import com.eniola.bakeit.utilities.APPConstant;
import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientService extends IntentService implements RecipeDataInterface.OnRecipeFetchedListener{

    public static final String ACTION_DISPLAY_RECIPE_ACTIVITY_PAGE =
            "com.eniola.bakeit.action.display_recipe_activity_page";
    public static final String ACTION_DISPLAY_RECIPE_INGRDIENT_LIST =
            "com.eniola.bakeit.action.display_recipe_ingredients_list";
    public static String RECIPE_ID;
    List<RecipeModel> allRecipes;

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

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appwidgetIds = appWidgetManager.getAppWidgetIds(
               new ComponentName(this, BakingAppWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appwidgetIds, R.id.appwidget_imageview);
    }

    private void handleActionDisplayIngredientsList(){
        RecipeData recipeData;
        APIClient apiClient = new APIClient();
        APIService apiService = apiClient.getRetrofit(APPConstant.BASE_URL).create(APIService.class);
        recipeData = new RecipeData(apiService);
        allRecipes = new ArrayList<>();
        recipeData.getRecipes(this);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        BakingAppWidgetProvider.updateAppWidgetIds(this, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onRecipeSuccessful(List<RecipeModel> recipeModel) {
        Log.d(APPConstant.DEBUG_TAG, "Fetched recipes size is " + allRecipes.size());
    }

    @Override
    public void onRecipeFailed(String errorMessage) {
        Log.d(APPConstant.DEBUG_TAG, "An error occurred when trying to fetch recipes for widget");
    }
}
