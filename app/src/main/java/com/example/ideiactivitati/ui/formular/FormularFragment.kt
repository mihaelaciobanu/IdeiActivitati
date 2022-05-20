package com.example.ideiactivitati.ui.formular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.ideiactivitati.R
import com.example.ideiactivitati.data.Activitate
import com.example.ideiactivitati.databinding.FragmentFormularBinding
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate

class FormularFragment : Fragment() {

    private var _binding: FragmentFormularBinding? = null
    private val viewModel : FormularViewModel by viewModels()

  private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val formularViewModel =
            ViewModelProvider(this).get(FormularViewModel::class.java)

        _binding = FragmentFormularBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.spinner.adapter =
            this.context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Types)) }

        viewModel.listaActivitati.observe(viewLifecycleOwner) {
            it.forEach {
                Toast.makeText(context,it.descriere + it.id.toString(),Toast.LENGTH_LONG).show()
            }
        }

        binding.btnformular.setOnClickListener{
            if( binding.etDescriere.text !=null &&
                    binding.etCost.text !=null &&
                    binding.etParticipanti.text != null) {
                var date: LocalDate = LocalDate.of(binding.datepicker.year, binding.datepicker.month +1, binding.datepicker.dayOfMonth )

                viewModel.activitate = Activitate(binding.etDescriere.text.toString()!!,
                    binding.spinner.selectedItem.toString()!!,
                    binding.etParticipanti.text.toString().toInt()!!,
                    binding.etCost.text.toString().toDouble()!!, date!!,
                    LatLng(0.0,0.0),
                    binding.etDetalii.text.toString()!!)

                viewModel.adaugaActivitate()
                               // Toast.makeText(context,binding.etDescriere.text,Toast.LENGTH_LONG).show()

            }
            else {
                Toast.makeText(context,"caca",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}