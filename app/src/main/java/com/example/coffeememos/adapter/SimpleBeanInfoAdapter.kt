package com.example.coffeememos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.SimpleBeanInfo
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.entity.Bean

import com.example.coffeememos.entity.RecipeWithBeans

class SimpleBeanInfoAdapter(context: Context, data: List<SimpleBeanInfo>) : BaseAdapter<SimpleBeanInfo, SimpleBeanInfoViewHolder>(context, data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleBeanInfoViewHolder {
        return SimpleBeanInfoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bean_item_horizontal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SimpleBeanInfoViewHolder, position: Int) {
        holder.country.text      = data[position].country
        holder.createdAt.text    = data[position].createdAt
        holder.farm.text         = data[position].farm
        holder.district.text     = data[position].district
        holder.rating.text       = context.getString(R.string.rate_decimal, data[position].rating)

        // お気に入りアイコンのセット
        setFavoriteIcon(holder.favorite, data[position].isFavorite)

        // アイテムタップ時のコールバックセット
        holder.itemView.setOnClickListener { view ->
            mListener.onClick(view, data[position])
        }

        holder.favorite.setOnClickListener {
            Toast.makeText(context, "favorite clicked!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFavoriteIcon(favoriteIcon: ImageView, isFavorite: Boolean) {
        if (isFavorite) favoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
        else favoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
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