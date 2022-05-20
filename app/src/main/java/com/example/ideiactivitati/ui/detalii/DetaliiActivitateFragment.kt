package com.example.ideiactivitati.ui.detalii

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ideiactivitati.data.Activitate
import com.example.ideiactivitati.databinding.FragmentDetaliiActivitateBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class DetaliiActivitateFragment : Fragment(), OnMapReadyCallback{

    private var _binding : FragmentDetaliiActivitateBinding? = null

    private val binding get() = _binding!!

    lateinit var activitate : Activitate

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentDetaliiActivitateBinding.inflate(inflater, container, false)
        activitate = arguments?.getSerializable("activitate") as Activitate
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDescriere.text = activitate.descriere
        binding.tvTip.text = activitate.tip

        var calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, activitate.data.year)
        calendar.set(Calendar.MONTH, activitate.data.monthValue)
        calendar.set(Calendar.DAY_OF_MONTH, activitate.data.dayOfMonth)
        binding.calendar.setDate(calendar.timeInMillis)
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync {
            it.addMarker(MarkerOptions().position(LatLng(44.442258, 26.089113)))
            //it.moveCamera(CameraUpdateFactory.newLatLng(LatLng(44.442258, 26.089113)))
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(44.442258, 26.089113), 10f))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.map.onDestroy()
        _binding = null
    }

    override fun onMapReady(p0: GoogleMap) {

    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.map.onLowMemory()
    }
}