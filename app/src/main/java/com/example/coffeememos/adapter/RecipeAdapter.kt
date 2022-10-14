package com.example.coffeememos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.entity.Bean

import com.example.coffeememos.entity.RecipeWithBeans

class RecipeAdapter(data: List<SimpleRecipe>) : BaseAdapter<SimpleRecipe, RecipeViewHolder>(data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_horizontal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.country.text      = data[position].country
        holder.createdAt.text    = data[position].createdAt
        holder.tool.text         = data[position].tool
        holder.roast.text        = data[position].roast

        holder.itemView.setOnClickListener { view ->
            mListener.onClick(view, data[position])
        }
    }
}



class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val country: TextView    = itemView.findViewById(R.id.country)
    val createdAt: TextView  = itemView.findViewById(R.id.createdAt)
    val tool: TextView       = itemView.findViewById(R.id.tool)
    val roast: TextView      = itemView.findViewById(R.id.roast)
}