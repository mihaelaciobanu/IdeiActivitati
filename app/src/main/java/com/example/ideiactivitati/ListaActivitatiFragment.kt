package com.example.ideiactivitati

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ideiactivitati.data.Activitate
import com.example.ideiactivitati.databinding.FragmentListaActivitatiBinding
import com.example.ideiactivitati.modelviews.ActivitatiViewModel


class ListaActivitatiFragment : Fragment() {

    private var _binding : FragmentListaActivitatiBinding? = null

    private val binding get() = _binding!!

    private val viewModel : ActivitatiViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListaActivitatiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel

        viewModel.listaActivitati.observe(viewLifecycleOwner){
            var adapter = ActivitatiAdapter(it)
            binding.rvActivitati.adapter = adapter
            adapter.setOnClickListener(object : ActivitatiAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val navController = requireActivity().findNavController(R.id.nav_host_fragment_content_main)
                    val bundle = Bundle()
                    bundle.putSerializable("activitate", it[position])
                    //navController.navigate(R.id.DetaliiActivitateFragment, bundle)
                }
            })
        }

        binding.rvActivitati.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}