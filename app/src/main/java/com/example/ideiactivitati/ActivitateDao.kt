package com.example.ideiactivitati

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ActivitateDao {
    @Insert
    suspend fun adaugaActivitate(activitate: Activitate): Long

    @Query("select * from Activitate")
    fun getAll() : LiveData<List<Activitate>>
}