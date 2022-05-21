package com.example.ideiactivitati.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.ideiactivitati.R
import com.example.ideiactivitati.databinding.FiltruActivitateFragmentBinding
import com.example.ideiactivitati.ui.formular.LocalizareService
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class FiltruActivitateFragment : Fragment() {
    private var _binding: FiltruActivitateFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FiltruActivitateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberPickerMetri.minValue = 0
        binding.numberPickerMetri.maxValue = 5000

        val apiKey = "11d9c807237c42f6996217f4a06c5eb6"
        val category = binding.spinnerActivities.selectedItem

        val localizareService = LocalizareService()

        val coordonate = localizareService.coordonate

        var lat = 44.427528
        var lng = 26.106027

        if (coordonate != null) {
            lat = coordonate.latitude
            lng = coordonate.longitude
        }

        val geoApifyUrl =
            "https://api.geoapify.com/v2/places?categories=${category}&lang=en&limit=10&apiKey=${apiKey}"

        binding.btnCauta.setOnClickListener {
            val metri = binding.numberPickerMetri.value
            if (binding.numberPickerMetri.value > 0) {
                searchPlaces("${geoApifyUrl}&filter=circle:${lng},${lat},${metri}")
            } else {
                searchPlaces("${geoApifyUrl}&filter=circle:${lng},${lat},5000")
            }
        }
    }

    fun searchPlaces(stringUrl: String) {

        val thread = Thread {
            try {
                val content = URL(stringUrl).readText()

                var jsonObject: JSONObject? = null

                jsonObject = JSONObject(content)

                if (jsonObject.has("error")) {
                    Toast.makeText(context, "Eroare! Reîncercați!", Toast.LENGTH_SHORT).show()
                } else if (jsonObject.has("features") && (jsonObject["features"] as JSONArray).length() > 0) {
                    val places = jsonObject["features"]

                    val navController =
                        requireActivity().findNavController(R.id.nav_host_fragment_content_main)

                    val bundle = Bundle()
                    bundle.putString("places", places.toString())
                    bundle.putString("tip", binding.spinnerActivities.selectedItem.toString())
                    navController.navigate(R.id.nav_maps, bundle)

                } else {
                    Toast.makeText(
                        context,
                        "Reîncercați schimbarea parametrilor!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }
}