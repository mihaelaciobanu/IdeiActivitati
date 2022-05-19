package com.example.ideiactivitati.widget

import android.content.Intent
import android.widget.RemoteViewsService

class ActivitatiWidgetService  : RemoteViewsService(){

    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return ActivitatiWidgetAdapter(this.application)
    }
}