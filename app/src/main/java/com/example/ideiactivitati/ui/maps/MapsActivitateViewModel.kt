package com.example.ideiactivitati.ui.maps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ideiactivitati.data.Activitate
import com.example.ideiactivitati.data.ActivitatiDataBase
import com.example.ideiactivitati.data.ActivitatiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsActivitateViewModel(application : Application) : AndroidViewModel(application) {

    var activitate : Activitate? = null

    private val repository = ActivitatiRepository(ActivitatiDataBase.getInstance(application)!!.getActivitateDao())

    val listaActivitati = repository.listaActivitati

    fun adaugaActivitate() {
        viewModelScope.launch (Dispatchers.IO ) {
            if(activitate != null) {
                repository.adaugaActivitate(activitate!!)
                activitate = null
            }
        }
    }
}