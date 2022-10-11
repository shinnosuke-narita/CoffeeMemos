package com.example.coffeememos.adapter

import android.app.LauncherActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.R
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.entity.Bean

class BeanAdapter(private val data: List<Bean>) : RecyclerView.Adapter<BeanViewHolder>()  {
    private lateinit var mListener: OnItemClickListener<Bean>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeanViewHolder {
        return BeanViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bean_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BeanViewHolder, position: Int) {
        holder.country.text         = data[position].country
        holder.farm.text            = data[position].farm
        holder.district.text        = data[position].district
        holder.elevationFrom.text   = data[position].elevationFrom.toString()
        holder.elevationTo.text     = data[position].elevationTo.toString()
        holder.store.text           = data[position].store
        holder.rate.text            = data[position].review.toString()

        holder.itemView.setOnClickListener { v ->
            mListener.onClick(v, data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener<Bean>) {
        mListener = listener
    }
}

class BeanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val country: TextView        = itemView.findViewById(R.id.rvCountry)
    val farm: TextView           = itemView.findViewById(R.id.rvFarm)
    val district: TextView       = itemView.findViewById(R.id.rvDistrict)
    val elevationFrom: TextView  = itemView.findViewById(R.id.rvElevationFrom)
    val elevationTo: TextView    = itemView.findViewById(R.id.rvElevationTo)
    val store: TextView          = itemView.findViewById(R.id.rvStore)
    val rate: TextView           = itemView.findViewById(R.id.rvRate)
}