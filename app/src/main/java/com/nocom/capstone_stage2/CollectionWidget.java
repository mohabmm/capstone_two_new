package com.nocom.capstone_stage2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;


public class CollectionWidget extends AppWidgetProvider {

    public static final String EXTRA_ITEM = "com.nocom.capstone_stage2 ";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), CollectionWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);


        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.putExtra("Random", Math.random() * 1000); // Add a random integer to stop the Intent being ignored.  This is needed for some API levels due to intent caching
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widgetlayout);
            rv.setRemoteAdapter(R.id.widget_list_view, intent);
            Intent intent2 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.widget_list_view, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[i], R.id.widget_list_view);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}