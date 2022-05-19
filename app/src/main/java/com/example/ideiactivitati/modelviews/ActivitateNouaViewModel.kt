package com.example.ideiactivitati.modelviews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ideiactivitati.data.Activitate
import com.example.ideiactivitati.data.ActivitatiDataBase
import com.example.ideiactivitati.data.ActivitatiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivitateNouaViewModel(application: Application) : AndroidViewModel(application) {

    var activitate : Activitate? = null

    private val repository = ActivitatiRepository(ActivitatiDataBase.getInstance(application)!!.getActivitateDao())

    fun adaugaActivitate() {
        viewModelScope.launch(Dispatchers.IO){
            if(activitate!=null) {
                repository.adaugaActivitate(activitate!!)
                activitate = null
            }
        }
    }
}