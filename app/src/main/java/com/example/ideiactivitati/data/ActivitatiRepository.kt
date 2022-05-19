package com.example.ideiactivitati.data

class ActivitatiRepository(private val activitateDao: ActivitateDao) {

    val listaActivitati = activitateDao.getAll()

    suspend fun adaugaActivitate(activitate: Activitate) {
        activitateDao.adaugaActivitate(activitate)
    }

    fun obtineToateActivitatile() : List<Activitate> {
        return activitateDao.getAllSync()
    }
}