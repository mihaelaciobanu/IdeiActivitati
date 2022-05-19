package com.example.ideiactivitati

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@RequiresApi(Build.VERSION_CODES.O)
@TypeConverters(Convertori::class)
@Database(entities = [Activitate::class], version =2)
abstract class ActivitatiDataBase : RoomDatabase() {
    abstract fun getActivitateDao () : ActivitateDao

    companion object {
        var activitatiDatabase : ActivitatiDataBase? = null

        fun getInstance(context: Context) : ActivitatiDataBase? {
            if(activitatiDatabase == null) {
                activitatiDatabase = Room.databaseBuilder(context, ActivitatiDataBase::class.java, "activitati.db")
                    .fallbackToDestructiveMigration().build()
            }
            return activitatiDatabase!!
        }
    }
}