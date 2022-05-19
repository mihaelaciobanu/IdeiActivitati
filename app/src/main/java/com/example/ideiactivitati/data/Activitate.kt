package com.example.ideiactivitati.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.io.Serializable
import java.time.LocalDate

@Entity
class Activitate constructor(
    var descriere : String,
    var tip : String,
    var participanti : Int,
    var cost : Double,
    var data : LocalDate,
    var locatie : LatLng,
    var detaliiSuplimentare : String? = null,
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
) : Serializable {
}