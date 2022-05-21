package com.example.ideiactivitati.ui.formular

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.ideiactivitati.R
import com.example.ideiactivitati.data.Activitate
import com.example.ideiactivitati.databinding.FragmentFormularBinding
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate

class FormularFragment : Fragment() {

    private var _binding: FragmentFormularBinding? = null
    private val viewModel : FormularViewModel by viewModels()

    private val binding get() = _binding!!

    lateinit var intent: Intent

    val COD_CERERE_PERMISIUNE_LOC=1

    var permisiuniDate =false

    var coordonate = LatLng(44.439663, 26.096306)

    private val receptorCoordonate = object : BroadcastReceiver() {

        override fun onReceive(p0: Context?, p1: Intent?) {
            coordonate = p1?.getParcelableExtra<LatLng>("locatie")!!
        }
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormularBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent = Intent(context, LocalizareService::class.java )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.spinner.adapter =
            this.context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Types)) }

        viewModel.listaActivitati.observe(viewLifecycleOwner) {
            it.forEach {
                //Toast.makeText(context,it.descriere + it.id.toString(),Toast.LENGTH_LONG).show()
            }
        }

        binding.switch1.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            if(b) {
                if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
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
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), COD_CERERE_PERMISIUNE_LOC)
                }
            }
        }

        binding.btnformular.setOnClickListener{
            if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
                if (binding.etDescriere.text != null &&
                    binding.etCost.text != null &&
                    binding.etParticipanti.text != null
                ) {
                    var date: LocalDate = LocalDate.of(
                        binding.datepicker.year,
                        binding.datepicker.month + 1,
                        binding.datepicker.dayOfMonth
                    )

                    viewModel.activitate = Activitate(
                        binding.etDescriere.text.toString()!!,
                        binding.spinner.selectedItem.toString()!!,
                        binding.etParticipanti.text.toString().toInt()!!,
                        binding.etCost.text.toString().toDouble()!!, date!!,
                        coordonate,
                        binding.etDetalii.text.toString()!!
                    )
                    viewModel.adaugaActivitate()
                } else {
                    Toast.makeText(context, "Completati corespunzator", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Nu exista permisiuni pt locatie ", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == COD_CERERE_PERMISIUNE_LOC) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permisiuniDate=true
                ContextCompat.startForegroundService(requireContext(), intent)
            }
        }
    }
}