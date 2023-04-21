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

class DialogDataAdapter(val context: Context, val data: List<DialogDataHolder.DialogData>): BaseAdapter() {
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView = convertView ?: inflater.inflate(R.layout.bean_process_item, parent, false)
        itemView.findViewById<TextView>(R.id.processTitle).text = data[position].name

        if (data[position].isSelected) itemView.findViewById<ImageView>(R.id.checkedRadioBtn).visibility = View.VISIBLE
        else itemView.findViewById<ImageView>(R.id.checkedRadioBtn).visibility = View.GONE

        return itemView
    }
}