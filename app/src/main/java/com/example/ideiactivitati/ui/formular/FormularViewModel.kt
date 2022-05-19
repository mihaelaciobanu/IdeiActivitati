package com.example.ideiactivitati.ui.formular

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.ideiactivitati.data.Activitate
import com.example.ideiactivitati.data.ActivitatiDataBase
import com.example.ideiactivitati.data.ActivitatiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class FormularViewModel(application : Application):  AndroidViewModel(application) {

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