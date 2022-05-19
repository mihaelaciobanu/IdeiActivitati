package com.example.ideiactivitati.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate


class Convertori {
    @TypeConverter
    fun dateToString(date: LocalDate) :String {
        return date.toString()
    }

    @TypeConverter
    fun stringToDate(data:String) : LocalDate {
        return LocalDate.parse(data)
    }

    @TypeConverter
    fun locatieToString(locatie: LatLng) : String {
        return locatie.latitude.toString() +","+ locatie.longitude.toString()
    }

    @TypeConverter
    fun locatieToLatLng(locatie:String) : LatLng {
        var coordonate = locatie.split(',')
        var latitudine = coordonate[0]
        var longitudine = coordonate[1].subSequence(1, coordonate[1].length).toString()

        return LatLng(latitudine.toDouble(), longitudine.toDouble())
    }

}