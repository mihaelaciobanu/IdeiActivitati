package com.example.ideiactivitati.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.ideiactivitati.R

/**
 * Implementation of App Widget functionality.
 */
class ActivitatiWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = "exemplu"
    // Construct the RemoteViews object
    val intent= Intent(context,ActivitatiWidgetService::class.java)

    val views = RemoteViews(context.packageName, R.layout.activitati_widget)
    views.setTextViewText(R.id.tv_descriereWidget, widgetText)
    views.setRemoteAdapter(R.id.lv_activitati_widget, intent)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}