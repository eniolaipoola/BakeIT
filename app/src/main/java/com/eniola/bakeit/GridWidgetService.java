package com.eniola.bakeit;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.eniola.bakeit.data.APIClient;
import com.eniola.bakeit.data.APIService;
import com.eniola.bakeit.data.RecipeData;
import com.eniola.bakeit.data.RecipeDataInterface;
import com.eniola.bakeit.models.RecipeModel;
import com.eniola.bakeit.utilities.APPConstant;
import java.util.ArrayList;
import java.util.List;

public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewFactory(this.getApplicationContext());
    }

    public class GridRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory, RecipeDataInterface.OnRecipeFetchedListener {

        Context mContext;
        List<RecipeModel> allRecipes;
        APIService apiService;
        APIClient apiClient;
        RecipeData recipeData;

        public GridRemoteViewFactory(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onCreate() {
            allRecipes = new ArrayList<>();
            apiClient = new APIClient();
            apiService = apiClient.getRetrofit(APPConstant.BASE_URL).create(APIService.class);
            recipeData = new RecipeData(apiService);
            recipeData.getRecipes(this);
        }

        @Override
        public void onDataSetChanged() {
            //get all recipes from the API call
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if(allRecipes != null){
                return allRecipes.size();
            }
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            String recipeName = allRecipes.get(i).getName();
            int recipeId = allRecipes.get(i).getId();
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.baking_app_widget_provider);
            remoteViews.setTextViewText(R.id.desiredRecipe, recipeName);
            remoteViews.setViewVisibility(R.id.empty_view, View.GONE);

            //Create a bundle for recipes
            Bundle extras = new Bundle();
            extras.putInt(RecipeIngredientService.RECIPE_ID,  recipeId);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtra("RECIPE", allRecipes.get(i));
            remoteViews.setOnClickFillInIntent(R.id.appwidget_imageview, fillInIntent);

            return remoteViews ;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public void onRecipeSuccessful(List<RecipeModel> recipeModel) {
            allRecipes.addAll(recipeModel);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, BakingAppWidgetProvider.class));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        }

        @Override
        public void onRecipeFailed(String errorMessage) {
            Log.d(APPConstant.DEBUG_TAG, "Cannot fetch recipes successfully");
        }
    }
}
