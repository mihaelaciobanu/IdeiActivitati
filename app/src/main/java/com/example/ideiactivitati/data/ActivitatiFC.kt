package com.example.ideiactivitati.data

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.sqlite.db.SupportSQLiteDatabase

class ActivitatiFC : ContentProvider() {

    companion object {
        val  PROVIDER_NAME = "com.example.ideiactivitati.data.ActivitatiFC"
        val CONTENT_URI =
            Uri.parse("content://$PROVIDER_NAME/activitati")
    }

    private var db: SupportSQLiteDatabase? = null
    override fun onCreate(): Boolean {
        var helper = ActivitatiDataBase.getInstance(context!!)?.openHelper
        db = helper?.writableDatabase
        return db != null
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return db?.query("SELECT * FROM Activitate")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        db?.insert("Activitate", 0,p1)
        context?.contentResolver?.notifyChange(p0, null)
        return p0
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        var count : Int = db?.delete("Activitate", p1, p2)!!
        context?.contentResolver?.notifyChange(p0, null)
        return count
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        var count : Int = db?.update("Activitate",0,p1, p2, p3 )!!
        context?.contentResolver?.notifyChange(p0, null)
        return count
    }
}