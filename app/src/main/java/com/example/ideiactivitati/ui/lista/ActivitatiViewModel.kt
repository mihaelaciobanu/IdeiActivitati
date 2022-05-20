package com.example.ideiactivitati.ui.lista

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ideiactivitati.data.ActivitatiDataBase
import com.example.ideiactivitati.data.ActivitatiRepository

class ActivitatiViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ActivitatiRepository(ActivitatiDataBase.getInstance(application)!!.getActivitateDao())
    val listaActivitati = repository.listaActivitati
}