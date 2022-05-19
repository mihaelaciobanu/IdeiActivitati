package com.example.ideiactivitati

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.ideiactivitati.data.Activitate
import com.example.ideiactivitati.databinding.FragmentActivitateNouaBinding
import com.example.ideiactivitati.modelviews.ActivitateNouaViewModel
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate


class ActivitateNouaFragment : Fragment() {


    private var _binding: FragmentActivitateNouaBinding? = null

    private val binding get() = _binding!!
    lateinit var intent : Intent
    private val viewModel : ActivitateNouaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activitate_noua, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // am hardcodat pentru a testa lista de activitati
        // trebuie preluat din controale si salvat prin viewModel
        // daca preluam locatia de pe harta pentru atributul locatie trebuie incluse si chestiile cu cererea permisiunilor (sunt in seminar facute deja)
        var activitate = Activitate("Voluntariat", "Charity", 2, 2.0, LocalDate.now(), LatLng(37.12,134.12))
        viewModel.activitate = activitate
        viewModel.adaugaActivitate()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}