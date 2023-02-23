package com.example.coffeememos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.entity.CustomBean
import com.example.coffeememos.utilities.DateUtil
import com.example.coffeememos.utilities.ViewUtil

class BeanAdapter(context: Context, data: List<CustomBean>) : BaseAdapter<CustomBean, BeanViewHolder>(context, data) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeanViewHolder {
        return BeanViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bean_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BeanViewHolder, position: Int) {
        holder.country.text     = data[position].country
        holder.species.text     = data[position].species
        holder.storeName.text   = data[position].store
        holder.processName.text = Constants.processList[data[position].process]
        holder.rate.text        = context.getString(R.string.rate_decimal, data[position].rating.toString())
        holder.createdAt.text   = DateUtil.formatEpochTimeMills(data[position].createdAt, DateUtil.pattern)
        ViewUtil.setCardTag(holder.farm, data[position].farm)
        ViewUtil.setCardTag(holder.district, data[position].district)
        ViewUtil.setFavoriteIcon(holder.favoriteIcon, data[position].isFavorite)

        // リストアイテムクリック時のコールバック
        holder.itemView.setOnClickListener { v ->
            mItemClickListener.onClick(v, data[position])
        }

        // favoriteアイコンクリック時のコールバック
        holder.favoriteIcon.setOnClickListener { v ->
            mFavoriteListener.onFavoriteClick(v, data[position])
        }
    }
}

class BeanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val country     : TextView  = itemView.findViewById(R.id.country)
    val farm        : TextView  = itemView.findViewById(R.id.farm)
    val district    : TextView  = itemView.findViewById(R.id.district)
    val storeName   : TextView  = itemView.findViewById(R.id.storeName)
    val processName : TextView  = itemView.findViewById(R.id.processName)
    val species     : TextView  = itemView.findViewById(R.id.speciesName)
    val rate        : TextView  = itemView.findViewById(R.id.rating)
    val favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)
    val createdAt   : TextView  = itemView.findViewById(R.id.createdAt)
}