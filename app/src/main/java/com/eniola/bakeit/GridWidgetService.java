package com.eniola.bakeit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.eniola.bakeit.data.APIClient;
import com.eniola.bakeit.data.APIService;
import com.eniola.bakeit.data.RecipeData;
import com.eniola.bakeit.data.RecipeDataInterface;
import com.eniola.bakeit.models.RecipeModel;
import com.eniola.bakeit.utilities.APPConstant;
import java.util.List;


class GridWidgetService  extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewFactory(this.getApplicationContext());
    }

    class GridRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory, RecipeDataInterface.OnRecipeFetchedListener {

        Context mContext;
        List<RecipeModel> allRecipes;
        APIService apiService;
        APIClient apiClient;
        RecipeData recipeData;

        public GridRemoteViewFactory(Context mContext) {
            this.mContext = mContext;
            apiClient = new APIClient();
            apiService = apiClient.getRetrofit(APPConstant.BASE_URL).create(APIService.class);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            //get all recipes from the API call
            recipeData = new RecipeData(apiService);
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
            return remoteViews ;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public void onRecipeSuccessful(List<RecipeModel> recipeModel) {
            allRecipes = recipeModel;
        }

        @Override
        public void onRecipeFailed(String errorMessage) {
            Log.d("Debug log", "Cannot fetch recipes successfully");
        }
    }
}
