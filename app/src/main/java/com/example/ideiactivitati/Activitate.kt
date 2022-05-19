package com.example.ideiactivitati

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
@RequiresApi(Build.VERSION_CODES.O)
class Activitate constructor(
    @PrimaryKey(autoGenerate = true)
    var id : Int =0,
    var descriere : String,
    var tip : String,
    var participanti : Int,
    var cost : Double,
    var data : LocalDate,
    var detaliiSuplimentare : String
) {
}