package com.withapp.coffeememo.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.R
import com.withapp.coffeememo.presentation.menu.model.MenuItem

class MenuAdapter(
    private val data: List<MenuItem>,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuItemViewHolder {
        return MenuItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.menu_list_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(
        holder: MenuItemViewHolder,
        position: Int
    ) {
        holder.title.text = data[position].title
        holder.description.text = data[position].description

        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }


    class MenuItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.desc)
    }
}
