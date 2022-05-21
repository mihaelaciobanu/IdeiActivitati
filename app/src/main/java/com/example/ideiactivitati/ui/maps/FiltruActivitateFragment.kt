package com.example.ideiactivitati.ui.maps

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.ideiactivitati.R
import com.example.ideiactivitati.databinding.FiltruActivitateFragmentBinding
import com.example.ideiactivitati.ui.formular.LocalizareService
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class FiltruActivitateFragment : Fragment() {
    private var _binding: FiltruActivitateFragmentBinding? = null

    private val binding get() = _binding!!

    var lat: Double = 0.0
    var lng: Double = 0.0

    val COD_CERERE_PERMISIUNE_LOC = 1

    var permisiuniDate = false

    lateinit var intent: Intent

    private val receptorCoordonate = object : BroadcastReceiver() {

        override fun onReceive(p0: Context?, p1: Intent?) {
            val coordonate: LatLng = p1?.getParcelableExtra("locatie")!!
            lat = coordonate.latitude
            lng = coordonate.longitude
            binding.locatieCurenta.text = coordonate.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent = Intent(context, LocalizareService::class.java)
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(receptorCoordonate, IntentFilter("TRIMITE_COORD"))
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(receptorCoordonate)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FiltruActivitateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCurrentLocation()

        binding.numberPickerMetri.minValue = 0
        binding.numberPickerMetri.maxValue = 5000

        binding.btnCauta.setOnClickListener {
            searchPlaces()
        }
    }

    fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            permisiuniDate = true
            ContextCompat.startForegroundService(requireContext(), intent)
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog)
            val yesBtn = dialog.findViewById(R.id.btn_yes) as Button
            yesBtn.setOnClickListener {
                context?.stopService(intent)
                dialog.dismiss()
            }
            dialog.show()

        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), COD_CERERE_PERMISIUNE_LOC
            )
        }
    }

    fun searchPlaces() {

        val apiKey = "11d9c807237c42f6996217f4a06c5eb6"
        val category = binding.spinnerActivities.selectedItem

        val geoApifyUrl =
            "https://api.geoapify.com/v2/places?categories=${category}&lang=en&limit=10&apiKey=${apiKey}"

        val metri = binding.numberPickerMetri.value

        val url: String = if (binding.numberPickerMetri.value > 0) {
            "${geoApifyUrl}&filter=circle:${lng},${lat},${metri}"
        } else {
            "${geoApifyUrl}&filter=circle:${lng},${lat},5000"
        }

        val thread = Thread {
            try {
                val content = URL(url).readText()

                var jsonObject: JSONObject? = null

                jsonObject = JSONObject(content)

                if (jsonObject.has("error")) {
                    requireActivity().runOnUiThread(Runnable {
                        Toast.makeText(context, "Eroare! Reîncercați!", Toast.LENGTH_SHORT).show()
                    }
                    )
                    Toast.makeText(context, "Eroare! Reîncercați!", Toast.LENGTH_SHORT).show()
                } else if (jsonObject.has("features") && (jsonObject["features"] as JSONArray).length() > 0) {
                    val places = jsonObject["features"]

                    requireActivity().runOnUiThread(Runnable {
                        val navController =
                            requireActivity().findNavController(R.id.nav_host_fragment_content_main)
                        val bundle = Bundle()
                        bundle.putString("places", places.toString())
                        bundle.putString("tip", binding.spinnerActivities.selectedItem.toString())
                        navController.navigate(R.id.nav_maps, bundle)
                    }
                    )
                } else {

                    requireActivity().runOnUiThread(Runnable {
                        Toast.makeText(
                            context,
                            "Reîncercați schimbarea parametrilor!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    )

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == COD_CERERE_PERMISIUNE_LOC) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permisiuniDate = true
                ContextCompat.startForegroundService(requireContext(), intent)
            }
        }
    }
}