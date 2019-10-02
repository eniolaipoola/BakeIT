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

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        Log.d("Debug", "app widget width size is " + width);
        RemoteViews remoteViews;
        if(width < 300){
            //remoteViews = makeWidgetLaunchDefaultActivity(context);
            remoteViews = makeWidgetLaunchService(context);
        } else {
            remoteViews = getRecipesGridRemoteView(context);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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
        RecipeIngredientService.startActionDisplayRecipeActivityPage(context);
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
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        Intent intent = new Intent(context, RecipeIngredientService.class);
        intent.putExtra(RecipeIngredientService.RECIPE_ID, 3);
        intent.setAction(RecipeIngredientService.ACTION_DISPLAY_RECIPE_ACTIVITY_PAGE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_imageview, pendingIntent);
        return views;
    }

    private static RemoteViews getRecipesGridRemoteView(Context context){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        Intent intent = new Intent(context, GridWidgetService.class);
        remoteViews.setRemoteAdapter(R.id.appwidget_imageview, intent);
        Intent appIntent = new Intent(context, RecipeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.widget_grid_view, pendingIntent);
        remoteViews.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        return  remoteViews;
    }
}

