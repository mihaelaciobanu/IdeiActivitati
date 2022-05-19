package com.example.ideiactivitati.widget

import android.app.Application
import android.database.Cursor
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.ideiactivitati.R
import com.example.ideiactivitati.data.ActivitatiFC

class ActivitatiWidgetAdapter(private val application: Application) : RemoteViewsService.RemoteViewsFactory {

    private var cursor : Cursor? =null

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        cursor?.close()
        cursor =  application.contentResolver?.query(ActivitatiFC.CONTENT_URI,null,null,null)
    }

    override fun onDestroy() {
        cursor?.close()
    }

    override fun getCount(): Int {
        return cursor?.count ?: 0
    }

    override fun getViewAt(p0: Int): RemoteViews {
        return RemoteViews(application.packageName, R.layout.element_widget).apply {
            cursor?.moveToPosition(p0) //TODO: verificare
            setTextViewText(R.id.tv_descriereWidget, cursor?.getString(0) ?: "" )
        }
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}