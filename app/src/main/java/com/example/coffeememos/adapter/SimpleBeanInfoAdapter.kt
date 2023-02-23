package com.example.coffeememos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.R
import com.example.coffeememos.SimpleBeanInfo
import com.example.coffeememos.utilities.ViewUtil
import com.example.coffeememos.utilities.ViewUtil.Companion.setFavoriteIcon

class SimpleBeanInfoAdapter(context: Context, data: List<SimpleBeanInfo>) : BaseAdapter<SimpleBeanInfo, SimpleBeanInfoViewHolder>(context, data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleBeanInfoViewHolder {
        return SimpleBeanInfoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bean_item_horizontal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SimpleBeanInfoViewHolder, position: Int) {
        holder.country.text      = data[position].country
        holder.createdAt.text    = data[position].createdAt
        holder.rating.text       = context.getString(R.string.rate_decimal, data[position].rating)

        // cardタグ セット
        ViewUtil.setCardTag(holder.farm, data[position].farm)
        ViewUtil.setCardTag(holder.district, data[position].district)

        // お気に入りアイコンのセット
        setFavoriteIcon(holder.favorite, data[position].isFavorite)

        // お気に入りアイコン コールバックセット
        holder.favorite.setOnClickListener { view ->
            mFavoriteListener.onFavoriteClick(view, data[position])
        }

        // アイテムタップ時のコールバックセット
        holder.itemView.setOnClickListener { view ->
            mItemClickListener.onClick(view, data[position])
        }
    }
}


class SimpleBeanInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val country  : TextView  = itemView.findViewById(R.id.country)
    val createdAt: TextView  = itemView.findViewById(R.id.createdAt)
    val farm     : TextView  = itemView.findViewById(R.id.farm)
    val district : TextView  = itemView.findViewById(R.id.district)
    val rating   : TextView  = itemView.findViewById(R.id.rating)
    val favorite : ImageView = itemView.findViewById(R.id.favoriteIcon)
}