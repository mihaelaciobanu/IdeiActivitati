package com.example.ideiactivitati.ui.grafic

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ideiactivitati.R
import com.example.ideiactivitati.databinding.FragmentGraficBinding
import com.example.ideiactivitati.ui.lista.ActivitatiViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate

class GraficFragment : Fragment() {

    private var _binding: FragmentGraficBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private val viewModel : ActivitatiViewModel by viewModels()

    lateinit var chartList:ArrayList<Entry>
    lateinit var dataSet: LineDataSet
    lateinit var lineData: LineData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGraficBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        createChart()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createChart()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun createChart(){
        var i = 1
        viewModel.listaActivitati.observe(viewLifecycleOwner) {
            chartList = ArrayList()

            it.forEach {
                chartList.add(Entry(10f * i,  it.cost.toFloat()))
                i += 1
            }

            dataSet=LineDataSet(chartList, "Evolutia costului activitatilor")
            lineData=LineData(dataSet)


            val l= getView()?.findViewById<com.github.mikephil.charting.charts.LineChart>(R.id.line_chart)
            if (l != null) {
                l.data=lineData
            }
            dataSet.color = Color.BLACK

            dataSet.valueTextColor= Color.BLUE
            dataSet.valueTextSize = 19f

            dataSet.setDrawFilled(true)
        }
    }
}