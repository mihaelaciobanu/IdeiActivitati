package com.example.ideiactivitati

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate

class Convertori {
    @TypeConverter
    fun dateToString(date : LocalDate) : String {
        return date.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun stringToDate(data : String) : LocalDate {
        return LocalDate.parse(data)
    }
}