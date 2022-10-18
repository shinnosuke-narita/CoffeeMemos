package com.example.coffeememos.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T, U : RecyclerView.ViewHolder>(val context: Context, protected val data: List<T>) : RecyclerView.Adapter<U>() {
    protected lateinit var mListener: OnItemClickListener<T>

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        mListener = listener
    }

    private var _listener: OnFavoriteClickListener? = null
    interface OnFavoriteClickListener {
        fun onClick()
    }

    fun setFavoriteListener(listener: OnFavoriteClickListener) {
        _listener = listener
    }
}
