package com.example.ideiactivitati.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActivitateDao {
    @Insert
    suspend fun adaugaActivitate(activitate: Activitate) : Long

    @Query("SELECT * FROM Activitate")
    fun getAll() : LiveData<List<Activitate>>

    @Query("SELECT * FROM Activitate")
    fun getAllSync() : List<Activitate>
    // a fi folosta pt widget ca acolo nu trebuie sa fie async
}