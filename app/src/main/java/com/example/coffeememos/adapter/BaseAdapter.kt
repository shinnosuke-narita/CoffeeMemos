package com.example.coffeememos.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.listener.OnFavoriteIconClickListener
import com.example.coffeememos.listener.OnItemClickListener


abstract class BaseAdapter<T, U : RecyclerView.ViewHolder>(val context: Context, protected val data: List<T>) : RecyclerView.Adapter<U>() {
    protected lateinit var mItemClickListener: OnItemClickListener<T>
    protected lateinit var mFavoriteListener: OnFavoriteIconClickListener

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        mItemClickListener = listener
    }

    fun setFavoriteListener(listener: OnFavoriteIconClickListener) {
        mFavoriteListener = listener
    }




}
