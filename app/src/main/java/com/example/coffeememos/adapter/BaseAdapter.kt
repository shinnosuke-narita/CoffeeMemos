package com.example.coffeememos.adapter

import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T, U : RecyclerView.ViewHolder>(protected val data: List<T>) : RecyclerView.Adapter<U>() {
    protected lateinit var mListener: OnItemClickListener<T>

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        mListener = listener
    }
}
