package com.example.ideiactivitati.ui.lista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ideiactivitati.R
import com.example.ideiactivitati.data.Activitate

class ActivitatiAdapter(val listaActivitati:List<Activitate>) : RecyclerView.Adapter<ActivitatiAdapter.ViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position : Int)
    }

    fun setOnClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val tvDescriere : TextView = view.findViewById(R.id.tv_descriere)
        val tvTip : TextView = view.findViewById(R.id.tv_tip)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_activitate, parent, false)

        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activitate = listaActivitati[position]
        holder.tvDescriere.text = activitate.descriere
        holder.tvTip.text = activitate.tip
    }

    override fun getItemCount(): Int {
        return listaActivitati.size
    }
}