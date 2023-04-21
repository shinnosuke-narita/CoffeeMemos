package com.withapp.coffeememo.core.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.withapp.coffeememo.R
import com.withapp.coffeememo.manager.DialogDataHolder

class SortAdapter (context: Context, private val data: List<DialogDataHolder.DialogData>) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(p0: Int): Long {
        // ID使わない
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View = convertView ?: inflater.inflate(R.layout.sort_list_item, null)

        itemView.findViewById<TextView>(R.id.sortName).text = data[position].name

        if (data[position].isSelected) {
            itemView.findViewById<ImageView>(R.id.checkIcon).visibility = View.VISIBLE
        } else {
            itemView.findViewById<ImageView>(R.id.checkIcon).visibility = View.INVISIBLE
        }

        return itemView
    }
}