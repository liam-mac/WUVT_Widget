package com.example.liam.wuvt_widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class Main extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds); //what is super?
        for(int i=0; i<appWidgetIds.length; i++){ //for all instances of the Widget
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.wuvt.vt.edu/last15")); //intent to view WUVT last15
            PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
            views.setOnClickPendingIntent(R.id.imageButton, pendingIntent); //determines what happens when you click the button

            //Update the app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}