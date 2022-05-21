package com.example.ideiactivitati.ui.maps

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.ideiactivitati.R
import com.example.ideiactivitati.data.Activitate
import com.example.ideiactivitati.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import kotlin.random.Random

class MapsFragment() : Fragment() {

    private lateinit var viewModel: MapsActivitateViewModel

    private var _binding: FragmentMapsBinding? = null

    private val binding get() = _binding!!

    var lat: Double = 0.0

    var lng: Double = 0.0

    private val callback = OnMapReadyCallback { googleMap ->

        val locatie = LatLng(lat, lng)
        googleMap.addMarker(MarkerOptions().position(locatie).title("Locație găsită"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(locatie))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val placesString = arguments?.getString("places")

        val places = JSONArray(placesString)

        if (places.length() != 0) {

            val index = (0..places.length()).shuffled().last()
            if (places[index] != null) {
                val place = places[index] as JSONObject

                if (place.has("properties")) {
                    val properties = place["properties"] as JSONObject

                    if (properties.has("lat")) {
                        lat = properties["lat"] as Double
                    }

                    if (properties.has("lon")) {
                        lng = properties["lon"] as Double
                    }

                    if (properties.has("name")) {
                        binding.tvLocatie.text = properties["name"].toString()
                    } else if (properties.has("street")) {
                        binding.tvLocatie.text = properties["street"].toString()
                    }
                }
            }
            else{
                Toast.makeText(context, "Eroare", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Eroare", Toast.LENGTH_SHORT).show()
        }

        mapFragment?.getMapAsync(callback)

        binding.btnAdaugaActivitate.setOnClickListener {
            if (binding.etDescriere.text.toString().isEmpty()) {
                Toast.makeText(context, "Completează descrierea!", Toast.LENGTH_SHORT).show()
            } else {
                val descriere = binding.etDescriere.text.toString()
                val data: LocalDate = LocalDate.of(
                    binding.datePickerLocatie.year,
                    binding.datePickerLocatie.month + 1,
                    binding.datePickerLocatie.dayOfMonth
                )
                val locatie = LatLng(lat, lng)
                val tip = arguments?.getString("tip").toString()
                viewModel.activitate = Activitate(
                    descriere, tip, 1, 0.0, data, locatie
                )
                viewModel.adaugaActivitate()
                Toast.makeText(context, "Activitate adaugata!", Toast.LENGTH_SHORT).show()
                val navController =
                    requireActivity().findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.nav_home)
            }
        }

        binding.btnReincearca.setOnClickListener {
            val navController =
                requireActivity().findNavController(R.id.nav_host_fragment_content_main)

            navController.popBackStack(R.id.nav_filtre, false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapsActivitateViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}