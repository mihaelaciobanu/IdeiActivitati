package com.example.ideiactivitati.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Convertori::class)
@Database(entities = [Activitate::class], version = 1)
abstract class ActivitatiDataBase : RoomDatabase() {
    abstract fun getActivitateDao() : ActivitateDao

    companion object {
        var activitatiDataBase: ActivitatiDataBase? = null

        fun getInstance(context: Context) : ActivitatiDataBase? {
            if(activitatiDataBase == null) {
                activitatiDataBase = Room.databaseBuilder(context, ActivitatiDataBase::class.java, "activitati.db")
                    .fallbackToDestructiveMigration() // se sterge baza de date cand se schimba versiunea
                    //.allowMainThreadQueries() // temporar pentru a testa
                    .build()
            }
            return activitatiDataBase!!
        }
    }
}