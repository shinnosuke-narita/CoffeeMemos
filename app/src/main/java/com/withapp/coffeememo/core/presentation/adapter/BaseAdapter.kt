package com.withapp.coffeememo.core.presentation.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.withapp.coffeememo.listener.OnFavoriteIconClickListener
import com.withapp.coffeememo.listener.OnItemClickListener


abstract class BaseAdapter<T, U : RecyclerView.ViewHolder>(val context: Context, protected val data: List<T>) : RecyclerView.Adapter<U>() {
    protected lateinit var mItemClickListener: OnItemClickListener<T>
    protected lateinit var mFavoriteListener: OnFavoriteIconClickListener<T>

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        mItemClickListener = listener
    }

    fun setFavoriteListener(listener: OnFavoriteIconClickListener<T>) {
        mFavoriteListener = listener
    }
}
