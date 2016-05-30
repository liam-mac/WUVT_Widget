package com.example.liam.wuvt_widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.RemoteViews;



import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import android.os.Environment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Main extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int i=0; i<appWidgetIds.length; i++){ //for all instances of the Widget
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.wuvt.vt.edu/last15")); //intent to view WUVT last15
            PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
            views.setOnClickPendingIntent(R.id.imageButton, pendingIntent); //determines what happens when you click the button

            //Update the app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

            //Save the list of songs to look at later

            String new_songs = "";
            try{
            org.jsoup.nodes.Document doc = org.jsoup.Jsoup.connect("https://www.wuvt.vt.edu/last15").get(); //sends GET request to example.com
                org.jsoup.nodes.Element song_table = doc.getElementById("last15tracks");
                org.jsoup.select.Elements table_body = song_table.getElementsByTag("tbody");
                new_songs = table_body.html(); //gets inner html of the body
            }
            catch (java.io.IOException e) {
            e.printStackTrace();
            }

            File file;
            FileOutputStream outputStream;
            try {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "WUVT_songs.html"); //Saves html file to DOCUMENTS folder on the phone

                outputStream = new FileOutputStream(file);
                outputStream.write(new_songs.getBytes()); //or content.string().getBytes() to string-i-fy first since getyBytes() takes a string as input
                outputStream.close();
            } catch (IOException e) { //if there's an error
                e.printStackTrace();
            }


        }
    }
}