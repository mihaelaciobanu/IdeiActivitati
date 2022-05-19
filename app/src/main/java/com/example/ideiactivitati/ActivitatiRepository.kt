package com.example.ideiactivitati

class ActivitatiRepository(private val activitateDao: ActivitateDao) {
    val listaActivitati = activitateDao.getAll()

    suspend fun adaugaActivitate(activitate: Activitate) {
        activitateDao.adaugaActivitate(activitate)
    }
}