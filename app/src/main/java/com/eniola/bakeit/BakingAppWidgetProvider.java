package com.eniola.bakeit;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import com.eniola.bakeit.UIs.RecipeActivity;
import com.eniola.bakeit.UIs.RecipeInformationActivity;
import com.eniola.bakeit.utilities.APPConstant;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews remoteViews;
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        Log.d(APPConstant.DEBUG_TAG, "width is " + width);
        if(width < 250){
            remoteViews = makeWidgetLaunchDefaultActivity(context);
        } else {
            remoteViews = getRecipesGridRemoteView(context);
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgetIds(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    private static RemoteViews getRecipesGridRemoteView(Context context){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);
        Intent intent = new Intent(context, GridWidgetService.class);
        remoteViews.setRemoteAdapter(R.id.widget_grid_view, intent);

        //set RecipeInformationActivity to launch when clicked
        Intent appIntent = new Intent(context, RecipeInformationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.widget_grid_view, pendingIntent);
        remoteViews.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        return  remoteViews;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RecipeIngredientService.startActionDisplayRecipeIngredientList(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    private static RemoteViews makeWidgetLaunchDefaultActivity(Context context){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        Intent intent = new Intent(context, RecipeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.appwidget_imageview, pendingIntent);
        return  remoteViews;
    }

    private static RemoteViews makeWidgetLaunchService(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        Intent intent = new Intent(context, RecipeIngredientService.class);
        intent.setAction(RecipeIngredientService.ACTION_DISPLAY_RECIPE_INGRDIENT_LIST);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_imageview, pendingIntent);
        return views;
    }
}

